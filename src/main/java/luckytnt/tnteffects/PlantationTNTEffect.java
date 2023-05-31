package luckytnt.tnteffects; 

import java.util.Random;

import org.joml.Vector3f;

import luckytnt.event.LevelEvents;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.MyceliumBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;

public class PlantationTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ExplosionHelper.doSphericalExplosion(ent.level(), ent.getPos(), 41, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.level())) <= 200) {
					if((state.getMaterial() == Material.BAMBOO || state.getMaterial() == Material.BAMBOO_SAPLING || state.getMaterial() == Material.CACTUS
					|| state.getMaterial() == Material.CLOTH_DECORATION || state.getMaterial() == Material.DECORATION || state.getMaterial() == Material.FIRE
					|| state.getMaterial() == Material.GRASS || state.getMaterial() == Material.LEAVES || state.getMaterial() == Material.MOSS
					|| state.getMaterial() == Material.NETHER_WOOD || state.getMaterial() == Material.PLANT || state.getMaterial() == Material.REPLACEABLE_FIREPROOF_PLANT
					|| state.getMaterial() == Material.REPLACEABLE_PLANT || state.getMaterial() == Material.REPLACEABLE_WATER_PLANT || state.getMaterial() == Material.SNOW
					|| state.getMaterial() == Material.TOP_SNOW || state.getMaterial() == Material.VEGETABLE || state.getMaterial() == Material.WATER_PLANT
					|| state.getMaterial() == Material.WOOD) && !(state.getBlock() instanceof GrassBlock) && !(state.getBlock() instanceof MyceliumBlock)) 
					{
						state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.level()));
					}
				}
			}
		});
		
		for(double offX = -42; offX <= 42; offX++) {
			for(double offZ = -42; offZ <= 42; offZ++) {
				double distance = Math.sqrt(offX * offX + offZ * offZ);
				if(distance <= 42) {
					int y = LevelEvents.getTopBlock(ent.level(), ent.x() + offX, ent.z() + offZ, true);
					BlockPos pos = new BlockPos(ent.x() + offX, y, ent.z() + offZ);
					ent.level().getBlockState(pos).getBlock().onBlockExploded(ent.level().getBlockState(pos), ent.level(), pos, ImprovedExplosion.dummyExplosion(ent.level()));
					ent.level().setBlock(pos, Blocks.GRASS_BLOCK.defaultBlockState(), 3);
				}
			}
		}
		
		BlockPos posBelow = toBlockPos(ent.getPos()).below();
		placeWater(posBelow, ent);
		
		for(double offX = -41; offX <= 41; offX++) {
			for(double offZ = -41; offZ <= 41; offZ++) {
				double distance = Math.sqrt(offX * offX + offZ * offZ);
				boolean blockFound = false;
				for(double offY = 320; offY > -64; offY--) {	
					BlockPos pos = new BlockPos(ent.x() + offX, ent.y() + offY, ent.z() + offZ);
					BlockPos posUp = new BlockPos(ent.x() + offX, ent.y() + offY + 1, ent.z() + offZ);
					BlockState state = ent.level().getBlockState(pos);
					BlockState stateUp = ent.level().getBlockState(posUp);
					
					if(state.getExplosionResistance(ent.level(), pos, ImprovedExplosion.dummyExplosion(ent.level())) < 200 && stateUp.getExplosionResistance(ent.level(), posUp, ImprovedExplosion.dummyExplosion(ent.level())) < 200 && !blockFound) {
						if(state.isCollisionShapeFullBlock(ent.level(), pos) && !stateUp.isCollisionShapeFullBlock(ent.level(), posUp) && state.getMaterial() != Material.LEAVES && stateUp.getMaterial() != Material.WATER && stateUp.getMaterial() != Material.LAVA) {
							blockFound = true;
							if(distance > 40 && distance <= 41) {
								placeCropsAndFarmland(pos, true, ent);
							} else if(distance > 39 && distance <= 40) {
								placeWater(pos, ent);
							} else if(distance > 32 && distance <= 39) {
								placeCropsAndFarmland(pos, false, ent);
							} else if(distance > 31 && distance <= 32) {
								placeWater(pos, ent);
							} else if(distance > 24 && distance <= 31) {
								placeCropsAndFarmland(pos, false, ent);
							} else if(distance > 23 && distance <= 24) {
								placeWater(pos, ent);
							} else if(distance > 16 && distance <= 23) {
								placeCropsAndFarmland(pos, false, ent);
							} else if(distance > 15 && distance <= 16) {
								placeWater(pos, ent);
							} else if(distance > 8 && distance <= 15) {
								placeCropsAndFarmland(pos, false, ent);
							} else if(distance > 7 && distance <= 8) {
								placeWater(pos, ent);
							} else if(distance <= 7) {
								placeCropsAndFarmland(pos, false, ent);
							} 
						}
					}
				}
			}
		}
	}

	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.level().addParticle(new DustParticleOptions(new Vector3f(1f, 0.5f, 0.1f), 1f), ent.x(), ent.y() + 1f, ent.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.PLANTATION_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 160;
	}
	
	public void placeCropsAndFarmland(BlockPos pos, boolean melonOrPumpkin, IExplosiveEntity ent) {
		if(!melonOrPumpkin) { 
			ent.level().getBlockState(pos).getBlock().onBlockExploded(ent.level().getBlockState(pos), ent.level(), pos, ImprovedExplosion.dummyExplosion(ent.level()));
			int rand = new Random().nextInt(4);
			BlockState crop = Blocks.POTATOES.defaultBlockState();
			switch(rand) {
				case 0: crop = Blocks.CARROTS.defaultBlockState().setValue(BlockStateProperties.AGE_7, new Random().nextInt(8)); break;
				case 1: crop = Blocks.POTATOES.defaultBlockState().setValue(BlockStateProperties.AGE_7, new Random().nextInt(8)); break;
				case 2: crop = Blocks.WHEAT.defaultBlockState().setValue(BlockStateProperties.AGE_7, new Random().nextInt(8)); break;
				case 3: crop = Blocks.BEETROOTS.defaultBlockState().setValue(BlockStateProperties.AGE_3, new Random().nextInt(4)); break;
			}
			ent.level().setBlock(pos, Blocks.FARMLAND.defaultBlockState().setValue(FarmBlock.MOISTURE, 7), 3);
			if(!ent.level().getBlockState(pos.above()).isCollisionShapeFullBlock(ent.level(), pos.above()) && !(ent.level().getBlockState(pos.above()).getBlock() instanceof FarmBlock)) 
				ent.level().setBlock(pos.above(), crop, 3);
		} else if(melonOrPumpkin) {
			ent.level().getBlockState(pos).getBlock().onBlockExploded(ent.level().getBlockState(pos), ent.level(), pos, ImprovedExplosion.dummyExplosion(ent.level()));
			int rand = Math.random() > 0.5 ? 0 : 1;
			BlockState crop = Blocks.POTATOES.defaultBlockState();
			switch(rand) {
				case 0: crop = Blocks.PUMPKIN_STEM.defaultBlockState().setValue(BlockStateProperties.AGE_7, new Random().nextInt(8)); break;
				case 1: crop = Blocks.MELON_STEM.defaultBlockState().setValue(BlockStateProperties.AGE_7, new Random().nextInt(8)); break;
			}
			ent.level().setBlock(pos, Blocks.FARMLAND.defaultBlockState().setValue(FarmBlock.MOISTURE, 7), 3);
			if(!ent.level().getBlockState(pos.above()).isCollisionShapeFullBlock(ent.level(), pos.above()) && !(ent.level().getBlockState(pos.above()).getBlock() instanceof FarmBlock)) 
				ent.level().setBlock(pos.above(), crop, 3);
		}
	}
	
	public void placeWater(BlockPos pos, IExplosiveEntity ent) {
		boolean placed = false; 
		if(!ent.level().getBlockState(pos.north()).isCollisionShapeFullBlock(ent.level(), pos.north()) && !(ent.level().getBlockState(pos.north()).getBlock() instanceof FarmBlock) && !(ent.level().getBlockState(pos.north()).getBlock() instanceof LiquidBlock)) {
			placeCropsAndFarmland(pos.north(), false, ent);
		} 
		if(!ent.level().getBlockState(pos.south()).isCollisionShapeFullBlock(ent.level(), pos.south()) && !(ent.level().getBlockState(pos.south()).getBlock() instanceof FarmBlock) && !(ent.level().getBlockState(pos.south()).getBlock() instanceof LiquidBlock)) {
			placeCropsAndFarmland(pos.south(), false, ent);
		} 
		if(!ent.level().getBlockState(pos.east()).isCollisionShapeFullBlock(ent.level(), pos.east()) && !(ent.level().getBlockState(pos.east()).getBlock() instanceof FarmBlock) && !(ent.level().getBlockState(pos.east()).getBlock() instanceof LiquidBlock)) {
			placeCropsAndFarmland(pos.east(), false, ent);
		} 
		if(!ent.level().getBlockState(pos.west()).isCollisionShapeFullBlock(ent.level(), pos.west()) && !(ent.level().getBlockState(pos.west()).getBlock() instanceof FarmBlock) && !(ent.level().getBlockState(pos.west()).getBlock() instanceof LiquidBlock)) {
			placeCropsAndFarmland(pos.west(), false, ent);
		}
		if((ent.level().getBlockState(pos.north()).isCollisionShapeFullBlock(ent.level(), pos.north()) || ent.level().getBlockState(pos.north()).getBlock() instanceof FarmBlock || ent.level().getBlockState(pos.north()).getBlock() instanceof LiquidBlock) 
			&& (ent.level().getBlockState(pos.south()).isCollisionShapeFullBlock(ent.level(), pos.south()) || ent.level().getBlockState(pos.south()).getBlock() instanceof FarmBlock || ent.level().getBlockState(pos.south()).getBlock() instanceof LiquidBlock) 
			&& (ent.level().getBlockState(pos.east()).isCollisionShapeFullBlock(ent.level(), pos.east()) || ent.level().getBlockState(pos.east()).getBlock() instanceof FarmBlock || ent.level().getBlockState(pos.east()).getBlock() instanceof LiquidBlock) 
			&& (ent.level().getBlockState(pos.west()).isCollisionShapeFullBlock(ent.level(), pos.west()) || ent.level().getBlockState(pos.west()).getBlock() instanceof FarmBlock || ent.level().getBlockState(pos.west()).getBlock() instanceof LiquidBlock)) 
		{
			ent.level().getBlockState(pos).getBlock().onBlockExploded(ent.level().getBlockState(pos), ent.level(), pos, ImprovedExplosion.dummyExplosion(ent.level()));
			ent.level().setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
			if(!ent.level().getBlockState(pos.below()).isCollisionShapeFullBlock(ent.level(), pos.below())) {
				ent.level().getBlockState(pos.below()).getBlock().onBlockExploded(ent.level().getBlockState(pos.below()), ent.level(), pos.below(), ImprovedExplosion.dummyExplosion(ent.level()));
				ent.level().setBlock(pos.below(), Blocks.DIRT.defaultBlockState(), 3);
			}
			placed = true;
		}
		if(!placed) {
			placeCropsAndFarmland(pos, false, ent);
		}
	}
}
