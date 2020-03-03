package com.chenkh.vchat.client.frame.msgMgr;

import com.chenkh.vchat.base.bean.VState;


public interface OnlineMsgListener {
	/**
	 * 忽略一条消息
	 * @param id 发送消息的id号
	 */
	public void ingoreMsg(int id);
	/**
	 * 忽略所有消息
	 */
	public void ingoreAllMsg();
	
	/**
	 * 接收到一条消息
	 * @param id
	 */
	public void recivedMsg(int id,String content);
	
	
	public void userStateChanged(VState state);
	
	
	
	
	
	
	
	

}
