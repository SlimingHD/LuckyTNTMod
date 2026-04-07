package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class NuclearWasteTNTEffect extends PrimedTNTEffect {

	private final int radius;

	public NuclearWasteTNTEffect(int radius) {
		this.radius = radius;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(entity.getLevel());
		ExplosionHelper.customSurfaceExplosion(entity.getLevel(), entity.getPos(), radius, (level, center, pos, state) -> {
			BlockPos posAbove = pos.above();
			BlockState stateAbove = level.getBlockState(posAbove);
			if (Math.max(stateAbove.getBlock().getExplosionResistance(), stateAbove.getFluidState().getExplosionResistance()) < 100f && BlockRegistry.NUCLEAR_WASTE.get().canSurvive(stateAbove, level, posAbove)) {
				level.setBlockAndUpdate(pos, BlockRegistry.NUCLEAR_WASTE.get().defaultBlockState());
				stateAbove.getBlock().wasExploded(level, posAbove, dummy);
			}
		});
	}

	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.9f, 1f, 0f), 1f), entity.x(), entity.y() + 1d, entity.z(), 0d, 0d, 0d);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.NUCLEAR_WASTE_TNT.get();
	}
}
