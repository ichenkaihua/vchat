package com.chenkh.vchat.client.view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * 
 * 
 * @author Administrator
 * 
 */
public class VDialog extends JDialog {

	private JPanel mainPane = new JPanel(new BorderLayout());

	private Point point;

	/**
	 * 构造一个有所有者，有模，有鼠标监听的窗口
	 * 
	 * @param dialog
	 */
	public VDialog(JDialog dialog) {
		super(dialog, true);
		init();
		point = new Point();
		this.addMouseListener(new MouseListener());
		this.addMouseMotionListener(new MouseListener());
	}
	
	public VDialog(){
		super();
		init();
	}
	
	

	public VDialog(boolean hasMouseListener) {
		super();
		init();
		if (hasMouseListener) {
			this.addMouseListener(new MouseListener());
			this.addMouseMotionListener(new MouseListener());
		}
	}

	private void init() {
		this.setUndecorated(true);

		this.setContentPane(mainPane);
		mainPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1,true));
		

	}

	public void setTopPanel(JPanel panel) {
		mainPane.add(panel, BorderLayout.NORTH);

	}

	public void setCenterPanel(JPanel panel) {
		mainPane.add(panel, BorderLayout.CENTER);

	}

	public void setDownPanel(JPanel panel) {
		mainPane.add(panel, BorderLayout.SOUTH);
	}

	private class MouseListener extends MouseAdapter {

		// 设置鼠标按下的事件，记录鼠标光标位置
		@Override
		public void mousePressed(MouseEvent e) {
			point = e.getPoint();
			// VDialog.this.requestFocus();
		}

		// 设置鼠标拖动事件，使窗口跟着移动
		@Override
		public void mouseDragged(MouseEvent e) {
			Point p = e.getPoint();
			VDialog.this.setLocation(VDialog.this.getX() + (p.x - point.x),
					VDialog.this.getY() + (p.y - point.y));

		}

	}



}
