package luckytnt.registry;

import luckytnt.LuckyTNTMod;
import luckytnt.util.advancement.Craft100Condition;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class LootConditionTypeRegistry {

	public static final ResourceLocation CRAFT_100_LOCATION = new ResourceLocation(LuckyTNTMod.MODID, "craft_100");
	
	public static final LootItemConditionType CRAFT_100 = new LootItemConditionType(new Craft100Condition.Serializer());
	
	@SubscribeEvent
	public static void registerLootConditionTypes(RegisterEvent event) {
		event.register(Registries.LOOT_CONDITION_TYPE, CRAFT_100_LOCATION, () -> CRAFT_100);
	}
}
