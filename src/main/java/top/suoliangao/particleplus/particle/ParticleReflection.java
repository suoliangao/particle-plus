package top.suoliangao.particleplus.particle;

import java.lang.reflect.Field;

import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.Vec3d;

public class ParticleReflection {
	// static fields
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
	
	private Particle p;
	public ParticleReflection (Particle p) {
		this.p = p;
	}
	// position
	/** Get x position */
	public double getX () {try {return x.getDouble(this.p);} catch (Exception e) {e.printStackTrace();return 0;}}
	/** Get y position */
	public double getY () {try {return y.getDouble(this.p);} catch (Exception e) {e.printStackTrace();return 0;}}
	/** Get z position */
	public double getZ () {try {return z.getDouble(this.p);} catch (Exception e) {e.printStackTrace();return 0;}}
	/** Get position of a particle as double array. */
	public double[] getPos () {
		try {
			return new double[] {x.getDouble(p), y.getDouble(p), z.getDouble(p)};
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/** Get position of a particle as Vec3d. */
	public Vec3d getVecPos () {
		try {
			return new Vec3d (x.getDouble(p), y.getDouble(p), z.getDouble(p));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
