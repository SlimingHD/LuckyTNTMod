package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterFullBlockExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class SculkTNTEffect extends PrimedTNTEffect {
	
	private final int radius;
	
	public SculkTNTEffect(int radius) {
		this.radius = radius;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(level);
		
		ExplosionHelper.legacySphericalExplosion(level, entity.getPos(), radius, 99f, new FilterAirExplosionRule(
			LogicExplosionRule.or(
				LogicExplosionRule.not(
					new FilterFullBlockExplosionRule(new AlwaysExplosionRule()), 
					new AlwaysExplosionRule()
				), 
				FilterBlockExplosionRule.builder().filterForTags(List.of(BlockTags.LEAVES, BlockTags.LOGS)).build(new AlwaysExplosionRule()), 
				new AlwaysExplosionRule()
			)
		));
		
		ExplosionHelper.customSphericalExplosion(level, entity.getPos(), radius, (lev, center, pos, state) -> {
			BlockPos posBelow = pos.below();
			BlockPos posAbove = pos.above();
			BlockState stateBelow = lev.getBlockState(posBelow);
			BlockState stateAbove = lev.getBlockState(posAbove);
			if (Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) < 100f && !state.isAir() && (stateAbove.isAir() || stateBelow.isAir()) && !state.is(BlockTags.LUSH_GROUND_REPLACEABLE)) {
				level.setBlockAndUpdate(pos, Blocks.STONE.defaultBlockState());
				state.getBlock().wasExploded(level, pos, dummy);
			}
		});
		
		if (level instanceof ServerLevel server) {
			RandomSource random = server.getRandom();
			ChunkGenerator chunkGenerator = server.getChunkSource().getGenerator();
			Registry<ConfiguredFeature<?, ?>> featureRegistry = server.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
			ConfiguredFeature<?, ?> deepDarkPatch = featureRegistry.getHolderOrThrow(CaveFeatures.SCULK_PATCH_DEEP_DARK).value();
			ConfiguredFeature<?, ?> ancientCityPatch = featureRegistry.getHolderOrThrow(CaveFeatures.SCULK_PATCH_ANCIENT_CITY).value();
			ExplosionHelper.customSphericalExplosion(server, entity.getPos(), radius, (lev, center, pos, state) -> {
				if (state.isAir()) {
					BlockPos posBelow = pos.below();
					BlockPos posAbove = pos.above();
					BlockState stateBelow = lev.getBlockState(posBelow);
					BlockState stateAbove = lev.getBlockState(posAbove);
					
					if (!stateAbove.isAir() && random.nextDouble() < 0.03d) {
						deepDarkPatch.place(server, chunkGenerator, random, pos);
					}
					if (!stateBelow.isAir() && random.nextDouble() < 0.03d) {
						if (random.nextBoolean()) {
							deepDarkPatch.place(server, chunkGenerator, random, pos);
						} else {
							ancientCityPatch.place(server, chunkGenerator, random, pos);
						}
					}
				}
			});
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.SCULK_TNT.get();
	}
}
