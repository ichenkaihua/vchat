package com.chenkh.vchat.client;

import java.sql.Timestamp;


import com.chenkh.vchat.base.msg.ChatMsg;
import com.chenkh.vchat.base.msg.ClientMsg;
import com.chenkh.vchat.base.msg.VerifyMsg;
import com.chenkh.vchat.base.msg.both.DeleteFriendMsg;
import com.chenkh.vchat.base.msg.both.UserStateChangeMsg;
import com.chenkh.vchat.base.msg.client.LoginMsg;
import com.chenkh.vchat.base.msg.server.AddFriendSucess;
import com.chenkh.vchat.base.msg.server.LoginResultMsg;
import com.chenkh.vchat.base.msg.server.QueryResultMsg;
import com.chenkh.vchat.base.msg.server.RegisterSucessMsg;
import com.chenkh.vchat.base.msg.server.StrangerMsg;
import com.chenkh.vchat.base.msg.server.enu.LoginResult_Type;
import com.chenkh.vchat.client.access.FrameTaskMgr;
import com.chenkh.vchat.client.access.IPut;
import com.chenkh.vchat.client.frame.chat.ChatFrameMgr;
import com.chenkh.vchat.client.frame.msgMgr.MsgMgrO;
import com.chenkh.vchat.client.listener.MsgListener;
import com.chenkh.vchat.client.view.frame.LoginFrame;
import com.chenkh.vchat.client.view.frame.MainFrame;
import com.chenkh.vchat.base.bean.Friend;
import com.chenkh.vchat.base.bean.Group;
import com.chenkh.vchat.base.bean.Stranger;
import com.chenkh.vchat.base.bean.User;
import com.chenkh.vchat.base.bean.VState;

public class FrameMgr implements MsgListener, FrameTaskMgr {
	private IPut taskMgr;
	private LoginFrame loginFrame = null;

	private MainFrame mainFrame = null;

	private VTray tray;
	private ChatFrameMgr chatFrameMgr;
	private MsgMgrO msgMgr;

	public FrameMgr(IPut taskMgr) {

		this.taskMgr = taskMgr;

		tray = new VTray();
		// 登录框架显示
		loginFrame = new LoginFrame(this, "QQ2013", 375, 300);
		loginFrame.setVisible(true);
		tray.setPopupMenu(loginFrame.getPopuMenu());
		// 给系统托盘类添加观察者，为loginframe
		tray.addObserver(loginFrame);
	}

	@Override
	public void parseMsg(LoginResultMsg msg) {
		LoginResult_Type type = msg.getResult();
		switch (type) {
		case SUCCESS:
			User u = msg.getUser();
			loginSuccess(u);
			break;
		default:
			loginFrame.loginResult(type);
			tray.loginLose();
			break;
		}

	}

	private void loginSuccess(User u) {
		// 初始化
		UserMgr.initUser(u);

		chatFrameMgr = new ChatFrameMgr(this);

		msgMgr = new MsgMgrO(chatFrameMgr);
		tray.setOnlineMsgMgr(msgMgr);

		tray.removeObserver(loginFrame);
		mainFrame = new MainFrame(this, msgMgr, "QQ2013", 400, 40, 300, 650);
		tray.addObserver(mainFrame);
		tray.setPopupMenu(mainFrame.getPopuMenu());
		mainFrame.userStateChanged(VState.imonline);
		tray.loginSucced(VState.imonline);

		loginFrame.dispose();
		mainFrame.setVisible(true);

	}

	@Override
	public void parseMsg(ChatMsg msg) {
		int friendId = msg.getFromId();
		msgMgr.recivedMsg(friendId, msg.getContent());

	}

	@Override
	public void parseMsg(VerifyMsg msg) {
		// TODO Auto-generated method stub
	}

	@Override
	public void putChatMsg(String content, int toId) {
		int id = UserMgr.getInstance().getUser().getId();
		ClientMsg msg = new ChatMsg(id, toId, content, new Timestamp(
				System.currentTimeMillis()));
		taskMgr.putMsg(msg);

	}

	@Override
	public void putMsg(ClientMsg msg) {
		if (msg instanceof LoginMsg) {
			tray.startLogin();
		}
		taskMgr.putMsg(msg);

	}

	@Override
	public void parseMsg(UserStateChangeMsg msg) {
		int friendId = msg.getFromId();
		Friend friend = UserMgr.getInstance().getFriend(friendId);
		if (friend != null) {
			friend.setState(msg.getState());
			mainFrame.rePaintTree(friend);
		}
	}

	@Override
	public void parseMsg(RegisterSucessMsg msg) {
		int id = msg.getId();
		loginFrame.registerSucced(id);

	}

	@Override
	public void parseMsg(QueryResultMsg msg) {
		mainFrame.queryResult(msg);

	}

	@Override
	public void parseMsg(AddFriendSucess msg) {

		Friend friend = msg.getFriend();
		int groupId = msg.getGroupId();
		UserMgr.getInstance().addFriend(friend, groupId);
		mainFrame.addFriendSucess(friend, groupId);

	}

	@Override
	public void parseMsg(DeleteFriendMsg msg) {
		mainFrame.deleteFriend(msg.getFriendId(), msg.getGroupId());
		Group group = UserMgr.getInstance().getGroupById(msg.getGroupId());
		group.deleteFriend(UserMgr.getInstance().getFriend(msg.getFriendId()));
		chatFrameMgr.removeOpenChat(msg.getFriendId());
		UserMgr.getInstance().deleteFriend(msg.getFriendId());

	}

	@Override
	public void parseMsg(StrangerMsg msg) {
		Stranger stranger = msg.getStranger();
		UserMgr.getInstance().addStranger(stranger);
		msgMgr.recivedMsg(stranger.getId(), msg.getMsg());

	}

}
