package luckytnt.commands;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;

import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;

public class RandomTNTCommandArgument implements ArgumentType<Integer> {
	private static final Dynamic2CommandExceptionType ERROR_TICK_COUNT_TOO_LOW = new Dynamic2CommandExceptionType((i, j) -> {
		return Component.translatable("command.randomtnt.tick_count_too_low", j, i);
	});

	private RandomTNTCommandArgument() {
	}

	public static RandomTNTCommandArgument random() {
		return new RandomTNTCommandArgument();
	}

	public Integer parse(StringReader reader) throws CommandSyntaxException {
		float f = reader.readFloat();
		int j = Math.round(f);
		if (j < 1) {
			throw ERROR_TICK_COUNT_TOO_LOW.create(j, 1);
		} else {
			return j;
		}
	}
	
	public static class Info implements ArgumentTypeInfo<RandomTNTCommandArgument, RandomTNTCommandArgument.Info.Template> {
		public void serializeToNetwork(RandomTNTCommandArgument.Info.Template template, FriendlyByteBuf buffer) {
			buffer.writeInt(1);
		}

		public RandomTNTCommandArgument.Info.Template deserializeFromNetwork(FriendlyByteBuf buffer) {
			return new RandomTNTCommandArgument.Info.Template();
		}

		public void serializeToJson(RandomTNTCommandArgument.Info.Template template, JsonObject json) {
			json.addProperty("min", 1);
		}

		public RandomTNTCommandArgument.Info.Template unpack(RandomTNTCommandArgument argument) {
			return new RandomTNTCommandArgument.Info.Template();
		}

		public final class Template implements ArgumentTypeInfo.Template<RandomTNTCommandArgument> {

			Template() {
			}

			public RandomTNTCommandArgument instantiate(CommandBuildContext p_265466_) {
				return RandomTNTCommandArgument.random();
			}

			public ArgumentTypeInfo<RandomTNTCommandArgument, ?> type() {
				return Info.this;
			}
		}
	}
}