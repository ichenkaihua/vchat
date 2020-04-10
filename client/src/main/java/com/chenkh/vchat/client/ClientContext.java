package com.chenkh.vchat.client;

import com.chenkh.vchat.base.bean.*;
import com.chenkh.vchat.base.msg.ChatMsg;
import com.chenkh.vchat.base.msg.VerifyMsg;
import com.chenkh.vchat.base.msg.both.DeleteFriendMsg;
import com.chenkh.vchat.base.msg.both.UserStateChangeMsg;
import com.chenkh.vchat.base.msg.server.AddFriendSucess;
import com.chenkh.vchat.base.msg.server.LoginResultMsg;
import com.chenkh.vchat.base.msg.server.StrangerMsg;
import com.chenkh.vchat.client.chat.FriendChatSession;
import com.chenkh.vchat.client.chat.IFriendChatSession;
import com.chenkh.vchat.client.frame.chat.ChatFrame;
import com.chenkh.vchat.client.frame.chat.ChatFrameMgr;
import com.chenkh.vchat.client.frame.msgMgr.MsgMgrO;
import com.chenkh.vchat.client.net.IClient;
import com.chenkh.vchat.client.net.IMsgListener;
import com.chenkh.vchat.client.view.frame.LoginFrame;
import com.chenkh.vchat.client.view.frame.MainFrame;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Slf4j
public class ClientContext implements IContext, IMsgListener {
//	private IPut taskMgr;
	private LoginFrame loginFrame = null;

	private MainFrame mainFrame = null;

	private ITray tray;
	private ChatFrameMgr chatFrameMgr;
	private MsgMgrO msgMgr;

	private IClient netClient;



	private User user;

	private Map<Integer, IFriendChatSession> friendChatSessionMap=new HashMap<>();


	/**
	 * 保存了所有当前对话frame，一个窗口包含了若干个session
	 */
	private List<ChatFrame> chatFrames=new ArrayList<>();


	public ClientContext(IClient netClient,ITray tray) {

		this.netClient=netClient;
		this.tray=tray;
		netClient.addMsgListener(this);
		// 登录框架显示
		loginFrame = new LoginFrame( "vchat", 375, 300,this);
		loginFrame.setVisible(true);
	}




	@Override
	public void onReceivedLoginResultMsg(LoginResultMsg loginResultMsg) {
		if(!loginResultMsg.isSuccess()) return;
		user=loginResultMsg.getUser();
		User u = loginResultMsg.getUser();

		UserMgr.initUser(u);

		chatFrameMgr = new ChatFrameMgr(this);

		msgMgr = new MsgMgrO(chatFrameMgr);
		tray.setOnlineMsgMgr(msgMgr);


		mainFrame = new MainFrame( msgMgr, "vchat", 400, 40, 300, 650,this);
		tray.addObserver(mainFrame);
		tray.setPopupMenu(mainFrame.getPopuMenu());
		mainFrame.userStateChanged(VState.imonline);
		tray.loginSucced(VState.imonline);

		loginFrame.dispose();


		mainFrame.setVisible(true);
	}




	public void parseMsg(ChatMsg msg) {
		int friendId = msg.getFromId();
		msgMgr.recivedMsg(friendId, msg.getContent());

	}

	/**
	 * 当接收到消息的时候，判断是否已经有关联的chatSession
	 * 如果有，则取出放入消息；否则，新建chatsession，并放入消息
	 * @param chatMsgBody
	 */
	@Override
	public void onReceivedChatMsg(ChatMsg chatMsgBody) {
		IFriendChatSession chatSession = createOrGetFriendChatSession(chatMsgBody.getFromId());
		chatSession.addFriendTextMsg(chatMsgBody.getContent(),chatMsgBody.getDate());
	}

	public void parseMsg(VerifyMsg msg) {
		// TODO Auto-generated method stub
	}


	public void putChatMsg(String content, int toId) {
		int id = UserMgr.getInstance().getUser().getId();
		ChatMsg msg = new ChatMsg(id,toId,content,new Timestamp(System.currentTimeMillis()));
		netClient.sendChatMsg(msg);


	}



	//@Override
	public void parseMsg(UserStateChangeMsg msg) {
		int friendId = msg.getFromId();
		Friend friend = UserMgr.getInstance().getFriend(friendId);
		if (friend != null) {
			friend.setState(msg.getState());
			mainFrame.rePaintTree(friend);
		}
	}




	//@Override
	public void parseMsg(AddFriendSucess msg) {

		Friend friend = msg.getFriend();
		int groupId = msg.getGroupId();
		UserMgr.getInstance().addFriend(friend, groupId);
		mainFrame.addFriendSucess(friend, groupId);

	}

	//@Override
	public void parseMsg(DeleteFriendMsg msg) {
		mainFrame.deleteFriend(msg.getFriendId(), msg.getGroupId());
		Group group = UserMgr.getInstance().getGroupById(msg.getGroupId());
		group.deleteFriend(UserMgr.getInstance().getFriend(msg.getFriendId()));
		chatFrameMgr.removeOpenChat(msg.getFriendId());
		UserMgr.getInstance().deleteFriend(msg.getFriendId());

	}

	//@Override
	public void parseMsg(StrangerMsg msg) {
		Stranger stranger = msg.getStranger();
		UserMgr.getInstance().addStranger(stranger);
		msgMgr.recivedMsg(stranger.getId(), msg.getMsg());

	}

	@Override
	public void onReceivedStrangerMsg(Stranger strangerMsgBody) {

	}



	@Override
	public IClient getNetClient() {
		return netClient;
	}

	@Override
	public void launchUi() {
		try {
			String ip = "127.0.0.1";
			int port = 2567;
			netClient.connectServer(ip, port);
			log.debug("连接服务器{}:{}成功",ip,port);
		} catch (IOException e) {
			log.error("连接服务器发生错误:",e);
		}
		SwingUtilities.invokeLater(()->{
			if(loginFrame==null) {
				loginFrame = new LoginFrame("vchat", 375, 300, this);
			}
			loginFrame.setVisible(true);
			tray.setPopupMenu(loginFrame.getPopuMenu());
		});



	}

	@Override
	public User getCurrentLoggedInUser() {
		return user;
	}

	@Override
	public boolean isLoggedIn() {
		return user!=null;
	}

	@Override
	public ITray getTray() {
		return tray;
	}

	@Override
	public IFriendChatSession createOrGetFriendChatSession(int friendId) {
		IFriendChatSession friendChatSession=null;

		if(friendChatSessionMap.containsKey(friendId)){
			friendChatSession = friendChatSessionMap.get(friendId);
		}
		else{

			for (Group group : user.getGroups()) {
				Optional<Friend> friendStream = group.getFriends().stream().filter(item -> item.getId() == friendId).findAny();
				if(friendStream.isPresent()){
					Friend friend = friendStream.get();
					friendChatSession= new FriendChatSession(netClient, friend);
					friendChatSessionMap.put(friendId, friendChatSession);
					break;
				}

			}

		}
		return friendChatSession;
	}


}
