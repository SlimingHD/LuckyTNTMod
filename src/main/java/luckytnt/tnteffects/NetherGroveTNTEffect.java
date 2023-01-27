package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IBlockExplosionCondition;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.Material;

public class NetherGroveTNTEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Holder<ConfiguredFeature<?, ?>> tree;
		Holder<ConfiguredFeature<?, ?>> vegetation;
		Block topBlock;
		if (Math.random() < 0.5D) {
			tree = entity.level().registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolderOrThrow(TreeFeatures.CRIMSON_FUNGUS);
			vegetation = entity.level().registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolderOrThrow(NetherFeatures.CRIMSON_FOREST_VEGETATION_BONEMEAL);
			topBlock = Blocks.CRIMSON_NYLIUM;
		} else {
			tree = entity.level().registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolderOrThrow(TreeFeatures.WARPED_FUNGUS);
			vegetation = entity.level().registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolderOrThrow(NetherFeatures.WARPED_FOREST_VEGETATION_BONEMEAL);
			topBlock = Blocks.WARPED_NYLIUM;
		}
		ExplosionHelper.doTopBlockExplosion(entity.level(), entity.getPos(), 30, new IBlockExplosionCondition() {
			
			@Override
			public boolean conditionMet(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getMaterial() == Material.LEAVES || state.is(BlockTags.LOGS) || state.is(BlockTags.FLOWERS) || state.is(BlockTags.WART_BLOCKS) || (!state.isCollisionShapeFullBlock(level, pos) && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion()) < 100)) {
					state.onBlockExploded(level, pos, ImprovedExplosion.dummyExplosion());
					return false;
				}
				return true;
			}
		}, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getExplosionResistance(level, pos.below(), ImprovedExplosion.dummyExplosion()) < 100) {
					level.getBlockState(pos.below()).onBlockExploded(level, pos, ImprovedExplosion.dummyExplosion());
					level.setBlockAndUpdate(pos.below(), topBlock.defaultBlockState());
				}
			}
		});
		ExplosionHelper.doTopBlockExplosion(entity.level(), entity.getPos(), 30, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(level instanceof ServerLevel sLevel) {
					if(Math.random() < 0.05f) {
						tree.value().place(sLevel, sLevel.getChunkSource().getGenerator(), sLevel.random, pos);
					}
					if(Math.random() < 0.1f) {
						vegetation.value().place(sLevel, sLevel.getChunkSource().getGenerator(), sLevel.random, pos);
					}
				}
			}
		});
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.NETHER_GROVE_TNT.get();
	}
}
