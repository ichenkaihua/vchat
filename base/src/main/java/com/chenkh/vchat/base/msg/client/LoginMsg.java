package com.chenkh.vchat.base.msg.client;

import java.nio.channels.AsynchronousSocketChannel;

import com.chenkh.vchat.base.msg.ClientMsg;
import com.chenkh.vchat.base.msg.ServerMsgMgr;
import com.chenkh.vchat.base.bean.VState;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class LoginMsg  {

	private final int id;

	private final String password;
	private final VState state;


	public LoginMsg(int id, String password,VState state) {
		this.id = id;
		this.state = state;
		this.password = password;

	}



}
