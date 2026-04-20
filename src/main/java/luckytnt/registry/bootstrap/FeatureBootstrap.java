package luckytnt.registry.bootstrap;

import com.google.common.collect.ImmutableList;

import luckytnt.registry.keys.ConfiguredFeatureKeys;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

public class FeatureBootstrap {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void bootstrapConfigured(BootstapContext<ConfiguredFeature<?, ?>> context) {
		context.register(ConfiguredFeatureKeys.MEGA_DARK_OAK, new ConfiguredFeature(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.DARK_OAK_LOG), new GiantTrunkPlacer(13, 2, 14), BlockStateProvider.simple(Blocks.DARK_OAK_LEAVES), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(3, 7)), new TwoLayersFeatureSize(1, 1, 2)).build()));
		context.register(ConfiguredFeatureKeys.MEGA_AZALEA, new ConfiguredFeature(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new GiantTrunkPlacer(13, 2, 14), new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(Blocks.AZALEA_LEAVES.defaultBlockState(), 3).add(Blocks.FLOWERING_AZALEA_LEAVES.defaultBlockState(), 1)), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(13, 17)), new TwoLayersFeatureSize(1, 1, 2)).build()));
		context.register(ConfiguredFeatureKeys.MEGA_CHERRY, new ConfiguredFeature(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.CHERRY_LOG), new MegaJungleTrunkPlacer(10, 2, 19), BlockStateProvider.simple(Blocks.CHERRY_LEAVES), new MegaJungleFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2), new TwoLayersFeatureSize(1, 1, 2)).build()));
		context.register(ConfiguredFeatureKeys.SUPER_BIRCH_BEES, new ConfiguredFeature(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.BIRCH_LOG), new StraightTrunkPlacer(8, 6, 0), BlockStateProvider.simple(Blocks.BIRCH_LEAVES), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().decorators(ImmutableList.of(new BeehiveDecorator(1f))).build()));
		context.register(ConfiguredFeatureKeys.SUPER_BIRCH_BEES_0002, new ConfiguredFeature(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.BIRCH_LOG), new StraightTrunkPlacer(8, 6, 0), BlockStateProvider.simple(Blocks.BIRCH_LEAVES), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().decorators(ImmutableList.of(new BeehiveDecorator(0.002f))).build()));
	}
}
