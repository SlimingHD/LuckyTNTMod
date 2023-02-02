package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class WitheringTNTEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.level(), entity.getPos(), 20);
		explosion.doEntityExplosion(2f, true);
		explosion.doBlockExplosion(1f, 1f, 1f, 1.5f, false, false);
		explosion.doBlockExplosion(new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.isCollisionShapeFullBlock(level, pos)) {
					state.onBlockExploded(level, pos, explosion);
					level.setBlockAndUpdate(pos, Math.random() < 0.5f ? Blocks.SOUL_SAND.defaultBlockState() : Blocks.SOUL_SOIL.defaultBlockState());
					if(!level.getBlockState(pos.above()).isAir() && !level.getBlockState(pos.above().above()).isAir()) {
						if(Math.random() < 0.1f) {
							WitherSkeleton skeleton = new WitherSkeleton(EntityType.WITHER_SKELETON, level);
							skeleton.setPos(pos.getX() + 0.5f, pos.getY() + 1f, pos.getZ() + 0.5f);
							level.addFreshEntity(skeleton);
						}
					}
				}
			}
		});
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.WITHERING_TNT.get();
	}
}
