package com.chenkh.vchat.client.frame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.chenkh.vchat.client.view.pane.ImagePanel;
import com.chenkh.vchat.base.bean.VState;

/**
 * 框架中的工厂类，负责建造复杂的容器布局，比如设置按钮的全图片化,封装内部类
 * 
 * @author Administrator
 * 
 */
public class VFactory {
	private static VFactory factory = new VFactory();

	private VFactory() {

	}

	public static VFactory getInstance() {
		return factory;
	}

	public JToggleButton getToggelButton(Image normal, Image highlight,
			Image push) {
		JToggleButton bn = new JToggleButton(this.getIcon(normal));
		bn.setSelectedIcon(this.getIcon(push));
		bn.setRolloverIcon(this.getIcon(highlight));
		bn.setContentAreaFilled(false);
		// bn.setbor
		bn.setBorder(null);

		return bn;

	}

	private Color panelBackground = new Color(245, 245, 245);

	/**
	 * 从指定的Image对象得到无边框无文字的按钮，按钮的背景为传入的image对象
	 * 
	 * @param image
	 *            按钮的背景
	 * @return 以image对象的按钮
	 */

	public JButton getButton(Image image) {
		JButton bn = new JButton(new ImageIcon(image));
		bn.setContentAreaFilled(false);
		bn.setBorder(null);
		bn.setFocusPainted(false);
		bn.setOpaque(false);
		return bn;
	}

	public JButton getCloseButton() {
		return this.getButton(ImageConstance.frame_close_normal,
				ImageConstance.frame_close_highlight,
				ImageConstance.frame_close_down);
	}

	public JButton getMaxButton() {
		return this.getButton(ImageConstance.frame_max_normal,
				ImageConstance.frame_max_highlight,
				ImageConstance.frame_max_down);
	}
	public JButton getMinButton() {
		return this.getButton(ImageConstance.frame_min_normal,
				ImageConstance.frame_min_highlight,
				ImageConstance.frame_min_down);
	}
	
	public JButton getRestoreButton() {
		return this.getButton(ImageConstance.frame_restore_normal,
				ImageConstance.frame_restore_highlight,
				ImageConstance.frame_restore_down);
	}
	
	
	
	

	// public JPopupMenu getPopupMenu(){}

	public JButton getSigleImageButton(Image image) {
		final JButton bn = new JButton(this.getIcon(image));
		bn.setContentAreaFilled(false);

		bn.setOpaque(false);
		bn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bn.setFocusPainted(false);
		bn.setBorderPainted(false);
		bn.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				bn.setBorderPainted(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				bn.setBorderPainted(false);
			}

		});

		return bn;

	}

	public JMenuItem getPopupMenuItem(Image icon, String label, int width,
			int height) {
		JMenuItem item = new JMenuItem(label, new ImageIcon(icon));
		item.setPreferredSize(new Dimension(width, height));
		item.setOpaque(false);

		return item;

	}

	public JMenuItem getPopupMenuItem(VState state) {
		JMenuItem item = null;
		int width = 120;
		int height = 30;
		Map<VState, Image> map = ImageConstance.states;
		item = this.getPopupMenuItem(map.get(state), state.getName(), width,
				height);

		return item;
	}

	public JMenuItem getPopupMenuItem(String label) {
		JMenuItem item = new JMenuItem(label);
		int width = 120;
		int height = 30;
		item.setOpaque(false);
		item.setPreferredSize(new Dimension(width, height));
		Font f = new Font("微软雅黑", Font.CENTER_BASELINE, 12);
		item.setFont(f);
		item.setBorder(BorderFactory.createEmptyBorder(2, 15, 2, 2));

		return item;
	}

	/**
	 * 得到普通按钮，白色背景，有边框
	 * 
	 * @param title
	 *            文字
	 * @return
	 */
	public JButton getButton(String title) {
		JButton bn = new JButton(title);
		bn.setBackground(Color.WHITE);
		return bn;

	}

	public JLabel getLabel(String s) {
		JLabel label = new JLabel(s);
		label.setBorder(null);

		return label;
	}

	/**
	 * 此方法用于得到指定标题，指定背景，指定按钮的顶部导航栏，标题往左靠，按钮尽量往右靠
	 * 
	 * @param title
	 *            框架标题
	 * @param bnMin
	 *            最小化的按钮
	 * @param bnClose
	 *            关闭按钮
	 * @param topBkg
	 *            背景图像
	 * @return 拥有指定背景，标题，按钮的JPanle
	 */

	public JPanel getTopPanel(String title, JButton bnMin, JButton bnClose,
			Image topBkg) {

		JPanel p = new ImagePanel(topBkg);
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		JLabel label = new JLabel(title);
		Font f = new Font("微软雅黑", Font.BOLD, 14);
		label.setFont(f);
		label.setForeground(Color.WHITE);
		p.add(Box.createHorizontalStrut(5));
		p.add(label);
		p.add(Box.createGlue());
		p.add(bnMin);
		p.add(bnClose);
		// p.setPreferredSize(new Dimension(p.getWidth(),50));
		// p.setBorder(BorderFactory.createEmptyBorder(0, 10, 2, 0));

		return p;
	}

	public JLabel getLabel(Image image) {
		JLabel label = new JLabel(new ImageIcon(image));
		label.setBorder(null);
		return label;
	}

	/**
	 * 调用此方法得到一个一boxLayot为布局，水平排列的JPanel
	 * 
	 * @return boxLayot为布局，水平排列的JPanel
	 */

	public JPanel getBoxLayoutPanelByX() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.setBackground(this.panelBackground);
		return p;
	}

	/**
	 * 通过指定的三张图片按钮
	 * 
	 * @param normal
	 *            普通显示的图片
	 * @param hover
	 *            鼠标经过的图片
	 * @param down
	 *            鼠标按下的图片
	 * @return
	 */
	public JButton getButton(Image normal, Image hover, Image down) {
		JButton bn = new JButton(this.getIcon(normal));

		bn.setOpaque(false);
		bn.setContentAreaFilled(false);
		bn.setFocusPainted(false);
		bn.setBorder(null);
		bn.setRolloverIcon(this.getIcon(hover));
		bn.setPressedIcon(this.getIcon(down));
		return bn;
	}

	/**
	 * 通过指定的文本和颜色得到一无边框，透明，鼠标状态为手的按钮
	 * 
	 * @param label
	 * @param bgColor
	 * @return
	 */

	public JButton getButton(String label, Color foreground) {
		JButton bn = new JButton(label);
		bn.setForeground(foreground);
		bn.setOpaque(false);
		bn.setBorder(null);
		bn.setContentAreaFilled(false);
		bn.setCursor(new Cursor(Cursor.HAND_CURSOR));

		return bn;
	}

	public JPanel getBoxLayoutPanelByXImage() {
		JPanel p = new ImagePanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		return p;
	}

	/**
	 * 调用此方法得到一个一boxLayot为布局，垂直排列的JPanel
	 * 
	 * @return boxLayot为布局，垂直排列的JPanel
	 */

	public JPanel getBoxLayoutPanelByY() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		// System.out.println(p.getBackground());
		p.setBackground(this.panelBackground);
		return p;
	}

	public JPanel getBoxLayoutPanelByYImage() {
		JPanel p = new ImagePanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		return p;
	}

	/**
	 * 用指定的图片和标题得到无边框以图片为背景的按钮
	 * 
	 * @param image
	 *            图片
	 * @param title
	 *            标题
	 * @return 以图片为背景，标题在背景中间
	 */

	public JButton getButton(Image image, String title) {
		return new VButton(image, image, title);
	}

	/*
	 * public JButton getButton(Image icon){ JButton bn = new JButton();
	 * bn.setIcon(new ImageIcon(icon)); bn.setOpaque(false); bn.setBorder(null);
	 * 
	 * return bn; }
	 */

	public JButton getButton(Image normalImage, Image enterImage, String label) {
		return new VButton(normalImage, enterImage, label);
	}

	public Icon getIcon(Image image) {
		return new ImageIcon(image);
	}

	/*
	 * 内部类，制造JButton
	 */
	private class VButton extends JButton {
		// normalImage 正常是的背景,
		// enterImage 鼠标进入后的背景
		// label 要显示的文字
		private Image normalImage = null;
		private Image enterImage = null;
		private String label = "";

		public VButton(Image normalImage, Image enterImage, String label) {
			this(normalImage, enterImage);
			this.label = label;
		}

		public VButton(Image normalImage, Image enterImage) {
			super(new ImageIcon(normalImage));
			this.normalImage = normalImage;
			this.enterImage = enterImage;
			this.setBorder(null);
			this.setContentAreaFilled(true);
		}

		public VButton(Image normalImage, String label) {
			super(new ImageIcon(normalImage));
			this.normalImage = normalImage;
			this.label = label;
			this.setBorder(null);
			this.setContentAreaFilled(false);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 * 重写父类的paintCompont()方法，实现在鼠标进入Button后的换肤功能
		 */
		public void paintComponent(Graphics g) {

			super.paintComponent(g);
			g.setColor(Color.BLACK);
			// 如果没有传入鼠标进入的图像，就不换肤
			if (this.enterImage != null) {
				if (this.getMousePosition() != null) {
					g.drawImage(new ImageIcon(enterImage).getImage(), 0, 0,
							this);
				} else {
					g.drawImage(new ImageIcon(normalImage).getImage(), 0, 0,
							this);
				}
			}
			// }

			if (label != null && !label.trim().equals("")) {
				g.setFont(g.getFont().deriveFont(12));
				g.drawString(label,
						(this.getWidth() - label.length() * 12) / 2,
						(this.getHeight() + 12 / label.length()) / 2);

			}
		}

	}

}
