package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;

public class DustBowlEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		WastelandTNTEffect.doVaporizeExplosion(entity.getLevel(), entity.getPos(), 25, true);
		ImprovedExplosion particleExplosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), 25);
		particleExplosion.spawnExplosionParticles();
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		RandomSource random = entity.getLevel().getRandom();
		entity.getLevel().addParticle(ParticleTypes.CLOUD, entity.x() + random.nextDouble() * 12d - 6d, entity.y() + 0.5d, entity.z() + random.nextDouble() * 12d - 6d, 0, 0, 0);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.DUST_BOWL.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 120;
	}
}
