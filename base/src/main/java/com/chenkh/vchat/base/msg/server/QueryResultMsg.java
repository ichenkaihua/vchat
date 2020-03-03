package com.chenkh.vchat.base.msg.server;

import java.util.List;

import com.chenkh.vchat.base.msg.ClientMsgMgr;
import com.chenkh.vchat.base.msg.ServerMsg;
import com.chenkh.vchat.base.bean.Stranger;

public class QueryResultMsg implements ServerMsg {

	private final List<Stranger> strangers;
	private final int toId;

	public QueryResultMsg(int toId, List<Stranger> strangers) {
		this.toId = toId;
		this.strangers = strangers;
	}

	public List<Stranger> getStrangers() {
		return strangers;
	}

	public int getToId() {
		return toId;
	}

	@Override
	public void parse(ClientMsgMgr mgr) {
		mgr.parseMsg(this);
	}

}
