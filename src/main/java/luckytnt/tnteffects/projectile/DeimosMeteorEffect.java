package luckytnt.tnteffects.projectile;

import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.util.RandomSource;

public class DeimosMeteorEffect extends IceMeteorEffect {

	public DeimosMeteorEffect() {
		super(80, 4f);
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		super.serverExplosion(entity);
		RandomSource random = entity.getLevel().getRandom();
		
		for (int count = 0; count < 300; count++) {
			LExplosiveProjectile mini = EntityRegistry.MINI_ICE_METEOR.get().create(entity.getLevel());
			mini.setPos(entity.getPos());
			mini.setOwner(entity.owner());
			mini.setDeltaMovement(random.nextDouble() * 8d - 4d, 3d + random.nextDouble() * 2d, random.nextDouble() * 8d - 4d);
			mini.setTNTFuse(100000);
			entity.getLevel().addFreshEntity(mini);
		}
	}
}
