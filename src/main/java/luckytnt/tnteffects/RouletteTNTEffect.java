package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;

public class RouletteTNTEffect extends PrimedTNTEffect{

	private final int strength;
	
	public RouletteTNTEffect(int strength) {
		this.strength = strength;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		if(Math.random() < 0.2f) {
			ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), strength);
			explosion.doEntityExplosion(1.25f, true);
			explosion.doImprovedBlockExplosion(1f, 1f, false, false, RandomSource.create());
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ROULETTE_TNT.get();
	}
}
