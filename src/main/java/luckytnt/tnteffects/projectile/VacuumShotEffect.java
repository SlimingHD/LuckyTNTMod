package luckytnt.tnteffects.projectile;

import luckytnt.registry.EntityRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.util.AdvancementHelper;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;

public class VacuumShotEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 2);
		explosion.doEntityExplosion((ent, distance) -> {
			if (ent.getType().equals(EntityRegistry.TOXIC_CLOUD.get()) && ent.level().getRandom().nextFloat() < 0.2f) {
				AdvancementHelper.grantAdvancementToOwnerOrNearby(entity, AdvancementKeys.INTENTIONAL_MALFUNCTION);
				ent.discard();
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.CLOUD, true, entity.x(), entity.y(), entity.z(), 0, 0, 0);
	}
	
	@Override
	public boolean playsSound() {
		return false;
	}
}
