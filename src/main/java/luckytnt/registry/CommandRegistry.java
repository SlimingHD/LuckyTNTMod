package luckytnt.registry;

import luckytnt.LuckyTNTMod;
import luckytnt.commands.LTMDisastersCommand;
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
	}
}
