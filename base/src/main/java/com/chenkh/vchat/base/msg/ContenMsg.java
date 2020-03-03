package com.chenkh.vchat.base.msg;

import java.io.Serializable;
import java.sql.Timestamp;

public class ContenMsg implements Serializable {

	private String conten;
	private Timestamp rdate;
	private int fromId;

	public int getFromId() {
		return fromId;
	}

	public void setFromId(int fromId) {
		this.fromId = fromId;
	}

	public ContenMsg(int fromId, String conten, Timestamp rdate) {
		super();
		this.fromId = fromId;
		this.conten = conten;
		this.rdate = rdate;
	}

	public String getConten() {
		return conten;
	}

	public void setConten(String conten) {
		this.conten = conten;
	}

	public Timestamp getRdate() {
		return rdate;
	}

	public void setRdate(Timestamp rdate) {
		this.rdate = rdate;
	}

}
