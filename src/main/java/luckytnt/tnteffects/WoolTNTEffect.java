package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class WoolTNTEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.level(), entity.getPos(), 40);
		explosion.doBlockExplosion(new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(!state.isCollisionShapeFullBlock(level, pos)){
					state.onBlockExploded(level, pos, explosion);
					level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				}
				else if(state.getMaterial() != Material.AIR) {
					state.onBlockExploded(level, pos, explosion);
					if(state.getMaterial().getColor() != null) {
						MaterialColor color = state.getMapColor(level, pos);
						
						if(color == MaterialColor.SNOW || color == MaterialColor.TERRACOTTA_WHITE || color == MaterialColor.QUARTZ) {
							level.setBlock(pos, Blocks.WHITE_WOOL.defaultBlockState(), 3);
						}
						else if(color == MaterialColor.DEEPSLATE || color == MaterialColor.TERRACOTTA_CYAN || color == MaterialColor.COLOR_GRAY) {
							level.setBlock(pos, Blocks.GRAY_WOOL.defaultBlockState(), 3);
						}
						else if(color == MaterialColor.WOOL || color == MaterialColor.CLAY || color == MaterialColor.COLOR_LIGHT_GRAY || color == MaterialColor.TERRACOTTA_LIGHT_GRAY || color == MaterialColor.STONE) {
							level.setBlock(pos, Blocks.LIGHT_GRAY_WOOL.defaultBlockState(), 3);
						}
						else if(color == MaterialColor.COLOR_BLACK || color == MaterialColor.TERRACOTTA_BLACK) {
							level.setBlock(pos, Blocks.BLACK_WOOL.defaultBlockState(), 3);
						}
						else if(color == MaterialColor.GRASS || color == MaterialColor.COLOR_LIGHT_GREEN || color == MaterialColor.EMERALD) {
							level.setBlock(pos, Blocks.LIME_WOOL.defaultBlockState(), 3);
						}
						else if(color == MaterialColor.COLOR_GREEN || color == MaterialColor.TERRACOTTA_LIGHT_GREEN || color == MaterialColor.TERRACOTTA_GREEN || color == MaterialColor.PLANT) {
							level.setBlock(pos, Blocks.GREEN_WOOL.defaultBlockState(), 3);
						}
						else if(color == MaterialColor.COLOR_YELLOW || color == MaterialColor.TERRACOTTA_YELLOW || color == MaterialColor.GOLD || color == MaterialColor.SAND) {
							level.setBlock(pos, Blocks.YELLOW_WOOL.defaultBlockState(), 3);
						}
						else if(color == MaterialColor.COLOR_ORANGE || color == MaterialColor.TERRACOTTA_ORANGE) {
							level.setBlock(pos, Blocks.ORANGE_WOOL.defaultBlockState(), 3);
						}
						else if(color == MaterialColor.COLOR_RED || color == MaterialColor.TERRACOTTA_RED || color == MaterialColor.NETHER || color == MaterialColor.CRIMSON_NYLIUM || color == MaterialColor.CRIMSON_HYPHAE) {
							level.setBlock(pos, Blocks.RED_WOOL.defaultBlockState(), 3);
						}
						else if(color == MaterialColor.COLOR_PINK || color == MaterialColor.TERRACOTTA_PINK) {
							level.setBlock(pos, Blocks.PINK_WOOL.defaultBlockState(), 3);
						}
						else if(color == MaterialColor.COLOR_MAGENTA || color == MaterialColor.TERRACOTTA_MAGENTA || color == MaterialColor.CRIMSON_STEM) {
							level.setBlock(pos, Blocks.MAGENTA_WOOL.defaultBlockState(), 3);
						}
						else if(color == MaterialColor.COLOR_PURPLE || color == MaterialColor.TERRACOTTA_PURPLE || color == MaterialColor.WARPED_HYPHAE) {
							level.setBlock(pos, Blocks.PURPLE_WOOL.defaultBlockState(), 3);
						}
						else if(color == MaterialColor.COLOR_LIGHT_BLUE || color == MaterialColor.TERRACOTTA_LIGHT_BLUE || color == MaterialColor.ICE || color == MaterialColor.DIAMOND) {
							level.setBlock(pos, Blocks.LIGHT_BLUE_WOOL.defaultBlockState(), 3);
						}
						else if(color == MaterialColor.COLOR_CYAN || color == MaterialColor.WARPED_STEM || color == MaterialColor.WARPED_NYLIUM || color == MaterialColor.WARPED_WART_BLOCK) {
							level.setBlock(pos, Blocks.CYAN_WOOL.defaultBlockState(), 3);
						}
						else if(color == MaterialColor.COLOR_BLUE || color == MaterialColor.TERRACOTTA_BLUE || color == MaterialColor.WATER || color == MaterialColor.LAPIS) {
							level.setBlock(pos, Blocks.BLUE_WOOL.defaultBlockState(), 3);
						}
						else if(color == MaterialColor.COLOR_BROWN || color == MaterialColor.TERRACOTTA_BROWN || color == MaterialColor.DIRT || color == MaterialColor.TERRACOTTA_GRAY || color == MaterialColor.WOOD) {
							level.setBlock(pos, Blocks.BROWN_WOOL.defaultBlockState(), 3);
						}
					}
				}
			}
		});
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.WOOL_TNT.get();
	}
}
