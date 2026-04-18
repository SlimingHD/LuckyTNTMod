package luckytnt.tnteffects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.rules.DrainAreaExplosionRule;
import luckytnt.util.StructureState;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterCollidableExplosionRule;
import luckytntlib.util.explosions.rules.FilterOffYExplosionRule;
import luckytntlib.util.explosions.rules.FilterSurfaceExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.structures.NetherFossilPieces;
import net.minecraft.world.level.levelgen.structure.structures.NetherFossilStructure;
import net.minecraft.world.phys.Vec3;

public class NetherTNTEffect extends PrimedTNTEffect {
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		Vec3 pos = new Vec3(entity.x(), level.getMinBuildHeight() + 64, entity.z());
		
		ExplosionHelper.createSphericalCrater(level, pos, 40, 200f);

		ExplosionHelper.createSphericalCrater(level, pos, 100, 200f, new FilterAirExplosionRule(
			new FilterOffYExplosionRule(-100, 50, new DrainAreaExplosionRule())
		));
		
		ImprovedExplosion explosion1 = new ImprovedExplosion(level, pos, 100).setExplosionFinishWork(e -> netherExplosionStep2(entity, level, pos));
		explosion1.doImprovedBlockExplosion(1f, 0.2f, true, false, null);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		if (ent.getTNTFuse() % 3 == 0) {
			Level level = ent.getLevel();
			for (double d = 0d; d <= 1.5d; d += 0.1d) {
				level.addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.75f), ent.x() + 0.5d, ent.y() + 1.1d + d, ent.z(), 0d, 0d, 0d);
				level.addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.75f), ent.x() + 0.4d, ent.y() + 1.1d + d, ent.z(), 0d, 0d, 0d);
				level.addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.75f), ent.x() - 0.5d, ent.y() + 1.1d + d, ent.z(), 0d, 0d, 0d);
				level.addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.75f), ent.x() - 0.4d, ent.y() + 1.1d + d, ent.z(), 0d, 0d, 0d);
			}
			for (double d = 0d; d <= 1d; d += 0.1d) {
				level.addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.75f), ent.x() + 0.5d - d, ent.y() + 1.1d, ent.z(), 0d, 0d, 0d);
				level.addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.75f), ent.x() + 0.5d - d, ent.y() + 1.2d, ent.z(), 0d, 0d, 0d);
				level.addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.75f), ent.x() + 0.5d - d, ent.y() + 2.6d, ent.z(), 0d, 0d, 0d);
				level.addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.75f), ent.x() + 0.5d - d, ent.y() + 2.5d, ent.z(), 0d, 0d, 0d);
			}
			for (double x = -0.3d; x <= 0.3d; x += 0.1d) {
				for (double y = 0.2d; y <= 1.3d; y += 0.1d) {
					level.addParticle(new DustParticleOptions(new Vector3f(0.5f, 0f, 1f), 0.75f), ent.x() + x + 0.05d, ent.y() + 1.1d + y, ent.z(), 0d, 0d, 0d);
				}
			}
		}
	} 
	
	@Override
	public Block getBlock() {
		return BlockRegistry.NETHER_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 180;
	}
	
	private static void netherExplosionStep2(IExplosiveEntity entity, Level level, Vec3 pos) {
		ImprovedExplosion explosion2 = new ImprovedExplosion(level, pos, 80).setExplosionFinishWork(e -> netherExplosionStep3(entity, level, pos));
		explosion2.doImprovedBlockExplosion(1f, 0.2f, true, false, new FilterAirExplosionRule(
			new FilterCollidableExplosionRule(new BlockExplosionRule(Blocks.NETHERRACK.defaultBlockState()))
		));
	}
	
	private static void netherExplosionStep3(IExplosiveEntity entity, Level level, Vec3 pos) {
		RandomSource random = level.getRandom();
		
		int biome = random.nextInt(3);
		BlockState surface;
		if (biome == 0) {
			surface = Blocks.CRIMSON_NYLIUM.defaultBlockState();
		} else if (biome == 1) {
			surface = Blocks.WARPED_NYLIUM.defaultBlockState();
		} else {
			surface = Blocks.SOUL_SAND.defaultBlockState();
		}
		
		ExplosionHelper.legacyCylindricalExplosion(level, pos.subtract(0d, 40d, 0d), 40, 20, 5f, new FilterOffYExplosionRule(-20, -4, 
			LogicExplosionRule.not(
				new FilterAirExplosionRule(new AlwaysExplosionRule()), 
				new BlockExplosionRule(Blocks.LAVA.defaultBlockState())
			)
		));
		
		ImprovedExplosion explosion3 = new ImprovedExplosion(level, pos, 80).setExplosionFinishWork(e -> netherExplosionStep4(entity, level, random, pos, biome, surface));
		explosion3.doImprovedBlockExplosion(1f, 0.2f, false, false, new FilterOffYExplosionRule(-80, -10, 
			FilterBlockExplosionRule.applyOnlyWhen(Blocks.NETHERRACK, 
				new FilterSurfaceExplosionRule(true, new BlockExplosionRule(surface))
			)
		));
	}
	
	private static void netherExplosionStep4(IExplosiveEntity entity, Level level, RandomSource random, Vec3 pos, int biome, BlockState surface) {
		if (level instanceof ServerLevel server) {
			List<Block> validFeatureSurfaces = List.of(Blocks.NETHERRACK, Blocks.SOUL_SOIL, surface.getBlock());
			
			Registry<ConfiguredFeature<?, ?>> configuredFeatures = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
			ConfiguredFeature<?, ?> glowstone = configuredFeatures.get(NetherFeatures.GLOWSTONE_EXTRA);
			Map<ConfiguredFeature<?, ?>, Float> features = new HashMap<>();
			if (biome == 0) {
				features.put(configuredFeatures.get(NetherFeatures.PATCH_CRIMSON_ROOTS), 0.02f);
				features.put(configuredFeatures.get(TreeFeatures.CRIMSON_FUNGUS), 0.04f);
				features.put(configuredFeatures.get(NetherFeatures.CRIMSON_FOREST_VEGETATION_BONEMEAL), 0.02f);
			} else if (biome == 1) {
				features.put(configuredFeatures.get(NetherFeatures.NETHER_SPROUTS_BONEMEAL), 0.02f);
				features.put(configuredFeatures.get(NetherFeatures.TWISTING_VINES_BONEMEAL), 0.01f);
				features.put(configuredFeatures.get(TreeFeatures.WARPED_FUNGUS), 0.04f);
				features.put(configuredFeatures.get(NetherFeatures.WARPED_FOREST_VEGETATION_BONEMEAL), 0.02f);
			} else if (biome == 2) {
				features.put(new ConfiguredFeature<>(Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(Blocks.SOUL_SOIL), BlockPredicate.matchesBlocks(validFeatureSurfaces), UniformInt.of(3, 6), 2)), 0.025f);
				features.put(configuredFeatures.get(NetherFeatures.PATCH_SOUL_FIRE), 0.01f);
				features.put(new ConfiguredFeature<>(new NetherFossilFeature(entity), NoneFeatureConfiguration.INSTANCE), 0.001f);
			}
			
			ChunkGenerator chunkGenerator = server.getChunkSource().getGenerator();
			BlockPos centerPos = BlockPos.containing(pos);
			ImprovedExplosion explosion4 = new ImprovedExplosion(server, null, null, pos.x, pos.y, pos.z, 150).setCustomExplosionEffect((lev, center, blockpos, state) -> {
				if (validFeatureSurfaces.contains(state.getBlock())) { 
					BlockPos posAbove = blockpos.above();
					BlockState stateAbove = lev.getBlockState(posAbove);
					if (stateAbove.isAir() && blockpos.getY() - centerPos.getY() <= -10) {
						for (Map.Entry<ConfiguredFeature<?, ?>, Float> entry : features.entrySet()) {
							if (random.nextFloat() < entry.getValue()) {
								entry.getKey().place(server, chunkGenerator, random, posAbove);
							}
						}
					}
					
					BlockPos posBelow = blockpos.below();
					BlockState stateBelow = lev.getBlockState(posBelow);
					if (stateBelow.isAir() && blockpos.getY() - centerPos.getY() >= 10 && random.nextFloat() < 0.005f) {
						glowstone.place(server, chunkGenerator, random, posBelow);
					}
				}
			});
			explosion4.doImprovedBlockExplosion(1f, 0.2f, true, false, null);
		}
	}
	
	public static class NetherFossil extends NetherFossilStructure {
		
		private final BlockPos pos;
		
		public NetherFossil(IExplosiveEntity entity, BlockPos pos) {
			super(StructureState.settings(entity, GenerationStep.Decoration.UNDERGROUND_DECORATION, TerrainAdjustment.BEARD_THIN), ConstantHeight.ZERO);
			this.pos = pos;
		}

		@Override
		public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext ctx) {
			return Optional.of(new Structure.GenerationStub(pos, p -> {
				NetherFossilPieces.addPieces(ctx.structureTemplateManager(), p, ctx.random(), pos);
			}));
		}
	}
	
	public static class NetherFossilFeature extends Feature<NoneFeatureConfiguration> {
		
		private final IExplosiveEntity entity;
		
		public NetherFossilFeature(IExplosiveEntity entity) {
			super(NoneFeatureConfiguration.CODEC);
			this.entity = entity;
		}

		@Override
		public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
			if (ctx.level() instanceof ServerLevel server) {
				ChunkPos chunkPos = new ChunkPos(ctx.origin());
				Structure structure = new NetherFossil(entity, ctx.origin());
				StructureStart start = structure.generate(server.registryAccess(), ctx.chunkGenerator(), ctx.chunkGenerator().getBiomeSource(), server.getChunkSource().randomState(), server.getStructureManager(), server.getRandom().nextLong(), chunkPos, 0, server, h -> true);
				start.placeInChunk(server, server.structureManager(), ctx.chunkGenerator(), server.getRandom(), BoundingBox.infinite(), chunkPos);
			}
			return true;
		}
	}
}
