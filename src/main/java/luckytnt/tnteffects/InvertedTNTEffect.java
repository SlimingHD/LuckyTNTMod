package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class InvertedTNTEffect extends PrimedTNTEffect{

	@SuppressWarnings("deprecation")
	public void serverExplosion(IExplosiveEntity entity) {
		for(float angleY = 0; angleY <= 360; angleY += 11.25f) {
			for(float angleX = 0; angleX <= 360; angleX += 11.25f) {
				double offX = 30 * ((Entity)entity).getViewVector(1).x + Math.random() * 4f - Math.random() * 4f;
				double offY = 30 * ((Entity)entity).getViewVector(1).y + Math.random() * 4f - Math.random() * 4f;
				double offZ = 30 * ((Entity)entity).getViewVector(1).z + Math.random() * 4f - Math.random() * 4f;
				double magnitude = Math.sqrt(offX * offX + offY * offY + offZ * offZ) + 0.1f;
				for(int j = 1; j < magnitude; j++) {
					BlockPos pos = new BlockPos(entity.x() + offX * j / magnitude, entity.y() + offY * j / magnitude, entity.z() + offZ * j / magnitude);
					if(entity.level().getBlockState(pos).getMaterial() == Material.AIR || entity.level().getBlockState(pos).getMaterial() == Material.PLANT || entity.level().getBlockState(pos).getMaterial() == Material.REPLACEABLE_PLANT || entity.level().getBlockState(pos).getMaterial() == Material.REPLACEABLE_FIREPROOF_PLANT) {
						if(entity.level().getBlockState(new BlockPos(pos.getX(), pos.getY() - 30 * 2, pos.getZ())).getBlock().getExplosionResistance() < 100) {
							entity.level().setBlock(pos, entity.level().getBlockState(new BlockPos(pos.getX(), pos.getY() - 30 * 2, pos.getZ())), 3);
						}
					}
				}
				((Entity)entity).setXRot(angleX);
			}
			((Entity)entity).setYRot(angleY);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.INVERTED_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 120;
	}
}
