package luckytnt.tnteffects.projectile;

import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;

public class AcceleratingDynamiteEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), (int)Math.round(3f * Mth.clamp(entity.getPersistentData().getDouble("speed"), 1f, 40f)));
		explosion.doEntityExplosion(1.5f, true);
		explosion.doImprovedBlockExplosion(1f, 1.25f, false, false, null);
		explosion.spawnExplosionParticles();
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Entity ent = (Entity)entity;
		Vec3 velocity = ent.getDeltaMovement().scale(1.05f);
		velocity = new Vec3(Mth.clamp(velocity.x(), -20d, 20d), Mth.clamp(velocity.y(), -20d, 20d), Mth.clamp(velocity.z(), -20d, 20d));
		ent.setDeltaMovement(velocity);
		if (ent.getDeltaMovement().length() > entity.getPersistentData().getDouble("speed")) {
			entity.getPersistentData().putDouble("speed", ent.getDeltaMovement().length());
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
		return 5000;
	}
}
