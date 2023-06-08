package luckytnt.util;

import java.util.List;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.BaseCoralPlantTypeBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class Materials {

	//WOOD
	static List<Block> WOOD = List.of(Blocks.BAMBOO_MOSAIC, Blocks.BAMBOO_MOSAIC_SLAB, Blocks.BAMBOO_MOSAIC_STAIRS, Blocks.BOOKSHELF, Blocks.CHISELED_BOOKSHELF, Blocks.CRAFTING_TABLE, Blocks.MUSHROOM_STEM, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.RED_MUSHROOM_BLOCK, Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.NOTE_BLOCK, Blocks.JUKEBOX, Blocks.SMITHING_TABLE, Blocks.LECTERN, Blocks.FLETCHING_TABLE, Blocks.COMPOSTER, Blocks.BARREL, Blocks.LOOM, Blocks.CARTOGRAPHY_TABLE);
	static List<TagKey<Block>> WOOD_TAGS = List.of(BlockTags.LOGS, BlockTags.PLANKS, BlockTags.ALL_SIGNS, BlockTags.WOODEN_TRAPDOORS, BlockTags.WOODEN_DOORS, BlockTags.WOODEN_SLABS, BlockTags.WOODEN_STAIRS, BlockTags.WOODEN_BUTTONS, BlockTags.WOODEN_PRESSURE_PLATES, BlockTags.WOODEN_FENCES, BlockTags.FENCE_GATES, BlockTags.BAMBOO_BLOCKS, BlockTags.CAMPFIRES, BlockTags.BEEHIVES, BlockTags.BANNERS);
	
	//PLANTS
	static List<Block> EXCLUDED_PLANTS = List.of(Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, Blocks.JACK_O_LANTERN, Blocks.MELON);
	static List<Block> INCLUDED_PLANTS = List.of(Blocks.CACTUS, Blocks.BAMBOO, Blocks.BAMBOO_SAPLING);
	
	//WATER_PLANTS
	static List<Block> WATER_PLANTS = List.of(Blocks.SEA_PICKLE, Blocks.KELP, Blocks.KELP_PLANT, Blocks.SEAGRASS, Blocks.TALL_SEAGRASS);
	
	public static boolean isWood(BlockState state) {
		for(TagKey<Block> tag : WOOD_TAGS) {
			if(state.is(tag)) {
				return true;
			}
		}
		return WOOD.contains(state.getBlock());
	}
	
	public static boolean isPlant(BlockState state) {
		if(state.is(BlockTags.SWORD_EFFICIENT) && !EXCLUDED_PLANTS.contains(state.getBlock())) {
			return true;
		}
		return INCLUDED_PLANTS.contains(state.getBlock());
	}
	
	public static boolean isWaterPlant(BlockState state) {
		return WATER_PLANTS.contains(state.getBlock()) || state.getBlock() instanceof BaseCoralPlantTypeBlock;
	}
}
