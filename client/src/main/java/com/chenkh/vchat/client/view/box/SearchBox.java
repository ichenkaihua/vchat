package com.chenkh.vchat.client.view.box;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import com.chenkh.vchat.base.msg.server.QueryResultMsg;
import com.chenkh.vchat.client.UserMgr;
import com.chenkh.vchat.client.frame.ImageConstance;
import com.chenkh.vchat.client.frame.VFactory;
import com.chenkh.vchat.client.view.frame.MainFrame;
import com.chenkh.vchat.client.view.pane.VPanel;
import com.chenkh.vchat.base.bean.Stranger;

public class SearchBox extends FrameBox implements TreeCellRenderer {

	private JTextField keyword = new JTextField();
	private VFactory factory = VFactory.getInstance();
	private JPanel center_top = new VPanel();
	private JComboBox<String> age = new JComboBox<String>(new String[] { " 不限",
			"10-20", "20-26" });
	private JComboBox<String> sex = new JComboBox<String>(new String[] { "不限",
			"男", "女" });
	private JComboBox<String> province = new JComboBox<String>();
	private JComboBox<String> city = new JComboBox<String>();
	private JButton bnSearch = new JButton("搜索");
	private MainFrame frame;

	private DefaultTreeModel treeModel = new DefaultTreeModel(
			new DefaultMutableTreeNode("root"));
	private JTree tree = new JTree(treeModel);
	private JPanel searchMsg = factory.getBoxLayoutPanelByX();
	private JLabel lbeSearchMsg = new JLabel();

	public SearchBox(MainFrame frame, String title, int width, int height) {
		super(title, width, height);
		this.frame = frame;
		JPanel center = new JPanel(new BorderLayout());
		center.add(center_top, BorderLayout.NORTH);
		this.setCneterPane(center);

		JLabel lbeKeyword = new JLabel("关键词:");
		JLabel lbeSex = new JLabel("性别:");
		JLabel lbeAge = new JLabel("年龄:");
		JLabel lbeProvince = new JLabel("省份:");
		JLabel lbeCity = new JLabel("市:");

		JPanel paneKeyword = factory.getBoxLayoutPanelByX();
		paneKeyword.add(lbeKeyword);
		paneKeyword.add(Box.createHorizontalStrut(5));
		paneKeyword.add(keyword);
		// keyword.setPreferredSize(new Dimension(20,20));

		paneKeyword.setOpaque(false);

		JPanel panSex = factory.getBoxLayoutPanelByX();
		panSex.add(lbeSex);
		panSex.add(Box.createHorizontalStrut(5));
		panSex.setOpaque(false);
		panSex.add(sex);

		JPanel paneAge = factory.getBoxLayoutPanelByX();
		paneAge.setOpaque(false);
		paneAge.add(lbeAge);
		paneAge.add(Box.createHorizontalStrut(5));
		paneAge.add(age);

		JPanel paneProvince = factory.getBoxLayoutPanelByX();
		paneProvince.setOpaque(false);
		paneProvince.add(lbeProvince);
		paneProvince.add(Box.createHorizontalStrut(5));
		paneProvince.add(province);

		JPanel paneCity = factory.getBoxLayoutPanelByX();
		this.initTree();

		paneCity.setOpaque(false);
		paneCity.add(lbeCity);
		paneCity.add(Box.createHorizontalStrut(5));
		paneCity.add(city);

		center_top.setLayout(new BoxLayout(center_top, BoxLayout.X_AXIS));

		JPanel p1 = factory.getBoxLayoutPanelByY();
		p1.setOpaque(false);
		p1.add(paneKeyword);
		p1.add(Box.createVerticalStrut(10));
		JPanel tem = factory.getBoxLayoutPanelByX();
		tem.add(paneProvince);
		tem.add(Box.createHorizontalStrut(20));
		tem.add(paneCity);
		tem.setOpaque(false);
		p1.add(tem);

		JPanel p2 = factory.getBoxLayoutPanelByY();
		p2.add(paneAge);
		p2.add(Box.createVerticalStrut(10));
		p2.add(panSex);
		p2.setOpaque(false);

		center_top.add(Box.createHorizontalStrut(10));
		center_top.add(p1);
		center_top.add(Box.createHorizontalStrut(20));
		center_top.add(p2);
		center_top.add(Box.createHorizontalStrut(20));
		center_top.add(bnSearch);
		bnSearch.addActionListener(this);
		center_top.add(Box.createHorizontalStrut(20));

		center_top.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

		JPanel center_center = new JPanel(new BorderLayout());

		JScrollPane scrollPane = new JScrollPane(tree);
		center_center.add(searchMsg, BorderLayout.NORTH);
		searchMsg.add(Box.createGlue());
		searchMsg.add(lbeSearchMsg);
		searchMsg.add(Box.createGlue());
		searchMsg.setVisible(false);

		lbeSearchMsg.setHorizontalAlignment(SwingConstants.CENTER);
		lbeSearchMsg.setFont(new Font("隶书", Font.BOLD, 20));
		lbeSearchMsg.setForeground(Color.RED);
		center_center.add(scrollPane, BorderLayout.CENTER);
		center_center.setBorder(BorderFactory.createEmptyBorder(0, 5, 20, 5));

		center.add(center_center, BorderLayout.CENTER);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		super.actionPerformed(e);
		if (e.getSource() == bnSearch) {
			String skeyword = keyword.getText().trim();
			frame.startSearch(skeyword);
			lbeSearchMsg.setText("正在搜索,,");
			return;
		}
		String str = e.getActionCommand();

		if (str.equals("showInfomation")) {
			this.showInfo();
		} else if (str.equals("addFriend")) {
			this.addFriend();
		}

	}

	private void addFriend() {
		int row = tree.getSelectionRows().length > 0 ? tree.getSelectionRows()[0]
				: -1;
		TreePath path = tree.getSelectionPath();

		if (row != -1) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
					.getLastPathComponent();
			Stranger stranger = (Stranger) node.getUserObject();
			if (!UserMgr.getInstance().isFriend(stranger.getId())
					&& UserMgr.getInstance().getUser().getId() != stranger
							.getId()) {
				AddFriendBox box = new AddFriendBox(frame, stranger.getId(),
						stranger.getUsername());
				box.setVisible(true);
			}
		}

	}

	private void showInfo() {
		int row = tree.getSelectionRows().length > 0 ? tree.getSelectionRows()[0]
				: -1;
		TreePath path = tree.getSelectionPath();

		if (row != -1) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
					.getLastPathComponent();
			Stranger stranger = (Stranger) node.getUserObject();
			frame.addShowInfoFrame(stranger);
		}

	}

	private void initTree() {
		tree.setRootVisible(false);
		tree.setCellRenderer(this);
		final JPopupMenu menu = new JPopupMenu();
		JMenuItem showInfomation = new JMenuItem("查看资料");
		JMenuItem addFriend = new JMenuItem("加为好友");
		showInfomation.setPreferredSize(new Dimension(110, 40));
		addFriend.setPreferredSize(new Dimension(110, 40));
		showInfomation.setActionCommand("showInfomation");
		addFriend.setActionCommand("addFriend");
		showInfomation.addActionListener(this);

		addFriend.addActionListener(this);
		menu.add(showInfomation);
		menu.add(addFriend);

		tree.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tree.getRowForLocation(e.getX(), e.getY());
				TreePath path = tree.getPathForLocation(e.getX(), e.getY());
				if (row != -1) {
					if (e.getClickCount() == 2
							&& e.getButton() == MouseEvent.BUTTON1) {
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
								.getLastPathComponent();
						Stranger stranger = (Stranger) node.getUserObject();
						frame.addShowInfoFrame(stranger);
						// SearchBox.this.bnSearch.a

					}
				}

			}

			@Override
			public void mousePressed(MouseEvent e) {
				int row = tree.getRowForLocation(e.getX(), e.getY());

				if (row != -1) {
					if (e.getButton() == MouseEvent.BUTTON3) {
						tree.setSelectionRow(row);
						menu.show(tree, e.getX(), e.getY());
					}
				}

			}

		});

	}

	@Override
	public void closeHappen() {
		// System.out.println("窗口即将退出!");

	}

	public void queryResult(QueryResultMsg msg) {
		if (!this.searchMsg.isVisible()) {
			searchMsg.setVisible(true);
		}
		List<Stranger> strangers = msg.getStrangers();
		if (strangers.size() == 0) {
			lbeSearchMsg.setText("哎，没有搜到好友");
		} else {
			lbeSearchMsg.setText("不错，搜索到" + strangers.size() + "个好友");
		}
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel
				.getRoot();
		root.removeAllChildren();

		for (Stranger stranger : strangers) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(stranger);
			root.add(node);

		}
		treeModel.reload(root);
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		JPanel node = factory.getBoxLayoutPanelByX();
		if (value.getClass() == DefaultMutableTreeNode.class) {
			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;
			Object obj = treeNode.getUserObject();
			if (obj.getClass() == Stranger.class) {
				Stranger stranger = (Stranger) obj;
				JLabel lbeHead = new JLabel(new ImageIcon(
						ImageConstance.menu_btn_highlight));

				JLabel lbeId = new JLabel("id:" + stranger.getId());
				JLabel lbeUsername = new JLabel("用户名:" + stranger.getUsername());
				JLabel lbeSign = new JLabel("签名:"
						+ (stranger.getSign() == null ? "无"
								: stranger.getSign()));
				JLabel lbeSex = new JLabel(stranger.getSex() == null ? ""
						: stranger.getSex());
				node.add(Box.createHorizontalStrut(10));
				node.add(lbeHead);
				node.add(Box.createHorizontalStrut(25));
				node.add(lbeId);
				node.add(Box.createHorizontalStrut(25));
				node.add(lbeSign);
				node.add(Box.createHorizontalStrut(25));
				node.add(lbeUsername);
				node.add(Box.createHorizontalStrut(25));
				node.add(lbeSex);
				node.add(Box.createGlue());

			}

		}

		if (selected) {
			node.setBackground(new Color(240, 245, 245));
		} else {
			node.setBackground(Color.WHITE);
		}

		node.setPreferredSize(new Dimension(this.getWidth() - 14, 40));

		return node;
	}

	

}
