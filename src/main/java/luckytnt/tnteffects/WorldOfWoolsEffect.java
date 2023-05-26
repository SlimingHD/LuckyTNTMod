package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;

public class WorldOfWoolsEffect extends PrimedTNTEffect {
	public static List<MaterialColor> WHITE = List.of(MaterialColor.SNOW, MaterialColor.QUARTZ, MaterialColor.TERRACOTTA_WHITE, MaterialColor.WOOL, MaterialColor.GLOW_LICHEN);
	public static List<MaterialColor> LIGHT_GRAY = List.of(MaterialColor.METAL, MaterialColor.CLAY, MaterialColor.COLOR_LIGHT_GRAY);
	public static List<MaterialColor> GRAY = List.of(MaterialColor.STONE, MaterialColor.COLOR_GRAY, MaterialColor.TERRACOTTA_CYAN, MaterialColor.DEEPSLATE);
	public static List<MaterialColor> BLACK = List.of(MaterialColor.COLOR_BLACK);
	public static List<MaterialColor> BROWN = List.of(MaterialColor.DIRT, MaterialColor.WOOD, MaterialColor.COLOR_BROWN, MaterialColor.PODZOL, MaterialColor.TERRACOTTA_BLACK, MaterialColor.TERRACOTTA_BROWN, MaterialColor.TERRACOTTA_GRAY, MaterialColor.TERRACOTTA_LIGHT_GRAY, MaterialColor.RAW_IRON);
	public static List<MaterialColor> RED = List.of(MaterialColor.FIRE, MaterialColor.COLOR_RED, MaterialColor.NETHER, MaterialColor.TERRACOTTA_RED, MaterialColor.CRIMSON_HYPHAE, MaterialColor.CRIMSON_NYLIUM, MaterialColor.TERRACOTTA_PINK);
	public static List<MaterialColor> ORANGE = List.of(MaterialColor.COLOR_ORANGE, MaterialColor.TERRACOTTA_ORANGE);
	public static List<MaterialColor> YELLOW = List.of(MaterialColor.SAND, MaterialColor.COLOR_YELLOW, MaterialColor.GOLD, MaterialColor.TERRACOTTA_YELLOW);
	public static List<MaterialColor> LIME = List.of(MaterialColor.GRASS, MaterialColor.COLOR_LIGHT_GREEN, MaterialColor.EMERALD);
	public static List<MaterialColor> GREEN = List.of(MaterialColor.PLANT, MaterialColor.COLOR_GREEN, MaterialColor.TERRACOTTA_LIGHT_GREEN, MaterialColor.TERRACOTTA_GREEN);
	public static List<MaterialColor> CYAN = List.of(MaterialColor.COLOR_CYAN, MaterialColor.WARPED_NYLIUM, MaterialColor.WARPED_STEM, MaterialColor.WARPED_WART_BLOCK);
	public static List<MaterialColor> LIGHT_BLUE = List.of(MaterialColor.ICE, MaterialColor.COLOR_LIGHT_BLUE, MaterialColor.DIAMOND);
	public static List<MaterialColor> BLUE = List.of(MaterialColor.WATER, MaterialColor.COLOR_BLUE, MaterialColor.LAPIS, MaterialColor.TERRACOTTA_LIGHT_BLUE);
	public static List<MaterialColor> PURPLE = List.of(MaterialColor.COLOR_PURPLE, MaterialColor.TERRACOTTA_BLUE, MaterialColor.WARPED_HYPHAE);
	public static List<MaterialColor> MAGENTA = List.of(MaterialColor.COLOR_MAGENTA, MaterialColor.TERRACOTTA_MAGENTA, MaterialColor.TERRACOTTA_PURPLE, MaterialColor.CRIMSON_STEM);
	public static List<MaterialColor> PINK = List.of(MaterialColor.COLOR_PINK);
	
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ImprovedExplosion explosion = new ImprovedExplosion(ent.level(), ent.getPos(), 100);
		explosion.doBlockExplosion(new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				MaterialColor color = state.getMapColor(level, pos);
				if(color != MaterialColor.NONE & !state.getCollisionShape(level, pos, CollisionContext.empty()).isEmpty()) {
					if(WHITE.contains(color)) {
						level.setBlock(pos, Blocks.WHITE_WOOL.defaultBlockState(), 3);
					} else if(LIGHT_GRAY.contains(color)) {
						level.setBlock(pos, Blocks.LIGHT_GRAY_WOOL.defaultBlockState(), 3);
					} else if(GRAY.contains(color)) {
						level.setBlock(pos, Blocks.GRAY_WOOL.defaultBlockState(), 3);
					} else if(BLACK.contains(color)) {
						level.setBlock(pos, Blocks.BLACK_WOOL.defaultBlockState(), 3);
					} else if(BROWN.contains(color)) {
						level.setBlock(pos, Blocks.BROWN_WOOL.defaultBlockState(), 3);
					} else if(RED.contains(color)) {
						level.setBlock(pos, Blocks.RED_WOOL.defaultBlockState(), 3);
					} else if(ORANGE.contains(color)) {
						level.setBlock(pos, Blocks.ORANGE_WOOL.defaultBlockState(), 3);
					} else if(YELLOW.contains(color)) {
						level.setBlock(pos, Blocks.YELLOW_WOOL.defaultBlockState(), 3);
					} else if(LIME.contains(color)) {
						level.setBlock(pos, Blocks.LIME_WOOL.defaultBlockState(), 3);
					} else if(GREEN.contains(color)) {
						level.setBlock(pos, Blocks.GREEN_WOOL.defaultBlockState(), 3);
					} else if(CYAN.contains(color)) {
						level.setBlock(pos, Blocks.CYAN_WOOL.defaultBlockState(), 3);
					} else if(LIGHT_BLUE.contains(color)) {
						level.setBlock(pos, Blocks.LIGHT_BLUE_WOOL.defaultBlockState(), 3);
					} else if(BLUE.contains(color)) {
						level.setBlock(pos, Blocks.BLUE_WOOL.defaultBlockState(), 3);
					} else if(PURPLE.contains(color)) {
						level.setBlock(pos, Blocks.PURPLE_WOOL.defaultBlockState(), 3);
					} else if(MAGENTA.contains(color)) {
						level.setBlock(pos, Blocks.MAGENTA_WOOL.defaultBlockState(), 3);
					} else if(PINK.contains(color)) {
						level.setBlock(pos, Blocks.PINK_WOOL.defaultBlockState(), 3);
					}
				}
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.WORLD_OF_WOOLS.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 150;
	}
}
