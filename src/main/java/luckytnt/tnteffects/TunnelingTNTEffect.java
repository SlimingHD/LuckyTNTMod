package luckytnt.tnteffects;

import luckytnt.block.TunnelingTNTBlock;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TunnelingTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Direction direction = Direction.byName(entity.getPersistentData().getString("direction")) != null ? Direction.byName(entity.getPersistentData().getString("direction")) : Direction.EAST;
		
		Level level = entity.getLevel();
		BlockPos center = BlockPos.containing(entity.getPos());
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(level);
		
		int xFactor = 0;
		int zFactor = 0;
		int minX = 0;
		int maxX = 0;
		int minZ = 0;
		int maxZ = 0;
		if (direction.getAxis() == Axis.X) {
			zFactor = 1;
			minZ = -4;
			maxZ = 4;
			if (direction.getStepX() > 0) {
				maxX = 90;
			} else {
				minX = 90;
			}
		} else {
			xFactor = 1;
			minX = -4;
			maxX = 4;
			if (direction.getStepZ() > 0) {
				maxZ = 90;
			} else {
				minZ = -90;
			}
		}
		
		for (int offX = minX; offX <= maxX; offX++) {
			for (int offY = -4; offY <= 4; offY++) {
				for (int offZ = minZ; offZ <= maxZ; offZ++) {
					double distanceSqr = offX * offX * xFactor + offY * offY + offZ * offZ * zFactor;
					if (distanceSqr <= 16) {
						BlockPos pos = center.offset(offX, offY, offZ);
						BlockState state = level.getBlockState(pos);
						if (state.getExplosionResistance(level, pos, dummy) < 100) {
							state.getBlock().wasExploded(level, pos, dummy);
							level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
						}
					}
				}
			}
		}
	}
	
	@Override
	public BlockState getBlockState(IExplosiveEntity entity) {
		return BlockRegistry.TUNNELING_TNT.get().defaultBlockState().setValue(TunnelingTNTBlock.FACING, Direction.byName(entity.getPersistentData().getString("direction")) != null ? Direction.byName(entity.getPersistentData().getString("direction")) : Direction.EAST);
	}
}
