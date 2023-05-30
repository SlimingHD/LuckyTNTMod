package luckytnt.tnteffects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mojang.datafixers.util.Pair;
import com.mojang.math.Vector3f;

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
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;

public class WorldOfWoolsEffect extends PrimedTNTEffect {
	public static List<MaterialColor> WHITE = List.of(MaterialColor.SNOW, MaterialColor.QUARTZ, MaterialColor.TERRACOTTA_WHITE, MaterialColor.WOOL);
	public static List<MaterialColor> LIGHT_GRAY = List.of(MaterialColor.METAL, MaterialColor.CLAY, MaterialColor.COLOR_LIGHT_GRAY);
	public static List<MaterialColor> GRAY = List.of(MaterialColor.STONE, MaterialColor.COLOR_GRAY, MaterialColor.TERRACOTTA_CYAN, MaterialColor.DEEPSLATE);
	public static List<MaterialColor> BLACK = List.of(MaterialColor.COLOR_BLACK);
	public static List<MaterialColor> BROWN = List.of(MaterialColor.DIRT, MaterialColor.WOOD, MaterialColor.COLOR_BROWN, MaterialColor.PODZOL, MaterialColor.TERRACOTTA_BLACK, MaterialColor.TERRACOTTA_BROWN, MaterialColor.TERRACOTTA_GRAY, MaterialColor.TERRACOTTA_LIGHT_GRAY, MaterialColor.RAW_IRON);
	public static List<MaterialColor> RED = List.of(MaterialColor.FIRE, MaterialColor.COLOR_RED, MaterialColor.NETHER, MaterialColor.TERRACOTTA_RED, MaterialColor.CRIMSON_HYPHAE, MaterialColor.CRIMSON_NYLIUM, MaterialColor.TERRACOTTA_PINK);
	public static List<MaterialColor> ORANGE = List.of(MaterialColor.COLOR_ORANGE, MaterialColor.TERRACOTTA_ORANGE);
	public static List<MaterialColor> YELLOW = List.of(MaterialColor.SAND, MaterialColor.COLOR_YELLOW, MaterialColor.GOLD, MaterialColor.TERRACOTTA_YELLOW);
	public static List<MaterialColor> LIME = List.of(MaterialColor.GRASS, MaterialColor.COLOR_LIGHT_GREEN, MaterialColor.EMERALD, MaterialColor.GLOW_LICHEN);
	public static List<MaterialColor> GREEN = List.of(MaterialColor.PLANT, MaterialColor.COLOR_GREEN, MaterialColor.TERRACOTTA_LIGHT_GREEN, MaterialColor.TERRACOTTA_GREEN);
	public static List<MaterialColor> CYAN = List.of(MaterialColor.COLOR_CYAN, MaterialColor.WARPED_NYLIUM, MaterialColor.WARPED_STEM, MaterialColor.WARPED_WART_BLOCK);
	public static List<MaterialColor> LIGHT_BLUE = List.of(MaterialColor.ICE, MaterialColor.COLOR_LIGHT_BLUE, MaterialColor.DIAMOND);
	public static List<MaterialColor> BLUE = List.of(MaterialColor.WATER, MaterialColor.COLOR_BLUE, MaterialColor.LAPIS, MaterialColor.TERRACOTTA_LIGHT_BLUE);
	public static List<MaterialColor> PURPLE = List.of(MaterialColor.COLOR_PURPLE, MaterialColor.TERRACOTTA_BLUE, MaterialColor.WARPED_HYPHAE);
	public static List<MaterialColor> MAGENTA = List.of(MaterialColor.COLOR_MAGENTA, MaterialColor.TERRACOTTA_MAGENTA, MaterialColor.TERRACOTTA_PURPLE, MaterialColor.CRIMSON_STEM);
	public static List<MaterialColor> PINK = List.of(MaterialColor.COLOR_PINK);
	
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		List<Pair<BlockPos, Block>> blocks = new ArrayList<>();
		
		ExplosionHelper.doSphericalExplosion(ent.level(), ent.getPos(), 100, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				MaterialColor color = state.getMapColor(level, pos);
				if(color != MaterialColor.NONE & !state.getCollisionShape(level, pos, CollisionContext.empty()).isEmpty() && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(level)) <= 200) {
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
				
				if((state.getMaterial() == Material.WATER || state.getMaterial() == Material.BUBBLE_COLUMN || state.getBlock() instanceof BaseCoralPlantTypeBlock) && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(level)) <= 200) {
					blocks.add(Pair.of(pos, Blocks.BLUE_STAINED_GLASS));
				}
				
				if(state.getBlock() == Blocks.SEAGRASS || state.getBlock() == Blocks.TALL_SEAGRASS || state.getBlock() == Blocks.KELP || state.getBlock() == Blocks.SEA_PICKLE || state.getBlock() == Blocks.KELP_PLANT) {
					blocks.add(Pair.of(pos, Blocks.GREEN_WOOL));
				}
				
				if(state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(level)) <= 200) {
					blocks.add(Pair.of(pos, Blocks.BLUE_STAINED_GLASS));
				}
				
				if(state.getMaterial() == Material.LAVA && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(level)) <= 200) {
					blocks.add(Pair.of(pos, Blocks.ORANGE_STAINED_GLASS));
				}
			}
		});
		
		for(Pair<BlockPos, Block> pair : blocks) {
			ent.level().setBlock(pair.getFirst(), pair.getSecond().defaultBlockState(), 3);
		}
		
		for(int i = 0; i < 3 + new Random().nextInt(6); i++) {
			int x = new Random().nextInt(151) - 75;
			int z = new Random().nextInt(151) - 75;
			
			BlockPos origin = new BlockPos(ent.x() + x, LevelEvents.getTopBlock(ent.level(), ent.x() + x, ent.z() + z, true) + 1, ent.z() + z);
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
			Sheep sheep = new Sheep(EntityType.SHEEP, ent.level());
			
			int x = new Random().nextInt(151) - 75;
			int z = new Random().nextInt(151) - 75;
			
			sheep.setPos(ent.x() + x, LevelEvents.getTopBlock(ent.level(), ent.x() + x, ent.z() + z, true) + 1, ent.z() + z);
			sheep.finalizeSpawn((ServerLevel)ent.level(), ent.level().getCurrentDifficultyAt(new BlockPos(ent.getPos())), MobSpawnType.MOB_SUMMONED, null, null);
			ent.level().addFreshEntity(sheep);
		}
		
		List<Sheep> list = ent.level().getEntitiesOfClass(Sheep.class, new AABB(new BlockPos(ent.getPos()).offset(100, 100, 100), new BlockPos(ent.getPos()).offset(-100, -100, -100)));
		for(Sheep sheep : list) {
			sheep.setColor(randomColor());
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		for(int i = 0; i < 50; i++) {
			ent.level().addParticle(new DustParticleOptions(new Vector3f(20f, 20f, 20f), 1f), ent.x() + Math.random() * 2 - Math.random() * 2, ent.y() + 1D + Math.random() * 2 - Math.random() * 2, ent.z() + Math.random() * 2 - Math.random() * 2, 0, 0, 0);
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
					if(distance > radius && distance <= (radius + 1) && ent.level().getBlockState(pos).getExplosionResistance(ent.level(), pos, ImprovedExplosion.dummyExplosion(ent.level())) <= 100) {
						ent.level().setBlock(pos, block.defaultBlockState(), 3);
					}
				}
			}
		} else {
			for(int offZ = -radius - 1; offZ <= radius + 1; offZ++) {
				for(int offY = 0; offY <= radius + 1; offY++) {
					BlockPos pos = origin.offset(0, offY, offZ);
					double distance = Math.sqrt(offZ * offZ + offY * offY);
					if(distance > radius && distance <= (radius + 1) && ent.level().getBlockState(pos).getExplosionResistance(ent.level(), pos, ImprovedExplosion.dummyExplosion(ent.level())) <= 100) {
						ent.level().setBlock(pos, block.defaultBlockState(), 3);
					}
				}
			}
		}
	}
	
	public void placeLegs(IExplosiveEntity ent, BlockPos origin, Block block, int radius, boolean xOrZ) {
		if(xOrZ) {
			for(int offY = -1; offY > -200; offY--) {
				BlockPos pos = origin.offset(radius + 1, offY, 0);
				if(ent.level().getBlockState(pos).getCollisionShape(ent.level(), pos, CollisionContext.empty()).isEmpty() && ent.level().getBlockState(pos).getExplosionResistance(ent.level(), pos, ImprovedExplosion.dummyExplosion(ent.level())) <= 100) {
					ent.level().setBlock(pos, block.defaultBlockState(), 3);
				} else {
					break;
				}
			}
			
			for(int offY = -1; offY > -200; offY--) {
				BlockPos pos = origin.offset(-radius - 1, offY, 0);
				if(ent.level().getBlockState(pos).getCollisionShape(ent.level(), pos, CollisionContext.empty()).isEmpty() && ent.level().getBlockState(pos).getExplosionResistance(ent.level(), pos, ImprovedExplosion.dummyExplosion(ent.level())) <= 100) {
					ent.level().setBlock(pos, block.defaultBlockState(), 3);
				} else {
					break;
				}
			}
		} else {
			for(int offY = -1; offY > -200; offY--) {
				BlockPos pos = origin.offset(0, offY, radius + 1);
				if(ent.level().getBlockState(pos).getCollisionShape(ent.level(), pos, CollisionContext.empty()).isEmpty() && ent.level().getBlockState(pos).getExplosionResistance(ent.level(), pos, ImprovedExplosion.dummyExplosion(ent.level())) <= 100) {
					ent.level().setBlock(pos, block.defaultBlockState(), 3);
				} else {
					break;
				}
			}
			
			for(int offY = -1; offY > -200; offY--) {
				BlockPos pos = origin.offset(0, offY, -radius - 1);
				if(ent.level().getBlockState(pos).getCollisionShape(ent.level(), pos, CollisionContext.empty()).isEmpty() && ent.level().getBlockState(pos).getExplosionResistance(ent.level(), pos, ImprovedExplosion.dummyExplosion(ent.level())) <= 100) {
					ent.level().setBlock(pos, block.defaultBlockState(), 3);
				} else {
					break;
				}
			}
		}
	}
}
