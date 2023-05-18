package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

public class RingTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		for(double angle = 0; angle < 360; angle += 30D) {
			PrimedLTNT tnt = EntityRegistry.TNT.get().create(ent.level());
			tnt.setTNTFuse(80);
			tnt.setOwner(ent.owner());
			double x = ent.x() + 10 * Math.cos(angle * Math.PI / 180);
			double z = ent.z() + 10 * Math.sin(angle * Math.PI / 180);
			double y = getFirstMotionBlockingBlock(ent.level(), x, z);
			tnt.setPos(x, y + 1D, z);
			ent.level().addFreshEntity(tnt);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.RING_TNT.get();
	}
	
	@SuppressWarnings("deprecation")
	public static int getFirstMotionBlockingBlock(Level level, double x, double z) {
		if(!level.isClientSide) {
			boolean blockFound = false;
			int y = 0;
			for(int offY = level.getMaxBuildHeight(); offY >= level.getMinBuildHeight(); offY--) {	
				BlockPos pos = new BlockPos(x, offY, z);
				BlockPos posUp = new BlockPos(x, offY + 1, z);
				BlockState state = level.getBlockState(pos);
				BlockState stateUp = level.getBlockState(posUp);				
				if(!blockFound) {
					if(!state.getBlock().getCollisionShape(state, level, pos, CollisionContext.empty()).isEmpty() && stateUp.getBlock().getCollisionShape(stateUp, level, posUp, CollisionContext.empty()).isEmpty()) {
						blockFound = true;
						y = offY;
					}	
				}
			}
			return y;
		} else {
			return 0;
		}
	}
}