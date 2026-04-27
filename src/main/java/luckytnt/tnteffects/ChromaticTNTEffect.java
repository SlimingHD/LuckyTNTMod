package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterCollidableExplosionRule;
import luckytntlib.util.explosions.rules.FilterDistanceExplosionRule;
import luckytntlib.util.explosions.rules.FilterFullBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterLiquidExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ChromaticTNTEffect extends PrimedTNTEffect {

	private static final Vector3f[] PARTICLE_COLORS = {
		new Vector3f(0.83f, 0.39f, 0.55f),
		new Vector3f(0.66f, 0.19f, 0.62f),
		new Vector3f(0.4f, 0.13f, 0.62f),
		new Vector3f(0.18f, 0.18f, 0.56f),
		new Vector3f(0.15f, 0.54f, 0.78f),
		new Vector3f(0.08f, 0.47f, 0.53f),
		new Vector3f(0.29f, 0.36f, 0.14f),
		new Vector3f(0.36f, 0.66f, 0.09f),
		new Vector3f(0.95f, 0.69f, 0.08f),
		new Vector3f(0.88f, 0.39f, 0.01f),
		new Vector3f(0.56f, 0.13f, 0.13f)
	};
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), 330, 1200f, new FilterAirExplosionRule(
			new StackedExplosionRule(
				LogicExplosionRule.and(
					LogicExplosionRule.not(new FilterLiquidExplosionRule(true, new AlwaysExplosionRule()), new AlwaysExplosionRule()),
					LogicExplosionRule.not(new FilterCollidableExplosionRule(new AlwaysExplosionRule()), new AlwaysExplosionRule()),
					new AlwaysExplosionRule()
				),
				FilterDistanceExplosionRule.greaterEqual(300, new BlockExplosionRule(Blocks.RED_CONCRETE.defaultBlockState())),
				FilterDistanceExplosionRule.greaterEqual(270, new BlockExplosionRule(Blocks.ORANGE_CONCRETE.defaultBlockState())),
				FilterDistanceExplosionRule.greaterEqual(240, new BlockExplosionRule(Blocks.YELLOW_CONCRETE.defaultBlockState())),
				FilterDistanceExplosionRule.greaterEqual(210, new BlockExplosionRule(Blocks.LIME_CONCRETE.defaultBlockState())),
				FilterDistanceExplosionRule.greaterEqual(180, new BlockExplosionRule(Blocks.GREEN_CONCRETE.defaultBlockState())),
				FilterDistanceExplosionRule.greaterEqual(150, new BlockExplosionRule(Blocks.CYAN_CONCRETE.defaultBlockState())),
				FilterDistanceExplosionRule.greaterEqual(120, new BlockExplosionRule(Blocks.LIGHT_BLUE_CONCRETE.defaultBlockState())),
				FilterDistanceExplosionRule.greaterEqual(90, new BlockExplosionRule(Blocks.BLUE_CONCRETE.defaultBlockState())),
				FilterDistanceExplosionRule.greaterEqual(60, new BlockExplosionRule(Blocks.PURPLE_CONCRETE.defaultBlockState())),
				FilterDistanceExplosionRule.greaterEqual(30, new BlockExplosionRule(Blocks.MAGENTA_CONCRETE.defaultBlockState())),
				new BlockExplosionRule(Blocks.PINK_CONCRETE.defaultBlockState())
			)
		));
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		if (entity.getTNTFuse() < 0) {
			return;
		}
		int step = 10 - entity.getTNTFuse() % 11;
		Vector3f color = PARTICLE_COLORS[step];
		double radius = Math.sqrt(0.5d) + step / 4d;
		for (double angle = 0d; angle < 360d; angle += 6d / radius) {
			entity.getLevel().addParticle(new DustParticleOptions(color, 1f), entity.x() + Math.sin(angle * Mth.DEG_TO_RAD) * radius, entity.y() + 0.5d, entity.z() + Math.cos(angle * Mth.DEG_TO_RAD) * radius, 0d, 0d, 0d);
		}
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 330;
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.CHROMATIC_TNT.get();
	}
}
