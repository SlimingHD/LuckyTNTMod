package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;

public class VaporizeTNTEffect extends PrimedTNTEffect {

	private final int strength;
	
	public VaporizeTNTEffect(int strength) {
		this.strength = strength;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		WastelandTNTEffect.doVaporizeExplosion(entity.getLevel(), entity.getPos(), strength, false);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.SMOKE, entity.x() + 1.3f, entity.y() + 0.5f, entity.z(), -0.1f, 0, 0);
		entity.getLevel().addParticle(ParticleTypes.SMOKE, entity.x() - 1.3f, entity.y() + 0.5f, entity.z(), 0.1f, 0, 0);
		entity.getLevel().addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 0.5f, entity.z() + 1.3f, 0, 0, -0.1f);
		entity.getLevel().addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 0.5f, entity.z() - 1.3f, 0, 0, 0.1f);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.VAPORIZE_TNT.get();
	}
}
