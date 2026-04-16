package luckytnt.registry;

import javax.annotation.Nullable;

import luckytnt.LuckyTNTMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class LuckyTNTDamageSources {

	private static final ResourceKey<DamageType> HAILSTONE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(LuckyTNTMod.MODID, "hailstone"));
	private static final ResourceKey<DamageType> SAY_GOODBYE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(LuckyTNTMod.MODID, "say_goodbye"));
	private static final ResourceKey<DamageType> EXTINCTION = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(LuckyTNTMod.MODID, "extinction"));
	private static final ResourceKey<DamageType> EXTINCTION_NO_SOURCE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(LuckyTNTMod.MODID, "extinction_no_source"));
	private static final ResourceKey<DamageType> DEATH_RAY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(LuckyTNTMod.MODID, "death_ray"));
	private static final ResourceKey<DamageType> DEATH_RAY_NO_SOURCE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(LuckyTNTMod.MODID, "death_ray_no_source"));

	public static DamageSource hailstone(Level level) {
		return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(HAILSTONE), null, null);
	}
	
	public static DamageSource sayGoodbye(Level level) {
		return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(SAY_GOODBYE), null, null);
	}
	
	public static DamageSource extinction(Level level, @Nullable Entity directSource) {
		if (directSource == null) {
			return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(EXTINCTION_NO_SOURCE), null, null);
		}
		return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(EXTINCTION), directSource, null);
	}
	
	public static DamageSource deathRay(Level level, @Nullable Entity directSource) {
		if (directSource == null) {
			return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DEATH_RAY_NO_SOURCE), null, null);
		}
		return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DEATH_RAY), directSource, null);
	}
}
