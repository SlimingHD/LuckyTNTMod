package luckytnt.tnteffects;

import luckytnt.block.TunnelingTNTBlock;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TunnelingTNTEffect extends PrimedTNTEffect {

	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Direction direction = Direction.byName(entity.getPersistentData().getString("direction")) != null ? Direction.byName(entity.getPersistentData().getString("direction")) : Direction.EAST;
		
		Level level = entity.getLevel();
		BlockPos center = BlockPos.containing(entity.getPos());
		
		ImprovedExplosion particleExplosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), 10);
		particleExplosion.spawnExplosionParticles();
		
		float[][] vectorLengths = new float[9][9];
		for (int i = 0; i <= 8; i++) {
			for (int j = 0; j <= 8; j++) {
				vectorLengths[i][j] = 480f;
			}
		}
		Vec3i dir = direction.getNormal();
		int xFactor = 1 - Math.abs(dir.getX());
		int zFactor = 1 - Math.abs(dir.getZ());
		for (int step = 0; step < 480; step++) {
			int circleCount = 0;
			int finishedCount = 0;
			for (int i = 0; i <= 8; i++) {
				for (int j = 0; j <= 8; j++) {
					int distanceSqr = (i - 4) * (i - 4) + (j - 4) * (j - 4);
					if (distanceSqr > 16) {
						continue;
					}
					
					circleCount++;
			
					float vectorLength = vectorLengths[i][j];
					if (vectorLength <= 0) {
						finishedCount++;
						continue;
					}
					
					BlockPos pos = center.offset(dir.multiply(step)).offset((i - 4) * xFactor, j - 4, (i - 4) * zFactor);
					BlockState state = level.getBlockState(pos);
					float resistance = Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance());
					vectorLengths[i][j] -= resistance;
					if (resistance > vectorLength) {
						continue;
					}
					level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
					state.getBlock().wasExploded(level, pos, particleExplosion);
				}
			}
			if (finishedCount == circleCount) {
				break;
			}
		}
	}
	
	@Override
	public BlockState getBlockState(IExplosiveEntity entity) {
		return BlockRegistry.TUNNELING_TNT.get().defaultBlockState().setValue(TunnelingTNTBlock.FACING, Direction.byName(entity.getPersistentData().getString("direction")) != null ? Direction.byName(entity.getPersistentData().getString("direction")) : Direction.EAST);
	}
}
