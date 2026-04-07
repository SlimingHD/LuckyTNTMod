package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;

public class PoseidonsWaveEffect extends OceanTNTEffect {

	public PoseidonsWaveEffect() {
		super(() -> BlockRegistry.POSEIDONS_WAVE, 60, 20, 20);
	}

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (entity.getTNTFuse() == 179 && entity.getLevel() instanceof ServerLevel server) {
			server.setWeatherParameters(0, 10000, true, true);
		}
	}

	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.SPLASH, entity.x(), entity.y() + 1d, entity.z(), 0d, 0d, 0d);
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 180;
	}
}
