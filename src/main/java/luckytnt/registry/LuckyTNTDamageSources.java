package luckytnt.registry;

import luckytnt.LuckyTNTMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

public class LuckyTNTDamageSources {

	private static final ResourceKey<DamageType> HAILSTONE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(LuckyTNTMod.MODID, "hailstone"));
	private static final ResourceKey<DamageType> SAY_GOODBYE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(LuckyTNTMod.MODID, "say_goodbye"));
	
	public static DamageSource hailstone(Level level) {
		return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(HAILSTONE), null, null);
	}
	
	public static DamageSource sayGoodbye(Level level) {
		return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(SAY_GOODBYE), null, null);
	}
}
