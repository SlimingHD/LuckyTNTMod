package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class UnbreakableTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.legacySphericalExplosion(entity.getLevel(), entity.getPos(), 15, 2000, new FilterAirExplosionRule(new BlockExplosionRule(Blocks.BEDROCK.defaultBlockState())));
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 1), entity.x(), entity.y() + 1f, entity.z(), 0, 0, 0);
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.8f, 0.8f, 0.8f), 1), entity.x(), entity.y() + 1f, entity.z(), 0, 0, 0);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.UNBREAKABLE_TNT.get();
	}
}
