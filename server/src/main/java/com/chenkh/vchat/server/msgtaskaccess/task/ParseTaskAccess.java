package com.chenkh.vchat.server.msgtaskaccess.task;

import com.chenkh.vchat.base.msg.ClientMsg;

import java.nio.channels.AsynchronousSocketChannel;



/**
 * 此接口是所有中间任务类（任务转换类）要实现的接口
 * 
 * @author Administrator
 * 
 */
public interface ParseTaskAccess {

	/**
	 * 得到客户端信息
	 * 
	 * @return 客户端信息
	 */
	public ClientMsg getMsg();

	/**
	 * 得到客户端异步套接字
	 * 
	 * @return 客户端异步套接字
	 */
	public AsynchronousSocketChannel getSocket();

}
