package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SnowTNTEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion dummyExplosion = new ImprovedExplosion(entity.level(), entity.getPos(), 10);
		ExplosionHelper.doTopBlockExplosionForAll(entity.level(), entity.getPos(), 15, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				state.onBlockExploded(level, pos, dummyExplosion);
				level.setBlockAndUpdate(pos, Blocks.SNOW.defaultBlockState());
			}
		});
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.SNOW_TNT.get();
	}
}
