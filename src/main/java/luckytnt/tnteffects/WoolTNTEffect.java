package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;

public class WoolTNTEffect extends PrimedTNTEffect{

	private final int strength;
	
	public WoolTNTEffect(int strength) {
		this.strength = strength;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.doSphericalExplosion(entity.getLevel(), entity.getPos(), strength, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				MapColor color = state.getMapColor(level, pos);
				if(color != MapColor.NONE & !state.getCollisionShape(level, pos, CollisionContext.empty()).isEmpty() && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(level)) <= 100) {
					if(WorldOfWoolsEffect.WHITE.contains(color)) {
						level.setBlock(pos, Blocks.WHITE_WOOL.defaultBlockState(), 3);
					} else if(WorldOfWoolsEffect.LIGHT_GRAY.contains(color)) {
						level.setBlock(pos, Blocks.LIGHT_GRAY_WOOL.defaultBlockState(), 3);
					} else if(WorldOfWoolsEffect.GRAY.contains(color)) {
						level.setBlock(pos, Blocks.GRAY_WOOL.defaultBlockState(), 3);
					} else if(WorldOfWoolsEffect.BLACK.contains(color)) {
						level.setBlock(pos, Blocks.BLACK_WOOL.defaultBlockState(), 3);
					} else if(WorldOfWoolsEffect.BROWN.contains(color)) {
						level.setBlock(pos, Blocks.BROWN_WOOL.defaultBlockState(), 3);
					} else if(WorldOfWoolsEffect.RED.contains(color)) {
						level.setBlock(pos, Blocks.RED_WOOL.defaultBlockState(), 3);
					} else if(WorldOfWoolsEffect.ORANGE.contains(color)) {
						level.setBlock(pos, Blocks.ORANGE_WOOL.defaultBlockState(), 3);
					} else if(WorldOfWoolsEffect.YELLOW.contains(color)) {
						level.setBlock(pos, Blocks.YELLOW_WOOL.defaultBlockState(), 3);
					} else if(WorldOfWoolsEffect.LIME.contains(color)) {
						level.setBlock(pos, Blocks.LIME_WOOL.defaultBlockState(), 3);
					} else if(WorldOfWoolsEffect.GREEN.contains(color)) {
						level.setBlock(pos, Blocks.GREEN_WOOL.defaultBlockState(), 3);
					} else if(WorldOfWoolsEffect.CYAN.contains(color)) {
						level.setBlock(pos, Blocks.CYAN_WOOL.defaultBlockState(), 3);
					} else if(WorldOfWoolsEffect.LIGHT_BLUE.contains(color)) {
						level.setBlock(pos, Blocks.LIGHT_BLUE_WOOL.defaultBlockState(), 3);
					} else if(WorldOfWoolsEffect.BLUE.contains(color)) {
						level.setBlock(pos, Blocks.BLUE_WOOL.defaultBlockState(), 3);
					} else if(WorldOfWoolsEffect.PURPLE.contains(color)) {
						level.setBlock(pos, Blocks.PURPLE_WOOL.defaultBlockState(), 3);
					} else if(WorldOfWoolsEffect.MAGENTA.contains(color)) {
						level.setBlock(pos, Blocks.MAGENTA_WOOL.defaultBlockState(), 3);
					} else if(WorldOfWoolsEffect.PINK.contains(color)) {
						level.setBlock(pos, Blocks.PINK_WOOL.defaultBlockState(), 3);
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
