package luckytnt.registry;

import luckytnt.client.renderer.AngryMinerRenderer;
import luckytnt.client.renderer.BombRenderer;
import luckytnt.client.renderer.BouncingTNTRenderer;
import luckytntlib.client.renderer.LDynamiteRenderer;
import luckytntlib.client.renderer.LTNTMinecartRenderer;
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
		event.registerEntityRenderer(EntityRegistry.ANGRY_MINERS.get(), LTNTRenderer::new);		
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
		event.registerEntityRenderer(EntityRegistry.TURRET_TNT.get(), LTNTRenderer::new);
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
		event.registerEntityRenderer(EntityRegistry.MOUNTAINTOP_REMOVAL.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DUST_BOWL.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.THE_REVOLUTION.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.POMPEII.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CHICXULUB.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.UNBREAKABLE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.END_GATE.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DENSE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.HYPERION.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TNT_X2000.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TSAR_BOMBA.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.WITHER_STORM.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.LEAPING_TNT.get(), BouncingTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.RUSSIAN_ROULETTE.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.KNOCKBACK_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.MIDAS_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.NEW_YEARS_FIREWORK.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.PULSAR_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.LIGHTNING_STORM.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SILK_TOUCH_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ITEM_FIREWORK.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ANIMAL_KINGDOM.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.GIANT_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.MIMIC_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.REVERSED_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ENTITY_FIREWORK.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CUSTOM_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.RESET_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.VICIOUS_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.HUNGRY_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SINKHOLE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FIRESTORM_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SNOWSTORM_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ACIDIC_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CATALYST_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TOXIC_CLOUDS.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DISASTER_CLEARER.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ICE_AGE.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CANNON_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.PLANTATION_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.GOTTHARD_TUNNEL.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.LEVITATING_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SQUARING_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.MINERAL_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FLOWER_FOREST_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ICY_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.GHOST_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.PARTICLE_PHYSICS_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.HEAT_DEATH.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CONTINENTAL_DRIFT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SUPERNOVA.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CITY_FIREWORK.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.METEOR_STORM.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CHUNK_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.COMPRESSED_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.EXTINCTION.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.MANSION.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.HELIX.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DEATH_RAY.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DOOMSDAY.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FIERY_HELL.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.STONE_COLD.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.JUNGLE_TNT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.JUMPING_TNT.get(), BouncingTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.WASTELAND_TNT.get(), BouncingTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TNT_X10000.get(), BouncingTNTRenderer::new);
	
		//Dynamite
		event.registerEntityRenderer(EntityRegistry.DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DYNAMITE_X5.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DYNAMITE_X20.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DYNAMITE_X100.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DYNAMITE_X500.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FIRE_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SNOW_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DYNAMITE_FIREWORK.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.NUCLEAR_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FREEZE_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FLOATING_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SPHERE_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FLAT_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.MININGFLAT_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.VAPORIZE_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.METEOR_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CUBIC_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.GROVE_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ENDER_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ARROW_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.LIGHTNING_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DIGGING_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.COMPACT_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ANIMAL_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.OCEAN_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SPIRAL_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CHEMICAL_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.REACTION_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.HELLFIRE_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FLOATING_ISLAND_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ERUPTING_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SHATTERPROOF_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.LAVA_OCEAN_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.WOOL_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.NUCLEAR_WASTE_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TIMER_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.GRAVITY_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.WITHERING_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SENSOR_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.RAINBOW_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ROULETTE_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.BOUNCING_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.IGNITER_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.MULTIPLYING_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.RANDOM_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.HOMING_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.PULSE_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.PHYSICS_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.PICKY_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CLUSTER_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TUNNELING_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.XRAY_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FARMING_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.BIG_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ICE_METEOR_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.HONEY_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ULTRALIGHT_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ACCELERATING_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.NETHER_GROVE_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.LUSH_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DRIPSTONE_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.END_DYNAMITE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CHRISTMAS_DYNAMITE.get(), LDynamiteRenderer::new);

		//Minecarts
		event.registerEntityRenderer(EntityRegistry.TNT_X5_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TNT_X20_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TNT_X100_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TNT_X500_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DIGGING_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DRILLING_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SPHERE_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FLOATING_ISLAND_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.OCEAN_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.HELLFIRE_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FIRE_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SNOW_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FREEZE_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.VAPORIZE_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.GRAVITY_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.LIGHTNING_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CUBIC_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ARROW_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TIMER_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FLAT_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.MININGFLAT_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.COMPACT_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ANIMAL_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ERUPTING_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.GROVE_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ENDER_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.METEOR_SHOWER_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.INVERTED_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.NUCLEAR_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CHEMICAL_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.REACTION_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.VILLAGE_DEFENSE_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ZOMBIE_APOCALYPSE_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SHATTERPROOF_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.LAVA_OCEAN_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.WOOL_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SAY_GOODBYE_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ANGRY_MINERS_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.WITHERING_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.NUCLEAR_WASTE_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.PUMPKIN_BOMB_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.AIR_STRIKE_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SPAMMING_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ROULETTE_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.XRAY_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.FARMING_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SWAP_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.IGNITER_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.BUTTER_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.PHYSICS_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ORE_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.REDSTONE_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.RANDOM_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TURRET_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.PULSE_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.PICKY_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.BIG_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.HONEY_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.EATING_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		event.registerEntityRenderer(EntityRegistry.LUCKY_TNT_MINECART.get(), LTNTMinecartRenderer::new);
		
		//Projectiles
		event.registerEntityRenderer(EntityRegistry.METEOR.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.LITTLE_METEOR.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SPIRAL_PROJECTILE.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ERUPTING_PROJECTILE.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.MINI_METEOR.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CHEMICAL_PROJECTILE.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CLUSTER_BOMB.get(), BombRenderer::new);
		event.registerEntityRenderer(EntityRegistry.SHRAPNEL.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.BOMB.get(), BombRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ICE_METEOR.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.LITTLE_ICE_METEOR.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.POMPEII_PROJECTILE.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CHICXULUB_METEOR.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TSAR_BOMBA_BOMB.get(), BombRenderer::new);
		event.registerEntityRenderer(EntityRegistry.PRESENT.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.ACIDIC_PROJECTILE.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.HAILSTONE.get(), LTNTRenderer::new);
		event.registerEntityRenderer(EntityRegistry.CHRISTMAS_DYNAMITE_PROJECTILE.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.DEATH_RAY_RAY.get(), LDynamiteRenderer::new);
		event.registerEntityRenderer(EntityRegistry.VACUUM_SHOT.get(), LDynamiteRenderer::new);
		
		//Other
		event.registerEntityRenderer(EntityRegistry.ANGRY_MINER.get(), AngryMinerRenderer::new);
		event.registerEntityRenderer(EntityRegistry.TOXIC_CLOUD.get(), LTNTRenderer::new);
	}
}
