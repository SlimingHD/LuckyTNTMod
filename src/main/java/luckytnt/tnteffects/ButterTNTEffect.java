package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ButterTNTEffect extends PrimedTNTEffect{
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion dummyExplosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 9);
		ExplosionHelper.doSphericalExplosion(entity.getLevel(), entity.getPos(), 9, new IForEachBlockExplosionEffect() {
		
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getExplosionResistance(level, pos, dummyExplosion) < 100 && !state.isAir()) {
					level.setBlockAndUpdate(pos, Blocks.GOLD_BLOCK.defaultBlockState());
				}
			}
		});
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.BUTTER_TNT.get();
	}
}
