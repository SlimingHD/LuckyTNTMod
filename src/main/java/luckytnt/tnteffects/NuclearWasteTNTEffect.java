package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class NuclearWasteTNTEffect extends PrimedTNTEffect{

	private final int radius;
	
	public NuclearWasteTNTEffect(int radius) {
		this.radius = radius;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.doTopBlockExplosion(entity.getLevel(), entity.getPos(), radius, new IForEachBlockExplosionEffect() {
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(!level.getBlockState(pos.above()).isCollisionShapeFullBlock(level, pos.above()) && level.getBlockState(pos.above()).getExplosionResistance(level, pos.above(), null) < 100) {
					level.getBlockState(pos.above()).onBlockExploded(level, pos.above(), ImprovedExplosion.dummyExplosion(entity.getLevel()));
					level.setBlockAndUpdate(pos, BlockRegistry.NUCLEAR_WASTE.get().defaultBlockState());
				}
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.9f, 1f, 0f), 1), entity.x(), entity.y() + 1f, entity.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.NUCLEAR_WASTE_TNT.get();
	}
}
