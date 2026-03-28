package luckytnt.tnteffects;

import org.joml.Math;
import org.joml.Vector3f;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class AetherTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ServerLevel serverLevel = (ServerLevel)entity.getLevel();
		RandomSource random = serverLevel.getRandom();
		ImprovedExplosion dummyExplosion = ImprovedExplosion.dummyExplosion(serverLevel);
		int islandHeight = LuckyTNTConfigValues.ISLAND_HEIGHT.get() * 2;
		ExplosionHelper.customSpheroidExplosion(serverLevel, entity.getPos(), 100, new Vector3f(1f, 0.5f, 1f), (level, center, pos, state) -> {
			if (Math.abs(entity.y() - pos.getY()) <= 35 && state.getExplosionResistance(level, pos, dummyExplosion) <= 200) {
				BlockPos islandPos = pos.above(islandHeight);
				if (state.is(BlockTags.LOGS) && state.hasProperty(BlockStateProperties.AXIS)) {
					level.setBlockAndUpdate(islandPos, Blocks.DARK_OAK_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)));
				} else if (state.is(BlockTags.LEAVES)) {
					if (random.nextFloat() < 0.9f) {
						level.setBlockAndUpdate(islandPos, Blocks.AZALEA_LEAVES.defaultBlockState());
					} else {
						level.setBlockAndUpdate(islandPos, Blocks.FLOWERING_AZALEA_LEAVES.defaultBlockState());
					}
				} else {
					level.setBlockAndUpdate(islandPos, state);
				}
			}
		});
		
		Registry<ConfiguredFeature<?, ?>> features = serverLevel.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
		ConfiguredFeature<?, ?> flowers = features.get(VegetationFeatures.FOREST_FLOWERS);
		ConfiguredFeature<?, ?> flowerForestFlowers = features.get(VegetationFeatures.FLOWER_FLOWER_FOREST);
		int maxDistanceSqr = 100 * 100;
		for(int offX = -100; offX <= 100; offX += 10) {
			for(int offZ = -100; offZ <= 100; offZ += 10) {
				int distanceSqr = offX * offX + offZ * offZ;
				int x = Mth.floor(entity.x()) + offX;
				int z = Mth.floor(entity.z()) + offZ;
				int y = getIslandTop(serverLevel, x, Mth.floor(entity.y()) + islandHeight, z);
				if(distanceSqr <= maxDistanceSqr && y != Integer.MAX_VALUE) {
					BlockPos pos = new BlockPos(x, y + 1, z);
					float rand = random.nextFloat();
					if (rand < 0.4f) {
						flowers.place(serverLevel, serverLevel.getChunkSource().getGenerator(), random, pos);
					} else if (rand < 0.8f) {
						flowerForestFlowers.place(serverLevel, serverLevel.getChunkSource().getGenerator(), random, pos);
					}
				}
			}
		}
	}
	
	private int getIslandTop(Level level, int x, int islandY, int z) {
		for (int offY = 35; offY >= -35; offY--) {
			BlockPos pos = new BlockPos(x, islandY + offY, z);
			BlockState state = level.getBlockState(pos);
			if (state.isCollisionShapeFullBlock(level, pos)) {
				return islandY + offY;
			}
		}
		return Integer.MAX_VALUE;
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		if(ent.getTNTFuse() % 3 == 0) {
			for(double d = 0D; d <= 1.5D; d += 0.1D) {
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.97f, 0.84f, 0.45f), 0.75f), ent.x() + 0.5D, ent.y() + 1.1D + d, ent.z(), 0, 0, 0);
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.97f, 0.84f, 0.45f), 0.75f), ent.x() + 0.4D, ent.y() + 1.1D + d, ent.z(), 0, 0, 0);
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.97f, 0.84f, 0.45f), 0.75f), ent.x() - 0.5D, ent.y() + 1.1D + d, ent.z(), 0, 0, 0);
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.97f, 0.84f, 0.45f), 0.75f), ent.x() - 0.4D, ent.y() + 1.1D + d, ent.z(), 0, 0, 0);
			}
			for(double d = 0D; d <= 1D; d += 0.1D) {
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.97f, 0.84f, 0.45f), 0.75f), ent.x() + 0.5D - d, ent.y() + 1.1D, ent.z(), 0, 0, 0);
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.97f, 0.84f, 0.45f), 0.75f), ent.x() + 0.5D - d, ent.y() + 1.2D, ent.z(), 0, 0, 0);
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.97f, 0.84f, 0.45f), 0.75f), ent.x() + 0.5D - d, ent.y() + 2.6D, ent.z(), 0, 0, 0);
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.97f, 0.84f, 0.45f), 0.75f), ent.x() + 0.5D - d, ent.y() + 2.5D, ent.z(), 0, 0, 0);
			}
			for(double x = -0.3D; x <= 0.3D; x += 0.1D) {
				for(double y = 0.2D; y <= 1.3D; y += 0.1D) {
					ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.31f, 0.46f, 0.86f), 0.75f), ent.x() + x + 0.05D, ent.y() + 1.1D + y, ent.z(), 0, 0, 0);
				}
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.AETHER_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 200;
	}
}
