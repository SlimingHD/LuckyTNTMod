package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class CompactTNTEffect extends PrimedTNTEffect{

	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.level(), entity.getPos(), 10);
		explosion.doBlockExplosion(new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(Math.random() < 0.1f && !state.isAir() && state.getExplosionResistance(level, pos, explosion) < 100) {
					state.onBlockExploded(level, pos, explosion);
					level.setBlockAndUpdate(pos, BlockRegistry.TNT.get().defaultBlockState());
				}
				if(Math.random() < 0.25f && level.getBlockState(pos.above()).isAir()) {
					level.setBlockAndUpdate(pos.above(), BaseFireBlock.getState(level, pos.above()));
				}
			}
		});
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.COMPACT_TNT.get();
	}
}
