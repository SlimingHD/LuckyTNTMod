package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

public class PrismTNTEffect extends PrimedTNTEffect {
	
	public final int size;
	
	public PrismTNTEffect(int size) {
		this.size = size;
	}

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		BlockPos pos = toBlockPos(ent.getPos()).offset(-1 * (size / 2) + 1, 0, -1 * (size / 2) + 1);
		
		for(int offY = (size / 2); offY > (-1 * (size / 2) - 1); offY--) {
			int tri = size;
			for(int offX = 0; offX < size; offX++) {
				for(int offZ = 0; offZ < tri; offZ++) {
					BlockPos pos1 = new BlockPos(pos.getX() + offX, pos.getY() + offY, pos.getZ() + offZ);
					if(ent.getLevel().getBlockState(pos1).getExplosionResistance(ent.getLevel(), pos1, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 100) {
						ent.getLevel().getBlockState(pos1).onBlockExploded(ent.getLevel(), pos1, ImprovedExplosion.dummyExplosion(ent.getLevel()));
					}
				}
				tri--;
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.PRISM_TNT.get();
	}
}
