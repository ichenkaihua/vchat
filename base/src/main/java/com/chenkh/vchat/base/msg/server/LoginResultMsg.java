package com.chenkh.vchat.base.msg.server;

import com.chenkh.vchat.base.msg.ClientMsgMgr;
import com.chenkh.vchat.base.msg.ServerMsg;
import com.chenkh.vchat.base.msg.server.enu.LoginResult_Type;
import com.chenkh.vchat.base.bean.User;

public class LoginResultMsg implements ServerMsg {
	private final LoginResult_Type result;
	private final User user;
	

	public User getUser() {
		return user;
	}

	public LoginResult_Type getResult() {
		return result;
	}

	public LoginResultMsg(LoginResult_Type result, User user) {
		this.user = user;
		this.result = result;
	}

	@Override
	public void parse(ClientMsgMgr mgr) {
		mgr.parseMsg(this);
	}

}
