package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytnt.util.Materials;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FieryHellEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ExplosionHelper.doSphericalExplosion(ent.getLevel(), ent.getPos(), 50, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if((Math.round(ent.y()) - pos.getY()) >= 0 && (Math.round(ent.y()) - pos.getY()) <= 20) {
					if((state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) < 100 || state.getBlock() instanceof LiquidBlock || state.isAir()) && !Materials.isStone(state)) {
						state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
						level.setBlock(pos, Blocks.LAVA.defaultBlockState(), 3);
					} 
				}
			}
		});
		
		ExplosionHelper.doSphericalExplosion(ent.getLevel(), ent.getPos(), 90, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				BlockPos posTop = pos.offset(0, 1, 0);
				BlockState stateTop = level.getBlockState(posTop);
				
				if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) < 100 && !state.isAir()) {
					if(Math.random() < 0.9f) {
						state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
						level.setBlock(pos, Blocks.NETHERRACK.defaultBlockState(), 3);
						if(Math.random() < 0.1f) {
							if(!Block.isFaceFull(stateTop.getCollisionShape(level, posTop), Direction.UP)) {
								level.setBlock(posTop, Blocks.FIRE.defaultBlockState(), 3);
							}
						}
					} else if(Math.random() < 0.3f) {
						state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
						level.setBlock(pos, Blocks.LAVA.defaultBlockState(), 3);
					}								
				}
			}
		});
		
		EntityRegistry.TNT_X20_EFFECT.build().serverExplosion(ent);
		
		for(int count = 0; count < 15; count++) {
			Entity ghast = new Ghast(EntityType.GHAST, ent.getLevel());
			ghast.setPos(ent.x() + 20 * Math.random() - 20 * Math.random(), ent.y() + 50 / 2 * Math.random() + 50 / 2, ent.z() + 20 * Math.random() - 20 * Math.random());
			ent.getLevel().addFreshEntity(ghast);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y(), ent.z(), 0D, 0.3D, 0D);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y(), ent.z(), 0.1D, 0.2D, 0D);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y(), ent.z(), -0.1D, 0.2D, 0D);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y(), ent.z(), 0D, 0.2D, 0.1D);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y(), ent.z(), 0D, 0.2D, -0.1D);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y(), ent.z(), 0.2D, 0.2D, 0D);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y(), ent.z(), -0.2D, 0.2D, 0D);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y(), ent.z(), 0D, 0.2D, 0.2D);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y(), ent.z(), 0D, 0.2D, -0.2D);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y(), ent.z(), 0.3D, 0.2D, 0.3D);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y(), ent.z(), -0.3D, 0.2D, -0.3D);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y(), ent.z(), -0.3D, 0.2D, 0.3D);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y(), ent.z(), 0.3D, 0.2D, -0.3D);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.FIERY_HELL.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 240;
	}
}
