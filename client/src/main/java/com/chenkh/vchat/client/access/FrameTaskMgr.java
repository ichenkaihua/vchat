package com.chenkh.vchat.client.access;


public interface FrameTaskMgr extends IPut {
	
	//public void putMsg(ClientMsg msg);
	
	public void putChatMsg(String content,int toId);
	
	
	
	
	

}
