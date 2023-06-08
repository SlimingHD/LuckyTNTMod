package luckytnt.tnteffects; 

import java.util.Random;

import org.joml.Vector3f;

import luckytnt.event.LevelEvents;
import luckytnt.registry.BlockRegistry;
import luckytnt.util.Materials;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.MyceliumBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class PlantationTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ExplosionHelper.doSphericalExplosion(ent.getLevel(), ent.getPos(), 41, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 200) {
					if((!state.isCollisionShapeFullBlock(level, pos) || state.is(Blocks.FIRE) || state.is(Blocks.SOUL_FIRE) 
					|| state.is(BlockTags.LEAVES) || Materials.isPlant(state) || state.is(BlockTags.SNOW)
					|| Materials.isWood(state)) && !(state.getBlock() instanceof GrassBlock) && !(state.getBlock() instanceof MyceliumBlock)) 
					{
						state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
					}
				}
			}
		});
		
		for(double offX = -42; offX <= 42; offX++) {
			for(double offZ = -42; offZ <= 42; offZ++) {
				double distance = Math.sqrt(offX * offX + offZ * offZ);
				if(distance <= 42) {
					int y = LevelEvents.getTopBlock(ent.getLevel(), ent.x() + offX, ent.z() + offZ, true);
					BlockPos pos = new BlockPos(Mth.floor(ent.x() + offX), y, Mth.floor(ent.z() + offZ));
					ent.getLevel().getBlockState(pos).getBlock().onBlockExploded(ent.getLevel().getBlockState(pos), ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
					ent.getLevel().setBlock(pos, Blocks.GRASS_BLOCK.defaultBlockState(), 3);
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
					BlockPos pos = new BlockPos(Mth.floor(ent.x() + offX), Mth.floor(ent.y() + offY), Mth.floor(ent.z() + offZ));
					BlockPos posUp = new BlockPos(Mth.floor(ent.x() + offX), Mth.floor(ent.y() + offY + 1), Mth.floor(ent.z() + offZ));
					BlockState state = ent.getLevel().getBlockState(pos);
					BlockState stateUp = ent.getLevel().getBlockState(posUp);
					
					if(state.getExplosionResistance(ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) < 200 && stateUp.getExplosionResistance(ent.getLevel(), posUp, ImprovedExplosion.dummyExplosion(ent.getLevel())) < 200 && !blockFound) {
						if(state.isCollisionShapeFullBlock(ent.getLevel(), pos) && !stateUp.isCollisionShapeFullBlock(ent.getLevel(), posUp) && !state.is(BlockTags.LEAVES) && !stateUp.is(Blocks.WATER) && !stateUp.is(Blocks.LAVA)) {
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
		ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0.5f, 0.1f), 1f), ent.x(), ent.y() + 1f, ent.z(), 0, 0, 0);
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
			ent.getLevel().getBlockState(pos).getBlock().onBlockExploded(ent.getLevel().getBlockState(pos), ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
			int rand = new Random().nextInt(4);
			BlockState crop = Blocks.POTATOES.defaultBlockState();
			switch(rand) {
				case 0: crop = Blocks.CARROTS.defaultBlockState().setValue(BlockStateProperties.AGE_7, new Random().nextInt(8)); break;
				case 1: crop = Blocks.POTATOES.defaultBlockState().setValue(BlockStateProperties.AGE_7, new Random().nextInt(8)); break;
				case 2: crop = Blocks.WHEAT.defaultBlockState().setValue(BlockStateProperties.AGE_7, new Random().nextInt(8)); break;
				case 3: crop = Blocks.BEETROOTS.defaultBlockState().setValue(BlockStateProperties.AGE_3, new Random().nextInt(4)); break;
			}
			ent.getLevel().setBlock(pos, Blocks.FARMLAND.defaultBlockState().setValue(FarmBlock.MOISTURE, 7), 3);
			if(!ent.getLevel().getBlockState(pos.above()).isCollisionShapeFullBlock(ent.getLevel(), pos.above()) && !(ent.getLevel().getBlockState(pos.above()).getBlock() instanceof FarmBlock)) 
				ent.getLevel().setBlock(pos.above(), crop, 3);
		} else if(melonOrPumpkin) {
			ent.getLevel().getBlockState(pos).getBlock().onBlockExploded(ent.getLevel().getBlockState(pos), ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
			int rand = Math.random() > 0.5 ? 0 : 1;
			BlockState crop = Blocks.POTATOES.defaultBlockState();
			switch(rand) {
				case 0: crop = Blocks.PUMPKIN_STEM.defaultBlockState().setValue(BlockStateProperties.AGE_7, new Random().nextInt(8)); break;
				case 1: crop = Blocks.MELON_STEM.defaultBlockState().setValue(BlockStateProperties.AGE_7, new Random().nextInt(8)); break;
			}
			ent.getLevel().setBlock(pos, Blocks.FARMLAND.defaultBlockState().setValue(FarmBlock.MOISTURE, 7), 3);
			if(!ent.getLevel().getBlockState(pos.above()).isCollisionShapeFullBlock(ent.getLevel(), pos.above()) && !(ent.getLevel().getBlockState(pos.above()).getBlock() instanceof FarmBlock)) 
				ent.getLevel().setBlock(pos.above(), crop, 3);
		}
	}
	
	public void placeWater(BlockPos pos, IExplosiveEntity ent) {
		boolean placed = false; 
		if(!ent.getLevel().getBlockState(pos.north()).isCollisionShapeFullBlock(ent.getLevel(), pos.north()) && !(ent.getLevel().getBlockState(pos.north()).getBlock() instanceof FarmBlock) && !(ent.getLevel().getBlockState(pos.north()).getBlock() instanceof LiquidBlock)) {
			placeCropsAndFarmland(pos.north(), false, ent);
		} 
		if(!ent.getLevel().getBlockState(pos.south()).isCollisionShapeFullBlock(ent.getLevel(), pos.south()) && !(ent.getLevel().getBlockState(pos.south()).getBlock() instanceof FarmBlock) && !(ent.getLevel().getBlockState(pos.south()).getBlock() instanceof LiquidBlock)) {
			placeCropsAndFarmland(pos.south(), false, ent);
		} 
		if(!ent.getLevel().getBlockState(pos.east()).isCollisionShapeFullBlock(ent.getLevel(), pos.east()) && !(ent.getLevel().getBlockState(pos.east()).getBlock() instanceof FarmBlock) && !(ent.getLevel().getBlockState(pos.east()).getBlock() instanceof LiquidBlock)) {
			placeCropsAndFarmland(pos.east(), false, ent);
		} 
		if(!ent.getLevel().getBlockState(pos.west()).isCollisionShapeFullBlock(ent.getLevel(), pos.west()) && !(ent.getLevel().getBlockState(pos.west()).getBlock() instanceof FarmBlock) && !(ent.getLevel().getBlockState(pos.west()).getBlock() instanceof LiquidBlock)) {
			placeCropsAndFarmland(pos.west(), false, ent);
		}
		if((ent.getLevel().getBlockState(pos.north()).isCollisionShapeFullBlock(ent.getLevel(), pos.north()) || ent.getLevel().getBlockState(pos.north()).getBlock() instanceof FarmBlock || ent.getLevel().getBlockState(pos.north()).getBlock() instanceof LiquidBlock) 
			&& (ent.getLevel().getBlockState(pos.south()).isCollisionShapeFullBlock(ent.getLevel(), pos.south()) || ent.getLevel().getBlockState(pos.south()).getBlock() instanceof FarmBlock || ent.getLevel().getBlockState(pos.south()).getBlock() instanceof LiquidBlock) 
			&& (ent.getLevel().getBlockState(pos.east()).isCollisionShapeFullBlock(ent.getLevel(), pos.east()) || ent.getLevel().getBlockState(pos.east()).getBlock() instanceof FarmBlock || ent.getLevel().getBlockState(pos.east()).getBlock() instanceof LiquidBlock) 
			&& (ent.getLevel().getBlockState(pos.west()).isCollisionShapeFullBlock(ent.getLevel(), pos.west()) || ent.getLevel().getBlockState(pos.west()).getBlock() instanceof FarmBlock || ent.getLevel().getBlockState(pos.west()).getBlock() instanceof LiquidBlock)) 
		{
			ent.getLevel().getBlockState(pos).getBlock().onBlockExploded(ent.getLevel().getBlockState(pos), ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
			ent.getLevel().setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
			if(!ent.getLevel().getBlockState(pos.below()).isCollisionShapeFullBlock(ent.getLevel(), pos.below())) {
				ent.getLevel().getBlockState(pos.below()).getBlock().onBlockExploded(ent.getLevel().getBlockState(pos.below()), ent.getLevel(), pos.below(), ImprovedExplosion.dummyExplosion(ent.getLevel()));
				ent.getLevel().setBlock(pos.below(), Blocks.DIRT.defaultBlockState(), 3);
			}
			placed = true;
		}
		if(!placed) {
			placeCropsAndFarmland(pos, false, ent);
		}
	}
}
