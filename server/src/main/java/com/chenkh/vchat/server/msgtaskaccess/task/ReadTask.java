package com.chenkh.vchat.server.msgtaskaccess.task;

import com.chenkh.vchat.base.msg.ClientMsg;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;



public class ReadTask implements ReadTaskAccess {

	private ByteBuffer bf;
	private AsynchronousSocketChannel socket;

	public ReadTask(ByteBuffer bf, AsynchronousSocketChannel socket) {
		this.bf = bf;
		this.socket = socket;
	}

	@Override
	public ByteBuffer getByteBuffer() {

		return bf;
	}

	@Override
	public ParseTaskAccess parseTask() {
		//定义一个parseTask
		ParseTaskAccess parseTask = null;
		//重置bf,使之成为可读状态
		bf.flip();
		//定义一个byte数组，负责装载bf的内容
		byte[] readByte = new byte[bf.limit()];
		//将bf的内容映射到byte数组
		bf.get(readByte);
		// 定义一个字节数组流,从字节数组读取内容
		ByteArrayInputStream bis = new ByteArrayInputStream(readByte);
		//包装成一个对象输入流
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(bis);
			//读取信息后装换成对象
			ClientMsg clientMsg = (ClientMsg) ois.readObject();
			//生成新的任务
			parseTask = new ParseTask(clientMsg,socket);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
				if(bis != null){
					bis.close();
				}

			} catch (IOException e) {

			}
		}
		//返回转换后的任务
		return parseTask;
	}

	@Override
	public AsynchronousSocketChannel getSocket() {

		return socket;
	}

}
