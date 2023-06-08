package luckytnt.tnteffects;

import org.joml.Math;
import org.joml.Vector3f;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.event.LevelEvents;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.Vec3;

public class AetherTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ExplosionHelper.doModifiedSphericalExplosion(ent.getLevel(), ent.getPos(), 100, new Vec3(1f, 0.5f, 1f), new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(!state.isAir() && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(level)) <= 200 && (ent.y() - pos.getY()) <= 35) {
					if(state.is(BlockTags.LOGS) && state.hasProperty(BlockStateProperties.AXIS)) {
						level.setBlock(pos.above(LuckyTNTConfigValues.ISLAND_HEIGHT.get() * 2), Blocks.DARK_OAK_LOG.defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS)), 3);
					} else if(state.is(BlockTags.LEAVES)) {
						if(Math.random() < 0.9D) {
							level.setBlock(pos.above(LuckyTNTConfigValues.ISLAND_HEIGHT.get() * 2), Blocks.AZALEA_LEAVES.defaultBlockState(), 3);
						} else {
							level.setBlock(pos.above(LuckyTNTConfigValues.ISLAND_HEIGHT.get() * 2), Blocks.FLOWERING_AZALEA_LEAVES.defaultBlockState(), 3);
						}
					} else {
						level.setBlock(pos.above(LuckyTNTConfigValues.ISLAND_HEIGHT.get() * 2), state, 3);
					}
				}
			}
		});
		
		for(int offX = -100; offX <= 100; offX++) {
			for(int offZ = -100; offZ <= 100; offZ++) {
				double distance = Math.sqrt(offX * offX + offZ * offZ);
				double x = ent.x() + offX;
				double z = ent.z() + offZ;
				if(distance <= 100) {
					BlockPos pos = new BlockPos(Mth.floor(x), LevelEvents.getTopBlock(ent.getLevel(), x, z, true), Mth.floor(z)).above();
					Registry<ConfiguredFeature<?, ?>> features = ent.getLevel().registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
					double random = Math.random();
					
					if(random > 0.1D && random <= 0.1125D) {
						features.get(VegetationFeatures.FOREST_FLOWERS).place((WorldGenLevel) ent.getLevel(), ((ServerLevel) ent.getLevel()).getChunkSource().getGenerator(), RandomSource.create(), pos);
					} else if(random > 0.15D && random <= 0.1625D) {
						features.get(VegetationFeatures.FLOWER_FLOWER_FOREST).place((WorldGenLevel) ent.getLevel(), ((ServerLevel) ent.getLevel()).getChunkSource().getGenerator(), RandomSource.create(), pos);
					}
				}
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		if(ent.getTNTFuse() % 3 == 0) {
			for(double d = 0D; d <= 1.5D; d += 0.1D) {
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.97f, 0.84f, 0.45f), 0.75f), ent.x() + 0.5D, ent.y() + 1.1D + d, ent.z(), 0, 0, 0);
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.97f, 0.84f, 0.45f), 0.75f), ent.x() + 0.4D, ent.y() + 1.1D + d, ent.z(), 0, 0, 0);
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.97f, 0.84f, 0.45f), 0.75f), ent.x() - 0.5D, ent.y() + 1.1D + d, ent.z(), 0, 0, 0);
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.97f, 0.84f, 0.45f), 0.75f), ent.x() - 0.4D, ent.y() + 1.1D + d, ent.z(), 0, 0, 0);
			}
			for(double d = 0D; d <= 1D; d += 0.1D) {
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.97f, 0.84f, 0.45f), 0.75f), ent.x() + 0.5D - d, ent.y() + 1.1D, ent.z(), 0, 0, 0);
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.97f, 0.84f, 0.45f), 0.75f), ent.x() + 0.5D - d, ent.y() + 1.2D, ent.z(), 0, 0, 0);
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.97f, 0.84f, 0.45f), 0.75f), ent.x() + 0.5D - d, ent.y() + 2.6D, ent.z(), 0, 0, 0);
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.97f, 0.84f, 0.45f), 0.75f), ent.x() + 0.5D - d, ent.y() + 2.5D, ent.z(), 0, 0, 0);
			}
			for(double x = -0.3D; x <= 0.3D; x += 0.1D) {
				for(double y = 0.2D; y <= 1.3D; y += 0.1D) {
					ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.31f, 0.46f, 0.86f), 0.75f), ent.x() + x + 0.05D, ent.y() + 1.1D + y, ent.z(), 0, 0, 0);
				}
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.AETHER_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 200;
	}
}
