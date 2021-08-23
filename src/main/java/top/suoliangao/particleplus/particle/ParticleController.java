package top.suoliangao.particleplus.particle;

import java.lang.reflect.Field;
import java.util.function.DoubleSupplier;

import it.unimi.dsi.fastutil.doubles.DoubleConsumer;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.Vec3d;

public class ParticleController {
	
	private static final Class<?> clazz = Particle.class;
	private Particle particle;
	
	private Field x, y, z;
	private DoubleConsumer setX, setY, setZ;
	
	private <T> T get (Field f) {try {return (T)f.get(particle);} catch (Exception e) {e.printStackTrace(); return null;}}
	private double getDouble (Field f) {try {return f.getDouble(particle);} catch (Exception e) {e.printStackTrace(); return 0;}}
	
	public ParticleController (Particle particle) {
		this.particle = particle;
		try {
			this.x = clazz.getField("x");
			this.y = clazz.getField("y");
			this.z = clazz.getField("z");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("[Particle Controller] " + this.getPosition());
	}
	
	public Vec3d getPosition () {
		return new Vec3d(this.getDouble(x), this.getDouble(y), this.getDouble(z));
	}
	
	public void setPosition (double x, double y, double z) {
		
	}
	
	public void setPosition (Vec3d pos) {
		
	}

}
