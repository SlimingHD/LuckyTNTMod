package luckytnt.tnteffects.projectile;

import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class MeteorEffect extends PrimedTNTEffect{
	
	private final int strength;
	private final float size;
	
	public MeteorEffect(int strength, float size) {
		this.strength = strength;
		this.size = size;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), strength);
		explosion.doEntityExplosion(3, true);
		ExplosionHelper.doSphericalExplosion(entity.getLevel(), entity.getPos(), strength, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(distance <= (strength - strength / 8) && state.getExplosionResistance(level, pos, explosion) <= 100) {
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
		entity.getLevel().addParticle(ParticleTypes.EXPLOSION, entity.x(), entity.y() + size, entity.z(), 0, 0, 0);
	}

	@Override
	public float getSize(IExplosiveEntity entity) {
		return size;
	}

	@Override
	public Block getBlock() {
		return Blocks.MAGMA_BLOCK;
	}
}
