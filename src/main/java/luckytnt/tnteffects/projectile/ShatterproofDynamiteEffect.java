package luckytnt.tnteffects.projectile;

import org.joml.Vector3f;

import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ShatterproofDynamiteEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.level(), entity.getPos(), 5);
		explosion.doBlockExplosion(1f, 1f, 0.7f, 1f, false, new IForEachBlockExplosionEffect() {	
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				state.onBlockExploded(level, pos, explosion);
				if(state.isCollisionShapeFullBlock(level, pos)) {
					level.setBlockAndUpdate(pos, Blocks.OBSIDIAN.defaultBlockState());
				}
				else {
					level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
				}
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.level().addParticle(new DustParticleOptions(new Vector3f(0.1f, 0.1f, 0.1f), 1), entity.x(), entity.y(), entity.z(), 0, 0, 0);
		entity.level().addParticle(new DustParticleOptions(new Vector3f(0.5f, 0.3f, 0.8f), 1), entity.x(), entity.y(), entity.z(), 0, 0, 0);
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.SHATTERPROOF_DYNAMITE.get();
	}
}
