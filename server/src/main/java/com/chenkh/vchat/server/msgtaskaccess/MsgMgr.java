package com.chenkh.vchat.server.msgtaskaccess;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import com.chenkh.vchat.base.msg.*;
import com.chenkh.vchat.base.msg.both.DeleteFriendMsg;
import com.chenkh.vchat.base.msg.both.UserStateChangeMsg;
import com.chenkh.vchat.base.msg.client.AddFriendMsg;
import com.chenkh.vchat.base.msg.client.LoginMsg;
import com.chenkh.vchat.base.msg.client.QueryMsg;
import com.chenkh.vchat.base.msg.client.RegisterMsg;
import com.chenkh.vchat.base.msg.server.AddFriendSucess;
import com.chenkh.vchat.base.msg.server.LoginResultMsg;
import com.chenkh.vchat.base.msg.server.QueryResultMsg;
import com.chenkh.vchat.base.msg.server.RegisterSucessMsg;
import com.chenkh.vchat.base.msg.server.StrangerMsg;
import com.chenkh.vchat.base.msg.server.enu.LoginResult_Type;
import com.chenkh.vchat.server.dao.GroupDAO;
import com.chenkh.vchat.server.dao.MsgDAO;
import com.chenkh.vchat.server.dao.Mysql_GroupDAO;
import com.chenkh.vchat.server.dao.Mysql_MsgDAO;
import com.chenkh.vchat.server.exception.PasswordNotCorrect;
import com.chenkh.vchat.server.exception.RegiserUserFailedException;
import com.chenkh.vchat.server.exception.UserNotFoundException;
import com.chenkh.vchat.server.msgtaskaccess.client.Client;
import com.chenkh.vchat.base.bean.Friend;
import com.chenkh.vchat.base.bean.Group;
import com.chenkh.vchat.base.bean.Stranger;
import com.chenkh.vchat.base.bean.User;
import com.chenkh.vchat.base.bean.VState;

public class MsgMgr implements MsgClientAccess {
	private final UserAccess userMgr;
	private ClientAccess clientMgr;
	private TaskAccessMgr taskMgr;
	private MsgDAO msgDao = new Mysql_MsgDAO();
	private GroupDAO groupDao = new Mysql_GroupDAO();

	public MsgMgr(TaskAccessMgr mgr) {
		userMgr = new UserMgr();
		this.taskMgr = mgr;
		clientMgr = new ClientMgr();
	}

	@Override
	public void parseMsg(ClientMsg msg, AsynchronousSocketChannel socket) {
		msg.parse(this, socket);

	}



	@Override
	public void parseMsg(LoginMsg msg, AsynchronousSocketChannel socket) {
		int id = msg.getId();
		String password = msg.getPassword();

		ServerMsg serverMsg = null;
		List<ContenMsg> offlineMsgs = null;
		ServerMsg offlineMsg = null;

		try {
			User u = null;
			u = userMgr.getUser(id, password);
			serverMsg = new LoginResultMsg(LoginResult_Type.SUCCESS, u);
			Set<Integer> friendIds = new HashSet<Integer>();
			for (Group group : u.getGroups()) {
				for (Friend friend : group.getFriends()) {
					if (clientMgr.isContain(friend.getId())) {
						VState mstate = clientMgr.getState(friend.getId());
						friend.setState(mstate);
					}
					friendIds.add(friend.getId());
				}
			}

			VState state = msg.getState();
			System.out.println("state:" + state);
			clientMgr.addClient(id, socket, state, friendIds);

			this.parseMsg(new UserStateChangeMsg(id, state), socket);

			offlineMsgs = msgDao.getOfflineMsg(u.getId());
			if (offlineMsgs != null && offlineMsgs.size() > 0) {

				offlineMsg = new OfflineMsg(u.getId(), offlineMsgs);
			}

		} catch (UserNotFoundException e) {
			serverMsg = new LoginResultMsg(LoginResult_Type.USER_NOTFOUN, null);

		} catch (PasswordNotCorrect e) {
			serverMsg = new LoginResultMsg(
					LoginResult_Type.PASSWORD_NOTCORRECT, null);

		} finally {
			ByteBuffer bf = this.parseObject(serverMsg);
			taskMgr.addWriteTask(bf, socket);
			if (offlineMsg != null) {
				ByteBuffer bf2 = this.parseObject(offlineMsg);
				taskMgr.addWriteTask(bf2, socket);
			}

		}

	}





	@Override
	public void parseMsg(ChatMsg msg, AsynchronousSocketChannel socket) {

		int fromId = msg.getFromId();
		int toId = msg.getToId();
		// 如果存在，则发送消息，否则，转存为离线消息
		if (clientMgr.isContain(toId)) {
			Client client = clientMgr.getClient(toId);
			AsynchronousSocketChannel so = client.getSocket();
			if (clientMgr.isFriend(toId, fromId)) {

				ByteBuffer bf = this.parseObject(msg);
				if (bf != null) {
					taskMgr.addWriteTask(bf, so);
				}
			} else {
				Stranger stranger = userMgr.getStrangerById(fromId);
				String conten = msg.getContent();
				ServerMsg smsg = new StrangerMsg(stranger, conten, toId);
				ByteBuffer bf = this.parseObject(smsg);
				taskMgr.addWriteTask(bf, so);
			}

		} else {
			msgDao.addOfflineMsg(fromId, toId, msg.getContent(), msg.getDate());
		}

	}

	// 传入对象，转换成bytebuffer
	private ByteBuffer parseObject(Object msg) {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ByteBuffer bf = null;

		try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
			oos.writeObject(msg);
			byte[] bytes = bos.toByteArray();
			bf = ByteBuffer.wrap(bytes);

		} catch (IOException e) {
			System.out.println("转换失败！");
			e.printStackTrace();
		}

		return bf;
	}

	@Override
	public void parseMsg(VerifyMsg msg, AsynchronousSocketChannel socket) {
		// TODO Auto-generated method stub
		System.out.println("test");

	}

	@Override
	public void parseMsg(UserStateChangeMsg msg,
			AsynchronousSocketChannel socket) {
		int id = msg.getFromId();
		if (clientMgr.isContain(id)) {
			Client c = clientMgr.getClient(id);
			c.setState(msg.getState());
		}

		Set<Integer> friends = clientMgr.getFriends(id);
		for (Integer friendid : friends) {
			if (clientMgr.isContain(friendid)) {
				Client c = clientMgr.getClient(friendid);
				ByteBuffer bf = this.parseObject(msg);
				taskMgr.addWriteTask(bf, c.getSocket());
			}
		}

	}

	@Override
	public void reMoveClient(AsynchronousSocketChannel socket) {
		int id = clientMgr.removeCient(socket);
		if (id != -1) {
			this.parseMsg(new UserStateChangeMsg(id, VState.offline), socket);
		}

	}

	@Override
	public void parseMsg(RegisterMsg msg, AsynchronousSocketChannel socket) {
		User u = msg.getUser();
		int id = -1;
		try {
			id = userMgr.addUser(u);
			if (id != -1) {
				ServerMsg resultMsg = new RegisterSucessMsg(id);
				ByteBuffer bf = this.parseObject(resultMsg);
				taskMgr.addWriteTask(bf, socket);
			}
		} catch (RegiserUserFailedException e) {

		}

	}

	@Override
	public void parseMsg(QueryMsg msg, AsynchronousSocketChannel socket) {
		int toId = msg.getQueryId();
		String keyword = msg.getKeyword();
		List<Stranger> strangers = userMgr.queryUser(toId, keyword);
		ServerMsg smsg = new QueryResultMsg(toId, strangers);
		ByteBuffer bf = this.parseObject(smsg);
		taskMgr.addWriteTask(bf, socket);
	}

	@Override
	public void parseMsg(AddFriendMsg msg, AsynchronousSocketChannel socket) {
		int fromId = msg.getMyId();
		int friendId = msg.getFriendId();
		String noteName = msg.getNoteName();
		int groupId = msg.getGroupId();
		Friend friend = userMgr.addFriend(fromId, friendId, groupId, noteName);

		if (friend != null) {
			if (clientMgr.isContain(friendId)) {
				Client friendClient = clientMgr.getClient(friendId);
				friend.setState(friendClient.getState());
			}

			if (clientMgr.isContain(fromId)) {
				clientMgr.addFriend(fromId, friendId);
				ServerMsg suceMsg = new AddFriendSucess(friend, groupId);
				ByteBuffer bf = this.parseObject(suceMsg);
				taskMgr.addWriteTask(bf, socket);
			}

		}

	}

	@Override
	public void parseMsg(DeleteFriendMsg msg, AsynchronousSocketChannel socket) {
		int myId = msg.getMyId();
		int friendId = msg.getFriendId();
		int groupId = msg.getGroupId();
		boolean result = userMgr.deleteFriend(myId, friendId, groupId);

		if (result) {
			ByteBuffer bf = this.parseObject(msg);
			taskMgr.addWriteTask(bf, socket);

		}

	}

}
