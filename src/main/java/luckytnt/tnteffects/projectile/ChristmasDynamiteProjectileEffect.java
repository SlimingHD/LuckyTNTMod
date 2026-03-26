package luckytnt.tnteffects.projectile;

import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;

public class ChristmasDynamiteProjectileEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		RandomSource random = entity.getLevel().getRandom();
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 10);
		explosion.doEntityExplosion(0.75f, true);
		explosion.doImprovedBlockExplosion(1f, 1f, false, false, null);
		explosion.spawnExplosionParticles();
		((ServerLevel)entity.getLevel()).sendParticles(ParticleTypes.WAX_OFF, entity.x() + random.nextDouble() - 0.5d, entity.y() + random.nextDouble() - 0.5d, entity.z() + random.nextDouble() - 0.5d, 100, 0.5f, 0.5f, 0.5f, 0f);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		RandomSource random = entity.getLevel().getRandom();
		for(int i = 0; i < 7; i++) {
			entity.getLevel().addParticle(ParticleTypes.WAX_OFF, true, entity.x() + random.nextDouble() - 0.5d, entity.y() + random.nextDouble() - 0.5d, entity.z() + random.nextDouble() - 0.5d, 0, 0, 0);
		}
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.CHRISTMAS_DYNAMITE.get();
	}
}
