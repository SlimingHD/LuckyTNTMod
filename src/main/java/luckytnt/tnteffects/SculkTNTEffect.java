package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class SculkTNTEffect extends PrimedTNTEffect {
	
	private final int radius;
	
	public SculkTNTEffect(int radius) {
		this.radius = radius;
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.doSphericalExplosion(entity.getLevel(), entity.getPos(), radius, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel())) < 100 && (!state.isCollisionShapeFullBlock(level, pos) || state.is(BlockTags.LEAVES) || state.is(BlockTags.LOGS))) {
					state.onBlockExploded(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel()));
				}
			}
		});
		ExplosionHelper.doSphericalExplosion(entity.getLevel(), entity.getPos(), Math.round(radius * 0.75f), new IForEachBlockExplosionEffect() {

			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(level.getBlockState(pos.below()).isAir() && !state.isAir() && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel())) < 100 && !state.is(BlockTags.LUSH_GROUND_REPLACEABLE)) {
					state.onBlockExploded(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel()));
					level.setBlockAndUpdate(pos, Blocks.STONE.defaultBlockState());
				}
				else if(level.getBlockState(pos.below()).getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel())) < 100 && !level.getBlockState(pos.below()).isAir() && state.isAir() && !level.getBlockState(pos.below()).is(BlockTags.LUSH_GROUND_REPLACEABLE)) {
					state.onBlockExploded(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel()));
					level.setBlockAndUpdate(pos.below(), Blocks.STONE.defaultBlockState());
				}
			}
		});
		if(entity.getLevel() instanceof ServerLevel sLevel) {
			ExplosionHelper.doSphericalExplosion(sLevel, entity.getPos(), Math.round(radius * 0.75f), new IForEachBlockExplosionEffect() {

				@Override
				public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
					if((level.getBlockState(pos.below()).isAir() && !state.isAir()) && Math.random() < 0.025f) {
						Holder<ConfiguredFeature<?, ?>> feature = entity.getLevel().registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolderOrThrow(CaveFeatures.SCULK_PATCH_DEEP_DARK);
						feature.value().place(sLevel, sLevel.getChunkSource().getGenerator(), sLevel.random, pos.below());
					}
					if((!level.getBlockState(pos.below()).isAir() && state.isAir()) && Math.random() < 0.03f) {
						Holder<ConfiguredFeature<?, ?>> feature = null;
						if(Math.random() < 0.5f) {
							feature = entity.getLevel().registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolderOrThrow(CaveFeatures.SCULK_PATCH_DEEP_DARK);
							feature.value().place(sLevel, sLevel.getChunkSource().getGenerator(), sLevel.random, pos);
						} else {
							feature = entity.getLevel().registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolderOrThrow(CaveFeatures.SCULK_PATCH_ANCIENT_CITY);
							feature.value().place(sLevel, sLevel.getChunkSource().getGenerator(), sLevel.random, pos);
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
