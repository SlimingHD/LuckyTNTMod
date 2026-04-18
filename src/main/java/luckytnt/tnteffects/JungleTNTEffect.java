package luckytnt.tnteffects;

import java.util.List;

import luckytnt.event.LevelEvents;
import luckytnt.registry.BlockRegistry;
import luckytnt.rules.FilterLiquidExplosionRule;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.CopyBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterFullBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterSurfaceExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.explosions.rules.OffsetExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class JungleTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		
		ExplosionHelper.createSphericalCrater(level, entity.getPos(), 150, 99.9f, new FilterAirExplosionRule(
			new StackedExplosionRule(
				FilterBlockExplosionRule.builder().filterForBlocks(Blocks.SEA_PICKLE, Blocks.SEAGRASS, Blocks.TALL_SEAGRASS, Blocks.KELP, Blocks.KELP_PLANT, Blocks.BUBBLE_COLUMN).filterForTags(List.of(BlockTags.CORALS, BlockTags.WALL_CORALS)).build(new CopyBlockExplosionRule()),
				LogicExplosionRule.or(
					FilterBlockExplosionRule.builder().filterForTags(List.of(BlockTags.LEAVES, BlockTags.LOGS)).build(new AlwaysExplosionRule()), 
					LogicExplosionRule.not(
						new FilterFullBlockExplosionRule(new AlwaysExplosionRule()), 
						new AlwaysExplosionRule()
					), 
					new AlwaysExplosionRule()
				)
			)
		));
		
		ExplosionHelper.createSphericalCrater(level, entity.getPos(), 150, 99.9f, new FilterSurfaceExplosionRule(true, 
			LogicExplosionRule.not(
				new OffsetExplosionRule(1, new FilterLiquidExplosionRule(new AlwaysExplosionRule())), 
				new BlockExplosionRule(Blocks.GRASS_BLOCK.defaultBlockState())
			)
		));
		
		if (level instanceof ServerLevel server) {
			RandomSource random = server.getRandom();
			BlockPos centerPos = BlockPos.containing(entity.getPos());
			ChunkGenerator chunkGenerator = server.getChunkSource().getGenerator();
			
			Registry<ConfiguredFeature<?, ?>> configuredFeatures = server.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
			ConfiguredFeature<?, ?> melonPatch = configuredFeatures.get(VegetationFeatures.PATCH_MELON);
			ConfiguredFeature<?, ?> jungleTrees = configuredFeatures.get(VegetationFeatures.TREES_JUNGLE);
			ConfiguredFeature<?, ?> grassPatch = configuredFeatures.get(VegetationFeatures.PATCH_GRASS_JUNGLE);
			
			LevelEvents.setBiomeInCylinder(server, entity.getPos(), 150, 100, Biomes.JUNGLE);
			
			ExplosionHelper.customSurfaceExplosion(server, entity.getPos(), 150, (lev, center, pos, state) -> {
				int offX = pos.getX() - centerPos.getX();
				int offZ = pos.getZ() - centerPos.getZ();
				BlockPos posAbove = pos.above();
				if (!lev.getBlockState(posAbove).isAir()) {
					return;
				}
				if (offX % 30 == 0 && offZ % 30 == 0) {
					melonPatch.place(server, chunkGenerator, random, posAbove);
				}
				if (random.nextFloat() < 0.666f) {
					ConfiguredFeature<?, ?> feature = random.nextBoolean() ? jungleTrees : grassPatch;
					feature.place(server, chunkGenerator, random, posAbove);
				}
			});
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.JUNGLE_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
}
