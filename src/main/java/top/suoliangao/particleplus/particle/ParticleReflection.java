package top.suoliangao.particleplus.particle;

import java.lang.reflect.Field;

import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.Vec3d;

public class ParticleReflection {
	
	private ParticleReflection () {}
	
	public static final Class<Particle> clazz = Particle.class;
	public static Field x, y, z, vx, vy, vz, r, g, b, a, age, dead, collideWithWorld;
	
	public static void init () throws Exception {
		// position
		x = clazz.getField("x");
		y = clazz.getField("y");
		z = clazz.getField("z");
		// velocity
		vx = clazz.getField("velocityX");
		vy = clazz.getField("velocityY");
		vz = clazz.getField("velocityZ");
		// color
		r = clazz.getField("colorRed");
		g = clazz.getField("colorGreen");
		b = clazz.getField("colorBlue");
		a = clazz.getField("colorAlpha");
		// other
		age = clazz.getField("age");
		dead = clazz.getField("dead");
	}
	
	/** Get position of a particle */
	public static Vec3d getPos (Particle p) {
		try {
			return new Vec3d (x.getDouble(p), y.getDouble(p), z.getDouble(p));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
