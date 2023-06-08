package luckytnt.tnteffects.projectile;

import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;

public class PhobosMeteorEffect extends IceMeteorEffect {

	public PhobosMeteorEffect() {
		super(140, 6f);
	}

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		super.serverExplosion(ent);
		
		for(int count = 0; count < 300; count++) {
			LExplosiveProjectile projectile = EntityRegistry.MINI_ICE_METEOR.get().create(ent.getLevel());
			projectile.setPos(ent.getPos());
			projectile.setOwner(ent.owner());
			projectile.setDeltaMovement(Math.random() * 4 - Math.random() * 4, 3 + Math.random() * 2, Math.random() * 4 - Math.random() * 4);
			ent.getLevel().addFreshEntity(projectile);
		}
		for(int count = 0; count < 6; count++) {
			LExplosiveProjectile projectile = EntityRegistry.LITTLE_ICE_METEOR.get().create(ent.getLevel());
			projectile.setPos(ent.getPos());
			projectile.setOwner(ent.owner());
			projectile.setDeltaMovement(Math.random() * 2 - Math.random() * 2, 3 + Math.random() * 2, Math.random() * 2 - Math.random() * 2);
			ent.getLevel().addFreshEntity(projectile);
		}
	}
}
