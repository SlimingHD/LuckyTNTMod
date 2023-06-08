package luckytnt.tnteffects;

import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class PhantomTNTEffect extends PrimedTNTEffect{
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 20);
		explosion.doEntityExplosion(2f, true);
		explosion.doBlockExplosion(1f, 1f, 1f, 1.5f, false, false);
	}
	
	public void explosionTick(IExplosiveEntity entity) {
		if(entity.getTNTFuse() == 5) {
			double offX = Math.random() * 90 - 45;
			double offZ = Math.random() * 90 - 45;
			boolean foundBlock = false;
			for(int offY = 320; offY > -64; offY--) {
	      		BlockPos pos = new BlockPos(Mth.floor(entity.x() + offX), offY, Mth.floor(entity.z() + offZ));
	      		BlockState state = entity.getLevel().getBlockState(pos);
	      		if(state.isCollisionShapeFullBlock(entity.getLevel(), pos) && !state.isAir() && !foundBlock) {
	      			((Entity)entity).setPos(entity.x() + offX, offY + 1, entity.z() + offZ);
	      			foundBlock = true;
	      		}
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {		
	}
	
	@Override
	public Block getBlock() {
		return Blocks.AIR;
	}
}
