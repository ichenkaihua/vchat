package com.chenkh.vchat.client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.Timer;

import com.chenkh.vchat.client.enu.LoginState;
import com.chenkh.vchat.client.frame.ImageConstance;
import com.chenkh.vchat.client.frame.msgMgr.MsgMgrO;
import com.chenkh.vchat.client.frame.msgMgr.OnlineMsgListener;
import com.chenkh.vchat.client.frame.msgMgr.OnlineMsgMgr;
import com.chenkh.vchat.client.listener.TrayListener;
import com.chenkh.vchat.client.listener.TrayObserverable;
import com.chenkh.vchat.client.view.dialog.MsgBox;
import com.chenkh.vchat.base.bean.VState;

/**
 * 系统托盘类
 * 
 * @author Administrator
 * 
 */
public class VTray implements  ActionListener,
		OnlineMsgListener, MouseMotionListener,ITray {
	private TrayIcon trayIcon;
	private List<TrayListener> listeners = new CopyOnWriteArrayList<>();
	private boolean logining = false;

	private boolean allowRemenber = true;
	private Point point = new Point();
	private Timer boxShowTimer = null;
	private Timer loginTimer = null;
	private Timer shumeTimer = null;
	private OnlineMsgMgr msgMgr;
	private boolean isFirst = true;
	private int firstX;

	public OnlineMsgMgr getMsgMgr() {
		return msgMgr;
	}

	private boolean hasAddMouseListener = false;
	private VState state = VState.imonline;

	private MsgBox box;

	public VTray() {

		SystemTray tray = SystemTray.getSystemTray();
		trayIcon = new TrayIcon(
				ImageConstance.tray_vchat_state.get(VState.offline), "vchat") {

			@Override
			public synchronized void addMouseMotionListener(
					MouseMotionListener listener) {
				VTray.this.allowRemenber = true;
				super.addMouseMotionListener(listener);
				VTray.this.hasAddMouseListener = true;
				VTray.this.shumeTimer.start();

			}

			@Override
			public synchronized void removeMouseMotionListener(
					MouseMotionListener listener) {
				super.removeMouseMotionListener(listener);
				VTray.this.hasAddMouseListener = false;
				if (VTray.this.boxShowTimer.isRunning()) {
					VTray.this.boxShowTimer.stop();

				}

				VTray.this.shumeTimer.stop();
				VTray.this.userStateChanged(state);

			}

		};
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.out.println("配置系统托盘时出错");
			e.printStackTrace();
		}
		initTimer();

		trayIcon.addActionListener(this);

	}

	public void setOnlineMsgMgr(OnlineMsgMgr msgMgr) {
		this.msgMgr = msgMgr;
		msgMgr.add(this);
		if (box == null) {
			box = new MsgBox(this);
		}

	}

	private void initTimer() {
		boxShowTimer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Point p = MouseInfo.getPointerInfo().getLocation();
				// 两个点相等,或者鼠标在鼠标内，则继续显示
				if ((p.x == point.x && p.y == point.y)
						|| box.getMousePosition(true) != null) {
					// 如果在显示
					box.toFront();
					if (!box.isVisible()) {
						box.setVisible(true);
					}

					// 如果不满足上面两个条件，则让box不显示
				} else {
					box.setVisible(false);
					allowRemenber = true;
					boxShowTimer.stop();
				}

			}

		});
		loginTimer = new Timer(500, new ActionListener() {
			LoginState[] loginStates = LoginState.values();
			int pos = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				trayIcon.setImage(ImageConstance.tray_login_state
						.get(loginStates[pos]));
				if (pos == loginStates.length) {
					pos = 0;
				}

			}

		});

		shumeTimer = new Timer(500, new ActionListener() {
			boolean flag = true;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (flag) {
					trayIcon.setImage(ImageConstance.blank);
					flag = false;
				} else {
					trayIcon.setImage(ImageConstance.head_mini);
					flag = true;
				}

			}

		});

	}

	@Override
	public void setPopupMenu(PopupMenu popup) {
		MenuItem menuItem=null;

		trayIcon.setPopupMenu(popup);
	}

	public void setTooltip(String tooltip) {
		trayIcon.setToolTip(tooltip);
	}

	@Override
	public void addObserver(TrayListener listener) {

		listeners.add(listener);

	}

	@Override
	public void removeObserver(TrayListener listener) {
		listeners.remove(listener);
	}

	public void startLogin() {
		if (!loginTimer.isRunning())
			loginTimer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for (TrayListener listener : listeners) {

			listener.trayClick();
		}

	}

	public void loginSucced(VState state) {
		if (loginTimer.isRunning()) {
			loginTimer.stop();
		}
		this.userStateChanged(state);

	}

	public void loginLose() {
		if (loginTimer.isRunning()) {
			loginTimer.stop();
		}

		this.userStateChanged(state);
	}

	@Override
	public void setOnlineMsgMgr(MsgMgrO msgMgr) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		point = e.getPoint();
		if (this.isFirst) {
			this.firstX = point.x;
			if (box != null) {

				box.setLocation(firstX - box.getSize().width / 2,
						box.getLocation().y);
				isFirst = false;
			}

		}

		// 判断有没有允许
		if (VTray.this.allowRemenber) {
			VTray.this.allowRemenber = false;

			// 判断任务开启没有，如果没有，则开启，否则不做处理
			if (!boxShowTimer.isRunning()) {
				box.setVisible(true);
				boxShowTimer.start();
			}

		}

	}

	@Override
	public void ingoreMsg(int id) {
		box.ingoreMsg(id);
		if (box.isEmpty()) {
			if (this.hasAddMouseListener) {
				trayIcon.removeMouseMotionListener(this);
				box.setVisible(false);
			}
		}

	}

	@Override
	public void ingoreAllMsg() {
		box.ingoreAllMsg();
		if (this.hasAddMouseListener) {
			trayIcon.removeMouseMotionListener(this);
			box.setVisible(false);
		}

	}

	@Override
	public void recivedMsg(int id, String content) {
		box.recivedMsg(id, content);
		if (!this.hasAddMouseListener) {
			trayIcon.addMouseMotionListener(this);
		}

	}

	@Override
	public void userStateChanged(VState state) {
		trayIcon.setImage(ImageConstance.tray_vchat_state.get(state));

	}

}
