package com.chenkh.vchat.client.view.frame;

import com.chenkh.vchat.base.bean.VState;
import com.chenkh.vchat.base.msg.server.LoginResultMsg;
import com.chenkh.vchat.client.IContext;
import com.chenkh.vchat.client.frame.ImageConstance;
import com.chenkh.vchat.client.net.IMsgListener;
import com.chenkh.vchat.client.view.box.RegisterFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 登录框架，继承VFrame，实现对登录按钮，取消登录按钮，确定按钮监听
 * 
 * @author Administrator
 * 
 */
public class LoginFrame extends VFrame implements ActionListener, IMsgListener {
	@Override
	public void dispose() {
		if(registerFrame != null && registerFrame.isVisible()){
			registerFrame.dispose();
		}
		context.getNetClient().removeMsgListener(this);
		context.getTray().removeObserver(this);
		super.dispose();
	}

	private JPanel loginMainPane = null;
	private JButton bnLogin = null;
	private JButton bnCancer = null;
	private CardLayout cardLayout = new CardLayout();
	private JPanel loginStatePane = new JPanel(cardLayout);
	private JComboBox<String> username = new JComboBox<String>();
	private JPasswordField password = new JPasswordField();
	private JCheckBox remenber_password = new JCheckBox("记住密码");
	private JCheckBox autoLogin = new JCheckBox("自动登录");
	private JButton bnRegister = new JButton("注册账号");
	private JButton bnLossPass = new JButton("忘记密码");
	private JButton bnReturn = null;
	private Font vfont = new Font("微软雅黑", Font.BOLD, 16);
	private JLabel errMsg = new JLabel("");
	private final String[] paneName = { "initPane", "loginingPane",
			"loginErrorPane" };
	private RegisterFrame registerFrame;
	private IContext context;



	public LoginFrame(String title, int width, int height, IContext context) {
		super( title, width, height);
		this.context=context;
		context.getNetClient().addMsgListener(this);
		context.getTray().addObserver(this);


		showSelf();



	}


	private void showSelf() {
		// loginMainPane = new VPanel(new BorderLayout());
		loginMainPane = new JPanel(new BorderLayout());
		loginMainPane.setBackground(Color.WHITE);
		mainPane.add(loginMainPane);
		JPanel login_bkg = new JPanel(new BorderLayout());
		JLabel login_bkg_label = factory
				.getLabel(ImageConstance.login_panel_bkg);
		login_bkg.add(login_bkg_label);
		loginMainPane.add(login_bkg, BorderLayout.NORTH);
		loginMainPane.add(loginStatePane, BorderLayout.CENTER);
		// 设置下拉框为可写
		this.username.setEditable(true);
		// 初始化按钮
		bnLogin = factory.getButton(ImageConstance.login_normal,
				ImageConstance.login_enter, "登录");
		bnCancer = factory.getButton(ImageConstance.login_normal,
				ImageConstance.login_enter, "取消登录");
		bnReturn = factory.getButton("确定");
		this.setButton(bnRegister);
		this.setButton(bnLossPass);
		bnRegister.addActionListener(this);
		username.setFont(vfont);
		password.setFont(vfont);

		// 设置窗体可见
		// this.setVisible(true);
		// 登陆之前的初始界面
		JPanel initPane = factory.getBoxLayoutPanelByY();
		// 登录中的界面
		JPanel loginingPane = new JPanel(new BorderLayout());
		// 登陆后的错误界面
		JPanel loginError = new JPanel(new BorderLayout());
		// 分别初始化三个界面
		this.showInitPane(initPane);
		this.showLoginingPane(loginingPane);
		this.showLoginErrorPane(loginError);
		// 分别给按钮添加动作监听
		bnLogin.addActionListener(this);
		bnCancer.addActionListener(this);
		bnReturn.addActionListener(this);

		// 初始化弹出菜单
		initPopupMenu();

		// 分别添加三个界面至loginState
		loginStatePane.add(initPane);
		loginStatePane.add(loginingPane);
		loginStatePane.add(loginError);
		cardLayout.addLayoutComponent(initPane, paneName[0]);
		cardLayout.addLayoutComponent(loginingPane, paneName[1]);
		cardLayout.addLayoutComponent(loginError, paneName[2]);
		this.getRootPane().setDefaultButton(bnLogin);

	}

	// 初始化系统托盘菜单
	private void initPopupMenu() {
		popupMenu = new PopupMenu();
		MenuItem item1 = new MenuItem("退出");



		MenuItem item2 = new MenuItem("打开主面板");
		item2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!LoginFrame.this.isVisible()) {
					LoginFrame.this.setVisible(true);
					System.out.println("单击显示面板");
				}
			}

		});
		item1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}

		});

		popupMenu.add(item2);
		popupMenu.add(item1);

	}

	// 设置似链接的按钮
	private void setButton(final JButton bn) {
		bn.setBorder(null);
		bn.setContentAreaFilled(false);
		bn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		bn.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {

				bn.setForeground(Color.BLUE);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				bn.setForeground(Color.BLACK);
			}

		});

	}

	// 初始化未登录的框架
	private void showInitPane(JPanel initPane) {
		JPanel top = factory.getBoxLayoutPanelByX();
		JPanel down = factory.getBoxLayoutPanelByY();

		JPanel west = factory.getBoxLayoutPanelByX();
		JPanel center = factory.getBoxLayoutPanelByY();
		JPanel east = factory.getBoxLayoutPanelByY();
		// 左边部分
		west.add(Box.createHorizontalStrut(20));
		west.add(factory.getLabel(ImageConstance.login_head));

		top.add(west);
		top.add(Box.createHorizontalStrut(10));

		// 中间部分

		center.add(Box.createVerticalStrut(10));
		center.add(username);
		center.add(Box.createVerticalStrut(10));
		center.add(password);
		JPanel checkbox = factory.getBoxLayoutPanelByX();
		checkbox.add(remenber_password);
		remenber_password.setBackground(new Color(245, 245, 245));
		autoLogin.setBackground(new Color(245, 245, 245));
		checkbox.add(Box.createHorizontalGlue());
		checkbox.add(autoLogin);
		center.add(checkbox);
		// center.add(Box.createVerticalStrut(15));

		top.add(center);
		top.add(Box.createHorizontalStrut(10));

		top.add(Box.createHorizontalStrut(10));
		// 右边部分

		east.add(Box.createVerticalStrut(20));

		east.add(bnRegister);
		east.add(Box.createVerticalStrut(22));
		east.add(bnLossPass);
		east.add(Box.createGlue());
		top.add(east);

		top.add(Box.createHorizontalStrut(20));

		JPanel down_top = factory.getBoxLayoutPanelByX();
		down_top.add(bnLogin);
		down.add(Box.createVerticalStrut(5));
		down.add(down_top);
		down.add(Box.createVerticalStrut(5));
		down.setBackground(new Color(238, 238, 235));
		// ce[r=238,g=238,b=238]

		initPane.add(top);
		initPane.add(down);

	}

	// 初始化登录中的框架
	private void showLoginingPane(JPanel loginPane) {
		// 上部分，放置登录头像以及登录进度条
		JPanel top = factory.getBoxLayoutPanelByY();
		JPanel top_head = factory.getBoxLayoutPanelByX();

		// 头像部分
		top_head.add(Box.createGlue());
		top_head.add(factory.getLabel(ImageConstance.login_head));
		top_head.add(Box.createGlue());

		top.add(top_head);

		JPanel namePane = factory.getBoxLayoutPanelByX();
		namePane.add(Box.createGlue());

		Font f = new Font("Dialog", Font.BOLD, 16);
		JLabel name = new JLabel("10000");
		name.setFont(f);
		namePane.add(name);
		namePane.add(Box.createGlue());

		top.add(namePane);

		// 进度条部分
		JPanel loading = factory.getBoxLayoutPanelByX();
		loading.add(factory.getLabel(ImageConstance.login_loading));
		top.add(loading);

		// 下部分，放置按钮
		JPanel down = factory.getBoxLayoutPanelByY();
		down.add(Box.createVerticalStrut(3));
		JPanel cancer = factory.getBoxLayoutPanelByX();
		cancer.add(Box.createGlue());
		cancer.add(bnCancer);
		cancer.add(Box.createGlue());
		down.add(cancer);
		down.add(Box.createVerticalStrut(3));
		down.setBackground(new Color(238, 238, 238));
		cancer.setBackground(new Color(238, 238, 238));
		loginPane.add(top);
		loginPane.add(down, BorderLayout.SOUTH);

	}

	// 初始化登陆后未成功的面板
	private void showLoginErrorPane(JPanel loginError) {
		JPanel top = factory.getBoxLayoutPanelByY();
		JPanel down = factory.getBoxLayoutPanelByY();

		JPanel top_top = factory.getBoxLayoutPanelByX();
		JLabel label = new JLabel("登录错误");
		Font f = new Font("Dialog", Font.BOLD, 16);
		label.setFont(f);
		top_top.add(Box.createGlue());
		top_top.add(label);
		top_top.add(Box.createGlue());

		JPanel top_down = factory.getBoxLayoutPanelByX();
		top_down.add(Box.createGlue());
		// JLabel errMsg = new JLabel("用户名不正确");
		errMsg.setFont(new Font("Dialog", Font.BOLD, 20));
		errMsg.setForeground(Color.RED);
		top_down.add(errMsg);
		top_down.add(Box.createGlue());

		down.add(Box.createVerticalStrut(10));
		JPanel center = factory.getBoxLayoutPanelByX();
		center.add(Box.createGlue());
		center.add(bnReturn);
		center.add(Box.createGlue());
		down.add(center);
		down.add(Box.createVerticalStrut(10));
		down.setBackground(new Color(238, 238, 238));
		center.setBackground(new Color(238, 238, 238));

		top.add(top_top);
		top.add(top_down);
		top.add(Box.createVerticalStrut(20));

		loginError.add(top);
		loginError.add(down, BorderLayout.SOUTH);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.bnLogin) {
			login();

		} else if(e.getSource()==bnRegister){
			if(this.registerFrame == null){
				registerFrame = new RegisterFrame("用户注册",400,500,context,registerId->{
					SwingUtilities.invokeLater(()->username.setSelectedItem(registerId));
				});
				registerFrame.setVisible(true);
			}
			else registerFrame.setVisible(true);
			
		} 
		else {
			cardLayout.show(loginStatePane, paneName[0]);
		}

	}

	private void login() {
		cardLayout.show(loginStatePane, paneName[1]);
		String sid = (String) username.getSelectedItem();
		int id = Integer.parseInt(sid);
		String pas = new String(password.getPassword());
		context.getNetClient().sendLoginMsg(id,pas,VState.imonline);
		context.getTray().startLogin();
	}






	@Override
	public void onReceivedLoginResultMsg(LoginResultMsg loginResultMsg) {

		if(this.isDisplayable() && !loginResultMsg.isSuccess()){
			SwingUtilities.invokeLater(()->{
				cardLayout.show(loginStatePane, paneName[1]);
				errMsg.setText(loginResultMsg.getReason());
				cardLayout.show(loginStatePane, paneName[2]);
			});

		}
	}

	@Override
	public PopupMenu getPopuMenu() {
		return popupMenu;
	}
}
