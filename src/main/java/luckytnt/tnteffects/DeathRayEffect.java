package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.LuckyTNTDamageSources;
import luckytnt.registry.SoundRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.util.AdvancementHelper;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterRandomExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class DeathRayEffect extends PrimedTNTEffect {

	public static final int DURATION = 520;
	public static final int LASER_START = DURATION - 170;
	public static final int SHOOT_START = DURATION - 380;
	public static final int RAY_START = SHOOT_START - 25;
	public static final int OBSIDIAN_START = RAY_START - 80;
	public static final int RAY_END = RAY_START - 85;
	public static final int FADEOUT_END = RAY_END - 10;
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		Entity ent = (Entity)entity;
		if(entity.getTNTFuse() == DURATION) {
			entity.getPersistentData().putInt("explosionSize", 1);
			entity.getPersistentData().putInt("particleSize", 1);
			level.playSound(null, entity.x(), entity.y(), entity.z(), SoundRegistry.DEATH_RAY.get(), SoundSource.HOSTILE, 20, 1);
			ent.setDeltaMovement(0, 0, 0);
		}
		
		if(entity.getTNTFuse() <= RAY_START && entity.getTNTFuse() >= RAY_END) {
			ent.setDeltaMovement(0, 0, 0);
			ent.setPos(ent.getPosition(0f));
			int explosionSize = entity.getPersistentData().getInt("explosionSize");
			
			if (!level.isClientSide()) {	
				if (entity.getTNTFuse() % 2 == 0) {
					if(entity.getTNTFuse() >= OBSIDIAN_START) {
						ExplosionHelper.createSphericalCrater(level, entity.getPos(), explosionSize, 2000f);
					} else {
						ExplosionHelper.createSphericalCrater(level, entity.getPos(), explosionSize, 2000f, new FilterAirExplosionRule(
							new StackedExplosionRule(
								new FilterRandomExplosionRule(0.8f, new BlockExplosionRule(Blocks.OBSIDIAN.defaultBlockState())),
								new FilterRandomExplosionRule(0.1f, new BlockExplosionRule(Blocks.MAGMA_BLOCK.defaultBlockState()))
							)
						));
					}
				}
				
				List<Entity> entities = level.getEntities(ent, ent.getBoundingBox().inflate(explosionSize + 5), EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
				entities.forEach(e -> {
					e.hurt(LuckyTNTDamageSources.deathRay(level, entity.owner()), explosionSize * 4f);
				});
			}
			
			entity.getPersistentData().putInt("explosionSize", explosionSize + 1);
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		AdvancementHelper.grantAdvancementToOwnerOrNearby(entity, AdvancementKeys.ORBITAL_STRIKE);
	}
	
	@Override
	public void playExplosionSound(Level level, Vec3 pos) {
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
		return DURATION;
	}
}
