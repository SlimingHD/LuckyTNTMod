package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.block.Block;

public class LightningTNTEffect extends PrimedTNTEffect{

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		double x = entity.getPos().x;
		double z = entity.getPos().z;
		if(entity.getTNTFuse() < 120) {
			if(entity.getLevel() instanceof ServerLevel S_Level) {
				double offX = Math.random() * 40 - 20;
				double offZ = Math.random() * 40 - 20;
				for(int offY = 320; offY > -64; offY--) {
					if(!entity.getLevel().getBlockState(new BlockPos(Mth.floor(x + offX), offY, Mth.floor(z + offZ))).isAir()) {
						Entity lighting = new LightningBolt(EntityType.LIGHTNING_BOLT, entity.getLevel());
						lighting.setPos(x + offX, offY, z + offZ);
						entity.getLevel().addFreshEntity(lighting);
						break;
					}
				}
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.LIGHTNING_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 200;
	}
}
