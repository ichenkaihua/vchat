package com.chenkh.vchat.client.listener;


import com.chenkh.vchat.base.msg.ChatMsg;
import com.chenkh.vchat.base.msg.VerifyMsg;
import com.chenkh.vchat.base.msg.both.DeleteFriendMsg;
import com.chenkh.vchat.base.msg.both.UserStateChangeMsg;
import com.chenkh.vchat.base.msg.server.AddFriendSucess;
import com.chenkh.vchat.base.msg.server.LoginResultMsg;
import com.chenkh.vchat.base.msg.server.QueryResultMsg;
import com.chenkh.vchat.base.msg.server.RegisterSucessMsg;
import com.chenkh.vchat.base.msg.server.StrangerMsg;

public interface MsgListener {

	public void parseMsg(UserStateChangeMsg msg);
	
	public void parseMsg(LoginResultMsg msg);

	public void parseMsg(ChatMsg msg);

	public void parseMsg(VerifyMsg msg);
	
	//解析消息
	public void parseMsg(RegisterSucessMsg msg);

	public void parseMsg(QueryResultMsg msg);

	public void parseMsg(AddFriendSucess msg);

	public void parseMsg(DeleteFriendMsg msg);

	public void parseMsg(StrangerMsg msg);
	

}
