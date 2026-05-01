package luckytnt.registry;

import luckytnt.LuckyTNTMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class LuckyTNTTabs {

	public static final ResourceLocation NORMAL_TNT_LOCATION = new ResourceLocation(LuckyTNTMod.MODID, "normal_tnt");
	public static final ResourceLocation GOD_TNT_LOCATION = new ResourceLocation(LuckyTNTMod.MODID, "god_tnt");
	public static final ResourceLocation DOOMSDAY_TNT_LOCATION = new ResourceLocation(LuckyTNTMod.MODID, "doomsday_tnt");
	public static final ResourceLocation ANNIHILATION_TNT_LOCATION = new ResourceLocation(LuckyTNTMod.MODID, "annihilation_tnt");
	public static final ResourceLocation DYNAMITE_LOCATION = new ResourceLocation(LuckyTNTMod.MODID, "dynamite");
	public static final ResourceLocation MINECART_LOCATION = new ResourceLocation(LuckyTNTMod.MODID, "minecarts");
	public static final ResourceLocation OTHER_LOCATION = new ResourceLocation(LuckyTNTMod.MODID, "other");
	
	public static CreativeModeTab NORMAL_TNT;
	public static CreativeModeTab GOD_TNT;
	public static CreativeModeTab DOOMSDAY_TNT;
	public static CreativeModeTab ANNIHILATION_TNT;
	public static CreativeModeTab DYNAMITE;
	public static CreativeModeTab MINECART;
	public static CreativeModeTab OTHER;
	
	@SubscribeEvent
	public static void registerTabs(RegisterEvent event) {
		NORMAL_TNT = CreativeModeTab.builder().title(Component.translatable("item_group.luckytntmod.normal_tnt")).icon(() -> new ItemStack(BlockRegistry.METEOR_TNT.get())).displayItems((enabledFlags, populator) -> {
			for(RegistryObject<? extends Item> item : LuckyTNTMod.RH.creativeTabItemLists.get("n")) {
				populator.accept(item.get());
			}
        }).withTabsBefore(CreativeModeTabs.SPAWN_EGGS).withTabsAfter(GOD_TNT_LOCATION).build();
		
		GOD_TNT = CreativeModeTab.builder().title(Component.translatable("item_group.luckytntmod.god_tnt")).icon(() -> new ItemStack(BlockRegistry.THE_REVOLUTION.get())).displayItems((enabledFlags, populator) -> {
			for(RegistryObject<? extends Item> item : LuckyTNTMod.RH.creativeTabItemLists.get("g")) {
				populator.accept(item.get());
			}
        }).withTabsBefore(NORMAL_TNT_LOCATION).withTabsAfter(DOOMSDAY_TNT_LOCATION).build();
		
		DOOMSDAY_TNT = CreativeModeTab.builder().title(Component.translatable("item_group.luckytntmod.doomsday_tnt")).icon(() -> new ItemStack(BlockRegistry.CHUNK_TNT.get())).displayItems((enabledFlags, populator) -> {
			for(RegistryObject<? extends Item> item : LuckyTNTMod.RH.creativeTabItemLists.get("d")) {
				populator.accept(item.get());
			}
        }).withTabsBefore(GOD_TNT_LOCATION).withTabsAfter(ANNIHILATION_TNT_LOCATION).build();
		
		ANNIHILATION_TNT = CreativeModeTab.builder().title(Component.translatable("item_group.luckytntmod.annihilation_tnt")).icon(() -> new ItemStack(BlockRegistry.CHROMATIC_TNT.get())).displayItems((enabledFlags, populator) -> {
			for(RegistryObject<? extends Item> item : LuckyTNTMod.RH.creativeTabItemLists.get("a")) {
				populator.accept(item.get());
			}
        }).withTabsBefore(DOOMSDAY_TNT_LOCATION).withTabsAfter(DYNAMITE_LOCATION).build();
		
		DYNAMITE = CreativeModeTab.builder().title(Component.translatable("item_group.luckytntmod.dynamite")).icon(() -> new ItemStack(ItemRegistry.DYNAMITE.get())).displayItems((enabledFlags, populator) -> {
			for(RegistryObject<? extends Item> item : LuckyTNTMod.RH.creativeTabItemLists.get("dy")) {
				populator.accept(item.get());
			}
        }).withTabsBefore(ANNIHILATION_TNT_LOCATION).withTabsAfter(MINECART_LOCATION).build();
		
		MINECART = CreativeModeTab.builder().title(Component.translatable("item_group.luckytntmod.minecarts")).icon(() -> new ItemStack(ItemRegistry.TNT_X5_MINECART.get())).displayItems((enabledFlags, populator) -> {
			for(RegistryObject<? extends Item> item : LuckyTNTMod.RH.creativeTabItemLists.get("m")) {
				populator.accept(item.get());
			}
        }).withTabsBefore(DYNAMITE_LOCATION).withTabsAfter(OTHER_LOCATION).build();
		
		OTHER = CreativeModeTab.builder().title(Component.translatable("item_group.luckytntmod.other")).icon(() -> new ItemStack(ItemRegistry.BLUE_CANDY.get())).displayItems((enabledFlags, populator) -> {
			populator.accept(ItemRegistry.NUCLEAR_WASTE.get());
			populator.accept(ItemRegistry.RED_CANDY.get());
			populator.accept(ItemRegistry.GREEN_CANDY.get());
			populator.accept(ItemRegistry.BLUE_CANDY.get());
			populator.accept(ItemRegistry.PURPLE_CANDY.get());
			populator.accept(ItemRegistry.YELLOW_CANDY.get());			
			populator.accept(ItemRegistry.URANIUM_INGOT.get());			
			populator.accept(ItemRegistry.ANTIMATTER.get());	
			populator.accept(ItemRegistry.STRANGE_MATTER.get());		
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
        }).withTabsBefore(MINECART_LOCATION).build();
		
		
		event.register(Registries.CREATIVE_MODE_TAB, NORMAL_TNT_LOCATION, () -> NORMAL_TNT);
		event.register(Registries.CREATIVE_MODE_TAB, GOD_TNT_LOCATION, () -> GOD_TNT);
		event.register(Registries.CREATIVE_MODE_TAB, DOOMSDAY_TNT_LOCATION, () -> DOOMSDAY_TNT);
		event.register(Registries.CREATIVE_MODE_TAB, ANNIHILATION_TNT_LOCATION, () -> ANNIHILATION_TNT);
		event.register(Registries.CREATIVE_MODE_TAB, DYNAMITE_LOCATION, () -> DYNAMITE);
		event.register(Registries.CREATIVE_MODE_TAB, MINECART_LOCATION, () -> MINECART);
		event.register(Registries.CREATIVE_MODE_TAB, OTHER_LOCATION, () -> OTHER);
	}
}
