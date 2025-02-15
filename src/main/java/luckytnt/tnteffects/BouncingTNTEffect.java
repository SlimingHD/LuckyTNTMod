package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;

public class BouncingTNTEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 10);
		explosion.doEntityExplosion(1.25f, true);
		explosion.doImprovedBlockExplosion(1f, 1.25f, false, false, RandomSource.create());
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if(((Entity)entity).onGround()) {
			((Entity)entity).getPersistentData().putInt("bounces", ((Entity)entity).getPersistentData().getInt("bounces") + 1);
			((Entity)entity).setDeltaMovement(Math.random() - Math.random(), Math.random() * 1.5f, Math.random() - Math.random());
			entity.getLevel().playSound(null, entity.x(), entity.y(), entity.z(), SoundEvents.SLIME_JUMP, SoundSource.MASTER, 1, 1);
		}
		if(((Entity)entity).getPersistentData().getInt("bounces") >= 12) {
			entity.getLevel().playSound((Entity)entity, toBlockPos(entity.getPos()), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4f, (1f + (entity.getLevel().getRandom().nextFloat() - entity.getLevel().getRandom().nextFloat()) * 0.2f) * 0.7f);
			if(entity.getLevel() instanceof ServerLevel) {
				serverExplosion(entity);
				entity.destroy();
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.BOUNCING_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 100000;
	}
}
