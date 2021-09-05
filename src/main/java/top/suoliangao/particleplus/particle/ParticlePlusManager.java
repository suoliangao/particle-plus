package top.suoliangao.particleplus.particle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;

public class ParticlePlusManager {//extends ParticleManager {
	
	private Map<String, ParticleGroup> particleGroups;
	
	public ParticlePlusManager() {
//		super(MinecraftClient.getInstance().world, MinecraftClient.getInstance().getTextureManager());
		// TODO Auto-generated constructor stub
		this.particleGroups = new HashMap<>();
	}
	
	public void addParticleGroup (String name, ParticleGroup group) {
		group.showAxis = true;
		if (this.particleGroups.isEmpty()) {
			System.out.println("Adding test group." + group.getPosition());
			this.particleGroups.put("test", group);
		} else {
			System.out.println("Cleaning test group.");
			particleGroups.remove("test");
		}
	}
	
	public ParticleGroup getParticleGroup (String name) {
		return this.particleGroups.get(name);
	}

	public ParticleGroup createGroup (String name, double x, double y, double z) {
		if (particleGroups.containsKey(name)) return null;
		ParticleGroup g = new ParticleGroup(x,y,z);
		particleGroups.put(name, g);
		return g;
	}

//	@Override
//	public void renderParticles(MatrixStack matrices, Immediate immediate, LightmapTextureManager lightmapTextureManager, Camera camera, float f) {
//		// TODO Auto-generated method stub
//		super.renderParticles(matrices, immediate, lightmapTextureManager, camera, f);
//		Tessellator tessellator = Tessellator.getInstance();
//		BufferBuilder bufferBuilder = tessellator.getBuffer();
//		particleGroups.values().forEach(pg -> {
//			pg.buildParticleGeometry(bufferBuilder, camera, f);
//		});
//	}
	
//	@Override
	public void tick() {
//		super.tick();
		particleGroups.values().forEach(pg -> {
			pg.tick();
		});
	}

//	private static List<ParticleGroup> particleGroups;
	
//	public static void init () {
//		MinecraftClient.getInstance().gameRenderer.getCamera();
//		particleGroups = new ArrayList<>(); 
//	}
//	
//	public static void addGroup (ParticleGroup group) {
//		particleGroups.add(group);
//	}
//	
//	public static void tick () {
//		for (int i = 0; i < particleGroups.size(); i ++) {
//			particleGroups.get(i).tick();
//			if (particleGroups.get(i).dead()) {
//				particleGroups.remove(i);
//				i--;
//			}
//		};
//	}

}
