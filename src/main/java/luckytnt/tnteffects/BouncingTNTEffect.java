package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class BouncingTNTEffect extends PrimedTNTEffect {
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Entity ent = (Entity)entity;
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		int bounces = ent.getPersistentData().getInt("bounces");
		if (ent.onGround()) {
			ent.getPersistentData().putInt("bounces", bounces + 1);
			ent.setDeltaMovement(random.nextDouble() * 2d - 1d, random.nextDouble() * 1.5d, random.nextDouble() * 2d - 1d);
			level.playSound(null, entity.x(), entity.y(), entity.z(), SoundEvents.SLIME_JUMP, SoundSource.MASTER, 1, 1);
			if (bounces >= 12) {
				if (!level.isClientSide()) {
					playExplosionSound(entity);
					serverExplosion(entity);
					entity.destroy();
				}
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 10);
		explosion.doEntityExplosion(1.25f, true);
		explosion.doImprovedBlockExplosion(1f, 1.25f, false, false, null);
		explosion.spawnExplosionParticles();
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
