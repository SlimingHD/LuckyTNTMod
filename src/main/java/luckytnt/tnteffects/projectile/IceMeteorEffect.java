package luckytnt.tnteffects.projectile;

import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.RandomList;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.CraterExplosionRule;
import luckytntlib.util.explosions.rules.DistanceExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterRandomDistanceExplosionRule;
import luckytntlib.util.explosions.rules.RandomBlockExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class IceMeteorEffect extends PrimedTNTEffect {
	
	private final int strength;
	private final float size;
	
	public IceMeteorEffect(int strength, float size) {
		this.strength = strength;
		this.size = size;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), strength);
		explosion.doEntityExplosion(3, true);
		explosion.spawnExplosionParticles();
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), strength, 100, new FilterAirExplosionRule(
				new StackedExplosionRule(
						DistanceExplosionRule.lessEqual((strength * 7) / 8, new CraterExplosionRule()),
						FilterRandomDistanceExplosionRule.quadraticDecrease((strength * 7) / 8, (strength * 9) / 8, 
								new RandomBlockExplosionRule(RandomList.<BlockState>floatBuilder()
										.addEntry(Blocks.BLUE_ICE.defaultBlockState(), 0.125f)
										.addEntry(Blocks.PACKED_ICE.defaultBlockState(), 0.125f)
										.addEntry(Blocks.AIR.defaultBlockState(), 0.75f).build()
								)
						)
				)
		));
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.ITEM_SNOWBALL, entity.x(), entity.y() + size, entity.z(), 0, 0, 0);
	}

	@Override
	public float getSize(IExplosiveEntity entity) {
		return size;
	}

	@Override
	public Block getBlock() {
		return Blocks.PACKED_ICE;
	}
}
