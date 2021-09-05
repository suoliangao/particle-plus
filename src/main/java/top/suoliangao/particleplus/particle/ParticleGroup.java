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
import net.minecraft.client.particle.ParticleManager;
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
		this.particles = new ArrayList<>();
		this.deadParticles = new HashSet<>();
		this.resetTransform();
//		ByteArrayDataOutput b = ByteStreams.newDataOutput();
//		ByteBuffer.wrap(null);
	}
	
	public Vec3d getPosition () {
		return new Vec3d (this.x, this.y, this.z);
	}
	// transform
	public void translate (double x, double y, double z) {
		double[] vec = MathUtil.transform(new double[] {x, y, z}, basis);
		this.x += vec[0];
		this.y += vec[1];
		this.z += vec[2];
		repositionParticles ();
	}
	
	public void move (double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		repositionParticles ();
	}
	
	public void moveto (double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		repositionParticles ();
	}
	
	public void rotate (double axisX, double axisY, double axisZ, double angle) {
		try {
			MathUtil.applyTransform(basis, MathUtil.rotationMatrix(angle, axisX, axisY, axisZ));
			repositionParticles ();
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
			repositionParticles ();
		} catch (InvalidInputException e) {}
	}
	
//	public void lookAt (double x, double y, double z, double angle) {
//		try {
//			double[] axis = MathUtil.cross(basis[6], basis[7], basis[8], x, y, z);
//			double a = MathUtil.angle (basis[6], basis[7], basis[8], x, y, z);
//			MathUtil.applyTransform(basis, MathUtil.rotationMatrix(a, axis[0], axis[1], axis[2]));
//			MathUtil.applyTransform(basis, MathUtil.rotationMatrix(MathUtil.Z_AXIS, angle));
//			repositionParticles ();
//		} catch (InvalidInputException e) {}
//	}
	
	public void resetTransform () {
		this.basis = MathUtil.IDENTITY_MATRIX.clone();
		repositionParticles ();
	}
	
	public void repositionParticles () {
		if (this.particles.isEmpty()) return;
		double[] vecs = new double[this.particles.size() * 3];
		int i = 0;
		for (ParticleWrap p : this.particles) {
			vecs[i*3+0] = p.getWorldX();
			vecs[i*3+1] = p.getWorldY();
			vecs[i*3+2] = p.getWorldZ();
			i++;
		}
		vecs = MathUtil.transform(vecs, basis);
		i = 0;
		for (ParticleWrap p : this.particles) {
			p.setWorldPos(this.x + vecs[i*3+0], this.y + vecs[i*3+1], this.z + vecs[i*3+2]);
			i++;
		}
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
	
	public void tick () {
		if (this.showAxis) {
			showAxis ();
		}
	}
	
	private void showAxis () {
		ParticleManager pm = MinecraftClient.getInstance().particleManager;
		double s = 3; Particle p;
		double r = 0;
		double[] d = new double[3];
		// rand x axis
		r = Math.random();
		d[0] = this.x + this.basis[0] * r;
		d[1] = this.y + this.basis[1] * r;
		d[2] = this.z + this.basis[2] * r;
		p = pm.addParticle(DustParticleEffect.DEFAULT, d[0], d[1], d[2], basis[0]/s, basis[1]/s, basis[2]/s);
		p.setColor(1, 0, 0); p.setMaxAge(15);
		// rand x axis
		r = Math.random();
		d[0] = this.x + this.basis[3] * r;
		d[1] = this.y + this.basis[4] * r;
		d[2] = this.z + this.basis[5] * r;
		p = pm.addParticle(DustParticleEffect.DEFAULT, d[0], d[1], d[2], basis[3]/s, basis[4]/s, basis[5]/s);
		p.setColor(0, 1, 0); p.setMaxAge(15);
		// rand x axis
		r = Math.random();
		d[0] = this.x + this.basis[6] * r;
		d[1] = this.y + this.basis[7] * r;
		d[2] = this.z + this.basis[8] * r;
		p = pm.addParticle(DustParticleEffect.DEFAULT, d[0], d[1], d[2], basis[6]/s, basis[7]/s, basis[8]/s);
		p.setColor(0, 0, 1); p.setMaxAge(15);
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
