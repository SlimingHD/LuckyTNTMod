package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

public class MimicTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		((Entity) ent).setDeltaMovement(0, 0, 0);
		((Entity) ent).setPos(((Entity) ent).xOld, ((Entity) ent).yOld, ((Entity) ent).zOld);
		if (ent.level().getNearestPlayer((Entity) ent, 5) != null && ent.level().getNearestPlayer((Entity) ent, 5) != ent.owner() && !ent.level().isClientSide()) {
			serverExplosion(ent);
			ent.destroy();
		}
	}

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ImprovedExplosion explosion = new ImprovedExplosion(ent.level(), ent.getPos(), 20f);
		explosion.doEntityExplosion(2f, true);
		explosion.doBlockExplosion(1f, 1f, 1f, 1.5f, false, false);
	}

	@Override
	public void spawnParticles(IExplosiveEntity ent) {

	}

	@Override
	public BlockState getBlockState(IExplosiveEntity ent) {
		return ent.level().getBlockState(new BlockPos(ent.getPos()).below()).isAir() ? BlockRegistry.MIMIC_TNT.get().defaultBlockState() : ent.level().getBlockState(new BlockPos(ent.getPos()).below());
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 20000;
	}
}
