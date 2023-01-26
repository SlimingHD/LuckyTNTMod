package luckytnt.registry;

import java.util.Collections;

import luckytnt.LuckyTNTMod;
import luckytnt.block.entity.SmokeTNTBlockEntity;
import luckytnt.entity.PrimedOreTNT;
import luckytnt.tnteffects.*;
import luckytnt.tnteffects.projectile.*;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.entity.LivingPrimedLTNT;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.explosions.StackedPrimedTNTEffect;
import luckytntlib.util.explosions.TNTxStrengthEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {
	
	//TNT
	public static final RegistryObject<EntityType<PrimedLTNT>> TNT = LuckyTNTMod.RH.registerTNTEntity("tnt", new TNTxStrengthEffect.Builder(() -> BlockRegistry.TNT).build());

	public static final RegistryObject<EntityType<PrimedLTNT>> TNT_X5 = LuckyTNTMod.RH.registerTNTEntity("tnt_x5", new TNTxStrengthEffect.Builder(() -> BlockRegistry.TNT_X5).fuse(120).explosionStrength(10f).randomVecLength(1.25f).knockbackStrength(1.5f).build());
	public static final RegistryObject<EntityType<PrimedLTNT>> TNT_X20 = LuckyTNTMod.RH.registerTNTEntity("tnt_x20", new TNTxStrengthEffect.Builder(() -> BlockRegistry.TNT_X20).fuse(160).explosionStrength(20f).randomVecLength(1.5f).knockbackStrength(2f).build());
	public static final RegistryObject<EntityType<PrimedLTNT>> TNT_X100 = LuckyTNTMod.RH.registerTNTEntity("tnt_x100", new TNTxStrengthEffect.Builder(() -> BlockRegistry.TNT_X100).fuse(200).explosionStrength(50f).yStrength(1.3f).knockbackStrength(3f).build());
	public static final RegistryObject<EntityType<PrimedLTNT>> TNT_X500 = LuckyTNTMod.RH.registerTNTEntity("tnt_x500", new TNTxStrengthEffect.Builder(() -> BlockRegistry.TNT_X500).fuse(240).explosionStrength(80f).yStrength(1.3f).knockbackStrength(5f).build());
	public static final RegistryObject<EntityType<PrimedLTNT>> COBBLESTONE_HOUSE_TNT = LuckyTNTMod.RH.registerTNTEntity("cooblestone_house_tnt", new HouseTNTEffect(() -> BlockRegistry.COBBLESTONE_HOUSE_TNT, "cobblehouse", -5, -3));
	public static final RegistryObject<EntityType<PrimedLTNT>> WOOD_HOUSE_TNT = LuckyTNTMod.RH.registerTNTEntity("woodhouse_tnt", new HouseTNTEffect(() -> BlockRegistry.WOOD_HOUSE_TNT, "woodhouse", -5, -3));
	public static final RegistryObject<EntityType<PrimedLTNT>> BRICK_HOUSE_TNT = LuckyTNTMod.RH.registerTNTEntity("brickhouse_tnt", new HouseTNTEffect(() -> BlockRegistry.BRICK_HOUSE_TNT, "brickhouse", -5, -3));
	public static final RegistryObject<EntityType<PrimedLTNT>> DIGGING_TNT = LuckyTNTMod.RH.registerTNTEntity("digging_tnt", new DiggingTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> DRILLING_TNT = LuckyTNTMod.RH.registerTNTEntity("drilling_tnt", new DrillingTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> SPHERE_TNT = LuckyTNTMod.RH.registerTNTEntity("sphere_tnt", new SphereTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> FLOATING_ISLAND = LuckyTNTMod.RH.registerTNTEntity("floating_island", new FloatingIslandEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> OCEAN_TNT = LuckyTNTMod.RH.registerTNTEntity("ocean_tnt", new OceanTNTEffect(() -> BlockRegistry.OCEAN_TNT, 30, 10, 10));
	public static final RegistryObject<EntityType<PrimedLTNT>> HELLFIRE_TNT = LuckyTNTMod.RH.registerTNTEntity("hellfire_tnt", new HellfireTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> FIRE_TNT = LuckyTNTMod.RH.registerTNTEntity("fire_tnt", new FireTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> SNOW_TNT = LuckyTNTMod.RH.registerTNTEntity("snow_tnt", new SnowTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> FREEZE_TNT = LuckyTNTMod.RH.registerTNTEntity("freeze_tnt", new FreezeTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> VAPORIZE_TNT = LuckyTNTMod.RH.registerTNTEntity("vaporize_tnt", new VaporizeTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> GRAVITY_TNT = LuckyTNTMod.RH.registerTNTEntity("gravity_tnt", new GravityTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> LIGHTNING_TNT = LuckyTNTMod.RH.registerTNTEntity("lightning_tnt", new LightningTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> CUBIC_TNT = LuckyTNTMod.RH.registerTNTEntity("cubic_tnt", new CubicTNTEffect(() -> BlockRegistry.CUBIC_TNT, 3));
	public static final RegistryObject<EntityType<PrimedLTNT>> FLOATING_TNT = LuckyTNTMod.RH.registerTNTEntity("floating_tnt", new StackedPrimedTNTEffect(new FloatingTNTEffect(), Collections.singletonList(new TNTxStrengthEffect.Builder(null).fuse(200).explosionStrength(50f).yStrength(1.3f).knockbackStrength(3f).build())));
	public static final RegistryObject<EntityType<PrimedLTNT>> TNT_FIREWORK = LuckyTNTMod.RH.registerTNTEntity("tnt_firework", new TNTFireworkEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> SAND_FIREWORK = LuckyTNTMod.RH.registerTNTEntity("sand_firework", new SandFireworkEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> ARROW_TNT = LuckyTNTMod.RH.registerTNTEntity("arrow_tnt", new ArrowTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> TIMER_TNT = LuckyTNTMod.RH.registerTNTEntity("timer_tnt", new StackedPrimedTNTEffect(new TimerTNTEffect(), Collections.singletonList(new TNTxStrengthEffect.Builder(null).fuse(120).explosionStrength(10f).randomVecLength(1.25f).knockbackStrength(1.5f).build())));
	public static final RegistryObject<EntityType<PrimedLTNT>> FLAT_TNT = LuckyTNTMod.RH.registerTNTEntity("flat_tnt", new FlatTNTEffect(() -> BlockRegistry.FLAT_TNT, 18, 9));
	public static final RegistryObject<EntityType<PrimedLTNT>> MININGFLAT_TNT = LuckyTNTMod.RH.registerTNTEntity("miningflat_tnt", new MiningflatTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> COMPACT_TNT = LuckyTNTMod.RH.registerTNTEntity("compact_tnt", new StackedPrimedTNTEffect(new TNTxStrengthEffect.Builder(() -> BlockRegistry.COMPACT_TNT).fuse(120).explosionStrength(10f).randomVecLength(1.25f).knockbackStrength(1.5f).build(), Collections.singletonList(new CompactTNTEffect())));
	public static final RegistryObject<EntityType<PrimedLTNT>> ANIMAL_TNT = LuckyTNTMod.RH.registerTNTEntity("animal_tnt", new AnimalTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> METEOR_TNT = LuckyTNTMod.RH.registerTNTEntity("meteor_tnt", new MeteorTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> SPIRAL_TNT = LuckyTNTMod.RH.registerTNTEntity("spiral_tnt", new SpiralTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> ERUPTING_TNT = LuckyTNTMod.RH.registerTNTEntity("erupting_tnt", new EruptingTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> GROVE_TNT = LuckyTNTMod.RH.registerTNTEntity("grove_tnt", new GroveTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> ENDER_TNT = LuckyTNTMod.RH.registerTNTEntity("ender_tnt", new EnderTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> METEOR_SHOWER = LuckyTNTMod.RH.registerTNTEntity("meteor_shower", new MeteorShowerEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> INVERTED_TNT = LuckyTNTMod.RH.registerTNTEntity("inverted_tnt", new InvertedTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> NUCLEAR_TNT = LuckyTNTMod.RH.registerTNTEntity("nuclear_tnt", new NuclearTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> CHEMICAL_TNT = LuckyTNTMod.RH.registerTNTEntity("chemical_tnt", new ChemicalTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> REACTION_TNT = LuckyTNTMod.RH.registerTNTEntity("reaction_tnt", new ReactionTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> EASTER_EGG = LuckyTNTMod.RH.registerTNTEntity("easter_egg", new EasterEggEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> DAY_TNT = LuckyTNTMod.RH.registerTNTEntity("day_tnt", new DayTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> NIGHT_TNT = LuckyTNTMod.RH.registerTNTEntity("night_tnt", new NightTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> VILLAGE_DEFENSE = LuckyTNTMod.RH.registerTNTEntity("village_defense", new VillageDefenseEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> ZOMBIE_APOCALYPSE = LuckyTNTMod.RH.registerTNTEntity("zombie_apocalypse", new ZombieApocalypseEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> SHATTERPROOF_TNT = LuckyTNTMod.RH.registerTNTEntity("shatterproof_tnt", new ShatterproofTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> GRAVEL_FIREWORK = LuckyTNTMod.RH.registerTNTEntity("gravel_firework", new GravelFireworkEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> LAVA_OCEAN_TNT = LuckyTNTMod.RH.registerTNTEntity("lava_ocean_tnt", new LavaOceanTNTEffect());
	public static final RegistryObject<EntityType<LivingPrimedLTNT>> ATTACKING_TNT = LuckyTNTMod.RH.registerLivingTNTEntity(LuckyTNTMod.entityRegistry, "attacking_tnt", (EntityType<LivingPrimedLTNT> type, Level level) -> new LivingPrimedLTNT(type, level, new TNTxStrengthEffect.Builder(() -> BlockRegistry.ATTACKING_TNT).fuse(400).explosionStrength(10f).randomVecLength(1.25f).knockbackStrength(1.5f).build()) {		
		@Override
		public void registerGoals() {
			super.registerGoals();
			targetSelector.addGoal(0, new NearestAttackableTargetGoal<Player>(this, Player.class, false, false));
			goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, false));
			goalSelector.addGoal(2, new OpenDoorGoal(this, true));
			goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
			goalSelector.addGoal(4, new RandomLookAroundGoal(this));
			goalSelector.addGoal(5, new FloatGoal(this));
		}	
	}, 5f, 1024f, 0.4f, 1f, true);
	public static final RegistryObject<EntityType<LivingPrimedLTNT>> WALKING_TNT = LuckyTNTMod.RH.registerLivingTNTEntity(LuckyTNTMod.entityRegistry, "walking_tnt", (EntityType<LivingPrimedLTNT> type, Level level) -> new LivingPrimedLTNT(type, level, new TNTxStrengthEffect.Builder(() -> BlockRegistry.WALKING_TNT).fuse(400).explosionStrength(10f).randomVecLength(1.25f).knockbackStrength(1.5f).build()) {		
		@Override
		public void registerGoals() {
			super.registerGoals();
			goalSelector.addGoal(0, new RandomStrollGoal(this, 1));
			goalSelector.addGoal(1, new RandomLookAroundGoal(this));
			goalSelector.addGoal(2, new FloatGoal(this));
		}	
	}, 5f, 1024f, 0.4f, 1f, true);
	public static final RegistryObject<EntityType<PrimedLTNT>> WOOL_TNT = LuckyTNTMod.RH.registerTNTEntity("wool_tnt", new WoolTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> SAY_GOODBYE = LuckyTNTMod.RH.registerTNTEntity("say_goodbye", new SayGoodbyeEffect());
	//public static final RegistryObject<EntityType<PrimedLTNT>> ANGRY_MINERS = LuckyTNTMod.RH.registerTNTEntity("angry_miners", new LavaOceanTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> WITHERING_TNT = LuckyTNTMod.RH.registerTNTEntity("withering_tnt", new WitheringTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> NUCLEAR_WASTE_TNT = LuckyTNTMod.RH.registerTNTEntity("nuclear_waste_tnt", new NuclearWasteTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> STATIC_TNT = LuckyTNTMod.RH.registerTNTEntity("static_tnt", new StackedPrimedTNTEffect(new StaticTNTEffect(), Collections.singletonList(new TNTxStrengthEffect.Builder(() -> BlockRegistry.TNT).build())));
	public static final RegistryObject<EntityType<PrimedLTNT>> PUMPKIN_BOMB = LuckyTNTMod.RH.registerTNTEntity("pumpkin_bomb", new PumpkinBombEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> SMOKE_TNT = LuckyTNTMod.RH.registerTNTEntity("smoke_tnt", new SmokeTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> TROLL_TNT = LuckyTNTMod.RH.registerTNTEntity("troll_tnt", new TrollTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> TROLL_TNT_MK2 = LuckyTNTMod.RH.registerTNTEntity("troll_tnt_mk2", new TrollTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> TROLL_TNT_MK3 = LuckyTNTMod.RH.registerTNTEntity("troll_tnt_mk3", new TrollTNTMk3Effect());
	public static final RegistryObject<EntityType<PrimedLTNT>> CLUSTER_BOMB_TNT = LuckyTNTMod.RH.registerTNTEntity("cluster_bomb_tnt", new ClusterBombTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> AIR_STRIKE = LuckyTNTMod.RH.registerTNTEntity("air_strike", new AirStrikeEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> SPAMMING_TNT = LuckyTNTMod.RH.registerTNTEntity("spamming_tnt", new SpammingTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> BOUNCING_TNT = LuckyTNTMod.RH.registerTNTEntity("bouncing_tnt", new BouncingTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> ROULETTE_TNT = LuckyTNTMod.RH.registerTNTEntity("roulette_tnt", new RouletteTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> SENSOR_TNT = LuckyTNTMod.RH.registerTNTEntity("sensor_tnt", new SensorTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> RAINBOW_FIREWORK = LuckyTNTMod.RH.registerTNTEntity("rainbow_firework", new RainbowFireworkEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> XRAY_TNT = LuckyTNTMod.RH.registerTNTEntity("xray_tnt", new XRayTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> FARMING_TNT = LuckyTNTMod.RH.registerTNTEntity("farming_tnt", new FarmingTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> PHANTOM_TNT = LuckyTNTMod.RH.registerTNTEntity("phantom_tnt", new PhantomTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> SWAP_TNT = LuckyTNTMod.RH.registerTNTEntity("swap_tnt", new SwapTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> IGNITER_TNT = LuckyTNTMod.RH.registerTNTEntity("igniter_tnt", new IgniterTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> MULTIPLYING_TNT = LuckyTNTMod.RH.registerTNTEntity("multiplying_tnt", new MultiplyingTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> BUTTER_TNT = LuckyTNTMod.RH.registerTNTEntity("butter_tnt", new ButterTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> TUNNELING_TNT = LuckyTNTMod.RH.registerTNTEntity("tunneling_tnt", new TunnelingTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> PHYSICS_TNT = LuckyTNTMod.RH.registerTNTEntity("physics_tnt", new PhysicsTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> ORE_TNT = LuckyTNTMod.RH.registerTNTEntity(LuckyTNTMod.entityRegistry, "ore_tnt", () -> EntityType.Builder.<PrimedLTNT>of(PrimedOreTNT::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).fireImmune().sized(1f, 1f).build("ore_tnt"));
	public static final RegistryObject<EntityType<PrimedLTNT>> REDSTONE_TNT = LuckyTNTMod.RH.registerTNTEntity("redstone_tnt", new RedstoneTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> RANDOM_TNT = LuckyTNTMod.RH.registerTNTEntity("random_tnt", new RandomTNTEffect());
	//public static final RegistryObject<EntityType<PrimedLTNT>> TURRET_TNT = LuckyTNTMod.RH.registerTNTEntity("turret_tnt", new TurretTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> PULSE_TNT = LuckyTNTMod.RH.registerTNTEntity("pulse_tnt", new PulseTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> DIVIDING_TNT = LuckyTNTMod.RH.registerTNTEntity("dividing_tnt", new DividingTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> PICKY_TNT = LuckyTNTMod.RH.registerTNTEntity("picky_tnt", new PickyTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> BIG_TNT = LuckyTNTMod.RH.registerTNTEntity("big_tnt", new BigTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> ICE_METEOR_TNT = LuckyTNTMod.RH.registerTNTEntity("ice_meteor_tnt", new IceMeteorTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> HONEY_TNT = LuckyTNTMod.RH.registerTNTEntity("honey_tnt", new HoneyTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> EATING_TNT = LuckyTNTMod.RH.registerTNTEntity("eating_tnt", new EatingTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> LUSH_TNT = LuckyTNTMod.RH.registerTNTEntity("lush_tnt", new LushTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> GEODE_TNT = LuckyTNTMod.RH.registerTNTEntity("geode_tnt", new GeodeTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> NETHER_GROVE_TNT = LuckyTNTMod.RH.registerTNTEntity("nether_grove_tnt", new NetherGroveTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> DRIPSTONE_TNT = LuckyTNTMod.RH.registerTNTEntity("dripstone_tnt", new DripstoneTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> GRAVEYARD_TNT = LuckyTNTMod.RH.registerTNTEntity("graveyard_tnt", new GraveyardTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> REPLAY_TNT = LuckyTNTMod.RH.registerTNTEntity("replay_tnt", new ReplayTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> END_TNT = LuckyTNTMod.RH.registerTNTEntity("end_tnt", new EndTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> CHRISTMAS_TNT = LuckyTNTMod.RH.registerTNTEntity("christmas_tnt", new ChristmasTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> EARTHQUAKE_TNT = LuckyTNTMod.RH.registerTNTEntity("earthquake_tnt", new EarthquakeTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> GLOBAL_DISASTER = LuckyTNTMod.RH.registerTNTEntity("global_disaster", new GlobalDisasterEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> HEAVENS_GATE = LuckyTNTMod.RH.registerTNTEntity("heavens_gate", new HeavensGateEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> HELLS_GATE = LuckyTNTMod.RH.registerTNTEntity("hells_gate", new HellsGateEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> MANKINDS_MARK = LuckyTNTMod.RH.registerTNTEntity("mankinds_mark", new MankindsMarkEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> POSEIDONS_WAVE = LuckyTNTMod.RH.registerTNTEntity("poseidons_wave", new PoseidonsWaveEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> HEXAHEDRON = LuckyTNTMod.RH.registerTNTEntity("hexahedron", new HexahedronEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> MOUNTAINTOP_REMOVAL = LuckyTNTMod.RH.registerTNTEntity("mountaintop_removal", new MountaintopRemovalEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> DUST_BOWL = LuckyTNTMod.RH.registerTNTEntity("dust_bowl", new DustBowlEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> THE_REVOLUTION = LuckyTNTMod.RH.registerTNTEntity("the_revolution", new TheRevolutionEffect());

	//Projectile
	public static final RegistryObject<EntityType<LExplosiveProjectile>> METEOR = LuckyTNTMod.RH.registerExplosiveProjectile("meteor", new MeteorEffect(), 2f, false);
	public static final RegistryObject<EntityType<LExplosiveProjectile>> SPIRAL_PROJECTILE = LuckyTNTMod.RH.registerExplosiveProjectile("spiral_projectile", new StackedPrimedTNTEffect(new SpiralTNTEffect(), Collections.singletonList(new TNTxStrengthEffect.Builder(null).explosionStrength(10f).randomVecLength(1.25f).knockbackStrength(1.5f).build())));
	public static final RegistryObject<EntityType<LExplosiveProjectile>> ERUPTING_PROJECTILE = LuckyTNTMod.RH.registerExplosiveProjectile("erupting_projectile", new StackedPrimedTNTEffect(new EruptingTNTEffect(), Collections.singletonList(new TNTxStrengthEffect.Builder(null).explosionStrength(10f).randomVecLength(1.25f).fire(true).knockbackStrength(1.5f).build())), 1f, false);
	public static final RegistryObject<EntityType<LExplosiveProjectile>> MINI_METEOR = LuckyTNTMod.RH.registerExplosiveProjectile("mini_meteor", new StackedPrimedTNTEffect(new MiniMeteorEffect(), Collections.singletonList(new TNTxStrengthEffect.Builder(null).explosionStrength(7).randomVecLength(1.25f).fire(true).build())), 1f, false);
	public static final RegistryObject<EntityType<LExplosiveProjectile>> CHEMICAL_PROJECTILE = LuckyTNTMod.RH.registerExplosiveProjectile("chemical_projectile", new ChemicalTNTEffect());
	public static final RegistryObject<EntityType<LExplosiveProjectile>> CLUSTER_BOMB = LuckyTNTMod.RH.registerExplosiveProjectile("cluster_bomb", new ClusterBombEffect());
	public static final RegistryObject<EntityType<LExplosiveProjectile>> SHRAPNEL = LuckyTNTMod.RH.registerExplosiveProjectile("shrapnel", new ShrapnelEffect(), 0.25f, true);
	public static final RegistryObject<EntityType<LExplosiveProjectile>> BOMB = LuckyTNTMod.RH.registerExplosiveProjectile("bomb", new BombEffect());
	public static final RegistryObject<EntityType<LExplosiveProjectile>> ICE_METEOR = LuckyTNTMod.RH.registerExplosiveProjectile("ice_meteor", new IceMeteorEffect(), 2f, false);
	
	//BlockEntities
	public static final RegistryObject<BlockEntityType<SmokeTNTBlockEntity>> SMOKE_TNT_BLOCK_ENTITY = LuckyTNTMod.blockEntityRegistry.register("smoke_tnt_block_entity", () -> BlockEntityType.Builder.of(SmokeTNTBlockEntity::new, BlockRegistry.SMOKE_TNT.get()).build(null));
}
