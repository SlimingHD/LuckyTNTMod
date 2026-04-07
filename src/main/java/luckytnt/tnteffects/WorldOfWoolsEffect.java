package luckytnt.tnteffects;

import java.util.List;
import java.util.Map;

import org.joml.Math;
import org.joml.Vector3f;

import com.google.common.collect.ImmutableMap;

import luckytnt.event.LevelEvents;
import luckytnt.registry.BlockRegistry;
import luckytnt.rules.FilterMapColorExplosionRule;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.SimpleExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.AABB;

public class WorldOfWoolsEffect extends PrimedTNTEffect {
	
	public static List<MapColor> WHITE = List.of(MapColor.SNOW, MapColor.QUARTZ, MapColor.TERRACOTTA_WHITE, MapColor.WOOL);
	public static List<MapColor> LIGHT_GRAY = List.of(MapColor.METAL, MapColor.CLAY, MapColor.COLOR_LIGHT_GRAY);
	public static List<MapColor> GRAY = List.of(MapColor.STONE, MapColor.COLOR_GRAY, MapColor.TERRACOTTA_CYAN, MapColor.DEEPSLATE);
	public static List<MapColor> BLACK = List.of(MapColor.COLOR_BLACK);
	public static List<MapColor> BROWN = List.of(MapColor.DIRT, MapColor.WOOD, MapColor.COLOR_BROWN, MapColor.PODZOL, MapColor.TERRACOTTA_BLACK, MapColor.TERRACOTTA_BROWN, MapColor.TERRACOTTA_GRAY, MapColor.TERRACOTTA_LIGHT_GRAY, MapColor.RAW_IRON);
	public static List<MapColor> RED = List.of(MapColor.FIRE, MapColor.COLOR_RED, MapColor.NETHER, MapColor.TERRACOTTA_RED, MapColor.CRIMSON_HYPHAE, MapColor.CRIMSON_NYLIUM, MapColor.TERRACOTTA_PINK);
	public static List<MapColor> ORANGE = List.of(MapColor.COLOR_ORANGE, MapColor.TERRACOTTA_ORANGE);
	public static List<MapColor> YELLOW = List.of(MapColor.SAND, MapColor.COLOR_YELLOW, MapColor.GOLD, MapColor.TERRACOTTA_YELLOW);
	public static List<MapColor> LIME = List.of(MapColor.GRASS, MapColor.COLOR_LIGHT_GREEN, MapColor.EMERALD, MapColor.GLOW_LICHEN);
	public static List<MapColor> GREEN = List.of(MapColor.PLANT, MapColor.COLOR_GREEN, MapColor.TERRACOTTA_LIGHT_GREEN, MapColor.TERRACOTTA_GREEN);
	public static List<MapColor> CYAN = List.of(MapColor.COLOR_CYAN, MapColor.WARPED_NYLIUM, MapColor.WARPED_STEM, MapColor.WARPED_WART_BLOCK);
	public static List<MapColor> LIGHT_BLUE = List.of(MapColor.ICE, MapColor.COLOR_LIGHT_BLUE, MapColor.DIAMOND);
	public static List<MapColor> BLUE = List.of(MapColor.WATER, MapColor.COLOR_BLUE, MapColor.LAPIS, MapColor.TERRACOTTA_LIGHT_BLUE);
	public static List<MapColor> PURPLE = List.of(MapColor.COLOR_PURPLE, MapColor.TERRACOTTA_BLUE, MapColor.WARPED_HYPHAE);
	public static List<MapColor> MAGENTA = List.of(MapColor.COLOR_MAGENTA, MapColor.TERRACOTTA_MAGENTA, MapColor.TERRACOTTA_PURPLE, MapColor.CRIMSON_STEM);
	public static List<MapColor> PINK = List.of(MapColor.COLOR_PINK);
	
	private static final Map<Integer, Block> BLOCK_BY_DISTANCE = ImmutableMap.of(0, Blocks.RED_CONCRETE, 1, Blocks.ORANGE_CONCRETE, 2, Blocks.YELLOW_CONCRETE, 3, Blocks.LIME_CONCRETE, 4, Blocks.BLUE_CONCRETE, 5, Blocks.PURPLE_CONCRETE);
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), 100, 200, new FilterAirExplosionRule(
			new StackedExplosionRule(
				FilterBlockExplosionRule.builder().filterForBlocks(Blocks.WATER, Blocks.BUBBLE_COLUMN).build(
					new SimpleExplosionRule(Blocks.BLUE_STAINED_GLASS.defaultBlockState())
				),
				FilterBlockExplosionRule.builder().filterForBlocks(Blocks.SEAGRASS, Blocks.TALL_SEAGRASS, Blocks.KELP, Blocks.KELP_PLANT).build(
					new SimpleExplosionRule(Blocks.GREEN_WOOL.defaultBlockState())
				),
				FilterBlockExplosionRule.applyOnlyWhen(Blocks.LAVA, new SimpleExplosionRule(Blocks.ORANGE_STAINED_GLASS.defaultBlockState())),
				new FilterMapColorExplosionRule(WHITE, new SimpleExplosionRule(Blocks.WHITE_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(LIGHT_GRAY, new SimpleExplosionRule(Blocks.LIGHT_GRAY_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(GRAY, new SimpleExplosionRule(Blocks.GRAY_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(BLACK, new SimpleExplosionRule(Blocks.BLACK_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(BROWN, new SimpleExplosionRule(Blocks.BROWN_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(RED, new SimpleExplosionRule(Blocks.RED_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(ORANGE, new SimpleExplosionRule(Blocks.ORANGE_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(YELLOW, new SimpleExplosionRule(Blocks.YELLOW_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(LIME, new SimpleExplosionRule(Blocks.LIME_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(GREEN, new SimpleExplosionRule(Blocks.GREEN_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(CYAN, new SimpleExplosionRule(Blocks.CYAN_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(LIGHT_BLUE, new SimpleExplosionRule(Blocks.LIGHT_BLUE_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(BLUE, new SimpleExplosionRule(Blocks.BLUE_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(PURPLE, new SimpleExplosionRule(Blocks.PURPLE_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(MAGENTA, new SimpleExplosionRule(Blocks.MAGENTA_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(PINK, new SimpleExplosionRule(Blocks.PINK_WOOL.defaultBlockState()))
			)
		));
		
		RandomSource random = entity.getLevel().getRandom();
		for (int i = 0; i < 3 + random.nextInt(6); i++) {
			int x = random.nextInt(151) - 75;
			int z = random.nextInt(151) - 75;

			BlockPos origin = new BlockPos(Mth.floor(entity.x() + x), Mth.floor(LevelEvents.getTopBlock(entity.getLevel(), entity.x() + x, entity.z() + z, true) + 1), Mth.floor(entity.z() + z));
			boolean xAxis = random.nextBoolean();
			int radius = 16 + random.nextInt(11);

			for (int j = 0; j < 6; j++) {
				Block block = BLOCK_BY_DISTANCE.get(j);
				placeRing(entity, origin, block, radius, xAxis);
				placeLegs(entity, origin, block, radius--, xAxis);
			}
		}
		
		for (int i = 0; i <= 60 + random.nextInt(21); i++) {
			Sheep sheep = new Sheep(EntityType.SHEEP, entity.getLevel());

			int x = random.nextInt(151) - 75;
			int z = random.nextInt(151) - 75;

			sheep.setPos(entity.x() + x, LevelEvents.getTopBlock(entity.getLevel(), entity.x() + x, entity.z() + z, true) + 1, entity.z() + z);
			sheep.finalizeSpawn((ServerLevel) entity.getLevel(), entity.getLevel().getCurrentDifficultyAt(toBlockPos(entity.getPos())), MobSpawnType.MOB_SUMMONED, null, null);
			entity.getLevel().addFreshEntity(sheep);
		}

		List<Sheep> list = entity.getLevel().getEntitiesOfClass(Sheep.class, new AABB(toBlockPos(entity.getPos()).offset(100, 100, 100), toBlockPos(entity.getPos()).offset(-100, -100, -100)));
		for (Sheep sheep : list) {
			sheep.setColor(randomColor(random));
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		RandomSource random = entity.getLevel().getRandom();
		for (int i = 0; i < 50; i++) {
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(20f, 20f, 20f), 1f), entity.x() + random.nextDouble() * 2 - random.nextDouble() * 2, entity.y() + 1D + random.nextDouble() * 2 - random.nextDouble() * 2, entity.z() + random.nextDouble() * 2 - random.nextDouble() * 2, 0, 0, 0);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.WORLD_OF_WOOLS.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 150;
	}
	
	private static void placeRing(IExplosiveEntity entity, BlockPos origin, Block block, int radius, boolean xAxis) {
		Level level = entity.getLevel();
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(level);
		
		if (xAxis) {
			for (int offX = -radius - 1; offX <= radius + 1; offX++) {
				for (int offY = 0; offY <= radius + 1; offY++) {
					BlockPos pos = origin.offset(offX, offY, 0);
					double distance = Math.sqrt(offX * offX + offY * offY);
					if (distance > radius && distance <= (radius + 1) && level.getBlockState(pos).getExplosionResistance(level, pos, dummy) <= 100) {
						level.setBlock(pos, block.defaultBlockState(), 3);
					}
				}
			}
		} else {
			for (int offZ = -radius - 1; offZ <= radius + 1; offZ++) {
				for (int offY = 0; offY <= radius + 1; offY++) {
					BlockPos pos = origin.offset(0, offY, offZ);
					double distance = Math.sqrt(offZ * offZ + offY * offY);
					if (distance > radius && distance <= (radius + 1) && level.getBlockState(pos).getExplosionResistance(level, pos, dummy) <= 100) {
						level.setBlock(pos, block.defaultBlockState(), 3);
					}
				}
			}
		}
	}
	
	private static void placeLegs(IExplosiveEntity entity, BlockPos origin, Block block, int radius, boolean xAxis) {
		Level level = entity.getLevel();
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(level);
		
		if (xAxis) {
			for (int offY = -1; offY > -200; offY--) {
				BlockPos pos = origin.offset(radius + 1, offY, 0);
				BlockState state = level.getBlockState(pos);
				if (!Block.isShapeFullBlock(state.getCollisionShape(level, pos)) && state.getExplosionResistance(level, pos, dummy) <= 100) {
					level.setBlock(pos, block.defaultBlockState(), 3);
				} else {
					break;
				}
			}

			for (int offY = -1; offY > -200; offY--) {
				BlockPos pos = origin.offset(-radius - 1, offY, 0);
				BlockState state = level.getBlockState(pos);
				if (!Block.isShapeFullBlock(state.getCollisionShape(level, pos)) && state.getExplosionResistance(level, pos, dummy) <= 100) {
					level.setBlock(pos, block.defaultBlockState(), 3);
				} else {
					break;
				}
			}
		} else {
			for (int offY = -1; offY > -200; offY--) {
				BlockPos pos = origin.offset(0, offY, radius + 1);
				BlockState state = level.getBlockState(pos);
				if (!Block.isShapeFullBlock(state.getCollisionShape(level, pos)) && state.getExplosionResistance(level, pos, dummy) <= 100) {
					level.setBlock(pos, block.defaultBlockState(), 3);
				} else {
					break;
				}
			}

			for (int offY = -1; offY > -200; offY--) {
				BlockPos pos = origin.offset(0, offY, -radius - 1);
				BlockState state = level.getBlockState(pos);
				if (!Block.isShapeFullBlock(state.getCollisionShape(level, pos)) && state.getExplosionResistance(level, pos, dummy) <= 100) {
					level.setBlock(pos, block.defaultBlockState(), 3);
				} else {
					break;
				}
			}
		}
	}
	
	private static DyeColor randomColor(RandomSource random) {
		return DyeColor.values()[random.nextInt(DyeColor.values().length)];
	}
}
