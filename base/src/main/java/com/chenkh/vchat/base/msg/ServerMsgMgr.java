package com.chenkh.vchat.base.msg;

import java.nio.channels.AsynchronousSocketChannel;

import com.chenkh.vchat.base.msg.both.DeleteFriendMsg;
import com.chenkh.vchat.base.msg.both.UserStateChangeMsg;
import com.chenkh.vchat.base.msg.client.AddFriendMsg;
import com.chenkh.vchat.base.msg.client.LoginMsg;
import com.chenkh.vchat.base.msg.client.QueryMsg;
import com.chenkh.vchat.base.msg.client.RegisterMsg;


/**
 * 服务端的消息管理器，负责分解客户端传过来的消息
 * @author Administrator
 *
 */

public interface ServerMsgMgr {
	
	
	public void parseMsg(ClientMsg msg,AsynchronousSocketChannel socket);
	
	
	public void parseMsg(LoginMsg msg,AsynchronousSocketChannel socket);
	
	
	public void parseMsg(ChatMsg msg,AsynchronousSocketChannel socket);
	
	
	public void parseMsg(VerifyMsg msg,AsynchronousSocketChannel socket);
	
	
	public void parseMsg(UserStateChangeMsg msg,AsynchronousSocketChannel socket);
	
	
	public void parseMsg(RegisterMsg msg,AsynchronousSocketChannel socket);
	
	public void parseMsg(QueryMsg msg,AsynchronousSocketChannel socket);
	
	public void parseMsg(AddFriendMsg msg,AsynchronousSocketChannel socket);
	
	
	public void parseMsg(DeleteFriendMsg msg,AsynchronousSocketChannel socket);
	
	
	
	
	
	
	
	
	
	
	

}
