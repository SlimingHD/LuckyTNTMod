package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ParticlePhysicsTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ImprovedExplosion explosion = new ImprovedExplosion(ent.getLevel(), (Entity)ent, ent.getPos(), 25);
		explosion.doBlockExplosion(1f, 1f, 1f, 1.5f, false, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				FallingBlockEntity block = FallingBlockEntity.fall(level, pos, state);
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				block.setDeltaMovement(Math.random() * 2 - Math.random() * 2, 1f + Math.random() * 3, Math.random() * 2 - Math.random() * 2);
				level.addFreshEntity(block);
			}
		});
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.PARTICLE_PHYSICS_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 160;
	}
}
