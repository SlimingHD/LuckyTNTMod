package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.CanSurviveExplosionRule;
import luckytntlib.util.explosions.rules.FilterCollidableExplosionRule;
import luckytntlib.util.explosions.rules.FilterSurfaceExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.block.Block;

public class NuclearWasteTNTEffect extends PrimedTNTEffect {

	private final int radius;

	public NuclearWasteTNTEffect(int radius) {
		this.radius = radius;
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.legacySurfaceExplosion(entity.getLevel(), entity.getPos(), radius, 99f, new FilterSurfaceExplosionRule(false, 
			LogicExplosionRule.not(
				new FilterCollidableExplosionRule(new AlwaysExplosionRule()), 
				new CanSurviveExplosionRule(BlockRegistry.NUCLEAR_WASTE.get().defaultBlockState())
			)
		));
	}

	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.9f, 1f, 0f), 1f), entity.x(), entity.y() + 1d, entity.z(), 0d, 0d, 0d);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.NUCLEAR_WASTE_TNT.get();
	}
}
