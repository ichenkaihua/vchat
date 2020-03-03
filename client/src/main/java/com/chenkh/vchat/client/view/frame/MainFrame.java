package com.chenkh.vchat.client.view.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;


import com.chenkh.vchat.base.msg.ClientMsg;
import com.chenkh.vchat.base.msg.both.DeleteFriendMsg;
import com.chenkh.vchat.base.msg.both.UserStateChangeMsg;
import com.chenkh.vchat.base.msg.client.QueryMsg;
import com.chenkh.vchat.base.msg.server.QueryResultMsg;
import com.chenkh.vchat.client.UserMgr;
import com.chenkh.vchat.client.access.FrameTaskMgr;
import com.chenkh.vchat.client.frame.ImageConstance;
import com.chenkh.vchat.client.frame.msgMgr.OnlineMsgListener;
import com.chenkh.vchat.client.frame.msgMgr.OnlineMsgMgr;
import com.chenkh.vchat.client.view.box.SearchBox;
import com.chenkh.vchat.client.view.box.ShowInfoFrame;
import com.chenkh.vchat.client.view.pane.ImagePanel;
import com.chenkh.vchat.base.bean.Friend;
import com.chenkh.vchat.base.bean.Group;
import com.chenkh.vchat.base.bean.Stranger;
import com.chenkh.vchat.base.bean.VState;

public class MainFrame extends VFrame implements OnlineMsgListener,
		ActionListener {
	// 用户

	// 定义用户信息panel，负责展现头像，签名等等
	private JPanel userPane = new ImagePanel(new BorderLayout());
	// 定义朋友列表块
	private JPanel friendPane = new JPanel(new BorderLayout());
	// 定义底部导航信息
	private JPanel navigationPane = new JPanel(new BorderLayout());
	private JLabel lbeName = new JLabel();
	private JButton bnHead = factory.getButton(ImageConstance.userImage);
	private JTextField sign = new JTextField();
	private JButton state = new JButton();
	private JTextField search = new JTextField();
	private JButton bnSearch = factory
			.getButton(ImageConstance.icon_search_normal);
	private JButton bnMsgs = new JButton();
	private JTree userTree = null;
	private DefaultTreeModel model;
	private JScrollPane userList = new JScrollPane();
	private Timer shumeTimer;
	private SearchBox searchBox = null;

	private JPopupMenu groupMenu = new JPopupMenu();
	private JPopupMenu friendMenu = new JPopupMenu();
	private JPopupMenu blankMenu = new JPopupMenu();
	private JPopupMenu mainMenu = new JPopupMenu();
	private JButton btnMainMenu = factory
			.getButton(ImageConstance.menu_btn_normal);
	private JButton btnTool = factory.getButton(ImageConstance.tools);
	private JButton btnSearch = factory.getButton(ImageConstance.search_normal);

	private int mouseRow = -1;
	private boolean hasMsg = true;
	private OnlineMsgMgr msgMgr;
	private UserMgr userMgr = UserMgr.getInstance();
	private VNode root = null;
	private Map<Integer, ShowInfoFrame> showInfoFrames = new HashMap<Integer, ShowInfoFrame>();

	public MainFrame(FrameTaskMgr taskMgr, OnlineMsgMgr msgMgr, String title,
			int x, int y, int width, int height) {

		super(taskMgr, title, x, y, width, height);
		this.msgMgr = msgMgr;

		msgMgr.add(this);
		// 初始化系统托盘弹出菜单
		lbeName.setText(userMgr.getUserNmae());
		sign.setText(userMgr.getSign());
		sign.setOpaque(false);

		lbeName.setFont(new Font("JDialog", Font.BOLD, 14));
		sign.setBorder(null);
		sign.setBorder(null);
		sign.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		sign.setColumns(12);
		state.setIcon(new ImageIcon(ImageConstance.states.get(VState.imonline)));
		state.setOpaque(false);
		state.setContentAreaFilled(false);
		state.setText("在线");

		state.setPreferredSize(new Dimension(100, 30));
		state.setHorizontalAlignment(SwingConstants.LEFT);
		state.setBorder(null);
		state.setFocusPainted(false);

		bnMsgs.setBorder(null);
		bnMsgs.setContentAreaFilled(false);
		bnMsgs.setOpaque(false);
		bnMsgs.setIcon(new ImageIcon(ImageConstance.msgbox));
		bnMsgs.setPressedIcon(new ImageIcon(ImageConstance.msgbox_l));
		bnMsgs.setText("2");
		bnMsgs.setFocusPainted(false);

		initPopuMenu();
		initUserPane();
		initFriendPane();
		initNavigationPane();
		addPanes();
		final JPopupMenu menu = new JPopupMenu();

		VState[] states = VState.values();
		for (int i = 0; i < states.length; i++) {
			JMenuItem item = factory.getPopupMenuItem(states[i]);
			item.setActionCommand(states[i].toString());
			menu.add(item);
			item.addActionListener(this);
		}
		menu.setBackground(Color.WHITE);

		state.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				menu.show(state, 0, state.getHeight());

			}

		});

	}

	public int getMouseRow() {
		return this.mouseRow;
	}

	// 初始化用户信息面板
	private void initUserPane() {
		JPanel center = new ImagePanel(new BorderLayout());
		center.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 5));
		JPanel head = new ImagePanel(new BorderLayout());
		head.add(bnHead);

		JPanel right = new ImagePanel(new BorderLayout());// factory.getBoxLayoutPanelByYImage();
		right.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		right.add(lbeName, BorderLayout.NORTH);
		JPanel psign = new ImagePanel(new BorderLayout());
		psign.add(sign, BorderLayout.WEST);
		right.add(psign);

		ImagePanel pth = new ImagePanel(new BorderLayout());
		pth.add(state, BorderLayout.WEST);
		pth.add(bnMsgs, BorderLayout.EAST);
		right.add(pth, BorderLayout.SOUTH);

		center.add(head, BorderLayout.WEST);
		center.add(right, BorderLayout.CENTER);

		JPanel down = new ImagePanel(new BorderLayout());
		down.add(search, BorderLayout.CENTER);
		down.add(bnSearch, BorderLayout.EAST);
		search.setBorder(null);

		// search.setSize(150,100);
		search.setSelectionColor(Color.BLUE);
		search.setSelectedTextColor(Color.WHITE);
		search.setFont(new Font("JDialog", Font.BOLD, 15));

		down.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		down.setPreferredSize(new Dimension(width, 30));

		userPane.add(center, BorderLayout.CENTER);
		userPane.add(down, BorderLayout.SOUTH);

	}

	// 初始化用户好友列表，会话界面
	private void initFriendPane() {
		initJTree();
		userTree.setCellRenderer(new TreeCell(this));
		JTabbedPane tablePane = new JTabbedPane();
		userList.setViewportView(userTree);
		userList.setBorder(null);
		tablePane.setBorder(null);
		userTree.setBorder(null);

		tablePane.addTab("好友列别", userList);
		tablePane.addTab("群/讨论组", new JPanel());
		tablePane.addTab("会话", new JPanel());
		friendPane.add(tablePane);

	}

	public void addShowInfoFrame(Stranger stranger) {
		int id = stranger.getId();
		ShowInfoFrame frame = null;
		if (userMgr.isFriend(id)) {
			Friend f = userMgr.getFriend(id);
			frame = new ShowInfoFrame(this, f);

			this.showInfoFrames.put(id, frame);

		} else {
			frame = new ShowInfoFrame(this, stranger);

			this.showInfoFrames.put(stranger.getId(), frame);
		}
		if (frame != null)
			frame.setVisible(true);
	}

	public boolean isContainShow(int id) {
		return this.showInfoFrames.containsKey(id);
	}

	public void setShowInfo(int id) {
		JFrame f = this.showInfoFrames.get(id);
		if (f != null) {
			f.setVisible(true);
		}

	}

	private void initTreeModel() {

		root = new VNode(userMgr.getUser());

		for (Group group : userMgr.getUser().getGroups()) {
			VNode groupNode = new VNode(group);
			for (Friend friend : group.getFriends()) {
				VNode friendNode = new VNode(friend);
				groupNode.add(friendNode);
			}
			root.add(groupNode);
		}
		model = new DefaultTreeModel(root);
		if (userMgr.getfriendCount() > 0)
			root.reSort();

	}

	private void initJTree() {
		initTreeModel();
		userTree = new JTree(model);

		userTree.setToggleClickCount(1);
		userTree.setRootVisible(false);

		userTree.setShowsRootHandles(false);
		userTree.putClientProperty("JTree.lineStyle", "None");
		((BasicTreeUI) userTree.getUI()).setLeftChildIndent(0);
		((BasicTreeUI) userTree.getUI()).setRightChildIndent(0);
		initTreeListener();
		// TODO Auto-generated method stub
		// testShume();

	}

	private void testShume() {
		Timer timer = new Timer(700, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (hasMsg) {
					hasMsg = false;
				} else
					hasMsg = true;

				userTree.repaint();

			}

		});
		timer.start();
	}

	private void initTreeListener() {
		JMenuItem addGroup = factory.getPopupMenuItem("增加分组");
		JMenuItem renameGroup = factory.getPopupMenuItem("重命名");
		JMenuItem deleteGroup = factory.getPopupMenuItem("删除分组");
		deleteGroup.setActionCommand("delete");
		deleteGroup.addActionListener(this);
		groupMenu.add(addGroup);
		groupMenu.add(renameGroup);
		groupMenu.add(deleteGroup);
		groupMenu.setBackground(Color.WHITE);

		JMenuItem addGroup_blank = factory.getPopupMenuItem("增加分组");

		blankMenu.add(addGroup_blank);
		blankMenu.setBackground(Color.WHITE);

		JMenuItem sendMsg = factory.getPopupMenuItem("发送消息");
		sendMsg.setActionCommand("addchat");
		sendMsg.addActionListener(this);
		JMenuItem viewData = factory.getPopupMenuItem("查看资料");
		viewData.setActionCommand("showinfo");
		viewData.addActionListener(this);
		JMenuItem reviseNoteName = factory.getPopupMenuItem("修改备注名称");
		JMenuItem moveTo = factory.getPopupMenuItem("移送联系人至");
		JMenuItem deleteFriend = factory.getPopupMenuItem("删除好友");
		deleteFriend.setActionCommand("delete");
		deleteFriend.addActionListener(this);

		friendMenu.add(sendMsg);
		friendMenu.add(viewData);
		friendMenu.add(reviseNoteName);
		friendMenu.add(moveTo);
		friendMenu.add(deleteFriend);
		friendMenu.setBackground(Color.WHITE);

		userTree.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				mouseRow = -1;
				userTree.repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {

				int selRow = userTree.getRowForLocation(e.getX(), e.getY());
				TreePath selPath = userTree.getPathForLocation(e.getX(),
						e.getY());
				// /System.out.println(selPath);

				if (selRow != -1) {
					TreeNode node = (TreeNode) selPath.getLastPathComponent();
					// 如果单击的是鼠标右键
					if (e.getButton() == MouseEvent.BUTTON3) {
						userTree.setSelectionRow(selRow);

						if (node.isLeaf()) {
							// System.out.println("是叶子节点");
							friendMenu.show(userTree,
									userTree.getMousePosition().x,
									userTree.getMousePosition().y);

						} else {
							groupMenu.show(userTree,
									userTree.getMousePosition().x,
									userTree.getMousePosition().y);

						}

					} else if (e.getButton() == MouseEvent.BUTTON1) {
						if (node.isLeaf() && e.getClickCount() == 2) {
							DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) node;

							Object obj = dnode.getUserObject();
							if (obj instanceof Friend) {
								Friend friend = (Friend) dnode.getUserObject();
								msgMgr.notifyOpenMsg(friend.getId());
							}
							// MainFrame.this.toFront();
						}
					}

				} else {
					if (e.getButton() == MouseEvent.BUTTON3)
						blankMenu.show(userTree, userTree.getMousePosition().x,
								userTree.getMousePosition().y);
				}

			}

		});

		userTree.addMouseMotionListener(new MouseAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {

				int selRow = userTree.getRowForLocation(e.getX(), e.getY());
				/*
				 * if(selRow != -1){ if(selRow != mouseRow){ mouseRow = selRow;
				 * userTree.repaint();
				 * 
				 * }
				 * 
				 * }
				 */
				if (selRow != mouseRow) {
					mouseRow = selRow;
					userTree.repaint();
				}

			}

		});

	}

	public void addChat() {
		int row = userTree.getSelectionRows()[0];
		TreePath path = userTree.getSelectionPath();
		this.addChat(row, path);
		System.out.println("打开窗口");

	}

	public void addChat(int row, TreePath path) {
		if (row != -1 && path != null) {
			Object node = path.getLastPathComponent();

			DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) node;

			Object obj = dnode.getUserObject();
			if (obj instanceof Friend) {
				Friend friend = (Friend) dnode.getUserObject();
				msgMgr.notifyOpenMsg(friend.getId());
			}

		}

	}

	// 初始化底部当行区
	private void initNavigationPane() {
		JPanel down = new JPanel(new FlowLayout(FlowLayout.LEFT));
		down.add(this.btnMainMenu);
		down.add(this.btnTool);
		down.add(this.btnSearch);
		btnMainMenu.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
		btnTool.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
		btnSearch.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
		navigationPane.add(down, BorderLayout.CENTER);
		down.setBackground(new Color(238, 238, 238));
		navigationPane.setBackground(new Color(238, 238, 238));
		btnMainMenu.setToolTipText("主菜单");
		btnTool.setToolTipText("打开系统设置");
		btnSearch.setToolTipText("搜索好友和群");
		btnSearch.addActionListener(this);
		btnMainMenu.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				btnMainMenu.setIcon(new ImageIcon(
						ImageConstance.menu_btn_highlight));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnMainMenu.setIcon(new ImageIcon(
						ImageConstance.menu_btn_normal));
			}

		});

		class MyMonitor extends MouseAdapter {

			@Override
			public void mouseEntered(MouseEvent e) {
				JButton bn = (JButton) e.getSource();
				bn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				JButton bn = (JButton) e.getSource();
				bn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));

			}

		}

		MouseListener ml = new MyMonitor();
		btnTool.addMouseListener(ml);
		btnSearch.addMouseListener(ml);

		JMenuItem resetPassword = factory.getPopupMenuItem("修改密码");
		JMenuItem resetAcount = factory.getPopupMenuItem("切换账号");
		JMenuItem exit = factory.getPopupMenuItem("退出");
		JMenuItem about = factory.getPopupMenuItem("关于我");

		mainMenu.add(resetPassword);
		mainMenu.add(resetAcount);
		mainMenu.add(exit);
		mainMenu.add(about);
		mainMenu.setBackground(Color.WHITE);

		btnMainMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int height = mainMenu.getHeight() == 0 ? 125 : mainMenu
						.getHeight();
				mainMenu.show(btnMainMenu, 0, 0 - height);

			}

		});

	}

	// 将三个区装入主面板
	private void addPanes() {
		JPanel main = new JPanel(new BorderLayout());
		// 将用户信息添加到北方
		main.add(userPane, BorderLayout.NORTH);
		main.add(friendPane, BorderLayout.CENTER);
		main.add(navigationPane, BorderLayout.SOUTH);
		this.mainPane.add(main);

		this.shumeTimer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				userTree.repaint();
				if (MainFrame.this.hasMsg) {
					hasMsg = false;
				} else {
					hasMsg = true;
				}

			}

		});
		/* timer.start(); */

	}

	private void initPopuMenu() {
		popupMenu = new PopupMenu();

		VState[] states = VState.values();
		for (int i = 0; i < states.length; i++) {
			MenuItem item = new MenuItem(states[i].getName());
			item.setActionCommand(states[i].toString());
			popupMenu.add(item);
			item.addActionListener(this);
		}

		MenuItem quit = new MenuItem("退出");
		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}

		});

		MenuItem show = new MenuItem("打开主面板");
		show.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.trayClick();

			}

		});

		popupMenu.add(show);
		popupMenu.add(quit);

	}

	@Override
	public PopupMenu getPopuMenu() {

		return popupMenu;
	}

	private int getNodeWidth() {
		return userList.getViewport().getExtentSize().width;

	}

	@Override
	public void closeHappen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ingoreMsg(int id) {
		if (!msgMgr.haveUnreadMsg()) {
			if (shumeTimer.isRunning()) {
				shumeTimer.stop();
			}
		}

	}

	@Override
	public void ingoreAllMsg() {

		// TODO Auto-generated method stub

	}

	public boolean isHaveMsg(int id) {
		return this.msgMgr.isHaveUnreadMsg(id);
	}

	@Override
	public void recivedMsg(int id, String content) {
		if (!this.shumeTimer.isRunning()) {
			shumeTimer.start();
		}

	}

	@Override
	public void userStateChanged(VState state) {
		this.state.setText(state.getName());
		this.state.setIcon(factory.getIcon(ImageConstance.states.get(state)));
		// taskMgr.putMsg(new UserState);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnSearch) {
			if (this.searchBox != null) {
				this.searchBox.setVisible(true);
				this.searchBox.toFront();
			} else {
				searchBox = new SearchBox(this, "搜索好友", 500, 450);
				searchBox.setVisible(true);
			}
			return;
		}

		String str = e.getActionCommand();

		switch (str) {
		case "addchat":
			this.addChat();
			break;
		case "showinfo":
			this.showInfo();
			break;
		case "delete":
			this.willDelete();

			break;
		default:
			VState[] states = VState.values();
			for (VState state : states) {

				if (state.toString().equals(str)) {
					msgMgr.notifyUserStateChanged(state);
					taskMgr.putMsg(new UserStateChangeMsg(userMgr.getUser()
							.getId(), state));
					return;
				}
			}

		}

	}

	private void willDelete() {
		Object obj = this.getSelectUserObject();
		if (obj == null)
			return;

		if (obj instanceof Friend) {
			Friend friend = (Friend) obj;
			ClientMsg msg = new DeleteFriendMsg(userMgr.getUser().getId(),
					friend.getId(), friend.getGroup().getGroupId());
			taskMgr.putMsg(msg);

		}

		/*
		 * if(obj instanceof Group){
		 * 
		 * }
		 */

	}

	public Object getSelectUserObject() {

		int row = userTree.getSelectionRows()[0];
		TreePath path = userTree.getSelectionPath();

		if (row != -1 && path != null) {
			Object node = path.getLastPathComponent();

			DefaultMutableTreeNode dnode = (DefaultMutableTreeNode) node;

			return dnode.getUserObject();

		}

		return null;
	}

	private void showInfo() {

		Object obj = this.getSelectUserObject();

		if (obj == null)
			return;

		if (obj instanceof Friend) {
			Friend friend = (Friend) obj;
			this.addShowInfoFrame(friend);

		}

	}

	public void addShowInfoFrame(Friend friend) {
		ShowInfoFrame frame = new ShowInfoFrame(this, friend);
		this.showInfoFrames.put(friend.getId(), frame);
		frame.setVisible(true);
	}

	public void rePaintTree(Friend friend) {

		int index = root.getIndexByChidren(new VNode(friend.getGroup()));
		System.out.println("找到：index:" + index);
		if (index != -1) {

			VNode node = (VNode) root.getChildAt(index);
			node.reSort();
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				userTree.updateUI();
				((BasicTreeUI) userTree.getUI()).setLeftChildIndent(0);
				((BasicTreeUI) userTree.getUI()).setRightChildIndent(0);
			}
		});

	}

	public void queryResult(QueryResultMsg msg) {
		this.searchBox.queryResult(msg);

	}

	public void startSearch(String skeyword) {
		int id = userMgr.getUser().getId();
		ClientMsg msg = new QueryMsg(skeyword, id);
		taskMgr.putMsg(msg);
	}

	public void addClientMsg(ClientMsg msg) {
		taskMgr.putMsg(msg);
	}

	public void addFriendSucess(Friend friend, int groupId) {
		for (Group group : userMgr.getUser().getGroups()) {
			if (group.getGroupId() == groupId) {
				VNode node = new VNode(group);
				int index = root.getIndexByChidren(node);
				if (index != -1) {
					VNode supe = (VNode) root.getChildAt(index);
					System.out.println("找的好友所在组");
					supe.add(new VNode(friend));
					root.reSort();
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {

							userTree.updateUI();
							((BasicTreeUI) userTree.getUI())
									.setLeftChildIndent(0);
							((BasicTreeUI) userTree.getUI())
									.setRightChildIndent(0);
						}
					});
					this.toFront();
					return;
				}

			}
		}

	}

	public void deleteFriend(int friendId, int groupId) {

		Group group = userMgr.getGroupById(groupId);
		VNode node = root.getChildByChildUserObject(group);
		if (node != null) {
			boolean result = node.removeChildByUserObject(userMgr
					.getFriend(friendId));

			if (result) {
				System.out.println("移除成功");
				root.reSort();
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {

						userTree.updateUI();
						((BasicTreeUI) userTree.getUI()).setLeftChildIndent(0);
						((BasicTreeUI) userTree.getUI()).setRightChildIndent(0);
					}
				});
			}

		}

	}

}
