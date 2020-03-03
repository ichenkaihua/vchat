package com.chenkh.vchat.base.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class User implements Serializable {
	private int id;
	private String userName;
	private String password;
	private String phone;
	private String sex;
	private String sign;
	private VState state=VState.imonline;
	private int friendCount;
	private String addr;
	private Timestamp rDate;
	private List<Group> groups;
	
	

	public int getFriendCount() {
		return friendCount;
	}

	public void setFriendCount(int friendCount) {
		this.friendCount = friendCount;
	}

	public VState getState() {
		return state;
	}

	public void setState(VState state) {
		this.state = state;
	}

	

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Timestamp getrDate() {
		return rDate;
	}

	public void setrDate(Timestamp rDate) {
		this.rDate = rDate;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public String toString() {
		return userName;
	}
	
	
	
	

}
