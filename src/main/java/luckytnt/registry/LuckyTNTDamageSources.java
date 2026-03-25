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
	
	public static DamageSource hailstone(Level level, @Nullable Entity directSource, @Nullable Entity indirectSource) {
		return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(HAILSTONE), directSource, indirectSource);
	}
}
