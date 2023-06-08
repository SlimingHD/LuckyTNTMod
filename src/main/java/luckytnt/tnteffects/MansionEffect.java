package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.core.particles.ParticleTypes;

public class MansionEffect extends HouseTNTEffect {

	public MansionEffect() {
		super(() -> BlockRegistry.MANSION, "mansion", -14, -11);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 2f, ent.z(), 0.1f, -0.15f, 0.1f);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 2f, ent.z(), -0.1f, -0.15f, -0.1f);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 2f, ent.z(), 0.1f, -0.15f, -0.1f);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 2f, ent.z(), -0.1f, -0.15f, 0.1f);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 2f, ent.z(), 0f, -0.15f, 0.1f);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 2f, ent.z(), 0f, -0.15f, -0.1f);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 2f, ent.z(), 0.1f, -0.15f, 0f);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 2f, ent.z(), -0.1f, -0.15f, 0f);
	}
}
