package luckytnt.tnteffects.projectile;

import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class MiniIceMeteorEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ImprovedExplosion explosion = new ImprovedExplosion(ent.level(), ent.getPos(), 7);
		explosion.doEntityExplosion(1f, true);
		explosion.doBlockExplosion(1f, 1f, 1f, 1.25f, false, false);
		
		for (BlockPos pos : explosion.getToBlow()) {
			if (Math.random() > 0.75f && ent.level().getBlockState(pos).isAir() && ent.level().getBlockState(pos.below()).isSolidRender(ent.level(), pos)) {
				ent.level().setBlockAndUpdate(pos, Math.random() < 0.5f ? Blocks.BLUE_ICE.defaultBlockState() : Blocks.PACKED_ICE.defaultBlockState());
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.level().addParticle(ParticleTypes.ITEM_SNOWBALL, entity.x(), entity.y() + 1D, entity.z(), 0, 0, 0);
	}

	@Override
	public float getSize(IExplosiveEntity entity) {
		return 1f;
	}

	@Override
	public Block getBlock() {
		return Blocks.PACKED_ICE;
	}
}
