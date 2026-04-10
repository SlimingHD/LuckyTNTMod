package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.FilterFullBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterOffYExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.explosions.rules.SimpleExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class GraveyardTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.legacySphericalExplosion(entity.getLevel(), entity.getPos().subtract(0d, 10d, 0d), 20, 100f, new FilterOffYExplosionRule(-20, 10, 
			LogicExplosionRule.not(
				new FilterFullBlockExplosionRule(new AlwaysExplosionRule()), 
				new SimpleExplosionRule(Blocks.GRASS_BLOCK.defaultBlockState())
			)
		));
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		for (int count = 0; count <= 20; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(0.5f, 0.2f, 0f), 0.75f), entity.x(), entity.y() + 1d + count * 0.05d, entity.z(), 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(new Vector3f(0.5f, 0.2f, 0f), 0.75f), entity.x() - 0.5d + count * 0.05d, entity.y() + 1.666d, entity.z(), 0d, 0d, 0d);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.GRAVEYARD_TNT.get();
	}
}
