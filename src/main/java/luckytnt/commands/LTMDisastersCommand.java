package luckytnt.commands;


import com.mojang.brigadier.context.CommandContext;

import luckytnt.LevelVariables;
import luckytnt.config.LuckyTNTConfigValues;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public class LTMDisastersCommand {

	public static int executeGetActiveDisasters(CommandContext<CommandSourceStack> command) {
		if(command.getSource().getEntity() instanceof Player) {
			ServerLevel level = command.getSource().getLevel();
			boolean disasterActive = false;
			if(LevelVariables.get(level).doomsdayTime > 0) {
				command.getSource().sendSuccess(() -> Component.translatable("command.ltmdisaster.doomsdayactive").append(Component.literal("" + LevelVariables.get(level).doomsdayTime / 1200)).append(Component.translatable("command.ltmdisaster.minute")), false);
				disasterActive = true;
			}
			if(LevelVariables.get(level).toxicCloudsTime > 0) {
				command.getSource().sendSuccess(() -> Component.translatable("command.ltmdisaster.toxicactive").append(Component.literal("" + LevelVariables.get(level).toxicCloudsTime / 1200)).append(Component.translatable("command.ltmdisaster.minute")), false);
				disasterActive = true;
			}
			if(LevelVariables.get(level).iceAgeTime > 0) {
				command.getSource().sendSuccess(() -> Component.translatable("command.ltmdisaster.iceageactive").append(Component.literal("" + LevelVariables.get(level).iceAgeTime / 1200)).append(Component.translatable("command.ltmdisaster.minute")), false);
				disasterActive = true;
			}
			if(LevelVariables.get(level).heatDeathTime > 0) {
				command.getSource().sendSuccess(() -> Component.translatable("command.ltmdisaster.heatdeathactive").append(Component.literal("" + LevelVariables.get(level).heatDeathTime / 1200)).append(Component.translatable("command.ltmdisaster.minute")), false);
				disasterActive = true;
			}
			if(LevelVariables.get(level).tntRainTime > 0) {
				command.getSource().sendSuccess(() -> Component.translatable("command.ltmdisaster.tntrainactive").append(Component.literal("" + LevelVariables.get(level).tntRainTime / 1200)).append(Component.translatable("command.ltmdisaster.minute")), false);
				disasterActive = true;
			}
			if(!disasterActive) {
				command.getSource().sendSuccess(() -> Component.translatable("command.ltmdisaster.nothingactive"), false);				
			}
		}
		return 1;
	}
	
	public static int executeClear(CommandContext<CommandSourceStack> command) {
		if(command.getSource().getEntity() instanceof Player) {
			ServerLevel level = command.getSource().getLevel();
			LevelVariables.get(level).doomsdayTime = 0;
			LevelVariables.get(level).toxicCloudsTime = 0;
			LevelVariables.get(level).iceAgeTime = 0;
			LevelVariables.get(level).heatDeathTime = 0;
			LevelVariables.get(level).tntRainTime = 0;
			LevelVariables.get(level).sync(level);
			level.setWeatherParameters(0, 0, false, false);
			command.getSource().sendSuccess(() -> Component.translatable("command.ltmdisaster.clear"), false);
		}		
		return 1;
	}
	
	public static int executeDoomsday(CommandContext<CommandSourceStack> command) {
		if(command.getSource().getEntity() instanceof Player) {
			ServerLevel level = command.getSource().getLevel();
			LevelVariables.get(level).doomsdayTime = 1000 * LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.get() + (int)Math.random() * 1000 * LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.get();
			LevelVariables.get(level).sync(level);
			level.setWeatherParameters(0, LevelVariables.get(level).doomsdayTime, true, true);
			command.getSource().sendSuccess(() -> Component.translatable("command.ltmdisaster.doomsday"), false);
		}		
		return 1;
	}
	
	public static int executeToxicClouds(CommandContext<CommandSourceStack> command) {
		if(command.getSource().getEntity() instanceof Player) {
			ServerLevel level = command.getSource().getLevel();
			LevelVariables.get(level).toxicCloudsTime = 1000 * LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.get() + (int)Math.random() * 1000 * LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.get();
			LevelVariables.get(level).sync(level);
			command.getSource().sendSuccess(() -> Component.translatable("command.ltmdisaster.toxicclouds"), false);
		}		
		return 1;
	}
	
	public static int executeIceAge(CommandContext<CommandSourceStack> command) {
		if(command.getSource().getEntity() instanceof Player) {
			ServerLevel level = command.getSource().getLevel();
			LevelVariables.get(level).iceAgeTime = 1000 * LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.get() + (int)Math.random() * 1000 * LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.get();
			LevelVariables.get(level).sync(level);
			level.setWeatherParameters(0, LevelVariables.get(level).iceAgeTime, true, true);
			command.getSource().sendSuccess(() -> Component.translatable("command.ltmdisaster.iceage"), false);
		}		
		return 1;
	}
	
	public static int executeHeatDeath(CommandContext<CommandSourceStack> command) {
		if(command.getSource().getEntity() instanceof Player) {
			ServerLevel level = command.getSource().getLevel();
			LevelVariables.get(level).heatDeathTime = 1000 * LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.get() + (int)Math.random() * 1000 * LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.get();
			LevelVariables.get(level).sync(level);
			command.getSource().sendSuccess(() -> Component.translatable("command.ltmdisaster.heatdeath"), false);
		}		
		return 1;
	}
	
	public static int executeTNTRain(CommandContext<CommandSourceStack> command) {
		if(command.getSource().getEntity() instanceof Player) {
			ServerLevel level = command.getSource().getLevel();
			LevelVariables.get(level).tntRainTime = 1000 * LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.get() + (int)Math.random() * 1000 * LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.get();
			LevelVariables.get(level).sync(level);
			command.getSource().sendSuccess(() -> Component.translatable("command.ltmdisaster.tntrain"), false);
		}		
		return 1;
	}
}
