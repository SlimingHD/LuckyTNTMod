package luckytnt.feature;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

import com.mojang.serialization.Codec;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.common.Tags.Biomes;

public class Altar extends Feature<NoneFeatureConfiguration>{
	public BlockState ground = Blocks.GRASS_BLOCK.defaultBlockState();
	
	public BlockState slab = Blocks.STONE_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM);
	public BlockState mossySlab = Blocks.MOSSY_STONE_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM);
	
	public BlockState wall = Blocks.STONE_BRICK_WALL.defaultBlockState();
	public BlockState mossyWall = Blocks.MOSSY_STONE_BRICK_WALL.defaultBlockState();
	
	public Altar(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}
	
	public BlockState randomBrick() {
		double random = Math.random();
		if(random < 0.5D) {
			return Blocks.STONE_BRICKS.defaultBlockState();
		} else if(random >= 0.5D && random < 0.8D) {
			return Blocks.MOSSY_STONE_BRICKS.defaultBlockState();
		} else if(random >= 0.8D) {
			return Blocks.CRACKED_STONE_BRICKS.defaultBlockState();
		}
		
		return Blocks.STONE_BRICKS.defaultBlockState();
	}
	
	public BlockState randomTNT() {
		double random = Math.random();
		if(random < 0.4D) {
			return BlockRegistry.PUMPKIN_BOMB.get().defaultBlockState();
		} else if(random >= 0.4D && random < 0.8D) {
			return BlockRegistry.ZOMBIE_APOCALYPSE.get().defaultBlockState();
		} else if(random >= 0.8D) {
			return BlockRegistry.GRAVEYARD_TNT.get().defaultBlockState();
		}
		
		return BlockRegistry.PUMPKIN_BOMB.get().defaultBlockState();
	}
	
	public BlockState randomSlab() {
		return Math.random() > 0.4D ? slab : mossySlab;
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
		
		if(level.getBiome(pos).is(Biomes.IS_MUSHROOM)) {
			ground = Blocks.MYCELIUM.defaultBlockState();
		}
		
		for(int offX = -3; offX <= 3; offX++) {
			for(int offY = -2; offY <= -1; offY++) {
				for(int offZ = -3; offZ <= 3; offZ++) {
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
		
		for(int offX = -1; offX <= 1; offX++) {
			for(int offZ = -1; offZ <= 1; offZ++) {
				level.setBlock(pos.offset(offX, 0, offZ), randomBrick(), 3);
			}
		}
		
		for(int offX = -1; offX <= 1; offX++) {
			level.setBlock(pos.offset(offX, 0, 2), randomSlab(), 3);
			level.setBlock(pos.offset(offX, 0, -2), randomSlab(), 3);
		}
		
		for(int offZ = -1; offZ <= 1; offZ++) {
			level.setBlock(pos.offset(2, 0, offZ), randomSlab(), 3);
			level.setBlock(pos.offset(-2, 0, offZ), randomSlab(), 3);
		}
		
		level.setBlock(pos.offset(1, 1, 0), randomSlab(), 3);
		level.setBlock(pos.offset(0, 1, -1), randomSlab(), 3);
		level.setBlock(pos.offset(1, 1, -1), Math.random() > 0.4D ? wall : mossyWall, 3);
		level.setBlock(pos.offset(1, 2, -1), randomSlab(), 3);
		
		for(int offY = 1; offY <= 3; offY++) {
			if(offY <= 2) {
				level.setBlock(pos.offset(1, offY, 1), Math.random() > 0.4D ? wall : mossyWall, 3);
				level.setBlock(pos.offset(-1, offY, 1), Math.random() > 0.4D ? wall : mossyWall, 3);
				level.setBlock(pos.offset(-1, offY, -1), Math.random() > 0.4D ? wall : mossyWall, 3);
			} else {
				level.setBlock(pos.offset(1, offY, 1), Math.random() > 0.4D ? wall : mossyWall, 3);
				level.setBlock(pos.offset(-1, offY, 1), randomSlab(), 3);
				level.setBlock(pos.offset(-1, offY, -1), Math.random() > 0.4D ? wall : mossyWall, 3);
				
				level.setBlock(pos.offset(0, offY, 1), randomSlab().setValue(SlabBlock.TYPE, SlabType.TOP), 3);
				level.setBlock(pos.offset(-1, offY, 0), randomSlab().setValue(SlabBlock.TYPE, SlabType.TOP), 3);
			}
		}
		
		level.setBlock(pos.offset(0, 4, 0), randomBrick(), 3);
		level.setBlock(pos.offset(1, 4, 1), randomSlab(), 3);
		level.setBlock(pos.offset(-1, 4, -1), randomSlab(), 3);
		
		level.setBlock(pos.offset(0, 1, 0), randomTNT(), 3);
		
		
		return false;
	}
}
