package luckytnt.registry;

import luckytnt.rules.*;
import luckytntlib.registry.ExplosionRuleRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class LuckyTNTExplosionRules {

	@SubscribeEvent
	public static void onRegister(RegisterEvent event) {
		event.register(ExplosionRuleRegistry.EXPLOSION_RULES_KEY, FilterMapColorExplosionRule.RESOURCE_LOCATION, () -> FilterMapColorExplosionRule::decode);
		event.register(ExplosionRuleRegistry.EXPLOSION_RULES_KEY, DrainAreaExplosionRule.RESOURCE_LOCATION, () -> DrainAreaExplosionRule::decode);
		event.register(ExplosionRuleRegistry.EXPLOSION_RULES_KEY, MirrorExplosionRule.RESOURCE_LOCATION, () -> MirrorExplosionRule::decode);
		event.register(ExplosionRuleRegistry.EXPLOSION_RULES_KEY, FilterLiquidExplosionRule.RESOURCE_LOCATION, () -> FilterLiquidExplosionRule::decode);
		event.register(ExplosionRuleRegistry.EXPLOSION_RULES_KEY, UpsideDownBlockExplosionRule.RESOURCE_LOCATION, () -> UpsideDownBlockExplosionRule::decode);
	}
}
