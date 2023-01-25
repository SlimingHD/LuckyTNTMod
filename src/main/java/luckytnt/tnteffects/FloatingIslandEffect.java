package luckytnt.tnteffects;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FloatingIslandEffect extends PrimedTNTEffect{
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion dummyExplosion = new ImprovedExplosion(null, entity.getPos(), 20);
		ExplosionHelper.doSphericalExplosion(entity.level(), entity.getPos(), 20, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(distance <= 20 && Math.abs(pos.getY() - entity.getPos().y) <= 15 && state.getExplosionResistance(level, pos, dummyExplosion) <= 100) {
					if(level.getBlockState(pos.offset(0, LuckyTNTConfigValues.ISLAND_HEIGHT.get(), 0)).getExplosionResistance(level, /*This pos is irrelevant*/ pos, dummyExplosion) <= 100) {
						level.setBlockAndUpdate(pos.offset(0, LuckyTNTConfigValues.ISLAND_HEIGHT.get(), 0), state);
					}
				}
			}
		});
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.FLOATING_ISLAND.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 120;
	}
}
