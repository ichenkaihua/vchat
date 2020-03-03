package com.chenkh.vchat.client.frame;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.image4j.codec.ico.ICODecoder;
import com.chenkh.vchat.client.enu.LoginState;
import com.chenkh.vchat.base.bean.VState;

/**
 * 图片的常量类，主要负责载入图片供程序使用
 * 
 * @author Administrator
 * 
 */
public class ImageConstance {
	/**
	 * 鼠标进入按钮的图片
	 */
	public static Image login_enter = null;
	/**
	 * 鼠标不进入按钮的图片
	 */
	public static Image login_normal = null;
	/**
	 * 框架关闭按钮图片
	 */
	public static Image frame_close_normal = null;
	public static Image frame_close_highlight = null;
	public static Image frame_close_down = null;

	/**
	 * 框架最小化按钮图片
	 */
	public static Image frame_min_normal = null;
	public static Image frame_min_highlight = null;
	public static Image frame_min_down = null;

	public static Image frame_max_normal;
	public static Image frame_max_highlight;
	public static Image frame_max_down;

	public static Image frame_restore_normal;
	public static Image frame_restore_highlight;
	public static Image frame_restore_down;

	/**
	 * 登陆状态的几个图标
	 */
	public static Map<LoginState, Image> tray_login_state = new HashMap<LoginState, Image>();
	/**
	 * 程序状态图标
	 */
	public static Map<VState, Image> tray_vchat_state = new HashMap<VState, Image>();
	/**
	 * 总背景图片，其他窗口背景图片都是剪切于他
	 */
	public static Image login_panel_bkg = null;
	/**
	 * 登录头像
	 */
	public static Image login_head = null;
	/**
	 * 登录进度条
	 */
	public static Image login_loading = null;
	/**
	 * 用户头像
	 */
	public static Image userImage = null;
	/**
	 * 好友头像
	 */
	public static Image head = null;
	/**
	 * 分组
	 */
	public static Image tip_0 = null;
	public static Image tip_90 = null;
	/**
	 * 在线状态图标
	 */
	public static EnumMap<VState, Image> states = null;
	/**
	 * 消息盒子
	 */
	public static Image msgbox_l;
	/**
	 * 消息盒子
	 */
	public static Image msgbox;

	public static Image icon_search_normal;

	public static Image main_search_delete;

	public static Image main_search_enter;

	public static Image menu_btn_normal;
	public static Image menu_btn_highlight;

	public static Image search_normal;

	public static Image tools;

	public static Image blank;

	public static Image msgBoxSet_down;
	public static Image msgBoxSet_hover;
	public static Image msgBoxSet_normal;

	public static Image delbutton_normal;
	public static Image delbutton_hover;
	public static Image delbutton_down;

	public static Image head_mini;

	public static Image face;
	public static Image font;
	public static Image register;
	public static Image sendpic;

	public static Image max_normal;
	public static Image max_full;
	public static Image ingore;

	public static Image font_bold_highlight;

	public static Image font_bold_normal;

	public static Image font_bold_push;

	public static Image font_color_normal;
	public static Image font_color_highlight;
	public static Image font_color_push;

	public static Image font_italic_normal;

	public static Image font_italic_highlight;
	public static Image font_italic_push;

	public static Image font_underline_normal;
	public static Image font_underline_highlight;
	public static Image font_underline_push;

	public static Image textmodel;

	public static Image font_image;
	public static Image head_stand;
	static {

		head_stand = font_image = Toolkit
				.getDefaultToolkit()
				.getImage(
						ImageConstance.class
								.getClassLoader()
								.getResource(
										"image/head/head_stand.png"));

		try {

			font_image = Toolkit
					.getDefaultToolkit()
					.getImage(
							ImageConstance.class
									.getClassLoader()
									.getResource(
											"image/chatframe/aio_quickbar_sysfont_tab_button_push.png"));

			font_bold_highlight = Toolkit
					.getDefaultToolkit()
					.getImage(
							ImageConstance.class
									.getClassLoader()
									.getResource(
											"image/chatframe/aio_quickbar_sysfont_bold_highlight.png"));

			font_bold_normal = Toolkit
					.getDefaultToolkit()
					.getImage(
							ImageConstance.class
									.getClassLoader()
									.getResource(
											"image/chatframe/aio_quickbar_sysfont_bold_normal.png"));

			font_bold_push = Toolkit
					.getDefaultToolkit()
					.getImage(
							ImageConstance.class
									.getClassLoader()
									.getResource(
											"image/chatframe/aio_quickbar_sysfont_bold_push.png"));

			font_color_highlight = Toolkit
					.getDefaultToolkit()
					.getImage(
							ImageConstance.class
									.getClassLoader()
									.getResource(
											"image/chatframe/aio_quickbar_sysfont_color_highlight.png"));
			font_color_normal = Toolkit
					.getDefaultToolkit()
					.getImage(
							ImageConstance.class
									.getClassLoader()
									.getResource(
											"image/chatframe/aio_quickbar_sysfont_color_normal.png"));
			font_color_push = Toolkit
					.getDefaultToolkit()
					.getImage(
							ImageConstance.class
									.getClassLoader()
									.getResource(
											"image/chatframe/aio_quickbar_sysfont_color_push.png"));

			font_italic_highlight = Toolkit
					.getDefaultToolkit()
					.getImage(
							ImageConstance.class
									.getClassLoader()
									.getResource(
											"image/chatframe/aio_quickbar_sysfont_italic_highlight.png"));
			font_italic_normal = Toolkit
					.getDefaultToolkit()
					.getImage(
							ImageConstance.class
									.getClassLoader()
									.getResource(
											"image/chatframe/aio_quickbar_sysfont_italic_normal.png"));
			font_italic_push = Toolkit
					.getDefaultToolkit()
					.getImage(
							ImageConstance.class
									.getClassLoader()
									.getResource(
											"image/chatframe/aio_quickbar_sysfont_italic_push.png"));

			font_underline_highlight = Toolkit
					.getDefaultToolkit()
					.getImage(
							ImageConstance.class
									.getClassLoader()
									.getResource(
											"image/chatframe/aio_quickbar_sysfont_underline_highlight.png"));
			font_underline_normal = Toolkit
					.getDefaultToolkit()
					.getImage(
							ImageConstance.class
									.getClassLoader()
									.getResource(
											"image/chatframe/aio_quickbar_sysfont_underline_normal.png"));
			font_underline_push = Toolkit
					.getDefaultToolkit()
					.getImage(
							ImageConstance.class
									.getClassLoader()
									.getResource(
											"image/chatframe/aio_quickbar_sysfont_underline_push.png"));
			textmodel = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/chatframe/aio_quickbar_textmodel.png"));

			ingore = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/menu/ignoreMessageIcon.png"));

			max_normal = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/button/max_normal.jpg"));

			max_full = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/button/max_full.jpg"));

			face = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/chatframe/aio_quickbar_face.png"));
			font = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/chatframe/aio_quickbar_font.png"));
			register = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/chatframe/aio_quickbar_register.png"));
			sendpic = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/chatframe/aio_quickbar_sendpic.png"));

			head_mini = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/head/head_mini.png"));

			delbutton_normal = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/menu/delbutton_normal.png"));
			delbutton_hover = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/menu/delbutton_highlight.png"));
			delbutton_down = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/menu/delbutton_down.png"));

			msgBoxSet_down = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/menu/msgboxset_down.png"));
			msgBoxSet_hover = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/menu/msgboxset_hover.png"));
			msgBoxSet_normal = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/menu/msgboxset_normal.png"));

			menu_btn_normal = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/menu/menu_btn_normal.png"));
			menu_btn_highlight = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/menu/menu_btn_highlight.png"));
			search_normal = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/menu/search_normal.png"));
			tools = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/menu/tools.png"));

			icon_search_normal = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/button/icon_search_normal.png"));
			main_search_delete = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/button/main_search_deldown.png"));
			main_search_enter = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/button/main_search_enter.png"));
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("路径出错，请检查");
		}

	}

	static {
		msgbox = Toolkit.getDefaultToolkit().getImage(
				ImageConstance.class.getClassLoader().getResource(
						"image/button/msgbox.png"));
		msgbox_l = Toolkit.getDefaultToolkit().getImage(
				ImageConstance.class.getClassLoader().getResource(
						"image/button/msgbox_l.png"));

		states = new EnumMap<VState, Image>(VState.class);
		Image image1 = Toolkit.getDefaultToolkit().getImage(
				ImageConstance.class.getClassLoader().getResource(
						"image/mainpane/away.png"));
		states.put(VState.away, image1);
		Image image2 = Toolkit.getDefaultToolkit().getImage(
				ImageConstance.class.getClassLoader().getResource(
						"image/mainpane/busy.png"));
		states.put(VState.busy, image2);
		Image image3 = Toolkit.getDefaultToolkit().getImage(
				ImageConstance.class.getClassLoader().getResource(
						"image/mainpane/imoffline.png"));
		states.put(VState.offline, image3);
		Image image4 = Toolkit.getDefaultToolkit().getImage(
				ImageConstance.class.getClassLoader().getResource(
						"image/mainpane/imonline.png"));
		states.put(VState.imonline, image4);
		Image image5 = Toolkit.getDefaultToolkit().getImage(
				ImageConstance.class.getClassLoader().getResource(
						"image/mainpane/invisible.png"));
		states.put(VState.invisible, image5);
		Image image6 = Toolkit.getDefaultToolkit().getImage(
				ImageConstance.class.getClassLoader().getResource(
						"image/mainpane/mute.png"));
		states.put(VState.mute, image6);
		Image image7 = Toolkit.getDefaultToolkit().getImage(
				ImageConstance.class.getClassLoader().getResource(
						"image/mainpane/Qme.png"));
		states.put(VState.Qme, image7);
	}

	static {
		tip_0 = Toolkit.getDefaultToolkit().getImage(
				ImageConstance.class.getClassLoader().getResource(
						"image/tip/0.png"));
		tip_90 = Toolkit.getDefaultToolkit().getImage(
				ImageConstance.class.getClassLoader().getResource(
						"image/tip/90.png"));

		login_loading = Toolkit.getDefaultToolkit().getImage(
				ImageConstance.class.getClassLoader().getResource(
						"image/button/loading.gif"));
		login_head = Toolkit.getDefaultToolkit().getImage(
				ImageConstance.class.getClassLoader().getResource(
						"image/head/login_head.jpg"));

		login_panel_bkg = Toolkit.getDefaultToolkit().getImage(
				ImageConstance.class.getClassLoader().getResource(
						"image/login_panel_bkg.jpg"));

		login_enter = Toolkit.getDefaultToolkit().getImage(
				ImageConstance.class.getClassLoader().getResource(
						"image/button/login_enter.png"));
		login_normal = Toolkit.getDefaultToolkit().getImage(
				ImageConstance.class.getClassLoader().getResource(
						"image/button/login_normal.png"));
		try {
			frame_close_normal = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/basic/btn_close_normal.png"));

			frame_close_highlight = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/basic/btn_close_highlight.png"));

			frame_close_down = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/basic/btn_close_down.png"));

			frame_min_normal = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/basic/btn_mini_normal.png"));

			frame_min_highlight = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/basic/btn_mini_highlight.png"));

			frame_min_down = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/basic/btn_mini_down.png"));

			frame_max_normal = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/basic/btn_max_normal.png"));
			frame_max_highlight = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/basic/btn_max_highlight.png"));
			frame_max_down = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/basic/btn_max_down.png"));
			frame_restore_normal = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/basic/btn_restore_normal.png"));
			frame_restore_highlight = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/basic/btn_restore_highlight.png"));
			frame_restore_down = Toolkit.getDefaultToolkit().getImage(
					ImageConstance.class.getClassLoader().getResource(
							"image/basic/btn_restore_down.png"));

		} catch (NullPointerException e) {
			System.out.println(e.getMessage());

		}
		userImage = Toolkit.getDefaultToolkit().getImage(
				ImageConstance.class.getClassLoader().getResource(
						"image/head/userImage.jpg"));
		head = Toolkit.getDefaultToolkit().getImage(
				ImageConstance.class.getClassLoader().getResource(
						"image/head/head.png"));

	}
	static {
		try {
			List<BufferedImage> blankb = ICODecoder.read(ImageConstance.class
					.getClassLoader().getResourceAsStream(
							"image/trayicon/blank.ico"));
			blank = blankb.get(0);

			List<BufferedImage> image1 = ICODecoder.read(ImageConstance.class
					.getClassLoader().getResourceAsStream(
							"image/trayicon/away.ico"));
			tray_vchat_state.put(VState.away, image1.get(0));

			List<BufferedImage> image2 = ICODecoder.read(ImageConstance.class
					.getClassLoader().getResourceAsStream(
							"image/trayicon/busy.ico"));
			tray_vchat_state.put(VState.busy, image2.get(0));
			List<BufferedImage> image3 = ICODecoder.read(ImageConstance.class
					.getClassLoader().getResourceAsStream(
							"image/trayicon/imonline.ico"));
			tray_vchat_state.put(VState.imonline, image3.get(0));

			List<BufferedImage> image4 = ICODecoder.read(ImageConstance.class
					.getClassLoader().getResourceAsStream(
							"image/trayicon/invisible.ico"));
			tray_vchat_state.put(VState.invisible, image4.get(0));

			List<BufferedImage> image5 = ICODecoder.read(ImageConstance.class
					.getClassLoader().getResourceAsStream(
							"image/trayicon/mute.ico"));
			tray_vchat_state.put(VState.mute, image5.get(0));

			List<BufferedImage> image6 = ICODecoder.read(ImageConstance.class
					.getClassLoader().getResourceAsStream(
							"image/trayicon/offline.ico"));
			tray_vchat_state.put(VState.offline, image6.get(0));

			List<BufferedImage> image7 = ICODecoder.read(ImageConstance.class
					.getClassLoader().getResourceAsStream(
							"image/trayicon/Qme.ico"));
			tray_vchat_state.put(VState.Qme, image7.get(0));

		} catch (IOException e) {
			System.out.println("载入图片错误");

			e.printStackTrace();
		}
	}
	static {

		try {
			//ImageConstance.class.getResourceAsStream("image/")

			List<BufferedImage> image1 = ICODecoder.read(ImageConstance.class
					.getResourceAsStream(
							"/image/trayicon/loginstate/Loading_1.ico"));

			tray_login_state.put(LoginState.Loading_1, image1.get(0));

			List<BufferedImage> image2 = ICODecoder.read(ImageConstance.class
					.getClassLoader().getResourceAsStream(
							"image/trayicon/loginstate/Loading_2.ico"));
			tray_login_state.put(LoginState.Loading_2, image2.get(0));

			List<BufferedImage> image3 = ICODecoder.read(ImageConstance.class
					.getClassLoader().getResourceAsStream(
							"image/trayicon/loginstate/Loading_3.ico"));
			tray_login_state.put(LoginState.Loading_3, image3.get(0));

			List<BufferedImage> image4 = ICODecoder.read(ImageConstance.class
					.getClassLoader().getResourceAsStream(
							"image/trayicon/loginstate/Loading_4.ico"));
			tray_login_state.put(LoginState.Loading_4, image4.get(0));

			List<BufferedImage> image5 = ICODecoder.read(ImageConstance.class
					.getClassLoader().getResourceAsStream(
							"image/trayicon/loginstate/Loading_5.ico"));
			tray_login_state.put(LoginState.Loading_5, image5.get(0));

			List<BufferedImage> image6 = ICODecoder.read(ImageConstance.class
					.getClassLoader().getResourceAsStream(
							"image/trayicon/loginstate/Loading_6.ico"));
			tray_login_state.put(LoginState.Loading_6, image6.get(0));

		} catch (IOException e) {
			System.out.println("载入登录状态图标出错");
			e.printStackTrace();
		}

	}

}
