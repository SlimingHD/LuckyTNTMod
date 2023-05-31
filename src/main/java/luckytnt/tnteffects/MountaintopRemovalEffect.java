package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.core.particles.ParticleTypes;

public class MountaintopRemovalEffect extends FlatTNTEffect {

	public MountaintopRemovalEffect() {
		super(() -> BlockRegistry.MOUNTAINTOP_REMOVAL, 100, 30, 180);
	}

	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.level().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 0.5f, ent.z(), 0.2f, -0.05f, 0);
		ent.level().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 0.5f, ent.z(), -0.2f, -0.05f, 0);
		ent.level().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 0.5f, ent.z(), 0, -0.05f, 0.2f);
		ent.level().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 0.5f, ent.z(), 0, -0.05f, -0.2f);
		
		ent.level().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 0.5f, ent.z(), 0.1f, -0.05f, 0.1f);
		ent.level().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 0.5f, ent.z(), -0.1f, -0.05f, -0.1f);
		ent.level().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 0.5f, ent.z(), 0.1f, -0.05f, -0.1f);
		ent.level().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 0.5f, ent.z(), -0.1f, -0.05f, 0.1f);
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 180;
	}
}
