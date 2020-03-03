package com.chenkh.vchat.client.view.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.chenkh.vchat.client.frame.ImageConstance;
import com.chenkh.vchat.client.frame.VFactory;
import com.chenkh.vchat.client.tool.ImageTranser;
import com.chenkh.vchat.base.bean.Friend;
import com.chenkh.vchat.base.bean.Group;
import com.chenkh.vchat.base.bean.VState;

public class TreeCell extends DefaultTreeCellRenderer {
	private MainFrame frame;
	private Color mouseHoverColor = new Color(238, 238, 238);
	private VFactory factory = VFactory.getInstance();

	private JLabel headLabel = new JLabel(factory.getIcon(ImageConstance.head));
	private Icon offlineheadImage = null;
	private JLabel grayHeadImage = null;
	private int width;
	private int height;
	private JLabel arrow_0 = factory.getLabel(ImageConstance.tip_0);
	private JLabel arrow_90 = factory.getLabel(ImageConstance.tip_90);

	public TreeCell(MainFrame frame) {
		this.frame = frame;
		width = frame.getWidth() - 10;
		height = 50;
		offlineheadImage = factory.getIcon(ImageTranser
				.getGrayPicture(ImageConstance.head));
		grayHeadImage = new JLabel(offlineheadImage);
		this.setLayout(new BorderLayout());
		this.setBackgroundNonSelectionColor(Color.WHITE);
		this.setBackgroundSelectionColor(new Color(222, 222, 222));

	}

	private JLabel getHeadLabel() {
		JLabel label = new JLabel();

		label.setPreferredSize(new Dimension(height, height));
		label.setLayout(new BorderLayout());
		
		
		
		return label;
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		JPanel nodePane = new JPanel(new BorderLayout());
		// 先判断是不是DefaultMutableTreeNode，
		if (value instanceof VNode) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			Object userObj = node.getUserObject();
			// 如果是分组信息，则显示分组
			if (userObj instanceof Group) {
				Group group = (Group) userObj;
				String groupName = group.getName();
				JLabel labelName = new JLabel(groupName);
				if (expanded)
					nodePane.add(arrow_90, BorderLayout.WEST);
				else
					nodePane.add(arrow_0, BorderLayout.WEST);
				nodePane.add(labelName, BorderLayout.CENTER);
				nodePane.setPreferredSize(new Dimension(width, 30));
				nodePane.setBackground(Color.WHITE);

			} else if (userObj instanceof Friend) {
				Friend friend = (Friend) userObj;
				VState state = friend.getState();
				JLabel left = this.getHeadLabel();

				if (state == VState.offline || state == VState.invisible) {
					left.add(grayHeadImage);
				} else {
					left.add(headLabel);
				}
				Box center = new Box(BoxLayout.Y_AXIS);
				center.setOpaque(false);
				String name = friend.getNoteName();
				center.add(new JLabel(name == null ? friend.getUsernmae()
						: name + "(" + friend.getUsernmae() + ")"));
				center.add(Box.createGlue());
				center.add(new JLabel(friend.getSign() == null ? "  " : friend
						.getSign()));
				center.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));

				JLabel lblState = new JLabel(state.getName());

				nodePane.add(left, BorderLayout.WEST);
				nodePane.add(center, BorderLayout.CENTER);
				nodePane.add(lblState, BorderLayout.EAST);
				nodePane.setPreferredSize(new Dimension(width, height));

			}

			if (sel && leaf) {
				nodePane.setBackground(this.getBackgroundSelectionColor());
			} else
				nodePane.setBackground(this.getBackgroundNonSelectionColor());

			if (row == frame.getMouseRow()) {
				nodePane.setBackground(this.mouseHoverColor);
			}
		}

		return nodePane;

	}

}
