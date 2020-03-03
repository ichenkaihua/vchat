package com.chenkh.vchat.client.view.pane;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import com.chenkh.vchat.client.tool.ImageTranser;


/**
 * 此类通过重写paintCompont()方法达到绘制特定的背景的功能
 * @author Administrator
 *
 */

public class ImagePanel extends JPanel {

	private Image background = null;
	private int height = -1;

	public ImagePanel(Image background) {
		super();
		this.background = background;
	}
	public ImagePanel(){
		super();
		
	}
	public ImagePanel(LayoutManager layout){
		super(layout);
	}
	
	public ImagePanel(Image background,LayoutManager layout){
		super(layout);
		this.background = background;
	}
	
	
	public ImagePanel (int height,LayoutManager layout){
		super(layout);
		this.height = height;
	}
	
	

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (background != null) {
			g.drawImage(background, 0, 0, this);
		}
		else {
			if(height > -1)g.drawImage(ImageTranser.getImage(this.getWidth(),height),0,0,this);
			else
			g.drawImage(ImageTranser.getImage(this.getWidth(),this.getHeight()),0,0,this);
		}

	}

}
