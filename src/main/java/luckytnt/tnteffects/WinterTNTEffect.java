package luckytnt.tnteffects;

import java.util.List;

import org.joml.Vector3f;

import luckytnt.entity.SnowySnowball;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.CanSurviveExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterCollidableExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class WinterTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), 150, 200f, 
			FilterBlockExplosionRule.builder().filterForBlocks(Blocks.BUBBLE_COLUMN, Blocks.WATER, Blocks.SEAGRASS, Blocks.TALL_SEAGRASS, Blocks.KELP, Blocks.KELP_PLANT).filterForTags(List.of(BlockTags.CORALS)).build(
				new BlockExplosionRule(Blocks.ICE.defaultBlockState())
			)
		);
		
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), 150, 200f, LogicExplosionRule.not(
			new FilterCollidableExplosionRule(new AlwaysExplosionRule()), 
			new CanSurviveExplosionRule(Blocks.SNOW.defaultBlockState())
		));
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		RandomSource random = entity.getLevel().getRandom();
		for (int i = 0; i < 50; i++) {
			SnowySnowball ball = new SnowySnowball(entity.getLevel(), entity.x() + random.nextDouble() * 100d - random.nextDouble() * 100d, entity.y() + 30d, entity.z() + random.nextDouble() * 100d - random.nextDouble() * 100d);
			ball.setDeltaMovement(random.nextDouble() * 0.2d - 0.1d, -0.1d - random.nextDouble() * 0.4d, random.nextDouble() * 0.2d - 0.1D);
			entity.getLevel().addFreshEntity(ball);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f), 1f), entity.x(), entity.y() + 1D, entity.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.WINTER_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 200;
	}
}
