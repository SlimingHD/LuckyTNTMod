package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytnt.rules.DrainAreaExplosionRule;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.CanSurviveExplosionRule;
import luckytntlib.util.explosions.rules.ExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterFullBlockExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.explosions.rules.SimpleExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class WastelandTNTEffect extends PrimedTNTEffect {
	
	public static final List<Block> GRASS = List.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.MYCELIUM, Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS);
	public static final List<Block> DIRT = List.of(Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.ROOTED_DIRT);

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		doVaporizeExplosion(entity.getLevel(), entity.getPos(), 75, true);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		RandomSource random = entity.getLevel().getRandom();
		for (int count = 0; count < 100; count++) {
			entity.getLevel().addParticle(ParticleTypes.CLOUD, true, entity.x() + random.nextDouble() * 30d - random.nextDouble() * 30d, entity.y() + 0.5d, entity.z() + random.nextDouble() * 30d - random.nextDouble() * 30d, 0, 0, 0);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.WASTELAND_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 180;
	}
	
	public static void doVaporizeExplosion(Level level, Vec3 position, int radius, boolean affectAllBlocks) {
		if (!level.isClientSide()) {
			ExplosionRule rule = null;
			if (affectAllBlocks) {
				rule = new FilterAirExplosionRule(
					new StackedExplosionRule(
						new DrainAreaExplosionRule(),
						FilterBlockExplosionRule.builder().filterForTags(List.of(BlockTags.ICE, BlockTags.SNOW, BlockTags.LEAVES)).build(
							new AlwaysExplosionRule()
						),
						LogicExplosionRule.or(
							FilterBlockExplosionRule.applyOnlyWhen(BlockTags.SWORD_EFFICIENT, 
								LogicExplosionRule.not(
									new FilterFullBlockExplosionRule(new AlwaysExplosionRule()),
									new AlwaysExplosionRule()
								)
							),
							FilterBlockExplosionRule.builder().filterForBlocks(Blocks.CACTUS, Blocks.BAMBOO, Blocks.BAMBOO_SAPLING).build(new AlwaysExplosionRule()),
							new StackedExplosionRule(
								new CanSurviveExplosionRule(Blocks.DEAD_BUSH.defaultBlockState()),
								new AlwaysExplosionRule()
							)
						),
						FilterBlockExplosionRule.builder().filterForBlocks(GRASS).build(
							new SimpleExplosionRule(Blocks.DIRT.defaultBlockState())
						),
						FilterBlockExplosionRule.builder().filterForBlocks(DIRT).build(
							new SimpleExplosionRule(Blocks.SAND.defaultBlockState())
						),
						FilterBlockExplosionRule.applyOnlyWhen(Blocks.WET_SPONGE,
							new SimpleExplosionRule(Blocks.SPONGE.defaultBlockState())
						)
					)
				);
			} else {
				rule = new FilterAirExplosionRule(new DrainAreaExplosionRule());
			}
			
			if (radius > 50) {
				ExplosionHelper.createSphericalCrater(level, position, radius, 100, rule);
			} else {
				ExplosionHelper.legacySphericalExplosion(level, position, radius, 100, rule);
			}
		}
	}
}
