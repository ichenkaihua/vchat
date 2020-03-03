package com.chenkh.vchat.client.view.box;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.chenkh.vchat.client.frame.VFactory;
import com.chenkh.vchat.client.view.frame.MainFrame;
import com.chenkh.vchat.base.bean.Friend;
import com.chenkh.vchat.base.bean.Stranger;

public class ShowInfoFrame extends FrameBox {

	private Stranger stranger;
	private VFactory factory = VFactory.getInstance();
	// private JPanel
	private JPanel center = factory.getBoxLayoutPanelByY();
	private JPanel down = factory.getBoxLayoutPanelByX();
	private Font font = new Font("隶书", Font.BOLD, 14);
	private JButton bnNoteName = new JButton("设置备注名");
	private Friend friend;
	private boolean isFriend = false;
	private MainFrame frame;
	private JButton bnAdd = new JButton("添加好友");

	public ShowInfoFrame(MainFrame frame, Stranger stranger) {
		super("用户资料:" + "-" + stranger.getId(), 350, 250);
		this.frame = frame;
		this.stranger = stranger;
		this.initStranger();
	}

	public ShowInfoFrame(MainFrame frame, Friend friend) {
		super("好友资料:" + "-" + friend.getId(), 350, 250);
		this.frame = frame;
		this.friend = friend;
		this.isFriend = true;
		this.initFriend();
	}

	private void initStranger() {
		JPanel tem = new JPanel(new BorderLayout());
		this.bnNoteName.setEnabled(false);
		tem.setOpaque(false);
		this.addPane("ID:", "" + stranger.getId());
		this.addPane("备注名称:", "", bnNoteName);
		this.addPane("用户名:", stranger.getUsername());
		this.addPane("签名:", stranger.getSign());
		this.addPane("性别:", stranger.getSex());
		this.addPane("注册时间", "20140714");
		tem.add(center, BorderLayout.NORTH);
		this.setCneterPane(tem);
		center.setBackground(new Color(250, 250, 250));
		this.initDown();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == bnAdd) {
			JButton bn = (JButton) e.getSource();
			if (bn.isEnabled()) {
				AddFriendBox box = new AddFriendBox(frame, stranger.getId(),
						stranger.getUsername());
				box.setBox(this);
				box.setVisible(true);
			}
		}

	}

	private void initFriend() {
		JPanel tem = new JPanel(new BorderLayout());
		this.bnNoteName.setEnabled(true);
		tem.setOpaque(false);
		this.addPane("ID:", friend.getId() + "");
		this.addPane("备注名称:", friend.getNoteName(), bnNoteName);
		bnNoteName.setVisible(true);
		this.addPane("用户名:", friend.getUsernmae());
		this.addPane("签名:", friend.getSign());
		this.addPane("性别:", friend.getSex());
		this.addPane("注册时间", "20140714");
		tem.add(center, BorderLayout.NORTH);
		this.setCneterPane(tem);
		center.setBackground(new Color(250, 250, 250));
		this.initDown();

	}

	private void initDown() {
		this.setDownPane(down);
		down.add(Box.createGlue());
		down.add(bnAdd);
		down.add(Box.createGlue());
		if (!this.isFriend) {
			bnAdd.addActionListener(this);
		} else {
			bnAdd.setEnabled(false);
		}

	}

	private void addPane(String title, String info) {
		JPanel p = this.getPanel(title, info);
		center.add(p);
	}

	private void addPane(String title, String info, JButton bn) {
		JPanel p = this.getPanel(title, info, bn);

		center.add(p);
	}

	private JPanel getPanel(String msg, String info) {
		JPanel p = new JPanel(new BorderLayout());
		JLabel label = new JLabel(msg);
		label.setPreferredSize(new Dimension(100, 30));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(font);
		JPanel p2 = new JPanel(new BorderLayout());
		JTextField field = new JTextField(info);
		p2.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		p2.add(field, BorderLayout.WEST);
		p.add(label, BorderLayout.WEST);
		field.setOpaque(false);
		field.setBorder(null);
		field.setEditable(false);
		p2.setOpaque(false);
		field.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		field.setFont(font);
		p.add(p2);
		p.setPreferredSize(new Dimension(this.getWidth(), 30));
		p.setOpaque(false);
		return p;
	}

	private JPanel getPanel(String msg, String info, JButton bn) {
		JPanel p = new JPanel(new BorderLayout());
		JLabel label = new JLabel(msg);
		label.setPreferredSize(new Dimension(100, 30));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(font);
		JPanel p2 = new JPanel(new BorderLayout());
		JTextField field = new JTextField(info);
		p2.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		p2.add(field, BorderLayout.WEST);
		JPanel p3 = new JPanel(new BorderLayout());
		p3.setOpaque(false);
		p3.add(bn, BorderLayout.WEST);
		p2.add(p3);
		p.add(label, BorderLayout.WEST);
		field.setOpaque(false);
		field.setAutoscrolls(false);
		field.setBorder(null);
		field.setEditable(false);
		p2.setOpaque(false);
		field.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		field.setFont(font);
		p.add(p2);
		p.setPreferredSize(new Dimension(this.getWidth(), 30));
		p.setOpaque(false);
		return p;
	}

	@Override
	public void closeHappen() {

	}

	public void hasFriend() {
		this.bnAdd.removeActionListener(this);
		this.bnAdd.setEnabled(false);
		this.toBack();
	}

}
