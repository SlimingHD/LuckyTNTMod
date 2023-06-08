package luckytnt.tnteffects;

import java.util.Random;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;

public class RandomTNTEffect extends PrimedTNTEffect{

	private final int maxStrength;
	
	public RandomTNTEffect(int maxStrength) {
		this.maxStrength = maxStrength;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), new Random().nextInt(maxStrength + 1));
		explosion.doEntityExplosion(1.25f, true);
		explosion.doBlockExplosion();
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.RANDOM_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 120;
	}
}
