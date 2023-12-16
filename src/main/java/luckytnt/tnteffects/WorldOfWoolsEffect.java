package luckytnt.tnteffects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joml.Math;
import org.joml.Vector3f;

import com.mojang.datafixers.util.Pair;

import luckytnt.event.LevelEvents;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseCoralPlantTypeBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;

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
	
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		List<Pair<BlockPos, Block>> blocks = new ArrayList<>();
		
		ExplosionHelper.doSphericalExplosion(ent.getLevel(), ent.getPos(), 100, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				MapColor color = state.getMapColor(level, pos);
				if(color != MapColor.NONE & !state.getCollisionShape(level, pos, CollisionContext.empty()).isEmpty() && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(level)) <= 200) {
					if(WHITE.contains(color)) {
						blocks.add(Pair.of(pos, Blocks.WHITE_WOOL));
					} else if(LIGHT_GRAY.contains(color)) {
						blocks.add(Pair.of(pos, Blocks.LIGHT_GRAY_WOOL));
					} else if(GRAY.contains(color)) {
						blocks.add(Pair.of(pos, Blocks.GRAY_WOOL));
					} else if(BLACK.contains(color)) {
						blocks.add(Pair.of(pos, Blocks.BLACK_WOOL));
					} else if(BROWN.contains(color)) {
						blocks.add(Pair.of(pos, Blocks.BROWN_WOOL));
					} else if(RED.contains(color)) {
						blocks.add(Pair.of(pos, Blocks.RED_WOOL));
					} else if(ORANGE.contains(color)) {
						blocks.add(Pair.of(pos, Blocks.ORANGE_WOOL));
					} else if(YELLOW.contains(color)) {
						blocks.add(Pair.of(pos, Blocks.YELLOW_WOOL));
					} else if(LIME.contains(color)) {
						blocks.add(Pair.of(pos, Blocks.LIME_WOOL));
					} else if(GREEN.contains(color)) {
						blocks.add(Pair.of(pos, Blocks.GREEN_WOOL));
					} else if(CYAN.contains(color)) {
						blocks.add(Pair.of(pos, Blocks.CYAN_WOOL));
					} else if(LIGHT_BLUE.contains(color)) {
						blocks.add(Pair.of(pos, Blocks.LIGHT_BLUE_WOOL));
					} else if(BLUE.contains(color)) {
						blocks.add(Pair.of(pos, Blocks.BLUE_WOOL));
					} else if(PURPLE.contains(color)) {
						blocks.add(Pair.of(pos, Blocks.PURPLE_WOOL));
					} else if(MAGENTA.contains(color)) {
						blocks.add(Pair.of(pos, Blocks.MAGENTA_WOOL));
					} else if(PINK.contains(color)) {
						blocks.add(Pair.of(pos, Blocks.PINK_WOOL));
					}
				}
				
				if((state.is(Blocks.WATER) || state.is(Blocks.BUBBLE_COLUMN) || state.getBlock() instanceof BaseCoralPlantTypeBlock) && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(level)) <= 200) {
					blocks.add(Pair.of(pos, Blocks.BLUE_STAINED_GLASS));
				}
				
				if(state.getBlock() == Blocks.SEAGRASS || state.getBlock() == Blocks.TALL_SEAGRASS || state.getBlock() == Blocks.KELP || state.getBlock() == Blocks.SEA_PICKLE || state.getBlock() == Blocks.KELP_PLANT) {
					blocks.add(Pair.of(pos, Blocks.GREEN_WOOL));
				}
				
				if(state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(level)) <= 200) {
					blocks.add(Pair.of(pos, Blocks.BLUE_STAINED_GLASS));
				}
				
				if(state.is(Blocks.LAVA) && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(level)) <= 200) {
					blocks.add(Pair.of(pos, Blocks.ORANGE_STAINED_GLASS));
				}
			}
		});
		
		for(Pair<BlockPos, Block> pair : blocks) {
			ent.getLevel().setBlock(pair.getFirst(), pair.getSecond().defaultBlockState(), 3);
		}
		
		for(int i = 0; i < 3 + new Random().nextInt(6); i++) {
			int x = new Random().nextInt(151) - 75;
			int z = new Random().nextInt(151) - 75;
			
			BlockPos origin = new BlockPos(Mth.floor(ent.x() + x), Mth.floor(LevelEvents.getTopBlock(ent.getLevel(), ent.x() + x, ent.z() + z, true) + 1), Mth.floor(ent.z() + z));
			boolean xOrZ = new Random().nextBoolean();
			int rr = 16 + new Random().nextInt(11);
			Block block = Blocks.RED_CONCRETE;
			
			for(int j = 0; j < 6; j++) {
				placeRing(ent, origin, block, rr, xOrZ);
				placeLegs(ent, origin, block, rr--, xOrZ);
				
				if(j == 0) {
					block = Blocks.ORANGE_CONCRETE;
				} else if(j == 1) {
					block = Blocks.YELLOW_CONCRETE;
				} else if(j == 2) {
					block = Blocks.LIME_CONCRETE;
				} else if(j == 3) {
					block = Blocks.BLUE_CONCRETE;
				} else if(j == 4) {
					block = Blocks.PURPLE_CONCRETE;
				}
			}
		}
		
		for(int i = 0; i <= 60 + new Random().nextInt(21); i++) {
			Sheep sheep = new Sheep(EntityType.SHEEP, ent.getLevel());
			
			int x = new Random().nextInt(151) - 75;
			int z = new Random().nextInt(151) - 75;
			
			sheep.setPos(ent.x() + x, LevelEvents.getTopBlock(ent.getLevel(), ent.x() + x, ent.z() + z, true) + 1, ent.z() + z);
			sheep.finalizeSpawn((ServerLevel)ent.getLevel(), ent.getLevel().getCurrentDifficultyAt(toBlockPos(ent.getPos())), MobSpawnType.MOB_SUMMONED, null, null);
			ent.getLevel().addFreshEntity(sheep);
		}
		
		BlockPos min = toBlockPos(ent.getPos()).offset(100, 100, 100);
		BlockPos max = toBlockPos(ent.getPos()).offset(-100, -100, -100);
		List<Sheep> list = ent.getLevel().getEntitiesOfClass(Sheep.class, new AABB(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ()));
		for(Sheep sheep : list) {
			sheep.setColor(randomColor());
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		for(int i = 0; i < 50; i++) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(20f, 20f, 20f), 1f), ent.x() + Math.random() * 2 - Math.random() * 2, ent.y() + 1D + Math.random() * 2 - Math.random() * 2, ent.z() + Math.random() * 2 - Math.random() * 2, 0, 0, 0);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.WORLD_OF_WOOLS.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 150;
	}
	
	public DyeColor randomColor() {
		int random = new Random().nextInt(DyeColor.values().length);
		return DyeColor.values()[random];
	}
	
	public void placeRing(IExplosiveEntity ent, BlockPos origin, Block block, int radius, boolean xOrZ) {
		if(xOrZ) {
			for(int offX = -radius - 1; offX <= radius + 1; offX++) {
				for(int offY = 0; offY <= radius + 1; offY++) {
					BlockPos pos = origin.offset(offX, offY, 0);
					double distance = Math.sqrt(offX * offX + offY * offY);
					if(distance > radius && distance <= (radius + 1) && ent.getLevel().getBlockState(pos).getExplosionResistance(ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 100) {
						ent.getLevel().setBlock(pos, block.defaultBlockState(), 3);
					}
				}
			}
		} else {
			for(int offZ = -radius - 1; offZ <= radius + 1; offZ++) {
				for(int offY = 0; offY <= radius + 1; offY++) {
					BlockPos pos = origin.offset(0, offY, offZ);
					double distance = Math.sqrt(offZ * offZ + offY * offY);
					if(distance > radius && distance <= (radius + 1) && ent.getLevel().getBlockState(pos).getExplosionResistance(ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 100) {
						ent.getLevel().setBlock(pos, block.defaultBlockState(), 3);
					}
				}
			}
		}
	}
	
	public void placeLegs(IExplosiveEntity ent, BlockPos origin, Block block, int radius, boolean xOrZ) {
		if(xOrZ) {
			for(int offY = -1; offY > -200; offY--) {
				BlockPos pos = origin.offset(radius + 1, offY, 0);
				if(ent.getLevel().getBlockState(pos).getCollisionShape(ent.getLevel(), pos, CollisionContext.empty()).isEmpty() && ent.getLevel().getBlockState(pos).getExplosionResistance(ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 100) {
					ent.getLevel().setBlock(pos, block.defaultBlockState(), 3);
				} else {
					break;
				}
			}
			
			for(int offY = -1; offY > -200; offY--) {
				BlockPos pos = origin.offset(-radius - 1, offY, 0);
				if(ent.getLevel().getBlockState(pos).getCollisionShape(ent.getLevel(), pos, CollisionContext.empty()).isEmpty() && ent.getLevel().getBlockState(pos).getExplosionResistance(ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 100) {
					ent.getLevel().setBlock(pos, block.defaultBlockState(), 3);
				} else {
					break;
				}
			}
		} else {
			for(int offY = -1; offY > -200; offY--) {
				BlockPos pos = origin.offset(0, offY, radius + 1);
				if(ent.getLevel().getBlockState(pos).getCollisionShape(ent.getLevel(), pos, CollisionContext.empty()).isEmpty() && ent.getLevel().getBlockState(pos).getExplosionResistance(ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 100) {
					ent.getLevel().setBlock(pos, block.defaultBlockState(), 3);
				} else {
					break;
				}
			}
			
			for(int offY = -1; offY > -200; offY--) {
				BlockPos pos = origin.offset(0, offY, -radius - 1);
				if(ent.getLevel().getBlockState(pos).getCollisionShape(ent.getLevel(), pos, CollisionContext.empty()).isEmpty() && ent.getLevel().getBlockState(pos).getExplosionResistance(ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 100) {
					ent.getLevel().setBlock(pos, block.defaultBlockState(), 3);
				} else {
					break;
				}
			}
		}
	}
}
