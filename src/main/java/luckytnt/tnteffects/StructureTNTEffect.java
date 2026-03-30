package luckytnt.tnteffects;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;

import luckytnt.block.StructureTNTBlock;
import luckytnt.registry.BlockRegistry;
import luckytnt.util.StructureState;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.SinglePieceStructure;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.structures.DesertPyramidPiece;
import net.minecraft.world.level.levelgen.structure.structures.DesertPyramidStructure;
import net.minecraft.world.level.levelgen.structure.structures.EndCityPieces;
import net.minecraft.world.level.levelgen.structure.structures.EndCityStructure;
import net.minecraft.world.level.levelgen.structure.structures.JungleTemplePiece;
import net.minecraft.world.level.levelgen.structure.structures.JungleTempleStructure;
import net.minecraft.world.level.levelgen.structure.structures.MineshaftPieces;
import net.minecraft.world.level.levelgen.structure.structures.MineshaftStructure;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressPieces;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressStructure;
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentPieces;
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentStructure;
import net.minecraft.world.level.levelgen.structure.structures.OceanRuinPieces;
import net.minecraft.world.level.levelgen.structure.structures.OceanRuinStructure;
import net.minecraft.world.level.levelgen.structure.structures.ShipwreckPieces;
import net.minecraft.world.level.levelgen.structure.structures.ShipwreckStructure;
import net.minecraft.world.level.levelgen.structure.structures.StrongholdPieces;
import net.minecraft.world.level.levelgen.structure.structures.StrongholdStructure;
import net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionPieces;
import net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionStructure;

public class StructureTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		if (entity.getLevel() instanceof ServerLevel server) {
			StructureState state = StructureState.byName(entity.getPersistentData().getString("structure"));
			
			ServerChunkCache chunkSource = server.getChunkSource();
			ChunkGenerator chunkGenerator = chunkSource.getGenerator();
			ChunkPos chunkPos = new ChunkPos(toBlockPos(entity.getPos()));
			
			Structure structure = state.getStructure(entity);
			StructureStart start = structure.generate(server.registryAccess(), chunkGenerator, chunkGenerator.getBiomeSource(), chunkSource.randomState(), server.getStructureManager(), server.getSeed(), chunkPos, 0, server, b -> true);
			start.placeInChunk(server, server.structureManager(), chunkGenerator, server.getRandom(), BoundingBox.infinite(), chunkPos);
		}
	}
	
	@Override
	public BlockState getBlockState(IExplosiveEntity entity) {
		StructureState structure = StructureState.byName(entity.getPersistentData().getString("structure"));
		return BlockRegistry.STRUCTURE_TNT.get().defaultBlockState().setValue(StructureTNTBlock.STRUCTURE, structure);
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
	
	public static class Mineshaft extends MineshaftStructure {
		
		private final int x, y, z;
		private final MineshaftStructure.Type type;
		
		private Mineshaft(Structure.StructureSettings settings, IExplosiveEntity entity, MineshaftStructure.Type mineshaftType) {
			super(settings, mineshaftType);
			x = Mth.floor(entity.x());
			y = Mth.floor(entity.y());
			z = Mth.floor(entity.z());
			type = mineshaftType;
		}
		
		public Mineshaft(Structure.StructureSettings settings, IExplosiveEntity entity) {
			this(settings, entity, entity.getLevel().getRandom().nextBoolean() ? MineshaftStructure.Type.NORMAL : MineshaftStructure.Type.MESA);
		}
		
		@Override
		public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext ctx) {
			StructurePiecesBuilder builder = new StructurePiecesBuilder();
			generatePieces(builder, ctx);
			return Optional.of(new Structure.GenerationStub(new BlockPos(x, y, z), Either.right(builder)));
		}

		private void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext ctx) {
			WorldgenRandom random = ctx.random();
			MineshaftPieces.MineShaftRoom mineshaftroom = new MineshaftPieces.MineShaftRoom(0, random, x, z, type);
			builder.addPiece(mineshaftroom);
			mineshaftroom.addChildren(mineshaftroom, builder, random);
		}
	}
	
	public static class WoodlandMansion extends WoodlandMansionStructure {

		private final int x, y, z;
		
		public WoodlandMansion(Structure.StructureSettings settings, IExplosiveEntity entity) {
			super(settings);
			x = Mth.floor(entity.x());
			y = Mth.floor(entity.y());
			z = Mth.floor(entity.z());
		}
		
		@Override
		public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext ctx) {
			Rotation rotation = Rotation.getRandom(ctx.random());
			BlockPos blockpos = new BlockPos(x, y, z);
			return Optional.of(new Structure.GenerationStub(blockpos, p -> {
				this.generatePieces(p, ctx, blockpos, rotation);
			}));
		}

		private void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext ctx, BlockPos pos, Rotation rotation) {
			List<WoodlandMansionPieces.WoodlandMansionPiece> list = Lists.newLinkedList();
			WoodlandMansionPieces.generateMansion(ctx.structureTemplateManager(), pos, rotation, list, ctx.random());
			list.forEach(builder::addPiece);
		}
	}
	
	public static class JungleTemple extends JungleTempleStructure {

		private final int x, y, z;
		
		public JungleTemple(Structure.StructureSettings settings, IExplosiveEntity entity) {
			super(settings);
			x = Mth.floor(entity.x());
			y = Mth.floor(entity.y());
			z = Mth.floor(entity.z());
		}
		
		@Override
		public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext ctx) {
			return Optional.of(new Structure.GenerationStub(new BlockPos(x, y, z), p -> {
				generatePieces(p, ctx);
			}));
		}
		
		private void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext ctx) {
			SinglePieceStructure.PieceConstructor constructor = JungleTemplePiece::new;
			builder.addPiece(constructor.construct(ctx.random(), x, z));
		}
	}
	
	public static class DesertPyramid extends DesertPyramidStructure {
		
		private final int x, y, z;
		
		public DesertPyramid(Structure.StructureSettings settings, IExplosiveEntity entity) {
			super(settings);
			x = Mth.floor(entity.x());
			y = Mth.floor(entity.y());
			z = Mth.floor(entity.z());
		}

		@Override
		public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext ctx) {
			return Optional.of(new Structure.GenerationStub(new BlockPos(x, y, z), p -> {
				generatePieces(p, ctx);
			}));
		}

		private void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext ctx) {
			SinglePieceStructure.PieceConstructor constructor = DesertPyramidPiece::new;
			builder.addPiece(constructor.construct(ctx.random(), x, z));
		}
	}
	
	public static class Shipwreck extends ShipwreckStructure {
		
		private final int x, y, z;
		
		public Shipwreck(Structure.StructureSettings settings, IExplosiveEntity entity) {
			super(settings, entity.getLevel().getRandom().nextBoolean());
			x = Mth.floor(entity.x());
			y = Mth.floor(entity.y());
			z = Mth.floor(entity.z());
		}
		
		@Override
		public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext ctx) {
			BlockPos pos = new BlockPos(x, y, z);
			return Optional.of(new Structure.GenerationStub(pos, p -> {
				ShipwreckPieces.addPieces(ctx.structureTemplateManager(), pos, Rotation.getRandom(ctx.random()), p, ctx.random(), isBeached);
			}));
		}
	}
	
	public static class Stronghold extends StrongholdStructure {

		private final int x, y, z;
		
		public Stronghold(Structure.StructureSettings settings, IExplosiveEntity entity) {
			super(settings);
			x = Mth.floor(entity.x());
			y = Mth.floor(entity.y());
			z = Mth.floor(entity.z());
		}
		
		@Override
		public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext ctx) {
			return Optional.of(new Structure.GenerationStub(new BlockPos(x, y, z), p -> {
				generatePieces(p, ctx);
			}));
		}

		private void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext ctx) {
			int i = 0;

			StrongholdPieces.StartPiece startPiece;
			do {
				builder.clear();
				ctx.random().setLargeFeatureSeed(ctx.seed() + i++, ctx.chunkPos().x, ctx.chunkPos().z);
				StrongholdPieces.resetPieces();
				startPiece = new StrongholdPieces.StartPiece(ctx.random(), x, z);
				builder.addPiece(startPiece);
				startPiece.addChildren(startPiece, builder, ctx.random());
				List<StructurePiece> list = startPiece.pendingChildren;

				while (!list.isEmpty()) {
					int j = ctx.random().nextInt(list.size());
					StructurePiece structurepiece = list.remove(j);
					structurepiece.addChildren(startPiece, builder, ctx.random());
				}
			} while (builder.isEmpty() || startPiece.portalRoomPiece == null);
		}
	}
	
	public static class Monument extends OceanMonumentStructure {
		
		private final int x, y, z;
		
		public Monument(Structure.StructureSettings settings, IExplosiveEntity entity) {
			super(settings);
			x = Mth.floor(entity.x());
			y = Mth.floor(entity.y());
			z = Mth.floor(entity.z());
		}

		@Override
		public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext ctx) {
			return Optional.of(new Structure.GenerationStub(new BlockPos(x, y, z), p -> {
				generatePieces(p, ctx);
			}));
		}

		private void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext ctx) {
			builder.addPiece(createTopPiece(ctx.chunkPos(), ctx.random()));
		}

		private StructurePiece createTopPiece(ChunkPos pos, WorldgenRandom rand) {
			int i = x - 29;
			int j = z - 29;
			Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
			return new OceanMonumentPieces.MonumentBuilding(rand, i, j, direction);
		}
	}
	
	public static class OceanRuin extends OceanRuinStructure {
		
		private final int x, y, z;
		
		public OceanRuin(Structure.StructureSettings settings, IExplosiveEntity entity) {
			super(settings, entity.getLevel().getRandom().nextBoolean() ? OceanRuinStructure.Type.COLD : OceanRuinStructure.Type.WARM, 1f, 1f);
			x = Mth.floor(entity.x());
			y = Mth.floor(entity.y());
			z = Mth.floor(entity.z());
		}
		
		@Override
		public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext ctx) {
			BlockPos pos = new BlockPos(x, y, z);
			return Optional.of(new Structure.GenerationStub(pos, p -> {
				OceanRuinPieces.addPieces(ctx.structureTemplateManager(), pos, Rotation.getRandom(ctx.random()), p, ctx.random(), this);
			}));
		}
	}
	
	public static class Fortress extends NetherFortressStructure {

		private final int x, y, z;
		
		public Fortress(Structure.StructureSettings settings, IExplosiveEntity entity) {
			super(settings);
			x = Mth.floor(entity.x());
			y = Mth.floor(entity.y());
			z = Mth.floor(entity.z());
		}
		
		@Override
		public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext ctx) {
			return Optional.of(new Structure.GenerationStub(new BlockPos(x, y, z), p -> {
				generatePieces(p, ctx);
			}));
		}

		private void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext ctx) {
			NetherFortressPieces.StartPiece startPiece = new NetherFortressPieces.StartPiece(ctx.random(), x, z);
			builder.addPiece(startPiece);
			startPiece.addChildren(startPiece, builder, ctx.random());
			List<StructurePiece> list = startPiece.pendingChildren;

			while (!list.isEmpty()) {
				int i = ctx.random().nextInt(list.size());
				StructurePiece structurepiece = list.remove(i);
				structurepiece.addChildren(startPiece, builder, ctx.random());
			}
		}
	}
	
	public static class EndCity extends EndCityStructure {

		private final int x, y, z;
		
		public EndCity(Structure.StructureSettings settings, IExplosiveEntity entity) {
			super(settings);
			x = Mth.floor(entity.x());
			y = Mth.floor(entity.y());
			z = Mth.floor(entity.z());
		}
		
		@Override
		public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext ctx) {
			Rotation rotation = Rotation.getRandom(ctx.random());
			BlockPos blockpos = new BlockPos(x, y, z);
			return Optional.of(new Structure.GenerationStub(blockpos, p -> {
				generatePieces(p, blockpos, rotation, ctx);
			}));
		}

		private void generatePieces(StructurePiecesBuilder builder, BlockPos pos, Rotation rotation, Structure.GenerationContext ctx) {
			List<StructurePiece> list = Lists.newArrayList();
			EndCityPieces.startHouseTower(ctx.structureTemplateManager(), pos, rotation, list, ctx.random());
			list.forEach(builder::addPiece);
		}
	}
}
