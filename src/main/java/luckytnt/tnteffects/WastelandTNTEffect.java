package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.WetSpongeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.IPlantable;

public class WastelandTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		doVaporizeExplosion(ent, 75, true);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		for (int count = 0; count < 100; count++) {
			ent.level().addParticle(ParticleTypes.CLOUD, true, ent.x() + Math.random() * 30 - Math.random() * 30, ent.y() + 0.5f, ent.z() + Math.random() * 30 - Math.random() * 30, 0, 0, 0);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.WASTELAND_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 180;
	}
	
	public static void doVaporizeExplosion(IExplosiveEntity ent, double radius, boolean dryArea) {
		if(!ent.level().isClientSide()) {
			for(double offX = -radius; offX <= radius; offX++) {
				for(double offY = -radius; offY <= radius; offY++) {
					for(double offZ = -radius; offZ <= radius; offZ++) {
						double distance = Math.sqrt(offX * offX + offY * offY + offZ * offZ);
						BlockPos pos = new BlockPos(Mth.floor(ent.x() + offX), Mth.floor(ent.y() + offY), Mth.floor(ent.z() + offZ));
						BlockPos posDown = pos.offset(0, -1, 0);
						BlockState state = ent.level().getBlockState(pos);
						BlockState stateDown = ent.level().getBlockState(posDown);
						
						if(distance <= radius) {
							if(state.getBlock() instanceof LiquidBlock || state.getMaterial() == Material.WATER_PLANT || state.getMaterial() == Material.REPLACEABLE_WATER_PLANT || state.getMaterial() == Material.BUBBLE_COLUMN) {
								ent.level().setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
							}
							if(state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED)) {
								ent.level().setBlock(pos, state.setValue(BlockStateProperties.WATERLOGGED, false), 3);
							}
							if(dryArea) {
								if(state.getMaterial() == Material.PLANT || state.getMaterial() == Material.REPLACEABLE_PLANT || state.getMaterial() == Material.REPLACEABLE_FIREPROOF_PLANT) {
									if(stateDown.canSustainPlant(ent.level(), posDown, Direction.UP, (IPlantable)Blocks.DEAD_BUSH)) {
										ent.level().setBlock(pos, Blocks.DEAD_BUSH.defaultBlockState(), 3);
									}
								}
								if(state.getMaterial() == Material.GRASS) {
									ent.level().setBlock(pos, Blocks.DIRT.defaultBlockState(), 3);
								} else if(state.getMaterial() == Material.DIRT) {
									ent.level().setBlock(pos, Blocks.SAND.defaultBlockState(), 3);
								} else if(state.getMaterial() == Material.WOOL) {
									ent.level().setBlock(pos, Blocks.WHITE_WOOL.defaultBlockState(), 3);
								} else if(state.getBlock() instanceof WetSpongeBlock) {
									ent.level().setBlock(pos, Blocks.SPONGE.defaultBlockState(), 3);
								} else if(state.getMaterial() == Material.ICE || state.getMaterial() == Material.ICE_SOLID || state.getMaterial() == Material.SNOW || state.getMaterial() == Material.TOP_SNOW || state.getMaterial() == Material.POWDER_SNOW || state.getMaterial() == Material.LEAVES) {
									ent.level().setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
								}
							}
						}
					}
				}
			}
		}
	}
}
