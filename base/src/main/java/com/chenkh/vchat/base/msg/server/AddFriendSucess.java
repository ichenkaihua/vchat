package com.chenkh.vchat.base.msg.server;

import com.chenkh.vchat.base.bean.Friend;

public class AddFriendSucess {

	private final Friend friend;
	private final int groupId;



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
