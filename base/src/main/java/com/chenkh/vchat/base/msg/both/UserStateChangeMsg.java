package com.chenkh.vchat.base.msg.both;

import com.chenkh.vchat.base.bean.VState;

public class UserStateChangeMsg {
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



}
