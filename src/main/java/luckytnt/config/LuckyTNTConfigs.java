package luckytnt.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class LuckyTNTConfigs {

	public static void register() {
		registerCommonConfig();
	}
	
	public static void registerCommonConfig() {
		ForgeConfigSpec.Builder S_BUILDER = new ForgeConfigSpec.Builder();
		LuckyTNTConfigValues.registerConfig(S_BUILDER);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, S_BUILDER.build());
	}
}
