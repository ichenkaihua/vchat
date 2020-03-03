package com.chenkh.vchat.base.msg.server;

import com.chenkh.vchat.base.msg.ClientMsgMgr;
import com.chenkh.vchat.base.msg.ServerMsg;
import com.chenkh.vchat.base.bean.Friend;

public class AddFriendSucess implements ServerMsg {

	private final Friend friend;
	private final int groupId;

	@Override
	public void parse(ClientMsgMgr mgr) {
		mgr.parseMsg(this);

	}

	public AddFriendSucess(Friend friend, int groupId) {
		this.friend = friend;
		this.groupId = groupId;
	}

	public Friend getFriend() {
		return friend;
	}

	public int getGroupId() {
		return groupId;
	}

}
