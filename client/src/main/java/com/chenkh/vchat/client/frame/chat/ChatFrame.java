package com.chenkh.vchat.client.frame.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.text.SimpleAttributeSet;

import com.chenkh.vchat.client.frame.ImageConstance;
import com.chenkh.vchat.client.frame.VFactory;
import com.chenkh.vchat.client.tool.Hard;
import com.chenkh.vchat.client.view.pane.ImagePanel;
import com.chenkh.vchat.client.view.pane.TabbedPaneUI;

public class ChatFrame extends JFrame implements ActionListener {

	private VFactory factory = VFactory.getInstance();
	private int maxTabCount = 6;
	private Point point = new Point();
	private Timer timer;
	private MouseMonitor ml = new MouseMonitor();

	private JPanel mainPane = new JPanel(new BorderLayout());
	private JPanel top = factory.getBoxLayoutPanelByXImage();
	private JPanel center = new ImagePanel(90, new BorderLayout());
	private JButton bnClose = factory.getButton(
			ImageConstance.frame_close_normal,
			ImageConstance.frame_close_highlight,
			ImageConstance.frame_close_down);
	private JButton bnMin = factory.getButton(ImageConstance.frame_min_normal,
			ImageConstance.frame_min_highlight, ImageConstance.frame_min_down);
	private JButton bnMax = factory.getButton(
			ImageConstance.frame_max_normal,
			ImageConstance.frame_max_highlight,
			ImageConstance.frame_max_down);

	private JButton bnRestore = factory.getButton(
			ImageConstance.frame_restore_normal,
			ImageConstance.frame_restore_highlight,
			ImageConstance.frame_restore_down);

	private JLabel title = new JLabel();
	private MouseInputHandler handler = new MouseInputHandler(this);

	private int width = 608;
	private int height = 522;

	private VTabbedPane tabbedPane;
	private Map<Integer, Tab> tabs = new HashMap<Integer, Tab>();
	private ChatFrameMgr frameMgr;

	public ChatFrame(ChatFrameMgr frameMgr, int maxSize) {
		this.frameMgr = frameMgr;
		this.setUndecorated(true);
		this.maxTabCount = maxSize;



		timer = new Timer(500, new ActionListener() {
			boolean flag;
			Image org = ChatFrame.this.getIconImage();
			Image blank = ImageConstance.blank;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (flag) {
					ChatFrame.this.setIconImage(blank);
					flag = false;
				} else {
					ChatFrame.this.setIconImage(org);
					flag = true;

				}

			}

		});

		/*
		 * this.addWindowListener(new WindowAdapter() { public void
		 * windowDeactivated(WindowEvent e) { ChatFrame.this.setVisible(true); }
		 * });
		 */

		// 设置最大化边界
		this.setMaximizedBounds(new Rectangle(new Dimension(Hard.screen.width,
				Hard.screen.height - Hard.insert.bottom)));

		this.setContentPane(mainPane);

		tabbedPane = new VTabbedPane(this);
		tabbedPane.setUI(new TabbedPaneUI());
		tabbedPane.addMouseListener(ml);
		tabbedPane.addMouseMotionListener(ml);
		this.addMouseListener(handler);
		this.addMouseMotionListener(handler);

		center.add(tabbedPane);

		title.setForeground(Color.WHITE);
		title.setFont(new Font("微软雅黑", Font.BOLD, 14));

		top.add(title);
		top.add(Box.createGlue());
		top.add(bnMin);
		top.add(bnMax);
		top.add(bnRestore);
		top.add(bnClose);
		
		bnRestore.setVisible(false);
		bnRestore.addActionListener(this);
		bnRestore.setToolTipText("还原");
		
		//top.get
		top.setBorder(BorderFactory.createEmptyBorder(0, 10, 2, 0));

		mainPane.add(top, BorderLayout.NORTH);
		mainPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));

		mainPane.add(center);

		bnMax.addActionListener(this);
		bnMax.setToolTipText("最大化");

		bnMin.addActionListener(this);
		bnMin.setToolTipText("最小化");
		bnClose.addActionListener(this);
		bnClose.setToolTipText("关闭");

		this.setSize(width, height);
		this.setMinimumSize(new Dimension(width, height));
		this.setLocationRelativeTo(null);

	}






	public void addChat(int id) {
		Tab tab = new Tab(this, id);
		tabbedPane.addTab(tab);

		tabs.put(id, tab);
		tabbedPane.setSelectTab(tab);

	}

	public void setSelect(int id) {
		Tab tab = tabs.get(id);
		tabbedPane.setSelectTab(tab);

	}

	public void removeChat(int id) {
		Tab tab = tabs.get(id);
		tabbedPane.removeTab(tab);
		tabs.remove(id);
		frameMgr.removeOpenChat(id);

	}

	public boolean isContain(int id) {
		return tabs.containsKey(id);
	}

	private class MouseMonitor extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
				if (ChatFrame.this.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
					ChatFrame.this.setExtendedState(JFrame.NORMAL);

				} else {
					ChatFrame.this.setExtendedState(JFrame.MAXIMIZED_BOTH);

				}
				// bn.setLocation(ChatFrame.this.getWidth() - bn.getWidth(), 0);

			}
		}

		// 设置鼠标按下的事件，记录鼠标光标位置
		@Override
		public void mousePressed(MouseEvent e) {
			point = e.getPoint();

		}

		// 设置鼠标拖动事件，使窗口跟着移动
		@Override
		public void mouseDragged(MouseEvent e) {

			Point p = e.getPoint();
			ChatFrame.this.setLocation(ChatFrame.this.getX() + (p.x - point.x),
					ChatFrame.this.getY() + (p.y - point.y));
		}

	}

	@Override
	public void setExtendedState(int state) {
		super.setExtendedState(state);
		if (state == JFrame.MAXIMIZED_BOTH) {
			//bnMax.setIcon(factory.getIcon(ImageConstance.max_full));
			//bnMax.setToolTipText("还原");
			bnMax.setVisible(false);
			bnRestore.setVisible(true);
			// full = true;
		} else if (state == JFrame.NORMAL) {
			//bnMax.setIcon(factory.getIcon(ImageConstance.max_normal));
			bnMax.setVisible(true);
			bnRestore.setVisible(false);
			
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object obj = e.getSource();

		if (obj == bnMin) {
			int state = this.getExtendedState();
			if (state == 6) {
				this.setExtendedState(7);
			} else {
				this.setExtendedState(JFrame.ICONIFIED);
			}

		} else if (obj == bnMax) {
			/*if (this.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
				this.setExtendedState(JFrame.NORMAL);

			} else {
				this.setExtendedState(JFrame.MAXIMIZED_BOTH);
			}*/
			
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
			

		}else if(obj == bnRestore){
			this.setExtendedState(JFrame.NORMAL);
		}
		else if (obj == bnClose) {
			int result = JOptionPane.showConfirmDialog(this, "您确定关闭对话吗?");
			if (result == 0) {
				this.removeAllTab();
				this.reSize();
				this.dispose();

			}

		}

	}

	private void removeAllTab() {
		frameMgr.removeOpenChat(tabs.keySet());
		tabbedPane.removeAll();
		tabs.clear();
	}

	private void reSize() {
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
	}

	public void setTitle(String titleAt, boolean bn) {
		// super.setTitle(titleAt);
		this.setTitle(titleAt);
		if (bn) {
			title.setText(titleAt);
		}

	}

	public boolean isFull() {
		return this.tabs.size() >= 6;
	}

	/**
	 * 设置所有Tab的编辑框为指定的属性
	 * 
	 * @param set
	 *            段落属性
	 */
	public void setEditorAttributeSet(SimpleAttributeSet set) {

		Collection<Tab> c = tabs.values();

		java.util.Iterator<Tab> it = c.iterator();

		while (it.hasNext()) {
			Tab tab = it.next();
			tab.setEditorAttributeSet(set);
		}

	}

	public void setMaxSize(int maxSize) {
		this.maxTabCount = maxSize;

	}

	public void sendMsg(int friendId, String msg) {
		frameMgr.sendMsg(msg, friendId);

	}

	public void showFrame() {

		if (this.getExtendedState() == JFrame.ICONIFIED) {
			this.setExtendedState(JFrame.NORMAL);
			System.out.println("设置为普通状态");
		} else if (this.getExtendedState() == 7) {
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}

		if (!this.isVisible()) {
			this.setVisible(true);
		}
		this.toFront();
	}

	public void reciveMsg(int id) {
		Tab tab = tabs.get(id);
		this.tabbedPane.recivedMsg(tab);
		if (this.getExtendedState() == JFrame.ICONIFIED
				|| this.getExtendedState() == 7) {
			this.setVisible(true);
		}

		/*
		 * if(!timer.isRunning()){ timer.start(); }
		 */

	}

}
