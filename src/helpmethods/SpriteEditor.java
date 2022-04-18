package helpmethods;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SpriteEditor {
	public static BufferedImage getRotatedImg(BufferedImage img, int angle) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage newImg = new BufferedImage(w, h, img.getType());
		Graphics2D g2 = newImg.createGraphics();
		g2.rotate(Math.toRadians(angle), w / 2, h / 2);
		g2.drawImage(img, 0, 0, null);
		g2.dispose();
		
		return newImg;
	}
	
	public static BufferedImage buildImg(BufferedImage[] imgs) {
		int w = imgs[0].getWidth();
		int h = imgs[0].getHeight();
		BufferedImage newImg = new BufferedImage(w, h, imgs[0].getType());
		Graphics2D g2 = newImg.createGraphics();
		for (BufferedImage img : imgs) {
			g2.drawImage(img, 0, 0, null);
		}
		g2.dispose();
		return newImg;
	}
	
	public static BufferedImage getRotatedLayeredImg(BufferedImage[] imgs, int angle, int index) {
		int w = imgs[0].getWidth();
		int h = imgs[0].getHeight();
		BufferedImage newImg = new BufferedImage(w, h, imgs[0].getType());
		Graphics2D g2 = newImg.createGraphics();
		
		for (int i = 0; i < imgs.length; i++) {
			if(index == i) {
				g2.rotate(Math.toRadians(angle), w / 2, h / 2);
			}
			g2.drawImage(imgs[i], 0, 0, null);
			if (index == i) {
				g2.rotate(Math.toRadians(angle), w / 2, h / 2);
			}
		}
		g2.dispose();
		return newImg;
	}
		
		public static BufferedImage[] getRotatedLayeredImg(BufferedImage[] imgs, BufferedImage secondImg, int angle) {
			int w = imgs[0].getWidth();
			int h = imgs[0].getHeight();
			BufferedImage[] aniArr = new BufferedImage[imgs.length];
			for (int i = 0; i < imgs.length; i++) {
				BufferedImage newImg = new BufferedImage(w, h, imgs[0].getType());
				Graphics2D g2 = newImg.createGraphics();
				g2.drawImage(imgs[i], 0, 0, null);
				g2.rotate(Math.toRadians(angle), w / 2, h / 2);
				g2.drawImage(secondImg, 0, 0, null);
				g2.dispose();
				aniArr[i] = newImg;
			}
			return aniArr;
		
	}
	
}
