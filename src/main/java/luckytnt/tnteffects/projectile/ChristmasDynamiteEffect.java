package luckytnt.tnteffects.projectile;

import java.util.Random;

import luckytnt.registry.EntityRegistry;
import luckytnt.registry.ItemRegistry;
import luckytnt.tnteffects.SnowTNTEffect;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ChristmasDynamiteEffect extends PrimedTNTEffect{

	@Override
	public void baseTick(IExplosiveEntity entity) {
		Level level = entity.level();
		if(entity instanceof LExplosiveProjectile ent) {
			if(ent.inGround() && ent.getTNTFuse() < 60) {
				if(level instanceof ServerLevel) {
					level.playSound((Entity)entity, new BlockPos(entity.getPos()), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4f, (1f + (level.random.nextFloat() - level.random.nextFloat()) * 0.2f) * 0.7f);
					serverExplosion(ent);
				}
				ent.destroy();
			}
			if(ent.getTNTFuse() > 0) {
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
		SnowTNTEffect snowEffect = new SnowTNTEffect(25);
		snowEffect.serverExplosion(entity);
		((ServerLevel)entity.level()).sendParticles(ParticleTypes.WAX_OFF, entity.x() + Math.random() - 0.5f, entity.y() + Math.random() - 0.5f, entity.z() + Math.random() - 0.5f, 500, 0.5f, 0.5f, 0.5f, 0f);
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if(entity.getTNTFuse() == 220) {
			entity.getPersistentData().putDouble("vecx", ((Entity)entity).getDeltaMovement().x);
			entity.getPersistentData().putDouble("vecz", ((Entity)entity).getDeltaMovement().z);
		}
		if(entity.getTNTFuse() <= 220 && entity.getTNTFuse() > 60) {
			((Entity)entity).setDeltaMovement(new Vec3(entity.getPersistentData().getDouble("vecx"), 0, entity.getPersistentData().getDouble("vecz")).normalize().scale(0.25f));
			if(entity.getTNTFuse() % 20 == 0) {
				LExplosiveProjectile dynamite = EntityRegistry.CHRISTMAS_DYNAMITE_PROJECTILE.get().create(entity.level());
				dynamite.setPos(entity.getPos());
				dynamite.setOwner(entity.owner());
				double randomX = Math.random();
				randomX *= new Random().nextBoolean() ? 1 : -1;
				double randomZ = Math.random();
				randomZ *= new Random().nextBoolean() ? 1 : -1;
				dynamite.setDeltaMovement(randomX, -Math.random() * 0.5f, randomZ);
				entity.level().addFreshEntity(dynamite);
			}
		}
		else if(entity.getTNTFuse() > 60){
			((Entity)entity).setDeltaMovement(((Entity)entity).getDeltaMovement().add(0f, 0.08f, 0f));
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		for(int i = 0; i < 7; i++) {
			entity.level().addParticle(ParticleTypes.WAX_OFF, true, entity.x() + Math.random() - 0.5f, entity.y() + Math.random() - 0.5f, entity.z() + Math.random() - 0.5f, 0, 0, 0);
		}
	}
	
	@Override
	public boolean explodesOnImpact() {
		return false;
	}
	
	@Override
	public boolean airFuse() {
		return true;
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.CHRISTMAS_DYNAMITE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 260;
	}
}
