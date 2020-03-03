package com.chenkh.vchat.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Executors;

import com.chenkh.vchat.client.access.NetTaskMgr;

public class VNet {
	private AsynchronousSocketChannel socket;
	private AsynchronousChannelGroup group;

	private NetTaskMgr taskMgr;
	

	public VNet(NetTaskMgr taskMgr) {
		this.taskMgr = taskMgr;
		try {
			group = AsynchronousChannelGroup.withCachedThreadPool(
					Executors.newCachedThreadPool(), 2);
			socket = AsynchronousSocketChannel.open(group);
			socket.connect(new InetSocketAddress("127.0.0.1", 2567), null,
					new ConnectionHandler());
			new Thread(new WriteMsgThread()).start();
			
	

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private void appdingRead() {

		ByteBuffer bf = ByteBuffer.allocate(1524);

		socket.read(bf, bf, new ReadHandler());

	}

	private class WriteMsgThread implements Runnable {
		WriteHandler writeHandler = new WriteHandler();

		@Override
		public void run() {
			while (true) {
				ByteBuffer bf = taskMgr.getClientMsgByteBuffer();
				socket.write(bf, null, writeHandler);
			}

		}

	}
	
	
	
	

	private class ConnectionHandler implements CompletionHandler<Void, Void> {

		@Override
		public void completed(Void result, Void attachment) {
System.out.println("连接至服务器成功");
			appdingRead();

		}

		@Override
		public void failed(Throwable exc, Void attachment) {
/*System.out.println("连接失败至服务器失败!,正在尝试重新连接");
			socket.connect(new InetSocketAddress("127.0.0.1", 2547), null,
				this);
*/
		}

	}

	private class WriteHandler implements CompletionHandler<Integer, Void> {

		@Override
		public void completed(Integer result, Void attachment) {
System.out.println("一个消息写入服务器成功--大小:"+result);

		}

		@Override
		public void failed(Throwable exc, Void attachment) {
System.out.println("一个消息写入服务器失败");

		}

	}

	private class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {

		@Override
		public void completed(Integer result, ByteBuffer attachment) {

			if(result >0){
System.out.println("从服务器接收到一个消息--大小:"+result+"接收下一个连接");
				taskMgr.putMsgByteBuffer(attachment);
				appdingRead();
				
			}
			else {
System.out.println("接收到服务器消息为空,接收下一个连接");
			
			}

			
		

		}

		@Override
		public void failed(Throwable exc, ByteBuffer attachment) {
System.out.println("尝试从服务器读取消息失败,尝试从服务器读取下一个消息");
			attachment.clear();
			System.out.println("length:"+attachment.limit());
			//socket.read(attachment,attachment,this);

						

		}

	}




}
