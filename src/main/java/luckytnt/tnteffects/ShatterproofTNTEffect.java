package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.rules.FilterCollidableExplosionRule;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.SimpleExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ShatterproofTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.legacySphericalExplosion(entity.getLevel(), entity.getPos(), 10, 1200, new FilterCollidableExplosionRule(new SimpleExplosionRule(Blocks.OBSIDIAN.defaultBlockState())));
	}

	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.1f, 0.1f, 0.1f), 1f), entity.x(), entity.y() + 1d, entity.z(), 0d, 0d, 0d);
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.5f, 0.3f, 0.8f), 1f), entity.x(), entity.y() + 1d, entity.z(), 0d, 0d, 0d);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.SHATTERPROOF_TNT.get();
	}
}
