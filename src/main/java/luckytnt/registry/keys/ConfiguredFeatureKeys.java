package luckytnt.registry.keys;

import luckytnt.LuckyTNTMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class ConfiguredFeatureKeys {

	public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_DARK_OAK = createKey("mega_dark_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_AZALEA = createKey("mega_azalea");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_CHERRY = createKey("mega_cherry");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SUPER_BIRCH_BEES = createKey("super_birch_bees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SUPER_BIRCH_BEES_0002 = createKey("super_birch_bees_0002");
	
	private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(LuckyTNTMod.MODID, name));
	}
}
