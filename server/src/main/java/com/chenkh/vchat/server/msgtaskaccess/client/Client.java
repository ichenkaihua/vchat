package com.chenkh.vchat.server.msgtaskaccess.client;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Set;

import com.chenkh.vchat.base.bean.VState;

/**
 * 客户端类，为了适应操作，已经重写hashCode()和equals(Object)方法
 * 
 * @author Administrator
 * 
 */
public class Client {
	private final int id;
	private final AsynchronousSocketChannel socket;
	private VState state;
	private Set<Integer> friendIds;

	/**
	 * 此构造方法会初始化id和socket，并且不可变， ip为socket的远程ip地址和端口
	 * 
	 * @param id
	 *            用户id号
	 * @param socket
	 *            用户地址的异步套接字
	 */

	public Client(int id, AsynchronousSocketChannel socket, VState state,
			Set<Integer> friendIds) {
		this.id = id;
		this.friendIds = friendIds;

		this.socket = socket;
		this.state = state;

	}

	public void addFriend(int friendId) {

		this.friendIds.add(friendId);
	}

	public boolean isFriend(int id) {
		return friendIds.contains(id);
	}

	public boolean isOpen() {
		if (socket == null)
			return true;
		return socket.isOpen();
	}

	public void close() {
		if (!socket.isOpen())
			return;
		try {
			socket.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public AsynchronousSocketChannel getSocket() {
		return socket;
	}

	public VState getState() {
		return state;
	}

	public void setState(VState state) {
		this.state = state;
	}

}
