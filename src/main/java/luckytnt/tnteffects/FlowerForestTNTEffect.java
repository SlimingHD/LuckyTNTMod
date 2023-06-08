package luckytnt.tnteffects;

import luckytnt.event.LevelEvents;
import luckytnt.registry.BlockRegistry;
import luckytnt.util.Materials;
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
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.phys.Vec3;

public class FlowerForestTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ExplosionHelper.doCylindricalExplosion(ent.getLevel(), ent.getPos(), 75, 75, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(distance <= 50 && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 200) {
					if((!state.isCollisionShapeFullBlock(level, pos) || state.is(Blocks.FIRE) || state.is(Blocks.SOUL_FIRE) 
					|| state.is(BlockTags.LEAVES) || Materials.isPlant(state) || state.is(BlockTags.SNOW)
					|| Materials.isWood(state)) && !(state.getBlock() instanceof GrassBlock) && !(state.getBlock() instanceof MyceliumBlock)) 
					{
						state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
					}
				}
			}
		});
		
		for(int offX = -75; offX <= 75; offX++) {
			for(int offZ = -75; offZ <= 75; offZ++) {
				double distance = Math.sqrt(offX * offX + offZ * offZ);
				int y = LevelEvents.getTopBlock(ent.getLevel(), ent.x() + offX, ent.z() + offZ, true);
				BlockPos pos = toBlockPos(new Vec3(ent.x() + offX, y, ent.z() + offZ));
				if(distance <= 75 && ent.getLevel().getBlockState(pos).getExplosionResistance(ent.getLevel(), pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 200 && ent.getLevel().getBlockState(pos.above()).isAir()) {
					ent.getLevel().setBlock(pos, Blocks.GRASS_BLOCK.defaultBlockState(), 3);
				}
			}
		}
		
		Registry<Biome> registry = ent.getLevel().registryAccess().registryOrThrow(Registries.BIOME);
		Holder<Biome> biome = registry.wrapAsHolder(registry.get(Biomes.FLOWER_FOREST));
		for(double offX = -75; offX < 75; offX++) {
			for(double offZ = -75; offZ < 75; offZ++) {
				boolean foundBlock = false;
				double distance = Math.sqrt(offX * offX + offZ * offZ);
				if(!ent.getLevel().isClientSide()) {
					if(distance < 75) {
						if(offX % 16 == 0 && offZ % 16 == 0) {
							for(LevelChunkSection section : ent.getLevel().getChunk(toBlockPos(new Vec3(ent.x() + offX, 0, ent.z() + offZ))).getSections()) {
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
						for(ServerPlayer player : ((ServerLevel)ent.getLevel()).players()) {
							player.connection.send(new ClientboundLevelChunkWithLightPacket(ent.getLevel().getChunkAt(toBlockPos(new Vec3(ent.x() + offX, 0, ent.z() + offZ))), ent.getLevel().getLightEngine(), null, null));
						}
						for(double offY = 320; offY > -64; offY--) {
							BlockPos pos = toBlockPos(new Vec3(ent.x() + offX, ent.y() + offY, ent.z() + offZ));
							BlockState state = ent.getLevel().getBlockState(pos);
							Registry<ConfiguredFeature<?, ?>> features = ent.getLevel().registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
							if(!foundBlock && state.isCollisionShapeFullBlock(ent.getLevel(), pos) && !state.isAir() && !(ent.getLevel().getBlockState(pos.above()).getBlock() instanceof LiquidBlock)) {
								double random = Math.random();
								if(random <= 0.1D) {
									features.get(VegetationFeatures.TREES_FLOWER_FOREST).place((WorldGenLevel)ent.getLevel(), ((ServerLevel)ent.getLevel()).getChunkSource().getGenerator(), RandomSource.create(), pos.above());
								} else if(random > 0.1D && random <= 0.1125D) {
									features.get(VegetationFeatures.FOREST_FLOWERS).place((WorldGenLevel)ent.getLevel(), ((ServerLevel)ent.getLevel()).getChunkSource().getGenerator(), RandomSource.create(), pos.above());
								} else if(random > 0.15D && random <= 0.1625D) {
									features.get(VegetationFeatures.FLOWER_FLOWER_FOREST).place((WorldGenLevel)ent.getLevel(), ((ServerLevel)ent.getLevel()).getChunkSource().getGenerator(), RandomSource.create(), pos.above());
								} else if(random > 0.2D && random <= 0.2125D) {
									features.get(VegetationFeatures.PATCH_GRASS).place((WorldGenLevel)ent.getLevel(), ((ServerLevel)ent.getLevel()).getChunkSource().getGenerator(), RandomSource.create(), pos.above());
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
