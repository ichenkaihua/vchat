package com.chenkh.vchat.client.access;


import com.chenkh.vchat.base.msg.ClientMsg;
import com.chenkh.vchat.base.msg.Msg;
import com.chenkh.vchat.base.msg.MsgBody;

public interface IPut {

	//public void putMsg(ClientMsg msg);

	void putMsg(Msg msg);

}
