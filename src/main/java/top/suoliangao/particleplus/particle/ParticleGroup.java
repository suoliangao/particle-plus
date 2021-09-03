package top.suoliangao.particleplus.particle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

public class ParticleGroup {
	
	protected double x, y, z;
	protected Vec3d vecX, vecY, vecZ;
	protected List<ParticleWrap> particles;
	protected Set<ParticleWrap> deadParticles;
	
	public ParticleGroup (double x, double y, double z) {
		this.x = x; this.y = y; this.z = z;
		this.resetTransform();
		this.particles = new ArrayList<>();
		this.deadParticles = new HashSet<>();
		ParticlePlusManager.addGroup(this);
	}
	
	public void translate (Vec3d v) {
		
	}
	
	public void move (Vec3d v) {
		this.x += v.x;
		this.y += v.y;
		this.z += v.z;
	}
	
	public void moveto (Vec3d target) {
		this.x = target.x;
		this.y = target.y;
		this.z = target.z;
	}
	
	public void rotate (Vec3d axis, double angle) {
		
	}
	
	public void rotate (Quaternion q) {
		
	}
	
	public void rotate (double x, double y, double z) {
		
	}
	
	public void lookAt (Vec3d direction, double angle) {
		
	}
	
	public void tick () {
		
	}
	
	public void resetTransform () {
		this.vecX = new Vec3d(1,0,0);
		this.vecY = new Vec3d(0,1,0);
		this.vecZ = new Vec3d(0,0,1);
	}
	
	public boolean dead () {
		if (this.particles.size() == 0) return true;
		for (int i = 0; i < this.particles.size(); i ++) {
			if (!this.particles.get(i).dead()) return false;
			this.deadParticles.add(this.particles.get(i));
		}
		if (this.particles.size() == 0) return true;
		if (!this.deadParticles.isEmpty()) {
			this.deadParticles.forEach(p -> {
				this.particles.remove(p);
			});
			this.deadParticles.clear();
		}
		return true;
	}
	
}
