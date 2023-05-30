package luckytnt.tnteffects;

import com.google.common.base.Predicate;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.level.ChunkPos;
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
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.material.Material;

public class AtlantisEffect extends PrimedTNTEffect {
	
	Predicate<Holder<Biome>> predicate = (holder) -> {
		return true;
	};
	
	@Override
	public void explosionTick(IExplosiveEntity ent) {
		if(ent.getTNTFuse() == 240) {
			if(ent.level() instanceof ServerLevel s_Level) {
	      		s_Level.setWeatherParameters(0, 10000, true, true);
	      	}
	      	ent.level().playSound(null, ent.x(), ent.y(), ent.z(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, 1000, 1);
		}
	}

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		Registry<Biome> registry = ent.level().registryAccess().registryOrThrow(Registry.BIOME_REGISTRY);
		Holder<Biome> biome = new Holder.Direct<Biome>(registry.get(Biomes.WARM_OCEAN));
		for(double offX = -100; offX < 100; offX++) {
			for(double offZ = -100; offZ < 100; offZ++) {
				boolean foundBlock = false;
				double distance = Math.sqrt(offX * offX + offZ * offZ);				
				if(ent.level() instanceof ServerLevel sLevel) {
					if(distance < 100) {
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
					}
					for(ServerPlayer player : sLevel.players()) {
						player.connection.send(new ClientboundLevelChunkWithLightPacket(ent.level().getChunkAt(new BlockPos(ent.x() + offX, 0, ent.z() + offZ)), ent.level().getLightEngine(), null, null, false));
					}
					if(distance < 50) {
						Registry<Structure> structures = ent.level().registryAccess().registryOrThrow(Registry.STRUCTURE_REGISTRY);
						
						Structure ocean_ruin = structures.get(BuiltinStructures.OCEAN_RUIN_WARM);
						
						for(double offY = 320; offY > -64; offY--) {
							BlockPos pos = new BlockPos(ent.x() + offX, ent.y() + offY + 8, ent.z() + offZ);
							BlockState state = ent.level().getBlockState(pos);
							if(!foundBlock && state.isCollisionShapeFullBlock(ent.level(), pos) && state.getMaterial() != Material.AIR) {
								if(Math.random() < 0.0005f) {
									StructureStart start = ocean_ruin.generate(sLevel.registryAccess(), sLevel.getChunkSource().getGenerator(), sLevel.getChunkSource().getGenerator().getBiomeSource(), sLevel.getChunkSource().randomState(), sLevel.getStructureManager(), sLevel.getSeed(), new ChunkPos(pos), 20, ent.level(), predicate);
									start.placeInChunk(sLevel, sLevel.structureManager(), sLevel.getChunkSource().getGenerator(), RandomSource.create(), new BoundingBox((int)ent.x() - 150, (int)ent.y() - 150, (int)ent.z() - 150, (int)ent.x() + 150, (int)ent.y() + 150, (int)ent.z() + 150), new ChunkPos(pos));
								}
								foundBlock = true;
							}
						}
					}
				}
			}
		}
		
		JungleTNTEffect.replaceNonSolidBlockOrVegetationWithAir(ent, 100, 100, true);
		
		ExplosionHelper.doSphericalExplosion(ent.level(), ent.getPos().add(0, 8, 0), 100, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				BlockPos posTop = pos.offset(0, 1, 0);
				BlockState stateTop = level.getBlockState(posTop);
				
				if(((ent.y() + 8) - pos.getY()) >= 0 && ((ent.y() + 8) - pos.getY()) <= 50) {
					if((state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.level())) < 0 || state.getBlock() instanceof LiquidBlock || state.getMaterial() == Material.AIR) && state.getMaterial() != Material.STONE) {
						state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.level()));
						level.setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
					}
					if(stateTop.getMaterial() == Material.WATER && state.getMaterial() != Material.AIR && (state.getBlock() == Blocks.GRASS_BLOCK || state.getBlock() == Blocks.STONE || state.getBlock() == Blocks.DEEPSLATE || state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.GRAVEL) && level.getBlockState(pos.above()).getBlock() != Blocks.SAND) {
						state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.level()));
						level.setBlock(pos, Blocks.SAND.defaultBlockState(), 3);
					}
				}
			}
		});
		
		for(int count = 0; count < 40; count++) {
			Entity squid = new Squid(EntityType.SQUID, ent.level());
			squid.setPos(ent.x() + 50 * Math.random() - 50 * Math.random(), ent.y() + 8, ent.z() + 50 * Math.random() - 50 * Math.random());
			ent.level().addFreshEntity(squid);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.level().addParticle(ParticleTypes.SPLASH, ent.x(), ent.y() + 1.5f, ent.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ATLANTIS.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 240;
	}
}
