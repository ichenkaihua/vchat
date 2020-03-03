package com.chenkh.vchat.base.msg.server;

import com.chenkh.vchat.base.msg.ClientMsgMgr;
import com.chenkh.vchat.base.msg.ServerMsg;

public class NotifyAddFriendMsg implements ServerMsg {

	@Override
	public void parse(ClientMsgMgr mgr) {
		mgr.parseMsg(this);
	}

}
