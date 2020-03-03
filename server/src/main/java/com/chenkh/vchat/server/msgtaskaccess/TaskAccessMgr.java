package com.chenkh.vchat.server.msgtaskaccess;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;


import com.chenkh.vchat.server.msgtaskaccess.task.WriteTaskAccess;

/**
 * 对信息管理类开放的接
 * 
 * @author Administrator
 * 
 */
public interface TaskAccessMgr {
	/**
	 * 增加一个向客户端写的任务
	 * 
	 * @param task
	 *            指定的任务
	 */
	public void addWriteTask(WriteTaskAccess task);

	/**
	 * 通过已经读取到内容的ByteBuffer和未关闭的套接字增加一个像客户端写的任务
	 * 
	 * @param bf
	 *            已经读取到的内容的ByteBuffer
	 * @param socket
	 *            未关闭的套接字
	 */
	public void addWriteTask(ByteBuffer bf, AsynchronousSocketChannel socket);
	
	
	
	
	
	

}
