package luckytnt.tnteffects;

import luckytnt.LuckyTNTMod;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class GroveTNTEffect extends PrimedTNTEffect {

	private final int strength;
	
	public GroveTNTEffect(int strength) {
		this.strength = strength;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		
		if (level instanceof ServerLevel server) {
			StructureTemplateManager manager = server.getStructureManager();
			StructureTemplate[] templates = new StructureTemplate[] {
				manager.getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, "acaciatree")),
				manager.getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, "sprucetree")),
				manager.getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, "oaktree")),
				manager.getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, "darkoaktree")),
				manager.getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, "birchtree")),
				manager.getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, "jungletree")),
			};

			ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(server);
			ImprovedExplosion explosion = new ImprovedExplosion(server, (Entity)entity, null, entity.x(), entity.y(), entity.z(), strength, false, (lev, center, pos, state) -> {
				if (state.isAir()) {
					return;
				}
				
				BlockPos posAbove = pos.above();
				BlockState stateAbove = lev.getBlockState(posAbove);
				if (Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) < 100f && state.isCollisionShapeFullBlock(lev, pos) && !stateAbove.isCollisionShapeFullBlock(lev, posAbove)) {
					lev.setBlockAndUpdate(pos, Blocks.GRASS_BLOCK.defaultBlockState());
					state.getBlock().wasExploded(lev, pos, dummy);
					if (random.nextFloat() < 0.2f) {
						templates[random.nextInt(templates.length)].placeInWorld(server, posAbove.offset(-1, 0, -1), posAbove.offset(-1, 0, -1), new StructurePlaceSettings(), random, 3);
					}
				}
			});
			explosion.doImprovedBlockExplosion(1f, 1f, false, false, null);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.GROVE_TNT.get();
	}
}
