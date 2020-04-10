package com.chenkh.vchat.client.view.box;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import com.chenkh.vchat.base.msg.ClientMsg;
import com.chenkh.vchat.base.msg.client.AddFriendMsg;
import com.chenkh.vchat.client.UserMgr;
import com.chenkh.vchat.client.frame.VFactory;
import com.chenkh.vchat.client.view.frame.MainFrame;
import com.chenkh.vchat.base.bean.Group;
import com.chenkh.vchat.base.bean.User;

public class AddFriendBox extends FrameBox {

	private JTextField note = new JTextField();
	private JComboBox<Group> groups = new JComboBox<Group>();
	private JPanel centerPane = new JPanel(new BorderLayout());
	private JButton bnSend = new JButton("完成");
	private VFactory factory = VFactory.getInstance();
	private MainFrame frame;
	private int friendId;
	private ShowInfoFrame box;

	public void setBox(ShowInfoFrame box) {
		this.box = box;
	}

	public AddFriendBox(MainFrame frame, int friendId, String friendName) {
		super("添加" + friendName + "为好友", 400, 250);
		this.friendId = friendId;
		this.frame = frame;
		JPanel box = factory.getBoxLayoutPanelByY();
		JPanel paneNote = factory.getBoxLayoutPanelByX();
		paneNote.add(new JLabel("备注名称:"));
		box.add(paneNote);
		paneNote.add(note);
		JPanel paneGroup = factory.getBoxLayoutPanelByX();
		paneGroup.add(new JLabel("所在分组:"));
		paneGroup.add(groups);
		box.add(paneGroup);

		DefaultComboBoxModel model = (DefaultComboBoxModel) groups.getModel();

		UserMgr userMgr = UserMgr.getInstance();

		User u = userMgr.getUser();

		for (Group group : u.getGroups()) {
			model.addElement(group);
		}

		centerPane.add(box, BorderLayout.NORTH);

		JPanel down = factory.getBoxLayoutPanelByX();
		down.add(Box.createGlue());
		down.add(bnSend);
		bnSend.addActionListener(this);
		down.add(Box.createGlue());

		this.setCneterPane(centerPane);
		this.setDownPane(down);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == bnSend) {
			this.sendMsg();
		}

	}

	private void sendMsg() {

		Object obj = groups.getSelectedItem();
		if (obj instanceof Group) {
			Group group = (Group) obj;
			int groupId = group.getGroupId();
			String noteName = this.note.getText().trim().equals("") ? null
					: note.getText();
			AddFriendMsg msg = new AddFriendMsg(UserMgr.getInstance().getUser()
					.getId(), this.friendId, groupId, noteName);
			frame.addClientMsg(msg);
			if (box != null) {
				box.hasFriend();
			}
			this.dispose();

		}

	}

	@Override
	public void closeHappen() {
		// TODO Auto-generated method stub

	}

}
