package luckytnt.tnteffects;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FloatingIslandEffect extends PrimedTNTEffect{
	
	private final int strength;
	
	public FloatingIslandEffect(int strength) {
		this.strength = strength;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.doSphericalExplosion(entity.getLevel(), entity.getPos(), strength, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(distance <= 20 && Math.abs(pos.getY() - entity.getPos().y) <= 15 && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel())) <= 100) {
					if(level.getBlockState(pos.offset(0, LuckyTNTConfigValues.ISLAND_HEIGHT.get(), 0)).getExplosionResistance(level, pos.offset(0, LuckyTNTConfigValues.ISLAND_HEIGHT.get(), 0), ImprovedExplosion.dummyExplosion(entity.getLevel())) <= 100) {
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
