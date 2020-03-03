package com.chenkh.vchat.client.frame.chat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.chenkh.vchat.client.access.FrameTaskMgr;
import com.chenkh.vchat.client.frame.ObjectContainer;

public class ChatFrameMgr implements ChatAccess {

	private List<ChatFrame> frames = new ArrayList<ChatFrame>();
	private int maxSize = 6;
	private FrameTaskMgr mgr;
	private Set<Integer> openIds = new HashSet<Integer>();
	private ObjectContainer objc = new ObjectContainer();

	public ChatFrameMgr(FrameTaskMgr mgr) {

		this.mgr = mgr;

	}

	public void addChat(int friendId) {

		for (ChatFrame frame : frames) {

			if (!frame.isFull()) {
				frame.addChat(friendId);
				return;
			}

		}

		ChatFrame frame = objc.getChatFrame(this, maxSize);
		frames.add(frame);
		frame.addChat(friendId);
		openIds.add(friendId);

	}

	public void removeOpenChat(int id) {
		openIds.remove(id);

	}

	public void removeOpenChat(Set<Integer> ids) {
		openIds.removeAll(ids);

	}

	@Override
	public void setSelect(int id) {
		for (ChatFrame frame : frames) {

			if (frame.isContain(id)) {
				frame.setSelect(id);
				return;
			}

		}

	}

	public void sendMsg(String msg, int friendId) {
		mgr.putChatMsg(msg, friendId);
	}

	@Override
	public boolean isContain(int id) {

		// return this.openIds.contains(id);

		for (ChatFrame frame : frames) {

			if (frame.isContain(id)) {

				return true;
			}

		}

		return false;

	}

	@Override
	public void reciveMsg(int id) {
		
		for (ChatFrame frame : frames) {

			if (frame.isContain(id)) {
				frame.reciveMsg(id);
				return;
			}

		}
		

	}

}
