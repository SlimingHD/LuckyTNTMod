package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlastResistanceExplosionRule;
import luckytntlib.util.explosions.rules.FilterFullBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterOffYExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class LavaOceanTNTEffect extends PrimedTNTEffect {

	private final int radius;
	private final int radiusY;

	public LavaOceanTNTEffect(int radius, int radiusY) {
		this.radius = radius;
		this.radiusY = radiusY;
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.legacyCylindricalExplosion(entity.getLevel(), entity.getPos(), radius, radiusY, 99.9f, new FilterOffYExplosionRule(-radiusY, 0,
			new StackedExplosionRule(
				new FilterBlastResistanceExplosionRule(3.9f, new BlockExplosionRule(Blocks.LAVA.defaultBlockState())),
				LogicExplosionRule.not(
					new FilterFullBlockExplosionRule(new AlwaysExplosionRule()),
					new BlockExplosionRule(Blocks.LAVA.defaultBlockState())
				)
			)
		));
	}

	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0.5f, 0.1f), 1f), entity.x(),
				entity.y() + 1d, entity.z(), 0d, 0d, 0d);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.LAVA_OCEAN_TNT.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
}
