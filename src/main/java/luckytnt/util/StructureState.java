package luckytnt.util;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import luckytnt.tnteffects.StructureTNTEffect;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.AncientCityStructurePieces;
import net.minecraft.data.worldgen.BastionPieces;
import net.minecraft.data.worldgen.DesertVillagePools;
import net.minecraft.data.worldgen.PillagerOutpostPools;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.data.worldgen.SavannaVillagePools;
import net.minecraft.data.worldgen.SnowyVillagePools;
import net.minecraft.data.worldgen.TaigaVillagePools;
import net.minecraft.data.worldgen.TrailRuinsStructurePools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressStructure;

public enum StructureState implements StringRepresentable {
	
	PILLAGER_OUTPOST("pillager_outpost", (entity, templatePools) -> {
		return new JigsawStructure(settings(entity, StructureState.PILLAGER_OUTPOST_OVERRIDES, GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN), templatePools.getHolderOrThrow(PillagerOutpostPools.START), 7, ConstantHeight.of(VerticalAnchor.absolute((int)entity.y())), true);
	}),
	MINESHAFT("mineshaft", (entity, templatePools) -> {
		return new StructureTNTEffect.Mineshaft(settings(entity, GenerationStep.Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.NONE), entity);
	}),
	MANSION("mansion", (entity, templatePools) -> {
		return new StructureTNTEffect.WoodlandMansion(settings(entity), entity);
	}),
	JUNGLE_TEMPLE("jungle_temple", (entity, templatePools) -> {
		return new StructureTNTEffect.JungleTemple(settings(entity), entity);
	}),
	DESERT_PYRAMID("desert_pyramid", (entity, templatePools) -> {
		return new StructureTNTEffect.DesertPyramid(settings(entity), entity);
	}),
	SHIPWRECK("shipwreck", (entity, templatePools) -> {
		return new StructureTNTEffect.Shipwreck(settings(entity), entity);
	}),
	STRONGHOLD("stronghold", (entity, templatePools) -> {
		return new StructureTNTEffect.Stronghold(settings(entity, TerrainAdjustment.BURY), entity);
	}),
	MONUMENT("monument", (entity, templatePools) -> {
		return new StructureTNTEffect.Monument(settings(entity, StructureState.MONUMENT_OVERRIDES, GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE), entity);
	}),
	OCEAN_RUIN("ocean_ruin", (entity, templatePools) -> {
		return new StructureTNTEffect.OceanRuin(settings(entity), entity);
	}),
	FORTRESS("fortress", (entity, templatePools) -> {
		return new StructureTNTEffect.Fortress(settings(entity, StructureState.FORTRESS_OVERRIDES, GenerationStep.Decoration.UNDERGROUND_DECORATION, TerrainAdjustment.NONE), entity);
	}),
	END_CITY("end_city", (entity, templatePools) -> {
		return new StructureTNTEffect.EndCity(settings(entity), entity);
	}),
	BASTION("bastion", (entity, templatePools) -> {
		return new JigsawStructure(settings(entity), templatePools.getHolderOrThrow(BastionPieces.START), 6, ConstantHeight.of(VerticalAnchor.absolute((int)entity.y())), false);
	}),
	VILLAGE_PLAINS("village_plains", (entity, templatePools) -> {
		return new JigsawStructure(villageSettings(entity), templatePools.getHolderOrThrow(PlainVillagePools.START), 6, ConstantHeight.of(VerticalAnchor.absolute((int)entity.y())), true);
	}),
	VILLAGE_DESERT("village_desert", (entity, templatePools) -> {
		return new JigsawStructure(villageSettings(entity), templatePools.getHolderOrThrow(DesertVillagePools.START), 6, ConstantHeight.of(VerticalAnchor.absolute((int)entity.y())), true);
	}),
	VILLAGE_SAVANNA("village_savanna", (entity, templatePools) -> {
		return new JigsawStructure(villageSettings(entity), templatePools.getHolderOrThrow(SavannaVillagePools.START), 6, ConstantHeight.of(VerticalAnchor.absolute((int)entity.y())), true);
	}),
	VILLAGE_SNOWY("village_snowy", (entity, templatePools) -> {
		return new JigsawStructure(villageSettings(entity), templatePools.getHolderOrThrow(SnowyVillagePools.START), 6, ConstantHeight.of(VerticalAnchor.absolute((int)entity.y())), true);
	}),
	VILLAGE_TAIGA("village_taiga", (entity, templatePools) -> {
		return new JigsawStructure(villageSettings(entity), templatePools.getHolderOrThrow(TaigaVillagePools.START), 6, ConstantHeight.of(VerticalAnchor.absolute((int)entity.y())), true);
	}),
	ANCIENT_CITY("ancient_city", (entity, templatePools) -> {
		return new JigsawStructure(settings(entity, StructureState.ANCIENT_CITY_OVERRIDES, GenerationStep.Decoration.UNDERGROUND_DECORATION, TerrainAdjustment.BEARD_BOX), templatePools.getHolderOrThrow(AncientCityStructurePieces.START), Optional.of(new ResourceLocation("city_anchor")), 7, ConstantHeight.of(VerticalAnchor.absolute((int)entity.y())), false, Optional.empty(), 116);
	}),
	TRAIL_RUINS("trail_ruins", (entity, templatePools) -> {
		return new JigsawStructure(settings(entity, Map.of(), GenerationStep.Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.BURY), templatePools.getHolderOrThrow(TrailRuinsStructurePools.START), 7, ConstantHeight.of(VerticalAnchor.absolute((int)entity.y())), false);
	});

	private static final Map<MobCategory, StructureSpawnOverride> FORTRESS_OVERRIDES = Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, NetherFortressStructure.FORTRESS_ENEMIES));
	private static final Map<MobCategory, StructureSpawnOverride> MONUMENT_OVERRIDES = Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create(new MobSpawnSettings.SpawnerData(EntityType.GUARDIAN, 1, 2, 4))), MobCategory.UNDERGROUND_WATER_CREATURE, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, MobSpawnSettings.EMPTY_MOB_LIST), MobCategory.AXOLOTLS, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, MobSpawnSettings.EMPTY_MOB_LIST));
	private static final Map<MobCategory, StructureSpawnOverride> PILLAGER_OUTPOST_OVERRIDES = Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create(new MobSpawnSettings.SpawnerData(EntityType.PILLAGER, 1, 1, 1))));
	private static final Map<MobCategory, StructureSpawnOverride> ANCIENT_CITY_OVERRIDES = Arrays.stream(MobCategory.values()).collect(Collectors.toMap(category -> {
		return category;
	}, category -> {
		return new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create());
	}));
	
	private final String name;
	private final StructureFactory factory;
	
	private StructureState(String name, StructureFactory factory) {
		this.name = name;
		this.factory = factory;
	}
	
	@NotNull
	public Structure getStructure(IExplosiveEntity entity) {
		Registry<StructureTemplatePool> templatePools = entity.getLevel().registryAccess().registryOrThrow(Registries.TEMPLATE_POOL);
		return factory.create(entity, templatePools);
	}
 	
	public String getSerializedName() {
		return name;
	}
	
	public static StructureState next(StructureState state) {
		return StructureState.values()[(state.ordinal() + 1) % StructureState.values().length];
	}
	
	public static StructureState byName(String name) {
		for (StructureState structure : StructureState.values()) {
			if (name.equals(structure.getSerializedName())) {
				return structure;
			}
		}
		return PILLAGER_OUTPOST;
	}
	
	private static StructureSettings settings(IExplosiveEntity entity, Map<MobCategory, StructureSpawnOverride> spawnOverrides, GenerationStep.Decoration step, TerrainAdjustment terrainAdaptation) {
		return new StructureSettings(HolderSet.direct(entity.getLevel().registryAccess().registryOrThrow(Registries.BIOME).holders().toList()), spawnOverrides, step, terrainAdaptation);
	}
	
	private static StructureSettings settings(IExplosiveEntity entity, GenerationStep.Decoration step, TerrainAdjustment terrainAdaptation) {
		return settings(entity, Map.of(), step, terrainAdaptation);
	}
	
	private static StructureSettings settings(IExplosiveEntity entity, TerrainAdjustment terrainAdaptation) {
		return settings(entity, GenerationStep.Decoration.SURFACE_STRUCTURES, terrainAdaptation);
	}
	
	private static StructureSettings settings(IExplosiveEntity entity) {
		return settings(entity, TerrainAdjustment.NONE);
	}
	
	private static StructureSettings villageSettings(IExplosiveEntity entity) {
		return settings(entity, TerrainAdjustment.BEARD_THIN);
	}
	
	@FunctionalInterface
	public static interface StructureFactory {
		
		@NotNull
		Structure create(IExplosiveEntity entity, Registry<StructureTemplatePool> templatePools);
	}
}
