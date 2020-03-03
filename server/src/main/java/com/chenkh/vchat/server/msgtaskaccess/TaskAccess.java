package com.chenkh.vchat.server.msgtaskaccess;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

import com.chenkh.vchat.server.msgtaskaccess.task.ReadTaskAccess;
import com.chenkh.vchat.server.msgtaskaccess.task.WriteTaskAccess;

/**
 * 管理所有任务的接口，对第一层提供的接口
 * @author Administrator
 *
 */
public interface TaskAccess {
	/**
	 * 向管理器增加从客户端读取到的任务
	 * @param task 要增加的任务
	 */
	public void addReadTask(ReadTaskAccess task);
	/**
	 * 得到一个像客户端写的任务
	 * @return 向客户端写的任务
	 */
	public 	WriteTaskAccess getWriteTask();
	
	/**
	 * 通过特定的ByteBuffer和异步套接字增加一个从客户端读取的任务
	 * @param bf 已经读取到有内容的ByteBuffer
	 * @param socket 异步套接字
	 */
	public void addReadTask(ByteBuffer bf,AsynchronousSocketChannel socket);
	
	public void reMoveClient(AsynchronousSocketChannel socket);

}
