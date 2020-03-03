package com.chenkh.vchat.base.msg.client;

import java.nio.channels.AsynchronousSocketChannel;

import com.chenkh.vchat.base.msg.ClientMsg;
import com.chenkh.vchat.base.msg.ServerMsgMgr;

public class AddFriendMsg implements ClientMsg {

	private final int myId;
	private final int friendId;
	private final int groupId;
	private final String noteName;

	public AddFriendMsg(int myId, int friendId, int groupId, String noteName) {
		this.myId = myId;
		this.friendId = friendId;
		this.groupId = groupId;
		this.noteName = noteName;
	}

	@Override
	public void parse(ServerMsgMgr mgr, AsynchronousSocketChannel socket) {
		mgr.parseMsg(this, socket);

	}

	public int getMyId() {
		return myId;
	}

	public int getFriendId() {
		return friendId;
	}

	public int getGroupId() {
		return groupId;
	}

	public String getNoteName() {
		return noteName;
	}

}
