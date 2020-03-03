package com.chenkh.vchat.client.view.box;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


import com.chenkh.vchat.base.msg.ClientMsg;
import com.chenkh.vchat.base.msg.client.RegisterMsg;
import com.chenkh.vchat.client.frame.VFactory;
import com.chenkh.vchat.client.view.frame.LoginFrame;
import com.chenkh.vchat.base.bean.User;

public class RegisterFrame extends FrameBox {
	private VFactory factory = VFactory.getInstance();

	private CardLayout cardLayout = new CardLayout();
	private JPanel rMainPane = new JPanel(cardLayout);
	private JLabel lbeState = this.getLabel("正在注册,,,");

	private JPanel registerPane = new JPanel(new GridLayout(5, 2, 10, 10));
	private JPanel resultPane = new JPanel(new BorderLayout());

	private JTextField resultId = new JTextField();

	private JPanel down = VFactory.getInstance().getBoxLayoutPanelByX();
	private JButton bnRegister = new JButton("注册");
	private JButton bnCancer = new JButton("取消");
	private JButton bnReset = new JButton("重设");
	private JTextField userName = new JTextField();
	private JPasswordField pass = new JPasswordField();
	private JTextField sign = new JTextField();
	private JTextField phone = new JTextField();
	private JTextArea addr = new JTextArea();
	private JButton bnReturn = new JButton("现在登陆");
	private LoginFrame frame;

	public RegisterFrame(LoginFrame frame,String title, int width, int height) {
		super(title, width, height);
		resultId.setEditable(false);
		this.frame = frame;
		registerPane.add(this.getLabel("*用户名:"));

		userName.setAutoscrolls(false);
		registerPane.add(userName);

		registerPane.add(this.getLabel("*密码:"));
		registerPane.add(pass);
		registerPane.add(this.getLabel("签名:"));
		registerPane.add(sign);
		registerPane.add(this.getLabel("电话号码:"));
		registerPane.add(phone);
		registerPane.add(this.getLabel("地址:"));
		JScrollPane scrollPane = new JScrollPane(addr);
		addr.setLineWrap(true);
		registerPane.add(scrollPane);

		registerPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 10));

		bnRegister.setForeground(Color.WHITE);

		bnRegister.setFont(new Font("宋体", Font.BOLD, 20));
		bnRegister.setBackground(new Color(10, 170, 250, 150).darker());

		down.add(bnCancer);
		bnCancer.addActionListener(this);
		bnRegister.addActionListener(this);
		bnReset.addActionListener(this);
		down.add(Box.createGlue());
		down.add(bnRegister);
		down.add(Box.createGlue());
		down.add(bnReset);
		down.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		this.setCneterPane(rMainPane);

		JPanel rePane = new JPanel(new BorderLayout());
		rePane.add(registerPane, BorderLayout.CENTER);
		rePane.add(down, BorderLayout.SOUTH);

		JPanel resultBox = new JPanel(null);
		resultBox.setSize(100, 400);

		resultBox.add(lbeState);
		lbeState.setFont(new Font("宋体", Font.BOLD, 18));
		lbeState.setForeground(Color.RED);
		lbeState.setBounds(100, 50, 250, 40);
		resultId.setBounds(160, 140, 200, 40);
		resultId.setBorder(null);
		resultId.setOpaque(false);
		resultId.setFont(new Font("隶书", Font.BOLD, 25));
		resultId.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		resultId.setHorizontalAlignment(SwingConstants.CENTER);
		resultId.setForeground(Color.RED);
		resultBox.add(resultId);

		resultPane.add(resultBox, BorderLayout.CENTER);

		// resultPane.setPreferredSize(new Dimension(110,40));

		JPanel resultDown = factory.getBoxLayoutPanelByX();
		resultDown.add(Box.createGlue());
		resultDown.add(bnReturn);
		bnReturn.addActionListener(this);
		resultDown.add(Box.createGlue());
		resultDown.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

		resultPane.add(resultDown, BorderLayout.SOUTH);

		rMainPane.add(rePane);
		rMainPane.add(resultPane);
		cardLayout.addLayoutComponent(rePane, "registerPane");
		cardLayout.addLayoutComponent(resultPane, "resultPane");
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == bnRegister) {
			
			
			this.register();

			
		} else if (e.getSource() == bnCancer) {
			this.dispose();

		} else if (e.getSource() == bnReset) {
			this.clearAll();
		}else if(e.getSource()==bnReturn){
			String sid = resultId.getText().trim();
			frame.loginNow(sid);
			this.dispose();
		} 
		else {
			super.actionPerformed(e);
		}

	}

	private void clearAll() {
		if (userName.getText() != null && !userName.getText().equals(""))
			userName.setText("");
		if (pass.getPassword() != null && pass.getPassword().length != 0)
			pass.setText("");
		if (sign.getText() != null && !sign.getText().equals(""))
			sign.setText("");
		if (phone.getText() != null && !phone.getText().equals(""))
			phone.setText("");
		if (addr.getText() != null && !addr.getText().equals(""))
			addr.setText("");
		userName.requestFocus();
	}

	private JLabel getLabel(String str) {
		JLabel label = new JLabel(str);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		// label.setVerticalAlignment(SwingConstants.TOP);
		return label;
	}

	@Override
	public void closeHappen() {
		// TODO Auto-generated method stub

	}


	public void succed(int id) {
		this.lbeState.setText("申请成功，账号为：");
		this.resultId.setVisible(true);
		this.resultId.setText(id+"");
		
	}
	
	
	private void register(){
		String name = userName.getText();
		String mpass = null;
		char[] passchars = pass.getPassword();
		User u  = new User();
		
		if(name.equals("")|| name.trim().equals("")||name.length()<6){
			JOptionPane.showConfirmDialog(this, "用户名大于6位");
			userName.requestFocus();
			return;
		}
		u.setUserName(name);
		
		if(passchars != null && passchars.length>= 6){
			mpass = String.valueOf(pass.getPassword());
			u.setPassword(mpass);
		}else {
			JOptionPane.showConfirmDialog(this, "请确保密码位数大于6位");
			pass.requestFocus();
			return;
		}
		
		if(sign.getText().length()>200){
			JOptionPane.showConfirmDialog(this, "请确保签名字数小于200");
		}
		if(!sign.getText().trim().equals("")){
			u.setSign(sign.getText());
		}
		
		u.setAddr(addr.getText());
		u.setPhone(phone.getText());
		cardLayout.show(rMainPane, "resultPane");
		ClientMsg msg = new RegisterMsg(u);
		frame.sendMsg(msg);		
		
		
	}
	
	
	

}
