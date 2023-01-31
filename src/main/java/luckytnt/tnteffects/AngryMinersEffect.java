package luckytnt.tnteffects;

import luckytnt.entity.AngryMiner;
import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;

public class AngryMinersEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		for(int count = 0; count <= 8; count++) {
			AngryMiner miner = EntityRegistry.ANGRY_MINER.get().create(entity.level());
			miner.setPos(entity.getPos());
			entity.level().addFreshEntity(miner);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ANGRY_MINERS.get();
	}
}
