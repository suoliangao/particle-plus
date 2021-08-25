package top.suoliangao.particleplus.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import net.minecraft.client.MinecraftClient;

/**
 * Manage mod's external resources, including external scripts, images, particle model files etc.
 * @author SuoLianGao
 *
 */
public class ExternalResourceManager {

	public static File FONT_DIR, SCRIPT_DIR, IMAGE_DIR;
//	public static Map<String, Font> loadedFonts
	
	public static File getModResourceDir () {
		return new File(MinecraftClient.getInstance().runDirectory, "particleplus");
	}
	
	public static final void initResources () {
		FONT_DIR = new File (getModResourceDir(), "fonts");
		SCRIPT_DIR = new File (getModResourceDir(), "scripts");
		IMAGE_DIR = new File (getModResourceDir(), "images");
		reload ();
	}
	
	public static void reload () {
		// create font dir
		if (!FONT_DIR.exists()) FONT_DIR.mkdirs();
		if (FONT_DIR.listFiles().length == 0) {
			System.out.println("Font directory is empty, extracting internal resources.");
			try {
				File f = new File (FONT_DIR, "TIMES.TTF");
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
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Fail to exteact resources.");
			}
		}
	}
	
	public static Font getFont (String name) {
		try {
			File f =  new File (FONT_DIR, name);
//			System.out.println("Font flie: " +  f.getAbsolutePath() + f.exists());
			return Font.createFont(Font.PLAIN, f);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Fail to load font.");
			return null;
		}
	}
}
