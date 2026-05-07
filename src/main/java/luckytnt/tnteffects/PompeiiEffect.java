package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.util.AdvancementHelper;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class PompeiiEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide() && entity.getTNTFuse() < 150 && entity.getTNTFuse() % 15 == 0) {
			Level level = entity.getLevel();
			RandomSource random = level.getRandom();
			for (int i = 0; i < 30; i++) {
				LExplosiveProjectile pompeii = EntityRegistry.POMPEII_PROJECTILE.get().create(level);
				pompeii.setPos(entity.getPos());
				pompeii.setOwner(entity.owner());
				pompeii.shoot(random.nextDouble() * 0.3d - 0.15d, 0.6d + random.nextDouble() * 0.4d, random.nextDouble() * 0.3d - 0.15D, 3f + random.nextFloat() * 2f, 0f);
				pompeii.setSecondsOnFire(1000);
				level.addFreshEntity(pompeii);
			}
			playExplosionSound(entity);
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		AdvancementHelper.grantAdvancementToOwnerOrNearby(entity, AdvancementKeys.ASHES_TO_ASHES);
	}

	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() + 0.5d, entity.y() + 1d, entity.z() + 0.5d, 0.05d, 0.2d, 0.05d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() - 0.5d, entity.y() + 1d, entity.z() - 0.5d, -0.05d, 0.2d, -0.05d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() + 0.5d, entity.y() + 1d, entity.z() - 0.5d, 0.05d, 0.2d, -0.05d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() - 0.5d, entity.y() + 1d, entity.z() + 0.5d, -0.05d, 0.2d, 0.05d);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.POMPEII.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 220;
	}
}
