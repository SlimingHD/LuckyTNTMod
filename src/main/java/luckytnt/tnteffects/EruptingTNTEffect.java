package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.entity.LTNTMinecart;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class EruptingTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		if ((entity instanceof PrimedLTNT || entity instanceof LTNTMinecart) && entity.getTNTFuse() < 60 && entity.getTNTFuse() % 3 == 0) {
			LExplosiveProjectile erupting = EntityRegistry.ERUPTING_PROJECTILE.get().create(level);
			erupting.setPos(entity.getPos());
			erupting.setOwner(entity.owner());
			erupting.shoot(random.nextDouble() * 0.2d - 0.1d, 0.6d + random.nextDouble() * 0.4d, random.nextDouble() * 0.2d - 0.1d, 3f + random.nextFloat() * 2f, 0f);	
			erupting.setSecondsOnFire(1000);
			level.addFreshEntity(erupting);
			level.playSound(null, toBlockPos(entity.getPos()), SoundEvents.GENERIC_EXPLODE, SoundSource.MASTER, 3, 1);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		if (entity instanceof PrimedLTNT) {
			entity.getLevel().addParticle(ParticleTypes.SMOKE, entity.x() + 0.5f, entity.y() + 1f, entity.z() + 0.5f, 0.05f, 0.2f, 0.05f);
			entity.getLevel().addParticle(ParticleTypes.SMOKE, entity.x() - 0.5f, entity.y() + 1f, entity.z() - 0.5f, -0.05f, 0.2f, -0.05f);
			entity.getLevel().addParticle(ParticleTypes.SMOKE, entity.x() + 0.5f, entity.y() + 1f, entity.z() - 0.5f, 0.05f, 0.2f, -0.05f);
			entity.getLevel().addParticle(ParticleTypes.SMOKE, entity.x() - 0.5f, entity.y() + 1f, entity.z() + 0.5f, -0.05f, 0.2f, 0.05f);
		} else {
			entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5f, entity.z(), 0, 0, 0);
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
