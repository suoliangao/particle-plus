package top.suoliangao.particleplus;


import java.io.File;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.particle.ParticleTypes;
import top.suoliangao.particleplus.command.ParticlePlusCommand;
import top.suoliangao.particleplus.particle.ImageParticleGroup;
import top.suoliangao.particleplus.particle.ParticlePlusManager;
import top.suoliangao.particleplus.util.ExternalResourceManager;
import top.suoliangao.particleplus.util.ImageUtil;

public class ParticlePlusMod  implements ModInitializer {
	
	private static ParticlePlusMod instance;
	public static ParticlePlusMod getInstance () {return instance;}
	
	private File modDataDir;
	public File getModDataDir () {return this.modDataDir;}
	
	@Override
	public void onInitialize() {
		ExternalResourceManager.initResources();
		ParticlePlusManager.init();
		ClientTickEvents.END_WORLD_TICK.register(world -> {
			ParticlePlusManager.tick();
		});
		// register command
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			ParticlePlusCommand.register(dispatcher);
		});
		// register client listener
		System.out.println("Registering client listener.");
		ClientPlayNetworking.registerGlobalReceiver(ParticlePlusCommand.PARTICLE_PACKET_ID, (client, handler, buf, responseSender) -> {
			System.out.println("Spawning particles on client.");
//			Particle p = client.particleManager.addParticle(ParticleTypes.END_ROD, buf.readDouble(), buf.readDouble(), buf.readDouble(), 0, 0, 0);
//			p.setColor(0x66/255f, 0xcc/255f, 0xff/255f);
//			new ParticleWrap(p);
			 new ImageParticleGroup(client.particleManager, ParticleTypes.END_ROD,  buf.readDouble(), buf.readDouble(), buf.readDouble(), ImageUtil.getTextImage("Hello Particles!", 24));
//			ExternalResourceManager.getFont("TIMES.TTF");
		});
		System.out.println("Hello Particles!");
	}

}
