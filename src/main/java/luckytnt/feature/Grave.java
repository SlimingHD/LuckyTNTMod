package luckytnt.feature;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

import com.mojang.serialization.Codec;

import luckytnt.config.LuckyTNTConfigValues;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.common.Tags.Biomes;

public class Grave extends Feature<NoneFeatureConfiguration>{

	public BlockState ground = Blocks.GRASS_BLOCK.defaultBlockState();
	
	public BlockState slab = Blocks.STONE_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM);
	public BlockState mossySlab = Blocks.MOSSY_STONE_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM);
	
	public BlockState stairSouth = Blocks.STONE_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.BOTTOM).setValue(StairBlock.SHAPE, StairsShape.STRAIGHT);
	public BlockState mossyStairSouth = Blocks.MOSSY_STONE_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.BOTTOM).setValue(StairBlock.SHAPE, StairsShape.STRAIGHT);
	public BlockState stairNorth = Blocks.STONE_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.BOTTOM).setValue(StairBlock.SHAPE, StairsShape.STRAIGHT);
	public BlockState mossyStairNorth = Blocks.MOSSY_STONE_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.BOTTOM).setValue(StairBlock.SHAPE, StairsShape.STRAIGHT);
	
	public BlockState stairSouthTop = Blocks.STONE_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP).setValue(StairBlock.SHAPE, StairsShape.STRAIGHT);
	public BlockState mossyStairSouthTop = Blocks.MOSSY_STONE_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH).setValue(StairBlock.HALF, Half.TOP).setValue(StairBlock.SHAPE, StairsShape.STRAIGHT);
	public BlockState stairNorthTop = Blocks.STONE_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP).setValue(StairBlock.SHAPE, StairsShape.STRAIGHT);
	public BlockState mossyStairNorthTop = Blocks.MOSSY_STONE_BRICK_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH).setValue(StairBlock.HALF, Half.TOP).setValue(StairBlock.SHAPE, StairsShape.STRAIGHT);

	public BlockState chestNorthLeft = Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.NORTH).setValue(ChestBlock.TYPE, ChestType.LEFT);
	public BlockState chestNorthRight = Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.NORTH).setValue(ChestBlock.TYPE, ChestType.RIGHT);
	public BlockState chestSouthLeft = Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH).setValue(ChestBlock.TYPE, ChestType.LEFT);
	public BlockState chestSouthRight = Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH).setValue(ChestBlock.TYPE, ChestType.RIGHT);
	
	public ResourceLocation GRAVE_LOOT_1 = new ResourceLocation("luckytntmod:chests/grave_loot_1");
	public ResourceLocation GRAVE_LOOT_2 = new ResourceLocation("luckytntmod:chests/grave_loot_2");
	public ResourceLocation GRAVE_LOOT_RARE = new ResourceLocation("luckytntmod:chests/grave_loot_rare");
	
	public Grave(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
		if(!LuckyTNTConfigValues.SEASON_EVENTS_ALWAYS_ACTIVE.get().booleanValue()) {
	        int m = LocalDate.now().get(ChronoField.MONTH_OF_YEAR);
	        if(m != 10) {
	        	return false;
	        }
		}
		
		WorldGenLevel level = ctx.level();
		BlockPos pos = ctx.origin();
		RandomSource random = ctx.level().getRandom();
		int rand = random.nextInt(3);
		
		if(level.getBiome(pos).is(Biomes.IS_MUSHROOM)) {
			ground = Blocks.MYCELIUM.defaultBlockState();
		}
		
		for(int offX = -2; offX <= 2; offX++) {
			for(int offY = -2; offY <= -1; offY++) {
				for(int offZ = -1; offZ <= 2; offZ++) {
					if(level.isEmptyBlock(pos.offset(offX, offY, offZ))) {
						if(offY == -2) {
							level.setBlock(pos.offset(offX, offY, offZ), Blocks.DIRT.defaultBlockState(), 3);
						} else {
							level.setBlock(pos.offset(offX, offY, offZ), ground, 3);
						}
					}
				}
			}
		}
		
		level.setBlock(pos, Math.random() > 0.4D ? slab : mossySlab, 3);
		level.setBlock(pos.offset(1, 0, 0), Math.random() > 0.4D ? slab : mossySlab, 3);
		level.setBlock(pos.offset(0, 0, 1), Math.random() > 0.4D ? slab : mossySlab, 3);
		level.setBlock(pos.offset(1, 0, 1), Math.random() > 0.4D ? slab : mossySlab, 3);
		
		level.setBlock(pos.offset(0, -1, 0), chestNorthLeft, 3);
		level.setBlock(pos.offset(1, -1, 0), chestNorthRight, 3);
		level.setBlock(pos.offset(0, -1, 1), chestSouthRight, 3);
		level.setBlock(pos.offset(1, -1, 1), chestSouthLeft, 3);
		
		if(level.getBlockEntity(pos.offset(0, -1, 0)) instanceof ChestBlockEntity && level.getBlockEntity(pos.offset(1, -1, 0)) instanceof ChestBlockEntity) {
			double d = Math.random();
			ChestBlockEntity tile1 = (ChestBlockEntity)level.getBlockEntity(pos.offset(0, -1, 0));
			ChestBlockEntity tile2 = (ChestBlockEntity)level.getBlockEntity(pos.offset(1, -1, 0));
			if(d < 0.45D) {
				tile1.setLootTable(GRAVE_LOOT_1, random.nextLong());
				tile2.setLootTable(GRAVE_LOOT_1, random.nextLong());
			} else if(d >= 0.45D && d < 0.9D) {
				tile1.setLootTable(GRAVE_LOOT_2, random.nextLong());
				tile2.setLootTable(GRAVE_LOOT_2, random.nextLong());
			} else if(d >= 0.9D) {
				tile1.setLootTable(GRAVE_LOOT_RARE, random.nextLong());
				tile2.setLootTable(GRAVE_LOOT_RARE, random.nextLong());
			}
		}
		
		if(level.getBlockEntity(pos.offset(0, -1, 1)) instanceof ChestBlockEntity && level.getBlockEntity(pos.offset(1, -1, 1)) instanceof ChestBlockEntity) {
			double d = Math.random();
			ChestBlockEntity tile1 = (ChestBlockEntity)level.getBlockEntity(pos.offset(0, -1, 1));
			ChestBlockEntity tile2 = (ChestBlockEntity)level.getBlockEntity(pos.offset(1, -1, 1));
			if(d < 0.45D) {
				tile1.setLootTable(GRAVE_LOOT_1, random.nextLong());
				tile2.setLootTable(GRAVE_LOOT_1, random.nextLong());
			} else if(d >= 0.45D && d < 0.9D) {
				tile1.setLootTable(GRAVE_LOOT_2, random.nextLong());
				tile2.setLootTable(GRAVE_LOOT_2, random.nextLong());
			} else if(d >= 0.9D) {
				tile1.setLootTable(GRAVE_LOOT_RARE, random.nextLong());
				tile2.setLootTable(GRAVE_LOOT_RARE, random.nextLong());
			}
		}
		
		switch(rand) {
			case 0: level.setBlock(pos.offset(-1, 0, 0), Blocks.STONE_BRICKS.defaultBlockState(), 3);
					level.setBlock(pos.offset(-1, 0, 1), Blocks.STONE_BRICKS.defaultBlockState(), 3);
					level.setBlock(pos.offset(-1, 1, 0), Math.random() > 0.4D ? stairSouth : mossyStairSouth, 3);
					level.setBlock(pos.offset(-1, 1, 1), Math.random() > 0.4D ? stairNorth : mossyStairNorth, 3);
					break;
					
			case 1: level.setBlock(pos.offset(-1, 0, 0), Math.random() > 0.4D ? stairSouth : mossyStairSouth, 3);
					level.setBlock(pos.offset(-1, 0, 1), Math.random() > 0.4D ? stairNorth : mossyStairNorth, 3);
					level.setBlock(pos.offset(-1, 1, 0), Math.random() > 0.4D ? stairSouth : mossyStairSouth, 3);
					level.setBlock(pos.offset(-1, 1, 1), Math.random() > 0.4D ? stairNorth : mossyStairNorth, 3);
					break;
					
			case 2: level.setBlock(pos.offset(-1, 0, 0), Math.random() > 0.4D ? stairSouth : mossyStairSouth, 3);
					level.setBlock(pos.offset(-1, 0, 1), Math.random() > 0.4D ? stairNorth : mossyStairNorth, 3);
					level.setBlock(pos.offset(-1, 1, 0), Math.random() > 0.4D ? stairSouthTop : mossyStairSouthTop, 3);
					level.setBlock(pos.offset(-1, 1, 1), Math.random() > 0.4D ? stairNorthTop : mossyStairNorthTop, 3);
					level.setBlock(pos.offset(-1, 2, 0), Math.random() > 0.4D ? stairSouth : mossyStairSouth, 3);
					level.setBlock(pos.offset(-1, 2, 1), Math.random() > 0.4D ? stairNorth : mossyStairNorth, 3);
					break;
				
			default:level.setBlock(pos.offset(-1, 0, 0), Blocks.STONE_BRICKS.defaultBlockState(), 3);
					level.setBlock(pos.offset(-1, 0, 1), Blocks.STONE_BRICKS.defaultBlockState(), 3);
					level.setBlock(pos.offset(-1, 1, 0), Math.random() > 0.4D ? stairSouth : mossyStairSouth, 3);
					level.setBlock(pos.offset(-1, 1, 1), Math.random() > 0.4D ? stairNorth : mossyStairNorth, 3);
					break;
		}
		
		return true;
	}
}
