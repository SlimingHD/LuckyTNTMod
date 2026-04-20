package luckytnt.tnteffects;

import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.keys.ConfiguredFeatureKeys;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.CraterExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterFullBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterSurfaceExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class GroveTNTEffect extends PrimedTNTEffect {

	private final int size, rangeToOccupy;
	private final float treeChance;
	private final boolean bigTrees;
	
	public GroveTNTEffect(int size, int rangeToOccupy, float treeChance, boolean bigTrees) {
		this.size = size;
		this.rangeToOccupy = rangeToOccupy;
		this.treeChance = treeChance;
		this.bigTrees = bigTrees;
	}
	
	public GroveTNTEffect(int size) {
		this(size, 3, 0.35f, false);
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		if (entity.getLevel() instanceof ServerLevel server) {
			RandomSource random = server.getRandom();
			
			int powerOfTwo = Mth.smallestEncompassingPowerOfTwo(size * 2);
			int shiftAmount = 32 - Integer.numberOfLeadingZeros(powerOfTwo) - 1;
			BitSet occupiedBlocks = new BitSet(powerOfTwo << shiftAmount);
			
			Registry<ConfiguredFeature<?, ?>> registry = server.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
			List<List<ConfiguredFeature<?, ?>>> features = TreeType.getFeatures(registry, bigTrees);
			
			ExplosionHelper.createCylindricalCrater(server, entity.getPos(), size, size, 99f, new StackedExplosionRule(
				FilterBlockExplosionRule.builder().filterForTags(List.of(BlockTags.LEAVES, BlockTags.LOGS)).build(new AlwaysExplosionRule()),
				new FilterSurfaceExplosionRule(false, new CraterExplosionRule()), 
				new FilterSurfaceExplosionRule(true, 
					new FilterFullBlockExplosionRule(new BlockExplosionRule(Blocks.GRASS_BLOCK.defaultBlockState()))
				)
			));
			
			BlockPos centerPos = BlockPos.containing(entity.getPos());
			List<Pair<ConfiguredFeature<?, ?>, BlockPos>> featuresToPlace = new LinkedList<>();
			ExplosionHelper.customSurfaceExplosion(server, entity.getPos(), size, (lev, center, pos, state) -> {
				int offX = pos.getX() - centerPos.getX() + size;
				int offZ = pos.getZ() - centerPos.getZ() + size;
				if (!occupiedBlocks.get((offX << shiftAmount) | offZ) && random.nextFloat() < treeChance) {
					int tree = random.nextInt(features.size());
					featuresToPlace.add(Pair.of(features.get(tree).get(random.nextInt(features.get(tree).size())), pos.above()));
					for (int x = Mth.clamp(offX - rangeToOccupy, 0, size * 2); x <= Mth.clamp(offX + rangeToOccupy, 0, size * 2); x++) {
						for (int z = Mth.clamp(offZ - rangeToOccupy, 0, size * 2); z <= Mth.clamp(offZ + rangeToOccupy, 0, size * 2); z++) {
							occupiedBlocks.set((x << shiftAmount) | z);
						}
					}
				}
			});
			
			ChunkGenerator chunkGenerator = server.getChunkSource().getGenerator();
			for (Pair<ConfiguredFeature<?, ?>, BlockPos> featureToPlace : featuresToPlace) {
				featureToPlace.getFirst().place(server, chunkGenerator, random, featureToPlace.getSecond());
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.GROVE_TNT.get();
	}
	
	private static enum TreeType {
		OAK(List.of(TreeFeatures.OAK, TreeFeatures.OAK_BEES_0002, TreeFeatures.OAK_BEES_002, TreeFeatures.OAK_BEES_005, TreeFeatures.SWAMP_OAK), List.of(TreeFeatures.FANCY_OAK, TreeFeatures.FANCY_OAK_BEES, TreeFeatures.FANCY_OAK_BEES_0002, TreeFeatures.FANCY_OAK_BEES_002, TreeFeatures.FANCY_OAK_BEES_005)),
		BIRCH(List.of(TreeFeatures.BIRCH, TreeFeatures.BIRCH_BEES_0002, TreeFeatures.BIRCH_BEES_002, TreeFeatures.BIRCH_BEES_005), List.of(ConfiguredFeatureKeys.SUPER_BIRCH_BEES, ConfiguredFeatureKeys.SUPER_BIRCH_BEES_0002)),
		SPRUCE(List.of(TreeFeatures.SPRUCE, TreeFeatures.PINE), List.of(TreeFeatures.MEGA_PINE, TreeFeatures.MEGA_SPRUCE)),
		DARK_OAK(List.of(TreeFeatures.DARK_OAK), List.of(ConfiguredFeatureKeys.MEGA_DARK_OAK)),
		JUNGLE(List.of(TreeFeatures.JUNGLE_TREE, TreeFeatures.JUNGLE_TREE_NO_VINE), List.of(TreeFeatures.MEGA_JUNGLE_TREE)),
		AZALEA(List.of(TreeFeatures.AZALEA_TREE), List.of(ConfiguredFeatureKeys.MEGA_AZALEA)),
		MANGROVE(List.of(TreeFeatures.MANGROVE), List.of(TreeFeatures.TALL_MANGROVE)),
		CHERRY(List.of(TreeFeatures.CHERRY, TreeFeatures.CHERRY_BEES_005), List.of(ConfiguredFeatureKeys.MEGA_CHERRY));
		
		private final List<ResourceKey<ConfiguredFeature<?, ?>>> smallTreeFeatures;
		private final List<ResourceKey<ConfiguredFeature<?, ?>>> bigTreeFeatures;
		
		private TreeType(List<ResourceKey<ConfiguredFeature<?, ?>>> smallTreeFeatures, List<ResourceKey<ConfiguredFeature<?, ?>>> bigTreeFeatures) {
			this.smallTreeFeatures = smallTreeFeatures;
			this.bigTreeFeatures = bigTreeFeatures;
		}
		
		private static List<List<ConfiguredFeature<?, ?>>> getFeatures(Registry<ConfiguredFeature<?, ?>> registry, boolean bigTrees) {
			List<List<ConfiguredFeature<?, ?>>> allFeatures = new LinkedList<>();
			for (TreeType type : values()) {
				List<ConfiguredFeature<?, ?>> features = new LinkedList<>();
				for (ResourceKey<ConfiguredFeature<?, ?>> key : bigTrees ? type.bigTreeFeatures : type.smallTreeFeatures) {
					features.add(registry.getOrThrow(key));
				}
				allFeatures.add(features);
			}
			return allFeatures;
		}
	}
}
