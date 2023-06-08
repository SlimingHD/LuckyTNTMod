package luckytnt.tnteffects;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.util.Materials;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
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
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.structures.NetherFossilPieces;
import net.minecraft.world.level.levelgen.structure.structures.NetherFossilStructure;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

public class NetherTNTEffect extends PrimedTNTEffect {
	public List<Block> list = List.of(Blocks.NETHERRACK, Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM, Blocks.SOUL_SAND, Blocks.SOUL_SOIL);
	
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ExplosionHelper.doSphericalExplosion(ent.getLevel(), new Vec3(ent.x(), 0, ent.z()), 40, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(level)) <= 200) {
					level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				}
			}
		});
		
		ImprovedExplosion explosion = new ImprovedExplosion(ent.getLevel(), new Vec3(ent.x(), 0, ent.z()), 100);
		explosion.doBlockExplosion(1f, 0.8f, 1f, 0.2f, false, true);
		
		ExplosionHelper.doSphericalExplosion(ent.getLevel(), new Vec3(ent.x(), 0, ent.z()), 100, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if((state.getBlock() instanceof LiquidBlock || state.getBlock() instanceof BubbleColumnBlock || Materials.isWaterPlant(state)) && pos.getY() <= 50) {
					level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				}
				
				if(state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) && pos.getY() <= 50) {
					level.setBlock(pos, state.setValue(BlockStateProperties.WATERLOGGED, false), 3);
				}
			}
		});
		
		ImprovedExplosion explosion3 = new ImprovedExplosion(ent.getLevel(), new Vec3(ent.x(), 0, ent.z()), 80);
		explosion3.doBlockExplosion(1f, 0.8f, 1f, 0.2f, true, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getExplosionResistance(level, pos, explosion3) <= 200 && !state.getCollisionShape(level, pos, CollisionContext.empty()).isEmpty()) {
					level.setBlock(pos, Blocks.NETHERRACK.defaultBlockState(), 3);
				}
			}
		});
		
		ExplosionHelper.doCylindricalExplosion(ent.getLevel(), new Vec3(ent.x(), -40, ent.z()), 40, 20, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(pos.getY() <= -44 && state.isAir()) {
					level.setBlock(pos, Blocks.LAVA.defaultBlockState(), 3);
				}
			}
		});
		
		int biome = new Random().nextInt(3);
		
		ImprovedExplosion explosion4 = new ImprovedExplosion(ent.getLevel(), new Vec3(ent.x(), 0, ent.z()), 80);
		explosion4.doBlockExplosion(1f, 0.8f, 1f, 0.2f, true, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				BlockPos posAbove = pos.above();
				BlockState stateAbove = level.getBlockState(posAbove);
				
				if(stateAbove.isAir() && state.getBlock() == Blocks.NETHERRACK && pos.getY() <= -10) {
					if(biome == 0) {
						level.setBlock(pos, Blocks.CRIMSON_NYLIUM.defaultBlockState(), 3);
					}
					
					if(biome == 1) {
						level.setBlock(pos, Blocks.WARPED_NYLIUM.defaultBlockState(), 3);
					}
					
					if(biome == 2) {
						level.setBlock(pos, Blocks.SOUL_SAND.defaultBlockState(), 3);
					}
				}
			}
		});
		
		ImprovedExplosion explosion5 = new ImprovedExplosion(ent.getLevel(), new Vec3(ent.x(), 0, ent.z()), 150);
		explosion5.doBlockExplosion(1f, 0.8f, 1f, 0.2f, true, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				BlockPos posBelow = pos.below();
				BlockState stateBelow = level.getBlockState(posBelow);
				BlockPos posAbove = pos.above();
				BlockState stateAbove = level.getBlockState(posAbove);
				
				if(list.contains(state.getBlock()) && stateAbove.isAir() && pos.getY() <= -10) {
					Registry<ConfiguredFeature<?, ?>> registry = ent.getLevel().registryAccess().registry(Registries.CONFIGURED_FEATURE).get();
					
					if(biome == 0) {
						if(Math.random() < 0.02D) {
							registry.get(NetherFeatures.PATCH_CRIMSON_ROOTS).place((WorldGenLevel)ent.getLevel(), ((ServerLevel)ent.getLevel()).getChunkSource().getGenerator(), RandomSource.create(), posAbove);
						}
						if(Math.random() < 0.04D) {
							registry.get(TreeFeatures.CRIMSON_FUNGUS).place((WorldGenLevel)ent.getLevel(), ((ServerLevel)ent.getLevel()).getChunkSource().getGenerator(), RandomSource.create(), posAbove);
						}
						if(Math.random() < 0.02D) {
							registry.get(NetherFeatures.CRIMSON_FOREST_VEGETATION_BONEMEAL).place((WorldGenLevel)ent.getLevel(), ((ServerLevel)ent.getLevel()).getChunkSource().getGenerator(), RandomSource.create(), posAbove);
						}
					}
					
					if(biome == 1) {
						if(Math.random() < 0.02D) {
							registry.get(NetherFeatures.NETHER_SPROUTS_BONEMEAL).place((WorldGenLevel)ent.getLevel(), ((ServerLevel)ent.getLevel()).getChunkSource().getGenerator(), RandomSource.create(), posAbove);
						}
						if(Math.random() < 0.01D) {
							registry.get(NetherFeatures.TWISTING_VINES_BONEMEAL).place((WorldGenLevel)ent.getLevel(), ((ServerLevel)ent.getLevel()).getChunkSource().getGenerator(), RandomSource.create(), posAbove);
						}
						if(Math.random() < 0.04D) {
							registry.get(TreeFeatures.WARPED_FUNGUS).place((WorldGenLevel)ent.getLevel(), ((ServerLevel)ent.getLevel()).getChunkSource().getGenerator(), RandomSource.create(), posAbove);
						}
						if(Math.random() < 0.02D) {
							registry.get(NetherFeatures.WARPED_FOREST_VEGETATION_BONEMEAL).place((WorldGenLevel)ent.getLevel(), ((ServerLevel)ent.getLevel()).getChunkSource().getGenerator(), RandomSource.create(), posAbove);
						}
					}
					
					if(biome == 2) {
						if(Math.random() < 0.025D) {
							DiskConfiguration config = new DiskConfiguration(RuleBasedBlockStateProvider.simple(Blocks.SOUL_SOIL), BlockPredicate.matchesBlocks(List.of(Blocks.NETHERRACK, Blocks.SOUL_SAND, Blocks.SOUL_SOIL)), UniformInt.of(3, 6), 2);
							Feature.DISK.place(config, (WorldGenLevel)ent.getLevel(), ((ServerLevel)ent.getLevel()).getChunkSource().getGenerator(), RandomSource.create(), pos);
						}
						if(Math.random() < 0.01D) {
							registry.get(NetherFeatures.PATCH_SOUL_FIRE).place((WorldGenLevel)ent.getLevel(), ((ServerLevel)ent.getLevel()).getChunkSource().getGenerator(), RandomSource.create(), posAbove);
						}
						if(Math.random() < 0.001D) {
							Structure structure = new NetherFossil(null, ConstantHeight.of(VerticalAnchor.absolute(pos.getY())), level);
							StructureStart start = structure.generate(ent.getLevel().registryAccess(), ((ServerLevel)ent.getLevel()).getChunkSource().getGenerator(), ((ServerLevel)ent.getLevel()).getChunkSource().getGenerator().getBiomeSource(), ((ServerLevel)ent.getLevel()).getChunkSource().randomState(), ((ServerLevel)ent.getLevel()).getStructureManager(), ((ServerLevel)ent.getLevel()).getSeed(), new ChunkPos(posAbove), 20, level, holder -> true);
							start.placeInChunk((WorldGenLevel)ent.getLevel(), ((ServerLevel)ent.getLevel()).structureManager(), ((ServerLevel)ent.getLevel()).getChunkSource().getGenerator(), RandomSource.create(), new BoundingBox(pos.getX() - 150, pos.getY() - 150, pos.getZ() - 150, pos.getX() + 150, pos.getY() + 150, pos.getZ() + 150), new ChunkPos(posAbove));
						}
					}
				}
				
				if(stateBelow.isAir() && state.getBlock() == Blocks.NETHERRACK && pos.getY() >= 10 && Math.random() < 0.005D) {
					Registry<ConfiguredFeature<?, ?>> registry = ent.getLevel().registryAccess().registry(Registries.CONFIGURED_FEATURE).get();
					registry.get(NetherFeatures.GLOWSTONE_EXTRA).place((WorldGenLevel)ent.getLevel(), ((ServerLevel)ent.getLevel()).getChunkSource().getGenerator(), RandomSource.create(), posBelow);
				}
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		if(ent.getTNTFuse() % 3 == 0) {
			for(double d = 0D; d <= 1.5D; d += 0.1D) {
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.75f), ent.x() + 0.5D, ent.y() + 1.1D + d, ent.z(), 0, 0, 0);
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.75f), ent.x() + 0.4D, ent.y() + 1.1D + d, ent.z(), 0, 0, 0);
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.75f), ent.x() - 0.5D, ent.y() + 1.1D + d, ent.z(), 0, 0, 0);
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.75f), ent.x() - 0.4D, ent.y() + 1.1D + d, ent.z(), 0, 0, 0);
			}
			for(double d = 0D; d <= 1D; d += 0.1D) {
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.75f), ent.x() + 0.5D - d, ent.y() + 1.1D, ent.z(), 0, 0, 0);
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.75f), ent.x() + 0.5D - d, ent.y() + 1.2D, ent.z(), 0, 0, 0);
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.75f), ent.x() + 0.5D - d, ent.y() + 2.6D, ent.z(), 0, 0, 0);
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.75f), ent.x() + 0.5D - d, ent.y() + 2.5D, ent.z(), 0, 0, 0);
			}
			for(double x = -0.3D; x <= 0.3D; x += 0.1D) {
				for(double y = 0.2D; y <= 1.3D; y += 0.1D) {
					ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.5f, 0f, 1f), 0.75f), ent.x() + x + 0.05D, ent.y() + 1.1D + y, ent.z(), 0, 0, 0);
				}
			}
		}
	} 
	
	@Override
	public Block getBlock() {
		return BlockRegistry.NETHER_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 180;
	}
	
	public class NetherFossil extends NetherFossilStructure {
		private final Level level;
		
		public NetherFossil(Structure.StructureSettings settings, HeightProvider height, Level level) {
			super(settings, height);
			this.level = level;
		}

		public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext ctx) {
			WorldgenRandom worldgenrandom = ctx.random();
			int i = ctx.chunkPos().getMinBlockX() + worldgenrandom.nextInt(16);
			int j = ctx.chunkPos().getMinBlockZ() + worldgenrandom.nextInt(16);
			int k = -64;
			int l = 0;
			
			while (l > k) {
				BlockState blockstate = level.getBlockState(new BlockPos(i, l, j));
				--l;
				BlockState blockstate1 = level.getBlockState(new BlockPos(i, l, j));
				if (blockstate.isAir() && (blockstate1.is(Blocks.SOUL_SAND) || blockstate1.is(Blocks.SOUL_SOIL) || blockstate1.is(Blocks.NETHERRACK))) {
					break;
				}
			}

			if (l <= k) {
				return Optional.empty();
			} else {
				BlockPos blockpos = new BlockPos(i, l, j);
				return Optional.of(new Structure.GenerationStub(blockpos, (accessor) -> {
					NetherFossilPieces.addPieces(ctx.structureTemplateManager(), accessor, worldgenrandom, blockpos);
				}));
			}
		}
	}
}
