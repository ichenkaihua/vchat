package com.chenkh.vchat.base.msg;

import java.io.Serializable;

public interface ServerMsg extends Serializable {
	
	public void parse(ClientMsgMgr mgr);

}
