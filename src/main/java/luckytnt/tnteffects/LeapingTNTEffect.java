package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;

public class LeapingTNTEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		if(((Entity)entity).getPersistentData().getInt("bounces") < 24) {
			ImprovedExplosion explosion = new ImprovedExplosion(entity.level(), (Entity)entity, entity.getPos(), 10f);
			explosion.doEntityExplosion(1.5f, true);
			explosion.doBlockExplosion(1f, 1f, 1f, 1.25f, false, false);
		} else {
			ImprovedExplosion explosion = new ImprovedExplosion(entity.level(), (Entity)entity, entity.getPos(), 20f);
			explosion.doEntityExplosion(2f, true);
			explosion.doBlockExplosion(1f, 1f, 1f, 1.5f, false, false);
		}
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if(((Entity)entity).isOnGround()) {
			((Entity)entity).getPersistentData().putInt("bounces", ((Entity)entity).getPersistentData().getInt("bounces") + 1);
			((Entity)entity).setDeltaMovement(Math.random() * 1.5D - Math.random() * 1.5D, Math.random() * 2f, Math.random() * 1.5D - Math.random() * 1.5D);
			entity.level().playSound(null, entity.x(), entity.y(), entity.z(), SoundEvents.SLIME_JUMP, SoundSource.MASTER, 1, 1);
			if(((Entity)entity).getPersistentData().getInt("bounces") >= 24) {
				if(entity.level() instanceof ServerLevel) {
					serverExplosion(entity);
				}
				entity.destroy();
			}
			if(((Entity)entity).getPersistentData().getInt("bounces") >= 1 && ((Entity)entity).getPersistentData().getInt("bounces") < 24 && entity.level() instanceof ServerLevel) {
				serverExplosion(entity);
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.LEAPING_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 100000;
	}
}
