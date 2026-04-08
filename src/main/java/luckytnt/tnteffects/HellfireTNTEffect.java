package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class HellfireTNTEffect extends PrimedTNTEffect {
	
	private final int strength;
	private final int ghastCount;
	
	public HellfireTNTEffect(int strength, int ghastCount) {
		this.strength = strength;
		this.ghastCount = ghastCount;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		
		ImprovedExplosion explosion = new ImprovedExplosion(level, (Entity)entity, entity.getPos(), strength);
		explosion.doEntityExplosion(2f, true);
		explosion.doImprovedBlockExplosion(1f, 1.5f, false, false, null);
		
		ImprovedExplosion explosion2 = new ImprovedExplosion(entity.getLevel(), (Entity)entity, null, entity.x(), entity.y(), entity.z(), Mth.floor(strength * 1.5f), false, (lev, center, pos, state) -> {
			if (random.nextFloat() < 0.9f) {
				lev.setBlockAndUpdate(pos, Blocks.NETHERRACK.defaultBlockState());
				if (random.nextFloat() < 0.1f) {
					BlockPos posAbove = pos.above();
					if (lev.getBlockState(posAbove).isAir()) {
						lev.setBlockAndUpdate(posAbove, BaseFireBlock.getState(lev, posAbove));
					}
				}
				state.getBlock().wasExploded(level, pos, explosion);
			} else if (random.nextFloat() < 0.3f) {
				level.setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState());
				state.getBlock().wasExploded(level, pos, explosion);
			}
		});
		explosion2.doImprovedBlockExplosion(1f, 1.5f, false, false, null);
		
		for (int i = 0; i < ghastCount; i++) {
			Ghast ghast = new Ghast(EntityType.GHAST, level);
			ghast.setPos(entity.getPos().add(0d, 20d + random.nextDouble() * 20d, 0d));
			level.playSound(ghast, ghast.blockPosition(), SoundEvents.GHAST_HURT, SoundSource.HOSTILE, 3f, 1f);
			level.addFreshEntity(ghast);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		
		level.addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5d, entity.z(), 0d, 0.1d, 0d);
		level.addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5d, entity.z(), 0.05d, 0.1d, 0d);
		level.addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5d, entity.z(), -0.05d, 0.1d, 0d);
		level.addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5d, entity.z(), 0d, 0.1d, 0.05d);
		level.addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5d, entity.z(), 0d, 0.1d, -0.05d);
		
		level.addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5d, entity.z(), 0.2d, 0d, 0d);
		level.addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5d, entity.z(), -0.2d, 0d, 0d);
		level.addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5d, entity.z(), 0d, 0d, 0.2d);
		level.addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5d, entity.z(), 0d, 0d, -0.2d);
		
		level.addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5d, entity.z(), 0.1d, 0d, 0.1d);
		level.addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5d, entity.z(), -0.1d, 0d, -0.1d);
		level.addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5d, entity.z(), 0.1d, 0d, -0.1d);
		level.addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5d, entity.z(), -0.1d, 0d, 0.1d);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.HELLFIRE_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
}
