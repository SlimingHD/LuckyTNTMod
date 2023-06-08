package luckytnt.tnteffects;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.google.common.base.Predicate;

import luckytnt.block.StructureTNTBlock;
import luckytnt.registry.BlockRegistry;
import luckytnt.util.StructureStates;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BastionPieces;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.SinglePieceStructure;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.DesertPyramidPiece;
import net.minecraft.world.level.levelgen.structure.structures.DesertPyramidStructure;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentPieces;
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class StructureTNTEffect extends PrimedTNTEffect {
	
	public Predicate<Holder<Biome>> PREDICATE = (holder) -> {
		return true;
	};

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		String value = ent.getPersistentData().getString("structure");
		if(ent.getLevel() instanceof ServerLevel sLevel) {
			RegistryAccess rAccess = sLevel.registryAccess();
			ChunkGenerator chunkGenerator = sLevel.getChunkSource().getGenerator();
			BiomeSource biomeSource = sLevel.getChunkSource().getGenerator().getBiomeSource();
			StructureTemplateManager sManager = sLevel.getStructureManager();
			StructureManager sFManager = sLevel.structureManager();
			BoundingBox bb = new BoundingBox((int)ent.x() - 150, (int)ent.y() - 150, (int)ent.z() - 150, (int)ent.x() + 150, (int)ent.y() + 150, (int)ent.z() + 150);
			ChunkPos chunkPosition = ((Entity)ent).chunkPosition();
			RandomSource random = RandomSource.create();
			RandomState randomState = sLevel.getChunkSource().randomState();
			
			Registry<Structure> registry = sLevel.registryAccess().registryOrThrow(Registries.STRUCTURE);
			Registry<StructureTemplatePool> pools = sLevel.registryAccess().registryOrThrow(Registries.TEMPLATE_POOL);

			Holder<StructureTemplatePool> pool = pools.wrapAsHolder(pools.get(BastionPieces.START));
			
			Structure pillager_outpost = registry.get(BuiltinStructures.PILLAGER_OUTPOST);
			Structure mansion = registry.get(BuiltinStructures.WOODLAND_MANSION);
			Structure jungle_pyramid = registry.get(BuiltinStructures.JUNGLE_TEMPLE);
			Structure desert_pyramid = new DesertPyramid(null);
			Structure stronghold = registry.get(BuiltinStructures.STRONGHOLD);
			Structure monument = new Monument(null);
			Structure fortress = registry.get(BuiltinStructures.FORTRESS);
			Structure end_city = registry.get(BuiltinStructures.END_CITY);
			Structure bastion = new JigsawStructure(null, pool, 6, ConstantHeight.of(VerticalAnchor.absolute((int)ent.y())), false);
			Structure village_plains = registry.get(BuiltinStructures.VILLAGE_PLAINS);
			Structure village_desert = registry.get(BuiltinStructures.VILLAGE_DESERT);
			Structure village_savanna = registry.get(BuiltinStructures.VILLAGE_SAVANNA);
			Structure village_snowy = registry.get(BuiltinStructures.VILLAGE_SNOWY);
			Structure village_taiga = registry.get(BuiltinStructures.VILLAGE_TAIGA);
			
			if(value.equals("pillager_outpost")) {
				StructureStart start = pillager_outpost.generate(rAccess, chunkGenerator, biomeSource, randomState, sManager, sLevel.getSeed(), chunkPosition, 20, sLevel, PREDICATE);
				start.placeInChunk(sLevel, sFManager, chunkGenerator, random, bb, chunkPosition);
			}
			else if(value.equals("mansion")) {
				StructureStart start = mansion.generate(rAccess, chunkGenerator, biomeSource, randomState, sManager, sLevel.getSeed(), chunkPosition, 20, sLevel, PREDICATE);
				start.placeInChunk(sLevel, sFManager, chunkGenerator, random, bb, chunkPosition);
			}
			else if(value.equals("jungle_pyramid")) {
				StructureStart start = jungle_pyramid.generate(rAccess, chunkGenerator, biomeSource, randomState, sManager, sLevel.getSeed(), chunkPosition, 20, sLevel, PREDICATE);
				start.placeInChunk(sLevel, sFManager, chunkGenerator, random, bb, chunkPosition);
			}
			else if(value.equals("desert_pyramid")) {
				StructureStart start = desert_pyramid.generate(rAccess, chunkGenerator, biomeSource, randomState, sManager, sLevel.getSeed(), chunkPosition, 20, sLevel, PREDICATE);
				start.placeInChunk(sLevel, sFManager, chunkGenerator, random, bb, chunkPosition);
			}
			else if(value.equals("stronghold")) {
				StructureStart start = stronghold.generate(rAccess, chunkGenerator, biomeSource, randomState, sManager, sLevel.getSeed(), chunkPosition, 20, sLevel, PREDICATE);
				start.placeInChunk(sLevel, sFManager, chunkGenerator, random, bb, chunkPosition);
			}
			else if(value.equals("monument")) {
				StructureStart start = monument.generate(rAccess, chunkGenerator, biomeSource, randomState, sManager, sLevel.getSeed(), chunkPosition, 20, sLevel, PREDICATE);
				start.placeInChunk(sLevel, sFManager, chunkGenerator, random, bb, chunkPosition);
			}
			else if(value.equals("fortress")) {
				StructureStart start = fortress.generate(rAccess, chunkGenerator, biomeSource, randomState, sManager, sLevel.getSeed(), chunkPosition, 20, sLevel, PREDICATE);
				start.placeInChunk(sLevel, sFManager, chunkGenerator, random, bb, chunkPosition);
			}
			else if(value.equals("end_city")) {
				StructureStart start = end_city.generate(rAccess, chunkGenerator, biomeSource, randomState, sManager, sLevel.getSeed(), chunkPosition, 20, sLevel, PREDICATE);
				start.placeInChunk(sLevel, sFManager, chunkGenerator, random, bb, chunkPosition);
			}
			else if(value.equals("bastion")) {
				StructureStart start = bastion.generate(rAccess, chunkGenerator, biomeSource, randomState, sManager, sLevel.getSeed(), chunkPosition, 20, sLevel, PREDICATE);
				start.placeInChunk(sLevel, sFManager, chunkGenerator, random, bb, chunkPosition);
			}
			else if(value.equals("village_plains")) {
				JungleTNTEffect.replaceNonSolidBlockOrVegetationWithAir(ent, 100, 10, true);
				StructureStart start = village_plains.generate(rAccess, chunkGenerator, biomeSource, randomState, sManager, sLevel.getSeed(), chunkPosition, 20, sLevel, PREDICATE);
				start.placeInChunk(sLevel, sFManager, chunkGenerator, random, bb, chunkPosition);
			}
			else if(value.equals("village_desert")) {
				JungleTNTEffect.replaceNonSolidBlockOrVegetationWithAir(ent, 100, 10, true);
				StructureStart start = village_desert.generate(rAccess, chunkGenerator, biomeSource, randomState, sManager, sLevel.getSeed(), chunkPosition, 20, sLevel, PREDICATE);
				start.placeInChunk(sLevel, sFManager, chunkGenerator, random, bb, chunkPosition);
			}
			else if(value.equals("village_savanna")) {
				JungleTNTEffect.replaceNonSolidBlockOrVegetationWithAir(ent, 100, 10, true);
				StructureStart start = village_savanna.generate(rAccess, chunkGenerator, biomeSource, randomState, sManager, sLevel.getSeed(), chunkPosition, 20, sLevel, PREDICATE);
				start.placeInChunk(sLevel, sFManager, chunkGenerator, random, bb, chunkPosition);
			}
			else if(value.equals("village_snowy")) {
				JungleTNTEffect.replaceNonSolidBlockOrVegetationWithAir(ent, 100, 10, true);
				StructureStart start = village_snowy.generate(rAccess, chunkGenerator, biomeSource, randomState, sManager, sLevel.getSeed(), chunkPosition, 20, sLevel, PREDICATE);
				start.placeInChunk(sLevel, sFManager, chunkGenerator, random, bb, chunkPosition);
			}
			else if(value.equals("village_taiga")) {
				JungleTNTEffect.replaceNonSolidBlockOrVegetationWithAir(ent, 100, 10, true);
				StructureStart start = village_taiga.generate(rAccess, chunkGenerator, biomeSource, randomState, sManager, sLevel.getSeed(), chunkPosition, 20, sLevel, PREDICATE);
				start.placeInChunk(sLevel, sFManager, chunkGenerator, random, bb, chunkPosition);
			}
		}
	}
	
	@Override
	public BlockState getBlockState(IExplosiveEntity ent) {
		boolean bool = false;
		List<StructureStates> list = Arrays.asList(StructureStates.values());
		for(StructureStates state : list) {
			if(ent.getPersistentData().getString("structure").equals(state.getSerializedName())) {
				bool = true;
			}
		}
		if(bool) {
			return BlockRegistry.STRUCTURE_TNT.get().defaultBlockState().setValue(StructureTNTBlock.STRUCTURE, StructureTNTBlock.STRUCTURE.getValue(ent.getPersistentData().getString("structure")).get());
		}
		return BlockRegistry.STRUCTURE_TNT.get().defaultBlockState();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 160;
	}
	
	public static class DesertPyramid extends DesertPyramidStructure {

		public DesertPyramid(Structure.StructureSettings settings) {
			super(settings);
		}
		
		@Override
		public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext ctx) {
			return onTopOfChunkCenter(ctx, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (p) -> {
				generatePieces(p, ctx);
			});
		}
		
		private void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext ctx) {
			ChunkPos chunkpos = ctx.chunkPos();
			SinglePieceStructure.PieceConstructor constructor = DesertPyramidPiece::new;
			builder.addPiece(constructor.construct(ctx.random(), chunkpos.getMinBlockX(), chunkpos.getMinBlockZ()));
		}
	}
	
	public static class Monument extends OceanMonumentStructure {

		public Monument(StructureSettings settings) {
			super(settings);
		}
		
		@Override
		public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext ctx) {
			return onTopOfChunkCenter(ctx, Heightmap.Types.OCEAN_FLOOR_WG, (p) -> {
				generatePieces(p, ctx);
			});
		}

		private static StructurePiece createTopPiece(ChunkPos pos, WorldgenRandom rand) {
			int i = pos.getMinBlockX() - 29;
			int j = pos.getMinBlockZ() - 29;
			Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
			return new OceanMonumentPieces.MonumentBuilding(rand, i, j, direction);
		}

		private static void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext ctx) {
			builder.addPiece(createTopPiece(ctx.chunkPos(), ctx.random()));
		}
	}
}
