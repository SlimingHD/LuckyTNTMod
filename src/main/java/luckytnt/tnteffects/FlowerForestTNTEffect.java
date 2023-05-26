package luckytnt.tnteffects;

import luckytnt.event.LevelEvents;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.MyceliumBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.chunk.PalettedContainerRO;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.Material;

public class FlowerForestTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ExplosionHelper.doCylindricalExplosion(ent.level(), ent.getPos(), 75, 75, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(distance <= 50 && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.level())) <= 200) {
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
		
		for(int offX = -75; offX <= 75; offX++) {
			for(int offZ = -75; offZ <= 75; offZ++) {
				double distance = Math.sqrt(offX * offX + offZ * offZ);
				int y = LevelEvents.getTopBlock(ent.level(), ent.x() + offX, ent.z() + offZ, true);
				BlockPos pos = new BlockPos(ent.x() + offX, y, ent.z() + offZ);
				if(distance <= 75 && ent.level().getBlockState(pos).getExplosionResistance(ent.level(), pos, ImprovedExplosion.dummyExplosion(ent.level())) <= 200 && ent.level().getBlockState(pos.above()).getMaterial() == Material.AIR) {
					ent.level().setBlock(pos, Blocks.GRASS_BLOCK.defaultBlockState(), 3);
				}
			}
		}
		
		Registry<Biome> registry = ent.level().registryAccess().registryOrThrow(Registries.BIOME);
		Holder<Biome> biome = registry.wrapAsHolder(registry.get(Biomes.FLOWER_FOREST));
		for(double offX = -75; offX < 75; offX++) {
			for(double offZ = -75; offZ < 75; offZ++) {
				boolean foundBlock = false;
				double distance = Math.sqrt(offX * offX + offZ * offZ);
				if(!ent.level().isClientSide()) {
					if(distance < 75) {
						if(offX % 16 == 0 && offZ % 16 == 0) {
							for(LevelChunkSection section : ent.level().getChunk(new BlockPos(ent.x() + offX, 0, ent.z() + offZ)).getSections()) {
								PalettedContainerRO<Holder<Biome>> biomesRO = section.getBiomes();
								for(int i = 0; i < 4; ++i) {
									for(int j = 0; j < 4; ++j) {
										for(int k = 0; k < 4; ++k) {
											if(biomesRO instanceof PalettedContainer<Holder<Biome>> biomes && biomes.get(i, j, k) != registry.wrapAsHolder(registry.get(Biomes.FLOWER_FOREST))) {
												biomes.getAndSetUnchecked(i, j, k, biome);
											}
										}
									}
								}
							}
						}
						for(ServerPlayer player : ((ServerLevel)ent.level()).players()) {
							player.connection.send(new ClientboundLevelChunkWithLightPacket(ent.level().getChunkAt(new BlockPos(ent.x() + offX, 0, ent.z() + offZ)), ent.level().getLightEngine(), null, null, false));
						}
						for(double offY = 320; offY > -64; offY--) {
							BlockPos pos = new BlockPos(ent.x() + offX, ent.y() + offY, ent.z() + offZ);
							BlockState state = ent.level().getBlockState(pos);
							Registry<ConfiguredFeature<?, ?>> features = ent.level().registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
							if(!foundBlock && state.isCollisionShapeFullBlock(ent.level(), pos) && state.getMaterial() != Material.AIR && !(ent.level().getBlockState(pos.above()).getBlock() instanceof LiquidBlock)) {
								double random = Math.random();
								if(random <= 0.1D) {
									features.get(VegetationFeatures.TREES_FLOWER_FOREST).place((WorldGenLevel)ent.level(), ((ServerLevel)ent.level()).getChunkSource().getGenerator(), RandomSource.create(), pos.above());
								} else if(random > 0.1D && random <= 0.1125D) {
									features.get(VegetationFeatures.FOREST_FLOWERS).place((WorldGenLevel)ent.level(), ((ServerLevel)ent.level()).getChunkSource().getGenerator(), RandomSource.create(), pos.above());
								} else if(random > 0.15D && random <= 0.1625D) {
									features.get(VegetationFeatures.FLOWER_FLOWER_FOREST).place((WorldGenLevel)ent.level(), ((ServerLevel)ent.level()).getChunkSource().getGenerator(), RandomSource.create(), pos.above());
								} else if(random > 0.2D && random <= 0.2125D) {
									features.get(VegetationFeatures.PATCH_GRASS).place((WorldGenLevel)ent.level(), ((ServerLevel)ent.level()).getChunkSource().getGenerator(), RandomSource.create(), pos.above());
								}
								foundBlock = true;
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.FLOWER_FOREST_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 160;
	}
}
