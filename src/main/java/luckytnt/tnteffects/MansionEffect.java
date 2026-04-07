package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;

public class MansionEffect extends HouseTNTEffect {

	public MansionEffect() {
		super(() -> BlockRegistry.MANSION, "mansion", -14, -11);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 2d, entity.z(), 0.1d, -0.15d, 0.1d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 2d, entity.z(), -0.1d, -0.15d, -0.1d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 2d, entity.z(), 0.1d, -0.15d, -0.1d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 2d, entity.z(), -0.1d, -0.15d, 0.1d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 2d, entity.z(), 0d, -0.15d, 0.1d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 2d, entity.z(), 0d, -0.15d, -0.1d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 2d, entity.z(), 0.1d, -0.15d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 2d, entity.z(), -0.1d, -0.15d, 0d);
	}
}
