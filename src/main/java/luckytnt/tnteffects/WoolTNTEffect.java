package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.rules.FilterMapColorExplosionRule;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
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
		ExplosionHelper.legacySphericalExplosion(entity.getLevel(), entity.getPos(), strength, 100f, new FilterAirExplosionRule(
			new StackedExplosionRule(
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.WHITE, new BlockExplosionRule(Blocks.WHITE_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.LIGHT_GRAY, new BlockExplosionRule(Blocks.LIGHT_GRAY_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.GRAY, new BlockExplosionRule(Blocks.GRAY_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.BLACK, new BlockExplosionRule(Blocks.BLACK_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.BROWN, new BlockExplosionRule(Blocks.BROWN_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.RED, new BlockExplosionRule(Blocks.RED_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.ORANGE, new BlockExplosionRule(Blocks.ORANGE_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.YELLOW, new BlockExplosionRule(Blocks.YELLOW_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.LIME, new BlockExplosionRule(Blocks.LIME_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.GREEN, new BlockExplosionRule(Blocks.GREEN_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.CYAN, new BlockExplosionRule(Blocks.CYAN_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.LIGHT_BLUE, new BlockExplosionRule(Blocks.LIGHT_BLUE_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.BLUE, new BlockExplosionRule(Blocks.BLUE_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.PURPLE, new BlockExplosionRule(Blocks.PURPLE_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.MAGENTA, new BlockExplosionRule(Blocks.MAGENTA_WOOL.defaultBlockState())),
				new FilterMapColorExplosionRule(WorldOfWoolsEffect.PINK, new BlockExplosionRule(Blocks.PINK_WOOL.defaultBlockState()))
			)
		));
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.WOOL_TNT.get();
	}
}
