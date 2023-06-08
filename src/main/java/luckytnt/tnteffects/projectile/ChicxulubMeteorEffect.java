package luckytnt.tnteffects.projectile;

import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
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

public class ChicxulubMeteorEffect extends PrimedTNTEffect {
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 80);
		explosion.doEntityExplosion(3, true);
		ExplosionHelper.doSphericalExplosion(entity.getLevel(), entity.getPos(), 60, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(distance <= 55 && state.getExplosionResistance(level, pos, explosion) <= 100) {
					state.onBlockExploded(level, pos, explosion);
				} else if(Math.random() < 0.6f && state.getExplosionResistance(level, pos, explosion) <= 100) {
					state.onBlockExploded(level, pos, explosion);
					if(Math.random() < 0.25f && level.getBlockState(pos.below()).isFaceSturdy(level, pos, Direction.UP)) {
						level.setBlockAndUpdate(pos, BaseFireBlock.getState(level, pos));
					}
				}
			}
		});
		
		for(int count = 0; count < 300; count++) {
			LExplosiveProjectile pompeii = EntityRegistry.POMPEII_PROJECTILE.get().create(entity.getLevel());
			pompeii.setPos(entity.getPos());
			pompeii.setOwner(entity.owner());
			pompeii.setDeltaMovement(Math.random() * 8D - 4D, 3 + Math.random() * 2, Math.random() * 8D - 4D);
			pompeii.setTNTFuse(100000);
			entity.getLevel().addFreshEntity(pompeii);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.EXPLOSION, entity.x(), entity.y() + 1f, entity.z(), 0, 0, 0);
	}

	@Override
	public float getSize(IExplosiveEntity entity) {
		return 4f;
	}

	@Override
	public Block getBlock() {
		return Blocks.MAGMA_BLOCK;
	}
}
