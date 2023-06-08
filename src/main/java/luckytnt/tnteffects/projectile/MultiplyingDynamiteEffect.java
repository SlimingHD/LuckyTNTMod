package luckytnt.tnteffects.projectile;

import luckytnt.registry.EntityRegistry;
import luckytnt.registry.ItemRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class MultiplyingDynamiteEffect extends PrimedTNTEffect{

	@Override
	public void baseTick(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		if(entity instanceof LExplosiveProjectile ent) {
			if(ent.inGround() && ent.getPersistentData().getInt("level") >= 3 && level instanceof ServerLevel) {
				serverExplosion(ent);
				ent.destroy();
			}
			if(ent.getTNTFuse() == 0 && level instanceof ServerLevel) {
				serverExplosion(ent);
				ent.destroy();
			}
			if(ent.getPersistentData().getInt("level") < 3) {
				explosionTick(ent);
				ent.setTNTFuse(ent.getTNTFuse() - 1);
			}
			if(level.isClientSide) {
				spawnParticles(entity);
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		if(entity.getPersistentData().getInt("level") < 3) {	
			for(int count = 0; count < 4; count++) {
				LExplosiveProjectile dynamite = EntityRegistry.MULTIPLYING_DYNAMITE.get().create(entity.getLevel());
				dynamite.setPos(entity.getPos());
				dynamite.setOwner(entity.owner());
				dynamite.setDeltaMovement(((Entity)entity).getDeltaMovement().add(Math.random() * 0.5f - 0.25f, Math.random() * 0.5f - 0.25f, Math.random() * 0.5f - 0.25f));
				dynamite.getPersistentData().putInt("level", entity.getPersistentData().getInt("level") + 1);
				entity.getLevel().addFreshEntity(dynamite);
			}
		}
		else {
			ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 8);
			explosion.doEntityExplosion(0.75f, true);
			explosion.doBlockExplosion(1f, 1f, 1f, 1.25f, false, false);
			level.playSound((Entity)entity, toBlockPos(entity.getPos()), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4f, (1f + (level.random.nextFloat() - level.random.nextFloat()) * 0.2f) * 0.7f);
		}
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if(entity.getPersistentData().getInt("level") < 3) {
			((Entity)entity).setDeltaMovement(((Entity)entity).getDeltaMovement().add(0f, 0.08f, 0f));
		}
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.MULTIPLYING_DYNAMITE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 20;
	}
}
