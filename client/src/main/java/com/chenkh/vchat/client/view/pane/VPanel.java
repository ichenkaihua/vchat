package com.chenkh.vchat.client.view.pane;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class VPanel extends JPanel {
	
	
	public VPanel(LayoutManager layout){
		super(layout);
	}
	
	
	public VPanel(){
		super();
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		//
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		GradientPaint background = new GradientPaint(0f, 0f, new Color(0,
				170, 255, 140).darker(), (float) this.getWidth(),
				(float) getHeight(), new Color(10, 170, 250, 150).darker());
		g2.setPaint(background);
		g2.fillRect(0, 0, getWidth(), this.getHeight());

	}

}
