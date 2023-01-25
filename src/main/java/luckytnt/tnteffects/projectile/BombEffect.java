package luckytnt.tnteffects.projectile;

import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;

public class BombEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.level(), entity.getPos(), 12f);
		explosion.doEntityExplosion(1.25f, true);
		explosion.doBlockExplosion(1f, 1f, 1f, 1f, false, false);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.level().addParticle(ParticleTypes.CLOUD, true, entity.x(), entity.y(), entity.z(), 0f, 0f, 0f);
	}
}
