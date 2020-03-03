package com.chenkh.vchat.client;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import com.chenkh.vchat.client.enu.AttSetType;
import com.chenkh.vchat.client.frame.chat.VDoc;
import com.chenkh.vchat.base.bean.Friend;
import com.chenkh.vchat.base.bean.Group;
import com.chenkh.vchat.base.bean.Stranger;
import com.chenkh.vchat.base.bean.User;

public class UserMgr {

	private final User user;
	private static UserMgr mgr;

	private Map<Integer, Friend> friends = new HashMap<Integer, Friend>();
	private Map<Integer, VDoc> docs = new HashMap<Integer, VDoc>();
	private Map<AttSetType, SimpleAttributeSet> sets = new HashMap<AttSetType, SimpleAttributeSet>();
	private Map<Integer, Stranger> strangers = new HashMap<Integer, Stranger>();

	private UserMgr(User user) {
		this.user = user;
		for (Group group : user.getGroups()) {
			for (Friend friend : group.getFriends()) {
				int id = friend.getId();
				friends.put(id, friend);
			}
		}

		SimpleAttributeSet userName = new SimpleAttributeSet();
		SimpleAttributeSet friendName = new SimpleAttributeSet();
		SimpleAttributeSet userMsg = new SimpleAttributeSet();
		SimpleAttributeSet friendMsg = new SimpleAttributeSet();

		StyleConstants.setForeground(userName, Color.BLUE);
		StyleConstants.setForeground(friendName, new Color(50, 105, 150));
		StyleConstants.setLeftIndent(friendName, 10);
		StyleConstants.setLeftIndent(userName, 10);
		StyleConstants.setLineSpacing(userName, 1);
		StyleConstants.setLineSpacing(friendName, 1);
		StyleConstants.setLineSpacing(userMsg, 0);
		// StyleConstants.setForeground(userMsg, Color.BLACK);
		StyleConstants.setLeftIndent(userMsg, 20);
		StyleConstants.setFontFamily(userMsg, "宋体");

		StyleConstants.setLineSpacing(friendMsg, 0);
		StyleConstants.setForeground(friendMsg, Color.BLACK);
		StyleConstants.setLeftIndent(friendMsg, 20);
		StyleConstants.setFontFamily(friendMsg, "宋体");

		sets.put(AttSetType.userName, userName);
		sets.put(AttSetType.friendName, friendName);
		sets.put(AttSetType.userMsg, userMsg);
		sets.put(AttSetType.friendMsg, friendMsg);

	}

	public SimpleAttributeSet getAttributeSet(AttSetType type) {
		return sets.get(type);
	}

	public Stranger getStranger(int id) {
		return this.strangers.get(id);
	}

	public void addStranger(Stranger stranger) {
		this.strangers.put(stranger.getId(), stranger);
	}

	public void removeStranger(int id) {
		this.strangers.remove(id);
	}

	public boolean isFriend(int id) {
		return this.friends.containsKey(id);
	}

	public VDoc getDocument(int id) {
		VDoc doc = null;
		if (docs.containsKey(id)) {
			doc = docs.get(id);
		} else {
			String friendName = null;
			String noteName = null;

			String name = null;

			doc = new VDoc(name);

			if (this.isFriend(id)) {

				friendName = this.getFriend(id).getUsernmae();
				noteName = this.getFriend(id).getNoteName();
				name = noteName == null ? friendName : noteName;
				doc = new VDoc(name);
				docs.put(id, doc);
			} else if (this.strangers.containsKey(id)) {
				doc = new VDoc(strangers.get(id).getUsername());
				docs.put(id, doc);
			}

		}
		return doc;

	}

	public void insertString(int friendId, String msg, boolean isSelf) {
		VDoc doc = this.getDocument(friendId);
		if (isSelf) {
			doc.addSelfMsg(msg);

		} else {
			doc.addFriendMsg(msg);
		}
	}

	public void setAttributeSet(AttSetType type, Color foreColor,
			String fontFamily, int fontsize, boolean isBold, boolean isItalic,
			boolean isUnderLine) {

	}

	public User getUser() {
		return user;
	}

	public Friend getFriend(int id) {
		return friends.get(id);
	}

	public void modifyFriend(Friend friend) {
		// friends.

	}

	public String getUserNmae() {
		return user.getUserName();
	}

	public String getSign() {
		return user.getSign();
	}

	public static void initUser(User user) {

		mgr = new UserMgr(user);
	}

	public static UserMgr getInstance() {
		return mgr;
	}

	public void insertString(int id, String[] contens, boolean isSelf) {
		for (String str : contens) {
			this.insertString(id, str, false);
		}

	}

	public void addFriend(Friend friend, int groupId) {
		for (Group group : user.getGroups()) {
			if (group.getGroupId() == groupId) {
				group.addFriend(friend);
				friend.setGroup(group);
				this.friends.put(friend.getId(), friend);
				return;
			}
		}

	}

	public Group getGroupById(int groupId) {

		for (Group group : user.getGroups()) {
			if (group.getGroupId() == groupId) {
				return group;
			}
		}
		return null;
	}

	public int getfriendCount() {

		return friends.size();
	}

	public void deleteFriend(int friendId) {
		this.friends.remove(friendId);
		this.docs.remove(friendId);

	}

	public boolean isStranger(int id) {

		return this.strangers.containsKey(id);
	}

	public String getName(int id) {
		String name = null;
		if (this.friends.containsKey(id)) {
			Friend friend = this.friends.get(id);
			name = friend.getNoteName() == null ? friend.getUsernmae() : friend
					.getNoteName();

		} else if (this.strangers.containsKey(id)) {
			Stranger stranger = this.strangers.get(id);
			name = stranger.getUsername();
		}
		return name;
	}
	
	public String getSign(int id) {
		String name = null;
		if (this.friends.containsKey(id)) {
			Friend friend = this.friends.get(id);
			name = friend.getSign()==null?"":friend.getSign();

		} else if (this.strangers.containsKey(id)) {
			Stranger stranger = this.strangers.get(id);
			name = stranger.getSign()==null?"":stranger.getSign();
		}
		return name;
	}
	
	

}
