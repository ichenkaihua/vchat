package com.chenkh.vchat.client.tool;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Hard {
	public static final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	public static final Insets insert;
	public static final GraphicsConfiguration gcf;
	
	
	
	
	static{
		JFrame frame = new JFrame();
		gcf = frame.getGraphicsConfiguration();
		insert = Toolkit.getDefaultToolkit().getScreenInsets(gcf);
	}
	
	
	
	final public static  int getScreenBotton(){
		return screen.height-insert.bottom;
	}
	
	final public static int getInsertBotton(){
		return insert.bottom;
	}
	
	
	final public static  int getScreenWidht(){
		return screen.width;
	}
	
	final public static int getScreenHeight(){
		return screen.height;
	}
	
	

}
