package com.chenkh.vchat.client.frame.chat;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

public class MouseInputHandler extends MouseInputAdapter {
	private static final int CORNER_DRAG_WIDTH = 10;
	private static final int BORDER_DRAG_THICKNESS = 5;
	/** 是否可移动窗体 */
	private boolean isMovingWindow;
	private boolean dragging;
	private int dragCursor;
	private int dragOffsetX;
	private int dragOffsetY;
	private int dragWidth;
	private int dragHeight;
	private JFrame myFrame;

	public MouseInputHandler(JFrame myFrame) {
		this.myFrame = myFrame;
	}

	/**
	 * 只处理鼠标左键按下操作
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e)) {
			return;
		}

		Point dragWindowOffset = e.getPoint();
		Window win = (Window) e.getSource();
		this.dragging = true;

		if (win != null) {
			/** 将窗体置前 */
			win.toFront();
		}

		Frame frame = (win instanceof Frame) ? (Frame) win : null;
		int frameState = frame != null ? frame.getExtendedState()
				: Frame.NORMAL;

		if (((frame != null) && ((frameState & Frame.MAXIMIZED_BOTH) == 0))) {
			this.isMovingWindow = true;
			this.dragOffsetX = dragWindowOffset.x;
			this.dragOffsetY = dragWindowOffset.y;
		}

		if (!this.isMovingWindow && frame != null && frame.isResizable()
				&& ((frameState & Frame.MAXIMIZED_BOTH) == 0)) {
			this.dragOffsetX = dragWindowOffset.x;
			this.dragOffsetY = dragWindowOffset.y;
		}
		this.dragWidth = win.getWidth();
		this.dragHeight = win.getHeight();
		this.dragCursor = getCursor(win, e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e)) {
			return;
		}

		if ((this.dragCursor != Cursor.DEFAULT_CURSOR)
				&& (!this.myFrame.isValid())) {
			myFrame.validate();
		}

		this.dragging = false;
		this.isMovingWindow = false;
		this.dragCursor = Cursor.DEFAULT_CURSOR;
		mouseMoved(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Window win = (Window) e.getSource();
		Frame frame = (win instanceof Frame) ? (Frame) win : null;
		int cursor = getCursor(win, e);

		if ((cursor != 0)
				&& (win.getBounds().contains(e.getLocationOnScreen()))
				&& ((frame != null) && (frame.isResizable()) && ((frame
						.getExtendedState() & Frame.MAXIMIZED_BOTH) == 0))) {
			win.setCursor(Cursor.getPredefinedCursor(cursor));
		} else {
			win.setCursor(Cursor.getDefaultCursor());
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (!this.dragging) {
			Window win = (Window) e.getSource();
			win.setCursor(Cursor.getDefaultCursor());
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e)) {
			if (myFrame.getBounds().contains(e.getPoint())) {
				mouseMoved(e);
			} else {
				mouseExited(e);
			}

			return;
		}

		Window win = (Window) e.getSource();
		Point point = e.getPoint();

		if (this.isMovingWindow && this.dragCursor == Cursor.DEFAULT_CURSOR) {
			Point eventLocationOnScreen = e.getLocationOnScreen();
			win.setLocation(eventLocationOnScreen.x - this.dragOffsetX,
					eventLocationOnScreen.y - this.dragOffsetY);
		} else if (this.dragCursor != Cursor.DEFAULT_CURSOR) {
			Rectangle rect = win.getBounds();
			Rectangle startBounds = new Rectangle(rect);
			Dimension min = win.getMinimumSize();
			Dimension max = win.getMaximumSize();

			switch (this.dragCursor) {
			case Cursor.E_RESIZE_CURSOR:
				adjust(rect, min, max, 0, 0, point.x
						+ (this.dragWidth - this.dragOffsetX) - rect.width, 0);
				break;
			case Cursor.S_RESIZE_CURSOR:
				adjust(rect, min, max, 0, 0, 0, point.y
						+ (this.dragHeight - this.dragOffsetY) - rect.height);
				break;
			case Cursor.N_RESIZE_CURSOR:
				adjust(rect, min, max, 0, point.y - this.dragOffsetY, 0,
						-(point.y - this.dragOffsetY));
				break;
			case Cursor.W_RESIZE_CURSOR:
				adjust(rect, min, max, point.x - this.dragOffsetX, 0,
						-(point.x - this.dragOffsetX), 0);
				break;
			case Cursor.NE_RESIZE_CURSOR:
				adjust(rect, min, max, 0, point.y - this.dragOffsetY, point.x
						+ (this.dragWidth - this.dragOffsetX) - rect.width,
						-(point.y - this.dragOffsetY));
				break;
			case Cursor.SE_RESIZE_CURSOR:
				adjust(rect, min, max, 0, 0, point.x
						+ (this.dragWidth - this.dragOffsetX) - rect.width,
						point.y + (this.dragHeight - this.dragOffsetY)
								- rect.height);
				break;
			case Cursor.NW_RESIZE_CURSOR:
				adjust(rect, min, max, point.x - this.dragOffsetX, point.y
						- this.dragOffsetY, -(point.x - this.dragOffsetX),
						-(point.y - this.dragOffsetY));
				break;
			case Cursor.SW_RESIZE_CURSOR:
				adjust(rect, min, max, point.x - this.dragOffsetX, 0,
						-(point.x - this.dragOffsetX), point.y
								+ (this.dragHeight - this.dragOffsetY)
								- rect.height);
				break;
			}

			if (!rect.equals(startBounds)) {
				win.setBounds(rect);

				if (Toolkit.getDefaultToolkit().isDynamicLayoutActive()) {
					win.validate();
					myFrame.getRootPane().repaint();
				}
			}
		}
	}

	/**
	 * 鼠标左键点击事件
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e)) {
			return;
		}

		Window win = (Window) e.getSource();
		Frame frame = (win instanceof Frame) ? (Frame) win : null;
		Point point = e.getPoint();
		int clickCount = e.getClickCount();

		if ((clickCount % 2 == 0) && (point.x <= 22) && (point.y <= 22)
				&& (myFrame.getRootPane().getWindowDecorationStyle() != 0)) {
			myFrame.dispatchEvent(new WindowEvent(myFrame,
					WindowEvent.WINDOW_CLOSING));
			return;
		}

		if (frame == null) {
			return;
		}

		int state = frame.getExtendedState();
		Window fullWin = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().getFullScreenWindow();

		if ((frame.isResizable()) && (frame != fullWin)
				&& (clickCount % 2 == 0)) {
			if ((state & Frame.MAXIMIZED_BOTH) != 0) {
				frame.setExtendedState(state & 0xFFFFFFF9);
			} else {
				frame.setExtendedState(state | Frame.MAXIMIZED_BOTH);
			}
		}
	}

	private void adjust(Rectangle bounds, Dimension min, Dimension max,
			int deltaX, int deltaY, int deltaWidth, int deltaHeight) {
		bounds.x += deltaX;
		bounds.y += deltaY;
		bounds.width += deltaWidth;
		bounds.height += deltaHeight;

		if (min != null) {
			if (bounds.width < min.width) {
				int correction = min.width - bounds.width;

				if (deltaX != 0) {
					bounds.x -= correction;
				}

				bounds.width = min.width;
			}

			if (bounds.height < min.height) {
				int correction = min.height - bounds.height;

				if (deltaY != 0) {
					bounds.y -= correction;
				}

				bounds.height = min.height;
			}
		}

		if (max != null) {
			if (bounds.width > max.width) {
				int correction = max.width - bounds.width;

				if (deltaX != 0) {
					bounds.x -= correction;
				}

				bounds.width = max.width;
			}

			if (bounds.height > max.height) {
				int correction = max.height - bounds.height;

				if (deltaY != 0) {
					bounds.y -= correction;
				}

				bounds.height = max.height;
			}
		}
	}

	private int getCursor(Window win, MouseEvent e) {
		int winWidth = win.getBounds().width;
		int winHeight = win.getBounds().height;
		Point p = e.getPoint();// e.getLocationOnScreen();
		if (win.getBounds().contains(e.getLocationOnScreen())) {
			// 先判断四个角
			if (p.x - CORNER_DRAG_WIDTH < 0 && p.y - CORNER_DRAG_WIDTH < 0) {
				return Cursor.NW_RESIZE_CURSOR;
			} else if (p.x - (winWidth - CORNER_DRAG_WIDTH) > 0
					&& p.y - CORNER_DRAG_WIDTH < 0) {
				return Cursor.NE_RESIZE_CURSOR;
			} else if (p.x - CORNER_DRAG_WIDTH < 0
					&& p.y - (winHeight - CORNER_DRAG_WIDTH) > 0) {
				return Cursor.SW_RESIZE_CURSOR;
			} else if (p.x - (winWidth - CORNER_DRAG_WIDTH) > 0
					&& p.y - (winHeight - CORNER_DRAG_WIDTH) > 0) {
				return Cursor.SE_RESIZE_CURSOR;
			}

			// 判断是否在中间部分
			if ((p.x > BORDER_DRAG_THICKNESS && p.x < winWidth
					- BORDER_DRAG_THICKNESS)
					&& (p.y > BORDER_DRAG_THICKNESS && p.y < winHeight
							- BORDER_DRAG_THICKNESS)) {
				return Cursor.DEFAULT_CURSOR;
			} else {

				if (p.x - BORDER_DRAG_THICKNESS < 0) {
					return Cursor.W_RESIZE_CURSOR;
				} else if (p.y - BORDER_DRAG_THICKNESS < 0) {
					return Cursor.N_RESIZE_CURSOR;
				} else if (winWidth - BORDER_DRAG_THICKNESS > p.x) {
					return Cursor.S_RESIZE_CURSOR;
				} else {
					return Cursor.E_RESIZE_CURSOR;
				}
			}
		}

		return Cursor.DEFAULT_CURSOR;
	}
}