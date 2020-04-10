package com.chenkh.vchat.base.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.sql.Timestamp;

public class Friend implements Serializable, Comparable<Friend> {
	private int id;
	private String usernmae;
	private String phone;
	private String sex;
	private String addr;
	@Expose
	private Timestamp rDate;
	@Expose
	private Group group;
	private String noteName;
	private String sign;
	private VState state = VState.offline;

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

	public String getNoteName() {
		return noteName;
	}

	public void setNoteName(String noteName) {
		this.noteName = noteName;
	}


	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsernmae() {
		return usernmae;
	}

	public void setUsernmae(String usernmae) {
		this.usernmae = usernmae;
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

	@Override
	public int compareTo(Friend o) {
		// 比较状态
		int flag = 0;
		
		if((state==VState.invisible && o.getState()==VState.offline ) || (state==VState.offline && o.getState()==VState.invisible)){
			flag = 0;
		}
		else {
			flag = state.compareTo(o.getState());
		}
		
		
		// 如果状态相同
		if (flag == 0) {
			// 比较名字
			int nameFlag = this.usernmae.compareTo(o.getUsernmae());
			if (nameFlag == 0) {
				return new Integer(id).compareTo(o.getId());
			} else
				return nameFlag;

		}
		// 状态不相同
		return flag;
	}

}
