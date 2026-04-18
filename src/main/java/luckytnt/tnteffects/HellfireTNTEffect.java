package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterRandomExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.level.Level;
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
		
		ImprovedExplosion netherExplosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), Mth.floor(strength * 1.5f));
		netherExplosion.doImprovedBlockExplosion(1f, 1.5f, false, true, new FilterAirExplosionRule(
			new StackedExplosionRule(
				new FilterRandomExplosionRule(0.9f, new BlockExplosionRule(Blocks.NETHERRACK.defaultBlockState())),
				new FilterRandomExplosionRule(0.3f, new BlockExplosionRule(Blocks.LAVA.defaultBlockState()))
			)
		));
		
		for (int i = 0; i < ghastCount; i++) {
			Ghast ghast = new Ghast(EntityType.GHAST, level);
			ghast.setPos(entity.getPos().add(0d, 20d + random.nextDouble() * 20d, 0d));
			level.playSound(ghast, ghast.blockPosition(), SoundEvents.GHAST_HURT, SoundSource.HOSTILE, 3f, 1f);
			level.addFreshEntity(ghast);
		}
		
		explosion.spawnExplosionParticles();
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
