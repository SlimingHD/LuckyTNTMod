package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class PrismTNTEffect extends PrimedTNTEffect {
	
	private final int size;
	
	public PrismTNTEffect(int size) {
		this.size = size;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(level);
		BlockPos center = toBlockPos(entity.getPos()).offset(-1 * (size / 2) + 1, 0, -1 * (size / 2) + 1);
		for (int offY = size / 2; offY > (-1 * (size / 2) - 1); offY--) {
			int tri = size;
			for (int offX = 0; offX < size; offX++) {
				for (int offZ = 0; offZ < tri; offZ++) {
					BlockPos pos = center.offset(offX, offY, offZ);
					BlockState state = level.getBlockState(pos);
					if (Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) <= 100f) {
						level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
						state.getBlock().wasExploded(level, pos, dummy);
					}
				}
				tri--;
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.PRISM_TNT.get();
	}
}
