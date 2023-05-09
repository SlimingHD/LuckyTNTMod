package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

public class PrismTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		BlockPos pos = new BlockPos(ent.getPos()).offset(-4, 0, -4);
		
		for(int offY = 5; offY > -6; offY--) {
			int tri = 10;
			for(int offX = 0; offX < 10; offX++) {
				for(int offZ = 0; offZ < tri; offZ++) {
					BlockPos pos1 = new BlockPos(pos.getX() + offX, pos.getY() + offY, pos.getZ() + offZ);
					if(ent.level().getBlockState(pos1).getExplosionResistance(ent.level(), pos1, ImprovedExplosion.dummyExplosion()) <= 100) {
						ent.level().getBlockState(pos1).onBlockExploded(ent.level(), pos1, ImprovedExplosion.dummyExplosion());
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
