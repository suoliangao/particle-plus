package top.suoliangao.particleplus.particle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.util.ByteArrayBuffer;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.fabricmc.fabric.impl.client.particle.ParticleFactoryRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import top.suoliangao.particleplus.util.MathUtil;
import top.suoliangao.particleplus.util.MathUtil.InvalidInputException;

import static java.lang.Math.sin;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import static java.lang.Math.cos;

public class ParticleGroup {
	
	protected double x, y, z;
	protected double[] basis;
	protected List<ParticleWrap> particles;
	protected Set<ParticleWrap> deadParticles;
	// misc settings
	public boolean showAxis = false;
	
	public ParticleGroup (double x, double y, double z) {
		System.out.println("Creating particle group.");
		this.x = x; this.y = y; this.z = z;
		this.resetTransform();
		this.particles = new ArrayList<>();
		this.deadParticles = new HashSet<>();
//		ByteArrayDataOutput b = ByteStreams.newDataOutput();
//		ByteBuffer.wrap(null);
	}
	// transform
	public void translate (double x, double y, double z) {
		double[] vec = MathUtil.transform(new double[] {x, y, z}, basis);
		this.x += vec[0];
		this.y += vec[1];
		this.z += vec[2];
	}
	
	public void move (double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	public void moveto (double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void rotate (double axisX, double axisY, double axisZ, double angle) {
		try {
			MathUtil.applyTransform(basis, MathUtil.rotationMatrix(angle, axisX, axisY, axisZ));
		} catch (InvalidInputException e) {}
	}
	/** Euler */
	public void rotate (double x, double y, double z) {
		double[] m = MathUtil.IDENTITY_MATRIX;
		try {
			if (Double.compare(0, y) == 0 && Double.compare(0, z) == 0) m = MathUtil.rotationMatirx(MathUtil.X_AXIS, x);
			else if (Double.compare(0, x) == 0 && Double.compare(0, z) == 0) m = MathUtil.rotationMatirx(MathUtil.Y_AXIS, y);
			else if (Double.compare(0, x) == 0 && Double.compare(0, y) == 0) m = MathUtil.rotationMatirx(MathUtil.Z_AXIS, z);
			else m = MathUtil.rotationMatrix(x, y, z);
			MathUtil.applyTransform(basis, m);
		} catch (InvalidInputException e) {}
	}
	
	public void lookAt (double x, double y, double z, double angle) {
		
	}
	
	public void tick () {
		if (this.showAxis) {
			double[] d = new double[3];
			// rand x axis
			d[0] = this.x + this.basis[0] * Math.random();
			d[1] = this.y + this.basis[1] * Math.random();
			d[2] = this.z + this.basis[2] * Math.random();
			MinecraftClient.getInstance().particleManager.addParticle(DustParticleEffect.DEFAULT, d[0], d[1], d[2], this.basis[0]/30, this.basis[1]/30, this.basis[2]/30).setColor(1, 0, 0);
			// rand x axis
			d[0] = this.x + this.basis[3] * Math.random();
			d[1] = this.y + this.basis[4] * Math.random();
			d[2] = this.z + this.basis[5] * Math.random();
			MinecraftClient.getInstance().particleManager.addParticle(DustParticleEffect.DEFAULT, d[0], d[1], d[2], this.basis[3]/30, this.basis[4]/30, this.basis[5]/30).setColor(0, 1, 0);
			// rand x axis
			d[0] = this.x + this.basis[6] * Math.random();
			d[1] = this.y + this.basis[7] * Math.random();
			d[2] = this.z + this.basis[8] * Math.random();
			MinecraftClient.getInstance().particleManager.addParticle(DustParticleEffect.DEFAULT, d[0], d[1], d[2], this.basis[6]/30, this.basis[7]/30, this.basis[8]/30).setColor(0, 0, 1);
		}
	}
	
	public void resetTransform () {
		this.basis = MathUtil.IDENTITY_MATRIX.clone();
	}
	// color
	public void setColor (float r, float g, float b) {
		this.particles.forEach(p -> {
			p.getParticle().setColor(r, g, b);
		});
	}
	// update
	public void buildParticleGeometry(VertexConsumer bufferBuilder, Camera camera, float f) {
		this.particles.forEach(p -> {
			p.getParticle().buildGeometry(bufferBuilder, camera, f);
		});
	}
//	public boolean dead () {
//		if (this.particles.size() == 0) return true;
//		for (int i = 0; i < this.particles.size(); i ++) {
//			if (!this.particles.get(i).dead()) return false;
//			this.deadParticles.add(this.particles.get(i));
//		}
//		if (this.particles.size() == 0) return true;
//		if (!this.deadParticles.isEmpty()) {
//			this.deadParticles.forEach(p -> {
//				this.particles.remove(p);
//			});
//			this.deadParticles.clear();
//		}
//		return true;
//	}
	
}
