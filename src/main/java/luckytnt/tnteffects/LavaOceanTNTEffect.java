package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class LavaOceanTNTEffect extends PrimedTNTEffect{

	private final int radius;
	private final int radiusY;
	
	public LavaOceanTNTEffect(int radius, int radiusY) {
		this.radius = radius;
		this.radiusY = radiusY;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.doCylindricalExplosion(entity.getLevel(), entity.getPos(), radius, radiusY, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(pos.getY() <= entity.getPos().y) {
					if((!state.isFaceSturdy(level, pos, Direction.UP) && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel())) < 100) || state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel())) < 4) {
						state.onBlockExploded(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel()));
						level.setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState());
					}
				}
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0.5f, 0.1f), 1f), entity.x(), entity.y() + 1f, entity.z(), 0, 0, 0);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.LAVA_OCEAN_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
}
