package luckytnt.tnteffects;

import luckytnt.registry.SoundRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterRandomExplosionRule;
import luckytntlib.util.explosions.rules.ScheduleTickExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class DeathRayEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Entity ent = (Entity)entity;
		if(entity.getTNTFuse() == 480) {
			entity.getPersistentData().putInt("explosionSize", 1);
			entity.getPersistentData().putInt("particleSize", 1);
			entity.getLevel().playSound(null, entity.x(), entity.y(), entity.z(), SoundRegistry.DEATH_RAY.get(), SoundSource.HOSTILE, 20, 1);
			ent.setDeltaMovement(0, 0, 0);
		}
		
		if(entity.getTNTFuse() < 80) {
			ent.setDeltaMovement(0, 0, 0);
			ent.setPos(ent.xOld, ent.yOld, ent.zOld);
			
			if(entity.getTNTFuse() > 5) {
				ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), entity.getPersistentData().getInt("explosionSize"), 2000);
			} else {
				ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), entity.getPersistentData().getInt("explosionSize"), 2000, new FilterAirExplosionRule(
						new StackedExplosionRule(
								new FilterRandomExplosionRule(0.1f, new ScheduleTickExplosionRule(new BlockExplosionRule(Blocks.LAVA.defaultBlockState()))),
								new FilterRandomExplosionRule(0.8f, new BlockExplosionRule(Blocks.OBSIDIAN.defaultBlockState()))
						)
				));
			}
			
			entity.getPersistentData().putInt("explosionSize", entity.getPersistentData().getInt("explosionSize") + 1);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
	}
	
	@Override
	public Block getBlock() {
		return Blocks.AIR;
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 480;
	}
}
