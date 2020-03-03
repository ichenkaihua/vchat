package com.chenkh.vchat.client.frame.chat;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

import com.chenkh.vchat.client.UserMgr;
import com.chenkh.vchat.base.bean.Friend;

public class Tab {
	private TabPane tabPane;
	private TabContenPane contenPane;
	private int friendId;
	private ChatFrame frame;

	public Tab(ChatFrame frame, int friendId) {
		this.friendId = friendId;
		this.frame = frame;

		Friend friend = UserMgr.getInstance().getFriend(friendId);

		String name = UserMgr.getInstance().getName(friendId);
		String sign = UserMgr.getInstance().getSign(friendId);
		tabPane = new TabPane(this, name);

		StyledDocument doc = UserMgr.getInstance().getDocument(friendId);
		contenPane = new TabContenPane(this, doc, name, sign);

	}

	public void remove() {
		frame.removeChat(friendId);
	}

	public TabPane getTabPane() {
		return tabPane;
	}

	public TabContenPane getContenPane() {
		return contenPane;
	}

	public void sendMsg(String msg) {
		frame.sendMsg(friendId, msg);
		UserMgr.getInstance().insertString(friendId, msg, true);

	}

	public void setSelect() {
		frame.setSelect(friendId);
	}

	public void setEditorAttributeSet(SimpleAttributeSet set) {
		// TODO Auto-generated method stub

	}

}
