package luckytnt.tnteffects;


import org.joml.Quaternionf;
import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.CanSurviveExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterCollidableExplosionRule;
import luckytntlib.util.explosions.rules.FilterDistanceExplosionRule;
import luckytntlib.util.explosions.rules.FilterFullBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterSurfaceExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class SnowstormTNTEffect extends PrimedTNTEffect {

	private static final Quaternionf QUAT = new Quaternionf().setAngleAxis(45f * Mth.DEG_TO_RAD, 0f, 1f, 0f);
	
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ExplosionHelper.createSphericalCrater(ent.getLevel(), ent.getPos(), 50, 200, new FilterAirExplosionRule(
			new StackedExplosionRule(
				FilterDistanceExplosionRule.lessEqual(10, 
					new StackedExplosionRule(
						new FilterFullBlockExplosionRule(new BlockExplosionRule(Blocks.BLUE_ICE.defaultBlockState())),
						LogicExplosionRule.and(
							LogicExplosionRule.not(
								new FilterFullBlockExplosionRule(new AlwaysExplosionRule()), 
								new AlwaysExplosionRule()
							),
							new FilterCollidableExplosionRule(new AlwaysExplosionRule()),
							new AlwaysExplosionRule()
						)
					)
				),
				FilterDistanceExplosionRule.greaterEqual(10, 
					FilterBlockExplosionRule.builder().filterForBlocks(Blocks.WATER, Blocks.BUBBLE_COLUMN, Blocks.SEAGRASS, Blocks.TALL_SEAGRASS, Blocks.KELP, Blocks.KELP_PLANT).build(new BlockExplosionRule(Blocks.ICE.defaultBlockState()))
				)
			)
		));
		
		ExplosionHelper.createSphericalCrater(ent.getLevel(), ent.getPos(), 50, 200, new FilterSurfaceExplosionRule(false,
			new CanSurviveExplosionRule(Blocks.SNOW.defaultBlockState())
		));
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		if (!level.isClientSide() && entity.getTNTFuse() % 4 == 0) {
			Vector3f vec = new Vector3f(0.5f, 1f, 0f);
			for (int i = 0; i < 8; i++) {
				Snowball ball = new Snowball(level, entity.x() + vec.x, entity.y() + vec.y, entity.z() + vec.z);
				ball.shoot(vec.x, vec.y, vec.z, 1f, 5f);
				level.addFreshEntity(ball);
				vec.rotate(QUAT);
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 1f), 1f), entity.x(), entity.y() + 1d, entity.z(), 0d, 0d, 0d);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.SNOWSTORM_TNT.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
}
