package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.core.particles.DustParticleOptions;

public class GlobalDisasterEffect extends SphereTNTEffect {

	public GlobalDisasterEffect() {
		super(() -> BlockRegistry.GLOBAL_DISASTER, 50);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		for(double angle = 0; angle < 360; angle += 6D) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), ent.x() + 2 * Math.cos(angle * Math.PI / 180), ent.y() + 0.5f, ent.z() + 2 * Math.sin(angle * Math.PI / 180), 0, 0, 0);
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), ent.x() + 2 * Math.cos(angle * Math.PI / 180), ent.y() + 0.5f + 2 * Math.sin(angle * Math.PI / 180), ent.z(), 0, 0, 0);
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), ent.x(), ent.y() + 0.5f + 2 * Math.cos(angle * Math.PI / 180), ent.z() + 2 * Math.sin(angle * Math.PI / 180), 0, 0, 0);
		}
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 240;
	}
}
