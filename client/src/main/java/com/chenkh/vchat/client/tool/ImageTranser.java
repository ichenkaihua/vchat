package com.chenkh.vchat.client.tool;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageTranser {
	public static BufferedImage bkg = null;
	public static BufferedImage grayImage = null;
	static {
		try {
			bkg = ImageIO.read(ImageTranser.class.getClassLoader().getResource(
                    "image/bkg.png"));

		} catch (IOException e) {
			System.out.println("读取过程发生错误!");
			e.printStackTrace();
		}
	}

	/**
	 * 从20,560开始，从bkg大图片得到指定宽度和高度的子图片，
	 * 
	 * @param width
	 *            :宽度
	 * @param height
	 *            ：高度
	 * @return ：Image对象
	 */

	public static Image getImage(int width, int height) {

		return bkg.getSubimage(0, 560, width, height);
	}

	/**
	 * 用指定的开始坐标和宽度高度，得到指定宽度和高度的Image对象
	 * 
	 * @param x
	 *            从x处开始截取
	 * @param y
	 *            从y处开始截取
	 * @param width
	 *            :宽度
	 * @param height
	 *            ：高度
	 * @return ： Image对象
	 */

	public static Image getImage(int x, int y, int width, int heith) {
		return bkg.getSubimage(x, y, width, heith);
	}

	/**
	 * 通过一张图片得到灰色图片
	 * 
	 * @param originalPic
	 *            源图片
	 * @return 灰色图片
	 */
	public static BufferedImage getGrayPicture(BufferedImage originalPic) {
		int imageWidth = originalPic.getWidth();
		int imageHeight = originalPic.getHeight();

		BufferedImage newPic = new BufferedImage(imageWidth, imageHeight,
				BufferedImage.TYPE_3BYTE_BGR);

		ColorConvertOp cco = new ColorConvertOp(
				ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
		cco.filter(originalPic, newPic);
		grayImage = newPic;
		return newPic;
	}

	public static BufferedImage getGrayPicture(Image image) {
		if (grayImage != null) {
			return grayImage;
		}
		Icon icon = new ImageIcon(image);
		BufferedImage bi = new BufferedImage(icon.getIconWidth(),
				icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D biContext = bi.createGraphics();
		biContext.drawImage(image, 0, 0, null);

		return getGrayPicture(bi);
	}

}
