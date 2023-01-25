package luckytnt.tnteffects.projectile;

import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class MeteorEffect extends PrimedTNTEffect{
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.level(), entity.getPos(), 40);
		explosion.doEntityExplosion(3, true);
		ExplosionHelper.doSphericalExplosion(entity.level(), entity.getPos(), 40, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(distance <= 35 && state.getExplosionResistance(level, pos, explosion) <= 100) {
					state.onBlockExploded(level, pos, explosion);
				}
				else if(Math.random() < 0.6f && state.getExplosionResistance(level, pos, explosion) <= 100) {
					state.onBlockExploded(level, pos, explosion);
					if(Math.random() < 0.25f && level.getBlockState(pos.below()).isFaceSturdy(level, pos, Direction.UP)) {
						level.setBlockAndUpdate(pos, BaseFireBlock.getState(level, pos));
					}
				}
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.level().addParticle(ParticleTypes.EXPLOSION, entity.x(), entity.y() + 1f, entity.z(), 0, 0, 0);
	}

	@Override
	public float getSize(IExplosiveEntity entity) {
		return 2f;
	}

	@Override
	public Block getBlock() {
		return Blocks.MAGMA_BLOCK;
	}
}
