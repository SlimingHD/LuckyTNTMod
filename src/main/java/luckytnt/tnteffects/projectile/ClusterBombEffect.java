package luckytnt.tnteffects.projectile;

import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.entity.Entity;

public class ClusterBombEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 10);
		explosion.doEntityExplosion(1f, true);
		explosion.doBlockExplosion(1f, 1f, 1f, 1.25f, false, false);
		for(int count = 0; count < 80; count++) {
			LExplosiveProjectile shrapnel = EntityRegistry.SHRAPNEL.get().create(entity.getLevel());
			shrapnel.setPos(entity.getPos());
			shrapnel.setOwner(entity.owner());
			shrapnel.setDeltaMovement(Math.random() - 0.5f, Math.random() * 1.5f, Math.random() - 0.5f);
			entity.getLevel().addFreshEntity(shrapnel);
		}
	}
}
