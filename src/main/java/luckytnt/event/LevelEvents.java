package luckytnt.event;

import java.util.BitSet;
import java.util.List;

import luckytnt.LevelVariables;
import luckytnt.LuckyTNTMod;
import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.EntityRegistry;
import luckytnt.util.Materials;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.entity.LivingPrimedLTNT;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.RandomList;
import luckytntlib.util.explosions.ImprovedExplosion;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LuckyTNTMod.MODID)
public class LevelEvents {
	
	private static final RandomList<EntityType<?>> TNTS = new RandomList<>(
		List.of(EntityRegistry.TNT_X20.get(), EntityRegistry.FIRE_TNT.get(), EntityRegistry.SNOW_TNT.get(), EntityRegistry.FREEZE_TNT.get(), EntityRegistry.ATTACKING_TNT.get(), EntityRegistry.BIG_TNT.get(), EntityRegistry.WALKING_TNT.get(), EntityRegistry.NUCLEAR_WASTE_TNT.get(), EntityRegistry.BOUNCING_TNT.get(), EntityRegistry.FARMING_TNT.get(), EntityRegistry.GROVE_TNT.get(), EntityRegistry.COMPACT_TNT.get(), EntityRegistry.RANDOM_TNT.get(), EntityRegistry.TNT_X5.get(), EntityRegistry.TNT.get()), 
		List.of(1f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 1f, 1f, 16f, 70f)
	);

	@SubscribeEvent
	public static void onLevelUpdate(TickEvent.LevelTickEvent event) {
		if (event.phase == Phase.START && event.side == LogicalSide.SERVER && event.level.dimension() == Level.OVERWORLD) {
			ServerLevel server = (ServerLevel)event.level;
			RandomSource random = server.getRandom();
			LevelVariables variables = LevelVariables.get(server);
			
			if (variables.doomsdayTime > 0) {
				variables.doomsdayTime--;
			}
			if (variables.toxicCloudsTime > 0) {
				variables.toxicCloudsTime--;
			}
			if (variables.iceAgeTime > 0) {
				variables.iceAgeTime--;
			}
			if (variables.heatDeathTime > 0) {
				variables.heatDeathTime--;
			}
			if (variables.tntRainTime > 0) {
				variables.tntRainTime--;
			}
			variables.sync(server);
			
			for (ServerPlayer player : server.players()) {
				if (variables.doomsdayTime > 0) {
					doomsdayDisaster(server, player, random);
				}
				if (variables.toxicCloudsTime > 0) {
					toxicCloudsDisaster(server, player, random);
				}
				if (variables.iceAgeTime > 0) {
					iceAgeDisaster(server, player, random);
				}
				if (variables.heatDeathTime > 0) {
					heatDeathDisaster(server, player, random);
				}
				if (variables.tntRainTime > 0) {
					tntRainDisaster(server, player, random, variables.tntRainTime);
				}
			}
		}
	}
	
	public static int getTopBlock(Level level, double x, double z, boolean ignoreLeaves) {
		if (!level.isClientSide()) {
			for (int y = level.getMaxBuildHeight(); y >= level.getMinBuildHeight(); y--) {
				BlockPos pos = new BlockPos(Mth.floor(x), y, Mth.floor(z));
				BlockPos posUp = new BlockPos(Mth.floor(x), y + 1, Mth.floor(z));
				BlockState state = level.getBlockState(pos);
				BlockState stateUp = level.getBlockState(posUp);
				if (ignoreLeaves) {
					if (state.isCollisionShapeFullBlock(level, pos) && !stateUp.isCollisionShapeFullBlock(level, posUp) && !state.is(BlockTags.LEAVES)) {
						return y;
					}
				} else {
					if (state.isCollisionShapeFullBlock(level, pos) && !stateUp.isCollisionShapeFullBlock(level, posUp)) {
						return y;
					}
				}
			}
		}
		return 0;
	}
	
	private static void doomsdayDisaster(ServerLevel server, ServerPlayer player, RandomSource random) {
		int dropHeight = LuckyTNTConfigValues.DROP_HEIGHT.get();
		double intensity = LuckyTNTConfigValues.AVERAGE_DIASTER_INTENSITY.get();
		double x = player.getX();
		double y = player.getY();
		double z = player.getZ();
		
		for (int count = 0; count < 6; count++) {
			Entity ent = EntityRegistry.HAILSTONE.get().create(server);
			ent.setPos(x + random.nextDouble() * 200 - 100, y + dropHeight / 4 + random.nextDouble() * dropHeight / 4, z + random.nextDouble() * 200 - 100);
			server.addFreshEntity(ent);
		}
		if (random.nextDouble() < 0.00675f * intensity) {
			LExplosiveProjectile ent = EntityRegistry.LITTLE_METEOR.get().create(server);
			ent.setPos(x + random.nextDouble() * 200 - random.nextDouble() * 200, y + dropHeight, z + random.nextDouble() * 200 - random.nextDouble() * 200);
			server.addFreshEntity(ent);
		}
		if (random.nextDouble() < 0.025f * intensity) {
			Entity ent = EntityRegistry.MINI_METEOR.get().create(server);
			ent.setPos(x + random.nextDouble() * 400 - 200, y + dropHeight, z + random.nextDouble() * 400 - 200);
			server.addFreshEntity(ent);
		}
		if (random.nextDouble() < 0.1f * intensity) {
			int xVal = Mth.floor(x + random.nextDouble() * 400 - 200);
			int zVal = Mth.floor(z + random.nextDouble() * 400 - 200);
			int yVal = server.getHeight(Types.WORLD_SURFACE, xVal, zVal) + 1;
			Entity lighting = new LightningBolt(EntityType.LIGHTNING_BOLT, server);
			lighting.setPos(xVal + 0.5d, yVal, zVal + 0.5d);
			server.addFreshEntity(lighting);
		}
	}
	
	private static void toxicCloudsDisaster(ServerLevel server, ServerPlayer player, RandomSource random) {
		double x = player.getX();
		double y = player.getY();
		double z = player.getZ();
		
		if (random.nextDouble() < 0.005f * LuckyTNTConfigValues.AVERAGE_DIASTER_INTENSITY.get()) {
			BlockPos pos = new BlockPos(Mth.floor(x + random.nextDouble() * 200 - 100), Mth.floor(y + random.nextDouble() * 100 - 50), Mth.floor(z + random.nextDouble() * 200 - 100));
			BlockState state = server.getBlockState(pos);
			if (state.isAir() || !state.isCollisionShapeFullBlock(server, pos)) {
				PrimedLTNT cloud = EntityRegistry.TOXIC_CLOUD.get().create(server);
				cloud.setPos(pos.getX(), pos.getY(), pos.getZ());
				server.addFreshEntity(cloud);
			}
		}
	}
	
	private static void iceAgeDisaster(ServerLevel server, ServerPlayer player, RandomSource random) {
		double x = player.getX();
		double z = player.getZ();
		BitSet emptySet = new BitSet(0);
		
		Registry<Biome> registry = server.registryAccess().registryOrThrow(Registries.BIOME);
		Holder<Biome> biome = registry.getHolderOrThrow(Biomes.SNOWY_TAIGA);
		for (int offX = -32; offX <= 32; offX += 16) {
			for (int offZ = -32; offZ <= 32; offZ += 16) {
				boolean needsUpdate = false;
				LevelChunk chunk = server.getChunk(Mth.floor(x + offX) >> 4, Mth.floor(z + offZ) >> 4);
				for (LevelChunkSection section : chunk.getSections()) {
					for (int i = 0; i < 4; ++i) {
						for (int j = 0; j < 4; ++j) {
							for (int k = 0; k < 4; ++k) {
								if (section.getBiomes().get(i, j, k).get() != biome.get() && section.getBiomes() instanceof PalettedContainer<Holder<Biome>> container) {
									container.getAndSetUnchecked(i, j, k, biome);
									needsUpdate = true;
								}
							}
						}
					}
				}
				if (needsUpdate) {
					for (ServerPlayer p : server.players()) {
						p.connection.send(new ClientboundLevelChunkWithLightPacket(chunk, server.getLightEngine(), emptySet, emptySet));
					}
				}
			}
		}
	}
	
	private static void heatDeathDisaster(ServerLevel server, ServerPlayer player, RandomSource random) {
		double intensity = LuckyTNTConfigValues.AVERAGE_DIASTER_INTENSITY.get();
		double x = player.getX();
		double z = player.getZ();
		BitSet emptySet = new BitSet(0);
		
		Registry<Biome> registry = server.registryAccess().registryOrThrow(Registries.BIOME);
		Holder<Biome> biome = registry.getHolderOrThrow(Biomes.DESERT);
		for (int offX = -32; offX <= 32; offX += 16) {
			for (int offZ = -32; offZ <= 32; offZ += 16) {
				boolean needsUpdate = false;
				LevelChunk chunk = server.getChunk(Mth.floor(x + offX) >> 4, Mth.floor(z + offZ) >> 4);
				for (LevelChunkSection section : chunk.getSections()) {
					for (int i = 0; i < 4; ++i) {
						for (int j = 0; j < 4; ++j) {
							for (int k = 0; k < 4; ++k) {
								if (section.getBiomes().get(i, j, k).get() != biome.get() && section.getBiomes() instanceof PalettedContainer<Holder<Biome>> container) {
									container.getAndSetUnchecked(i, j, k, biome);
									needsUpdate = true;
								}
							}
						}
					}
				}
				if (needsUpdate) {
					for (ServerPlayer p : server.players()) {
						p.connection.send(new ClientboundLevelChunkWithLightPacket(chunk, server.getLightEngine(), emptySet, emptySet));
					}
				}
			}
		}
		
		for (int i = 0; i < 1 + (int)(0.5d * intensity); i++) {
			int offX = random.nextInt(60) - 30;
			int offZ = random.nextInt(60) - 30;
			int posY = getTopBlock(server, x + offX, z + offZ, false);
			BlockPos pos = new BlockPos(Mth.floor(x + offX), Mth.floor(posY + 1), Mth.floor(z + offZ));
			BlockState state = server.getBlockState(pos);
			if ((Materials.isPlant(state) || state.isAir()) && state.getExplosionResistance(server, pos, ImprovedExplosion.dummyExplosion(server)) <= 100) {
				if (random.nextDouble() > 0.1D) {
					server.setBlock(pos, BaseFireBlock.getState(server, pos), 3);
				} else {
					server.setBlock(pos, Blocks.LAVA.defaultBlockState(), 3);
				}
			}
		}
		for (int i = 0; i < 1 + (int) (0.5d * intensity); i++) {
			int offX = random.nextInt(60) - 30;
			int offZ = random.nextInt(60) - 30;
			int posY = getTopBlock(server, x + offX, z + offZ, true);
			BlockPos pos = new BlockPos(Mth.floor(x + offX), posY, Mth.floor(z + offZ));
			BlockState state = server.getBlockState(pos);
			if (state.is(Blocks.GRASS_BLOCK)) {
				server.setBlock(pos, random.nextDouble() > 0.5D ? Blocks.COARSE_DIRT.defaultBlockState() : Blocks.DIRT.defaultBlockState(), 3);
			} else if (server.getBlockState(pos.above()).is(Blocks.WATER) && random.nextDouble() > 0.6D) {
				server.setBlock(pos, Blocks.MAGMA_BLOCK.defaultBlockState(), 3);
			}
		}
		for (int offX = -30; offX < 30; offX += 2) {
			for (int offZ = -30; offZ < 30; offZ += 2) {
				int posY = getTopBlock(server, x + offX, z + offZ, true);
				BlockPos pos = new BlockPos(Mth.floor(x + offX), posY + 1, Mth.floor(z + offZ));
				BlockState state = server.getBlockState(pos);
				if ((Materials.isPlant(state) || state.isAir()) && server.getBlockState(pos.below()).canSustainPlant(server, pos.below(), Direction.UP, (IPlantable)Blocks.DEAD_BUSH) && state.getExplosionResistance(server, pos, ImprovedExplosion.dummyExplosion(server)) <= 100 && state.getBlock() != Blocks.DEAD_BUSH) {
					server.setBlock(pos, Blocks.DEAD_BUSH.defaultBlockState(), 3);
				}
			}
		}
	}
	
	private static void tntRainDisaster(ServerLevel server, ServerPlayer player, RandomSource random, int tntRainTime) {
		int i = 4 - LuckyTNTConfigValues.AVERAGE_DIASTER_INTENSITY.get().intValue() / 3;
		if (tntRainTime % i == 0) {
			Entity ent = TNTS.getRandomItem(random).create(server);
			ent.setPos(player.getX() + random.nextDouble() * 80d - 40d, player.getY() + 20d + random.nextDouble() * 10d, player.getZ() + random.nextDouble() * 80d - 40d);
			if (ent instanceof PrimedLTNT tnt) {
				tnt.setFuse(120);
			}
			if (ent instanceof LivingPrimedLTNT tnt) {
				tnt.setTNTFuse(120);
			}
			server.addFreshEntity(ent);
		}
	}
}
