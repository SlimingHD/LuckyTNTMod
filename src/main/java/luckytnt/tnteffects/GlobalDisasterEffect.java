package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.Mth;

public class GlobalDisasterEffect extends SphereTNTEffect {

	public GlobalDisasterEffect() {
		super(() -> BlockRegistry.GLOBAL_DISASTER, 50, 1000f);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		for (double angle = 0d; angle < 360d; angle += 6d) {
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), entity.x() + 2d * Math.cos(angle * Mth.DEG_TO_RAD), entity.y() + 0.5d, entity.z() + 2d * Math.sin(angle * Mth.DEG_TO_RAD), 0d, 0d, 0d);
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), entity.x() + 2d * Math.cos(angle * Mth.DEG_TO_RAD), entity.y() + 0.5d + 2d * Math.sin(angle * Mth.DEG_TO_RAD), entity.z(), 0d, 0d, 0d);
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), entity.x(), entity.y() + 0.5d + 2d * Math.cos(angle * Mth.DEG_TO_RAD), entity.z() + 2d * Math.sin(angle * Mth.DEG_TO_RAD), 0d, 0d, 0d);
		}
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 240;
	}
}
