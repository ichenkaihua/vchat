package com.chenkh.vchat.base.msg.client;

import java.nio.channels.AsynchronousSocketChannel;

import com.chenkh.vchat.base.msg.ClientMsg;
import com.chenkh.vchat.base.msg.ServerMsgMgr;
import com.chenkh.vchat.base.bean.User;

public class RegisterMsg  {
	private final User user;

	public RegisterMsg(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}



}
