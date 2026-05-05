package luckytnt.registry;

import java.util.List;
import java.util.Set;

import luckytnt.LuckyTNTMod;
import luckytnt.registry.bootstrap.FeatureBootstrap;
import luckytnt.registry.bootstrap.LuckyTNTAdvancements;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LuckyTNTMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerationHandler {
	
	private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.CONFIGURED_FEATURE, (ctx) -> FeatureBootstrap.bootstrapConfigured(ctx));
	
	@SubscribeEvent
	public static void onGatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();

		generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(output, event.getLookupProvider(), BUILDER, Set.of(LuckyTNTMod.MODID)));
		generator.addProvider(event.includeServer(), new ForgeAdvancementProvider(output, event.getLookupProvider(), event.getExistingFileHelper(), List.of(new LuckyTNTAdvancements())));
	}
}
