package luckytnt.util;

import net.minecraft.network.chat.contents.TranslatableContents;

public enum CustomTNTConfig {	

	NO_EXPLOSION(new TranslatableContents("luckytntmod.config.no_tnt")),
	NORMAL_EXPLOSION(new TranslatableContents("luckytntmod.config.normal_tnt")),
	SPHERICAL_EXPLOSION(new TranslatableContents("luckytntmod.config.spherical_tnt")),
	CUBICAL_EXPLOSION(new TranslatableContents("luckytntmod.config.cubical_tnt")),
	EASTER_EGG(new TranslatableContents("luckytntmod.config.easter_egg_tnt")),
	FIREWORK(new TranslatableContents("luckytntmod.config.firework_tnt"));
	
	private final TranslatableContents name;
	
	private CustomTNTConfig(TranslatableContents name) {
		this.name = name;
	}
	
	public String getName() {
		return name.getKey();
	}
	
	public TranslatableContents getComponent() {
		return name;
	}
}
