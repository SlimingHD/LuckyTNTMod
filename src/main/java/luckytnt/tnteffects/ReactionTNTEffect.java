package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class ReactionTNTEffect extends PrimedTNTEffect {
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		
		if (!level.isClientSide() && entity.getTNTFuse() < 100) {
			RandomSource random = level.getRandom();
			
			if (entity.getPersistentData().getInt("nextExplosion") == 0) {
				float explosionSize = 10f + random.nextFloat() * 10f;
				Vec3 randomPos = new Vec3(random.nextDouble() * 40d - 20d, random.nextDouble() * 20d - 10d, random.nextDouble() * 40d - 20d);
				Vec3 realPos = entity.getPos().add(randomPos);
				
				ImprovedExplosion explosion = new ImprovedExplosion(level, (Entity)entity, realPos, Math.round(explosionSize));
				explosion.doEntityExplosion(1f + 0.05f * explosionSize, true);
				explosion.doImprovedBlockExplosion(0.75f, 1.25f, false, false, null);
				explosion.spawnExplosionParticles();
				
				level.playSound(null, toBlockPos(realPos), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4f, (1f + (random.nextFloat() - random.nextFloat()) * 0.2f) * 0.7f);
				entity.getPersistentData().putInt("nextExplosion", 2 + random.nextInt(3));
			}
			entity.getPersistentData().putInt("nextExplosion", entity.getPersistentData().getInt("nextExplosion") - 1);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		
		if (random.nextDouble() < 0.15d) {
			level.addParticle(new DustParticleOptions(new Vector3f(0.37f, 1f, 1f), 1f), entity.x() + random.nextDouble() * 0.5d - random.nextDouble() * 0.5d, entity.y() + 1d + random.nextDouble() * 0.35d, entity.z() + random.nextDouble() * 0.5d - random.nextDouble() * 0.5d, 0d, 0d, 0d);
		}
		if (random.nextDouble() < 0.15d) {
			level.addParticle(new DustParticleOptions(new Vector3f(0.59f, 1f, 0f), 1f), entity.x() + random.nextDouble() * 0.5d - random.nextDouble() * 0.5d, entity.y() + 1d + random.nextDouble() * 0.35d, entity.z() + random.nextDouble() * 0.5d - random.nextDouble() * 0.5d, 0d, 0d, 0d);
		}
		if (random.nextDouble() < 0.15d) {
			level.addParticle(new DustParticleOptions(new Vector3f(0.11f, 0.26f, 0.11f), 1f), entity.x() + random.nextDouble() * 0.5d - random.nextDouble() * 0.5d, entity.y() + 1d + random.nextDouble() * 0.35d, entity.z() + random.nextDouble() * 0.5d - random.nextDouble() * 0.5d, 0d, 0d, 0d);
		}
		if (random.nextDouble() < 0.15d) {
			level.addParticle(new DustParticleOptions(new Vector3f(0.16f, 0.42f, 0.15f), 1f), entity.x() + random.nextDouble() * 0.5d - random.nextDouble() * 0.5d, entity.y() + 1d + random.nextDouble() * 0.35d, entity.z() + random.nextDouble() * 0.5d - random.nextDouble() * 0.5d, 0d, 0d, 0d);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.REACTION_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 240;
	}
}
