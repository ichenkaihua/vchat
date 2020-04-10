package com.chenkh.vchat.base.msg;

import java.util.List;

public class OfflineMsg   {
	private int toId;

	//private ContenMsg[] msgs;
	List<ContenMsg> msgs;

	public OfflineMsg(int toId, List<ContenMsg> msgs) {
		this.toId = toId;
		this.msgs = msgs;
	}


	public int getToId() {
		return toId;
	}

	public void setToId(int toId) {
		this.toId = toId;
	}

	public List<ContenMsg> getMsgs() {
		return msgs;
	}

}
