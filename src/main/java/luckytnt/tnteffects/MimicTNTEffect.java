package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

public class MimicTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		((Entity) ent).setDeltaMovement(0, 0, 0);
		((Entity) ent).setPos(((Entity) ent).xOld, ((Entity) ent).yOld, ((Entity) ent).zOld);
		if (ent.getLevel().getNearestPlayer((Entity) ent, 5) != null && ent.getLevel().getNearestPlayer((Entity) ent, 5) != ent.owner()) {
			ent.getLevel().playSound((Entity)ent, toBlockPos(ent.getPos()), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4f, (1f + (ent.getLevel().getRandom().nextFloat() - ent.getLevel().getRandom().nextFloat()) * 0.2f) * 0.7f);
			if(!ent.getLevel().isClientSide()) {
				serverExplosion(ent);
				ent.destroy();
			}
		}
	}

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ImprovedExplosion explosion = new ImprovedExplosion(ent.getLevel(), (Entity)ent, ent.getPos(), 20);
		explosion.doEntityExplosion(2f, true);
		explosion.doImprovedBlockExplosion(1f, 1.5f, false, false, RandomSource.create());
	}

	@Override
	public void spawnParticles(IExplosiveEntity ent) {

	}

	@Override
	public BlockState getBlockState(IExplosiveEntity ent) {
		return ent.getLevel().getBlockState(toBlockPos(ent.getPos()).below()).isAir() ? BlockRegistry.MIMIC_TNT.get().defaultBlockState() : ent.getLevel().getBlockState(toBlockPos(ent.getPos()).below());
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 20000;
	}
}
