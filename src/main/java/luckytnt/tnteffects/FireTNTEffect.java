package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FireTNTEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion dummyExplosion = new ImprovedExplosion(entity.level(), entity.getPos(), 10);
		ExplosionHelper.doTopBlockExplosionForAll(entity.level(), entity.getPos(), 15, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				state.onBlockExploded(level, pos, dummyExplosion);
				level.setBlockAndUpdate(pos, BaseFireBlock.getState(level, pos));
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.level().addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5f, entity.z(), 0, 0.2f, 0);
		entity.level().addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5f, entity.z(), 0.05f, 0.15f, 0.05f);
		entity.level().addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5f, entity.z(), -0.05f, 0.15f, -0.05f);
		entity.level().addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5f, entity.z(), -0.05f, 0.15f, 0.05f);
		entity.level().addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5f, entity.z(), 0.05f, 0.15f, -0.05f);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.FIRE_TNT.get();
	}
}
