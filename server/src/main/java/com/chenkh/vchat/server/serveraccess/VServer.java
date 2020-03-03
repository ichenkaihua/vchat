package com.chenkh.vchat.server.serveraccess;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Executors;

import com.chenkh.vchat.server.msgtaskaccess.TaskAccess;
import com.chenkh.vchat.server.msgtaskaccess.TaskMgr;
import com.chenkh.vchat.server.msgtaskaccess.task.WriteTaskAccess;

/**
 * 服务器的总管理者，
 * 
 * @author Administrator
 * 
 */
public class VServer implements ServerAccess {
	private AsynchronousChannelGroup group;
	private AsynchronousServerSocketChannel server;
	private boolean start = false;
	private final int port;
	private TaskAccess taskMgr = null;
	private AcceptHandler acceptHandler = null;
	private Thread writeThread = new Thread(new WriteThread(), "writeThread");

	public VServer(int port) {
		this.port = port;
		taskMgr = new TaskMgr();
		acceptHandler = new AcceptHandler();

		try {
			group = AsynchronousChannelGroup.withCachedThreadPool(
					Executors.newCachedThreadPool(), 50);
			server = AsynchronousServerSocketChannel.open(group);
			server.bind(new InetSocketAddress("127.0.0.1", port));

			System.out.println("server开启成功!");
		} catch (IOException e) {

			e.printStackTrace();
		}

		start = true;
		writeThread.start();

		server.accept(null, acceptHandler);
	}
	//绑定地址
	private String getLocalAddress() {
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}

	public static void main(String[] args) {
		new VServer(2567);
	}

	private class WriteThread implements Runnable {

		@Override
		public void run() {
			WriteHandler handler = new WriteHandler();
			while (start) {
				WriteTaskAccess task = taskMgr.getWriteTask();
				ByteBuffer bf = task.getByteBuffer();
				AsynchronousSocketChannel socket = task.getSocket();
				socket.write(bf, socket, handler);
			}

		}

	}

	private class AcceptHandler implements
			CompletionHandler<AsynchronousSocketChannel, Void> {

		@Override
		public void completed(AsynchronousSocketChannel result, Void attachment) {

			ByteBuffer bf = ByteBuffer.allocate(1024);
			result.read(bf, bf, new ReadHandler(result));



			System.out.println("一个客户端连接成功,重新注册新连接");
			server.accept(null, this);
		}

		@Override
		public void failed(Throwable exc, Void attachment) {
			System.out.println("一个客户端尝试连接失败,重新注册新连接");
			server.accept(null, this);

		}

	}

	private class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {
		private AsynchronousSocketChannel socket;

		public ReadHandler(AsynchronousSocketChannel socket) {
			this.socket = socket;
		}

		@Override
		public void completed(Integer result, ByteBuffer attachment) {
			System.out.println("读取到来自客户端的消息--大小:" + result);

			if (result <= -1) {
				System.out.println("客户端已经关闭");
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {

				taskMgr.addReadTask(attachment, socket);
				ByteBuffer bf = ByteBuffer.allocate(1024);
				socket.read(bf, bf, this);
			}

		}

		@Override
		public void failed(Throwable exc, ByteBuffer attachment) {
			System.out.println("一个客户端读消息读取失败,读取下一次消息");
			try {
				socket.close();
				taskMgr.reMoveClient(socket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/*
			 * ByteBuffer bf = ByteBuffer.allocate(1024); try {
			 * System.out.println(socket.getRemoteAddress()); } catch
			 * (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); } socket.read(bf, bf, this);
			 */
		}

	}

	private class WriteHandler implements
			CompletionHandler<Integer, AsynchronousSocketChannel> {

		@Override
		public void completed(Integer result,
				AsynchronousSocketChannel attachment) {
			System.out.println("发送一个消息成功--大小:" + result);

		}

		@Override
		public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
			System.out.println("发送失败");

		}

	}

}
