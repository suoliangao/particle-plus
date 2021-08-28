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

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.NbtCompoundArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
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
		LiteralCommandNode root = dispatcher.register(literal ("/particle"));
		// *** misc commands ***
		dispatcher.register(literal ("/particle").then(literal("reload")));
		// *** create effects ***
		dispatcher.register(literal ("/particle").then(literal("create").then(argument("effect", NbtCompoundArgumentType.nbtCompound()).redirect(root))));
		//create particle text
		dispatcher.register(literal ("/particle").then(literal("create").then(literal("text")
				.then(argument("text", StringArgumentType.string()).then(argument("font",StringArgumentType.word()).suggests(FONT_SUGGESTION_PROVIDER).then(argument("size",FloatArgumentType.floatArg(0)).suggests((ctx, builder) -> {builder.suggest(16);return builder.buildFuture();}).redirect(root)
				.executes(new ParticlePlusCommand())))))));
		
		// *** static attributes ***
		dispatcher.register(literal ("/particle").then(literal("pos").then(argument("pos", Vec3ArgumentType.vec3()).redirect(root).executes(new ParticlePlusCommand()))));
		dispatcher.register(literal ("/particle").then(literal("rot").then(argument("rot", Vec3ArgumentType.vec3()).redirect(root).executes(new ParticlePlusCommand()))));
		dispatcher.register(literal ("/particle").then(literal("lookat")
				.then(argument("target", Vec3ArgumentType.vec3()).then(argument("angle", FloatArgumentType.floatArg())).redirect(root).executes(new ParticlePlusCommand()))
				.then(argument("target", EntityArgumentType.entity()).then(argument("angle", FloatArgumentType.floatArg())).redirect(root).executes(new ParticlePlusCommand()))
				));
		dispatcher.register(literal ("/particle").then(literal("color").then(argument("color", StringArgumentType.word()).suggests(COLOR_SUGGESTION_PROVIDER).redirect(root).executes(new ParticlePlusCommand()))));
		dispatcher.register(literal ("/particle").then(literal("life")
				.then(argument("start", IntegerArgumentType.integer(0))
				.then(argument("end", IntegerArgumentType.integer(-1)).suggests((ctx, builder) -> {builder.suggest(-1); return builder.buildFuture();})
				.redirect(root).executes(new ParticlePlusCommand())))));
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
