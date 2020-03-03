package com.chenkh.vchat.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


import com.chenkh.vchat.base.msg.ClientMsg;
import com.chenkh.vchat.base.msg.ServerMsg;
import com.chenkh.vchat.client.access.IPut;
import com.chenkh.vchat.client.access.MsgTaskMgr;
import com.chenkh.vchat.client.access.NetTaskMgr;

public class TaskMgr implements NetTaskMgr,IPut, MsgTaskMgr {
	// 装载从服务器读到的ByteBuffer，供转换线程转换成ServerMsg
	private BlockingQueue<ByteBuffer> reciverByteBuffers = new ArrayBlockingQueue<ByteBuffer>(
			30);
	// 装载服务器传送过来的消息，供消息分解者分解
	private BlockingQueue<ServerMsg> reciverMsgs = new ArrayBlockingQueue<ServerMsg>(
			30);
	// 装载客户端消息，由界面类添加
	private BlockingQueue<ClientMsg> sendMsgs = new ArrayBlockingQueue<ClientMsg>(
			30);
	// 装载客户端消息的ByteBuffer，供异步套接字发送给服务端
	private BlockingQueue<ByteBuffer> sendByteBuffers = new ArrayBlockingQueue<ByteBuffer>(
			30);
	// 将byteBuffer转换成ServerMsg的线程
	private Thread parseReciverMsgsThread = new Thread(
			new ParseReciverMsgsThread());

	// 将客户端msg对象转换成byteBufer对象的线程
	private Thread parseSendBufferThread = new Thread(
			new ParseSendBufferThread());

	public TaskMgr() {
		parseReciverMsgsThread.start();
		parseSendBufferThread.start();
	}

	@Override
	public void putMsgByteBuffer(ByteBuffer bf) {

		try {
			reciverByteBuffers.put(bf);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	@Override
	public ByteBuffer getClientMsgByteBuffer() {
		ByteBuffer bf = null;
		try {
			bf = sendByteBuffers.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return bf;
	}

	private class ParseReciverMsgsThread implements Runnable {

		@Override
		public void run() {
			try {
				while (true) {

					ByteBuffer bf = reciverByteBuffers.take();
					bf.flip();
					byte[] readByte = new byte[bf.limit()];
					bf.get(readByte);
					ByteArrayInputStream bis = new ByteArrayInputStream(
							readByte);
					ObjectInputStream ois = null;
					try {
						ois = new ObjectInputStream(bis);
						ServerMsg msg = (ServerMsg) ois.readObject();
						reciverMsgs.put(msg);
					}catch(EOFException e){
System.out.println("字节数组无内容");
						
					}  
					catch (IOException e) {

						e.printStackTrace();
					} 
					
					catch (ClassNotFoundException e) {

						e.printStackTrace();
					}
					finally {
						try {
							if (ois != null) {
								ois.close();
								ois = null;
							}
							if (bis != null) {
								bis.close();
								bis = null;
							}
						} catch (IOException e) {

						}
					}

				}
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}

	}

	private class ParseSendBufferThread implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					ClientMsg sendMsg = sendMsgs.take();
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					ObjectOutputStream oos = null;
					try {
						oos = new ObjectOutputStream(bos);
						oos.writeObject(sendMsg);
						byte[] writeByte = bos.toByteArray();
						ByteBuffer bf = ByteBuffer.wrap(writeByte);
						sendByteBuffers.put(bf);
						

					} catch (IOException e) {

						e.printStackTrace();
					} finally {
						try {
							if (oos != null) {
								oos.close();
								oos = null;
							}
							if (bos != null) {
								bos.close();
								bos = null;
							}
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}

		}

	}

	@Override
	public ServerMsg getServerMsg() {
		ServerMsg msg = null;

		try {
			msg = reciverMsgs.take();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		return msg;
	}

	@Override
	public void putMsg(ClientMsg msg) {
		try {
			sendMsgs.put(msg);
			System.out.println("一个任务添加成功，来自taskMgr");
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

}
