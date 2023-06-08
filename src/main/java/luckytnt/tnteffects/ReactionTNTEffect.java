package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class ReactionTNTEffect extends PrimedTNTEffect{
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		if(!level.isClientSide && entity.getTNTFuse() < 100) {
			if(entity.getPersistentData().getInt("nextExplosion") == 0) {
				Vec3 randomPos = new Vec3(Math.random() * 40 - 20, Math.random() * 20 - 10, Math.random() * 40 - 20);
				float explosionSize = 10 + level.random.nextFloat() * 10;
				ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos().add(randomPos), Math.round(explosionSize));
				explosion.doEntityExplosion(1f + 0.05f * explosionSize, true);
				explosion.doBlockExplosion(1f, 1f, 0.75f, 1.25f, false, false);
				level.playSound((Entity)entity, toBlockPos(entity.getPos()).offset(toBlockPos(randomPos)), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4f, (1f + (level.random.nextFloat() - level.random.nextFloat()) * 0.2f) * 0.7f);
				entity.getPersistentData().putInt("nextExplosion", 2 + level.random.nextInt(3));
			}
			entity.getPersistentData().putInt("nextExplosion", entity.getPersistentData().getInt("nextExplosion") - 1);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		if(Math.random() < 0.15f) {
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.37f, 1f, 1f), 1), entity.x() + Math.random() * 0.5f - Math.random() * 0.5f, entity.y() + 1f + Math.random() * 0.35f, entity.z() + Math.random() * 0.5f - Math.random() * 0.5f, 0, 0, 0);
		}
		if(Math.random() < 0.15f) {
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.59f, 1f, 0f), 1), entity.x() + Math.random() * 0.5f - Math.random() * 0.5f, entity.y() + 1f + Math.random() * 0.35f, entity.z() + Math.random() * 0.5f - Math.random() * 0.5f, 0, 0, 0);
		}
		if(Math.random() < 0.15f) {
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.11f, 0.26f, 0.11f), 1), entity.x() + Math.random() * 0.5f - Math.random() * 0.5f, entity.y() + 1f + Math.random() * 0.35f, entity.z() + Math.random() * 0.5f - Math.random() * 0.5f, 0, 0, 0);
		}
		if(Math.random() < 0.15f) {
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.16f, 0.42f, 0.15f), 1), entity.x() + Math.random() * 0.5f - Math.random() * 0.5f, entity.y() + 1f + Math.random() * 0.35f, entity.z() + Math.random() * 0.5f - Math.random() * 0.5f, 0, 0, 0);
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
