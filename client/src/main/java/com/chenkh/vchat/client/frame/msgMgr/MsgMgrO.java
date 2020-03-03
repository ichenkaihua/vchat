package com.chenkh.vchat.client.frame.msgMgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chenkh.vchat.base.bean.VState;
import com.chenkh.vchat.client.UserMgr;
import com.chenkh.vchat.client.frame.chat.ChatAccess;

public class MsgMgrO implements OnlineMsgMgr {

	private Set<OnlineMsgListener> listeners = new HashSet<OnlineMsgListener>();

	private ChatAccess chatMgr;

	private Map<Integer, List<String>> unReadMsgs = new HashMap<Integer, List<String>>();
	private UserMgr userMgr = UserMgr.getInstance();

	public MsgMgrO(ChatAccess chatMgr) {
		this.chatMgr = chatMgr;

	}


	
	public void recivedMsg(int id, String conten) {
		if (!unReadMsgs.containsKey(id)) {
			List<String> str = new ArrayList<String>();
			str.add(conten);
			unReadMsgs.put(id, str);
		} else {
			List<String> str = unReadMsgs.get(id);
			str.add(conten);
		}
		/**
		 * 如果这个人对话一经建立，则通知chatmgr来了一条消息，否则，通知托盘和主框架来了一条消息
		 */
		if (chatMgr.isContain(id)) {
			chatMgr.reciveMsg(id);

		} else {
			for (OnlineMsgListener listener : listeners) {
				listener.recivedMsg(id, conten);
			}

		}
		// 无论有没有打开，都写入文档
		userMgr.insertString(id, conten, false);

	}

	
	

	public void recivedMsg(int id, String[] contens) {
		for (String str : contens) {
			this.recivedMsg(id, str);
		}
	}

	@Override
	public void notifyIgnoreMsg(int id) {
		
		this.unReadMsgs.remove(id);
		for (OnlineMsgListener listener : listeners) {
			listener.ingoreMsg(id);
		}

	}

	@Override
	public void notifyIgnoreAllMsg() {
		for (OnlineMsgListener listener : listeners) {
			listener.ingoreAllMsg();
		}

	}

	@Override
	public void notifyOpenMsg(int id) {

		if (chatMgr.isContain(id)) {

			chatMgr.setSelect(id);
		} else {
			System.out.println("不包含");
			chatMgr.addChat(id);
		}

		if (this.unReadMsgs.containsKey(id)) {
			this.unReadMsgs.remove(id);
			this.notifyIgnoreMsg(id);

		}

	}

	@Override
	public void notifyUserStateChanged(VState state) {
		for (OnlineMsgListener listener : listeners) {
			listener.userStateChanged(state);
		}

	}

	@Override
	public void add(OnlineMsgListener listener) {
		this.listeners.add(listener);

	}

	@Override
	public boolean isHaveUnreadMsg(int id) {
		
		return this.unReadMsgs.containsKey(id);
	}

	@Override
	public boolean haveUnreadMsg() {
		
		return (!this.unReadMsgs.isEmpty());
	}

}
