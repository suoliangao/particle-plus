package top.suoliangao.particleplus.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.command.ParticleCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ParticlePlusCommand implements Command<ServerCommandSource> {
	
	public static final Identifier PARTICLE_PACKET_ID = Identifier.tryParse("particle_plus_packet");
	
	private ParticlePlusCommand () {}
	
	public enum Mode {
		TEST("test");
		
		private String name;
		
		private Mode (String name) { this.name = name; };
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return this.name;
		} 
	}
	
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		if (PARTICLE_PACKET_ID == null) {
			System.err.println("Fail to register //particle command, identifier parse faild.");
			return;
		}
		dispatcher.register(
				literal("/particle").executes(new ParticlePlusCommand()));
	}
	
	@Override
	public int run(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
		// TODO Auto-generated method stub
		if (PARTICLE_PACKET_ID == null) return 0;
		System.out.println("Executing //particle command!!!");
		
		Vec3d pos = ctx.getSource().getPosition();
		System.out.println(PARTICLE_PACKET_ID);
		ctx.getSource().getWorld().getPlayers().forEach(player -> {
			System.out.println(player.getName().toString());
			PacketByteBuf buff = PacketByteBufs.create();
			System.out.println("11111");
			buff.writeDouble(pos.x).writeDouble(pos.y+1).writeDouble(pos.z);
			ServerPlayNetworking.send(player, PARTICLE_PACKET_ID, buff);
			System.out.println("Sending packet to player " + player.getName());
		});
		return 1;
	}
	
	public boolean execute () {
		
		return true;
	}

}
