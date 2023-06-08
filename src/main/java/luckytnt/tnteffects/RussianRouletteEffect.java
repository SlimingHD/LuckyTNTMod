package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;

public class RussianRouletteEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		if(Math.random() < 0.2f) {
			ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 80);
			explosion.doEntityExplosion(5f, true);
			explosion.doBlockExplosion(1f, 1.3f, 1f, 1f, false, false);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.RUSSIAN_ROULETTE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 160;
	}
}
