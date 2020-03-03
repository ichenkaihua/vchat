package com.chenkh.vchat.server.dao;

import java.util.List;

import com.chenkh.vchat.server.exception.PasswordNotCorrect;
import com.chenkh.vchat.server.exception.RegiserUserFailedException;
import com.chenkh.vchat.server.exception.UserNotFoundException;
import com.chenkh.vchat.base.bean.Friend;
import com.chenkh.vchat.base.bean.Stranger;
import com.chenkh.vchat.base.bean.User;

public interface UserDAO {

	/**
	 * 此方法用于得到指定ID
	 * 的用户，如果用户名没找到，跑出UserNotFoundException异常
	 * 如果密码不正确，抛出PasswrodNotCorrect异常
	 * 如果都正确，返回一个User
	 * @param id 要得到用户的ID
	 * @param password 用户密码
	 * @return 如果出现异常，则User 为空，
	 * @throws UserNotFoundException 用户名没找到时抛出
	 * @throws PasswordNotCorrect 密码不正确时抛出
	 */
	public User getUser(int id, String password) throws UserNotFoundException, PasswordNotCorrect;
	/**
	 * 在指定id的用户下添加指定组的指定好友
	 * @param myId 主ID
	 * @param friendId 好友ID
	 * @param groupid 分组id
	 * @return 是否添加成功
	 */

	public Friend addFriend(int myId, int friendId,int groupid,String noteName);
	
	
	
	/**
	 * 删除指定id用户的下的指定好友
	 * @param myId 主ID
	 * @param friendId 好友ID
	 * @return 是否是否删除成功
	 */
	public boolean deleteFriend(int myId, int friendId,int groupid);
	
	
	/**
	 * 修改好友的昵称（备注名称）
	 * @param myId 主Id
	 * @param friendId 好友Id
	 * @param noteName 要修改的昵称
	 * @return 是否修改成功
	 */
	public boolean modifyFriendNoteName(int myId, int friendId, String noteName,int groupId);
	/**
	 * 通过传入的User对象，修改用户资料,只会修改用户名，性别，地址和电话
	 * @param u 已经修改过的User
	 * @return 是否修改成功
	 */
	public boolean modifyUser(User u);
	
	/**
	 * 注册用户，如果失败，抛出注册失败异常
	 * @param u 要注册的用户对象
	 * @return 注册成功后的Id号
	 * @throws RegiserUserFailedException 注册失败后抛出的异常
	 */
	public  int addUser(User u) throws RegiserUserFailedException;
	public List<Stranger> queryUser(int toId, String keyword);
	
	public Stranger getStranger(int id);
	

}
