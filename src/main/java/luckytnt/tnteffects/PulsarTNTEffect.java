package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;

public class PulsarTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		if(ent.getTNTFuse() == 399) {
			ent.getPersistentData().putFloat("size", 30f);
		}
		if(ent.getTNTFuse() < 305) {
			if(ent.getTNTFuse() % 30 == 0 && !ent.getLevel().isClientSide()) {
				ImprovedExplosion explosion = new ImprovedExplosion(ent.getLevel(), (Entity)ent, ent.getPos(), Mth.floor(ent.getPersistentData().getFloat("size")));
				explosion.doEntityExplosion(4f, true);
				explosion.doBlockExplosion(1f, ent.getPersistentData().getFloat("size") > 45f ? 1.3f : 1f, 1f, ent.getPersistentData().getFloat("size") <= 80f ? 1.25f : 0.05f, false, ent.getPersistentData().getFloat("size") > 80f ? true : false);
			
				ent.getPersistentData().putFloat("size", ent.getPersistentData().getFloat("size") + 7f);
			}
			((Entity)ent).setDeltaMovement(0, 0, 0);
			((Entity)ent).setPos(((Entity)ent).xOld, ((Entity)ent).yOld, ((Entity)ent).zOld);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		for(double offX = -2; offX <= 2; offX+=0.1) {
     		for(double offZ = -2; offZ <= 2; offZ+=0.1) {
     			double offY = Math.sqrt(offX * offX + offZ * offZ);
     			if(offY <= 1.2) {
     				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.4f, 0f, 0.8f), 1f), ent.x() + offX, ent.y() + 1 + (offY * 4), ent.z() + offZ, 0, 0, 0);
     				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.4f, 0f, 0.8f), 1f), ent.x() + offX, ent.y() + (offY * -4), ent.z() + offZ, 0, 0, 0);
     			}
     		}
     	}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.PULSAR_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 400;
	}
}
