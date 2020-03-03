package com.chenkh.vchat.base.bean;

public enum VState {
	imonline("我在线上"), Qme("Q我吧"), away("离开"), busy("忙碌"), mute("请勿打扰"), invisible(
			"隐身"), offline("离线");
	private final String name;

	public String getName() {
		return name;
	}

	private VState(String name) {
		this.name = name;

	}

}
