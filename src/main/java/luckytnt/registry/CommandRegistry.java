package luckytnt.registry;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import luckytnt.LuckyTNTMod;
import luckytnt.commands.LTMDisastersCommand;
import luckytnt.commands.RandomTNTCommand;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LuckyTNTMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandRegistry {

	@SubscribeEvent
	public static void registerCommands(final RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("ltmdisaster").requires(s -> s.hasPermission(2)).executes(LTMDisastersCommand::executeGetActiveDisasters)
				.then(Commands.literal("clear").executes(LTMDisastersCommand::executeClear))
				.then(Commands.literal("doomsday").executes(LTMDisastersCommand::executeDoomsday))
				.then(Commands.literal("toxic_clouds").executes(LTMDisastersCommand::executeToxicClouds))
				.then(Commands.literal("ice_age").executes(LTMDisastersCommand::executeIceAge))
				.then(Commands.literal("heat_death").executes(LTMDisastersCommand::executeHeatDeath))
				.then(Commands.literal("tnt_rain").executes(LTMDisastersCommand::executeTNTRain))
		);
		
		event.getDispatcher().register(Commands.literal("randomtnt").requires(s -> s.hasPermission(2))
				
				.then(Commands.literal("normal_tnt")
				.then(Commands.argument("amount", IntegerArgumentType.integer(1)).executes((p) -> {
					return RandomTNTCommand.executeGiveItems(p.getSource(), IntegerArgumentType.getInteger(p, "amount"), true, "n");
			    })
				.then(Commands.argument("allowDuplicate", BoolArgumentType.bool()).executes((p) -> {
					return RandomTNTCommand.executeGiveItems(p.getSource(), IntegerArgumentType.getInteger(p, "amount"), BoolArgumentType.getBool(p, "allowDuplicate"), "n");
				}))))
				
				.then(Commands.literal("dynamite")
				.then(Commands.argument("amount", IntegerArgumentType.integer(1)).executes((p) -> {
					return RandomTNTCommand.executeGiveItems(p.getSource(), IntegerArgumentType.getInteger(p, "amount"), true, "dy");
				})
				.then(Commands.argument("allowDuplicate", BoolArgumentType.bool()).executes((p) -> {
					return RandomTNTCommand.executeGiveItems(p.getSource(), IntegerArgumentType.getInteger(p, "amount"), BoolArgumentType.getBool(p, "allowDuplicate"), "dy");
				}))))
				
				.then(Commands.literal("god_tnt")
				.then(Commands.argument("amount", IntegerArgumentType.integer(1)).executes((p) -> {
					return RandomTNTCommand.executeGiveItems(p.getSource(), IntegerArgumentType.getInteger(p, "amount"), true, "g");
				})
				.then(Commands.argument("allowDuplicate", BoolArgumentType.bool()).executes((p) -> {
					return RandomTNTCommand.executeGiveItems(p.getSource(), IntegerArgumentType.getInteger(p, "amount"), BoolArgumentType.getBool(p, "allowDuplicate"), "g");
				}))))
				
				.then(Commands.literal("doomsday_tnt")
				.then(Commands.argument("amount", IntegerArgumentType.integer(1)).executes((p) -> {
					return RandomTNTCommand.executeGiveItems(p.getSource(), IntegerArgumentType.getInteger(p, "amount"), true, "d");
				})
				.then(Commands.argument("allowDuplicate", BoolArgumentType.bool()).executes((p) -> {
					return RandomTNTCommand.executeGiveItems(p.getSource(), IntegerArgumentType.getInteger(p, "amount"), BoolArgumentType.getBool(p, "allowDuplicate"), "d");
				}))))
		);
	}
}
