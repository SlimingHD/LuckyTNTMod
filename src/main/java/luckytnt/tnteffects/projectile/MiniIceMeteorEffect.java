package luckytnt.tnteffects.projectile;

import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.RandomList;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterRandomDistanceExplosionRule;
import luckytntlib.util.explosions.rules.RandomBlockExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class MiniIceMeteorEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 7);
		explosion.doEntityExplosion(1f, true);
		explosion.doImprovedBlockExplosion(1f, 1.25f, false, false, null);
		explosion.spawnExplosionParticles();
		
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), 7, 100, new FilterAirExplosionRule(
				FilterRandomDistanceExplosionRule.quadraticDecrease(0, 7, 
						new RandomBlockExplosionRule(RandomList.ofEqualProbability(Blocks.BLUE_ICE.defaultBlockState(), Blocks.PACKED_ICE.defaultBlockState()))
				)
		));
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.ITEM_SNOWBALL, entity.x(), entity.y() + 1D, entity.z(), 0, 0, 0);
	}

	@Override
	public float getSize(IExplosiveEntity entity) {
		return 1f;
	}

	@Override
	public Block getBlock() {
		return Blocks.PACKED_ICE;
	}
}
