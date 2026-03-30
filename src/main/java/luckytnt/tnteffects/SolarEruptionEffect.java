package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class SolarEruptionEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		Level level = ent.getLevel();
		if (!level.isClientSide() && ent.getTNTFuse() < 260 && ent.getTNTFuse() % 20 == 0) {
			RandomSource random = level.getRandom();
			for (int count = 0; count < 40; count++) {
				LExplosiveProjectile tnt = EntityRegistry.SOLAR_ERUPTION_PROJECTILE.get().create(level);
				tnt.setPos(ent.getPos());
				tnt.setOwner(ent.owner());
				tnt.setDeltaMovement(random.nextDouble() * 6d - 3d, 5d + random.nextDouble() * 2d, random.nextDouble() * 6d - 3d);
				tnt.setSecondsOnFire(1000);
				level.addFreshEntity(tnt);
				level.playSound(null, toBlockPos(ent.getPos()), SoundEvents.TNT_PRIMED, SoundSource.MASTER, 3f, 1f);
			}
		}
	}

	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		Level level = ent.getLevel();
		double x = ent.x();
		double y = ent.y();
		double z = ent.z();

		level.addParticle(ParticleTypes.FLAME, x + 0.5d, y + 0.5d, z + 0.5d, 0.1d, 0.4d, 0.1d);
		level.addParticle(ParticleTypes.FLAME, x - 0.5d, y + 0.5d, z - 0.5d, -0.1d, 0.4d, -0.1d);
		level.addParticle(ParticleTypes.FLAME, x + 0.5d, y + 0.5d, z - 0.5d, 0.1d, 0.4d, -0.1d);
		level.addParticle(ParticleTypes.FLAME, x - 0.5d, y + 0.5d, z + 0.5d, -0.1d, 0.4d, 0.1d);
		level.addParticle(ParticleTypes.FLAME, x + 0.5d, y + 0.5d, z + 0.5d, 0.05d, 0d, 0.05d);
		level.addParticle(ParticleTypes.FLAME, x - 0.5d, y + 0.5d, z - 0.5d, -0.05d, 0d, -0.05d);
		level.addParticle(ParticleTypes.FLAME, x + 0.5d, y + 0.5d, z - 0.5d, 0.05d, 0d, -0.05d);
		level.addParticle(ParticleTypes.FLAME, x - 0.5d, y + 0.5d, z + 0.5d, -0.05d, 0d, 0.05d);
		level.addParticle(ParticleTypes.LAVA, x + 0.5d, y + 1d, z + 0.5d, 0d, 0d, 0d);
		level.addParticle(ParticleTypes.LAVA, x - 0.5d, y + 1d, z - 0.5d, 0d, 0d, 0d);
		level.addParticle(ParticleTypes.LAVA, x + 0.5d, y + 1d, z - 0.5d, 0d, 0d, 0d);
		level.addParticle(ParticleTypes.LAVA, x - 0.5d, y + 1d, z + 0.5d, 0d, 0d, 0d);
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
