package luckytnt.registry;

import luckytnt.LuckyTNTMod;
import luckytntlib.item.LDynamiteItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistry {

	//Dynamite
	public static final RegistryObject<LDynamiteItem> DYNAMITE = LuckyTNTMod.RH.registerDynamiteItem("dynamite", EntityRegistry.DYNAMITE, "dy");
	
	//Other
	public static final RegistryObject<Item> NUCLEAR_WASTE = LuckyTNTMod.itemRegistry.register("nuclear_waste", () -> new BlockItem(BlockRegistry.NUCLEAR_WASTE.get(), new Item.Properties()));
	public static final RegistryObject<Item> RED_CANDY = LuckyTNTMod.itemRegistry.register("red_candy", () -> new Item(new Item.Properties().stacksTo(64).food(new FoodProperties.Builder().alwaysEat().fast().nutrition(1).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 2), 1).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 0), 1).build())));
	public static final RegistryObject<Item> GREEN_CANDY = LuckyTNTMod.itemRegistry.register("green_candy", () -> new Item(new Item.Properties().stacksTo(64).food(new FoodProperties.Builder().alwaysEat().fast().nutrition(1).effect(() -> new MobEffectInstance(MobEffects.JUMP, 200, 2), 1).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 2), 1).build())));
	public static final RegistryObject<Item> BLUE_CANDY = LuckyTNTMod.itemRegistry.register("blue_candy", () -> new Item(new Item.Properties().stacksTo(64).food(new FoodProperties.Builder().alwaysEat().fast().nutrition(1).effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 1200, 0), 1).effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 500, 2), 1).effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 1200, 0), 0).build())));
	public static final RegistryObject<Item> PURPLE_CANDY = LuckyTNTMod.itemRegistry.register("purple_candy", () -> new Item(new Item.Properties().stacksTo(64).food(new FoodProperties.Builder().alwaysEat().fast().nutrition(1).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 3), 1).effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 2000, 1), 1).build())));
	public static final RegistryObject<Item> YELLOW_CANDY = LuckyTNTMod.itemRegistry.register("yellow_candy", () -> new Item(new Item.Properties().stacksTo(64).food(new FoodProperties.Builder().alwaysEat().fast().nutrition(1).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 2000, 3), 1).build())));;
	public static final RegistryObject<Item> URANIUM_INGOT = LuckyTNTMod.itemRegistry.register("uranium_ingot", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> URANIUM_ORE = LuckyTNTMod.itemRegistry.register("uranium_ore", () -> new BlockItem(BlockRegistry.URANIUM_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> DEEPSLATE_URANIUM_ORE = LuckyTNTMod.itemRegistry.register("deepslate_uranium_ore", () -> new BlockItem(BlockRegistry.DEEPSLATE_URANIUM_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> GUNPOWDER_ORE = LuckyTNTMod.itemRegistry.register("gunpowder_ore", () -> new BlockItem(BlockRegistry.GUNPOWDER_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> DEEPSLATE_GUNPOWDER_ORE = LuckyTNTMod.itemRegistry.register("deepslate_gunpowder_ore", () -> new BlockItem(BlockRegistry.DEEPSLATE_GUNPOWDER_ORE.get(), new Item.Properties()));
}
