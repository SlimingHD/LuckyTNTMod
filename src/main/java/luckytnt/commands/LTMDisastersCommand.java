package luckytnt.commands;

import com.mojang.brigadier.context.CommandContext;

import luckytnt.LevelVariables;
import luckytnt.config.LuckyTNTConfigValues;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class LTMDisastersCommand {

	public static int executeGetActiveDisasters(CommandContext<CommandSourceStack> command) {
		LevelVariables variables = LevelVariables.get(command.getSource().getLevel());
		boolean disasterActive = false;
		
		if (variables.doomsdayTime > 0) {
			command.getSource().sendSuccess(() -> Component.translatable("command.luckytntmod.ltmdisaster.doomsdayactive", String.format("%1$.1f", variables.doomsdayTime / 1200d)), false);
			disasterActive = true;
		}
		if (variables.toxicCloudsTime > 0) {
			command.getSource().sendSuccess(() -> Component.translatable("command.luckytntmod.ltmdisaster.toxiccloudsactive", String.format("%1$.1f", variables.toxicCloudsTime / 1200d)), false);
			disasterActive = true;
		}
		if (variables.iceAgeTime > 0) {
			command.getSource().sendSuccess(() -> Component.translatable("command.luckytntmod.ltmdisaster.iceageactive", String.format("%1$.1f", variables.iceAgeTime / 1200d)), false);
			disasterActive = true;
		}
		if (variables.heatDeathTime > 0) {
			command.getSource().sendSuccess(() -> Component.translatable("command.luckytntmod.ltmdisaster.heatdeathactive", String.format("%1$.1f", variables.heatDeathTime / 1200d)), false);
			disasterActive = true;
		}
		if (variables.tntRainTime > 0) {
			command.getSource().sendSuccess(() -> Component.translatable("command.luckytntmod.ltmdisaster.tntrainactive", String.format("%1$.1f", variables.tntRainTime / 1200d)), false);
			disasterActive = true;
		}
		if (!disasterActive) {
			command.getSource().sendSuccess(() -> Component.translatable("command.luckytntmod.ltmdisaster.nothingactive"), false);
		}
		
		return 1;
	}
	
	public static int executeClear(CommandContext<CommandSourceStack> command) {
		ServerLevel level = command.getSource().getLevel();
		LevelVariables variables = LevelVariables.get(level);
		
		variables.doomsdayTime = 0;
		variables.toxicCloudsTime = 0;
		variables.iceAgeTime = 0;
		variables.heatDeathTime = 0;
		variables.tntRainTime = 0;
		variables.sync(level);
		
		level.setWeatherParameters(0, 0, false, false);
		
		command.getSource().sendSuccess(() -> Component.translatable("command.luckytntmod.ltmdisaster.clear"), false);
		
		return 1;
	}
	
	public static int executeDoomsday(CommandContext<CommandSourceStack> command) {
		ServerLevel level = command.getSource().getLevel();
		LevelVariables variables = LevelVariables.get(level);
		
		variables.doomsdayTime = randomLength(level);
		variables.sync(level);
		
		level.setWeatherParameters(0, variables.doomsdayTime, true, true);
		
		command.getSource().sendSuccess(() -> Component.translatable("command.luckytntmod.ltmdisaster.doomsday"), false);
		
		return 1;
	}
	
	public static int executeToxicClouds(CommandContext<CommandSourceStack> command) {
		ServerLevel level = command.getSource().getLevel();
		LevelVariables variables = LevelVariables.get(level);
		
		variables.toxicCloudsTime = randomLength(level);
		variables.sync(level);
		
		command.getSource().sendSuccess(() -> Component.translatable("command.luckytntmod.ltmdisaster.toxicclouds"), false);
		
		return 1;
	}
	
	public static int executeIceAge(CommandContext<CommandSourceStack> command) {
		ServerLevel level = command.getSource().getLevel();
		LevelVariables variables = LevelVariables.get(level);
		
		variables.iceAgeTime = randomLength(level);
		variables.sync(level);
		
		level.setWeatherParameters(0, variables.iceAgeTime, true, true);
		
		command.getSource().sendSuccess(() -> Component.translatable("command.luckytntmod.ltmdisaster.iceage"), false);
		
		return 1;
	}
	
	public static int executeHeatDeath(CommandContext<CommandSourceStack> command) {
		ServerLevel level = command.getSource().getLevel();
		LevelVariables variables = LevelVariables.get(level);
		
		variables.heatDeathTime = randomLength(level);
		variables.sync(level);
		
		command.getSource().sendSuccess(() -> Component.translatable("command.luckytntmod.ltmdisaster.heatdeath"), false);
		
		return 1;
	}
	
	public static int executeTNTRain(CommandContext<CommandSourceStack> command) {
		ServerLevel level = command.getSource().getLevel();
		LevelVariables variables = LevelVariables.get(level);
		
		variables.tntRainTime = randomLength(level);
		variables.sync(level);
		
		command.getSource().sendSuccess(() -> Component.translatable("command.luckytntmod.ltmdisaster.tntrain"), false);
		
		return 1;
	}
	
	public static int randomLength(Level level) {
		return 600 * LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.get() + level.getRandom().nextInt(600 * LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.get() + 1);
	}
}
