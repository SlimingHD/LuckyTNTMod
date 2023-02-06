package luckytnt.registry;

import luckytnt.LuckyTNTMod;
import luckytnt.feature.Altar;
import luckytnt.feature.Grave;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class FeatureRegistry {

	public static final RegistryObject<Feature<?>> graves = LuckyTNTMod.featureRegistry.register("graves", () -> new Grave(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<?>> altar = LuckyTNTMod.featureRegistry.register("altar", () -> new Altar(NoneFeatureConfiguration.CODEC));
}
