package luckytnt.registry.keys;

import luckytnt.LuckyTNTMod;
import net.minecraft.resources.ResourceLocation;

public class AdvancementKeys {

	public static ResourceLocation ROOT = createAdvancement("root");
	public static ResourceLocation REAL_ESTATE_ROOKIE = createAdvancement("real_estate_rookie");
	public static ResourceLocation REAL_ESTATE_AGENT = createAdvancement("real_estate_agent");
	public static ResourceLocation REAL_ESTATE_MASTER = createAdvancement("real_estate_master");
	
	private static ResourceLocation createAdvancement(String name) {
		return new ResourceLocation(LuckyTNTMod.MODID, LuckyTNTMod.MODID + "/" + name);
	}
}
