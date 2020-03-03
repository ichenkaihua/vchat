package com.chenkh.vchat.client.view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.chenkh.vchat.client.UserMgr;
import com.chenkh.vchat.client.VTray;
import com.chenkh.vchat.client.frame.ImageConstance;
import com.chenkh.vchat.client.frame.VFactory;
import com.chenkh.vchat.client.frame.msgMgr.OnlineMsgListener;
import com.chenkh.vchat.client.frame.msgMgr.OnlineMsgMgr;
import com.chenkh.vchat.client.tool.Hard;
import com.chenkh.vchat.client.view.pane.ImagePanel;
import com.chenkh.vchat.base.bean.Friend;
import com.chenkh.vchat.base.bean.Stranger;
import com.chenkh.vchat.base.bean.VState;

/**
 * 消息提示类,负责显示系统托盘的未读消息
 * 
 * @author Administrator
 * 
 */
public class MsgBox extends VDialog implements ActionListener,
		OnlineMsgListener {
	private VFactory factory = VFactory.getInstance();
	private JButton userName;
	private JButton msgSetting;
	private JButton ignoreAll;
	private JButton openAll;
	private Box box = new Box(BoxLayout.Y_AXIS);
	private Map<Integer, ListPane> msgs = new HashMap<Integer, ListPane>();
	private VTray tray;
	private UserMgr userMgr = UserMgr.getInstance();
	private OnlineMsgMgr msgMgr;

	public MsgBox(VTray tray) {
		super(false);
		this.tray = tray;
		this.setUndecorated(true);
		box = new Box(BoxLayout.Y_AXIS);
		// 设置顶部导航区域
		JPanel top = new ImagePanel(new BorderLayout());
		userName = new JButton("xiaosachen");
		userName.setCursor(new Cursor(Cursor.HAND_CURSOR));
		userName.setContentAreaFilled(false);
		userName.setBorder(null);
		userName.setFocusPainted(false);
		userName.setOpaque(false);
		userName.addActionListener(tray);
		msgMgr = tray.getMsgMgr();
		userName.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				userName.setText("<html><font color='white'><u>xiaosachen</font></u>");

			}

			@Override
			public void mouseExited(MouseEvent e) {
				userName.setText("xiaosachen");
			}

		});
		msgSetting = factory.getButton(ImageConstance.msgBoxSet_normal,
				ImageConstance.msgBoxSet_hover, ImageConstance.msgBoxSet_down);
		top.add(userName, BorderLayout.WEST);
		top.add(msgSetting, BorderLayout.EAST);
		top.setBorder(BorderFactory.createEmptyBorder(2, 5, 5, 5));
		top.setPreferredSize(new Dimension(230, 30));
		this.setTopPanel(top);

		// 设置消息区

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(box, BorderLayout.CENTER);
		this.setCenterPanel(panel);

		// 设置底部区
		JPanel down = new JPanel(new BorderLayout());
		down.setBackground(new Color(245, 245, 249));
		ignoreAll = factory.getButton("忽略全部", Color.BLUE);
		openAll = factory.getButton("查看全部", Color.BLUE);
		ignoreAll.addActionListener(this);
		openAll.addActionListener(this);
		down.add(ignoreAll, BorderLayout.WEST);
		down.add(openAll, BorderLayout.EAST);
		down.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 5));
		this.setDownPanel(down);

		showFrame();
	}

	@Override
	public void ingoreMsg(int id) {

		if (msgs.containsKey(id)) {
			ListPane pane = msgs.get(id);
			box.remove(pane);
			msgs.remove(id);
			this.showFrame();
		}
	}

	@Override
	public void ingoreAllMsg() {
		box.removeAll();
		msgs.clear();
		this.showFrame();
	}

	@Override
	public void recivedMsg(int id, String content) {

		if (msgs.containsKey(id)) {
			ListPane pane = msgs.get(id);
			pane.addMsg(content);
		} else {
			ListPane pane = new ListPane(id, content);
			msgs.put(id, pane);
			box.add(pane);
			showFrame();
		}

	}

	@Override
	public void userStateChanged(VState state) {
		// TODO Auto-generated method stub

	}

	public boolean isEmpty() {
		return msgs.isEmpty();
	}

	public void showFrame() {
		this.pack();
		this.setLocation(Hard.getScreenWidht() - this.getWidth() - 80,
				Hard.getScreenBotton() - this.getHeight());

	}

	public int getMsgSize() {
		return this.msgs.size();
	}

	private class ListPane extends JPanel implements ActionListener {
		private int id;
		private String conten;
		private JButton msgCount = new JButton();
		private JLabel newConten = new JLabel();
		private JLabel friendHead = new JLabel(
				factory.getIcon(ImageConstance.head));
		private JLabel friendName = new JLabel();
		private int count;

		public ListPane(int id, String conten) {
			this.id = id;
			newConten.setText(conten);
			count = 1;
			this.initMsgButoon();
			String name = null;
			if(userMgr.isFriend(id)){
				Friend friend = userMgr.getFriend(id);
				name = friend.getNoteName() == null ? friend
						.getUsernmae() : friend.getNoteName();
			}
			
			else if(userMgr.isStranger(id)){
				Stranger stranger = userMgr.getStranger(id);
				name = stranger.getUsername();
			}
			
			
			

			friendName.setText(name);

			this.setLayout(new BorderLayout());
			this.add(friendHead, BorderLayout.WEST);
			JPanel p_center = factory.getBoxLayoutPanelByY();
			p_center.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
			p_center.setOpaque(false);
			p_center.add(friendName);
			p_center.add(newConten);

			this.add(msgCount, BorderLayout.EAST);
			this.add(p_center);

			this.setPreferredSize(new Dimension(MsgBox.this.getWidth(), 50));
			this.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
			this.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					msgMgr.notifyOpenMsg(ListPane.this.id);

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					ListPane.this.setBackground(new Color(210, 230, 240));
					msgCount.setText("忽略");

				}

				@Override
				public void mouseExited(MouseEvent e) {
					ListPane.this.setBackground(Color.WHITE);
					msgCount.setText(count + "");
				}

			});

		}

		private void initMsgButoon() {
			msgCount.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			msgCount.setContentAreaFilled(false);
			msgCount.addActionListener(this);
			msgCount.setOpaque(false);
			msgCount.setPreferredSize(new Dimension(30, 30));

		}

		public void addMsg(String conten) {
			count++;
			msgCount.setText(count + "");

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			msgMgr.notifyIgnoreMsg(id);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == ignoreAll) {
			msgMgr.notifyIgnoreAllMsg();
		} else if (obj == openAll) {
			this.openAll();
			// TODO Auto-generated method stub
		}

	}

	private void openAll() {

	}

}
