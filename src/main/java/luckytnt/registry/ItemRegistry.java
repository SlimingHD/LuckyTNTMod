package luckytnt.registry;

import java.util.List;

import javax.annotation.Nullable;

import luckytnt.LuckyTNTMod;
import luckytntlib.item.LDynamiteItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistry {

	//Dynamite
	public static final RegistryObject<LDynamiteItem> DYNAMITE = LuckyTNTMod.RH.registerDynamiteItem("dynamite", EntityRegistry.DYNAMITE, "dy");
	public static final RegistryObject<LDynamiteItem> DYNAMITE_X5 = LuckyTNTMod.RH.registerDynamiteItem("dynamite_x5", EntityRegistry.DYNAMITE_X5, "dy");
	public static final RegistryObject<LDynamiteItem> DYNAMITE_X20 = LuckyTNTMod.RH.registerDynamiteItem("dynamite_x20", EntityRegistry.DYNAMITE_X20, "dy");
	public static final RegistryObject<LDynamiteItem> DYNAMITE_X100 = LuckyTNTMod.RH.registerDynamiteItem("dynamite_x100", EntityRegistry.DYNAMITE_X100, "dy");
	public static final RegistryObject<LDynamiteItem> DYNAMITE_X500 = LuckyTNTMod.RH.registerDynamiteItem("dynamite_x500", EntityRegistry.DYNAMITE_X500, "dy");
	public static final RegistryObject<LDynamiteItem> FIRE_DYNAMITE = LuckyTNTMod.RH.registerDynamiteItem("fire_dynamite", EntityRegistry.FIRE_DYNAMITE, "dy");
	public static final RegistryObject<LDynamiteItem> SNOW_DYNAMITE = LuckyTNTMod.RH.registerDynamiteItem("snow_dynamite", EntityRegistry.SNOW_DYNAMITE, "dy");
	public static final RegistryObject<LDynamiteItem> DYNAMITE_FIREWORK = LuckyTNTMod.RH.registerDynamiteItem("dynamite_firework", EntityRegistry.DYNAMITE_FIREWORK, "dy");
	public static final RegistryObject<LDynamiteItem> NUCLEAR_DYNAMITE = LuckyTNTMod.RH.registerDynamiteItem("nuclear_dynamite", EntityRegistry.NUCLEAR_DYNAMITE, "dy");
	public static final RegistryObject<LDynamiteItem> FREEZE_DYNAMITE = LuckyTNTMod.RH.registerDynamiteItem("freeze_dynamite", EntityRegistry.FREEZE_DYNAMITE, "dy");
	public static final RegistryObject<LDynamiteItem> FLOATING_DYNAMITE = LuckyTNTMod.RH.registerDynamiteItem("floating_dynamite", EntityRegistry.FLOATING_DYNAMITE, "dy");
	public static final RegistryObject<LDynamiteItem> SPHERE_DYNAMITE = LuckyTNTMod.RH.registerDynamiteItem("sphere_dynamite", EntityRegistry.SPHERE_DYNAMITE, "dy");
	public static final RegistryObject<LDynamiteItem> FLAT_DYNAMITE = LuckyTNTMod.RH.registerDynamiteItem("flat_dynamite", EntityRegistry.FLAT_DYNAMITE, "dy");
	public static final RegistryObject<LDynamiteItem> MININGFLAT_DYNAMITE = LuckyTNTMod.RH.registerDynamiteItem("miningflat_dynamite", EntityRegistry.MININGFLAT_DYNAMITE, "dy");
	public static final RegistryObject<LDynamiteItem> VAPORIZE_DYNAMITE = LuckyTNTMod.RH.registerDynamiteItem("vaporize_dynamite", EntityRegistry.VAPORIZE_DYNAMITE, "dy");
	public static final RegistryObject<LDynamiteItem> METEOR_DYNAMITE = LuckyTNTMod.RH.registerDynamiteItem("meteor_dynamite", EntityRegistry.METEOR_DYNAMITE, "dy");
	public static final RegistryObject<LDynamiteItem> CUBIC_DYNAMITE = LuckyTNTMod.RH.registerDynamiteItem("cubic_dynamite", EntityRegistry.CUBIC_DYNAMITE, "dy");
	public static final RegistryObject<LDynamiteItem> GROVE_DYNAMITE = LuckyTNTMod.RH.registerDynamiteItem("grove_dynamite", EntityRegistry.GROVE_DYNAMITE, "dy");
	public static final RegistryObject<LDynamiteItem> ENDER_DYNAMITE = LuckyTNTMod.RH.registerDynamiteItem("ender_dynamite", EntityRegistry.ENDER_DYNAMITE, "dy");
	public static final RegistryObject<LDynamiteItem> ARROW_DYNAMITE = LuckyTNTMod.RH.registerDynamiteItem("arrow_dynamite", EntityRegistry.ARROW_DYNAMITE, "dy");
	public static final RegistryObject<LDynamiteItem> LIGHTNING_DYNAMITE = LuckyTNTMod.RH.registerDynamiteItem("lightning_dynamite", EntityRegistry.LIGHTNING_DYNAMITE, "dy");
	public static final RegistryObject<LDynamiteItem> DIGGING_DYNAMITE = LuckyTNTMod.RH.registerDynamiteItem("digging_dynamite", EntityRegistry.DIGGING_DYNAMITE, "dy");
	public static final RegistryObject<LDynamiteItem> COMPACT_DYNAMITE = LuckyTNTMod.RH.registerDynamiteItem("compact_dynamite", EntityRegistry.COMPACT_DYNAMITE, "dy");
	
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
	public static final RegistryObject<Item> CONFIGURATION_WAND = LuckyTNTMod.itemRegistry.register("configuration_wand", () -> new Item(new Item.Properties().stacksTo(1)) {
		@Override
		public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
			super.appendHoverText(stack, level, components, flag);
			components.add(Component.translatable("item.configuration_wand.info"));
		}
	});
}
