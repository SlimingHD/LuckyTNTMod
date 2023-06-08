package luckytnt.tnteffects;

import luckytnt.LuckyTNTMod;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class GroveTNTEffect extends PrimedTNTEffect {

	private final int strength;
	
	public GroveTNTEffect(int strength) {
		this.strength = strength;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), strength);
		explosion.doBlockExplosion(new IForEachBlockExplosionEffect() {
			
			@SuppressWarnings("resource")
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.isFaceSturdy(level, pos, Direction.UP) && state.getExplosionResistance(level, pos, explosion) < 100 && !state.isAir() && (level.getBlockState(pos.above()).isAir() || level.getBlockState(pos.above()).getBlock().defaultDestroyTime() <= 0.2f)) {
					level.setBlockAndUpdate(pos, Blocks.GRASS_BLOCK.defaultBlockState());
					if(Math.random() < 0.2f) {
						int random = level.random.nextInt(6);
						String string = "";
						switch (random) {
							case 0: string = "acaciatree"; break;
							case 1: string = "sprucetree"; break;
							case 2: string = "oaktree"; break;
							case 3: string = "darkoaktree"; break;
							case 4: string = "birchtree"; break;
							case 5: string = "jungletree"; break;
						}
						StructureTemplate template = ((ServerLevel)entity.getLevel()).getStructureManager().getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, string));
						if(template != null) {
							template.placeInWorld((ServerLevel)entity.getLevel(), pos.offset(-1, 0, -1), pos.offset(-1, 0, -1), new StructurePlaceSettings(), entity.getLevel().random, 3);
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
