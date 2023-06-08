package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.LuckyTNTMod;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class HyperionEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 50);
		explosion.doBlockExplosion(new IForEachBlockExplosionEffect() {
			
			@SuppressWarnings("resource")
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.isFaceSturdy(level, pos, Direction.UP) && state.getExplosionResistance(level, pos, explosion) < 100 && !state.isAir() && (level.getBlockState(pos.above()).isAir() || level.getBlockState(pos.above()).getBlock().defaultDestroyTime() <= 0.2f)) {
					level.setBlockAndUpdate(pos, Blocks.GRASS_BLOCK.defaultBlockState());
					if(Math.random() < 0.015f) {
						int random = level.random.nextInt(6);
						String string = "";
						switch (random) {
							case 0: string = "giant_acaciatree"; break;
							case 1: string = "giant_sprucetree"; break;
							case 2: string = "giant_oaktree"; break;
							case 3: string = "giant_darkoaktree"; break;
							case 4: string = "giant_birchtree"; break;
							case 5: string = "giant_jungletree"; break;
						}
						StructureTemplate template = ((ServerLevel)entity.getLevel()).getStructureManager().getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, string));
						if(template != null) {
							template.placeInWorld((ServerLevel)entity.getLevel(), pos.offset(-5, 0, -5), pos.offset(-5, 0, -5), new StructurePlaceSettings(), entity.getLevel().random, 3);
						}
					}
				}
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		for(int count = 0; count < 10; count++) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.5f, 0.3f, 0f), 1f), ent.x() + (Math.random() * 0.5D - 0.25D), ent.y() + 1f + Math.random() * 2f, ent.z() + (Math.random() * 0.5D - 0.25D), 0, 0, 0);
		}
		for(int count = 0; count < 40; count++) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0.5f, 0f), 1f), ent.x() + (Math.random() * 2D - 1D), ent.y() + 3f + (Math.random() * 2D - 1D), ent.z() + (Math.random() * 2D - 1D), 0, 0, 0);
		}
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 140;
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.HYPERION.get();
	}
}
