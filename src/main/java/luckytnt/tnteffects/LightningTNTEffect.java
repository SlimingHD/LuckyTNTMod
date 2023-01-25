package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class LightningTNTEffect extends PrimedTNTEffect{

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		double x = entity.getPos().x;
		double z = entity.getPos().z;
		if(entity.getTNTFuse() < 120) {
			if(entity.level() instanceof ServerLevel S_Level) {
				double offX = Math.random() * 20 - Math.random() * 20;
				double offZ = Math.random() * 20 - Math.random() * 20;
				for(double offY = 320; offY > -64; offY--) {
					if(entity.level().getBlockState(new BlockPos(x + offX, offY, z + offZ)).getMaterial() != Material.AIR) {
						Entity lighting = new LightningBolt(EntityType.LIGHTNING_BOLT, entity.level());
						lighting.setPos(x + offX, offY, z + offZ);
						entity.level().addFreshEntity(lighting);
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
