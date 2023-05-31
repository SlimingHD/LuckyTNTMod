package luckytnt.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public enum CustomTNTConfig {	

	NO_EXPLOSION(Component.translatable("luckytntmod.config.no_tnt")),
	NORMAL_EXPLOSION(Component.translatable("luckytntmod.config.normal_tnt")),
	SPHERICAL_EXPLOSION(Component.translatable("luckytntmod.config.spherical_tnt")),
	CUBICAL_EXPLOSION(Component.translatable("luckytntmod.config.cubical_tnt")),
	EASTER_EGG(Component.translatable("luckytntmod.config.easter_egg_tnt")),
	FIREWORK(Component.translatable("luckytntmod.config.firework_tnt"));
	
	private final MutableComponent name;
	
	private CustomTNTConfig(MutableComponent name) {
		this.name = name;
	}
	
	public String getName() {
		return name.getString();
	}
	
	public MutableComponent getComponent() {
		return name;
	}
}
