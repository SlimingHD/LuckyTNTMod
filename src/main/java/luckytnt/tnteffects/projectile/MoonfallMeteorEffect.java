package luckytnt.tnteffects.projectile;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.util.AdvancementHelper;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.CraterExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterDistanceExplosionRule;
import luckytntlib.util.explosions.rules.FilterRandomDistanceExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;

public class MoonfallMeteorEffect extends PrimedTNTEffect {

	private final int explosionSize = 300;
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		RandomSource random = entity.getLevel().getRandom();
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), explosionSize);
		explosion.doEntityExplosion(3, true);
		explosion.spawnExplosionParticles();
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), explosionSize, 100, new FilterAirExplosionRule(
				new StackedExplosionRule(
						FilterDistanceExplosionRule.lessEqual((explosionSize * 7) / 8, new CraterExplosionRule()),
						FilterRandomDistanceExplosionRule.quadraticDecrease((explosionSize * 7) / 8, (explosionSize * 9) / 8, new CraterExplosionRule())
				)
		));

		for(int count = 0; count < 8; count++) {
			LExplosiveProjectile meteorProjectile = EntityRegistry.CHICXULUB_METEOR.get().create(entity.getLevel());
			meteorProjectile.setPos(entity.getPos());
			meteorProjectile.setOwner(entity.owner());
			meteorProjectile.setDeltaMovement(random.nextDouble() * 9d - 4.5d, 3d + random.nextDouble() * 2d, random.nextDouble() * 9d - 4.5d);
			meteorProjectile.setTNTFuse(100000);
			entity.getLevel().addFreshEntity(meteorProjectile);
		}
		
		AdvancementHelper.grantAdvancementToOwnerOrNearby(entity, AdvancementKeys.WHAT_WOULD_ELON_DO);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.MOON.get();
	}

	@Override
	public float getSize(IExplosiveEntity entity) {
		return 30f;
	}
}
