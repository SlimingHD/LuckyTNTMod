package luckytnt.tnteffects;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
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
		ImprovedExplosion dummyExplosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 4);
		for(float y = 0; y < VEC_LENGTH; y += 0.3f) {
			BlockPos pos = toBlockPos(entity.getPos().add(0, -y, 0));
			BlockState blockState = entity.getLevel().getBlockState(pos);
			FluidState fluidState = entity.getLevel().getFluidState(pos);
			Optional<Float> explosionResistance = damageCalculator.getBlockExplosionResistance(dummyExplosion, entity.getLevel(), pos, blockState, fluidState);
			if(explosionResistance.isPresent() && explosionResistance.get() > MAX_RESISTANCE) {
				y += 100f;
			}
			else {
				blocks.add(pos);
			}
		}
		for(BlockPos pos : blocks) {
			entity.getLevel().getBlockState(pos).getBlock().onBlockExploded(entity.getLevel().getBlockState(pos), entity.getLevel(), pos, dummyExplosion);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 1.4f, entity.z(), 0, -0.1f, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.DIGGING_TNT.get();
	}
}
