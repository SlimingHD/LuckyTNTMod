package luckytnt.tnteffects;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.CopyBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlastResistanceExplosionRule;
import luckytntlib.util.explosions.rules.FilterOffYExplosionRule;
import luckytntlib.util.explosions.rules.OffsetExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;

public class FloatingIslandEffect extends PrimedTNTEffect {
	
	private final int strength;
	
	public FloatingIslandEffect(int strength) {
		this.strength = strength;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos().add(0d, LuckyTNTConfigValues.ISLAND_HEIGHT.get(), 0d), strength, 101f, new FilterOffYExplosionRule(-strength + 4, strength - 4,
			new OffsetExplosionRule(-LuckyTNTConfigValues.ISLAND_HEIGHT.get(), new FilterBlastResistanceExplosionRule(101f, new CopyBlockExplosionRule()))
		));
		ImprovedExplosion particleExplosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), 8);
		particleExplosion.spawnExplosionParticles();
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.FLOATING_ISLAND.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 120;
	}
}
