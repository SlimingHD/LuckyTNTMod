package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;

public class SpiralTNTEffect extends PrimedTNTEffect{
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if(entity instanceof PrimedLTNT) {
			Entity ent = (Entity)entity;
			ent.setDeltaMovement(ent.getDeltaMovement().x, 0.15f, ent.getDeltaMovement().z);
			if(entity.getTNTFuse() < 60) {
				if(entity.getTNTFuse() % 3 == 0) {
					ent.getPersistentData().putFloat("spiral_power", Mth.clamp(ent.getPersistentData().getFloat("spiral_power") + 0.06f, 0.2f, Float.MAX_VALUE));
					LExplosiveProjectile spiral_tnt = EntityRegistry.SPIRAL_PROJECTILE.get().create(entity.getLevel());
					spiral_tnt.setPos(entity.x(), entity.y(), entity.z());
					spiral_tnt.setOwner(entity.owner());
					spiral_tnt.shoot(ent.getLookAngle().x, ent.getLookAngle().y, ent.getLookAngle().z, ent.getPersistentData().getFloat("spiral_power"), 0);
					entity.getLevel().playSound(null, toBlockPos(entity.getPos()), SoundEvents.DISPENSER_LAUNCH, SoundSource.MASTER, 3, 1);
					entity.getLevel().addFreshEntity(spiral_tnt);
					ent.setYRot(ent.getYRot() + 30f);
				}
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.SPIRAL_TNT.get();
	}
	
	@Override
	public boolean airFuse() {
		return true;
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return entity instanceof PrimedLTNT ? 140 : 10000;
	}
}
