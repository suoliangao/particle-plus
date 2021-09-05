package top.suoliangao.particleplus.particle;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.util.function.DoubleSupplier;

import org.spongepowered.asm.util.asm.ASM;

import it.unimi.dsi.fastutil.doubles.DoubleConsumer;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
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
	
	private class ReflectionHelper {
		
	}
	
	private Particle particle;
	private double x, y, z, worldX, worldY, worldZ;
	ParticleGroup group;
	private int maxAge;
	
	public ParticleWrap (Particle particle) {
		this.particle = particle;
		this.maxAge = this.particle.getMaxAge();
	}
	
	public double[] getPos () {
		return new double[] {this.x, this.y, this.z};
	}
	
	public void setPos (double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getWorldX () {return this.worldX;}
	public double getWorldY () {return this.worldY;}
	public double getWorldZ () {return this.worldZ;}
//	public double[] getWroldPos () {return new double[] {this.worldX, this.worldY, this.worldZ};}
	
	public void setWorldPos (double x, double y, double z) {
		this.worldX = x;
		this.worldY = y;
		this.worldZ = z;
		particle.setPos(x, y, z);
	}
	
	public Particle getParticle () {return this.particle;}
}
