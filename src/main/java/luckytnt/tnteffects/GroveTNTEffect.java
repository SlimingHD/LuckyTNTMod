package luckytnt.tnteffects;

import luckytnt.LuckyTNTMod;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class GroveTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.level(), entity.getPos(), 20);
		explosion.doBlockExplosion(new IForEachBlockExplosionEffect() {
			
			@SuppressWarnings("resource")
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.isFaceSturdy(level, pos, Direction.UP) && state.getExplosionResistance(level, pos, explosion) < 100 && !state.isAir() && level.canSeeSky(pos.above())) {
					level.setBlockAndUpdate(pos, Blocks.GRASS_BLOCK.defaultBlockState());
					if(Math.random() < 0.33f) {
						int random = level.random.nextInt(6);
						StructureTemplate template = null;
						switch (random) {
							case 0: template = ((ServerLevel)entity.level()).getStructureManager().getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, "acaciatree"));
									if(template != null) {
										template.placeInWorld((ServerLevel)entity.level(), pos.offset(-1, 0, -1), pos.offset(-1, 0, -1), new StructurePlaceSettings(), entity.level().random, 3);
									}
									break;
							case 1: template = ((ServerLevel)entity.level()).getStructureManager().getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, "sprucetree"));
									if(template != null) {
										template.placeInWorld((ServerLevel)entity.level(), pos.offset(-1, 0, -1), pos.offset(-1, 0, -1), new StructurePlaceSettings(), entity.level().random, 3);
									}
									break;
							case 2: template = ((ServerLevel)entity.level()).getStructureManager().getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, "oaktree"));
									if(template != null) {
										template.placeInWorld((ServerLevel)entity.level(), pos.offset(-1, 0, -1), pos.offset(-1, 0, -1), new StructurePlaceSettings(), entity.level().random, 3);
									}
									break;
							case 3: template = ((ServerLevel)entity.level()).getStructureManager().getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, "darkoaktree"));
									if(template != null) {
										template.placeInWorld((ServerLevel)entity.level(), pos.offset(-1, 0, -1), pos.offset(-1, 0, -1), new StructurePlaceSettings(), entity.level().random, 3);
									}
									break;
							case 4: template = ((ServerLevel)entity.level()).getStructureManager().getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, "birchtree"));
									if(template != null) {
										template.placeInWorld((ServerLevel)entity.level(), pos.offset(-1, 0, -1), pos.offset(-1, 0, -1), new StructurePlaceSettings(), entity.level().random, 3);
									}
									break;
							case 5: template = ((ServerLevel)entity.level()).getStructureManager().getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, "jungletree"));
									if(template != null) {
										template.placeInWorld((ServerLevel)entity.level(), pos.offset(-1, 0, -1), pos.offset(-1, 0, -1), new StructurePlaceSettings(), entity.level().random, 3);
									}
									break;
						}
					}
				}
			}
		});
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.GROVE_TNT.get();
	}
}
