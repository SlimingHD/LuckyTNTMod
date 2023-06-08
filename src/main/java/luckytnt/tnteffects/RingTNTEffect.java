package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

public class RingTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		for(double angle = 0; angle < 360; angle += 30D) {
			PrimedLTNT tnt = EntityRegistry.TNT.get().create(ent.getLevel());
			tnt.setTNTFuse(80);
			tnt.setOwner(ent.owner());
			double x = ent.x() + 10 * Math.cos(angle * Math.PI / 180);
			double z = ent.z() + 10 * Math.sin(angle * Math.PI / 180);
			double y = getFirstMotionBlockingBlock(ent.getLevel(), x, z);
			tnt.setPos(x, y + 1D, z);
			ent.getLevel().addFreshEntity(tnt);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.RING_TNT.get();
	}
	
	public static int getFirstMotionBlockingBlock(Level level, double x, double z) {
		if(!level.isClientSide) {
			boolean blockFound = false;
			int y = 0;
			for(int offY = level.getMaxBuildHeight(); offY >= level.getMinBuildHeight(); offY--) {	
				BlockPos pos = new BlockPos(Mth.floor(x), offY, Mth.floor(z));
				BlockPos posUp = new BlockPos(Mth.floor(x), offY + 1, Mth.floor(z));
				BlockState state = level.getBlockState(pos);
				BlockState stateUp = level.getBlockState(posUp);				
				if(!blockFound) {
					if(!state.getCollisionShape(level, pos, CollisionContext.empty()).isEmpty() && stateUp.getCollisionShape(level, posUp, CollisionContext.empty()).isEmpty()) {
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
