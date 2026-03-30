package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SnowTNTEffect extends PrimedTNTEffect {

	private final int strength;

	public SnowTNTEffect(int strength) {
		this.strength = strength;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(entity.getLevel());
		ExplosionHelper.customSurfaceExplosion(entity.getLevel(), entity.getPos(), strength, (level, center, b, s) -> {
			BlockPos pos = b.above();
			BlockState state = level.getBlockState(pos);
			if (Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) < 100f && Blocks.SNOW.canSurvive(state, level, pos)) {
				level.setBlockAndUpdate(pos, Blocks.SNOW.defaultBlockState());
				state.getBlock().wasExploded(level, pos, dummy);
			}
		});
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.SNOW_TNT.get();
	}
}
