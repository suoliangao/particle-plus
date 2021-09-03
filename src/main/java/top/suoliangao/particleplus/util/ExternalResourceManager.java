package top.suoliangao.particleplus.util;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import net.minecraft.client.MinecraftClient;

/**
 * Manage mod's external resources, including external scripts, images, particle model files etc.
 * @author SuoLianGao
 *
 */
public class ExternalResourceManager {

	public static File FONT_DIR, SCRIPT_DIR, IMAGE_DIR;
	private static final Map<String, Font> fonts = new HashMap<>();
	private static final Map<String, File> images = new HashMap<>();
//	private static final Map<String, File> scripts = new HashMap<>();
	
	public static Set<String> getFonts () {return fonts.keySet();}
	public static Set<String> getImages () {return images.keySet();}
	
	public static File getModResourceDir () {
		return new File(MinecraftClient.getInstance().runDirectory, "particleplus");
	}
	
	public static final void initResources () {
		FONT_DIR = new File (getModResourceDir(), "fonts");
		IMAGE_DIR = new File (getModResourceDir(), "images");
		reload ();
	}
	
	public static void reload () {
		fonts.clear();
		images.clear();
		reloadFont();
		reloadImage();
	}
	
	private static void reloadFont () {
		if (!FONT_DIR.exists()) FONT_DIR.mkdirs();
		try {
			File f = new File (FONT_DIR, "TIMES.TTF");
			if (FONT_DIR.listFiles().length == 0) {
				System.out.println("Font directory is empty, extracting internal resources.");
				f.createNewFile();
				InputStream is = ExternalResourceManager.class.getResourceAsStream("/assets/particleplus/defaultres/TIMES.TTF");
				OutputStream os = new FileOutputStream(f);
				byte[] buf = new byte[1024];
				int len;
				while ((len = is.read(buf)) != -1) {
					os.write(buf, 0, len);
					os.flush();
				}
				os.close();
				is.close();
			}
			System.out.println("Loading fonts...");
			for (File f_ : FONT_DIR.listFiles()) fonts.put(f_.getName(), Font.createFont(Font.PLAIN, f_));
			System.out.println(fonts.size() + " successfully loaded.");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Fail to exteact resources.");
		}
		
	}
	
	private static void reloadImage () {
		if (!IMAGE_DIR.exists()) FONT_DIR.mkdirs();
		for (File f_ : FONT_DIR.listFiles()) images.put(f_.getName(), f_);
	}
	
	public static BufferedImage getImage (String name) {
		try {
			return ImageIO.read(images.get(name));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Font getFont (String name) {
		return fonts.get(name);
	}
}
