package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.core.particles.ParticleTypes;

public class MankindsMarkEffect extends HouseTNTEffect {

	public MankindsMarkEffect() {
		super(() -> BlockRegistry.MANKINDS_MARK, "ausgeburt_der_haeslichkeit", -11, -6);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 1.5f, ent.z(), 0.05f, -0.1f, 0.05f);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 1.5f, ent.z(), -0.05f, -0.1f, -0.05f);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 1.5f, ent.z(), 0.05f, -0.1f, -0.05f);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 1.5f, ent.z(), -0.05f, -0.1f, 0.05f);
	}
}
