package top.suoliangao.particleplus.particle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ParticleGroup {
	
	protected double x,y,z;
	protected List<ParticleWrap> particles;
	protected Set<ParticleWrap> deadParticles;
	
	public ParticleGroup (double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.particles = new ArrayList<>();
		this.deadParticles = new HashSet<>();
		ParticlePlusManager.addGroup(this);
	}
	
	public void tick () {
		
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
