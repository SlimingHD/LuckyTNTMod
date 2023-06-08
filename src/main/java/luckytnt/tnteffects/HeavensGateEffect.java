package luckytnt.tnteffects;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class HeavensGateEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.doSphericalExplosion(entity.getLevel(), entity.getPos(), 30, new IForEachBlockExplosionEffect() {
		
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				BlockPos posTop = pos.offset(0, LuckyTNTConfigValues.ISLAND_HEIGHT.get(), 0);
				BlockState stateTop = level.getBlockState(posTop);
				
				if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel())) < 200 && stateTop.isAir() && !state.isAir() && Math.abs(entity.y() - pos.getY()) <= 20) {
					level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
					level.setBlock(posTop, state, 3);
				}
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x() + 0.7f, ent.y(), ent.z(), 0, 0.1f, 0);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x() - 0.7f, ent.y(), ent.z(), 0, 0.1f, 0);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y(), ent.z() + 0.7f, 0, 0.1f, 0);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y(), ent.z() - 0.7f, 0, 0.1f, 0);
		
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x() + 0.6f, ent.y(), ent.z() + 0.6f, 0, 0.1f, 0);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x() - 0.6f, ent.y(), ent.z() - 0.6f, 0, 0.1f, 0);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x() - 0.6f, ent.y(), ent.z() + 0.6f, 0, 0.1f, 0);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x() + 0.6f, ent.y(), ent.z() - 0.6f, 0, 0.1f, 0);
		
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x() + 0.3f, ent.y() + 0.5f, ent.z(), 0, 0.1f, 0);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x() - 0.3f, ent.y() + 0.5f, ent.z(), 0, 0.1f, 0);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 0.5f, ent.z() + 0.3f, 0, 0.1f, 0);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 0.5f, ent.z() - 0.3f, 0, 0.1f, 0);
		
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x() + 0.2f, ent.y() + 0.5f, ent.z() + 0.2f, 0, 0.1f, 0);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x() - 0.2f, ent.y() + 0.5f, ent.z() - 0.2f, 0, 0.1f, 0);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x() - 0.2f, ent.y() + 0.5f, ent.z() + 0.2f, 0, 0.1f, 0);
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x() + 0.2f, ent.y() + 0.5f, ent.z() - 0.2f, 0, 0.1f, 0);
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
