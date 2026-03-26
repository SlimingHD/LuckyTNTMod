package luckytnt.tnteffects.projectile;

import luckytnt.registry.EntityRegistry;
import luckytnt.registry.ItemRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;

public class DynamiteFireworkEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		RandomSource random = entity.getLevel().getRandom();
		for (int count = 0; count <= 300; count++) {
			LExplosiveProjectile dynamite = EntityRegistry.DYNAMITE.get().create(entity.getLevel());
			dynamite.setPos(entity.getPos());
			dynamite.setOwner(dynamite.owner());
			dynamite.setDeltaMovement(random.nextDouble() * 2d - 1d, random.nextDouble() * 2d - 1d, random.nextDouble() * 2d - 1d);
			entity.getLevel().addFreshEntity(dynamite);
		}
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		((Entity)entity).setDeltaMovement(((Entity)entity).getDeltaMovement().add(0f, 0.08f, 0f));
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y(), entity.z(), 0, 0, 0);
	}
	
	@Override
	public boolean airFuse() {
		return true;
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.DYNAMITE_FIREWORK.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 40;
	}
}
