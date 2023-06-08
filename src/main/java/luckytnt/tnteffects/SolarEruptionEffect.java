package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Block;

public class SolarEruptionEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		if(ent.getTNTFuse() < 260) {
			if(ent.getTNTFuse() % 20 == 0) {
				for(int count = 0; count < 40; count++) {
					LExplosiveProjectile tnt = EntityRegistry.SOLAR_ERUPTION_PROJECTILE.get().create(ent.getLevel());
					tnt.setPos(ent.getPos());
					tnt.setOwner(ent.owner());
					tnt.setDeltaMovement(Math.random() * 3f - Math.random() * 3f, 5 + Math.random() * 2, Math.random() * 3f - Math.random() * 3f);		
					tnt.setSecondsOnFire(1000);
					ent.getLevel().addFreshEntity(tnt);
					ent.getLevel().playSound(null, toBlockPos(ent.getPos()), SoundEvents.TNT_PRIMED, SoundSource.MASTER, 3, 1);
				}
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() + 0.5f, ent.y() + 0.5f, ent.z() + 0.5f, 0.1f, 0.4f, 0.1f);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() - 0.5f, ent.y() + 0.5f, ent.z() - 0.5f, -0.1f, 0.4f, -0.1f);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() + 0.5f, ent.y() + 0.5f, ent.z() - 0.5f, 0.1f, 0.4f, -0.1f);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() - 0.5f, ent.y() + 0.5f, ent.z() + 0.5f, -0.1f, 0.4f, 0.1f);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() + 0.5f, ent.y() + 0.5f, ent.z() + 0.5f, 0.05f, 0f, 0.05f);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() - 0.5f, ent.y() + 0.5f, ent.z() - 0.5f, -0.05f, 0f, -0.05f);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() + 0.5f, ent.y() + 0.5f, ent.z() - 0.5f, 0.05f, 0f, -0.05f);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() - 0.5f, ent.y() + 0.5f, ent.z() + 0.5f, -0.05f, 0f, 0.05f);
		ent.getLevel().addParticle(ParticleTypes.LAVA, ent.x() + 0.5f, ent.y() + 1f, ent.z() + 0.5f, 0, 0, 0);
		ent.getLevel().addParticle(ParticleTypes.LAVA, ent.x() - 0.5f, ent.y() + 1f, ent.z() - 0.5f, 0, 0, 0);
		ent.getLevel().addParticle(ParticleTypes.LAVA, ent.x() + 0.5f, ent.y() + 1f, ent.z() - 0.5f, 0, 0, 0);
		ent.getLevel().addParticle(ParticleTypes.LAVA, ent.x() - 0.5f, ent.y() + 1f, ent.z() + 0.5f, 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.SOLAR_ERUPTION.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 360;
	}
}
