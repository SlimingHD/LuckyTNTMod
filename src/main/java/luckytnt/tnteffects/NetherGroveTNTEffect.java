package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.CraterExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterFullBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterSurfaceExplosionRule;
import luckytntlib.util.explosions.rules.SimpleExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class NetherGroveTNTEffect extends PrimedTNTEffect {

	private final int radius;

	public NetherGroveTNTEffect(int radius) {
		this.radius = radius;
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();

		Registry<ConfiguredFeature<?, ?>> configuredFeatures = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
		ConfiguredFeature<?, ?> tree;
		ConfiguredFeature<?, ?> vegetation;
		BlockState surfaceBlock;
		if (random.nextBoolean()) {
			tree = configuredFeatures.getOrThrow(TreeFeatures.CRIMSON_FUNGUS);
			vegetation = configuredFeatures.getOrThrow(NetherFeatures.CRIMSON_FOREST_VEGETATION_BONEMEAL);
			surfaceBlock = Blocks.CRIMSON_NYLIUM.defaultBlockState();
		} else {
			tree = configuredFeatures.getOrThrow(TreeFeatures.WARPED_FUNGUS);
			vegetation = configuredFeatures.getOrThrow(NetherFeatures.WARPED_FOREST_VEGETATION_BONEMEAL);
			surfaceBlock = Blocks.WARPED_NYLIUM.defaultBlockState();
		}

		ExplosionHelper.legacySphericalExplosion(level, entity.getPos(), radius, 99f, new StackedExplosionRule(
			FilterBlockExplosionRule.builder().filterForTags(List.of(BlockTags.LEAVES, BlockTags.LOGS, BlockTags.WART_BLOCKS)).build(new AlwaysExplosionRule()),
			new FilterSurfaceExplosionRule(false, new CraterExplosionRule()), 
			new FilterSurfaceExplosionRule(true, 
				new FilterFullBlockExplosionRule(new SimpleExplosionRule(surfaceBlock))
			)
		));

		if (level instanceof ServerLevel server) {
			ChunkGenerator chunkGenerator = server.getChunkSource().getGenerator();
			ExplosionHelper.customSurfaceExplosion(level, entity.getPos(), radius, (lev, center, pos, state) -> {
				if (random.nextFloat() < 0.05f) {
					tree.place(server, chunkGenerator, random, pos.above());
				}
				if (random.nextFloat() < 0.1f) {
					vegetation.place(server, chunkGenerator, random, pos.above());
				}
			});
		}
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.NETHER_GROVE_TNT.get();
	}
}
