package top.suoliangao.particleplus.particle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.world.ClientWorld;

public class ParticlePlusManager {
	
	private static List<ParticleGroup> particleGroups;
	
	public static void init () {
		particleGroups = new ArrayList<>(); 
	}
	
	public static void addGroup (ParticleGroup group) {
		particleGroups.add(group);
	}
	
	public static void tick () {
		for (int i = 0; i < particleGroups.size(); i ++) {
			particleGroups.get(i).tick();
			if (particleGroups.get(i).dead()) {
				particleGroups.remove(i);
				i--;
			}
		};
	}

}
