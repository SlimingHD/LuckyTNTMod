package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;

public class RouletteTNTEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		if(Math.random() < 0.2f) {
			ImprovedExplosion explosion = new ImprovedExplosion(entity.level(), entity.getPos(), 10f);
			explosion.doEntityExplosion(1.25f, true);
			explosion.doBlockExplosion(1f, 1f, 1f, 1f, false, false);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ROULETTE_TNT.get();
	}
}
