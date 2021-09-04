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
	
	public Particle getParticle () {return this.particle;}
}
