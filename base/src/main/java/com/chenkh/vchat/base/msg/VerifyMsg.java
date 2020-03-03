package com.chenkh.vchat.base.msg;

import java.nio.channels.AsynchronousSocketChannel;

public class VerifyMsg implements ServerMsg, ClientMsg {

	private final int fromId;
	private final int toId;
	private final String content;

	public int getFromId() {
		return fromId;
	}

	public int getToId() {
		return toId;
	}

	public String getContent() {
		return content;
	}

	public VerifyMsg(int fromId, int toId, String content) {
		super();
		this.fromId = fromId;
		this.toId = toId;
		this.content = content;
	}

	@Override
	public void parse(ServerMsgMgr mgr, AsynchronousSocketChannel socket) {
		mgr.parseMsg(this, socket);

	}

	@Override
	public void parse(ClientMsgMgr mgr) {
		mgr.parseMsg(this);

	}

}
