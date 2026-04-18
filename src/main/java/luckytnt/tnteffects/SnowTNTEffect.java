package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.CanSurviveExplosionRule;
import luckytntlib.util.explosions.rules.FilterCollidableExplosionRule;
import luckytntlib.util.explosions.rules.FilterSurfaceExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class SnowTNTEffect extends PrimedTNTEffect {

	private final int strength;

	public SnowTNTEffect(int strength) {
		this.strength = strength;
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.legacySphericalExplosion(entity.getLevel(), entity.getPos(), strength, 99f, new FilterSurfaceExplosionRule(false, 
			LogicExplosionRule.not(
				new FilterCollidableExplosionRule(new AlwaysExplosionRule()), 
				new CanSurviveExplosionRule(Blocks.SNOW.defaultBlockState())
			)
		));
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.SNOW_TNT.get();
	}
}
