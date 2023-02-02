package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EffectRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.IForEachEntityExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

public class NuclearTNTEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.level(), (Entity) entity, entity.getPos(), 50f);
		explosion.doEntityExplosion(4f, true);
		explosion.doEntityExplosion(new IForEachEntityExplosionEffect() {		
			@Override
			public void doEntityExplosion(Entity entity, double distance) {
				if(entity instanceof LivingEntity living) {
					living.addEffect(new MobEffectInstance(EffectRegistry.CONTAMINATED_EFFECT.get(), 2400));
				}
			}
		});
		explosion.doBlockExplosion(1f, 1.3f, 1f, 1f, false, new IForEachBlockExplosionEffect() {			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				state.onBlockExploded(level, pos, explosion);
				if(Math.random() < 0.33f && level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP) && level.getBlockState(pos).isAir()) {
					level.setBlockAndUpdate(pos, BlockRegistry.NUCLEAR_WASTE.get().defaultBlockState());
				}
			}
		});
		ExplosionHelper.doSphericalExplosion(entity.level(), entity.getPos(), 150, new IForEachBlockExplosionEffect() {		
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getBlock() instanceof BushBlock || state.getBlock() instanceof LeavesBlock) {
					state.onBlockExploded(level, pos, explosion);
				}
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.level().addParticle(new DustParticleOptions(new Vector3f(0.9f, 1f, 0f), 1), entity.x(), entity.y() + 1f, entity.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.NUCLEAR_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 200;
	}
}
