package luckytnt.tnteffects;

import java.util.Random;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.chunk.PalettedContainerRO;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.Tags;

public class JungleTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		replaceNonSolidBlockOrVegetationWithAir(ent, 150, 99, true);
		
		ExplosionHelper.doSphericalExplosion(ent.level(), ent.getPos(), 150, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				BlockPos posTop = pos.offset(0, 1, 0);
				BlockState stateTop = level.getBlockState(posTop);
				
				if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.level())) < 100 && stateTop.getExplosionResistance(level, posTop, ImprovedExplosion.dummyExplosion(ent.level())) < 100 && Block.isFaceFull(state.getCollisionShape(level, pos), Direction.UP) && (stateTop.getMaterial() == Material.AIR || stateTop.getMaterial() == Material.PLANT || stateTop.getMaterial() == Material.REPLACEABLE_PLANT || stateTop.getMaterial() == Material.REPLACEABLE_FIREPROOF_PLANT || stateTop.getMaterial() == Material.TOP_SNOW)) {
					state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.level()));
					level.setBlock(pos, Blocks.GRASS_BLOCK.defaultBlockState(), 3);
				}
			}
		});
		
		doJungleExplosion(ent, 150);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.JUNGLE_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 160;
	}
	
	public static void replaceNonSolidBlockOrVegetationWithAir(IExplosiveEntity ent, double radius, float maxResistance, boolean vegetation) {
		if(!ent.level().isClientSide()) {
			for(double offX = -radius; offX <= radius; offX++) {
				for(double offY = radius; offY >= -radius; offY--) {
					for(double offZ = -radius; offZ <= radius; offZ++) {
						double distance = Math.sqrt(offX * offX + offY * offY + offZ * offZ);
						BlockPos pos = new BlockPos(ent.x() + offX, ent.y() + offY, ent.z() + offZ);
						BlockState state = ent.level().getBlockState(pos);
						if(distance <= radius) {					
							if(state.getExplosionResistance(ent.level(), pos, ImprovedExplosion.dummyExplosion(ent.level())) <= maxResistance && state.getMaterial() != Material.AIR && ((!state.isCollisionShapeFullBlock(ent.level(), pos) && !state.is(Blocks.MUD) && !state.is(Tags.Blocks.CHESTS)) || (vegetation && (state.getMaterial() == Material.LEAVES || state.is(BlockTags.LOGS) || state.getBlock() == Blocks.MANGROVE_ROOTS)))) {
								if(state.getMaterial() == Material.REPLACEABLE_WATER_PLANT || state.getMaterial() == Material.WATER_PLANT) {
									Block block1 = state.getBlock();
									block1.onBlockExploded(state, ent.level(), pos, ImprovedExplosion.dummyExplosion(ent.level()));
									ent.level().setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
								} else {
									state.getBlock().onBlockExploded(state, ent.level(), pos, ImprovedExplosion.dummyExplosion(ent.level()));
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static void doJungleExplosion(IExplosiveEntity ent, double radius) {
		Registry<Biome> registry = ent.level().registryAccess().registryOrThrow(Registries.BIOME);
		Holder<Biome> biome = registry.wrapAsHolder(registry.get(Biomes.JUNGLE));
		for(double offX = -radius; offX < radius; offX++) {
			for(double offZ = -radius; offZ < radius; offZ++) {
				boolean foundBlock = false;
				double distance = Math.sqrt(offX * offX + offZ * offZ);
				if(!ent.level().isClientSide()) {
					if(distance < radius) {
						if(offX % 16 == 0 && offZ % 16 == 0) {
							for(LevelChunkSection section : ent.level().getChunk(new BlockPos(ent.x() + offX, 0, ent.z() + offZ)).getSections()) {
								PalettedContainerRO<Holder<Biome>> biomesRO = section.getBiomes();
								for(int i = 0; i < 4; ++i) {
									for(int j = 0; j < 4; ++j) {
										for(int k = 0; k < 4; ++k) {
											if(biomesRO instanceof PalettedContainer<Holder<Biome>> biomes && biomes.get(i, j, k) != biome) {
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
						
						Registry<ConfiguredFeature<?, ?>> features = ent.level().registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
						
						ConfiguredFeature<?, ?> patch_melon = features.get(VegetationFeatures.PATCH_MELON);
						ConfiguredFeature<?, ?> trees_jungle = features.get(VegetationFeatures.TREES_JUNGLE);
						ConfiguredFeature<?, ?> patch_grass_jungle = features.get(VegetationFeatures.PATCH_GRASS_JUNGLE);
						
						for(double offY = 320; offY > -64; offY--) {
							BlockPos pos = new BlockPos(ent.x() + offX, ent.y() + offY, ent.z() + offZ);
							BlockState state = ent.level().getBlockState(pos);
							if(!foundBlock && state.isCollisionShapeFullBlock(ent.level(), pos) && state.getMaterial() != Material.AIR && !(ent.level().getBlockState(pos.above()).getBlock() instanceof LiquidBlock)) {
								if(offX % 30 == 0 && offZ % 30 == 0) {
									patch_melon.place((ServerLevel)ent.level(), ((ServerLevel)ent.level()).getChunkSource().getGenerator(), RandomSource.create(), pos.above());
								}
								int random = new Random().nextInt(3);
								switch(random) {
									case 0: trees_jungle.place((ServerLevel)ent.level(), ((ServerLevel)ent.level()).getChunkSource().getGenerator(), RandomSource.create(), pos.above()); break;
									case 1:	patch_grass_jungle.place((ServerLevel)ent.level(), ((ServerLevel)ent.level()).getChunkSource().getGenerator(), RandomSource.create(), pos.above()); break;
								}
								foundBlock = true;
							}
						}
					}
				}
			}
		}
	}
}
