package luckytnt.tnteffects;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.CopyBlockExplosionRule;
import luckytntlib.util.explosions.rules.CraterExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlastResistanceExplosionRule;
import luckytntlib.util.explosions.rules.FilterOffYExplosionRule;
import luckytntlib.util.explosions.rules.OffsetExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class HeavensGateEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos().add(0d, LuckyTNTConfigValues.ISLAND_HEIGHT.get(), 0d), 30, 200f, new FilterOffYExplosionRule(-20, 20,
			new OffsetExplosionRule(-LuckyTNTConfigValues.ISLAND_HEIGHT.get(), new FilterBlastResistanceExplosionRule(200f, new CopyBlockExplosionRule())
		)));
		
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), 30, 200f, new FilterOffYExplosionRule(-20, 20, new CraterExplosionRule()));
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		
		level.addParticle(ParticleTypes.SMOKE, entity.x() + 0.7d, entity.y(), entity.z(), 0d, 0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x() - 0.7d, entity.y(), entity.z(), 0d, 0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y(), entity.z() + 0.7d, 0d, 0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y(), entity.z() - 0.7d, 0d, 0.1d, 0d);
		
		level.addParticle(ParticleTypes.SMOKE, entity.x() + 0.6d, entity.y(), entity.z() + 0.6d, 0d, 0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x() - 0.6d, entity.y(), entity.z() - 0.6d, 0d, 0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x() - 0.6d, entity.y(), entity.z() + 0.6d, 0d, 0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x() + 0.6d, entity.y(), entity.z() - 0.6d, 0d, 0.1d, 0d);
		
		level.addParticle(ParticleTypes.SMOKE, entity.x() + 0.3d, entity.y() + 0.5d, entity.z(), 0d, 0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x() - 0.3d, entity.y() + 0.5d, entity.z(), 0d, 0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 0.5d, entity.z() + 0.3d, 0d, 0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 0.5d, entity.z() - 0.3d, 0d, 0.1d, 0d);
		
		level.addParticle(ParticleTypes.SMOKE, entity.x() + 0.2d, entity.y() + 0.5d, entity.z() + 0.2d, 0d, 0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x() - 0.2d, entity.y() + 0.5d, entity.z() - 0.2d, 0d, 0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x() - 0.2d, entity.y() + 0.5d, entity.z() + 0.2d, 0d, 0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x() + 0.2d, entity.y() + 0.5d, entity.z() - 0.2d, 0d, 0.1d, 0d);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.HEAVENS_GATE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 140;
	}
}
