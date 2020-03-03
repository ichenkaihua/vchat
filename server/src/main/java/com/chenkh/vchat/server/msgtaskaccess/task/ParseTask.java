package com.chenkh.vchat.server.msgtaskaccess.task;

import com.chenkh.vchat.base.msg.ClientMsg;

import java.nio.channels.AsynchronousSocketChannel;



public class ParseTask implements ParseTaskAccess {
	private ClientMsg msg;
	private AsynchronousSocketChannel socket;

	public ParseTask(ClientMsg msg, AsynchronousSocketChannel socket) {
		this.msg = msg;
		this.socket = socket;
	}

	@Override
	public ClientMsg getMsg() {
		return msg;
	}

	@Override
	public AsynchronousSocketChannel getSocket() {
		return socket;
	}

}
