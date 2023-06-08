package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class SinkholeTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		if(ent.getTNTFuse() == 250) {
			ent.getPersistentData().putInt("depth", 20);
		}
		if(ent.getTNTFuse() <= 150) {
			((Entity)ent).setDeltaMovement(Vec3.ZERO);
			((Entity)ent).setNoGravity(true);
		}
		if(ent.getTNTFuse() <= 150 && !ent.getLevel().isClientSide() && ent.getTNTFuse() % 2 == 0) {
			for(int offX = -33; offX <= 33; offX++) {
				for(int offY = -33; offY <= 33; offY++) {
					for(int offZ = -33; offZ <= 33; offZ++) {
						double distance = Math.sqrt(offX * offX + offY * offY + offZ * offZ) + Math.random() * 4D - 2D;
						BlockPos pos = new BlockPos(Mth.floor(ent.x() + offX), Mth.floor(ent.y() + offY + ent.getPersistentData().getInt("depth")), Mth.floor(ent.z() + offZ));
						if(distance <= 30 && ent.getLevel().getBlockState(pos).getExplosionResistance(ent.getLevel(), toBlockPos(ent.getPos()), ImprovedExplosion.dummyExplosion(ent.getLevel())) < 200) {
							ent.getLevel().getBlockState(pos).getBlock().onBlockExploded(ent.getLevel().getBlockState(pos), ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
						}
					}
				}
			}
			
			ent.getPersistentData().putInt("depth", ent.getPersistentData().getInt("depth") - 1);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.SINKHOLE_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 250;
	}
}
