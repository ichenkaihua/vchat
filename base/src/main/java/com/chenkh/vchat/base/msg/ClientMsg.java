package com.chenkh.vchat.base.msg;

import java.io.Serializable;
import java.nio.channels.AsynchronousSocketChannel;

/**
 * 客户端信息的总接口，分解时，需要借助服务器消息管理器接口，和一个通道口
 * @author Administrator
 *
 */
public interface ClientMsg extends Serializable {
	
	public void parse(ServerMsgMgr mgr,AsynchronousSocketChannel socket);
	

}
