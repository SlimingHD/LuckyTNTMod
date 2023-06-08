package luckytnt.tnteffects.projectile;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.EntityRegistry;
import luckytnt.registry.ItemRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.Item;

public class IceMeteorDynamiteEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		LExplosiveProjectile meteor = EntityRegistry.LITTLE_ICE_METEOR.get().create(entity.getLevel());
		meteor.setPos(entity.x(), entity.y() + LuckyTNTConfigValues.DROP_HEIGHT.get(), entity.z());
		meteor.setOwner(entity.owner());
		entity.getLevel().addFreshEntity(meteor);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.ITEM_SNOWBALL, entity.x(), entity.y(), entity.z(), 0, 0, 0);
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.ICE_METEOR_DYNAMITE.get();
	}
}
