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

	private final int strength;
	
	public NuclearTNTEffect(int strength) {
		this.strength = strength;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity) entity, entity.getPos(), strength);
		explosion.doEntityExplosion(strength / 10f, true);
		explosion.doEntityExplosion(new IForEachEntityExplosionEffect() {		
			@Override
			public void doEntityExplosion(Entity entity, double distance) {
				if(entity instanceof LivingEntity living) {
					living.addEffect(new MobEffectInstance(EffectRegistry.CONTAMINATED_EFFECT.get(), 48 * strength));
				}
			}
		});
		explosion.doBlockExplosion(1f, 1.3f, 1f, 1f, false, false);
		ExplosionHelper.doSphericalExplosion(entity.getLevel(), entity.getPos(), strength * 3, new IForEachBlockExplosionEffect() {		
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getBlock() instanceof BushBlock || state.getBlock() instanceof LeavesBlock) {
					state.onBlockExploded(level, pos, explosion);
				}
			}
		});
		explosion.doBlockExplosion(new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				BlockState stateAbove = level.getBlockState(pos.above());
				if(stateAbove.isAir() && !state.isAir() && Math.random() < 0.33f) {
					level.setBlockAndUpdate(pos.above(), BlockRegistry.NUCLEAR_WASTE.get().defaultBlockState());
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
		return BlockRegistry.NUCLEAR_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 200;
	}
}
