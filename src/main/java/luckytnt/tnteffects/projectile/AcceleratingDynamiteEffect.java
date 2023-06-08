package luckytnt.tnteffects.projectile;

import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;

public class AcceleratingDynamiteEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), (int)Math.round(2f * Mth.clamp(entity.getPersistentData().getDouble("speed"), 1f, 20f)));
		explosion.doEntityExplosion(1.5f, true);
		explosion.doBlockExplosion(1f, 1f, 1f, 1.25f, false, false);
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		((Entity)entity).setDeltaMovement(((Entity)entity).getDeltaMovement().add(((Entity)entity).getDeltaMovement().scale(0.05f)));
		if(((Entity)entity).getDeltaMovement().length() > entity.getPersistentData().getDouble("speed")) {
			entity.getPersistentData().putDouble("speed", ((Entity)entity).getDeltaMovement().length());
		}
	}
	
	@Override
	public boolean airFuse() {
		return true;
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.ACCELERATING_DYNAMITE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 10000;
	}
}
