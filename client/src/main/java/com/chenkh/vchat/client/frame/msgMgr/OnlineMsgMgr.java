package com.chenkh.vchat.client.frame.msgMgr;

import com.chenkh.vchat.base.bean.VState;

public interface OnlineMsgMgr  {
	
	
	
	
	/**
	 * 通知管理者忽略一条消息
	 * @param id
	 */
	public void notifyIgnoreMsg(int id);
	
	/**
	 * 通知管理者忽略所有消息
	 * @param id
	 */
	public void notifyIgnoreAllMsg();
	
	
	/**
	 * 通知管理者打开一条消息
	 * @param id 
	 */
	public void notifyOpenMsg(int id);
	
	
	
	public void notifyUserStateChanged(VState state);
	
	
	
	public void add(OnlineMsgListener listener);
	
	
	
	public boolean isHaveUnreadMsg(int id);
	
	
	public boolean haveUnreadMsg();
	
	
	
	
	
	
	
	
	

}
