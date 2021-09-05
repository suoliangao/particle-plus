package top.suoliangao.particleplus;

import java.io.File;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback.Registry;
import net.fabricmc.loader.launch.knot.KnotClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.TranslatableText;
import top.suoliangao.particleplus.command.ParticlePlusCommand;
import top.suoliangao.particleplus.particle.ImageParticleGroup;
import top.suoliangao.particleplus.particle.ParticleGroup;
import top.suoliangao.particleplus.particle.ParticlePlusManager;
import top.suoliangao.particleplus.util.ExternalResourceManager;
import top.suoliangao.particleplus.util.ImageUtil;

public class ParticlePlusMod implements ModInitializer, ClientModInitializer, DedicatedServerModInitializer {
	
	private static ParticlePlusMod instance;
	public static ParticlePlusMod getInstance () {return instance;}
	
	public static final File modDataDir = new File ("particleplus");

	public ParticlePlusManager ppmgr;
	
	public ParticlePlusMod () {
		instance = this;
	}
	
	public void initParticleManager () {
		System.out.println("Initializing particle plus mod!!!");
		ppmgr = new ParticlePlusManager();
		System.out.println("Particle manager initialized.");
	}
	
	@Override
	public void onInitialize() {
		ExternalResourceManager.initResources();
		// register command
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			ParticlePlusCommand.register(dispatcher);
		});

	}

	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_WORLD_TICK.register(world -> {
			ppmgr.tick();
		});
		// register client listener
		System.out.println("Registering client listener.");
		ClientPlayNetworking.registerGlobalReceiver(ParticlePlusCommand.PARTICLE_PACKET_ID, (client, handler, buf, responseSender) -> {
			System.out.println("Spawning particles on client.");
			ParticleGroup pg = null;
			switch (buf.readByte()) {
			case 0: //create
				ppmgr.addParticleGroup("test", new ParticleGroup(buf.readDouble(), buf.readDouble(), buf.readDouble()));
				break;
			case 1: //Euler
				pg = ppmgr.getParticleGroup("test");
				if (pg != null)
					pg.rotate(buf.readDouble(), buf.readDouble(), buf.readDouble());
				break;
			case 2: //Euler
				pg = ppmgr.getParticleGroup("test");
				if (pg != null)
					pg.rotate(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble());
				break;
			}
//			Particle p = client.particleManager.addParticle(ParticleTypes.END_ROD, buf.readDouble(), buf.readDouble(), buf.readDouble(), 0, 0, 0);
//			p.setColor(0x66/255f, 0xcc/255f, 0xff/255f);
//			new ParticleWrap(p);
//			new ImageParticleGroup(client.particleManager, ParticleTypes.END_ROD,  buf.readDouble(), buf.readDouble(), buf.readDouble(), ImageUtil.getTextImage("Hello Particles!", 24));
//			ExternalResourceManager.getFont("TIMES.TTF");
		});
		System.out.println("Hello Particles!");
	}


	@Override
	public void onInitializeServer() {

	}
}
