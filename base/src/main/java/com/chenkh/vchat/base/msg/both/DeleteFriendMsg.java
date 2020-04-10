package com.chenkh.vchat.base.msg.both;


public class DeleteFriendMsg   {
	private final int myId;
	private final int friendId;
	private final int groupId;

	public int getMyId() {
		return myId;
	}

	public int getFriendId() {
		return friendId;
	}

	public int getGroupId() {
		return groupId;
	}

	public DeleteFriendMsg(int myId, int friendId, int groupId) {
		super();
		this.myId = myId;
		this.friendId = friendId;
		this.groupId = groupId;
	}



}
