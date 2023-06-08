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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class VredefortProjectileEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ImprovedExplosion explosion = new ImprovedExplosion(ent.getLevel(), (Entity)ent, ent.getPos(), 140);
		explosion.doEntityExplosion(3f, true);
		
		ExplosionHelper.doSphericalExplosion(ent.getLevel(), ent.getPos(), 120, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				BlockPos posDown = pos.offset(0, -1, 0);
				BlockState stateDown = level.getBlockState(posDown);
				
				if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) < 800 && distance < 120) {
					if(distance >= 115) {
						if(Math.random() < 0.6f) {
							state.onBlockExploded(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
							level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
						}
					}
					else if(distance < 115) {
						state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
						level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);							
					}	
					if(Block.isFaceFull(stateDown.getCollisionShape(level, posDown), Direction.UP)) {
						if(Math.random() < 0.05f && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) < 800) {
							state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
							level.setBlock(pos, Blocks.FIRE.defaultBlockState(), 3);
						}
					}
				}

			}
		});
		
		for(int count = 0; count < 300; count++) {
			LExplosiveProjectile projectile = EntityRegistry.SOLAR_ERUPTION_PROJECTILE.get().create(ent.getLevel());
			projectile.setPos(ent.getPos());
			projectile.setOwner(ent.owner());
			projectile.setDeltaMovement(Math.random() * 4 - Math.random() * 4, 3 + Math.random() * 2, Math.random() * 4 - Math.random() * 4);
			ent.getLevel().addFreshEntity(projectile);
		}
		for(int count = 0; count < 6; count++) {
			LExplosiveProjectile projectile = EntityRegistry.LITTLE_METEOR.get().create(ent.getLevel());
			projectile.setPos(ent.getPos());
			projectile.setOwner(ent.owner());
			projectile.setDeltaMovement(Math.random() * 2 - Math.random() * 2, 3 + Math.random() * 2, Math.random() * 2 - Math.random() * 2);
			ent.getLevel().addFreshEntity(projectile);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(ParticleTypes.EXPLOSION, ent.x(), ent.y(), ent.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return Blocks.MAGMA_BLOCK;
	}
	
	@Override
	public float getSize(IExplosiveEntity ent) {
		return 6f;
	}
}
