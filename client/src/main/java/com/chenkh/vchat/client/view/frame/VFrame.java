package com.chenkh.vchat.client.view.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import com.chenkh.vchat.client.access.FrameTaskMgr;
import com.chenkh.vchat.client.frame.ImageConstance;
import com.chenkh.vchat.client.frame.VFactory;
import com.chenkh.vchat.client.listener.TrayListener;
import com.chenkh.vchat.client.tool.ImageTranser;

/**
 * 此框架为继承JDialog的抽象类，登陆框架，主框架的父类，已经实现鼠标监听，并且带有导航栏，导航栏按钮实现动作监听, 子类只需负责内容面板
 * 
 * @author Administrator
 * 
 */
public abstract class VFrame extends JDialog  implements TrayListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected JPanel mainPane = new JPanel(new BorderLayout());
	protected Point point = new Point(0, 0);
	protected JButton bnMin = null;
	protected JButton bnClose = null;
	protected VFactory factory = null;
	protected Image topBkg = null;
	protected String title = null;

	protected PopupMenu popupMenu;

	public VFrame( String title, int width, int height) {

		this.setUndecorated(true);// 去除系统框架
		this.width = width;
		this.height = height;
		this.setSize(width, height);

		this.setPreferredSize(new Dimension(width, height));// 设置最适大小
		this.setLocationRelativeTo(null);// 使窗体居中
		// 设置框架容器为mainPane
		this.setTitle(title);
		this.setContentPane(mainPane);
		// 获得工厂实例

		// factory = new VFactory();
		factory = VFactory.getInstance();

		// 从工厂得到最小化按钮

		bnClose = factory.getButton(ImageConstance.frame_close_normal,
				ImageConstance.frame_close_highlight,
				ImageConstance.frame_close_down);
		System.out.println(bnClose.getIcon().getIconHeight());
		bnMin = factory.getButton(ImageConstance.frame_min_normal,
				ImageConstance.frame_min_highlight,
				ImageConstance.frame_min_down);
		System.out.println(bnMin.getIcon().getIconHeight());
		bnMin.setToolTipText("最小化窗口");
		// 从工厂得到关闭按钮,并设置提示

		bnClose.setToolTipText("退出");
		// 对窗体添加鼠标键盘监听，对按钮添加动作监听
		this.addMouseListener(new VListener());
		this.addMouseMotionListener(new VListener());
		bnMin.addActionListener(new VListener());
		bnClose.addActionListener(new VListener());
		// 得到顶部窗口背景
		topBkg = ImageTranser.getImage(width, 50);
		// 从工厂得到顶层Panel，
		JPanel topPanel = factory.getTopPanel(title, bnMin, bnClose, topBkg);
		// 把顶层Panel添加到主容器的北部
		topPanel.setBorder(null);
		mainPane.add(topPanel, BorderLayout.NORTH);
		mainPane.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1,
				Color.GRAY));

	}

	public VFrame(String title, int x, int y, int width,
			int height) {
		this(title, width, height);
		this.setLocation(x, y);
	}

	@Override
	public void trayClick() {
		// if(this.ist)
		this.toFront();
		if (!this.isValid()) {

			this.setVisible(true);


		}

	}

	protected   void closeHappen(){};

	public abstract PopupMenu getPopuMenu();

	public class VListener extends MouseInputAdapter implements ActionListener {

		// 设置鼠标按下的事件，记录鼠标光标位置
		@Override
		public void mousePressed(MouseEvent e) {
			point = e.getPoint();
			VFrame.this.requestFocus();
		}

		// 设置鼠标拖动事件，使窗口跟着移动
		@Override
		public void mouseDragged(MouseEvent e) {
			Point p = e.getPoint();
			VFrame.this.setLocation(VFrame.this.getX() + (p.x - point.x),
					VFrame.this.getY() + (p.y - point.y));

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == bnMin) {
				VFrame.this.dispose();

			} else if (e.getSource() == bnClose) {
				closeHappen();
				System.exit(0);
			}

		}

	}


}
