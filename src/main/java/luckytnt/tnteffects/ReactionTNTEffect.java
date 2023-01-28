package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class ReactionTNTEffect extends PrimedTNTEffect{

	private int nextExplosion = 0;
	
	@SuppressWarnings("resource")
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if(!entity.level().isClientSide && entity.getTNTFuse() < 100) {
			if(nextExplosion == 0) {
				Vec3 randomPos = new Vec3(Math.random() * 40 - 20, Math.random() * 20 - 10, Math.random() * 40 - 20);
				float explosionSize = 10 + entity.level().random.nextFloat() * 10;
				ImprovedExplosion explosion = new ImprovedExplosion(entity.level(), (Entity)entity, entity.getPos().add(randomPos), explosionSize);
				explosion.doEntityExplosion(1f + 0.05f * explosionSize, true);
				explosion.doBlockExplosion(1f, 1f, 0.75f, 1.25f, false, false);
				entity.level().playSound((Entity)entity, new BlockPos(entity.getPos()).offset(randomPos.x, randomPos.y, randomPos.z), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4f, (1f + (entity.level().random.nextFloat() - entity.level().random.nextFloat()) * 0.2f) * 0.7f);
				nextExplosion = 2 + entity.level().random.nextInt(3);
			}
			nextExplosion--;
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		if(Math.random() < 0.15f) {
			entity.level().addParticle(new DustParticleOptions(new Vector3f(0.37f, 1f, 1f), 1), entity.x() + Math.random() * 0.5f - Math.random() * 0.5f, entity.y() + 1f + Math.random() * 0.35f, entity.z() + Math.random() * 0.5f - Math.random() * 0.5f, 0, 0, 0);
		}
		if(Math.random() < 0.15f) {
			entity.level().addParticle(new DustParticleOptions(new Vector3f(0.59f, 1f, 0f), 1), entity.x() + Math.random() * 0.5f - Math.random() * 0.5f, entity.y() + 1f + Math.random() * 0.35f, entity.z() + Math.random() * 0.5f - Math.random() * 0.5f, 0, 0, 0);
		}
		if(Math.random() < 0.15f) {
			entity.level().addParticle(new DustParticleOptions(new Vector3f(0.11f, 0.26f, 0.11f), 1), entity.x() + Math.random() * 0.5f - Math.random() * 0.5f, entity.y() + 1f + Math.random() * 0.35f, entity.z() + Math.random() * 0.5f - Math.random() * 0.5f, 0, 0, 0);
		}
		if(Math.random() < 0.15f) {
			entity.level().addParticle(new DustParticleOptions(new Vector3f(0.16f, 0.42f, 0.15f), 1), entity.x() + Math.random() * 0.5f - Math.random() * 0.5f, entity.y() + 1f + Math.random() * 0.35f, entity.z() + Math.random() * 0.5f - Math.random() * 0.5f, 0, 0, 0);
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
