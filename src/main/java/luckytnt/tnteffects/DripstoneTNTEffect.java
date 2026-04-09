package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytnt.rules.FilterFullBlockExplosionRule;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.CraterExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class DripstoneTNTEffect extends PrimedTNTEffect {

	private final int radius;
	
	public DripstoneTNTEffect(int radius) {
		this.radius = radius;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), radius, 100, new FilterAirExplosionRule(
				new StackedExplosionRule(
						FilterBlockExplosionRule.builder()
							.filterForTags(List.of(BlockTags.LOGS, BlockTags.LEAVES))
							.build(new CraterExplosionRule()
						),
						LogicExplosionRule.not(new FilterFullBlockExplosionRule(new AlwaysExplosionRule()), new CraterExplosionRule())
				)
		));
		ImprovedExplosion particleExplosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), radius);
		particleExplosion.spawnExplosionParticles();
		ExplosionHelper.customSphericalExplosion(entity.getLevel(), entity.getPos(), radius, (level, center, pos, state) -> {
			BlockState stateBelow = level.getBlockState(pos.below());
			if ((stateBelow.isAir() && !state.is(BlockTags.DRIPSTONE_REPLACEABLE) && state.getExplosionResistance(level, pos, particleExplosion) < 100) ||
					(state.isAir() && stateBelow.is(BlockTags.DRIPSTONE_REPLACEABLE) && stateBelow.getExplosionResistance(level, pos, particleExplosion) < 100)) {
				state.getBlock().wasExploded(level, pos, particleExplosion);
				level.setBlockAndUpdate(pos, Blocks.STONE.defaultBlockState());
			}
		});
		if (entity.getLevel() instanceof ServerLevel serverLevel) {
			RandomSource random = entity.getLevel().getRandom();
			Registry<ConfiguredFeature<?, ?>> featureRegistry = serverLevel.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
			ExplosionHelper.customSphericalExplosion(serverLevel, entity.getPos(), Math.round(radius * 0.75f), (level, center, pos, state) -> {
				BlockState stateBelow = level.getBlockState(pos.below());
				if (((!state.isAir() && stateBelow.isAir()) || (stateBelow.isAir() && !state.isAir())) && random.nextFloat() < 0.1f) {
					if (random.nextFloat() < 0.9f) {
						featureRegistry.getHolderOrThrow(CaveFeatures.DRIPSTONE_CLUSTER).get().place(serverLevel, serverLevel.getChunkSource().getGenerator(), random, pos);
					} else {
						featureRegistry.getHolderOrThrow(CaveFeatures.LARGE_DRIPSTONE).get().place(serverLevel, serverLevel.getChunkSource().getGenerator(), random, pos);
					}
				}
			});
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.DRIPSTONE_TNT.get();
	}
}
