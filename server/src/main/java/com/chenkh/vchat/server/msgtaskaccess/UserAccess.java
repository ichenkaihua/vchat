package com.chenkh.vchat.server.msgtaskaccess;

import java.util.List;

import com.chenkh.vchat.server.dao.UserDAO;
import com.chenkh.vchat.server.exception.PasswordNotCorrect;
import com.chenkh.vchat.server.exception.RegiserUserFailedException;
import com.chenkh.vchat.server.exception.UserNotFoundException;
import com.chenkh.vchat.base.bean.Friend;
import com.chenkh.vchat.base.bean.Stranger;
import com.chenkh.vchat.base.bean.User;

public interface UserAccess {

	/**
	 * 得到用户信息，包括组和朋友,如果用户不存在或者密码不正确，将返回null
	 * 
	 * @param id
	 *            用户id
	 * @param password
	 *            用户密码
	 * @return
	 * @throws PasswordNotCorrect 当密码不正确时抛出
	 * @throws UserNotFoundException  当用户名不存在时抛出
	 */
	public User getUser(int id,String password) throws UserNotFoundException, PasswordNotCorrect;
	/**
	 * 将指定的朋友添加到指定的用户中
	 * 
	 * @param myId
	 *            主ID，即
	 * @param friendId
	 *            好友id
	 * @return 如果添加成功，返回Friend，否则返回null
	 */
	public Friend addFriend(int myId,int friendId,int groupId,String noteName);

	/**
	 * 从指定的朋友中删除指定的好友
	 * 
	 * @param myId
	 *            主ID，即
	 * @param friendId
	 *            好友id
	 * @return 如果删除成功，返回true，否则返回false
	 */

	public boolean deleteFriend(int myId,int friendId,int groupId);
	/**
	 * 指定用户修改指定朋友的备注名称
	 * 
	 * @param myId
	 *            主Id
	 * @param friendId
	 *            朋友ID
	 * @param noteName
	 *            要修改的名字
	 * @return 修改成功则返回true,否则返回false
	 */
	public boolean modifyFriendNoteName(int myId, int friendId, String noteName,int groupId);

	/**
	 * 用户修改资料
	 * 
	 * @param u
	 *            用户资料
	 * @return 修改成功，返回true，否则返回false
	 */
	public boolean modifyUser(User u);

	public UserDAO getDao();

	public void setDao(UserDAO dao);
	
	
	public int addUser (User u)  throws RegiserUserFailedException;
	
	
	
	public List<Stranger> queryUser(int toId, String keyword);
	
	public Stranger getStrangerById(int id);
	
	
	
	
	
	
	
	

}
