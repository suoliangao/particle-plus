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
		for (int i = 0; i < this.width; i ++) {
			for (int j = 0; j < this.height; j ++) {
				Color c = new Color(img.getRGB(i, j), true);
				if(c.getAlpha() != 0) {
					ParticleWrap p = new ParticleWrap (pm.addParticle(pe, this.x + i/10f, this.y + (this.height - j)/10f, this.z, 0,0,0));
					p.setColor(0x66/255f, 0xcc/255f, 0xff/255f);
					p.setAlpha(c.getAlpha());
					p.setAge(200);
					p.setMaxAge(400);
					this.particles.add(p);
				}
			}
		}
		System.out.println("Time cost: " + (System.currentTimeMillis() - start_t)/1000d + "seconds");
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		for (int i = 0; i < this.particles.size(); i ++) {
			this.particles.get(i).setColor(0x66/255f, 0xcc/255f, 0xff/255f);
		};
	}
	
}
