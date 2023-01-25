package luckytnt.tnteffects;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class DiggingTNTEffect extends PrimedTNTEffect{

	private static final float VEC_LENGTH = 60f;
	private static final float MAX_RESISTANCE = 100f;
	private final ExplosionDamageCalculator damageCalculator = new ExplosionDamageCalculator();

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Set<BlockPos> blocks = new HashSet<>();
		ImprovedExplosion dummyExplosion = new ImprovedExplosion(entity.level(), entity.getPos(), 4);
		for(float y = 0; y < VEC_LENGTH; y += 0.3f) {
			BlockPos pos = new BlockPos(entity.getPos()).offset(0, -y, 0);
			BlockState blockState = entity.level().getBlockState(pos);
			FluidState fluidState = entity.level().getFluidState(pos);
			Optional<Float> explosionResistance = damageCalculator.getBlockExplosionResistance(dummyExplosion, entity.level(), pos, blockState, fluidState);
			if(explosionResistance.isPresent() && explosionResistance.get() > MAX_RESISTANCE) {
				y += 100f;
			}
			else {
				blocks.add(pos);
			}
		}
		for(BlockPos pos : blocks) {
			entity.level().getBlockState(pos).getBlock().onBlockExploded(entity.level().getBlockState(pos), entity.level(), pos, dummyExplosion);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.level().addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 1.4f, entity.z(), 0, -0.1f, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.DIGGING_TNT.get();
	}
}
