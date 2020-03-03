package com.chenkh.vchat.server.dao;

import com.chenkh.vchat.base.msg.ContenMsg;

import java.sql.Timestamp;
import java.util.List;



public interface MsgDAO  {
	
	public List<ContenMsg> getOfflineMsg(int id);
	
	
	public void addOfflineMsg(int fromId,int toId,String msg,Timestamp date);
	
	

}
