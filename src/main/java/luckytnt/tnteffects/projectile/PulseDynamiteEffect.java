package luckytnt.tnteffects.projectile;

import com.mojang.math.Vector3f;

import luckytnt.registry.ItemRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class PulseDynamiteEffect extends PrimedTNTEffect{

	@Override
	public void baseTick(IExplosiveEntity entity) {
		Level level = entity.level();
		if(entity instanceof LExplosiveProjectile ent) {
			if(ent.inGround()) {
				ent.getPersistentData().putBoolean("hitBefore", true);
			}
			if(ent.getTNTFuse() == 0) {
				ent.destroy();
			}
			if(ent.inGround() || ent.getPersistentData().getBoolean("hitBefore")) {
				explosionTick(ent);
				ent.setTNTFuse(ent.getTNTFuse() - 1);
			}
			if(level.isClientSide) {
				spawnParticles(entity);
			}
		}
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Level level = entity.level();
		if (entity.getTNTFuse() <= 185) {
			((Entity)entity).setDeltaMovement(0, 0, 0);
			((Entity)entity).setPos(((Entity) entity).getPosition(0f));
			if (entity.getTNTFuse() % 20 == 0) {
				if (entity.level() instanceof ServerLevel) {
					ImprovedExplosion explosion = new ImprovedExplosion(entity.level(), (Entity)entity, entity.getPos(), entity.getPersistentData().getInt("strength"));
					explosion.doEntityExplosion(1f, true);
					explosion.doBlockExplosion(1f, 1f, 1f, 1.25f, false, false);
					level.playSound((Entity)entity, new BlockPos(entity.getPos()), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4f, (1f + (level.random.nextFloat() - level.random.nextFloat()) * 0.2f) * 0.7f);
					entity.getPersistentData().putInt("strength", entity.getPersistentData().getInt("strength") + 1);
				}
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		double phi = Math.PI * (3f - Math.sqrt(5f));
		for(int i = 0; i < 200; i++) {
			double y = 1f - ((double)i / (200f - 1f)) * 2f;
			double radius = Math.sqrt(1f - y * y);
			
			double theta = phi * i;
			
			double x = Math.cos(theta) * radius;
			double z = Math.sin(theta) * radius;
			
			entity.level().addParticle(new DustParticleOptions(new Vector3f(0.4f, 0.4f, 1f), 0.75f), entity.x() + x, entity.y() + y + 0.5f, entity.z() + z, 0, 0, 0);
		}
	}
	
	@Override
	public boolean explodesOnImpact() {
		return false;
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.PULSE_DYNAMITE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 300;
	}
}
