package com.chenkh.vchat.base.msg.server;

import com.chenkh.vchat.base.msg.ClientMsgMgr;
import com.chenkh.vchat.base.msg.ServerMsg;
import com.chenkh.vchat.base.bean.Stranger;

public class StrangerMsg implements ServerMsg {
	public Stranger getStranger() {
		return stranger;
	}

	public String getMsg() {
		return msg;
	}

	public int getToId() {
		return toId;
	}

	public StrangerMsg(Stranger stranger, String msg, int toId) {
		super();
		this.stranger = stranger;
		this.msg = msg;
		this.toId = toId;
	}

	private final Stranger stranger;
	private final String msg;
	private final int toId;

	@Override
	public void parse(ClientMsgMgr mgr) {
		mgr.parseMsg(this);
	}

}
