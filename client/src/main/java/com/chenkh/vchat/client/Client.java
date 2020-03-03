package com.chenkh.vchat.client;


import com.chenkh.vchat.client.frame.ImageConstance;

import java.io.InputStream;

public class Client {

	private TaskMgr taskMgr;
	private MsgMgr msgMgr;
	private FrameMgr frameMgr;
	private VNet net;

	public Client() {
		taskMgr = new TaskMgr();
		net = new VNet(taskMgr);
		frameMgr = new FrameMgr(taskMgr);
		msgMgr = new MsgMgr(taskMgr, frameMgr);
	}

	public static void main(String[] args) {

		new Client();

	}

}
