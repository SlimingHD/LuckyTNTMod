package luckytnt.tnteffects.projectile;

import luckytnt.registry.EntityRegistry;
import luckytnt.registry.ItemRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;

public class SpiralDynamiteEffect extends PrimedTNTEffect{

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Entity ent = (Entity)entity;
		ent.setDeltaMovement(ent.getDeltaMovement().add(0f, 0.08f, 0f));
		if(entity.getTNTFuse() < 30) {
			if(entity.getTNTFuse() % 3 == 0) {
				ent.setXRot(180f);
				ent.setYRot(ent.getPersistentData().getFloat("angle") + 30f);
				ent.getPersistentData().putFloat("angle", ent.getYRot());
				ent.getPersistentData().putFloat("spiral_power", Mth.clamp(ent.getPersistentData().getFloat("spiral_power") + 0.12f, 0.2f, Float.MAX_VALUE));
				LExplosiveProjectile spiral_tnt = EntityRegistry.SPIRAL_PROJECTILE.get().create(entity.getLevel());
				spiral_tnt.setPos(entity.x(), entity.y(), entity.z());
				spiral_tnt.setOwner(entity.owner());
				spiral_tnt.shoot(ent.getLookAngle().x, ent.getLookAngle().y, ent.getLookAngle().z, ent.getPersistentData().getFloat("spiral_power"), 0);
				entity.getLevel().playSound(null, toBlockPos(entity.getPos()), SoundEvents.DISPENSER_LAUNCH, SoundSource.MASTER, 3, 1);
				entity.getLevel().addFreshEntity(spiral_tnt);
			}
		}
	}
	
	@Override
	public boolean playsSound() {
		return false;
	}

	@Override
	public boolean airFuse() {
		return true;
	}
	
	@Override
	public boolean explodesOnImpact() {
		return false;
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.SPIRAL_DYNAMITE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 60;
	}
}
