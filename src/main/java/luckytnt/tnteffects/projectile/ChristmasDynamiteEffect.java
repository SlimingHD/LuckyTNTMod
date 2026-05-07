package luckytnt.tnteffects.projectile;

import org.joml.Math;

import luckytnt.registry.EntityRegistry;
import luckytnt.registry.ItemRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.tnteffects.SnowTNTEffect;
import luckytnt.util.AdvancementHelper;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ChristmasDynamiteEffect extends PrimedTNTEffect {

	@Override
	public void baseTick(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		if (entity instanceof LExplosiveProjectile ent) {
			if (ent.inGround() && ent.getTNTFuse() < 60) {
				if (level instanceof ServerLevel) {
					level.playSound((Entity)entity, toBlockPos(entity.getPos()), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4f, (1f + (level.random.nextFloat() - level.random.nextFloat()) * 0.2f) * 0.7f);
					serverExplosion(ent);
				}
				ent.destroy();
			}
			if (ent.getTNTFuse() > 0) {
				explosionTick(ent);
				ent.setTNTFuse(ent.getTNTFuse() - 1);
			}
			if (level.isClientSide) {
				spawnParticles(entity);
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		SnowTNTEffect snowEffect = new SnowTNTEffect(25);
		snowEffect.serverExplosion(entity);
		((ServerLevel)entity.getLevel()).sendParticles(ParticleTypes.WAX_OFF, entity.x() + Math.random() - 0.5f, entity.y() + Math.random() - 0.5f, entity.z() + Math.random() - 0.5f, 500, 0.5f, 0.5f, 0.5f, 0f);
		AdvancementHelper.grantAdvancementToOwnerOrNearby(entity, AdvancementKeys.TIS_THE_SEASON);
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Entity ent = (Entity)entity;
		Level level = entity.getLevel();
		if (entity.getTNTFuse() > 220) {
			ent.setDeltaMovement(ent.getDeltaMovement().add(0f, 0.08f, 0f));
		}
		if (entity.getTNTFuse() == 220) {
			entity.getPersistentData().putDouble("vecx", ent.getDeltaMovement().x);
			entity.getPersistentData().putDouble("vecz", ent.getDeltaMovement().z);
		}
		if (entity.getTNTFuse() <= 220 && entity.getTNTFuse() > 60) {
			ent.setDeltaMovement(new Vec3(entity.getPersistentData().getDouble("vecx"), 0, entity.getPersistentData().getDouble("vecz")).normalize().scale(0.25f));
			if (entity.getTNTFuse() % 20 == 0) {
				LExplosiveProjectile dynamite = EntityRegistry.CHRISTMAS_DYNAMITE_PROJECTILE.get().create(entity.getLevel());
				dynamite.setPos(entity.getPos());
				dynamite.setOwner(entity.owner());
				double randomX = level.getRandom().nextDouble() * (level.getRandom().nextBoolean() ? 1 : -1);
				double randomZ = level.getRandom().nextDouble() * (level.getRandom().nextBoolean() ? 1 : -1);
				dynamite.setDeltaMovement(randomX, level.getRandom().nextDouble() * -0.5f, randomZ);
				entity.getLevel().addFreshEntity(dynamite);
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		for(int i = 0; i < 7; i++) {
			entity.getLevel().addParticle(ParticleTypes.WAX_OFF, true, entity.x() + Math.random() - 0.5f, entity.y() + Math.random() - 0.5f, entity.z() + Math.random() - 0.5f, 0, 0, 0);
		}
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
