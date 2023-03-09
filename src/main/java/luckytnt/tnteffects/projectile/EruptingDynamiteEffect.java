package luckytnt.tnteffects.projectile;

import luckytnt.registry.EntityRegistry;
import luckytnt.registry.ItemRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class EruptingDynamiteEffect extends PrimedTNTEffect{

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
		if(entity.getTNTFuse() < 15 && entity.getTNTFuse() % 3 == 0) {
			LExplosiveProjectile erupting_tnt = EntityRegistry.ERUPTING_PROJECTILE.get().create(level);
			erupting_tnt.setPos(entity.getPos());
			erupting_tnt.setOwner(entity.owner());
			erupting_tnt.shoot((Math.random() * 2D - 1D) * 0.1f, 0.6f + Math.random() * 0.4f, (Math.random() * 2D - 1D) * 0.1f, 2f + level.random.nextFloat(), 0f);	
			erupting_tnt.setSecondsOnFire(1000);
			level.addFreshEntity(erupting_tnt);
			level.playSound(null, new BlockPos(entity.getPos()), SoundEvents.GENERIC_EXPLODE, SoundSource.MASTER, 3, 1);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.level().addParticle(ParticleTypes.FLAME, entity.x(), entity.y(), entity.z(), 0, 0, 0);
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.ERUPTING_DYNAMITE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 40;
	}
}
