package com.chenkh.vchat.server.msgtaskaccess;

import java.nio.channels.AsynchronousSocketChannel;
import java.util.Set;

import com.chenkh.vchat.server.msgtaskaccess.client.Client;
import com.chenkh.vchat.base.bean.VState;


/**
 * 客户端管理类要实现的借口
 * @author Administrator
 *
 */
public interface ClientAccess {
	
	/**
	 * 通过指定的id得到用户
	 * @param id 指定用户id
	 * @return 如果不存在，则返回null
	 */
	public Client getClient(int id);
	
	/**
	 * 增加客户端
	 * @param client 客户端
	 */
	public void addClient(int id,Client client);
	
	/**
	 * 通过id和套接字添加客户端
	 * @param socket 套接字
	 * @param id 用户id
	 */
	public void addClient(int id,AsynchronousSocketChannel socket,VState state,Set<Integer> friendIds);
	
	
	public boolean isContain(int id);

	public VState getState(int id);
	
	
	public boolean isFriend(int mainId,int checkId);
	
	public void addFriend(int mainId,int friendId);
	
	public Set<Integer> getFriends(int id);

	public int removeCient(AsynchronousSocketChannel socket);
	
	
	

}
