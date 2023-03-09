package luckytnt.registry;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColorRegistry {
	
	@SubscribeEvent
	public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
		event.register((BlockState state, BlockAndTintGetter tint, BlockPos pos, int color) -> {
			return tint != null && pos != null ? BiomeColors.getAverageGrassColor(tint, pos) : GrassColor.get(0.5D, 1D);
		}, BlockRegistry.CUSTOM_FIREWORK.get());
	}
	
	@SubscribeEvent
	public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
		event.register((ItemStack stack, int color) -> {
			return GrassColor.get(0.5D, 1D);
		}, BlockRegistry.CUSTOM_FIREWORK.get());
	}
}
