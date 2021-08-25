package top.suoliangao.particleplus.command;

import java.io.File;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.arguments.StringArgumentType;
public class FileArgumentType implements ArgumentType<File> {

	@Override
	public File parse(StringReader reader) throws CommandSyntaxException {
		// TODO Auto-generated method stub
		return null;
	}

}
