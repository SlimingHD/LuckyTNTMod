package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;

public class MankindsMarkEffect extends HouseTNTEffect {

	public MankindsMarkEffect() {
		super(() -> BlockRegistry.MANKINDS_MARK, "ausgeburt_der_haeslichkeit", -11, -6);
	}

	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		Level level = ent.getLevel();

		level.addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 1.5d, ent.z(), 0.05d, -0.1d, 0.05d);
		level.addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 1.5d, ent.z(), -0.05d, -0.1d, -0.05d);
		level.addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 1.5d, ent.z(), 0.05d, -0.1d, -0.05d);
		level.addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 1.5d, ent.z(), -0.05d, -0.1d, 0.05d);
	}
}
