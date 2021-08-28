package top.suoliangao.particleplus.command;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.CommandNode;

import net.minecraft.command.argument.ParticleEffectArgumentType;
import net.minecraft.command.argument.TextArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import top.suoliangao.particleplus.util.ExternalResourceManager;

public class EffectTypeCommandNodes {
	private EffectTypeCommandNodes () {}
	// root
	/** Root command node */
	public static final LiteralArgumentBuilder<ServerCommandSource> PARTICLE_PLUS_ROOT = 
			literal ("/particle")
			.then(argument("particle", ParticleEffectArgumentType.particleEffect())
			.then(argument("pos", Vec3ArgumentType.vec3())
			));
	// Particle formation nodes
	/** Single particle */
	public static LiteralArgumentBuilder<ServerCommandSource> BASE_NODE = literal("base");
	public static CommandNode<ServerCommandSource> CLOUD_NODE;
	public static CommandNode<ServerCommandSource> IMAGE_NODE;
	/** Particle text */
	public static LiteralArgumentBuilder<ServerCommandSource> TEXT_NODE = literal("text").then(argument("text", TextArgumentType.text())
			.then(argument("font", StringArgumentType.word()).suggests((ctx, builder) -> {
				ExternalResourceManager.getFonts().forEach(str -> {
					builder.suggest(str);
				});
				return builder.buildFuture();
			})
			.then(argument("size", FloatArgumentType.floatArg())
			)));
	public static CommandNode<ServerCommandSource> SHAPE2D;
	public static CommandNode<ServerCommandSource> SHAPE3D;
	// Particle attribute control
	/** Root node for //particle command */
	// Particle attribute control
	public static CommandNode<ServerCommandSource> SCRIPT_NODE;
	public static CommandNode<ServerCommandSource> MATH_NODE;
	public static CommandNode<ServerCommandSource> ATTRIBUTE_NODE;
	
	public static CommandNode<ServerCommandSource> buildTree () {
//		PARTICLE_PLUS_ROOT.then(BASE_NODE).then(TEXT_NODE).;
		return PARTICLE_PLUS_ROOT.build();
	}
	
	
}
