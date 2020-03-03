package com.chenkh.vchat.server.msgtaskaccess;

import java.nio.channels.AsynchronousSocketChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.chenkh.vchat.server.msgtaskaccess.client.Client;
import com.chenkh.vchat.base.bean.VState;

/**
 * 客户端管理类，负责对客户端的增加，删除，管理
 * 
 * @author Administrator
 * 
 */
public class ClientMgr implements ClientAccess {

	private Map<Integer, Client> clients = new HashMap<Integer, Client>();

	@Override
	public Client getClient(int id) {

		return clients.get(id);

	}

	@Override
	public void addClient(int id, Client client) {
		clients.put(id, client);

	}

	public boolean isContain(int id) {
		return clients.containsKey(id);
	}

	@Override
	public void addClient(int id, AsynchronousSocketChannel socket,
			VState state, Set<Integer> friendIds) {

		Client client = new Client(id, socket, state, friendIds);
		this.addClient(id, client);

	}

	@Override
	public VState getState(int id) {
		VState state = null;

		Client c = clients.get(id);
		state = c.getState();

		return state;
	}

	@Override
	public boolean isFriend(int mainId, int checkId) {
		if (this.isContain(mainId)) {
			Client c = clients.get(mainId);
			return c.isFriend(mainId);

		}

		return false;

	}

	public Set<Integer> getFriends(int id) {
		Set<Integer> friends = new HashSet<Integer>();
		for (Integer iid : clients.keySet()) {
			Client c = clients.get(iid);
			if (c.isFriend(id)) {

				friends.add(iid);
			}

		}
		return friends;
	}

	@Override
	public int removeCient(AsynchronousSocketChannel socket) {
		for (Integer id : clients.keySet()) {
			Client c = clients.get(id);
			if (c.getSocket().equals(socket)) {
				clients.remove(id);
				System.out.println("移除完成");

				return id;
			}
		}
		return -1;

	}

	@Override
	public void addFriend(int mainId, int friendId) {
		Client c = this.clients.get(mainId);
		c.addFriend(friendId);

	}

}
