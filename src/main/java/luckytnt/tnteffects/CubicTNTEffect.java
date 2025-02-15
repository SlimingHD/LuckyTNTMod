package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;

public class CubicTNTEffect extends PrimedTNTEffect{
	private final int strength;
	
	public CubicTNTEffect(int strength) {
		this.strength = strength;
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.createCubicalCrater(entity.getLevel(), entity.getPos(), strength, 100);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.CUBIC_TNT.get();
	}
}
