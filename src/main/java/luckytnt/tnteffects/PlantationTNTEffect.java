package luckytnt.tnteffects; 

import java.util.LinkedList;
import java.util.List;

import org.joml.Vector3f;

import com.mojang.datafixers.util.Pair;

import luckytnt.event.LevelEvents;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterFullBlockExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class PlantationTNTEffect extends PrimedTNTEffect {

	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		
		ExplosionHelper.createCylindricalCrater(level, entity.getPos(), 41, 41, 200, new FilterAirExplosionRule(
			LogicExplosionRule.or(
				FilterBlockExplosionRule.builder().filterForTags(List.of(BlockTags.LEAVES, BlockTags.LOGS)).build(new AlwaysExplosionRule()), 
				LogicExplosionRule.not(
					new FilterFullBlockExplosionRule(new AlwaysExplosionRule()), 
					new AlwaysExplosionRule()
				), 
				new AlwaysExplosionRule()
			)
		));

		LinkedList<Pair<BlockPos, Integer>> placedCrops = new LinkedList<>();
		
		int x = Mth.floor(entity.x());
		int z = Mth.floor(entity.z());
		int maxDistanceSqr = 41 * 41;
		int maxCropDistanceSqr = 40 * 40;
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(level);
		for (int offX = -41; offX <= 41; offX++) {
			for (int offZ = -41; offZ <= 41; offZ++) {
				int distanceSqr = offX * offX + offZ * offZ;
				if (distanceSqr <= maxDistanceSqr) {
					BlockPos pos = new BlockPos(x + offX, LevelEvents.getTopBlock(level, x + offX, z + offZ, false), z + offZ);
					BlockPos posAbove = pos.above();
					BlockState stateAbove = level.getBlockState(posAbove);
					if (stateAbove.isAir()) {
						placeCrop(level, pos, dummy, distanceSqr > maxCropDistanceSqr);
						placedCrops.add(Pair.of(pos, distanceSqr));
					}
				}
			}
		}
		
		int[] waterDistances = new int[]{7 * 7, 8 * 8, 15 * 15, 16 * 16, 23 * 23, 24 * 24, 31 * 31, 32 * 32, 39 * 39, 40 * 40};
		for (Pair<BlockPos, Integer> pair : placedCrops) {
			int distanceSqr = pair.getSecond();
			boolean placeWater = false;
			for (int i = 0; i < waterDistances.length; i += 2) {
				if (distanceSqr > waterDistances[i] && distanceSqr <= waterDistances[i + 1]) {
					placeWater = true;
					break;
				}
			}
			if (placeWater) {
				BlockPos pos = pair.getFirst();
				BlockPos posAbove = pos.above();
				BlockState stateAbove = level.getBlockState(posAbove);
				if (Math.max(stateAbove.getBlock().getExplosionResistance(), stateAbove.getFluidState().getExplosionResistance()) <= 200f) {
					level.setBlockAndUpdate(posAbove, Blocks.AIR.defaultBlockState());
					stateAbove.getBlock().wasExploded(level, posAbove, dummy);
				}
				placeWater(level, pos, dummy);
			}
		}
	}

	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0.5f, 0.1f), 1f), entity.x(), entity.y() + 1d, entity.z(), 0d, 0d, 0d);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.PLANTATION_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
	
	@SuppressWarnings("deprecation")
	private static void placeCrop(Level level, BlockPos pos, ImprovedExplosion dummy, boolean stemCrop) {
		BlockState crop = stemCrop ? randomStemCrop(level.getRandom()) : randomCrop(level.getRandom());
		
		BlockPos posAbove = pos.above();
		BlockState state = level.getBlockState(pos);
		BlockState stateAbove = level.getBlockState(posAbove);
		
		boolean placedFarmland = false;
		if (Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) <= 200f) {
			level.setBlockAndUpdate(pos, Blocks.FARMLAND.defaultBlockState().setValue(FarmBlock.MOISTURE, 7));
			state.getBlock().wasExploded(level, pos, dummy);
			placedFarmland = true;
		}
		if (placedFarmland && Math.max(stateAbove.getBlock().getExplosionResistance(), stateAbove.getFluidState().getExplosionResistance()) <= 200f) {
			level.setBlockAndUpdate(posAbove, crop);
			state.getBlock().wasExploded(level, posAbove, dummy);
		}
	}
	
	@SuppressWarnings("deprecation")
	private static void placeWater(Level level, BlockPos pos, ImprovedExplosion dummy) {
		BlockState state = level.getBlockState(pos);
		if (Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) > 200f) {
			return;
		}
		
		for (Direction dir : Direction.Plane.HORIZONTAL) {
			BlockPos posRelative = pos.relative(dir);
			BlockState stateRelative = level.getBlockState(posRelative);
			if (!stateRelative.is(Blocks.FARMLAND) && !stateRelative.is(Blocks.WATER) && !stateRelative.isCollisionShapeFullBlock(level, posRelative)) {
				placeCrop(level, posRelative, dummy, false);
			}
		}
		
		BlockPos posBelow = pos.below();
		BlockState stateBelow = level.getBlockState(posBelow);
		if (!stateBelow.is(Blocks.WATER) && !stateBelow.isCollisionShapeFullBlock(level, posBelow) && Math.max(stateBelow.getBlock().getExplosionResistance(), stateBelow.getFluidState().getExplosionResistance()) <= 200f) {
			level.setBlockAndUpdate(posBelow, Blocks.DIRT.defaultBlockState());
			stateBelow.getBlock().wasExploded(level, posBelow, dummy);
		}
		
		boolean shouldPlaceWater = true;
		for (Direction dir : Direction.Plane.HORIZONTAL) {
			BlockPos posRelative = pos.relative(dir);
			BlockState stateRelative = level.getBlockState(posRelative);
			if (!stateRelative.is(Blocks.FARMLAND) && !stateRelative.is(Blocks.WATER) && !stateRelative.isCollisionShapeFullBlock(level, posRelative)) {
				shouldPlaceWater = false;
				break;
			}
		}
		if (shouldPlaceWater) {
			level.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
			state.getBlock().wasExploded(level, pos, dummy);
		} else {
			placeCrop(level, pos, dummy, false);
		}
	}
	
	private static BlockState randomCrop(RandomSource random) {
		return switch(random.nextInt(4)) {
			case 1 -> Blocks.POTATOES.defaultBlockState().setValue(BlockStateProperties.AGE_7, random.nextInt(8));
			case 2 -> Blocks.WHEAT.defaultBlockState().setValue(BlockStateProperties.AGE_7, random.nextInt(8));
			case 3 -> Blocks.BEETROOTS.defaultBlockState().setValue(BlockStateProperties.AGE_3, random.nextInt(4));
			default -> Blocks.CARROTS.defaultBlockState().setValue(BlockStateProperties.AGE_7, random.nextInt(8));
		};
	}
	
	private static BlockState randomStemCrop(RandomSource random) {
		return random.nextBoolean() ? Blocks.PUMPKIN_STEM.defaultBlockState().setValue(BlockStateProperties.AGE_7, random.nextInt(8)) : Blocks.MELON_STEM.defaultBlockState().setValue(BlockStateProperties.AGE_7, random.nextInt(8));
	}
}
