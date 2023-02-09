package luckytnt.registry;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.joml.Vector3f;

import luckytnt.LuckyTNTMod;
import luckytnt.block.entity.ItemFireworkBlockEntity;
import luckytnt.block.entity.SmokeTNTBlockEntity;
import luckytnt.entity.AngryMiner;
import luckytnt.entity.HailstoneProjectile;
import luckytnt.entity.PrimedItemFirework;
import luckytnt.entity.PrimedOreTNT;
import luckytnt.entity.PrimedReplayTNT;
import luckytnt.entity.PrimedResetTNT;
import luckytnt.tnteffects.*;
import luckytnt.tnteffects.projectile.BombEffect;
import luckytnt.tnteffects.projectile.ChicxulubMeteorEffect;
import luckytnt.tnteffects.projectile.ClusterBombEffect;
import luckytnt.tnteffects.projectile.DiggingDynamiteEffect;
import luckytnt.tnteffects.projectile.DynamiteFireworkEffect;
import luckytnt.tnteffects.projectile.FloatingDynamiteEffect;
import luckytnt.tnteffects.projectile.HailstoneEffect;
import luckytnt.tnteffects.projectile.IceMeteorEffect;
import luckytnt.tnteffects.projectile.LightningDynamiteEffect;
import luckytnt.tnteffects.projectile.MeteorDynamiteEffect;
import luckytnt.tnteffects.projectile.MeteorEffect;
import luckytnt.tnteffects.projectile.MiniMeteorEffect;
import luckytnt.tnteffects.projectile.ShrapnelEffect;
import luckytnt.tnteffects.projectile.TsarBombaBombEffect;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.entity.LivingPrimedLTNT;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.tnteffects.GeneralDynamiteEffect;
import luckytntlib.util.tnteffects.StackedPrimedTNTEffect;
import luckytntlib.util.tnteffects.TNTXStrengthEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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

	public static final TNTXStrengthEffect.Builder WEAK_TNT_EFFECT = new TNTXStrengthEffect.Builder().explosionStrength(2f).knockbackStrength(0.5f);	
	public static final TNTXStrengthEffect.Builder TNT_EFFECT = new TNTXStrengthEffect.Builder();	
	public static final TNTXStrengthEffect.Builder TNT_X5_EFFECT = new TNTXStrengthEffect.Builder().explosionStrength(10f).randomVecLength(1.25f).knockbackStrength(1.5f);
	public static final TNTXStrengthEffect.Builder TNT_X20_EFFECT = new TNTXStrengthEffect.Builder().explosionStrength(20f).randomVecLength(1.5f).knockbackStrength(2f);
	public static final TNTXStrengthEffect.Builder TNT_X100_EFFECT = new TNTXStrengthEffect.Builder().explosionStrength(50f).yStrength(1.3f).knockbackStrength(3f);
	public static final TNTXStrengthEffect.Builder TNT_X500_EFFECT = new TNTXStrengthEffect.Builder().explosionStrength(80f).yStrength(1.3f).knockbackStrength(5f);
	public static final TNTXStrengthEffect.Builder TNT_X2000_EFFECT = new TNTXStrengthEffect.Builder().explosionStrength(160f).resistanceImpact(0.167f).randomVecLength(0.05f).knockbackStrength(15f).isStrongExplosion(true);
	
	//TNT
	public static final RegistryObject<EntityType<PrimedLTNT>> TNT = LuckyTNTMod.RH.registerTNTEntity("tnt", TNT_EFFECT.buildTNT(() -> BlockRegistry.TNT, 80));

	//Normal TNT
	public static final RegistryObject<EntityType<PrimedLTNT>> TNT_X5 = LuckyTNTMod.RH.registerTNTEntity("tnt_x5", TNT_X5_EFFECT.buildTNT(() -> BlockRegistry.TNT_X5, 120));
	public static final RegistryObject<EntityType<PrimedLTNT>> TNT_X20 = LuckyTNTMod.RH.registerTNTEntity("tnt_x20", TNT_X20_EFFECT.buildTNT(() -> BlockRegistry.TNT_X20, 160));
	public static final RegistryObject<EntityType<PrimedLTNT>> TNT_X100 = LuckyTNTMod.RH.registerTNTEntity("tnt_x100", TNT_X100_EFFECT.buildTNT(() -> BlockRegistry.TNT_X100, 200));
	public static final RegistryObject<EntityType<PrimedLTNT>> TNT_X500 = LuckyTNTMod.RH.registerTNTEntity("tnt_x500", TNT_X500_EFFECT.buildTNT(() -> BlockRegistry.TNT_X500, 240));
	public static final RegistryObject<EntityType<PrimedLTNT>> COBBLESTONE_HOUSE_TNT = LuckyTNTMod.RH.registerTNTEntity("cooblestone_house_tnt", new HouseTNTEffect(() -> BlockRegistry.COBBLESTONE_HOUSE_TNT, "cobblehouse", -5, -3));
	public static final RegistryObject<EntityType<PrimedLTNT>> WOOD_HOUSE_TNT = LuckyTNTMod.RH.registerTNTEntity("woodhouse_tnt", new HouseTNTEffect(() -> BlockRegistry.WOOD_HOUSE_TNT, "woodhouse", -5, -3));
	public static final RegistryObject<EntityType<PrimedLTNT>> BRICK_HOUSE_TNT = LuckyTNTMod.RH.registerTNTEntity("brickhouse_tnt", new HouseTNTEffect(() -> BlockRegistry.BRICK_HOUSE_TNT, "brickhouse", -5, -3));
	public static final RegistryObject<EntityType<PrimedLTNT>> DIGGING_TNT = LuckyTNTMod.RH.registerTNTEntity("digging_tnt", new DiggingTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> DRILLING_TNT = LuckyTNTMod.RH.registerTNTEntity("drilling_tnt", new DrillingTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> SPHERE_TNT = LuckyTNTMod.RH.registerTNTEntity("sphere_tnt", new SphereTNTEffect(9));
	public static final RegistryObject<EntityType<PrimedLTNT>> FLOATING_ISLAND = LuckyTNTMod.RH.registerTNTEntity("floating_island", new FloatingIslandEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> OCEAN_TNT = LuckyTNTMod.RH.registerTNTEntity("ocean_tnt", new OceanTNTEffect(() -> BlockRegistry.OCEAN_TNT, 30, 10, 10));
	public static final RegistryObject<EntityType<PrimedLTNT>> HELLFIRE_TNT = LuckyTNTMod.RH.registerTNTEntity("hellfire_tnt", new HellfireTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> FIRE_TNT = LuckyTNTMod.RH.registerTNTEntity("fire_tnt", new FireTNTEffect(10));
	public static final RegistryObject<EntityType<PrimedLTNT>> SNOW_TNT = LuckyTNTMod.RH.registerTNTEntity("snow_tnt", new SnowTNTEffect(10));
	public static final RegistryObject<EntityType<PrimedLTNT>> FREEZE_TNT = LuckyTNTMod.RH.registerTNTEntity("freeze_tnt", new FreezeTNTEffect(10));
	public static final RegistryObject<EntityType<PrimedLTNT>> VAPORIZE_TNT = LuckyTNTMod.RH.registerTNTEntity("vaporize_tnt", new VaporizeTNTEffect(12));
	public static final RegistryObject<EntityType<PrimedLTNT>> GRAVITY_TNT = LuckyTNTMod.RH.registerTNTEntity("gravity_tnt", new GravityTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> LIGHTNING_TNT = LuckyTNTMod.RH.registerTNTEntity("lightning_tnt", new LightningTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> CUBIC_TNT = LuckyTNTMod.RH.registerTNTEntity("cubic_tnt", new CubicTNTEffect(3));
	public static final RegistryObject<EntityType<PrimedLTNT>> FLOATING_TNT = LuckyTNTMod.RH.registerTNTEntity("floating_tnt", new StackedPrimedTNTEffect(new FloatingTNTEffect(), Collections.singletonList(TNT_X100_EFFECT.build())));
	public static final RegistryObject<EntityType<PrimedLTNT>> TNT_FIREWORK = LuckyTNTMod.RH.registerTNTEntity("tnt_firework", new TNTFireworkEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> SAND_FIREWORK = LuckyTNTMod.RH.registerTNTEntity("sand_firework", new SandFireworkEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> ARROW_TNT = LuckyTNTMod.RH.registerTNTEntity("arrow_tnt", new ArrowTNTEffect(300));
	public static final RegistryObject<EntityType<PrimedLTNT>> TIMER_TNT = LuckyTNTMod.RH.registerTNTEntity("timer_tnt", new StackedPrimedTNTEffect(new TimerTNTEffect(), Collections.singletonList(TNT_X5_EFFECT.build())));
	public static final RegistryObject<EntityType<PrimedLTNT>> FLAT_TNT = LuckyTNTMod.RH.registerTNTEntity("flat_tnt", new FlatTNTEffect(() -> BlockRegistry.FLAT_TNT, 18, 9));
	public static final RegistryObject<EntityType<PrimedLTNT>> MININGFLAT_TNT = LuckyTNTMod.RH.registerTNTEntity("miningflat_tnt", new MiningflatTNTEffect(30, 9));
	public static final RegistryObject<EntityType<PrimedLTNT>> COMPACT_TNT = LuckyTNTMod.RH.registerTNTEntity("compact_tnt", new StackedPrimedTNTEffect(TNT_X5_EFFECT.buildTNT(() -> BlockRegistry.COMPACT_TNT, 120, true), Collections.singletonList(new CompactTNTEffect(0.05D, 9f, () -> BlockRegistry.TNT))));
	public static final RegistryObject<EntityType<PrimedLTNT>> ANIMAL_TNT = LuckyTNTMod.RH.registerTNTEntity("animal_tnt", new AnimalTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> METEOR_TNT = LuckyTNTMod.RH.registerTNTEntity("meteor_tnt", new MeteorTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> SPIRAL_TNT = LuckyTNTMod.RH.registerTNTEntity("spiral_tnt", new SpiralTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> ERUPTING_TNT = LuckyTNTMod.RH.registerTNTEntity("erupting_tnt", new EruptingTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> GROVE_TNT = LuckyTNTMod.RH.registerTNTEntity("grove_tnt", new GroveTNTEffect(20));
	public static final RegistryObject<EntityType<PrimedLTNT>> ENDER_TNT = LuckyTNTMod.RH.registerTNTEntity("ender_tnt", new EnderTNTEffect(20));
	public static final RegistryObject<EntityType<PrimedLTNT>> METEOR_SHOWER = LuckyTNTMod.RH.registerTNTEntity("meteor_shower", new MeteorShowerEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> INVERTED_TNT = LuckyTNTMod.RH.registerTNTEntity("inverted_tnt", new InvertedTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> NUCLEAR_TNT = LuckyTNTMod.RH.registerTNTEntity("nuclear_tnt", new NuclearTNTEffect(50));
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
	public static final RegistryObject<EntityType<LivingPrimedLTNT>> ATTACKING_TNT = LuckyTNTMod.RH.registerLivingTNTEntity(LuckyTNTMod.entityRegistry, "attacking_tnt", (EntityType<LivingPrimedLTNT> type, Level level) -> new LivingPrimedLTNT(type, level, TNT_X5_EFFECT.buildTNT(() -> BlockRegistry.ATTACKING_TNT, 400)) {		
		@Override
		public void registerGoals() {
			super.registerGoals();
			targetSelector.addGoal(0, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, false, false, PREDICATE));
			goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, false));
			goalSelector.addGoal(2, new OpenDoorGoal(this, true));
			goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
			goalSelector.addGoal(4, new RandomLookAroundGoal(this));
			goalSelector.addGoal(5, new FloatGoal(this));
		}	
	}, 5f, 1024f, 0.4f, 1f, true);
	public static final RegistryObject<EntityType<LivingPrimedLTNT>> WALKING_TNT = LuckyTNTMod.RH.registerLivingTNTEntity(LuckyTNTMod.entityRegistry, "walking_tnt", (EntityType<LivingPrimedLTNT> type, Level level) -> new LivingPrimedLTNT(type, level, TNT_X5_EFFECT.buildTNT(() -> BlockRegistry.ATTACKING_TNT, 400)) {		
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
	public static final RegistryObject<EntityType<PrimedLTNT>> ANGRY_MINERS = LuckyTNTMod.RH.registerTNTEntity("angry_miners", new AngryMinersEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> WITHERING_TNT = LuckyTNTMod.RH.registerTNTEntity("withering_tnt", new WitheringTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> NUCLEAR_WASTE_TNT = LuckyTNTMod.RH.registerTNTEntity("nuclear_waste_tnt", new NuclearWasteTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> STATIC_TNT = LuckyTNTMod.RH.registerTNTEntity("static_tnt", new StackedPrimedTNTEffect(new StaticTNTEffect(), Collections.singletonList(TNT_EFFECT.build())));
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
	public static final RegistryObject<EntityType<PrimedLTNT>> TURRET_TNT = LuckyTNTMod.RH.registerTNTEntity("turret_tnt", new TurretTNTEffect());
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
	public static final RegistryObject<EntityType<PrimedLTNT>> GRAVEYARD_TNT = LuckyTNTMod.RH.registerTNTEntity("graveyard_tnt", new StackedPrimedTNTEffect(new GraveyardTNTEffect(), Collections.singletonList(new HouseTNTEffect(() -> BlockRegistry.GRAVEYARD_TNT, "graveyard", -10, -10))));
	public static final RegistryObject<EntityType<PrimedLTNT>> REPLAY_TNT = LuckyTNTMod.RH.registerTNTEntity(LuckyTNTMod.entityRegistry, "replay_tnt", () -> EntityType.Builder.<PrimedLTNT>of(PrimedReplayTNT::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).fireImmune().sized(1f, 1f).build("replay_tnt"));
	public static final RegistryObject<EntityType<PrimedLTNT>> END_TNT = LuckyTNTMod.RH.registerTNTEntity("end_tnt", new EndTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> CHRISTMAS_TNT = LuckyTNTMod.RH.registerTNTEntity("christmas_tnt", new StackedPrimedTNTEffect(new ChristmasTNTEffect(), Collections.singletonList(new SnowTNTEffect(50))));
	public static final RegistryObject<EntityType<PrimedLTNT>> EARTHQUAKE_TNT = LuckyTNTMod.RH.registerTNTEntity("earthquake_tnt", new EarthquakeTNTEffect());
	
	//God TNT
	public static final RegistryObject<EntityType<PrimedLTNT>> GLOBAL_DISASTER = LuckyTNTMod.RH.registerTNTEntity("global_disaster", new GlobalDisasterEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> HEAVENS_GATE = LuckyTNTMod.RH.registerTNTEntity("heavens_gate", new HeavensGateEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> HELLS_GATE = LuckyTNTMod.RH.registerTNTEntity("hells_gate", new HellsGateEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> MANKINDS_MARK = LuckyTNTMod.RH.registerTNTEntity("mankinds_mark", new MankindsMarkEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> POSEIDONS_WAVE = LuckyTNTMod.RH.registerTNTEntity("poseidons_wave", new PoseidonsWaveEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> HEXAHEDRON = LuckyTNTMod.RH.registerTNTEntity("hexahedron", new HexahedronEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> MOUNTAINTOP_REMOVAL = LuckyTNTMod.RH.registerTNTEntity("mountaintop_removal", new MountaintopRemovalEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> DUST_BOWL = LuckyTNTMod.RH.registerTNTEntity("dust_bowl", new DustBowlEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> THE_REVOLUTION = LuckyTNTMod.RH.registerTNTEntity("the_revolution", new TheRevolutionEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> POMPEII = LuckyTNTMod.RH.registerTNTEntity("pompeii", new PompeiiEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> CHICXULUB = LuckyTNTMod.RH.registerTNTEntity("chicxulub", new ChicxulubEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> UNBREAKABLE_TNT = LuckyTNTMod.RH.registerTNTEntity("unbreakable_tnt", new UnbreakableTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> END_GATE = LuckyTNTMod.RH.registerTNTEntity("end_gate", new EndGateEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> DENSE_TNT = LuckyTNTMod.RH.registerTNTEntity("dense_tnt", new StackedPrimedTNTEffect(new TNTXStrengthEffect.Builder().explosionStrength(12f).randomVecLength(1.1f).knockbackStrength(1.5f).buildTNT(() -> BlockRegistry.DENSE_TNT, 160, true), Collections.singletonList(new CompactTNTEffect(0.02D, 11f, () -> BlockRegistry.COMPACT_TNT))));
	public static final RegistryObject<EntityType<PrimedLTNT>> HYPERION = LuckyTNTMod.RH.registerTNTEntity("hyperion", new HyperionEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> TNT_X2000 = LuckyTNTMod.RH.registerTNTEntity("tnt_x2000", TNT_X2000_EFFECT.buildTNT(() -> BlockRegistry.TNT_X2000, 400));
	public static final RegistryObject<EntityType<PrimedLTNT>> TSAR_BOMBA = LuckyTNTMod.RH.registerTNTEntity("tsar_bomba", new TsarBombaEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> TOXIC_CLOUDS = LuckyTNTMod.RH.registerTNTEntity("toxic_clouds", new DisasterTNTEffect("toxic_clouds", false));
	public static final RegistryObject<EntityType<PrimedLTNT>> DISASTER_CLEARER = LuckyTNTMod.RH.registerTNTEntity("disaster_clearer", new DisasterTNTEffect("clear", false));
	public static final RegistryObject<EntityType<PrimedLTNT>> WITHER_STORM = LuckyTNTMod.RH.registerTNTEntity("wither_storm", new WitherStormEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> LEAPING_TNT = LuckyTNTMod.RH.registerTNTEntity("leaping_tnt", new LeapingTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> RUSSIAN_ROULETTE = LuckyTNTMod.RH.registerTNTEntity("russian_roulette", new RussianRouletteEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> KNOCKBACK_TNT = LuckyTNTMod.RH.registerTNTEntity("knockback_tnt", new KnockbackTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> MIDAS_TNT = LuckyTNTMod.RH.registerTNTEntity("midas_tnt", new MidasTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> NEW_YEARS_FIREWORK = LuckyTNTMod.RH.registerTNTEntity("new_years_firework", new NewYearsFireworkEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> PULSAR_TNT = LuckyTNTMod.RH.registerTNTEntity("pulsar_tnt", new PulsarTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> LIGHTNING_STORM = LuckyTNTMod.RH.registerTNTEntity("lightning_storm", new LightningStormEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> SILK_TOUCH_TNT = LuckyTNTMod.RH.registerTNTEntity("silk_touch_tnt", new SilkTouchTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> ITEM_FIREWORK = LuckyTNTMod.RH.registerTNTEntity(LuckyTNTMod.entityRegistry, "item_firework", () -> EntityType.Builder.<PrimedLTNT>of(PrimedItemFirework::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).fireImmune().sized(1f, 1f).build("item_firework"));
	public static final RegistryObject<EntityType<PrimedLTNT>> ANIMAL_KINGDOM = LuckyTNTMod.RH.registerTNTEntity("animal_kingdom", new AnimalKingdomEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> ICE_AGE = LuckyTNTMod.RH.registerTNTEntity("ice_age", new DisasterTNTEffect("ice_age", true));
	public static final RegistryObject<EntityType<PrimedLTNT>> GIANT_TNT = LuckyTNTMod.RH.registerTNTEntity("giant_tnt", new GiantTNTEffect(), 10f, true);
	public static final RegistryObject<EntityType<PrimedLTNT>> MIMIC_TNT = LuckyTNTMod.RH.registerTNTEntity("mimic_tnt", new MimicTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> REVERSED_TNT = LuckyTNTMod.RH.registerTNTEntity("reversed_tnt", new ReversedTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> ENTITY_FIREWORK = LuckyTNTMod.RH.registerTNTEntity("entity_firework", new EntityFireworkEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> CUSTOM_TNT = LuckyTNTMod.RH.registerTNTEntity("custom_tnt", new CustomTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> RESET_TNT = LuckyTNTMod.RH.registerTNTEntity(LuckyTNTMod.entityRegistry, "reset_tnt", () -> EntityType.Builder.<PrimedLTNT>of(PrimedResetTNT::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).fireImmune().sized(1f, 1f).build("reset_tnt"));
	public static final RegistryObject<EntityType<LivingPrimedLTNT>> VICIOUS_TNT = LuckyTNTMod.RH.registerLivingTNTEntity(LuckyTNTMod.entityRegistry, "vicious_tnt", (EntityType<LivingPrimedLTNT> type, Level level) -> new LivingPrimedLTNT(type, level, TNT_X20_EFFECT.buildTNT(() -> BlockRegistry.VICIOUS_TNT, 400)) {		
		@Override
		public void registerGoals() {
			super.registerGoals();
			targetSelector.addGoal(0, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, false, false, PREDICATE));
			goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, false));
			goalSelector.addGoal(2, new OpenDoorGoal(this, true));
			goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
			goalSelector.addGoal(4, new RandomLookAroundGoal(this));
			goalSelector.addGoal(5, new FloatGoal(this));
		}	
	}, 10f, 1024f, 0.5f, 1f, true);
	public static final RegistryObject<EntityType<PrimedLTNT>> HUNGRY_TNT = LuckyTNTMod.RH.registerTNTEntity("hungry_tnt", new HungryTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> SINKHOLE_TNT = LuckyTNTMod.RH.registerTNTEntity("sinkhole_tnt", new SinkholeTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> FIRESTORM_TNT = LuckyTNTMod.RH.registerTNTEntity("firestorm_tnt", new FirestormTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> SNOWSTORM_TNT = LuckyTNTMod.RH.registerTNTEntity("snowstorm_tnt", new SnowstormTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> ACIDIC_TNT = LuckyTNTMod.RH.registerTNTEntity("acidic_tnt", new AcidicTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> CATALYST_TNT = LuckyTNTMod.RH.registerTNTEntity("catalyst_tnt", new CatalystTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> CANNON_TNT = LuckyTNTMod.RH.registerTNTEntity("cannon_tnt", new CannonTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> PLANTATION_TNT = LuckyTNTMod.RH.registerTNTEntity("plantation_tnt", new PlantationTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> GOTTHARD_TUNNEL = LuckyTNTMod.RH.registerTNTEntity("gotthard_tunnel", new GotthardTunnelEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> LEVITATING_TNT = LuckyTNTMod.RH.registerTNTEntity("levitating_tnt", new StackedPrimedTNTEffect(TNT_X500_EFFECT.buildTNT(() -> BlockRegistry.LEVITATING_TNT, 160, false), List.of(new LevitatingTNTEffect())));
	public static final RegistryObject<EntityType<PrimedLTNT>> SQUARING_TNT = LuckyTNTMod.RH.registerTNTEntity("squaring_tnt", new SquaringTNTEffect());
	public static final RegistryObject<EntityType<PrimedLTNT>> MINERAL_TNT = LuckyTNTMod.RH.registerTNTEntity("mineral_tnt", new MineralTNTEffect());
	
	//Dynamite
	public static final RegistryObject<EntityType<LExplosiveProjectile>> DYNAMITE = LuckyTNTMod.RH.registerExplosiveProjectile("dynamite", WEAK_TNT_EFFECT.buildDynamite(() -> ItemRegistry.DYNAMITE));
	public static final RegistryObject<EntityType<LExplosiveProjectile>> DYNAMITE_X5 = LuckyTNTMod.RH.registerExplosiveProjectile("dynamite_x5", TNT_EFFECT.buildDynamite(() -> ItemRegistry.DYNAMITE_X5));
	public static final RegistryObject<EntityType<LExplosiveProjectile>> DYNAMITE_X20 = LuckyTNTMod.RH.registerExplosiveProjectile("dynamite_x20", TNT_X5_EFFECT.buildDynamite(() -> ItemRegistry.DYNAMITE_X20));
	public static final RegistryObject<EntityType<LExplosiveProjectile>> DYNAMITE_X100 = LuckyTNTMod.RH.registerExplosiveProjectile("dynamite_x100", new TNTXStrengthEffect.Builder().explosionStrength(25f).randomVecLength(1.5f).knockbackStrength(2.5f).buildDynamite(() -> ItemRegistry.DYNAMITE_X100));
	public static final RegistryObject<EntityType<LExplosiveProjectile>> DYNAMITE_X500 = LuckyTNTMod.RH.registerExplosiveProjectile("dynamite_x500", new TNTXStrengthEffect.Builder().explosionStrength(60f).yStrength(1.4f).knockbackStrength(3.5f).buildDynamite(() -> ItemRegistry.DYNAMITE_X500));
	public static final RegistryObject<EntityType<LExplosiveProjectile>> FIRE_DYNAMITE = LuckyTNTMod.RH.registerExplosiveProjectile("fire_dynamite", new GeneralDynamiteEffect(() -> ItemRegistry.FIRE_DYNAMITE, ParticleTypes.FLAME, new FireTNTEffect(5)));
	public static final RegistryObject<EntityType<LExplosiveProjectile>> SNOW_DYNAMITE = LuckyTNTMod.RH.registerExplosiveProjectile("snow_dynamite", new GeneralDynamiteEffect(() -> ItemRegistry.SNOW_DYNAMITE, new SnowTNTEffect(5)));
	public static final RegistryObject<EntityType<LExplosiveProjectile>> DYNAMITE_FIREWORK = LuckyTNTMod.RH.registerExplosiveProjectile("dynamite_firework", new DynamiteFireworkEffect());
	public static final RegistryObject<EntityType<LExplosiveProjectile>> NUCLEAR_DYNAMITE = LuckyTNTMod.RH.registerExplosiveProjectile("nuclear_dynamite", new GeneralDynamiteEffect(() -> ItemRegistry.NUCLEAR_DYNAMITE, new DustParticleOptions(new Vector3f(0.9f, 1f, 0f), 1f), new NuclearTNTEffect(25)));
	public static final RegistryObject<EntityType<LExplosiveProjectile>> FREEZE_DYNAMITE = LuckyTNTMod.RH.registerExplosiveProjectile("freeze_dynamite", new GeneralDynamiteEffect(() -> ItemRegistry.FREEZE_DYNAMITE, new FreezeTNTEffect(5)));
	public static final RegistryObject<EntityType<LExplosiveProjectile>> FLOATING_DYNAMITE = LuckyTNTMod.RH.registerExplosiveProjectile("floating_dynamite", new FloatingDynamiteEffect());
	public static final RegistryObject<EntityType<LExplosiveProjectile>> SPHERE_DYNAMITE = LuckyTNTMod.RH.registerExplosiveProjectile("sphere_dynamite", new GeneralDynamiteEffect(() -> ItemRegistry.SPHERE_DYNAMITE, new SphereTNTEffect(5)));
	public static final RegistryObject<EntityType<LExplosiveProjectile>> FLAT_DYNAMITE = LuckyTNTMod.RH.registerExplosiveProjectile("flat_dynamite", new GeneralDynamiteEffect(() -> ItemRegistry.FLAT_DYNAMITE, new FlatTNTEffect(9, 5)));
	public static final RegistryObject<EntityType<LExplosiveProjectile>> MININGFLAT_DYNAMITE = LuckyTNTMod.RH.registerExplosiveProjectile("miningflat_dynamite", new GeneralDynamiteEffect(() -> ItemRegistry.MININGFLAT_DYNAMITE, new MiningflatTNTEffect(15, 5)));
	public static final RegistryObject<EntityType<LExplosiveProjectile>> VAPORIZE_DYNAMITE = LuckyTNTMod.RH.registerExplosiveProjectile("vaporize_dynamite", new GeneralDynamiteEffect(() -> ItemRegistry.VAPORIZE_DYNAMITE, new VaporizeTNTEffect(6)));
	public static final RegistryObject<EntityType<LExplosiveProjectile>> METEOR_DYNAMITE = LuckyTNTMod.RH.registerExplosiveProjectile("meteor_dynamite", new MeteorDynamiteEffect());
	public static final RegistryObject<EntityType<LExplosiveProjectile>> CUBIC_DYNAMITE = LuckyTNTMod.RH.registerExplosiveProjectile("cubic_dynamite", new GeneralDynamiteEffect(() -> ItemRegistry.CUBIC_DYNAMITE, new CubicTNTEffect(2)));
	public static final RegistryObject<EntityType<LExplosiveProjectile>> GROVE_DYNAMITE = LuckyTNTMod.RH.registerExplosiveProjectile("grove_dynamite", new GeneralDynamiteEffect(() -> ItemRegistry.GROVE_DYNAMITE, new GroveTNTEffect(10)));
	public static final RegistryObject<EntityType<LExplosiveProjectile>> ENDER_DYNAMITE = LuckyTNTMod.RH.registerExplosiveProjectile("ender_dynamite", new GeneralDynamiteEffect(() -> ItemRegistry.ENDER_DYNAMITE, new DustParticleOptions(new Vector3f(0.6f, 0f, 0.9f), 1f), new EnderTNTEffect(10)));
	public static final RegistryObject<EntityType<LExplosiveProjectile>> ARROW_DYNAMITE = LuckyTNTMod.RH.registerExplosiveProjectile("arrow_dynamite", new GeneralDynamiteEffect(() -> ItemRegistry.ARROW_DYNAMITE, new ArrowTNTEffect(150)));
	public static final RegistryObject<EntityType<LExplosiveProjectile>> LIGHTNING_DYNAMITE = LuckyTNTMod.RH.registerExplosiveProjectile("lightning_dynamite", new LightningDynamiteEffect());
	public static final RegistryObject<EntityType<LExplosiveProjectile>> DIGGING_DYNAMITE = LuckyTNTMod.RH.registerExplosiveProjectile("digging_dynamite", new DiggingDynamiteEffect());
	public static final RegistryObject<EntityType<LExplosiveProjectile>> COMPACT_DYNAMITE = LuckyTNTMod.RH.registerExplosiveProjectile("compact_dynamite", new GeneralDynamiteEffect(() -> ItemRegistry.COMPACT_DYNAMITE, new StackedPrimedTNTEffect(new TNTXStrengthEffect.Builder().explosionStrength(10f).randomVecLength(1.1f).knockbackStrength(1.5f).build(true), Collections.singletonList(new CompactTNTEffect(0.05f, 9f, () -> BlockRegistry.TNT)))));
	
	//Projectile
	public static final RegistryObject<EntityType<LExplosiveProjectile>> METEOR = LuckyTNTMod.RH.registerExplosiveProjectile("meteor", new MeteorEffect(40, 2f), 2f, false);
	public static final RegistryObject<EntityType<LExplosiveProjectile>> LITTLE_METEOR = LuckyTNTMod.RH.registerExplosiveProjectile("little_meteor", new MeteorEffect(20, 1.5f), 1.5f, false);
	public static final RegistryObject<EntityType<LExplosiveProjectile>> SPIRAL_PROJECTILE = LuckyTNTMod.RH.registerExplosiveProjectile("spiral_projectile", new StackedPrimedTNTEffect(new SpiralTNTEffect(), Collections.singletonList(TNT_X5_EFFECT.build())));
	public static final RegistryObject<EntityType<LExplosiveProjectile>> ERUPTING_PROJECTILE = LuckyTNTMod.RH.registerExplosiveProjectile("erupting_projectile", new StackedPrimedTNTEffect(new EruptingTNTEffect(), Collections.singletonList(TNT_X5_EFFECT.build(true))), 1f, false);
	public static final RegistryObject<EntityType<LExplosiveProjectile>> MINI_METEOR = LuckyTNTMod.RH.registerExplosiveProjectile("mini_meteor", new StackedPrimedTNTEffect(new MiniMeteorEffect(), Collections.singletonList(new TNTXStrengthEffect.Builder().explosionStrength(7).randomVecLength(1.25f).build(true))), 1f, false);
	public static final RegistryObject<EntityType<LExplosiveProjectile>> CHEMICAL_PROJECTILE = LuckyTNTMod.RH.registerExplosiveProjectile("chemical_projectile", new ChemicalTNTEffect());
	public static final RegistryObject<EntityType<LExplosiveProjectile>> CLUSTER_BOMB = LuckyTNTMod.RH.registerExplosiveProjectile("cluster_bomb", new ClusterBombEffect());
	public static final RegistryObject<EntityType<LExplosiveProjectile>> SHRAPNEL = LuckyTNTMod.RH.registerExplosiveProjectile("shrapnel", new ShrapnelEffect(), 0.25f, true);
	public static final RegistryObject<EntityType<LExplosiveProjectile>> BOMB = LuckyTNTMod.RH.registerExplosiveProjectile("bomb", new BombEffect());
	public static final RegistryObject<EntityType<LExplosiveProjectile>> ICE_METEOR = LuckyTNTMod.RH.registerExplosiveProjectile("ice_meteor", new IceMeteorEffect(), 2f, false);
	public static final RegistryObject<EntityType<LExplosiveProjectile>> POMPEII_PROJECTILE = LuckyTNTMod.RH.registerExplosiveProjectile("pompeii_projectile", new StackedPrimedTNTEffect(new PompeiiEffect(), Collections.singletonList(new TNTXStrengthEffect.Builder().explosionStrength(6f).randomVecLength(1.25f).knockbackStrength(1.5f).build(true))), 1f, false);
	public static final RegistryObject<EntityType<LExplosiveProjectile>> CHICXULUB_METEOR = LuckyTNTMod.RH.registerExplosiveProjectile("chicxulub_meteor", new ChicxulubMeteorEffect(), 4f, false);
	public static final RegistryObject<EntityType<LExplosiveProjectile>> TSAR_BOMBA_BOMB = LuckyTNTMod.RH.registerExplosiveProjectile("tsar_bomba_bomb", new TsarBombaBombEffect(), 1.2f, false);
	public static final RegistryObject<EntityType<LExplosiveProjectile>> PRESENT = LuckyTNTMod.RH.registerExplosiveProjectile("present", new StackedPrimedTNTEffect(new ChristmasTNTEffect(), Collections.singletonList(TNT_X5_EFFECT.build())));
	public static final RegistryObject<EntityType<LExplosiveProjectile>> ACIDIC_PROJECTILE = LuckyTNTMod.RH.registerExplosiveProjectile("acidic_projectile", new AcidicTNTEffect(), 1f, false);
	public static final RegistryObject<EntityType<LExplosiveProjectile>> HAILSTONE = LuckyTNTMod.entityRegistry.register("hailstone", () -> EntityType.Builder.<LExplosiveProjectile>of((EntityType<LExplosiveProjectile> type, Level level) -> new HailstoneProjectile(type, level, new HailstoneEffect()), MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).sized(0.1f, 0.1f).build("hailstone"));
	
	//Other
	public static RegistryObject<EntityType<AngryMiner>> ANGRY_MINER = LuckyTNTMod.entityRegistry.register("angry_miner", () -> EntityType.Builder.<AngryMiner>of(AngryMiner::new, MobCategory.MISC).sized(0.6f, 1.8f).build("angry_miner"));
	public static final RegistryObject<EntityType<PrimedLTNT>> TOXIC_CLOUD = LuckyTNTMod.RH.registerTNTEntity("toxic_cloud", new ToxicCloudEffect());
	
	//BlockEntities
	public static final RegistryObject<BlockEntityType<SmokeTNTBlockEntity>> SMOKE_TNT_BLOCK_ENTITY = LuckyTNTMod.blockEntityRegistry.register("smoke_tnt_block_entity", () -> BlockEntityType.Builder.of(SmokeTNTBlockEntity::new, BlockRegistry.SMOKE_TNT.get()).build(null));
	public static final RegistryObject<BlockEntityType<ItemFireworkBlockEntity>> ITEM_FIREWORK_BLOCK_ENTITY = LuckyTNTMod.blockEntityRegistry.register("item_firework_block_entity", () -> BlockEntityType.Builder.of(ItemFireworkBlockEntity::new, BlockRegistry.ITEM_FIREWORK.get()).build(null));

	public static Predicate<LivingEntity> PREDICATE = living -> { 
		return living instanceof Player player && !player.isCreative() && !player.isSpectator() ? true : false;
	};
}
