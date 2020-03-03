package com.chenkh.vchat.base.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class Stranger implements Serializable {
	private int id;
	private String username;
	private String phone;
	private String sex;
	private String addr;
	private Timestamp rDate;
	private String sign;
	private VState state = VState.offline;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsernmae(String username) {
		this.username = username;
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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public VState getState() {
		return state;
	}

	public void setState(VState state) {
		this.state = state;
	}

	public String toString(){
		return this.username+"id:"+id;
	}
	
	
}
