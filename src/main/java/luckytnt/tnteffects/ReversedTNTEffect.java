package luckytnt.tnteffects;

import java.util.List;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.BlockRegistry;
import luckytnt.rules.CopyBlockExplosionRule;
import luckytnt.rules.MirrorExplosionRule;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class ReversedTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ExplosionHelper.legacySphericalExplosion(ent.getLevel(), ent.getPos().add(0, LuckyTNTConfigValues.ISLAND_HEIGHT.get(), 0), 30, 200, new MirrorExplosionRule(
			List.of(Axis.Y), 
			new CopyBlockExplosionRule(-LuckyTNTConfigValues.ISLAND_HEIGHT.get())
		));
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		
		level.addParticle(ParticleTypes.SMOKE, entity.x() + 0.7d, entity.y() + 1d, entity.z(), 0d, -0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x() - 0.7d, entity.y() + 1d, entity.z(), 0d, -0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 1d, entity.z() + 0.7d, 0d, -0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 1d, entity.z() - 0.7d, 0d, -0.1d, 0d);

		level.addParticle(ParticleTypes.SMOKE, entity.x() + 0.6d, entity.y() + 1d, entity.z() + 0.6d, 0d, -0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x() - 0.6d, entity.y() + 1d, entity.z() - 0.6d, 0d, -0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x() - 0.6d, entity.y() + 1d, entity.z() + 0.6d, 0d, -0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x() + 0.6d, entity.y() + 1d, entity.z() - 0.6d, 0d, -0.1d, 0d);

		level.addParticle(ParticleTypes.SMOKE, entity.x() + 0.3d, entity.y() + 1.5d, entity.z(), 0d, -0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x() - 0.3d, entity.y() + 1.5d, entity.z(), 0d, -0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 1.5d, entity.z() + 0.3d, 0d, -0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 1.5d, entity.z() - 0.3d, 0d, -0.1d, 0d);

		level.addParticle(ParticleTypes.SMOKE, entity.x() + 0.2d, entity.y() + 1.5d, entity.z() + 0.2d, 0d, -0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x() - 0.2d, entity.y() + 1.5d, entity.z() - 0.2d, 0d, -0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x() - 0.2d, entity.y() + 1.5d, entity.z() + 0.2d, 0d, -0.1d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x() + 0.2d, entity.y() + 1.5d, entity.z() - 0.2d, 0d, -0.1d, 0d);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.REVERSED_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 140;
	}
}
