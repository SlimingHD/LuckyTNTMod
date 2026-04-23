package luckytnt.tnteffects.projectile;

import luckytnt.registry.EntityRegistry;
import luckytnt.registry.ItemRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MultiplyingDynamiteEffect extends PrimedTNTEffect {

	@Override
	public void baseTick(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		if (entity instanceof LExplosiveProjectile ent) {
			if (ent.inGround() && ent.getPersistentData().getInt("level") >= 3 && !level.isClientSide()) {
				serverExplosion(ent);
				ent.destroy();
			}
			if (ent.getTNTFuse() == 0 && !level.isClientSide()) {
				serverExplosion(ent);
				ent.destroy();
			}
			if (ent.getPersistentData().getInt("level") < 3) {
				explosionTick(ent);
				ent.setTNTFuse(ent.getTNTFuse() - 1);
			}
			if (level.isClientSide()) {
				spawnParticles(entity);
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		Entity ent = (Entity)entity;
		RandomSource random = level.getRandom();
		if (entity.getPersistentData().getInt("level") < 3) {	
			for (int count = 0; count < 4; count++) {
				LExplosiveProjectile dynamite = EntityRegistry.MULTIPLYING_DYNAMITE.get().create(entity.getLevel());
				dynamite.setPos(entity.getPos());
				dynamite.setOwner(entity.owner());
				dynamite.setDeltaMovement(ent.getDeltaMovement().add(random.nextDouble() * 0.5d - 0.25d, random.nextDouble() * 0.5d - 0.25d, random.nextDouble() * 0.5d - 0.25d));
				dynamite.getPersistentData().putInt("level", entity.getPersistentData().getInt("level") + 1);
				entity.getLevel().addFreshEntity(dynamite);
			}
		} else {
			ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), ent, entity.getPos(), 8);
			explosion.doEntityExplosion(0.75f, true);
			explosion.doImprovedBlockExplosion(1f, 1.25f, false, false, null);
			explosion.spawnExplosionParticles();
			playExplosionSound(entity);
		}
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Entity ent = (Entity)entity;
		if (entity.getPersistentData().getInt("level") < 3) {
			ent.addDeltaMovement(new Vec3(0d, 0.08d, 0d));
		}
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.MULTIPLYING_DYNAMITE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 20;
	}
}
