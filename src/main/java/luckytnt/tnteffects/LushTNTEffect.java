package luckytnt.tnteffects;

import java.util.List;

import org.joml.Vector3f;

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
import net.minecraft.core.particles.DustParticleOptions;
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

public class LushTNTEffect extends PrimedTNTEffect {

	private final int radius;
	
	public LushTNTEffect(int radius) {
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
			ConfiguredFeature<?, ?> ceilingPatch = featureRegistry.getHolderOrThrow(CaveFeatures.MOSS_PATCH_CEILING).value();
			ConfiguredFeature<?, ?> lushClay = featureRegistry.getHolderOrThrow(CaveFeatures.LUSH_CAVES_CLAY).value();
			ConfiguredFeature<?, ?> mossPatch = featureRegistry.getHolderOrThrow(CaveFeatures.MOSS_PATCH).value();
			ExplosionHelper.customSphericalExplosion(server, entity.getPos(), radius, (lev, center, pos, state) -> {
				if (state.isAir()) {
					BlockPos posBelow = pos.below();
					BlockPos posAbove = pos.above();
					BlockState stateBelow = lev.getBlockState(posBelow);
					BlockState stateAbove = lev.getBlockState(posAbove);
					
					if (!stateAbove.isAir() && random.nextDouble() < 0.025d) {
						ceilingPatch.place(server, chunkGenerator, random, pos);
					}
					if (!stateBelow.isAir() && random.nextDouble() < 0.1d) {
						if (random.nextBoolean()) {
							lushClay.place(server, chunkGenerator, random, pos);
						} else {
							mossPatch.place(server, chunkGenerator, random, pos);
						}
					}
				}
			});
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		
		for (int count = 0; count <= 20; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(0.36f, 0.27f, 0.11f), 0.75f), entity.x() + random.nextDouble() * 0.0625d - random.nextDouble() * 0.0625d, entity.y() + 1d + random.nextDouble() * 0.375d, entity.z() + random.nextDouble() * 0.0625d - random.nextDouble() * 0.0625d, 0d, 0d, 0d);
		}
		for (int count = 0; count <= 60; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(0.44f, 0.57f, 0.18f), 0.75f), entity.x() + random.nextDouble() * 0.75d - random.nextDouble() * 0.75d, entity.y() + 1d + 0.375d + random.nextDouble() * 0.625d, entity.z() + random.nextDouble() * 0.75d - random.nextDouble() * 0.75d, 0d, 0d, 0d);
		}
		for (int count = 0; count <= 10; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(0.82f, 0.48f, 0.89f), 0.75f), entity.x() + random.nextDouble() * 0.75d - random.nextDouble() * 0.75d, entity.y() + 1d + 0.375d + random.nextDouble() * 0.625d, entity.z() + random.nextDouble() * 0.75d - random.nextDouble() * 0.75d, 0d, 0d, 0d);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.LUSH_TNT.get();
	}
}
