package com.chenkh.vchat.base.msg;

import java.nio.channels.AsynchronousSocketChannel;
import java.sql.Timestamp;

public class ChatMsg implements ServerMsg,ClientMsg {
	private final int fromId;
	private final int toId;
	private final String content;
	private final Timestamp date;

	public ChatMsg(int fromId, int toId,String content, Timestamp date) {
		this.fromId = fromId;
		this.toId = toId;
		this.content = content;
		this.date = date;
	}

	public int getFromId() {
		return fromId;
	}

	public String getContent() {
		return content;
	}

	public Timestamp getDate() {
		return date;
	}

	@Override
	public void parse(ClientMsgMgr mgr) {
		mgr.parseMsg(this);
	}

	@Override
	public void parse(ServerMsgMgr mgr, AsynchronousSocketChannel socket) {
		mgr.parseMsg(this, socket);
		
	}

	public int getToId() {
		return toId;
	}

}
