package luckytnt.tnteffects;

import java.util.Random;

import luckytnt.entity.PrimedOreTNT;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class OreTNTEffect extends PrimedTNTEffect{

	@SuppressWarnings("resource")
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if(!entity.level().isClientSide) {
			PrimedOreTNT tnt = (PrimedOreTNT)entity;
			if(entity.getTNTFuse() == 150) {
				ExplosionHelper.doSphericalExplosion(entity.level(), entity.getPos(), 12, new IForEachBlockExplosionEffect() {					
					@Override
					public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
						if(!state.isAir() && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion()) < 100 && state.isCollisionShapeFullBlock(level, pos)) {
							tnt.availablePos.add(pos);
						}
					}
				});
			}
			Block block = Blocks.COAL_ORE;
			int random = new Random().nextInt(18);
			switch(random) {
				case 0: block = Blocks.COAL_ORE; break;
				case 1: block = Blocks.IRON_ORE; break;
				case 2: block = Blocks.GOLD_ORE; break;
				case 3: block = Blocks.COPPER_ORE; break;
				case 4: block = Blocks.DIAMOND_ORE; break;
				case 5: block = Blocks.EMERALD_ORE; break;
				case 6: block = Blocks.NETHER_QUARTZ_ORE; break;
				case 7: block = Blocks.NETHER_GOLD_ORE; break;
				case 8: block = Blocks.LAPIS_ORE; break;
				case 9: block = Blocks.REDSTONE_ORE; break;
				case 10: block = Blocks.DEEPSLATE_COAL_ORE; break;
				case 11: block = Blocks.DEEPSLATE_IRON_ORE; break;
				case 12: block = Blocks.DEEPSLATE_COPPER_ORE; break;
				case 13: block = Blocks.DEEPSLATE_GOLD_ORE; break;
				case 14: block = Blocks.DEEPSLATE_EMERALD_ORE; break;
				case 15: block = Blocks.DEEPSLATE_DIAMOND_ORE; break;
				case 16: block = Blocks.DEEPSLATE_LAPIS_ORE; break;
				case 17: block = Blocks.DEEPSLATE_REDSTONE_ORE; break;
				//Uran und Gunpowder
			}
			for (int count = 0; count < 5; count++) {			
				int rand = new Random().nextInt(tnt.availablePos.size());
				BlockPos pos = tnt.availablePos.get(rand);
				tnt.availablePos.remove(rand);
				entity.level().setBlockAndUpdate(pos, block.defaultBlockState());
				entity.level().playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1f, 1f);
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		PrimedOreTNT tnt = (PrimedOreTNT)entity;
		for (int count = 0; count < 750; count++) {			
			Block block = Blocks.COAL_ORE;
			int random = new Random().nextInt(18);
			switch(random) {
				case 0: block = Blocks.COAL_ORE; break;
				case 1: block = Blocks.IRON_ORE; break;
				case 2: block = Blocks.GOLD_ORE; break;
				case 3: block = Blocks.COPPER_ORE; break;
				case 4: block = Blocks.DIAMOND_ORE; break;
				case 5: block = Blocks.EMERALD_ORE; break;
				case 6: block = Blocks.NETHER_QUARTZ_ORE; break;
				case 7: block = Blocks.NETHER_GOLD_ORE; break;
				case 8: block = Blocks.LAPIS_ORE; break;
				case 9: block = Blocks.REDSTONE_ORE; break;
				case 10: block = Blocks.DEEPSLATE_COAL_ORE; break;
				case 11: block = Blocks.DEEPSLATE_IRON_ORE; break;
				case 12: block = Blocks.DEEPSLATE_COPPER_ORE; break;
				case 13: block = Blocks.DEEPSLATE_GOLD_ORE; break;
				case 14: block = Blocks.DEEPSLATE_EMERALD_ORE; break;
				case 15: block = Blocks.DEEPSLATE_DIAMOND_ORE; break;
				case 16: block = Blocks.DEEPSLATE_LAPIS_ORE; break;
				case 17: block = Blocks.DEEPSLATE_REDSTONE_ORE; break;
				//Uran und Gunpowder
			}
			int rand = new Random().nextInt(tnt.availablePos.size());
			BlockPos pos = tnt.availablePos.get(rand);
			tnt.availablePos.remove(rand);
			entity.level().setBlockAndUpdate(pos, block.defaultBlockState());
			entity.level().playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1f, 1f);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ORE_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 150;
	}
}
