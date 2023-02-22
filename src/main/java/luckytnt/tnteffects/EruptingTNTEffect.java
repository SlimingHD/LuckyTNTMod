package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.entity.LTNTMinecart;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class EruptingTNTEffect extends PrimedTNTEffect{

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Level level = entity.level();
		if(entity instanceof PrimedLTNT || entity instanceof LTNTMinecart) {
			if(entity.getTNTFuse() < 60) {
				if(entity.getTNTFuse() % 3 == 0) {
					LExplosiveProjectile erupting_tnt = EntityRegistry.ERUPTING_PROJECTILE.get().create(level);
					erupting_tnt.setPos(entity.getPos());
					erupting_tnt.setOwner(entity.owner());
					erupting_tnt.shoot((Math.random() * 2D - 1D) * 0.1f, 0.6f + Math.random() * 0.4f, (Math.random() * 2D - 1D) * 0.1f, 3f + level.random.nextFloat() * 2f, 0f);	
					erupting_tnt.setSecondsOnFire(1000);
					level.addFreshEntity(erupting_tnt);
					level.playSound(null, new BlockPos(entity.getPos()), SoundEvents.GENERIC_EXPLODE, SoundSource.MASTER, 3, 1);
				}
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		if(entity instanceof PrimedLTNT) {
			entity.level().addParticle(ParticleTypes.SMOKE, entity.x() + 0.5f, entity.y() + 1f, entity.z() + 0.5f, 0.05f, 0.2f, 0.05f);
			entity.level().addParticle(ParticleTypes.SMOKE, entity.x() - 0.5f, entity.y() + 1f, entity.z() - 0.5f, -0.05f, 0.2f, -0.05f);
			entity.level().addParticle(ParticleTypes.SMOKE, entity.x() + 0.5f, entity.y() + 1f, entity.z() - 0.5f, 0.05f, 0.2f, -0.05f);
			entity.level().addParticle(ParticleTypes.SMOKE, entity.x() - 0.5f, entity.y() + 1f, entity.z() + 0.5f, -0.05f, 0.2f, 0.05f);
		}
		else {
			entity.level().addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5f, entity.z(), 0, 0, 0);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ERUPTING_TNT.get();
	}
	
	@Override
	public boolean airFuse() {
		return true;
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return entity instanceof PrimedLTNT || entity instanceof LTNTMinecart ? 140 : 100000;
	}
}
