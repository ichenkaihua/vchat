package com.chenkh.vchat.base.msg.both;

import java.nio.channels.AsynchronousSocketChannel;

import com.chenkh.vchat.base.msg.ClientMsg;
import com.chenkh.vchat.base.msg.ClientMsgMgr;
import com.chenkh.vchat.base.msg.ServerMsg;
import com.chenkh.vchat.base.msg.ServerMsgMgr;

public class DeleteFriendMsg implements ClientMsg,ServerMsg {
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

	@Override
	public void parse(ServerMsgMgr mgr, AsynchronousSocketChannel socket) {
		mgr.parseMsg(this, socket);

	}

	@Override
	public void parse(ClientMsgMgr mgr) {
		mgr.parseMsg(this);
		
	}

}
