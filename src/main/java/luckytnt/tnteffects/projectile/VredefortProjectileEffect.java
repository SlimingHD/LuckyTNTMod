package luckytnt.tnteffects.projectile;

import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.util.RandomSource;

public class VredefortProjectileEffect extends MeteorEffect {

	public VredefortProjectileEffect() {
		super(120, 6f);
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		super.serverExplosion(entity);
		RandomSource random = entity.getLevel().getRandom();
		
		for(int count = 0; count < 300; count++) {
			LExplosiveProjectile projectile = EntityRegistry.SOLAR_ERUPTION_PROJECTILE.get().create(entity.getLevel());
			projectile.setPos(entity.getPos());
			projectile.setOwner(entity.owner());
			projectile.setDeltaMovement(random.nextDouble() * 8d - 4d, 3d + random.nextDouble() * 2d, random.nextDouble() * 8d - 4d);
			entity.getLevel().addFreshEntity(projectile);
		}
		for(int count = 0; count < 6; count++) {
			LExplosiveProjectile projectile = EntityRegistry.LITTLE_METEOR.get().create(entity.getLevel());
			projectile.setPos(entity.getPos());
			projectile.setOwner(entity.owner());
			projectile.setDeltaMovement(random.nextDouble() * 4d - 2d, 3d + random.nextDouble() * 2d, random.nextDouble() * 4d - 2d);
			entity.getLevel().addFreshEntity(projectile);
		}
	}
}
