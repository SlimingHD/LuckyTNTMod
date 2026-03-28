package luckytnt.tnteffects.projectile;

import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.CraterExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterDistanceExplosionRule;
import luckytntlib.util.explosions.rules.FilterRandomDistanceExplosionRule;
import luckytntlib.util.explosions.rules.FireExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class MeteorEffect extends PrimedTNTEffect {
	
	private final int explosionSize;
	private final float modelSize;
	
	public MeteorEffect(int explosionSize, float modelSize) {
		this.explosionSize = explosionSize;
		this.modelSize = modelSize;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), explosionSize);
		explosion.doEntityExplosion(3, true);
		explosion.spawnExplosionParticles();
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), explosionSize, 100, new FilterAirExplosionRule(
				new StackedExplosionRule(
						FilterDistanceExplosionRule.lessEqual((explosionSize * 7) / 8, new CraterExplosionRule()),
						FilterRandomDistanceExplosionRule.quadraticDecrease((explosionSize * 7) / 8, (explosionSize * 9) / 8, new CraterExplosionRule())
				)
		));
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), (explosionSize * 9) / 8, 100, new FireExplosionRule(0.25f));
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.EXPLOSION, entity.x(), entity.y() + modelSize, entity.z(), 0, 0, 0);
	}

	@Override
	public float getSize(IExplosiveEntity entity) {
		return modelSize;
	}

	@Override
	public Block getBlock() {
		return Blocks.MAGMA_BLOCK;
	}
}
