package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class PulseTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		
		if (!level.isClientSide()) {
			if (entity.getTNTFuse() < 205) {
				if (entity.getTNTFuse() % 20 == 0) {		      		
					ImprovedExplosion explosion = new ImprovedExplosion(level, (Entity)entity, entity.getPos(), entity.getPersistentData().getInt("strength"));
					explosion.doEntityExplosion(1.5f, true);
					explosion.doImprovedBlockExplosion(1f, 1f, false, false, null);
					explosion.spawnExplosionParticles();
		      		
					if (entity.getTNTFuse() > 0) {
						playExplosionSound(entity);
					}
					
					entity.getPersistentData().putInt("strength", entity.getPersistentData().getInt("strength") + 2);
				}
			}
		}
		if (entity.getTNTFuse() < 205) {
			((Entity)entity).setDeltaMovement(0d, 0d, 0d);
			((Entity)entity).setPos(((Entity)entity).getPosition(0f));
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		
		for (double angle = 0d; angle < 360d; angle += 6d) {
			level.addParticle(new DustParticleOptions(new Vector3f(0.4f, 0.4f, 1f), 0.75f), entity.x() + 0.75d * Math.cos(angle * Mth.DEG_TO_RAD), entity.y(), entity.z() + 0.75d * Math.sin(angle * Mth.DEG_TO_RAD), 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(new Vector3f(0.4f, 0.4f, 1f), 0.75f), entity.x() + Math.cos(angle * Mth.DEG_TO_RAD), entity.y() + 0.5d, entity.z() + Math.sin(angle * Mth.DEG_TO_RAD), 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(new Vector3f(0.4f, 0.4f, 1f), 0.75f), entity.x() + 0.75d * Math.cos(angle * Mth.DEG_TO_RAD), entity.y() + 1d, entity.z() + 0.75d * Math.sin(angle * Mth.DEG_TO_RAD), 0d, 0d, 0d);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.PULSE_TNT.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 300;
	}
}
