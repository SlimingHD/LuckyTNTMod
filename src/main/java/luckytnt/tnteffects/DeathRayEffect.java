package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.SoundRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DeathRayEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		if(ent.getTNTFuse() == 480) {
			ent.getPersistentData().putInt("explosionSize", 1);
			ent.getPersistentData().putInt("particleSize", 1);
			ent.getLevel().playSound(null, ent.x(), ent.y(), ent.z(), SoundRegistry.DEATH_RAY.get(), SoundSource.HOSTILE, 20, 1);
			((Entity)ent).setDeltaMovement(0, 0, 0);
		}
		
		if(ent.getTNTFuse() < 80) {
			((Entity)ent).setDeltaMovement(0, 0, 0);
			((Entity)ent).setPos(((Entity)ent).xOld, ((Entity)ent).yOld, ((Entity)ent).zOld);
			
			ExplosionHelper.doSphericalExplosion(ent.getLevel(), ent.getPos(), ent.getPersistentData().getInt("explosionSize"), new IForEachBlockExplosionEffect() {
				
				@Override
				public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
					if(distance >= 75) {
						if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) < 2000 && !state.isAir()) {
							if(Math.random() < 0.1f) {
								level.setBlock(pos, Blocks.LAVA.defaultBlockState(), 3);
							} else if(Math.random() < 0.8f) {
								level.setBlock(pos, Blocks.OBSIDIAN.defaultBlockState(), 3);
							}
						}
					} else if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) < 2000 && !state.isAir()) {
						state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
					}
				}
			});
			
			ent.getPersistentData().putInt("explosionSize", ent.getPersistentData().getInt("explosionSize") + 1);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		if(ent.getTNTFuse() > 120) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.8f, 0f, 0f), 1f), true, ent.x(), ent.y(), ent.z(), 0, 0, 0);
		}
		if(ent.getTNTFuse() < 140) {
			for(int count = 0; count < 200; count++) {
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.5f, 0f, 2f), 10f), true, ent.x() + Math.random() - Math.random(), ent.y() + 135f - Math.random() * ent.getPersistentData().getInt("particleSize"), ent.z() + Math.random() - Math.random(), 0, 0, 0);
			}
			ent.getPersistentData().putInt("particleSize", ent.getPersistentData().getInt("particleSize") + 2);
		}
	}
	
	@Override
	public Block getBlock() {
		return Blocks.AIR;
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 480;
	}
}
