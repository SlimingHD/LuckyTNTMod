package luckytnt.registry;

import luckytnt.LuckyTNTMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class LuckyTNTTabs {

	public static CreativeModeTab NORMAL_TNT;
	public static CreativeModeTab GOD_TNT;
	public static CreativeModeTab DOOMSDAY_TNT;
	public static CreativeModeTab DYNAMITE;
	public static CreativeModeTab MINECART;
	public static CreativeModeTab OTHER;
	
	@SubscribeEvent
	public static void registerTabs(RegisterEvent event) {
		NORMAL_TNT = CreativeModeTab.builder().title(Component.translatable("item_group.luckytntmod.normal_tnt")).icon(() -> new ItemStack(BlockRegistry.METEOR_TNT.get())).displayItems((enabledFlags, populator) -> {
			for(RegistryObject<? extends Item> item : LuckyTNTMod.RH.creativeTabItemLists.get("n")) {
				populator.accept(item.get());
			}
        }).withTabsBefore(new ResourceLocation("spawn_eggs")).withTabsAfter(new ResourceLocation(LuckyTNTMod.MODID, "god_tnt")).build();
		
		GOD_TNT = CreativeModeTab.builder().title(Component.translatable("item_group.luckytntmod.god_tnt")).icon(() -> new ItemStack(BlockRegistry.THE_REVOLUTION.get())).displayItems((enabledFlags, populator) -> {
			for(RegistryObject<? extends Item> item : LuckyTNTMod.RH.creativeTabItemLists.get("g")) {
				populator.accept(item.get());
			}
        }).withTabsBefore(new ResourceLocation(LuckyTNTMod.MODID, "normal_tnt")).withTabsAfter(new ResourceLocation(LuckyTNTMod.MODID, "doomsday_tnt")).build();
		
		DOOMSDAY_TNT = CreativeModeTab.builder().title(Component.translatable("item_group.luckytntmod.doomsday_tnt")).icon(() -> new ItemStack(BlockRegistry.CHUNK_TNT.get())).displayItems((enabledFlags, populator) -> {
			for(RegistryObject<? extends Item> item : LuckyTNTMod.RH.creativeTabItemLists.get("d")) {
				populator.accept(item.get());
			}
        }).withTabsBefore(new ResourceLocation(LuckyTNTMod.MODID, "god_tnt")).withTabsAfter(new ResourceLocation(LuckyTNTMod.MODID, "dynamite")).build();
		
		DYNAMITE = CreativeModeTab.builder().title(Component.translatable("item_group.luckytntmod.dynamite")).icon(() -> new ItemStack(ItemRegistry.DYNAMITE.get())).displayItems((enabledFlags, populator) -> {
			for(RegistryObject<? extends Item> item : LuckyTNTMod.RH.creativeTabItemLists.get("dy")) {
				populator.accept(item.get());
			}
        }).withTabsBefore(new ResourceLocation(LuckyTNTMod.MODID, "doomsday_tnt")).withTabsAfter(new ResourceLocation(LuckyTNTMod.MODID, "minecarts")).build();
		
		MINECART = CreativeModeTab.builder().title(Component.translatable("item_group.luckytntmod.minecarts")).icon(() -> new ItemStack(ItemRegistry.TNT_X5_MINECART.get())).displayItems((enabledFlags, populator) -> {
			for(RegistryObject<? extends Item> item : LuckyTNTMod.RH.creativeTabItemLists.get("m")) {
				populator.accept(item.get());
			}
        }).withTabsBefore(new ResourceLocation(LuckyTNTMod.MODID, "dynamite")).withTabsAfter(new ResourceLocation(LuckyTNTMod.MODID, "other")).build();
		
		OTHER = CreativeModeTab.builder().title(Component.translatable("item_group.luckytntmod.other")).icon(() -> new ItemStack(ItemRegistry.BLUE_CANDY.get())).displayItems((enabledFlags, populator) -> {
			populator.accept(ItemRegistry.NUCLEAR_WASTE.get());
			populator.accept(ItemRegistry.RED_CANDY.get());
			populator.accept(ItemRegistry.GREEN_CANDY.get());
			populator.accept(ItemRegistry.BLUE_CANDY.get());
			populator.accept(ItemRegistry.PURPLE_CANDY.get());
			populator.accept(ItemRegistry.YELLOW_CANDY.get());			
			populator.accept(ItemRegistry.URANIUM_INGOT.get());			
			populator.accept(ItemRegistry.ANTIMATTER.get());			
			populator.accept(ItemRegistry.URANIUM_ORE.get());			
			populator.accept(ItemRegistry.DEEPSLATE_URANIUM_ORE.get());			
			populator.accept(ItemRegistry.GUNPOWDER_ORE.get());			
			populator.accept(ItemRegistry.DEEPSLATE_GUNPOWDER_ORE.get());
			populator.accept(ItemRegistry.CONFIGURATION_WAND.get());
			populator.accept(ItemRegistry.OBSIDIAN_RAIL.get());
			populator.accept(ItemRegistry.OBSIDIAN_POWERED_RAIL.get());
			populator.accept(ItemRegistry.OBSIDIAN_ACTIVATOR_RAIL.get());
			populator.accept(ItemRegistry.OBSIDIAN_DETECTOR_RAIL.get());
			populator.accept(ItemRegistry.DEATH_RAY_RAY.get());
			populator.accept(ItemRegistry.VACUUM_CLEANER.get());
			populator.accept(ItemRegistry.TOXIC_STONE.get());
        }).withTabsBefore(new ResourceLocation(LuckyTNTMod.MODID, "minecarts")).build();
		
		
		event.register(Registries.CREATIVE_MODE_TAB, new ResourceLocation(LuckyTNTMod.MODID, "normal_tnt"), () -> NORMAL_TNT);
		event.register(Registries.CREATIVE_MODE_TAB, new ResourceLocation(LuckyTNTMod.MODID, "god_tnt"), () -> GOD_TNT);
		event.register(Registries.CREATIVE_MODE_TAB, new ResourceLocation(LuckyTNTMod.MODID, "doomsday_tnt"), () -> DOOMSDAY_TNT);
		event.register(Registries.CREATIVE_MODE_TAB, new ResourceLocation(LuckyTNTMod.MODID, "dynamite"), () -> DYNAMITE);
		event.register(Registries.CREATIVE_MODE_TAB, new ResourceLocation(LuckyTNTMod.MODID, "minecarts"), () -> MINECART);
		event.register(Registries.CREATIVE_MODE_TAB, new ResourceLocation(LuckyTNTMod.MODID, "other"), () -> OTHER);
	}
}
