package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;

public class StaticTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (entity instanceof Entity ent) {
			ent.setPos(ent.xo, ent.yo, ent.zo);
			ent.setDeltaMovement(0, 0, 0);
		}
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.STATIC_TNT.get();
	}
}