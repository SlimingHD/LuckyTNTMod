package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.rules.FilterMapColorExplosionRule;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.SimpleExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class WoolTNTEffect extends PrimedTNTEffect {

	private final int strength;
	
	public WoolTNTEffect(int strength) {
		this.strength = strength;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.legacySphericalExplosion(entity.getLevel(), entity.getPos(), strength, 100, new FilterAirExplosionRule(
			new StackedExplosionRule(
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.WHITE, new SimpleExplosionRule(Blocks.WHITE_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.LIGHT_GRAY, new SimpleExplosionRule(Blocks.LIGHT_GRAY_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.GRAY, new SimpleExplosionRule(Blocks.GRAY_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.BLACK, new SimpleExplosionRule(Blocks.BLACK_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.BROWN, new SimpleExplosionRule(Blocks.BROWN_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.RED, new SimpleExplosionRule(Blocks.RED_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.ORANGE, new SimpleExplosionRule(Blocks.ORANGE_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.YELLOW, new SimpleExplosionRule(Blocks.YELLOW_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.LIME, new SimpleExplosionRule(Blocks.LIME_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.GREEN, new SimpleExplosionRule(Blocks.GREEN_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.CYAN, new SimpleExplosionRule(Blocks.CYAN_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.LIGHT_BLUE, new SimpleExplosionRule(Blocks.LIGHT_BLUE_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.BLUE, new SimpleExplosionRule(Blocks.BLUE_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.PURPLE, new SimpleExplosionRule(Blocks.PURPLE_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.MAGENTA, new SimpleExplosionRule(Blocks.MAGENTA_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.PINK, new SimpleExplosionRule(Blocks.PINK_WOOL.defaultBlockState()))
			)
		));
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.WOOL_TNT.get();
	}
}
