package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.rules.FilterCollidableExplosionRule;
import luckytnt.rules.FilterLiquidExplosionRule;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.SimpleExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class FreezeTNTEffect extends PrimedTNTEffect {

	private final int strength;
	
	public FreezeTNTEffect(int strength) {
		this.strength = strength;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), strength, 101f, new StackedExplosionRule(
				new FilterCollidableExplosionRule(new SimpleExplosionRule(Blocks.ICE.defaultBlockState())),
				new FilterLiquidExplosionRule(new SimpleExplosionRule(Blocks.ICE.defaultBlockState()))
		));
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.FREEZE_TNT.get();
	}
}
