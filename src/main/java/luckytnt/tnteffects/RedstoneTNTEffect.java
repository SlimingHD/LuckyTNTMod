package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ComparatorMode;

public class RedstoneTNTEffect extends PrimedTNTEffect {

	private static final Direction[] DIRECTIONS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.DOWN};
	
	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random  = level.getRandom();
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(level);
		
		ExplosionHelper.customSurfaceExplosion(level, entity.getPos(), 10, (lev, center, pos, state) -> {
			BlockPos posAbove = pos.above();
			BlockState stateAbove = lev.getBlockState(posAbove);
			BlockState toPlace = getRandomBlock(random);
			if (Math.max(stateAbove.getBlock().getExplosionResistance(), stateAbove.getFluidState().getExplosionResistance()) < 100 && toPlace.canSurvive(level, posAbove)) {
				level.setBlockAndUpdate(posAbove, toPlace);
				stateAbove.getBlock().wasExploded(level, pos, dummy);
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(DustParticleOptions.REDSTONE, entity.x(), entity.y() + 1d, entity.z(), 0d, 0d, 0d);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.REDSTONE_TNT.get();
	}
	
	private static BlockState getRandomBlock(RandomSource random) {
		BlockState[] states = new BlockState[] {
			Blocks.REDSTONE_BLOCK.defaultBlockState(),
			Blocks.REDSTONE_LAMP.defaultBlockState(),
			Blocks.NOTE_BLOCK.defaultBlockState().setValue(BlockStateProperties.NOTE, random.nextInt(25)),
			Blocks.REDSTONE_TORCH.defaultBlockState(),
			Blocks.REDSTONE_WIRE.defaultBlockState(),
			Blocks.TARGET.defaultBlockState(),
			Blocks.SCULK_SENSOR.defaultBlockState(),
			Blocks.HOPPER.defaultBlockState().setValue(BlockStateProperties.FACING_HOPPER, getRandomDirectionNotUp(random)),
			Blocks.PISTON.defaultBlockState().setValue(BlockStateProperties.FACING, getRandomDirection(random)),
			Blocks.STICKY_PISTON.defaultBlockState().setValue(BlockStateProperties.FACING, getRandomDirection(random)),
			Blocks.OBSERVER.defaultBlockState().setValue(BlockStateProperties.FACING, getRandomDirection(random)),
			Blocks.DROPPER.defaultBlockState().setValue(BlockStateProperties.FACING, getRandomDirection(random)),
			Blocks.DISPENSER.defaultBlockState().setValue(BlockStateProperties.FACING, getRandomDirection(random)),
			Blocks.DAYLIGHT_DETECTOR.defaultBlockState().setValue(BlockStateProperties.INVERTED, random.nextBoolean()),
			Blocks.LEVER.defaultBlockState().setValue(BlockStateProperties.POWERED, random.nextBoolean()).setValue(BlockStateProperties.HORIZONTAL_FACING, getRandomDirectionHorizontal(random)).setValue(BlockStateProperties.ATTACH_FACE, AttachFace.FLOOR),
			Blocks.REPEATER.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, getRandomDirectionHorizontal(random)).setValue(BlockStateProperties.DELAY, 1 + random.nextInt(4)).setValue(BlockStateProperties.LOCKED, random.nextBoolean()),
			Blocks.COMPARATOR.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, getRandomDirectionHorizontal(random)).setValue(BlockStateProperties.MODE_COMPARATOR, random.nextBoolean() ? ComparatorMode.COMPARE : ComparatorMode.SUBTRACT)
		};
		
		return states[random.nextInt(states.length)];
	}
	
	public static Direction getRandomDirection(RandomSource random) {
		return Direction.values()[random.nextInt(Direction.values().length)];
	}
	
	private static Direction getRandomDirectionNotUp(RandomSource random) {
		return DIRECTIONS[random.nextInt(DIRECTIONS.length)];
	}
	
	public static Direction getRandomDirectionHorizontal(RandomSource random) {
		return Direction.Plane.HORIZONTAL.getRandomDirection(random);
	}
}
