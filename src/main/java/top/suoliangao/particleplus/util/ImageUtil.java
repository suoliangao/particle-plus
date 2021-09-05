package top.suoliangao.particleplus.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.minecraft.client.MinecraftClient;

public class ImageUtil {
	
	public static BufferedImage getTextImage (String text, String fontName, float fontSize) {
		// get width & height
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		Font font = ExternalResourceManager.getFont(fontName).deriveFont(fontSize);
		g2d.setFont(font);
//		new Font
		FontMetrics fm = g2d.getFontMetrics();
		int w = fm.stringWidth(text), h = fm.getHeight();
		g2d.dispose();
		// draw image
		img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		g2d = img.createGraphics();
		g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLACK);
        g2d.setBackground(new Color(0, 0, 0, 0));
        g2d.drawString(text, 0, fm.getAscent());
        g2d.dispose();
		return img;
	}
	
}
