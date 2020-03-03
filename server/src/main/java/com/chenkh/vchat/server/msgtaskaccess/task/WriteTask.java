package com.chenkh.vchat.server.msgtaskaccess.task;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class WriteTask implements WriteTaskAccess {
	private final ByteBuffer bf;
	private final AsynchronousSocketChannel socket;
	
	
	
	public WriteTask(ByteBuffer bf,AsynchronousSocketChannel socket){
		this.bf = bf;
		this.socket = socket;
	}
	
	
	

	@Override
	public ByteBuffer getByteBuffer() {	
		return bf;
	}

	@Override
	public AsynchronousSocketChannel getSocket() {
		return socket;
	}

}
