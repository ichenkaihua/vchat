package com.chenkh.vchat.client.access;

import java.nio.ByteBuffer;

public interface NetTaskMgr {
	
	 void putMsgByteBuffer(ByteBuffer bf);
	
	 ByteBuffer getClientMsgByteBuffer();
	
	
	
	
	
	

}
