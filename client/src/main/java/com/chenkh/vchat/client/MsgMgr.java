package com.chenkh.vchat.client;


import com.chenkh.vchat.base.msg.*;
import com.chenkh.vchat.base.msg.both.DeleteFriendMsg;
import com.chenkh.vchat.base.msg.both.UserStateChangeMsg;
import com.chenkh.vchat.base.msg.server.AddFriendSucess;
import com.chenkh.vchat.base.msg.server.LoginResultMsg;
import com.chenkh.vchat.base.msg.server.NotifyAddFriendMsg;
import com.chenkh.vchat.base.msg.server.QueryResultMsg;
import com.chenkh.vchat.base.msg.server.RegisterSucessMsg;
import com.chenkh.vchat.base.msg.server.StrangerMsg;
import com.chenkh.vchat.client.access.MsgTaskMgr;
import com.chenkh.vchat.client.listener.MsgListener;

public class MsgMgr implements ClientMsgMgr, Runnable {
	private MsgTaskMgr taskMgr;

	private MsgListener listener;

	public MsgMgr(MsgTaskMgr taskMgr, MsgListener listener) {
		this.taskMgr = taskMgr;
		this.listener = listener;
		new Thread(new ParseThread()).start();
	}

	@Override
	public void parseMsg(ServerMsg msg) {
		msg.parse(this);

	}

	private class ParseThread implements Runnable {

		private boolean start = true;

		@Override
		public void run() {
			while (start) {
				ServerMsg msg = null;
				msg = taskMgr.getServerMsg();
				MsgMgr.this.parseMsg(msg);
			}

		}

	}

	@Override
	public void run() {

		while (true) {
			try {
				Thread.sleep(15000);

			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

	}

	@Override
	public void parseMsg(ChatMsg msg) {

		listener.parseMsg(msg);

	}

	@Override
	public void parseMsg(VerifyMsg msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseMsg(LoginResultMsg msg) {

		listener.parseMsg(msg);

	}

	@Override
	public void parseMsg(OfflineMsg msg) {
		System.out.println("有离线消息!");

	}

	@Override
	public void parseMsg(UserStateChangeMsg msg) {
		listener.parseMsg(msg);

	}

	@Override
	public void parseMsg(RegisterSucessMsg msg) {
		listener.parseMsg(msg);

	}

	@Override
	public void parseMsg(QueryResultMsg msg) {
		listener.parseMsg(msg);

	}

	@Override
	public void parseMsg(AddFriendSucess msg) {
		listener.parseMsg(msg);

	}

	@Override
	public void parseMsg(NotifyAddFriendMsg msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseMsg(DeleteFriendMsg msg) {
		listener.parseMsg(msg);

	}

	@Override
	public void parseMsg(StrangerMsg msg) {
		listener.parseMsg(msg);
		
	}

}
