package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
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
					BlockPos pos = new BlockPos(ent.x() + offX, offY - 64, ent.z() + offZ);
					if(distance <= rad && ent.level().getBlockState(pos).getExplosionResistance(ent.level(), pos, ImprovedExplosion.dummyExplosion(ent.level())) <= 200) {
						ent.level().getBlockState(pos).getBlock().onBlockExploded(ent.level().getBlockState(pos), ent.level(), pos, ImprovedExplosion.dummyExplosion(ent.level()));
					}
					if(distance > rad && distance <= (rad + 1) && ent.level().getBlockState(pos).getExplosionResistance(ent.level(), pos, ImprovedExplosion.dummyExplosion(ent.level())) <= 200) {
						if(rad != prevRad) {
							if((Block.isShapeFullBlock(ent.level().getBlockState(pos.above().north()).getShape(ent.level(), pos.above().north())) && ent.level().getBlockState(pos.above().north()).canOcclude())
							|| (Block.isShapeFullBlock(ent.level().getBlockState(pos.above().east()).getShape(ent.level(), pos.above().east())) && ent.level().getBlockState(pos.above().east()).canOcclude())
							|| (Block.isShapeFullBlock(ent.level().getBlockState(pos.above().south()).getShape(ent.level(), pos.above().south())) && ent.level().getBlockState(pos.above().south()).canOcclude())
							|| (Block.isShapeFullBlock(ent.level().getBlockState(pos.above().west()).getShape(ent.level(), pos.above().west())) && ent.level().getBlockState(pos.above().west()).canOcclude()))
							{
								ent.level().getBlockState(pos).getBlock().onBlockExploded(ent.level().getBlockState(pos), ent.level(), pos, ImprovedExplosion.dummyExplosion(ent.level()));
								if(pos.getY() > (Math.random() * 2 - Math.random() * 2)) {
									ent.level().setBlock(pos, Blocks.STONE.defaultBlockState(), 3);
								} else {
									ent.level().setBlock(pos, Blocks.DEEPSLATE.defaultBlockState(), 3);
								}
							}
						} else if(prevRad == rad) {
							if(Block.isShapeFullBlock(ent.level().getBlockState(pos.above()).getShape(ent.level(), pos)) && ent.level().getBlockState(pos.above()).canOcclude()) {
								Block block = ent.level().getBlockState(pos).getBlock();
								block.onBlockExploded(ent.level().getBlockState(pos), ent.level(), pos, ImprovedExplosion.dummyExplosion(ent.level()));
								if(pos.getY() > (Math.random() * 2 - Math.random() * 2)) {
									ent.level().setBlock(pos, Blocks.STONE.defaultBlockState(), 3);
								} else {
									ent.level().setBlock(pos, Blocks.DEEPSLATE.defaultBlockState(), 3);
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
			BlockPos pos = new BlockPos(ent.x(), i, ent.z());
			ent.level().getBlockState(pos).getBlock().onBlockExploded(ent.level().getBlockState(pos), ent.level(), pos, ImprovedExplosion.dummyExplosion(ent.level()));
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
