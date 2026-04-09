package luckytnt.tnteffects;

import java.util.List;

import luckytnt.event.LevelEvents;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.CraterExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterSurfaceExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.Vec3;

public class FlowerForestTNTEffect extends PrimedTNTEffect {
	
	private static final List<TagKey<Block>> REMOVE_TAGS = List.of(BlockTags.LOGS, BlockTags.PLANKS, BlockTags.BAMBOO_BLOCKS, BlockTags.BEEHIVES, BlockTags.LEAVES);

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		ExplosionHelper.createCylindricalCrater(level, entity.getPos(), 75, 75, 200f, new StackedExplosionRule(
			new FilterSurfaceExplosionRule(false, new CraterExplosionRule()),
			FilterBlockExplosionRule.builder().filterForTags(REMOVE_TAGS).build(new CraterExplosionRule())
		));
		
		int maxDistanceSqr = 75 * 75;
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(level);
		for (int offX = -75; offX <= 75; offX++) {
			for (int offZ = -75; offZ <= 75; offZ++) {
				int distanceSqr = offX * offX + offZ * offZ;
				int y = LevelEvents.getTopBlock(level, entity.x() + offX, entity.z() + offZ, true);
				BlockPos pos = toBlockPos(new Vec3(entity.x() + offX, y, entity.z() + offZ));
				if (distanceSqr <= maxDistanceSqr && level.getBlockState(pos).getExplosionResistance(level, pos, dummy) <= 200f && level.getBlockState(pos.above()).isAir()) {
					level.setBlock(pos, Blocks.GRASS_BLOCK.defaultBlockState(), 3);
				}
			}
		}
		
		RandomSource random = level.getRandom();
		ServerLevel serverLevel = (ServerLevel)level;
		LevelEvents.setBiomeInCylinder(serverLevel, entity.getPos(), 75, Biomes.FLOWER_FOREST);
		Registry<ConfiguredFeature<?, ?>> features = entity.getLevel().registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
		for (int offX = -75; offX < 75; offX++) {
			for (int offZ = -75; offZ < 75; offZ++) {
				int distanceSqr = offX * offX + offZ * offZ;
				if (distanceSqr <= maxDistanceSqr) {
					int y = LevelEvents.getTopBlock(level, entity.x() + offX, entity.z() + offZ, false);
					BlockPos pos = toBlockPos(new Vec3(entity.x() + offX, y, entity.z() + offZ));
					BlockState state = entity.getLevel().getBlockState(pos);
					if (state.isCollisionShapeFullBlock(level, pos) && !state.isAir() && !(level.getBlockState(pos.above()).getBlock() instanceof LiquidBlock)) {
						float rand = random.nextFloat();
						if (rand <= 0.1d) {
							features.get(VegetationFeatures.TREES_FLOWER_FOREST).place(serverLevel, serverLevel.getChunkSource().getGenerator(), random, pos.above());
						} else if (rand <= 0.1125d) {
							features.get(VegetationFeatures.FOREST_FLOWERS).place(serverLevel, serverLevel.getChunkSource().getGenerator(), random, pos.above());
						} else if (rand <= 0.125d) {
							features.get(VegetationFeatures.FLOWER_FLOWER_FOREST).place(serverLevel, serverLevel.getChunkSource().getGenerator(), random, pos.above());
						} else if (rand <= 0.1375d) {
							features.get(VegetationFeatures.PATCH_GRASS).place(serverLevel, serverLevel.getChunkSource().getGenerator(), random, pos.above());
						}
					}
				}
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.FLOWER_FOREST_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
}
