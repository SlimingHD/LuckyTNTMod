package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.WetSpongeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.IPlantable;

public class DustBowlEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.doSphericalExplosion(entity.level(), entity.getPos(), 25, new IForEachBlockExplosionEffect() {
		
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				BlockPos posBelow = pos.offset(0, -1, 0);
				BlockState stateBelow = level.getBlockState(posBelow);
				
				if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion()) <= 200) {
					if(state.getBlock() instanceof LiquidBlock || state.getMaterial() == Material.WATER_PLANT || state.getMaterial() == Material.REPLACEABLE_WATER_PLANT || state.getMaterial() == Material.BUBBLE_COLUMN) {
						level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
					}
					if(state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED)) {
						level.setBlock(pos, state.setValue(BlockStateProperties.WATERLOGGED, false), 3);
					}
					if(state.getMaterial() == Material.PLANT || state.getMaterial() == Material.REPLACEABLE_PLANT || state.getMaterial() == Material.REPLACEABLE_FIREPROOF_PLANT || state.getMaterial() == Material.BAMBOO_SAPLING) {
						if(stateBelow.canSustainPlant(level, posBelow, Direction.UP, (IPlantable)Blocks.DEAD_BUSH)) {
							level.setBlock(pos, Blocks.DEAD_BUSH.defaultBlockState(), 3);
						}
					}
					if(state.getMaterial() == Material.GRASS) {
						level.setBlock(pos, Blocks.DIRT.defaultBlockState(), 3);
					} else if(state.getMaterial() == Material.DIRT) {
						level.setBlock(pos, Blocks.SAND.defaultBlockState(), 3);
					} else if(state.getMaterial() == Material.WOOL) {
						level.setBlock(pos, Blocks.WHITE_WOOL.defaultBlockState(), 3);
					} else if(state.getBlock() instanceof WetSpongeBlock) {
						level.setBlock(pos, Blocks.SPONGE.defaultBlockState(), 3);
					} else if(state.getMaterial() == Material.ICE || state.getMaterial() == Material.ICE_SOLID || state.getMaterial() == Material.SNOW || state.getMaterial() == Material.TOP_SNOW || state.getMaterial() == Material.POWDER_SNOW || state.getMaterial() == Material.LEAVES) {
						level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
					}
				}
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.level().addParticle(ParticleTypes.CLOUD, ent.x() + Math.random() * 6 - Math.random() * 6, ent.y() + 0.5f, ent.z() + Math.random() * 6 - Math.random() * 6, 0, 0, 0);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.DUST_BOWL.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 120;
	}
}
