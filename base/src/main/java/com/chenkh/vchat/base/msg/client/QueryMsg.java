package com.chenkh.vchat.base.msg.client;

import java.nio.channels.AsynchronousSocketChannel;

import com.chenkh.vchat.base.msg.ClientMsg;
import com.chenkh.vchat.base.msg.ServerMsgMgr;

public class QueryMsg  {
	private final String keyword;
	private final int queryId;
	
	public QueryMsg(String keyword,int id){
		this.keyword = keyword;
		this.queryId = id;
	}
	
	public int getQueryId(){
		return queryId;
	}
	
	public String getKeyword(){
		return this.keyword;
	}
	

	
	
	
	
	
	
	
	

}
