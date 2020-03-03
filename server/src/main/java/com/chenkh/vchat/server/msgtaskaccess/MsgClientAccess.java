package com.chenkh.vchat.server.msgtaskaccess;

import com.chenkh.vchat.base.msg.ServerMsgMgr;

import java.nio.channels.AsynchronousSocketChannel;



public interface MsgClientAccess extends ServerMsgMgr {
	
	
	public void reMoveClient(AsynchronousSocketChannel socket);
	

}
