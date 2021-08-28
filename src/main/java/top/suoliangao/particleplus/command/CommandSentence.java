package top.suoliangao.particleplus.command;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.RedirectModifier;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.CommandNode;

public class CommandSentence<S> extends CommandNode<S>{

	protected CommandSentence(Command<S> command, Predicate<S> requirement, CommandNode<S> redirect,
			RedirectModifier<S> modifier, boolean forks) {
		super(command, requirement, redirect, modifier, forks);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean isValidInput(String input) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsageText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parse(StringReader reader, CommandContextBuilder<S> ctx) throws CommandSyntaxException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder)
			throws CommandSyntaxException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArgumentBuilder<S, ?> createBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getSortedKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getExamples() {
		// TODO Auto-generated method stub
		return null;
	}

}
