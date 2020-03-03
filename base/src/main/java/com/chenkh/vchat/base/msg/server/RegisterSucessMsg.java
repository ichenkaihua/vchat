package com.chenkh.vchat.base.msg.server;

import com.chenkh.vchat.base.msg.ClientMsgMgr;
import com.chenkh.vchat.base.msg.ServerMsg;

public class RegisterSucessMsg implements ServerMsg {
	private final int id;
	
	
	public RegisterSucessMsg(int id){
		this.id = id;
	}
	
	
	public int getId(){
		return this.id;
	}
	
	
	

	@Override
	public void parse(ClientMsgMgr mgr) {
		mgr.parseMsg(this);
	}

}
