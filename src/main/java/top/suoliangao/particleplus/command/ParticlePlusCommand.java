package top.suoliangao.particleplus.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import top.suoliangao.particleplus.ParticlePlusMod;
import top.suoliangao.particleplus.util.ExternalResourceManager;

import static net.minecraft.server.command.CommandManager.literal;

import java.util.List;

public class ParticlePlusCommand implements Command<ServerCommandSource> {
	
	public static final Identifier PARTICLE_PACKET_ID = Identifier.tryParse("particle_plus_packet");
	/** Command suggestion providers */
	private class sp {
		private static final SuggestionProvider<ServerCommandSource> IMAGE_SUGGESTION_PROVIDER = (ctx, builder) -> {
			ExternalResourceManager.getImages().forEach(str -> {builder.suggest(str);});
			return builder.buildFuture();
		};
		private static final SuggestionProvider<ServerCommandSource> FONT_SUGGESTION_PROVIDER = (ctx, builder) -> {
			ExternalResourceManager.getFonts().forEach(str -> {builder.suggest(str);});
			return builder.buildFuture();
		};
		private static final SuggestionProvider<ServerCommandSource> COLOR_SUGGESTION_PROVIDER = (ctx, builder) -> {
			builder.suggest("0xffffffff");
			builder.suggest("rgb_255_255_255");
			builder.suggest("rgb_255_255_255_255");
			builder.suggest("rgbf_1.0_1.0_1.0");
			builder.suggest("hsv_255_255_255");
			return builder.buildFuture();
		};
	}
	/** Command executors */
	private class CommandExecutor {
		private static void sendPacket (ServerCommandSource source, PacketByteBuf buf) {
			
		}
		
		private static final Command<ServerCommandSource> TEST_COMMAND = ctx -> {
			ctx.getSource().sendFeedback(new LiteralText("Single player world: " + ctx.getSource().getMinecraftServer().isSinglePlayer()), false);
			return 1;
		};
		private static final Command<ServerCommandSource> CREATE_GROUP = ctx -> {
			if (ctx.getSource().getMinecraftServer().isSinglePlayer()) {
				//TODO Single player world action.
//				ParticlePlusMod.getInstance().ppmgr.
			}
			ctx.getSource().sendFeedback(new LiteralText("Single player world: " + ctx.getSource().getMinecraftServer().isSinglePlayer()), false);
			return 1;
		};
	}
	private ParticlePlusCommand () {}
	
	@SuppressWarnings("unchecked")
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		if (PARTICLE_PACKET_ID == null) {
			System.err.println("Fail to register //particle command, identifier parse faild.");
			return;
		}
		System.out.println("Register particleplus command!");
		dispatcher.register(literal("/particle").executes(CommandExecutor.TEST_COMMAND));
		dispatcher.register(literal("/particle").then(literal("randrot").executes(new ParticlePlusCommand())));
	}
	
	@Override
	public int run(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
		// TODO Auto-generated method stub
		if (PARTICLE_PACKET_ID == null) return 0;
		List nodes = ctx.getNodes();
		PacketByteBuf buff = PacketByteBufs.create();
		if (nodes.size() == 1) {
			test (buff, ctx.getSource().getPosition());
		} else {
			randRot(buff);
		}
		ctx.getSource().getWorld().getPlayers().forEach(player -> {
			ServerPlayNetworking.send(player, PARTICLE_PACKET_ID, buff);
		});
		return 1;
	}
	
	private void test (PacketByteBuf buff, Vec3d pos) {
		buff.writeByte(0);
		buff.writeDouble(pos.x).writeDouble(pos.y+1).writeDouble(pos.z);
	}
	
	private void randRot (PacketByteBuf buff) {
		switch ((int)(2*Math.random())) {
		case 0: // Euler
			System.out.println("Euler rotation.");
			buff.writeByte(1);
			buff.writeDouble(360 * Math.random()).writeDouble(360 * Math.random()).writeDouble(360 * Math.random());
			return;
		case 1: // Turn around axis
			System.out.println("Turn around axis");
			buff.writeByte(2);
			buff.writeDouble(Math.random()).writeDouble(Math.random()).writeDouble(Math.random()).writeDouble(360 * Math.random());
			return;
		}
	}
	
	public boolean execute () {
		
		return true;
	}

}
