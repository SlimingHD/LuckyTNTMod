package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class OceanTNTEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion dummyExplosion = new ImprovedExplosion(entity.level(), (Entity) entity, entity.getPos().x, entity.getPos().y, entity.getPos().z, 15);
		ExplosionHelper.doCylindricalExplosion(entity.level(), entity.getPos(), 30, 20, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(pos.getY() <= entity.getPos().y) {
					if((!state.isFaceSturdy(level, pos, Direction.UP) && state.getExplosionResistance(level, pos, dummyExplosion) < 100) || state.getExplosionResistance(level, pos, dummyExplosion) < 4) {
						state.onBlockExploded(level, pos, dummyExplosion);
						level.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
					}
				}
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.level().addParticle(ParticleTypes.SPLASH, entity.x(), entity.y() + 1.4f, entity.z(), 0, 0, 0);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.OCEAN_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
}
