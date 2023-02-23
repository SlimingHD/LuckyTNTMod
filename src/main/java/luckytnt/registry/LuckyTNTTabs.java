package luckytnt.registry;

import java.util.List;

import luckytnt.LuckyTNTMod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
	public static void registerTabs(CreativeModeTabEvent.Register event) {
		NORMAL_TNT = event.registerCreativeModeTab(new ResourceLocation(LuckyTNTMod.MODID, "normal_tnt"), List.of(new ResourceLocation(LuckyTNTMod.MODID, "god_tnt")), List.of(new ResourceLocation("spawn_eggs")), builder -> builder.title(Component.translatable("item_group.luckytntmod.normal_tnt")).icon(() -> new ItemStack(BlockRegistry.METEOR_TNT.get())).displayItems((enabledFlags, populator, hasPermissions) -> {
			for(RegistryObject<? extends Item> item : LuckyTNTMod.RH.creativeTabItemLists.get("n")) {
				populator.accept(item.get());
			}
        }));
		GOD_TNT = event.registerCreativeModeTab(new ResourceLocation(LuckyTNTMod.MODID, "god_tnt"), List.of(new ResourceLocation(LuckyTNTMod.MODID, "doomsday_tnt")), List.of(new ResourceLocation(LuckyTNTMod.MODID, "normal_tnt")), builder -> builder.title(Component.translatable("item_group.luckytntmod.god_tnt")).icon(() -> new ItemStack(BlockRegistry.THE_REVOLUTION.get())).displayItems((enabledFlags, populator, hasPermissions) -> {
			for(RegistryObject<? extends Item> item : LuckyTNTMod.RH.creativeTabItemLists.get("g")) {
				populator.accept(item.get());
			}
        }));
		DOOMSDAY_TNT = event.registerCreativeModeTab(new ResourceLocation(LuckyTNTMod.MODID, "doomsday_tnt"), List.of(new ResourceLocation(LuckyTNTMod.MODID, "dynamite")), List.of(new ResourceLocation(LuckyTNTMod.MODID, "god_tnt")), builder -> builder.title(Component.translatable("item_group.luckytntmod.doomsday_tnt")).icon(() -> new ItemStack(BlockRegistry.CHUNK_TNT.get())).displayItems((enabledFlags, populator, hasPermissions) -> {
			for(RegistryObject<? extends Item> item : LuckyTNTMod.RH.creativeTabItemLists.get("d")) {
				populator.accept(item.get());
			}
        }));
		DYNAMITE = event.registerCreativeModeTab(new ResourceLocation(LuckyTNTMod.MODID, "dynamite"), List.of(new ResourceLocation(LuckyTNTMod.MODID, "minecarts")), List.of(new ResourceLocation(LuckyTNTMod.MODID, "doomsday_tnt")), builder -> builder.title(Component.translatable("item_group.luckytntmod.dynamite")).icon(() -> new ItemStack(ItemRegistry.DYNAMITE.get())).displayItems((enabledFlags, populator, hasPermission) -> {
			for(RegistryObject<? extends Item> item : LuckyTNTMod.RH.creativeTabItemLists.get("dy")) {
				populator.accept(item.get());
			}
		}));
		MINECART = event.registerCreativeModeTab(new ResourceLocation(LuckyTNTMod.MODID, "minecarts"), List.of(new ResourceLocation(LuckyTNTMod.MODID, "other")), List.of(new ResourceLocation(LuckyTNTMod.MODID, "dynamite")), builder -> builder.title(Component.translatable("item_group.luckytntmod.minecarts")).icon(() -> new ItemStack(ItemRegistry.TNT_X5_MINECART.get())).displayItems((enabledFlags, populator, hasPermission) -> {
			for(RegistryObject<? extends Item> item : LuckyTNTMod.RH.creativeTabItemLists.get("m")) {
				populator.accept(item.get());
			}
		}));
		OTHER = event.registerCreativeModeTab(new ResourceLocation(LuckyTNTMod.MODID, "other"), List.of(), List.of(new ResourceLocation(LuckyTNTMod.MODID, "minecarts")), builder -> builder.title(Component.translatable("item_group.luckytntmod.other")).icon(() -> new ItemStack(ItemRegistry.BLUE_CANDY.get())).displayItems((enabledFlags, populator, hasPermissions) -> {
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
		}));
	}
}
