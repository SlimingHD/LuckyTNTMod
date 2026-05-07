package luckytnt.tnteffects.projectile;

import luckytnt.registry.EntityRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.util.AdvancementHelper;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.util.RandomSource;

public class ChicxulubMeteorEffect extends MeteorEffect {
	
	public ChicxulubMeteorEffect() {
		super(60, 4f);
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		super.serverExplosion(entity);
		RandomSource random = entity.getLevel().getRandom();
		
		for(int count = 0; count < 300; count++) {
			LExplosiveProjectile pompeiiProjectile = EntityRegistry.POMPEII_PROJECTILE.get().create(entity.getLevel());
			pompeiiProjectile.setPos(entity.getPos());
			pompeiiProjectile.setOwner(entity.owner());
			pompeiiProjectile.setDeltaMovement(random.nextDouble() * 8d - 4d, 3d + random.nextDouble() * 2d, random.nextDouble() * 8d - 4d);
			pompeiiProjectile.setTNTFuse(100000);
			entity.getLevel().addFreshEntity(pompeiiProjectile);
		}
		
		AdvancementHelper.grantAdvancementToOwnerOrNearby(entity, AdvancementKeys.BAD_DAY);
	}
}
