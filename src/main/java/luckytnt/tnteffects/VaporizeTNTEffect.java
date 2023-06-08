package luckytnt.tnteffects;

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
import net.minecraft.world.level.block.CoralFanBlock;
import net.minecraft.world.level.block.KelpPlantBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.BlockState;

public class VaporizeTNTEffect extends PrimedTNTEffect{

	private final int strength;
	
	public VaporizeTNTEffect(int strength) {
		this.strength = strength;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.doSphericalExplosion(entity.getLevel(), entity.getPos(), strength, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getBlock() instanceof LiquidBlock  || state.getBlock() instanceof KelpPlantBlock || state.getBlock() instanceof SeagrassBlock || state.getBlock() instanceof TallSeagrassBlock || state.getBlock() instanceof CoralFanBlock) {
					state.onBlockExploded(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel()));
				}
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.SMOKE, entity.x() + 1.3f, entity.y() + 0.5f, entity.z(), -0.1f, 0, 0);
		entity.getLevel().addParticle(ParticleTypes.SMOKE, entity.x() - 1.3f, entity.y() + 0.5f, entity.z(), 0.1f, 0, 0);
		entity.getLevel().addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 0.5f, entity.z() + 1.3f, 0, 0, -0.1f);
		entity.getLevel().addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 0.5f, entity.z() - 1.3f, 0, 0, 0.1f);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.VAPORIZE_TNT.get();
	}
}
