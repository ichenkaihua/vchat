package com.chenkh.vchat.client.frame;

import java.util.LinkedList;

import com.chenkh.vchat.client.frame.chat.ChatFrame;
import com.chenkh.vchat.client.frame.chat.ChatFrameMgr;

public class ObjectContainer {

	private LinkedList<ChatFrame> frames = new LinkedList<ChatFrame>();
	
	
	

	public ChatFrame getChatFrame(ChatFrameMgr mgr, int maxSize) {
		ChatFrame frame = null;
		if (frames.isEmpty()) {
			frame = new ChatFrame(mgr, maxSize);
			this.tackFrame(frame);
		} else {
			frame = frames.poll();
			frame.setMaxSize(maxSize);
		}
		return frame;
	}

	public void tackFrame(ChatFrame frame) {
		frames.add(frame);
	}
	
	
	
	
	
	
	
	
	

}
