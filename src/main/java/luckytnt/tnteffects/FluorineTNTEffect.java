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
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class FluorineTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		if(level.isClientSide()) {
			return;
		}
		RandomSource random = level.getRandom();
		if(entity.getTNTFuse() < 310) {
			if(entity.getPersistentData().getInt("nextExplosion") <= 0) {
				double x = entity.x() + random.nextDouble() * 160d - 80d;
				double y = entity.y() + random.nextDouble() * 60d - 30d;
				double z = entity.z() + random.nextDouble() * 160d - 80d;
				ImprovedExplosion explosion = new ImprovedExplosion(level, (Entity)entity, new Vec3(x, y, z), 50 + random.nextInt(31));
				explosion.doEntityExplosion(7f, true);
				explosion.doImprovedBlockExplosion(0.75f, 0.5f, false, false, null);
				level.playSound(null, new BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z)), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4, (1f + (random.nextFloat() - random.nextFloat()) * 0.2f) * 0.7f);
				entity.getPersistentData().putInt("nextExplosion", 5 + random.nextInt(3));
			}
			entity.getPersistentData().putInt("nextExplosion", entity.getPersistentData().getInt("nextExplosion") - 1);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		for (double i = 0d; i < 1d; i += 0.05d) {
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0.65f), 1f), entity.x() + 0.5d, entity.y() + i, entity.z() + 0.5d, 0, 0, 0);
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0.65f), 1f), entity.x() - 0.5d, entity.y() + i, entity.z() + 0.5d, 0, 0, 0);
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0.65f), 1f), entity.x() + 0.5d, entity.y() + i, entity.z() - 0.5d, 0, 0, 0);
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0.65f), 1f), entity.x() - 0.5d, entity.y() + i, entity.z() - 0.5d, 0, 0, 0);
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0.65f), 1f), entity.x() + 0.5d, entity.y() + i, entity.z(), 0, 0, 0);
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0.65f), 1f), entity.x() - 0.5d, entity.y() + i, entity.z(), 0, 0, 0);
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0.65f), 1f), entity.x(), entity.y() + i, entity.z() + 0.5d, 0, 0, 0);
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0.65f), 1f), entity.x(), entity.y() + i, entity.z() - 0.5d, 0, 0, 0);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.FLUORINE_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 400;
	}
}
