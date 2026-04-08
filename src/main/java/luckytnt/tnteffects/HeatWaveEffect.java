package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.FireExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class HeatWaveEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ExplosionHelper.createSphericalCrater(ent.getLevel(), ent.getPos(), 150, 200f, new FireExplosionRule(1f));
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		for (int i = 0; i < 50; i++) {
			level.addParticle(ParticleTypes.FLAME, entity.x() + random.nextDouble() * 10d - random.nextDouble() * 10d, entity.y() + random.nextDouble() * 10d - random.nextDouble() * 10d, entity.z() + random.nextDouble() * 10d - random.nextDouble() * 10d, random.nextDouble() * 0.1d - random.nextDouble() * 0.1d, random.nextDouble() * 0.1d - random.nextDouble() * 0.1d, random.nextDouble() * 0.1d - random.nextDouble() * 0.1d);
		}	
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.HEAT_WAVE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 200;
	}
}
