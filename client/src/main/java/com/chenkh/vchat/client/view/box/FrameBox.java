package com.chenkh.vchat.client.view.box;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.chenkh.vchat.client.frame.ImageConstance;
import com.chenkh.vchat.client.frame.VFactory;
import com.chenkh.vchat.client.view.pane.VPanel;

public abstract class FrameBox extends JFrame implements ActionListener {

	protected String tilte;
	protected int width;
	protected int height;
	private JPanel mainPane = new JPanel(new BorderLayout());
	private Point point = new Point();
	private MouseListener listener = new MouseListener();
	private VFactory factory = VFactory.getInstance();
	private JButton bnClose = factory.getCloseButton();
	private JButton bnMin = factory.getMinButton();

	public FrameBox(String title, int width, int height) {
		this.tilte = title;
		this.width = width;
		this.height = height;
		this.setTitle(title);
		this.setUndecorated(true);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setContentPane(mainPane);
		JPanel topPane = new VPanel();
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.X_AXIS));
		JLabel lbeTitle = new JLabel(title);
		
		lbeTitle.setIcon(new ImageIcon(ImageConstance.menu_btn_highlight));
		topPane.add(lbeTitle);
		lbeTitle.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lbeTitle.setForeground(Color.WHITE);
		topPane.add(Box.createGlue());
		topPane.add(bnMin);
		topPane.add(bnClose);
		topPane.setPreferredSize(new Dimension(width, 25));
		topPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 0));

		mainPane.add(topPane, BorderLayout.NORTH);
		bnMin.addActionListener(this);
		bnClose.addActionListener(this);
		bnMin.setToolTipText("最小化");
		bnClose.setToolTipText("关闭");

		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		this.setIconImage(ImageConstance.head_stand);
		

	}

	public void setCneterPane(JPanel panel) {
		this.mainPane.add(panel, BorderLayout.CENTER);
	}

	public void setDownPane(JPanel panel) {
		this.mainPane.add(panel, BorderLayout.SOUTH);
	}

	private class MouseListener extends MouseAdapter {

		// 设置鼠标按下的事件，记录鼠标光标位置
		@Override
		public void mousePressed(MouseEvent e) {
			point = e.getPoint();
		}

		// 设置鼠标拖动事件，使窗口跟着移动
		@Override
		public void mouseDragged(MouseEvent e) {
			Point p = e.getPoint();
			FrameBox.this.setLocation(FrameBox.this.getX() + (p.x - point.x),
					FrameBox.this.getY() + (p.y - point.y));

		}

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bnMin) {
			this.setExtendedState(JFrame.ICONIFIED);
		} else if (e.getSource() == bnClose) {
			closeHappen();
			this.dispose();
		}

	}

	public abstract void closeHappen();

}
