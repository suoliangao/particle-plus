package top.suoliangao.particleplus;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.client.particle.Particle;
import net.minecraft.particle.ParticleTypes;
import top.suoliangao.particleplus.command.ParticlePlusCommand;
import top.suoliangao.particleplus.particle.ParticleController;

public class ParticlePlusMod  implements ModInitializer {
	
	@Override
	public void onInitialize() {
		// TODO Auto-generated method stub
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			ParticlePlusCommand.register(dispatcher);
		});
		// register client listener
		System.out.println("Registering client listener.");
		ClientPlayNetworking.registerGlobalReceiver(ParticlePlusCommand.PARTICLE_PACKET_ID, (client, handler, buf, responseSender) -> {
			System.out.println("Spawning particles on client.");
			Particle p = client.particleManager.addParticle(ParticleTypes.END_ROD, buf.readDouble(), buf.readDouble(), buf.readDouble(), 0, 0, 0);
			p.setColor(0x66/255f, 0xcc/255f, 0xff/255f);
			new ParticleController(p);
		});
		System.out.println("Hello Particles!");
	}

}
