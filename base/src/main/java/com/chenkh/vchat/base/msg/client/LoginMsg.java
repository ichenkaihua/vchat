package com.chenkh.vchat.base.msg.client;

import java.nio.channels.AsynchronousSocketChannel;

import com.chenkh.vchat.base.msg.ClientMsg;
import com.chenkh.vchat.base.msg.ServerMsgMgr;
import com.chenkh.vchat.base.bean.VState;

public class LoginMsg implements ClientMsg {

	private final int id;


	private final String password;
	private VState state;
	
	public VState getState() {
		return state;
	}


	public void setState(VState state) {
		this.state = state;
	}


	public int getId() {
		return id;
	}


	public String getPassword() {
		return password;
	}

	public LoginMsg(int id, String password,VState state) {
		this.id = id;
		this.state = state;
		this.password = password;

	}

	@Override
	public void parse(ServerMsgMgr mgr, AsynchronousSocketChannel socket) {
		mgr.parseMsg(this, socket);
	}

}
