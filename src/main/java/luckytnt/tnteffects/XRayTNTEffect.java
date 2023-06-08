package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;

public class XRayTNTEffect extends PrimedTNTEffect{

	private final int radius;
	
	public XRayTNTEffect(int radius) {
		this.radius = radius;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.doSphericalExplosion(entity.getLevel(), entity.getPos(), radius, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(!state.is(Tags.Blocks.ORES) && !state.isAir() && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel())) <= 100) {
					state.onBlockExploded(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel()));
					level.setBlockAndUpdate(pos, Blocks.GLASS.defaultBlockState());
				}
			}
		});
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.XRAY_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
}
