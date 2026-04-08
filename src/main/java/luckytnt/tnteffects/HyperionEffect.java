package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.LuckyTNTMod;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
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

public class HyperionEffect extends PrimedTNTEffect {

	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		
		if (level instanceof ServerLevel server) {
			StructureTemplateManager manager = server.getStructureManager();
			StructureTemplate[] templates = new StructureTemplate[] {
				manager.getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, "giant_acaciatree")),
				manager.getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, "giant_sprucetree")),
				manager.getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, "giant_oaktree")),
				manager.getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, "giant_darkoaktree")),
				manager.getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, "giant_birchtree")),
				manager.getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, "giant_jungletree"))
			};
			
			ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(server);
			ImprovedExplosion explosion = new ImprovedExplosion(server, (Entity)entity, null, entity.x(), entity.y(), entity.z(), 50, false, (lev, center, pos, state) -> {
				if (state.isAir()) {
					return;
				}
				
				BlockPos posAbove = pos.above();
				BlockState stateAbove = lev.getBlockState(posAbove);
				if (Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) < 100f && state.isCollisionShapeFullBlock(lev, pos) && !stateAbove.isCollisionShapeFullBlock(lev, posAbove)) {
					lev.setBlockAndUpdate(pos, Blocks.GRASS_BLOCK.defaultBlockState());
					state.getBlock().wasExploded(lev, pos, dummy);
					if (random.nextFloat() < 0.015f) {
						templates[random.nextInt(templates.length)].placeInWorld(server, posAbove.offset(-5, 0, -5), posAbove.offset(-5, 0, -5), new StructurePlaceSettings(), random, 3);
					}
				}
			});
			explosion.doImprovedBlockExplosion(1f, 1f, false, false, null);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		
		for (int count = 0; count < 10; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(0.5f, 0.3f, 0f), 1f), entity.x() + random.nextDouble() * 0.5d - 0.25d, entity.y() + 1d + random.nextDouble() * 2d, entity.z() + random.nextDouble() * 0.5d - 0.25d, 0d, 0d, 0d);
		}
		for (int count = 0; count < 40; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(0f, 0.5f, 0f), 1f), entity.x() + random.nextDouble() * 2d - 1d, entity.y() + 3d + random.nextDouble() * 2d - 1d, entity.z() + random.nextDouble() * 2d - 1d, 0d, 0d, 0d);
		}
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 140;
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.HYPERION.get();
	}
}
