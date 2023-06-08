package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class KolaBoreholeTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		int y = ((int)Math.ceil(ent.y()) + 64);
		if(y % 6 != 0) {
			y += 6;
			while(y % 6 != 0) {
				if(y % 6 == 0) {
					break;
				} else {
					y -= 1;
				}
			}
		}
		int intv = y / 6;
		int rad = 8;
		int prevRad = 8;
			
		for(int offY = y - 1; offY >= 0; offY--) {
			for(int offX = -10; offX <= 10; offX++) {
				for(int offZ = -10; offZ <= 10; offZ++) {
					double distance = Math.sqrt(offX * offX + offZ * offZ);
					BlockPos pos = new BlockPos(Mth.floor(ent.x() + offX), offY - 64, Mth.floor(ent.z() + offZ));
					if(distance <= rad && ent.getLevel().getBlockState(pos).getExplosionResistance(ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 200) {
						ent.getLevel().getBlockState(pos).getBlock().onBlockExploded(ent.getLevel().getBlockState(pos), ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
					}
					if(distance > rad && distance <= (rad + 1) && ent.getLevel().getBlockState(pos).getExplosionResistance(ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 200) {
						if(rad != prevRad) {
							if((Block.isShapeFullBlock(ent.getLevel().getBlockState(pos.above().north()).getShape(ent.getLevel(), pos.above().north())) && ent.getLevel().getBlockState(pos.above().north()).canOcclude())
							|| (Block.isShapeFullBlock(ent.getLevel().getBlockState(pos.above().east()).getShape(ent.getLevel(), pos.above().east())) && ent.getLevel().getBlockState(pos.above().east()).canOcclude())
							|| (Block.isShapeFullBlock(ent.getLevel().getBlockState(pos.above().south()).getShape(ent.getLevel(), pos.above().south())) && ent.getLevel().getBlockState(pos.above().south()).canOcclude())
							|| (Block.isShapeFullBlock(ent.getLevel().getBlockState(pos.above().west()).getShape(ent.getLevel(), pos.above().west())) && ent.getLevel().getBlockState(pos.above().west()).canOcclude()))
							{
								ent.getLevel().getBlockState(pos).getBlock().onBlockExploded(ent.getLevel().getBlockState(pos), ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
								if(pos.getY() > (Math.random() * 2 - Math.random() * 2)) {
									ent.getLevel().setBlock(pos, Blocks.STONE.defaultBlockState(), 3);
								} else {
									ent.getLevel().setBlock(pos, Blocks.DEEPSLATE.defaultBlockState(), 3);
								}
							}
						} else if(prevRad == rad) {
							if(Block.isShapeFullBlock(ent.getLevel().getBlockState(pos.above()).getShape(ent.getLevel(), pos)) && ent.getLevel().getBlockState(pos.above()).canOcclude()) {
								Block block = ent.getLevel().getBlockState(pos).getBlock();
								block.onBlockExploded(ent.getLevel().getBlockState(pos), ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
								if(pos.getY() > (Math.random() * 2 - Math.random() * 2)) {
									ent.getLevel().setBlock(pos, Blocks.STONE.defaultBlockState(), 3);
								} else {
									ent.getLevel().setBlock(pos, Blocks.DEEPSLATE.defaultBlockState(), 3);
								}
							}
						}
					}
				}
			}
			prevRad = rad;
			if(offY % intv == 0) {
				rad--;
			}
		}
		for(int i = -59; i >= -65; i--) {
			BlockPos pos = new BlockPos(Mth.floor(ent.x()), i, Mth.floor(ent.z()));
			ent.getLevel().getBlockState(pos).getBlock().onBlockExploded(ent.getLevel().getBlockState(pos), ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.KOLA_BOREHOLE_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 200;
	}
}
