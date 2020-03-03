package com.chenkh.vchat.client.frame.chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


import com.chenkh.vchat.client.frame.ImageConstance;
import com.chenkh.vchat.client.frame.VFactory;

public class TabPane extends JPanel implements MouseListener, ActionListener {
	private Tab tab;
	private VFactory factory = VFactory.getInstance();
	private JButton bnClose;
	private JLabel lbeName;
	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private JLabel head = new JLabel(new ImageIcon(ImageConstance.head_mini));

	public TabPane(Tab tab,String name) {
		super();
		this.name = name;
		this.tab = tab;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		// 初始化关闭按钮
		bnClose = factory.getButton(ImageConstance.delbutton_normal,
				ImageConstance.delbutton_hover, ImageConstance.delbutton_down);
		// 初始化好友备注名称
		lbeName = new JLabel(name);
		lbeName.setHorizontalAlignment(SwingConstants.CENTER);
		lbeName.setVerticalAlignment(SwingConstants.TOP);
		this.add(head);
		this.add(Box.createHorizontalStrut(10));

		// 把好友标签添加到pane中
		this.add(lbeName);
		this.add(Box.createHorizontalStrut(10));
		// 将关闭按钮添加到panel中
		this.add(bnClose);
		this.addMouseListener(this);
		bnClose.addActionListener(this);
		//Base
		
		this.setOpaque(false);
		
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int count = e.getClickCount();
		if (count == 2) {
			tab.remove();
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {

		if (e.getButton() == MouseEvent.BUTTON1) {

			int count = e.getClickCount();
			if (count == 1) {
				tab.setSelect();
			}

		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		tab.remove();

	}

}
