package luckytnt.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class LuckyTNTConfigValues {
	
	public static ForgeConfigSpec.IntValue ISLAND_HEIGHT;
	public static ForgeConfigSpec.IntValue DROP_HEIGHT;
	
	public static void registerConfig(ForgeConfigSpec.Builder builder) {
		builder.comment("TNT Settings").push("Offsets");
		ISLAND_HEIGHT = builder.comment("Y offset of floating islands").defineInRange("islandHeight", 50, 20, 160);
		DROP_HEIGHT = builder.comment("").defineInRange("dropHeight", 200, 60, 400);
		builder.pop();
	}
}
