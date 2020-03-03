package com.chenkh.vchat.server.msgtaskaccess;

import java.util.List;

import com.chenkh.vchat.server.dao.Mysql_UserDAO;
import com.chenkh.vchat.server.dao.UserDAO;
import com.chenkh.vchat.server.exception.PasswordNotCorrect;
import com.chenkh.vchat.server.exception.RegiserUserFailedException;
import com.chenkh.vchat.server.exception.UserNotFoundException;
import com.chenkh.vchat.base.bean.Friend;
import com.chenkh.vchat.base.bean.Stranger;
import com.chenkh.vchat.base.bean.User;

public class UserMgr implements UserAccess {
	private UserDAO dao = null;

	public UserMgr() {
		dao = new Mysql_UserDAO();
	}

	@Override
	public User getUser(int id, String password) throws UserNotFoundException,
			PasswordNotCorrect {
		return dao.getUser(id, password);
	}

	@Override
	public Friend addFriend(int myId, int friendId, int groupId,
			String noteName) {
		return dao.addFriend(myId, friendId, groupId, noteName);
	}

	@Override
	public boolean deleteFriend(int myId, int friendId, int groupId) {
		return dao.deleteFriend(myId, friendId, groupId);
	}

	@Override
	public boolean modifyFriendNoteName(int myId, int friendId,
			String noteName, int groupId) {
		return dao.modifyFriendNoteName(myId, friendId, noteName, groupId);
	}

	@Override
	public boolean modifyUser(User u) {
		return dao.modifyUser(u);
	}

	@Override
	public UserDAO getDao() {
		return dao;
	}

	@Override
	public void setDao(UserDAO dao) {
		this.dao = dao;
	}

	@Override
	public int addUser(User u) throws RegiserUserFailedException {

		return dao.addUser(u);

	}

	@Override
	public List<Stranger> queryUser(int toId, String keyword) {

		return dao.queryUser(toId, keyword);
	}

	@Override
	public Stranger getStrangerById(int id) {
		return dao.getStranger(id);
	}

}
