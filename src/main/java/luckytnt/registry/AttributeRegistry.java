package luckytnt.registry;

import luckytnt.LuckyTNTMod;
import luckytnt.entity.AngryMiner;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = LuckyTNTMod.MODID, bus = Bus.MOD)
public class AttributeRegistry {

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(EntityRegistry.ANGRY_MINER.get(), AngryMiner.createAttributes().build());
		event.put(EntityRegistry.ATTACKING_TNT.get(), Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.4f).add(Attributes.MAX_HEALTH, 1024).add(Attributes.ATTACK_DAMAGE, 5).build());
		event.put(EntityRegistry.WALKING_TNT.get(), Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.4f).add(Attributes.MAX_HEALTH, 1024).build());
		event.put(EntityRegistry.VICIOUS_TNT.get(), Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.5f).add(Attributes.MAX_HEALTH, 1024).add(Attributes.ATTACK_DAMAGE, 10).build());
		event.put(EntityRegistry.EVIL_TNT.get(), Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.6f).add(Attributes.MAX_HEALTH, 1024).add(Attributes.ATTACK_DAMAGE, 20).build());
	}
}
