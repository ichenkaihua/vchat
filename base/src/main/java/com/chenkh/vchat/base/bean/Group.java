package com.chenkh.vchat.base.bean;

import java.io.Serializable;
import java.util.List;

public class Group implements Serializable, Comparable<Group> {
	private int groupId;
	private int userId;
	private String name;
	private List<Friend> friends;
	private int friendCount;

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getGroupId() {
		return this.groupId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Friend> getFriends() {
		return friends;
	}

	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}

	public int getFriendCount() {
		return friendCount;
	}

	public void setFriendCount(int friendCount) {
		this.friendCount = friendCount;
	}

	public boolean contains(Friend friend) {
		return friends.contains(friend);
	}

	public String toString() {
		return this.name;
	}

	@Override
	public int compareTo(Group o) {

		int flag = this.name.compareTo(o.getName());

		if (flag == 0) {
			return new Integer(this.friendCount).compareTo(o.getFriendCount());

		}

		return flag;

	}

	public void addFriend(Friend friend) {
		this.friends.add(friend);

	}

	public void deleteFriend(Friend friend) {
		this.friends.remove(friend);
		this.friendCount = friends.size();

	}

}
