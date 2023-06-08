package luckytnt.tnteffects.projectile;

import luckytnt.registry.EntityRegistry;
import luckytnt.registry.ItemRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;

public class ClusterDynamiteEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		if(entity instanceof LExplosiveProjectile dynamite) {
			if(!dynamite.inGround()) {
				for(int count = 0; count < 75; count++) {
					LExplosiveProjectile shrapnel = EntityRegistry.SHRAPNEL.get().create(entity.getLevel());
					shrapnel.setPos(entity.getPos());
					shrapnel.setOwner(entity.owner());
					shrapnel.setDeltaMovement(dynamite.getDeltaMovement().add(new Vec3(Math.random() - Math.random(), Math.random() - Math.random(), Math.random() - Math.random()).scale(0.4f)));
					entity.getLevel().addFreshEntity(shrapnel);
				}
			}
			else {
				ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 8);
				explosion.doEntityExplosion(1f, true);
				explosion.doBlockExplosion(1f, 1f, 1f, 1.25f, false, false);
				for(int count = 0; count < 50; count++) {
					LExplosiveProjectile shrapnel = EntityRegistry.SHRAPNEL.get().create(entity.getLevel());
					shrapnel.setPos(entity.getPos());
					shrapnel.setOwner(entity.owner());
					shrapnel.setDeltaMovement(dynamite.getDeltaMovement().add(Math.random() * 2f - 1f, Math.random() * 2f - 1f, Math.random() * 2f - 1f).scale(-1f));
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
