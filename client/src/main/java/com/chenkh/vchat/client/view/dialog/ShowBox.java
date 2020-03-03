package com.chenkh.vchat.client.view.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.chenkh.vchat.client.frame.ImageConstance;
import com.chenkh.vchat.client.frame.VFactory;
import com.chenkh.vchat.client.view.pane.VPanel;

public abstract class ShowBox extends VDialog implements ActionListener {

	private int width;
	private int height;
	private String title;
	private VFactory factory = VFactory.getInstance();
	private JButton bnClose = factory.getCloseButton();

	public ShowBox(String title, int width, int height) {
		super(true);
		this.setSize(width, height);
		this.setTitle(title);
		JLabel lbeTitle = new JLabel(title);
		lbeTitle.setIcon(new ImageIcon(ImageConstance.menu_btn_highlight));
		this.setLocationRelativeTo(null);
		lbeTitle.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lbeTitle.setForeground(Color.WHITE);
		JPanel topPane = new VPanel();
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.X_AXIS));
		topPane.add(lbeTitle);
		topPane.add(Box.createGlue());

		topPane.add(bnClose);
		topPane.setPreferredSize(new Dimension(width, 25));
		topPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 0));
		this.setTopPanel(topPane);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == bnClose) {
			this.dispose();
		}

	}

}
