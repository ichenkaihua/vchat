package com.chenkh.vchat.client.view.pane;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.plaf.basic.BasicTabbedPaneUI;

import com.chenkh.vchat.client.tool.ImageTranser;

public class TabbedPaneUI extends BasicTabbedPaneUI {

	public TabbedPaneUI() {
		super();
		// System.out.println(this.getBaseline(0));
		// this.se

	}

	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
			int x, int y, int w, int h, boolean isSelected) {
		if (isSelected) {
			g.setColor(Color.BLACK);
			g.drawLine(x, y, x + w, y);
			g.drawLine(x, y, x, y + h - 4);
			// g.drawLine(x, y, x, y+h);
			g.drawLine(x + w, y, x + w, y + h - 4);
		}

	}

	protected void paintTabBackground(Graphics g, int tabPlacement,
			int tabIndex, int x, int y, int w, int h, boolean isSelected) {
		if (isSelected) {

			g.drawImage(ImageTranser.getImage(w, h), x, y, w, h, null);
		} else {

			Color c = tabPane.getBackgroundAt(tabIndex);
			g.setColor(c);
			g.fill3DRect(x, y, w, h, true);
		}

	}

	@Override
	protected void paintContentBorder(Graphics g, int tabPlacement,
			int selectedIndex) {
	}

}
