package luckytnt.tnteffects.projectile;

import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;

public class DeimosMeteorEffect extends IceMeteorEffect {

	public DeimosMeteorEffect() {
		super(80, 4f);
	}

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		super.serverExplosion(ent);
		
		for(int count = 0; count < 300; count++) {
			LExplosiveProjectile mini = EntityRegistry.MINI_ICE_METEOR.get().create(ent.getLevel());
			mini.setPos(ent.getPos());
			mini.setOwner(ent.owner());
			mini.setDeltaMovement(Math.random() * 8D - 4D, 3 + Math.random() * 2, Math.random() * 8D - 4D);
			mini.setTNTFuse(100000);
			ent.getLevel().addFreshEntity(mini);
		}
	}
}
