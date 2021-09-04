package top.suoliangao.particleplus.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.LiteralCommandNode;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.NbtCompoundArgumentType;
import net.minecraft.command.argument.RotationArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import top.suoliangao.particleplus.util.ExternalResourceManager;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ParticlePlusCommand implements Command<ServerCommandSource> {
	
	public static final Identifier PARTICLE_PACKET_ID = Identifier.tryParse("particle_plus_packet");
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
	
	private ParticlePlusCommand () {}
	
	@SuppressWarnings("unchecked")
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		if (PARTICLE_PACKET_ID == null) {
			System.err.println("Fail to register //particle command, identifier parse faild.");
			return;
		}
//		LiteralCommandNode root = dispatcher.register(literal ("/particle"));
		// *** misc commands ***
//		dispatcher.register(literal ("/particle_admin").then(literal("reload").executes(ctx -> {ExternalResourceManager.reload();return 1;})));
		// *** particle group effects ***
		
		// *** simple animation ***
		System.out.println("Register particleplus command!");
		dispatcher.register(literal("/particle").executes(new ParticlePlusCommand()));
	}
	
	@Override
	public int run(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
		// TODO Auto-generated method stub
		if (PARTICLE_PACKET_ID == null) return 0;
		System.out.println("Executing //particle command!!!");
		Vec3d pos = ctx.getSource().getPosition();
		ctx.getSource().getWorld().getPlayers().forEach(player -> {
			PacketByteBuf buff = PacketByteBufs.create();
			buff.writeDouble(pos.x).writeDouble(pos.y+1).writeDouble(pos.z);
			ServerPlayNetworking.send(player, PARTICLE_PACKET_ID, buff);
		});
		return 1;
	}
	
	public boolean execute () {
		
		return true;
	}

}
