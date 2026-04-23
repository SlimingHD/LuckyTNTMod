package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.rules.CopyPropertiesExplosionRule;
import luckytnt.rules.FilterLiquidExplosionRule;
import luckytntlib.util.BiomeSetter;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.RandomList;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.CopyBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlastResistanceExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterCollidableExplosionRule;
import luckytntlib.util.explosions.rules.FilterOffYExplosionRule;
import luckytntlib.util.explosions.rules.FilterSurfaceExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.explosions.rules.OffsetExplosionRule;
import luckytntlib.util.explosions.rules.RandomBlockExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
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
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class AetherTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ServerLevel serverLevel = (ServerLevel)entity.getLevel();
		RandomSource random = serverLevel.getRandom();
		int islandHeight = 80;
		ExplosionHelper.createSphericalCrater(serverLevel, entity.getPos().add(0d, islandHeight, 0d), 100, 200f, new FilterOffYExplosionRule(-25, 25,
			new OffsetExplosionRule(-islandHeight, 
				new FilterBlastResistanceExplosionRule(200f, 
					new StackedExplosionRule(
						FilterBlockExplosionRule.applyOnlyWhen(BlockTags.LEAVES, new CopyPropertiesExplosionRule(new RandomBlockExplosionRule(RandomList.<BlockState>floatBuilder().addEntry(Blocks.FLOWERING_AZALEA_LEAVES.defaultBlockState(), 0.1f).addEntry(Blocks.AZALEA_LEAVES.defaultBlockState(), 0.9f).build()))),
						FilterBlockExplosionRule.applyOnlyWhen(BlockTags.LOGS, new CopyPropertiesExplosionRule(new BlockExplosionRule(Blocks.DARK_OAK_LOG.defaultBlockState()))),
						LogicExplosionRule.not(new FilterCollidableExplosionRule(new AlwaysExplosionRule()), new AlwaysExplosionRule()),
						new FilterSurfaceExplosionRule(true, new BlockExplosionRule(Blocks.GRASS_BLOCK.defaultBlockState())),
						new FilterLiquidExplosionRule(new BlockExplosionRule(Blocks.WATER.defaultBlockState())),
						new CopyBlockExplosionRule()
					)
				)
			)
		));
		
		Registry<ConfiguredFeature<?, ?>> features = serverLevel.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
		ConfiguredFeature<?, ?> flowers = features.get(VegetationFeatures.FOREST_FLOWERS);
		ConfiguredFeature<?, ?> flowerForestFlowers = features.get(VegetationFeatures.FLOWER_FLOWER_FOREST);
		ConfiguredFeature<?, ?> grassPatch = features.get(VegetationFeatures.PATCH_GRASS);
		ConfiguredFeature<?, ?> tallGrassPatch = features.get(VegetationFeatures.PATCH_TALL_GRASS);
		int maxDistanceSqr = 100 * 100;
		for(int offX = -100; offX <= 100; offX += 5) {
			for(int offZ = -100; offZ <= 100; offZ += 5) {
				int distanceSqr = offX * offX + offZ * offZ;
				int x = Mth.floor(entity.x()) + offX;
				int z = Mth.floor(entity.z()) + offZ;
				int y = getIslandTop(serverLevel, x, Mth.floor(entity.y()) + islandHeight, z);
				if(distanceSqr <= maxDistanceSqr && y != Integer.MAX_VALUE) {
					BlockPos pos = new BlockPos(x, y, z);
					float rand = random.nextFloat();
					if (rand < 0.3f) {
						flowers.place(serverLevel, serverLevel.getChunkSource().getGenerator(), random, pos);
					} else if (rand < 0.6f) {
						flowerForestFlowers.place(serverLevel, serverLevel.getChunkSource().getGenerator(), random, pos);
					} else if (rand < 0.75f) {
						grassPatch.place(serverLevel, serverLevel.getChunkSource().getGenerator(), random, pos);
					} else if (rand < 0.8f) {
						tallGrassPatch.place(serverLevel, serverLevel.getChunkSource().getGenerator(), random, pos);
					}
				}
			}
		}
		
		BiomeSetter.setBiomeInCylinder(serverLevel, entity.getPos().add(0d, islandHeight, 0d), 120, 60, Biomes.CHERRY_GROVE);
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
