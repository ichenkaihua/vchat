package com.chenkh.vchat.client.frame.chat;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTabbedPane;

public class VTabbedPane extends JTabbedPane {
	private ChatFrame frame;
	private Color msgColor = Color.YELLOW;
	private Color normalColor = new Color(184,207,229);
	
	

	public VTabbedPane(ChatFrame frame) {
		super();
		this.frame = frame;

	}

	/**
	 * 增加一个选项卡
	 */
	public void addTab(Tab tab) {
		TabPane tapPane = tab.getTabPane();
		TabContenPane contenPane = tab.getContenPane();

		this.add(contenPane, tapPane.getName());
		int index = this.indexOfComponent(contenPane);
		this.setTabComponentAt(index, tapPane);

	}

	/**
	 * 移除一个选项卡
	 */
	public void removeTab(Tab tab) {

		TabContenPane contenPane = tab.getContenPane();
		this.remove(contenPane);

	}

	/**
	 * 设置选定一个选项卡
	 * 
	 * @param tab
	 */
	public void setSelectTab(Tab tab) {
		TabContenPane contenPane = tab.getContenPane();
		this.setSelectedComponent(contenPane);
		frame.showFrame();

	}

	@Override
	public void setSelectedComponent(Component c) {

		super.setSelectedComponent(c);

		int index = this.getSelectedIndex();
		if (index != -1) {
			frame.setTitle(this.getTitleAt(index), true);
			if(this.getBackgroundAt(index).equals(msgColor)){
				this.setBackgroundAt(index, normalColor);
			}
			
		}
		
		

	}

	public void recivedMsg(Tab tab) {

		TabContenPane contenPane = tab.getContenPane();

		int index = this.indexOfComponent(contenPane);
		if (index != -1 && index != this.getSelectedIndex()) {
			
			
			this.setBackgroundAt(index,this.msgColor);
			
		}

	}

}
