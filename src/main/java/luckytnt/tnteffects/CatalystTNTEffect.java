package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class CatalystTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (entity.getTNTFuse() < 200) {
			Level level = entity.getLevel();
			if (!level.isClientSide()) {
				RandomSource random = level.getRandom();
				if (entity.getPersistentData().getInt("nextExplosion") <= 0) {
					double x = entity.x() + Math.random() * 40 - Math.random() * 40;
					double y = entity.y() + Math.random() * 15 - Math.random() * 15;
					double z = entity.z() + Math.random() * 40 - Math.random() * 40;
					ImprovedExplosion explosion = new ImprovedExplosion(level, (Entity)entity, new Vec3(x, y, z), 25 + random.nextInt(16));
					explosion.doEntityExplosion(2f, true);
					explosion.doImprovedBlockExplosion(1f, 0.75f, false, false, null);
					explosion.spawnExplosionParticles();
					level.playSound(null, BlockPos.containing(x, y, z), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4, (1f + (random.nextFloat() - random.nextFloat()) * 0.2f) * 0.7f);
					entity.getPersistentData().putInt("nextExplosion", 4 + random.nextInt(2));
				}
				entity.getPersistentData().putInt("nextExplosion", entity.getPersistentData().getInt("nextExplosion") - 1);
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		for (double i = 0d; i < 1d; i += 0.05d) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 1f), ent.x() + 0.5d, ent.y() + i, ent.z() + 0.5d, 0, 0, 0);
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 1f), ent.x() - 0.5d, ent.y() + i, ent.z() + 0.5d, 0, 0, 0);
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 1f), ent.x() + 0.5d, ent.y() + i, ent.z() - 0.5d, 0, 0, 0);
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 1f), ent.x() - 0.5d, ent.y() + i, ent.z() - 0.5d, 0, 0, 0);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.CATALYST_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 340;
	}
}
