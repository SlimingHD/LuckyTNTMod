package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;

public class MiningflatTNTEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion dummyExplosion = new ImprovedExplosion(entity.level(), entity.getPos(), 30);
		ExplosionHelper.doCylindricalExplosion(entity.level(), entity.getPos(), 30, 9, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(pos.getY() >= entity.y()) {
					if(state.getExplosionResistance(level, pos, dummyExplosion) < 100) {
						if(state.is(Tags.Blocks.ORES)) {
							Block.dropResources(state, level, pos);
						}
						state.onBlockExploded(level, pos, dummyExplosion);
						if(pos.getY() - entity.y() == 0) {
							if(Math.random() < 0.05f && Block.canSupportCenter(level, pos.below(), Direction.UP)) {
								level.setBlockAndUpdate(pos, Blocks.TORCH.defaultBlockState());
							}
						}
					}
				}
			}
		});
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.MININGFLAT_TNT.get();
	}
}
