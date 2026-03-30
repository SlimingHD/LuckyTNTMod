package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.rules.FilterCollidableExplosionRule;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.SimpleExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ButterTNTEffect extends PrimedTNTEffect {
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.legacySphericalExplosion(entity.getLevel(), entity.getPos(), 9, 100, new FilterAirExplosionRule(
				new FilterCollidableExplosionRule(new SimpleExplosionRule(Blocks.GOLD_BLOCK.defaultBlockState()))
		));
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.BUTTER_TNT.get();
	}
}
