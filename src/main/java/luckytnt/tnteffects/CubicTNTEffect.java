package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;

public class CubicTNTEffect extends PrimedTNTEffect {
	
	private final int strength;
	private final float maxResistance;
	
	public CubicTNTEffect(int strength, float maxResistance) {
		this.strength = strength;
		this.maxResistance = maxResistance;
	}
	
	public CubicTNTEffect(int strength) {
		this(strength, 99f);
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.createCubicalCrater(entity.getLevel(), entity.getPos(), strength, maxResistance);
		ImprovedExplosion particleExplosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), strength);
		particleExplosion.spawnExplosionParticles();
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.CUBIC_TNT.get();
	}
}
