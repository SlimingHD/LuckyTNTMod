package luckytnt.registry;

import luckytnt.client.renderer.BombRenderer;
import luckytnt.client.renderer.BouncingTNTRenderer;
import luckytntlib.client.renderer.LTNTRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RendererRegistry {

	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		//TNT
		event.registerEntityRenderer(EntityRegistry.TNT.get(), LTNTRenderer::new);

		event.registerEntityRenderer(EntityRegistry.TNT_X5.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TNT_X20.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TNT_X100.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TNT_X500.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.COBBLESTONE_HOUSE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.WOOD_HOUSE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.BRICK_HOUSE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DIGGING_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DRILLING_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SPHERE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FLOATING_ISLAND.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.OCEAN_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.HELLFIRE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FIRE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SNOW_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FREEZE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.VAPORIZE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.GRAVITY_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.LIGHTNING_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CUBIC_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FLOATING_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TNT_FIREWORK.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SAND_FIREWORK.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ARROW_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TIMER_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FLAT_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.MININGFLAT_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.COMPACT_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ANIMAL_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.METEOR_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SPIRAL_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ERUPTING_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.GROVE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ENDER_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.METEOR_SHOWER.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.INVERTED_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.NUCLEAR_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CHEMICAL_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.REACTION_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.EASTER_EGG.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DAY_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.NIGHT_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.VILLAGE_DEFENSE.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ZOMBIE_APOCALYPSE.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SHATTERPROOF_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.GRAVEL_FIREWORK.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.LAVA_OCEAN_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ATTACKING_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.WALKING_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.WOOL_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SAY_GOODBYE.get(), LTNTRenderer::new);
		//event.registerEntityRenderer(EntityRegistry.ANGRY_MINERS.get(), LTNTRenderer::new);		
		event.registerEntityRenderer(EntityRegistry.WITHERING_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.NUCLEAR_WASTE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.STATIC_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.PUMPKIN_BOMB.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SMOKE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TROLL_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TROLL_TNT_MK2.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TROLL_TNT_MK3.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CLUSTER_BOMB_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.AIR_STRIKE.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SPAMMING_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.BOUNCING_TNT.get(), BouncingTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ROULETTE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SENSOR_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.RAINBOW_FIREWORK.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.XRAY_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FARMING_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.PHANTOM_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SWAP_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.IGNITER_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.MULTIPLYING_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.BUTTER_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TUNNELING_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.PHYSICS_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ORE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.REDSTONE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.RANDOM_TNT.get(), LTNTRenderer::new);
		//event.registerEntityRenderer(EntityRegistry.TURRET_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.PULSE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DIVIDING_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.PICKY_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.BIG_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ICE_METEOR_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.HONEY_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.EATING_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.LUSH_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.GEODE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.NETHER_GROVE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DRIPSTONE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.GRAVEYARD_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.REPLAY_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.END_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CHRISTMAS_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.EARTHQUAKE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.GLOBAL_DISASTER.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.HEAVENS_GATE.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.HELLS_GATE.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.MANKINDS_MARK.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.POSEIDONS_WAVE.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.HEXAHEDRON.get(), LTNTRenderer::new);
		
		//Projectiles
		event.registerEntityRenderer(EntityRegistry.METEOR.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SPIRAL_PROJECTILE.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ERUPTING_PROJECTILE.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.MINI_METEOR.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CHEMICAL_PROJECTILE.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CLUSTER_BOMB.get(), BombRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SHRAPNEL.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.BOMB.get(), BombRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ICE_METEOR.get(), LTNTRenderer::new);
	}
}
