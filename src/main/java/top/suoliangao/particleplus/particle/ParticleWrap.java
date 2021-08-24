package top.suoliangao.particleplus.particle;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.util.function.DoubleSupplier;

import org.spongepowered.asm.util.asm.ASM;

import it.unimi.dsi.fastutil.doubles.DoubleConsumer;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

/**
 * A wrapped particle class, get control over private fields in vanilla particle class.
 * @author SuoLianGao
 *
 */
public class ParticleWrap {
	
	private static final Class<?> clazz = Particle.class;
	private Particle particle;
	// field to modify in particle
	private Field x, y, z, vx, vy, vz, r, g, b, a, age, collideWithWorld;
	private int maxAge;
	// reflection get
	private <T> T get (Field f) {try {return (T)f.get(particle);} catch (Exception e) {e.printStackTrace(); return null;}}
	private double getDouble (Field f) {try {return f.getDouble(particle);} catch (Exception e) {e.printStackTrace(); return 0;}}
	private float getFloat (Field f) {try {return f.getFloat(particle);} catch (Exception e) {e.printStackTrace(); return 0;}}
	// reflection set
	private <T> void set (Field f, T value) {try {f.set(particle, value);} catch (Exception e) {e.printStackTrace();}}
	private void setDouble (Field f, double value) {try {f.setDouble(particle, value);} catch (Exception e) {e.printStackTrace();}}
	private void setFloat (Field f, float value) {try {f.setFloat(particle, value);} catch (Exception e) {e.printStackTrace();}}
	
	public ParticleWrap (Particle particle) {
		this.particle = particle;
		this.maxAge = this.particle.getMaxAge();
		try {
			// position
			this.x = clazz.getField("x");
			this.y = clazz.getField("y");
			this.z = clazz.getField("z");
			// velocity
			this.vx = clazz.getField("velocityX");
			this.vy = clazz.getField("velocityY");
			this.vz = clazz.getField("velocityZ");
			// color
			this.r = clazz.getField("colorRed");
			this.g = clazz.getField("colorGreen");
			this.b = clazz.getField("colorBlue");
			this.a = clazz.getField("colorAlpha");
			// other
			this.age = clazz.getField("age");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("[Particle Controller] " + this.getPosition());
	}
	// position
	public Vec3d getPosition () {
		return new Vec3d(this.getDouble(x), this.getDouble(y), this.getDouble(z));
	}
	
	public void setPos (double x, double y, double z) {
		particle.setPos(x, y, z);
	}
	
	public void setPos (Vec3d pos) {
		particle.setPos(pos.x, pos.y, pos.z);
	}
	// velocity
	public Vec3d getVelocity () {
		return new Vec3d(this.getDouble(vx), this.getDouble(vy), this.getDouble(vz));
	}
	
	public void setVelocity (double vx, double vy, double vz) {
		setDouble(this.vx, vx); setDouble(this.vy, vx); setDouble(this.vz, vz);
	}
	
	public void setVelocity (Vec3d pos) {
		this.setVelocity(pos.x, pos.y, pos.z);
	}
	// color
	public Vec3f getColor () {
		return new Vec3f (getFloat(r), getFloat(g), getFloat(b));
	}
	
	public float getAlpha () {
		return getFloat(a);
	}
	
	public void setColor (float r, float g, float b) {
		particle.setColor(r, g, b);
	}
	
	public void setAlpha (float a) {
		setFloat(this.a, a);
	}
	// age
	public int getAge () {
		try {
			return this.age.getInt(this.particle);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getMaxAge () {
		return this.maxAge;
	}
	
	public void setAge (int age) {
		try {
			this.age.setInt(this.particle, age);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setMaxAge (int maxAge) {
		particle.setMaxAge(maxAge);
	}
}
