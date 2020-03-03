package com.chenkh.vchat.base.msg.both;

import java.nio.channels.AsynchronousSocketChannel;

import com.chenkh.vchat.base.msg.ClientMsg;
import com.chenkh.vchat.base.msg.ClientMsgMgr;
import com.chenkh.vchat.base.msg.ServerMsg;
import com.chenkh.vchat.base.msg.ServerMsgMgr;
import com.chenkh.vchat.base.bean.VState;

public class UserStateChangeMsg implements ClientMsg, ServerMsg {
	int fromId;
	VState state;

	public UserStateChangeMsg(int fromId, VState state) {

		this.fromId = fromId;
		this.state = state;
	}

	public int getFromId() {
		return fromId;
	}

	public void setFromId(int fromId) {
		this.fromId = fromId;
	}

	public VState getState() {
		return state;
	}

	public void setState(VState state) {
		this.state = state;
	}

	@Override
	public void parse(ClientMsgMgr mgr) {
		mgr.parseMsg(this);

	}

	@Override
	public void parse(ServerMsgMgr mgr, AsynchronousSocketChannel socket) {
		mgr.parseMsg(this, socket);
	}

}
