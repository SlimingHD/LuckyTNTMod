package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;

public class MountaintopRemovalEffect extends FlatTNTEffect {

	public MountaintopRemovalEffect() {
		super(() -> BlockRegistry.MOUNTAINTOP_REMOVAL, 100, 30, 180);
	}

	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		Level level = entity.getLevel();

		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 0.5d, entity.z(), 0.2d, -0.05d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 0.5d, entity.z(), -0.2d, -0.05d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 0.5d, entity.z(), 0d, -0.05d, 0.2d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 0.5d, entity.z(), 0d, -0.05d, -0.2d);

		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 0.5d, entity.z(), 0.1d, -0.05d, 0.1d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 0.5d, entity.z(), -0.1d, -0.05d, -0.1d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 0.5d, entity.z(), 0.1d, -0.05d, -0.1d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 0.5d, entity.z(), -0.1d, -0.05d, 0.1d);
	}
}