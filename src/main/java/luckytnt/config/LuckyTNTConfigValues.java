package luckytnt.config;

import luckytnt.util.CustomTNTConfig;
import net.minecraftforge.common.ForgeConfigSpec;

public class LuckyTNTConfigValues {
	
	public static ForgeConfigSpec.IntValue ISLAND_HEIGHT;
	public static ForgeConfigSpec.IntValue DROP_HEIGHT;
	
	public static ForgeConfigSpec.EnumValue<CustomTNTConfig> CUSTOM_TNT_FIRST_EXPLOSION;
	public static ForgeConfigSpec.IntValue CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY;
	
	public static ForgeConfigSpec.EnumValue<CustomTNTConfig> CUSTOM_TNT_SECOND_EXPLOSION;
	public static ForgeConfigSpec.IntValue CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY;
	
	public static ForgeConfigSpec.EnumValue<CustomTNTConfig> CUSTOM_TNT_THIRD_EXPLOSION;
	public static ForgeConfigSpec.IntValue CUSTOM_TNT_THIRD_EXPLOSION_INTENSITY;
	
	public static void registerConfig(ForgeConfigSpec.Builder builder) {
		builder.comment("TNT Settings").push("Offsets");
		ISLAND_HEIGHT = builder.comment("Y offset of floating islands").defineInRange("islandHeight", 50, 20, 160);
		DROP_HEIGHT = builder.comment("Y offset of dropped projectiles").defineInRange("dropHeight", 60, 200, 400);
		builder.pop();
		builder.comment("Custom TNT Settings").push("First Explosion");
		CUSTOM_TNT_FIRST_EXPLOSION = builder.comment("Explosion Effect of the first Explosion").defineEnum("explosionType", CustomTNTConfig.NO_EXPLOSION);
		CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY = builder.comment("Multiplier to the power of the first explosion").defineInRange("explosionIntensity", 1, 1, 20);
		builder.pop();
		builder.push("Second Explosion");
		CUSTOM_TNT_SECOND_EXPLOSION = builder.comment("Explosion Effect of the second Explosion").defineEnum("explosionType", CustomTNTConfig.NO_EXPLOSION);
		CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY = builder.comment("Multiplier to the power of the second explosion").defineInRange("explosionIntensity", 1, 1, 20);
		builder.pop();
		builder.push("Third Explosion");
		CUSTOM_TNT_THIRD_EXPLOSION = builder.comment("Explosion Effect of the third Explosion").defineEnum("explosionType", CustomTNTConfig.NO_EXPLOSION);
		CUSTOM_TNT_THIRD_EXPLOSION_INTENSITY = builder.comment("Multiplier to the power of the third explosion").defineInRange("explosionIntensity", 1, 1, 20);
		builder.pop();
	}
}
