package luckytnt.config;

import luckytnt.util.CustomTNTConfig;
import net.minecraftforge.common.ForgeConfigSpec;

public class LuckyTNTConfigValues {
	
	public static ForgeConfigSpec.IntValue DROP_HEIGHT;
	public static ForgeConfigSpec.IntValue MAXIMUM_DISASTER_TIME;
	public static ForgeConfigSpec.DoubleValue AVERAGE_DIASTER_INTENSITY;
	
	public static ForgeConfigSpec.EnumValue<CustomTNTConfig> CUSTOM_TNT_FIRST_EXPLOSION;
	public static ForgeConfigSpec.IntValue CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY;
	
	public static ForgeConfigSpec.EnumValue<CustomTNTConfig> CUSTOM_TNT_SECOND_EXPLOSION;
	public static ForgeConfigSpec.IntValue CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY;
	
	public static ForgeConfigSpec.EnumValue<CustomTNTConfig> CUSTOM_TNT_THIRD_EXPLOSION;
	public static ForgeConfigSpec.IntValue CUSTOM_TNT_THIRD_EXPLOSION_INTENSITY;

	public static ForgeConfigSpec.BooleanValue SEASON_EVENTS_ALWAYS_ACTIVE;
	
	public static ForgeConfigSpec.BooleanValue RENDER_CONTAMINATED_OVERLAY;
	
	public static ForgeConfigSpec.BooleanValue PRESENT_DROP_DESTROY_BLOCKS;
	
	public static void registerConfig(ForgeConfigSpec.Builder builder) {
		builder.comment("TNT settings").push("Offsets");
		DROP_HEIGHT = builder.comment("y offset of dropped projectiles").defineInRange("dropHeight", 200, 60, 400);
		builder.pop();
		builder.comment("Disaster settings").push("Disasters");
		MAXIMUM_DISASTER_TIME  = builder.comment("Maximum duration of disasters in minutes").defineInRange("maximumTime", 12, 2, 24);
		AVERAGE_DIASTER_INTENSITY = builder.comment("Multiplier for the destructive capabilities of disasters").defineInRange("averageIntensity", 1d, 1d, 10d);
		builder.pop();
		builder.comment("Custom TNT settings").push("First explosion");
		CUSTOM_TNT_FIRST_EXPLOSION = builder.comment("Explosion effect of the first explosion").defineEnum("explosionType", CustomTNTConfig.NO_EXPLOSION);
		CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY = builder.comment("Multiplier for the power of the first explosion").defineInRange("explosionIntensity", 1, 1, 20);
		builder.pop();
		builder.push("Second explosion");
		CUSTOM_TNT_SECOND_EXPLOSION = builder.comment("Explosion effect of the second explosion").defineEnum("explosionType", CustomTNTConfig.NO_EXPLOSION);
		CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY = builder.comment("Multiplier for the power of the second explosion").defineInRange("explosionIntensity", 1, 1, 20);
		builder.pop();
		builder.push("Third explosion");
		CUSTOM_TNT_THIRD_EXPLOSION = builder.comment("Explosion effect of the third explosion").defineEnum("explosionType", CustomTNTConfig.NO_EXPLOSION);
		CUSTOM_TNT_THIRD_EXPLOSION_INTENSITY = builder.comment("Multiplier for the power of the third explosion").defineInRange("explosionIntensity", 1, 1, 20);
		builder.pop();
		builder.comment("Special events settings").push("Event settings");
		SEASON_EVENTS_ALWAYS_ACTIVE = builder.comment("Whether season specific events ignore the date").define("seasonEventsAlwaysActive", false);
		PRESENT_DROP_DESTROY_BLOCKS = builder.comment("Whether the Present Drop destroys blocks").define("presentDropDestroy", true);
		builder.pop();
		builder.comment("Overlay settings").push("Render settings");
		RENDER_CONTAMINATED_OVERLAY = builder.comment("Whether an overlay is rendererd while the contaminated effect is active").define("renderContaminatedOverlay", true);
		builder.pop();
	}
	
	public static CustomTNTConfig getCustomTNTExplosion(int level) {
		switch (level) {
			case 0: return CUSTOM_TNT_FIRST_EXPLOSION.get();
			case 1: return CUSTOM_TNT_SECOND_EXPLOSION.get();
			case 2: return CUSTOM_TNT_THIRD_EXPLOSION.get();
		}
		return CustomTNTConfig.NO_EXPLOSION;
	}
	
	public static int getCustomTNTExplosionIntensity(int level) {
		switch (level) {
			case 0: return CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY.get();
			case 1: return CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY.get();
			case 2: return CUSTOM_TNT_THIRD_EXPLOSION_INTENSITY.get();
		}
		return 0;
	}
}
