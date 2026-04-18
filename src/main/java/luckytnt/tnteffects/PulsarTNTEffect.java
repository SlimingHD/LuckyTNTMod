package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;

public class PulsarTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide()) {
			CompoundTag tag = entity.getPersistentData();
			if (entity.getTNTFuse() == 399) {
				tag.putInt("size", 30);
			}
			if (entity.getTNTFuse() < 305) {
				if (entity.getTNTFuse() % 30 == 0) {
					playExplosionSound(entity);
					
					ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity) entity, entity.getPos(), tag.getInt("size"));
					explosion.doEntityExplosion(4f, true);
					explosion.doImprovedBlockExplosion(1f, tag.getInt("size") <= 80f ? 1.25f : 0.05f, tag.getInt("size") > 80f, false, null);
					explosion.spawnExplosionParticles();

					tag.putInt("size", tag.getInt("size") + 7);
				}
			}
		}
		if (entity.getTNTFuse() < 305 && entity instanceof Entity ent) {
			ent.setDeltaMovement(0d, 0d, 0d);
			ent.setPos(ent.getPosition(0f));
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		double distSqr = 1.2d * 1.2d;
		for (double offX = -2d; offX <= 2d; offX += 0.1d) {
     		for (double offZ = -2d; offZ <= 2d; offZ += 0.1d) {
     			double offY = offX * offX + offZ * offZ;
     			if (offY <= distSqr) {
     				entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.4f, 0f, 0.8f), 1f), entity.x() + offX, entity.y() + 1d + (offY * 4d), entity.z() + offZ, 0d, 0d, 0d);
     				entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.4f, 0f, 0.8f), 1f), entity.x() + offX, entity.y() + (offY * -4d), entity.z() + offZ, 0d, 0d, 0d);
     			}
     		}
     	}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.PULSAR_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 400;
	}
}
