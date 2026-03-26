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
import net.minecraft.world.phys.Vec3;

public class ClusterDynamiteEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		if (entity instanceof LExplosiveProjectile dynamite) {
			RandomSource random = entity.getLevel().getRandom();
			if (!dynamite.inGround()) {
				for (int count = 0; count < 75; count++) {
					LExplosiveProjectile shrapnel = EntityRegistry.SHRAPNEL.get().create(entity.getLevel());
					shrapnel.setPos(entity.getPos());
					shrapnel.setOwner(entity.owner());
					shrapnel.setDeltaMovement(dynamite.getDeltaMovement().add(new Vec3(random.nextDouble() * 2d - 1d, random.nextDouble() * 2d - 1d, random.nextDouble() * 2d - 1d).scale(0.4f)));
					entity.getLevel().addFreshEntity(shrapnel);
				}
			} else {
				ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 8);
				explosion.doEntityExplosion(1f, true);
				explosion.doImprovedBlockExplosion(1f, 1.25f, false, false, null);
				explosion.spawnExplosionParticles();
				for (int count = 0; count < 50; count++) {
					LExplosiveProjectile shrapnel = EntityRegistry.SHRAPNEL.get().create(entity.getLevel());
					shrapnel.setPos(entity.getPos());
					shrapnel.setOwner(entity.owner());
					shrapnel.setDeltaMovement(dynamite.getDeltaMovement().add(random.nextDouble() * 2d - 1d, random.nextDouble() * 2d - 1d, random.nextDouble() * 2d - 1d).scale(-1f));
					entity.getLevel().addFreshEntity(shrapnel);
				}
			}
		}
	}
	
	@Override
	public boolean airFuse() {
		return true;
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.CLUSTER_DYNAMITE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 20;
	}
}
