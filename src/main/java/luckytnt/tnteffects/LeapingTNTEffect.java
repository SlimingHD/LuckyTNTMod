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

public class LeapingTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Entity ent = (Entity)entity;
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		int bounces = ent.getPersistentData().getInt("bounces");
		if (ent.onGround()) {
			ent.getPersistentData().putInt("bounces", bounces + 1);
			ent.setDeltaMovement(random.nextDouble() * 2d - 1d, random.nextDouble() * 1.5d, random.nextDouble() * 2d - 1d);
			level.playSound(null, entity.x(), entity.y(), entity.z(), SoundEvents.SLIME_JUMP, SoundSource.MASTER, 1f, 1f);
			if (bounces >= 1) {
				if (!level.isClientSide()) {
					playExplosionSound(entity);
					serverExplosion(entity);
					if (bounces >= 24) {
						entity.destroy();
					}
				}
			}
		}
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		if (entity.getPersistentData().getInt("bounces") < 24) {
			ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity) entity, entity.getPos(), 10);
			explosion.doEntityExplosion(1.5f, true);
			explosion.doImprovedBlockExplosion(1f, 1.25f, false, false, null);
			explosion.spawnExplosionParticles();
		} else {
			ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity) entity, entity.getPos(), 20);
			explosion.doEntityExplosion(2f, true);
			explosion.doImprovedBlockExplosion(1f, 1.5f, false, false, null);
			explosion.spawnExplosionParticles();
		}
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.LEAPING_TNT.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 100000;
	}
}
