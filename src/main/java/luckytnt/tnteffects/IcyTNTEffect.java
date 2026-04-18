package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.CanSurviveExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterCollidableExplosionRule;
import luckytntlib.util.explosions.rules.FilterSurfaceExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class IcyTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		if (!level.isClientSide()) {
			RandomSource random = level.getRandom();
			for (int i = 0; i < 20; i++) {
				Snowball ball = new Snowball(level, entity.x() + random.nextDouble() * 80d - 40d, entity.y() + 30d + random.nextDouble() * 20d - 10d, entity.z() + random.nextDouble() * 80d - 40d);
				ball.setDeltaMovement(random.nextDouble() * 0.8d - 0.4d, -0.1d - random.nextDouble() * 0.4d, random.nextDouble() * 0.8d - 0.4d);
				level.addFreshEntity(ball);
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.legacySphericalExplosion(entity.getLevel(), entity.getPos(), 40, 100f, new FilterAirExplosionRule(
			new StackedExplosionRule(
				FilterBlockExplosionRule.builder().filterForBlocks(WastelandTNTEffect.GRASS).filterForTags(List.of(BlockTags.LEAVES, BlockTags.SAND)).build(
					new BlockExplosionRule(Blocks.BLUE_ICE.defaultBlockState())
				),
				FilterBlockExplosionRule.builder().filterForBlocks(Blocks.WATER, Blocks.BUBBLE_COLUMN, Blocks.SEAGRASS, Blocks.TALL_SEAGRASS, Blocks.KELP, Blocks.KELP_PLANT).build(
					new BlockExplosionRule(Blocks.ICE.defaultBlockState())
				)
			)
		));
		
		ExplosionHelper.legacyCylindricalExplosion(entity.getLevel(), entity.getPos(), 40, 40, 100f, new FilterSurfaceExplosionRule(false, 
			LogicExplosionRule.not(
				new FilterCollidableExplosionRule(new AlwaysExplosionRule()), 
				new CanSurviveExplosionRule(Blocks.SNOW.defaultBlockState())
			)
		));
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ICY_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 180;
	}
}
