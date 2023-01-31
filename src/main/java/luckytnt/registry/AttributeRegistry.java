package luckytnt.registry;

import luckytnt.LuckyTNTMod;
import luckytnt.entity.AngryMiner;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = LuckyTNTMod.MODID, bus = Bus.MOD)
public class AttributeRegistry {

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(EntityRegistry.ANGRY_MINER.get(), AngryMiner.createAttributes().build());
	}
}
