package com.chenkh.vchat.server.msgtaskaccess.task;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

/**
 * 此接口是所有向客户端写任务的包装类要实现的接口
 * 
 * @author Administrator
 * 
 */
public interface WriteTaskAccess {
	/**
	 * 得到ByteBuffer
	 * 
	 * @return ByteBuffer
	 */
	public ByteBuffer getByteBuffer();

	/**
	 * 得到异步套接字
	 * 
	 * @return 异步套接字
	 */
	public AsynchronousSocketChannel getSocket();

}
