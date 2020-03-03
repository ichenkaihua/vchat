package com.chenkh.vchat.client.frame.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.text.StyledDocument;

import com.chenkh.vchat.client.UserMgr;
import com.chenkh.vchat.client.enu.AttSetType;
import com.chenkh.vchat.client.frame.ImageConstance;
import com.chenkh.vchat.client.frame.VFactory;

public class TabContenPane extends JPanel implements MouseListener,
		ActionListener {
	private VFactory factory = VFactory.getInstance();
	private final Tab tab;
	private JButton bnName;
	private JButton bnHead;
	private JLabel lbeSign = new JLabel();
	private JSplitPane splitPane = new JSplitPane();
	private JTextPane textPane;
	private JPanel fontSet = factory.getBoxLayoutPanelByX();
	private JComboBox<String> font_family = new JComboBox<String>(
			GraphicsEnvironment.getLocalGraphicsEnvironment()
					.getAvailableFontFamilyNames());
	private JComboBox<Integer> font_size = null;
	private Color fontColor;
	private JScrollBar bar = new JScrollBar();

	private JTextPane textEditor = new JTextPane();
	private JButton bnFace;
	private JButton bnFont;
	private JButton bnRegister;
	private JButton sendpic;
	private JButton bnClose;
	private JButton bnSend;
	private JToggleButton font_bold = factory.getToggelButton(
			ImageConstance.font_bold_normal,
			ImageConstance.font_bold_highlight, ImageConstance.font_bold_push);
	private JToggleButton font_italic = factory.getToggelButton(
			ImageConstance.font_italic_normal,
			ImageConstance.font_italic_highlight,
			ImageConstance.font_italic_push);
	private JToggleButton font_underline = factory.getToggelButton(
			ImageConstance.font_underline_normal,
			ImageConstance.font_underline_highlight,
			ImageConstance.font_underline_push);
	private JToggleButton font_color = factory
			.getToggelButton(ImageConstance.font_color_normal,
					ImageConstance.font_color_highlight,
					ImageConstance.font_color_push);

	public TabContenPane(Tab tab, StyledDocument doc, String name, String sign) {

		super(new BorderLayout());

		this.tab = tab;

		textPane = new JTextPane(doc);

		DefaultCaret caret = new DefaultCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		caret.setVisible(true);
		textPane.setCaret(caret);
		textPane.setEditable(false);
		
		
		

		Integer[] sizes = new Integer[11];

		for (int i = 0; i < sizes.length; i++) {
			sizes[i] = 9 + i;
		}

		font_size = new JComboBox<Integer>(sizes);

		bnFace = factory.getSigleImageButton(ImageConstance.face);
		bnFont = factory.getSigleImageButton(ImageConstance.font);
		bnRegister = factory.getSigleImageButton(ImageConstance.register);
		sendpic = factory.getSigleImageButton(ImageConstance.sendpic);
		bnRegister.setText("消息记录");

		// 定义一个顶部容器，装入头像，用户名，签名等,设置为透明
		JPanel topPane = factory.getBoxLayoutPanelByXImage();
		// topPane.setBackground(new Color(210,210,250));
		topPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// 定义一个头像右边容器，负责装入用户名，签名,设置为透明
		JPanel topPane_right = factory.getBoxLayoutPanelByY();
		topPane_right.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
		topPane_right.setOpaque(false);

		// 初始化头像，用户名，签名
		bnHead = factory.getButton(ImageConstance.head);
		bnHead.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
		bnHead.setCursor(new Cursor(Cursor.HAND_CURSOR));
		Font f = new Font("宋体", Font.BOLD, 20);
		bnName = factory.getButton(name, Color.BLACK);
		// bnName.setForeground(Color.WHITE);
		bnName.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
				Color.black));
		bnName.setBorderPainted(false);
		bnName.setFocusPainted(false);

		bnName.setFont(f);
		lbeSign.setText(sign);
		lbeSign.setToolTipText(lbeSign.getText());

		// 将用户名，签名放入右方容器
		topPane_right.add(bnName);
		topPane_right.add(Box.createGlue());
		topPane_right.add(lbeSign);

		// 将头像，用户名，签名添加到上方容器内
		topPane.add(bnHead);
		topPane.add(topPane_right);

		// 初始化发送，关闭按钮
		bnClose = new JButton("关闭");
		bnSend = new JButton("发送");
		bnClose.setFocusPainted(false);
		bnSend.setFocusPainted(false);
		bnClose.addActionListener(this);
		bnSend.addActionListener(this);
		font_family.setAlignmentX(SwingConstants.CENTER);

		initCenterPane();

		this.add(topPane, BorderLayout.NORTH);
		this.add(splitPane, BorderLayout.CENTER);

		this.addListener();

	}

	private void initCenterPane() {

		JPanel left = new JPanel(new BorderLayout());

		JPanel right = new JPanel(new BorderLayout());
		right.setOpaque(false);
		splitPane.setOpaque(false);
		// right.setPreferredSize(new Dimension(220,this.getHeight()));

		splitPane.setLeftComponent(left);
		splitPane.setRightComponent(right);
		splitPane.setResizeWeight(0.7);
		splitPane.setOneTouchExpandable(true);
		splitPane.setContinuousLayout(true);

		JScrollPane textScrollPane = new JScrollPane(textPane);
		textScrollPane.setVerticalScrollBar(bar);
		

		left.add(textScrollPane, BorderLayout.CENTER);

		textEditor.setBackground(Color.WHITE);
		// textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		// this.textEditor.setPreferredSize(new Dimension(this.getWidth(),
		// 100));
		JScrollPane scrollPane = new JScrollPane(textEditor);
		scrollPane.setPreferredSize(new Dimension(this.getWidth(), 100));

		JPanel sendPane = factory.getBoxLayoutPanelByX();
		sendPane.add(Box.createGlue());
		sendPane.add(bnClose);
		sendPane.add(Box.createHorizontalStrut(20));
		sendPane.add(bnSend);
		sendPane.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 10));
		// sendPane.setBackground(new Color(210,230,240));
		sendPane.setOpaque(false);

		JPanel left_down = new JPanel(new BorderLayout());

		JPanel toolbar = factory.getBoxLayoutPanelByX();
		toolbar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		int strut = 10;

		bnFont.addActionListener(this);

		fontSet.setOpaque(false);
		fontSet.setVisible(false);
		fontSet.setBorder(BorderFactory.createEmptyBorder(1, 10, 1, 10));

		fontSet.add(new JLabel(factory.getIcon(ImageConstance.textmodel)));
		fontSet.add(Box.createHorizontalStrut(15));

		fontSet.add(new JLabel(factory.getIcon(ImageConstance.font_image)));
		fontSet.add(Box.createHorizontalStrut(15));
		Font f = new Font("微软雅黑", Font.LAYOUT_NO_LIMIT_CONTEXT, 12);
		fontSet.add(this.font_family);
		font_family.setBorder(null);
		font_family.setSelectedItem("宋体");
		font_family.setFont(f);
		font_family.setPreferredSize(new Dimension(10, 15));
		font_family.setBackground(Color.WHITE);

		font_size.setFont(f);
		font_size.setPreferredSize(new Dimension(10, 15));
		font_size.setBackground(Color.WHITE);
		font_size.setBorder(null);

		fontSet.add(Box.createHorizontalStrut(15));
		fontSet.add(this.font_size);
		fontSet.add(Box.createHorizontalStrut(15));

		fontSet.add(font_bold);

		fontSet.add(Box.createHorizontalStrut(15));
		fontSet.add(font_italic);
		fontSet.add(Box.createHorizontalStrut(15));
		fontSet.add(font_underline);
		fontSet.add(Box.createHorizontalStrut(15));
		fontSet.add(font_color);
		fontSet.add(Box.createGlue());
		font_color.addActionListener(this);
		JPanel ptool = factory.getBoxLayoutPanelByY();
		ptool.setOpaque(false);

		ptool.add(fontSet);
		ptool.add(toolbar);

		toolbar.add(bnFont);
		toolbar.add(Box.createHorizontalStrut(strut));
		toolbar.add(bnFace);
		toolbar.add(Box.createHorizontalStrut(strut));
		toolbar.add(this.sendpic);
		toolbar.add(Box.createHorizontalStrut(strut));
		toolbar.add(Box.createGlue());
		toolbar.add(this.bnRegister);
		// System.out.println(Color.BLUE);
		toolbar.setBackground(new Color(200, 220, 240));

		// toolbar.setOpaque(false);

		left.setBackground(new Color(210, 230, 240));

		left_down.add(ptool, BorderLayout.NORTH);

		left_down.add(scrollPane, BorderLayout.CENTER);
		left_down.add(sendPane, BorderLayout.SOUTH);

		left_down.setBackground(new Color(210, 230, 240));

		left.add(left_down, BorderLayout.SOUTH);

	}

	private void addListener() {
		bnName.addMouseListener(this);
		bnHead.addMouseListener(this);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Object obj = e.getSource();
		if (obj == bnName) {
			bnName.setBorderPainted(true);
		} else if (obj == bnHead) {
			bnHead.setBorder(BorderFactory.createBevelBorder(
					BevelBorder.RAISED, Color.BLUE, Color.BLACK));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object obj = e.getSource();
		if (obj == bnName) {
			bnName.setBorderPainted(false);
		} else if (obj == bnHead) {
			bnHead.setBorder(BorderFactory
					.createLineBorder(Color.GRAY, 2, true));
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == bnClose) {
			tab.remove();
		} else if (obj == bnSend) {
			if (textEditor.getText() != null
					&& !textEditor.getText().trim().equals("")) {
				tab.sendMsg(textEditor.getText());
				 bar.setValue(bar.getMaximum());
				textEditor.setText("");
			}
		} else if (obj == font_color) {
			if (font_color.isSelected()) {
				fontColor = JColorChooser.showDialog(this, "字体颜色", Color.BLACK);
			}
		} else if (obj == bnFont) {
			if (!this.fontSet.isVisible()) {
				fontSet.setVisible(true);
			} else {
				fontSet.setVisible(false);
				String name = (String) font_family.getSelectedItem();
				int size = (Integer) font_size.getSelectedItem();
				UserMgr.getInstance().setAttributeSet(AttSetType.userMsg,
						this.font_color.isSelected() ? fontColor : null, name,
						size, font_bold.isSelected(), font_italic.isSelected(),
						font_underline.isSelected());
			}
		}

	}

}
