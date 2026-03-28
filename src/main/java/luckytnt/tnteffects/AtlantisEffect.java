package luckytnt.tnteffects;

import luckytnt.event.LevelEvents;
import luckytnt.explosionrules.FilterOffYExplosionRule;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterSurfaceExplosionRule;
import luckytntlib.util.explosions.rules.SimpleExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public class AtlantisEffect extends PrimedTNTEffect {
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (entity.getTNTFuse() == 240) {
			if (entity.getLevel() instanceof ServerLevel s_Level) {
	      		s_Level.setWeatherParameters(0, 10000, true, true);
	      	}
	      	entity.getLevel().playSound(null, entity.x(), entity.y(), entity.z(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, 1000, 1);
		}
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ServerLevel serverLevel = (ServerLevel)entity.getLevel();
		LevelEvents.setBiomeInCylinder(serverLevel, entity.getPos(), 100, Biomes.WARM_OCEAN);
		
		ExplosionHelper.createCylindricalCrater(serverLevel, entity.getPos(), 100, 50, 100f, new StackedExplosionRule(
				new FilterAirExplosionRule(new FilterSurfaceExplosionRule(true, new SimpleExplosionRule(Blocks.SAND.defaultBlockState()))),
				new FilterOffYExplosionRule(-50, 8, new SimpleExplosionRule(Blocks.WATER.defaultBlockState()))
		));
		
		Registry<Structure> structures = serverLevel.registryAccess().registryOrThrow(Registries.STRUCTURE);
		Structure oceanRuin = structures.get(BuiltinStructures.OCEAN_RUIN_WARM);
		ExplosionHelper.customSurfaceExplosion(serverLevel, entity.getPos(), 50, (level, center, pos, state) -> {
			if (level.getRandom().nextDouble() < 0.0005d) {
				StructureStart start = oceanRuin.generate(serverLevel.registryAccess(), serverLevel.getChunkSource().getGenerator(), serverLevel.getChunkSource().getGenerator().getBiomeSource(), serverLevel.getChunkSource().randomState(), serverLevel.getStructureManager(), serverLevel.getSeed(), new ChunkPos(pos), 20, entity.getLevel(), biomeHolder -> true);
				start.placeInChunk(serverLevel, serverLevel.structureManager(), serverLevel.getChunkSource().getGenerator(), RandomSource.create(), new BoundingBox(Mth.floor(entity.x()) - 150, Mth.floor(entity.y()) - 150, Mth.floor(entity.z()) - 150, Mth.floor(entity.x()) + 150, Mth.floor(entity.y()) + 150, Mth.floor(entity.z()) + 150), new ChunkPos(pos));			
			}
		});
		
		RandomSource random = entity.getLevel().getRandom();
		for (int count = 0; count < 40; count++) {
			Entity squid = new Squid(EntityType.SQUID, entity.getLevel());
			squid.setPos(entity.x() + random.nextDouble() * 100d - 50d, entity.y() + 8, entity.z() + random.nextDouble() * 100d - 50d);
			entity.getLevel().addFreshEntity(squid);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.SPLASH, entity.x(), entity.y() + 1.5f, entity.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ATLANTIS.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 240;
	}
}
