package com.chenkh.vchat.server.msgtaskaccess.task;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
/**
 * 从客户端读取内容的包装接口
 * 所有从客户端读取的信息都包装类的接口
 * @author Administrator
 *
 */
public interface ReadTaskAccess {
	
	/**
	 * 得到ByteBuffer 
	 * @return ByteBuffer
	 */
	public ByteBuffer getByteBuffer();
	
	/**
	 * 负责将对象转换成ParseTaskAccess对象
	 * @return 转换后的ParseTaskAccess对象
	 */
	public ParseTaskAccess parseTask();
	
	/**
	 * 得到任务的套接字
	 * @return 套接字
	 */
	public AsynchronousSocketChannel getSocket();
	
	

}
