package luckytnt.tnteffects.projectile;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.EntityRegistry;
import luckytnt.registry.ItemRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.Item;

public class MeteorDynamiteEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		LExplosiveProjectile meteor = EntityRegistry.LITTLE_METEOR.get().create(entity.level());
		meteor.setPos(entity.getPos().add(0, LuckyTNTConfigValues.DROP_HEIGHT.get(), 0));
		entity.level().addFreshEntity(meteor);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.level().addParticle(ParticleTypes.LAVA, entity.x(), entity.y(), entity.z(), 0, 0, 0);
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.METEOR_DYNAMITE.get();
	}
}
