package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LightningStormEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		if(ent.getTNTFuse() < 120) {
			for(int count = 0; count < 10; count++) {
				if(ent.level() instanceof ServerLevel S_Level) {
					double offX = Math.random() * 150D - 75D;
					double offZ = Math.random() * 150D - 75D;
					for(double offY = ent.level().getMaxBuildHeight(); offY > ent.level().getMinBuildHeight(); offY--) {
						if(ent.level().getBlockState(new BlockPos(ent.x() + offX, offY, ent.z() + offZ)).getMaterial() != Material.AIR) {
							Entity lighting = new LightningBolt(EntityType.LIGHTNING_BOLT, ent.level());
							lighting.setPos(ent.x() + offX, offY, ent.z() + offZ);
							ent.level().addFreshEntity(lighting);
							break;
						}
					}
				}
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		List<LivingEntity> ents = ent.level().getEntitiesOfClass(LivingEntity.class, new AABB(ent.x() - 75, ent.y() - 75, ent.z() - 75, ent.x() + 75, ent.y() + 75, ent.z() + 75));
		for(LivingEntity lent : ents) {
			for(double offY = ent.level().getMaxBuildHeight(); offY > ent.level().getMinBuildHeight(); offY--) {
				if(ent.level().getBlockState(new BlockPos(lent.getX(), offY, lent.getZ())).getMaterial() != Material.AIR) {
					Entity lighting = new LightningBolt(EntityType.LIGHTNING_BOLT,  ent.level());
					lighting.setPos(lent.getX(), offY, lent.getZ());
					ent.level().addFreshEntity(lighting);
					
					ImprovedExplosion explosion = new ImprovedExplosion(ent.level(), new Vec3(lent.getX(), offY, lent.getZ()), 3f);
					explosion.doEntityExplosion(1f, true);
					explosion.doBlockExplosion(1f, 1.2f, 1f, 1.2f, false, false);
					
					break;
				}
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.LIGHTNING_STORM.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 160;
	}
}
