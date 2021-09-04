package top.suoliangao.particleplus.particle;

import java.awt.Color;
import java.awt.image.BufferedImage;

import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;

public class ImageParticleGroup extends ParticleGroup {
	
	private int height, width;
	
	public ImageParticleGroup (ParticleManager pm, ParticleEffect pe, double x, double y, double z, BufferedImage img) {
		super (x,y,z);
		this.height = img.getHeight();
		this.width = img.getWidth();
		long start_t = System.currentTimeMillis();
		System.out.println("Time cost: " + (System.currentTimeMillis() - start_t)/1000d + "seconds");
	}
	
	@Override
	public void tick() {
		
	}
	
}
