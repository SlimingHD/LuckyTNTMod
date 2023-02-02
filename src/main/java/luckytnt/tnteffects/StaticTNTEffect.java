package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;

public class StaticTNTEffect extends PrimedTNTEffect{

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		((Entity)entity).setPos(((Entity)entity).xo, ((Entity)entity).yo, ((Entity)entity).zo);
		((Entity)entity).setDeltaMovement(0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.STATIC_TNT.get();
	}
}