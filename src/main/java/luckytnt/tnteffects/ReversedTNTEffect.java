package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.rules.MirrorExplosionRule;
import luckytnt.rules.UpsideDownBlockExplosionRule;
import luckytnt.util.AdvancementHelper;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.FilterBlastResistanceExplosionRule;
import luckytntlib.util.explosions.rules.OffsetExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class ReversedTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		int islandHeight = 80;
		ExplosionHelper.legacySphericalExplosion(entity.getLevel(), entity.getPos().add(0, islandHeight, 0), 30, 200f, new MirrorExplosionRule(List.of(Axis.Y), 
			new OffsetExplosionRule(-islandHeight, 
				new FilterBlastResistanceExplosionRule(200f, new UpsideDownBlockExplosionRule())
			)
		));
		AdvancementHelper.grantAdvancementToOwnerOrNearby(entity, AdvancementKeys.WHAT_GOES_UP);
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
