package luckytnt.registry;

import luckytnt.LuckyTNTMod;
import luckytnt.commands.RandomTNTCommandArgument;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommandArgumentRegistry {

	@SubscribeEvent
	public static void registerCommandArguments(RegisterEvent event) {
		event.register(Registries.COMMAND_ARGUMENT_TYPE, new ResourceLocation(LuckyTNTMod.MODID, "random_tnt_argument"), () -> new RandomTNTCommandArgument.Info());
		ArgumentTypeInfos.registerByClass(RandomTNTCommandArgument.class, new RandomTNTCommandArgument.Info());
	}
}
