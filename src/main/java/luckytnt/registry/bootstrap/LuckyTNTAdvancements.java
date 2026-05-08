package luckytnt.registry.bootstrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import luckytnt.LuckyTNTMod;
import luckytnt.registry.BlockRegistry;
import luckytnt.registry.ItemRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.util.advancement.Craft100Condition;
import luckytntlib.block.LTNTBlock;
import luckytntlib.item.LDynamiteItem;
import luckytntlib.item.LTNTMinecartItem;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.LootTableTrigger;
import net.minecraft.advancements.critereon.RecipeCraftedTrigger;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider.AdvancementGenerator;
import net.minecraftforge.registries.RegistryObject;

public class LuckyTNTAdvancements implements AdvancementGenerator {

	@Override
	public void generate(Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
		Advancement root = advancement(null, display(Blocks.TNT, "root", new ResourceLocation(LuckyTNTMod.MODID, "textures/block/gunpowder_ore.png"), FrameType.TASK, false, false, false), AdvancementKeys.ROOT, saver, existingFileHelper, Map.of("aquire_tnt", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.TNT)), false);

		Advancement itGlowsInTheDark = advancement(root, ItemRegistry.URANIUM_INGOT.get(), FrameType.TASK, AdvancementKeys.IT_GLOWS_IN_THE_DARK, saver, existingFileHelper, Map.of("aquire_uranium_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.URANIUM_INGOT.get())));
		Advancement antithesis = advancement(itGlowsInTheDark, ItemRegistry.ANTIMATTER.get(), FrameType.GOAL, AdvancementKeys.ANTITHESIS, saver, existingFileHelper, Map.of("aquire_antimatter", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ANTIMATTER.get())));
		Advancement strangerThings = advancement(antithesis, ItemRegistry.STRANGE_MATTER.get(), FrameType.CHALLENGE, AdvancementKeys.STRANGER_THINGS, saver, existingFileHelper, Map.of("aquire_strange_matter", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STRANGE_MATTER.get())));
		Advancement manualOverride = advancement(root, ItemRegistry.CONFIGURATION_WAND.get(), FrameType.GOAL, AdvancementKeys.MANUAL_OVERRIDE, "use_config_wand", saver, existingFileHelper);
		advancement(root, ItemRegistry.DEATH_RAY_RAY.get(), FrameType.GOAL, AdvancementKeys.NEVER_GONNA_GIVE_YOU_UP, "break_bedrock_with_death_ray_ray", saver, existingFileHelper);
		advancement(root, ItemRegistry.VACUUM_CLEANER.get(), FrameType.GOAL, AdvancementKeys.INTENTIONAL_MALFUNCTION, "suck_toxic_cloud_away", saver, existingFileHelper);
		
		Advancement realEstateRookie = advancement(root, BlockRegistry.WOOD_HOUSE_TNT.get(), FrameType.TASK, AdvancementKeys.REAL_ESTATE_ROOKIE, "explode_any_house_tnt", saver, existingFileHelper);
		advancement(root, BlockRegistry.DIGGING_TNT.get(), FrameType.CHALLENGE, AdvancementKeys.SIZE_MATTERS, "small_digging_tnt_hole", saver, existingFileHelper);
		advancement(root, BlockRegistry.TIMER_TNT.get(), FrameType.CHALLENGE, AdvancementKeys.BAD_TIMING, "hit_by_timer_tnt_or_dynamite", saver, existingFileHelper);
		advancement(root, BlockRegistry.EASTER_EGG.get(), FrameType.TASK, AdvancementKeys.FALSE_ADVERTISING, "explode_easter_egg", saver, existingFileHelper);
		advancement(root, BlockRegistry.DAY_TNT.get(), FrameType.TASK, AdvancementKeys.DAYLIGHT_SAVINGS, "explode_night_or_day_tnt", saver, existingFileHelper);
		Advancement rickrolled = advancement(root, BlockRegistry.SAY_GOODBYE.get(), FrameType.TASK, AdvancementKeys.RICKROLLED, "die_by_say_goodbye", saver, existingFileHelper);
		advancement(root, BlockRegistry.PUMPKIN_BOMB.get(), FrameType.TASK, AdvancementKeys.TRICK_OR_TREAT, "explode_pumpkin_bomb", saver, existingFileHelper);
		advancement(root, BlockRegistry.XRAY_TNT.get(), FrameType.TASK, AdvancementKeys.I_CAN_SEE_CLEARLY_NOW, "explode_xray_tnt", saver, existingFileHelper);
		Advancement allThatGlitters = advancement(root, BlockRegistry.BUTTER_TNT.get(), FrameType.TASK, AdvancementKeys.ALL_THAT_GLITTERS, "explode_butter_tnt", saver, existingFileHelper);
		Advancement restInPieces = advancement(root, BlockRegistry.GRAVEYARD_TNT.get(), FrameType.TASK, AdvancementKeys.REST_IN_PIECES, "explode_graveyard_tnt", saver, existingFileHelper);
		advancement(root, BlockRegistry.PICKY_TNT.get(), FrameType.TASK, AdvancementKeys.PATTERN_RECOGNITION, "explode_picky_tnt", saver, existingFileHelper);
		advancement(restInPieces, Blocks.COBWEB, FrameType.GOAL, AdvancementKeys.INDIANA_JONES, saver, existingFileHelper, Map.of("loot_grave_rare", LootTableTrigger.TriggerInstance.lootTableUsed(new ResourceLocation(LuckyTNTMod.MODID, "chests/grave_loot_rare"))));
		Advancement dejaVu = advancement(root, BlockRegistry.REPLAY_TNT.get(), FrameType.TASK, AdvancementKeys.DEJA_VU, "explode_replay_tnt", saver, existingFileHelper);
		advancement(root, BlockRegistry.CHRISTMAS_TNT.get(), FrameType.TASK, AdvancementKeys.TIS_THE_SEASON, "explode_christmas_tnt", saver, existingFileHelper);
		Advancement mixUp = advancement(root, BlockRegistry.GROVE_TNT.get(), FrameType.TASK, AdvancementKeys.MIX_UP, "explode_grove_tnt", saver, existingFileHelper);
		
		//should be with cross-category but is needed for other advancements as root
		Map<String, CriterionTriggerInstance> theGoodTheBadAndTheUglyCriteria = Map.of(
			"aquire_tnt_firework", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.TNT_FIREWORK.get()),
			"aquire_sand_firework", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.SAND_FIREWORK.get()),
			"aquire_gravel_firework", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.GRAVEL_FIREWORK.get()),
			"aquire_rainbow_firework", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.RAINBOW_FIREWORK.get())
		);
		Advancement theGoodTheBadAndTheUgly = advancement(root, BlockRegistry.GRAVEL_FIREWORK.get(), FrameType.TASK, AdvancementKeys.THE_GOOD_THE_BAD_AND_THE_UGLY, saver, existingFileHelper, theGoodTheBadAndTheUglyCriteria, new String[][]{{"aquire_sand_firework", "aquire_rainbow_firework"}, {"aquire_tnt_firework"}, {"aquire_gravel_firework"}});
		
		Advancement realEstateAgent = advancement(realEstateRookie, BlockRegistry.MANKINDS_MARK.get(), FrameType.TASK, AdvancementKeys.REAL_ESTATE_AGENT, "explode_mankinds_mark", saver, existingFileHelper);
		advancement(itGlowsInTheDark, BlockRegistry.DUST_BOWL.get(), FrameType.TASK, AdvancementKeys.SURFIN_IN_THE_USA, "explode_dust_bowl", saver, existingFileHelper);
		advancement(itGlowsInTheDark, BlockRegistry.THE_REVOLUTION.get(), FrameType.TASK, AdvancementKeys.TURNING_POINT, "explode_the_revolution", saver, existingFileHelper);
		advancement(itGlowsInTheDark, BlockRegistry.POMPEII.get(), FrameType.TASK, AdvancementKeys.ASHES_TO_ASHES, "explode_pompeii", saver, existingFileHelper);
		advancement(itGlowsInTheDark, BlockRegistry.CHICXULUB.get(), FrameType.TASK, AdvancementKeys.BAD_DAY, "explode_chicxulub", saver, existingFileHelper);
		advancement(itGlowsInTheDark, BlockRegistry.UNBREAKABLE_TNT.get(), FrameType.TASK, AdvancementKeys.BEDROCK_EDITION, "explode_unbreakable_tnt", saver, existingFileHelper);
		Advancement bigIvan = advancement(itGlowsInTheDark, BlockRegistry.TSAR_BOMBA.get(), FrameType.TASK, AdvancementKeys.BIG_IVAN, "explode_tsar_bomba", saver, existingFileHelper);
		advancement(allThatGlitters, BlockRegistry.MIDAS_TNT.get(), FrameType.TASK, AdvancementKeys.THE_GOLDEN_TOUCH, "explode_midas_tnt", saver, existingFileHelper);
		advancement(theGoodTheBadAndTheUgly, BlockRegistry.NEW_YEARS_FIREWORK.get(), FrameType.CHALLENGE, AdvancementKeys.RESOLUTION, "explode_new_years_firework_on_new_years", saver, existingFileHelper);
		advancement(itGlowsInTheDark, BlockRegistry.REVERSED_TNT.get(), FrameType.TASK, AdvancementKeys.WHAT_GOES_UP, "explode_reversed_tnt", saver, existingFileHelper);
		advancement(manualOverride, BlockRegistry.GOTTHARD_TUNNEL.get(), FrameType.TASK, AdvancementKeys.STREET_LEGAL, "explode_gotthard_tunnel_with_streets", saver, existingFileHelper);
		advancement(itGlowsInTheDark, BlockRegistry.WORLD_OF_WOOLS.get(), FrameType.TASK, AdvancementKeys.OVER_THE_RAINBOW, "explode_world_of_wools", saver, existingFileHelper);
		advancement(itGlowsInTheDark, BlockRegistry.PRESENT_DROP.get(), FrameType.TASK, AdvancementKeys.BURN_IT_DOWN, "explode_present_drop_with_block_destruction", saver, existingFileHelper);
		Advancement reachForTheStars = advancement(mixUp, BlockRegistry.HYPERION.get(), FrameType.TASK, AdvancementKeys.REACH_FOR_THE_STARS, "explode_hyperion", saver, existingFileHelper);
		advancement(itGlowsInTheDark, BlockRegistry.LIGHTNING_STORM.get(), FrameType.TASK, AdvancementKeys.UNLIMITED_POWER, "explode_lightning_storm", saver, existingFileHelper);
		advancement(itGlowsInTheDark, BlockRegistry.PARTICLE_PHYSICS_TNT.get(), FrameType.TASK, AdvancementKeys.OH_MY_PC, "explode_particle_physics_tnt", saver, existingFileHelper);
		advancement(dejaVu, BlockRegistry.RESET_TNT.get(), FrameType.TASK, AdvancementKeys.RESURRECTION, "have_reset_tnt_revive_an_entity_that_died", saver, existingFileHelper);

		advancement(rickrolled, BlockRegistry.EXTINCTION.get(), FrameType.GOAL, AdvancementKeys.NAIL_IN_THE_COFFIN, "die_by_extinction", saver, existingFileHelper);
		advancement(realEstateAgent, BlockRegistry.MANSION.get(), FrameType.GOAL, AdvancementKeys.REAL_ESTATE_MASTER, "explode_mansion", saver, existingFileHelper);
		advancement(antithesis, BlockRegistry.DEATH_RAY.get(), FrameType.GOAL, AdvancementKeys.ORBITAL_STRIKE, "explode_death_ray", saver, existingFileHelper);
		advancement(reachForTheStars, BlockRegistry.JUNGLE_TNT.get(), FrameType.GOAL, AdvancementKeys.REFORESTATION, "explode_jungle_tnt", saver, existingFileHelper);
		advancement(antithesis, BlockRegistry.ATLANTIS.get(), FrameType.GOAL, AdvancementKeys.THE_LOST_EMPIRE, "explode_atlantis", saver, existingFileHelper);
		advancement(antithesis, BlockRegistry.BLACK_HOLE_TNT.get(), FrameType.GOAL, AdvancementKeys.WHAT_LIES_BEYOND, "explode_black_hole_tnt", saver, existingFileHelper);
		advancement(antithesis, BlockRegistry.ILLUMINATI_TNT.get(), FrameType.GOAL, AdvancementKeys.ALL_SEEING, "explode_illuminati_tnt", saver, existingFileHelper);
		Advancement sharedDelusion = advancement(antithesis, BlockRegistry.AETHER_TNT.get(), FrameType.GOAL, AdvancementKeys.SHARED_DELUSION, "explode_aether_tnt", saver, existingFileHelper);
		advancement(bigIvan, BlockRegistry.HYDROGEN_BOMB.get(), FrameType.GOAL, AdvancementKeys.BOUNDLESS_INCOMPETENCE, "explode_hydrogen_bomb", saver, existingFileHelper);
		advancement(theGoodTheBadAndTheUgly, BlockRegistry.CITY_FIREWORK.get(), FrameType.GOAL, AdvancementKeys.ORGANIZED_CHAOS, "explode_city_firework", saver, existingFileHelper);
		advancement(theGoodTheBadAndTheUgly, BlockRegistry.CUSTOM_FIREWORK.get(), FrameType.CHALLENGE, AdvancementKeys.FEEDBACK_LOOP, "light_custom_firework_atop_custom_firework", saver, existingFileHelper);
		advancement(antithesis, BlockRegistry.TNT_X10000.get(), FrameType.GOAL, AdvancementKeys.OVER_9000, "explode_tnt_x10000", saver, existingFileHelper);
		
		advancement(strangerThings, BlockRegistry.CHROMATIC_TNT.get(), FrameType.CHALLENGE, AdvancementKeys.CHROMATIC_ABERRATION, "explode_chromatic_tnt", saver, existingFileHelper);
		advancement(strangerThings, BlockRegistry.SUPERFLAT_TNT.get(), FrameType.CHALLENGE, AdvancementKeys.ENDLESS_EXPANSES, "explode_superflat_tnt", saver, existingFileHelper);
		advancement(strangerThings, BlockRegistry.HYPERNOVA.get(), FrameType.CHALLENGE, AdvancementKeys.STAR_KILLER, "explode_hypernova", saver, existingFileHelper);
		advancement(strangerThings, BlockRegistry.MOONFALL.get(), FrameType.CHALLENGE, AdvancementKeys.WHAT_WOULD_ELON_DO, "explode_moonfall", saver, existingFileHelper);

		Advancement bullseye = advancement(root, ItemRegistry.HOMING_DYNAMITE.get(), FrameType.TASK, AdvancementKeys.BULLSEYE, "hit_entity_with_homing_dynamite", saver, existingFileHelper);
		
		advancement(root, BlockRegistry.WALKING_TNT.get(), FrameType.TASK, AdvancementKeys.LIFE_FINDS_A_WAY, "explode_any_living_tnt", saver, existingFileHelper);
		advancement(root, BlockRegistry.FARMING_TNT.get(), FrameType.GOAL, AdvancementKeys.FROZEN_HARVEST, "explode_any_farming_tnt_in_frozen_biome", saver, existingFileHelper);
		Map<String, CriterionTriggerInstance> theDieIsCastCriteria = Map.of(
			"aquire_lucky_tnt", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.LUCKY_TNT.get()),
			"aquire_lucky_god", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.LUCKY_GOD.get()),
			"aquire_lucky_doomsday", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.LUCKY_DOOMSDAY.get()),
			"aquire_lucky_annihilation", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.LUCKY_ANNIHILATION.get()),
			"aquire_lucky_dynamite", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.LUCKY_DYNAMITE.get()),
			"aquire_lucky_tnt_minecart", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.LUCKY_TNT_MINECART.get())
		);
		advancement(root, BlockRegistry.LUCKY_TNT.get(), FrameType.GOAL, AdvancementKeys.THE_DIE_IS_CAST, saver, existingFileHelper, theDieIsCastCriteria, true);
		advancement(root, BlockRegistry.SENSOR_TNT.get(), FrameType.GOAL, AdvancementKeys.WHAT_GOES_AROUND, "die_by_any_sensor_tnt_of_your_own", saver, existingFileHelper);
		Map<String, CriterionTriggerInstance> privateIslandsCriteria = Map.of(
			"aquire_floating_island", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.FLOATING_ISLAND.get()),
			"aquire_heavens_gate", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.HEAVENS_GATE.get()),
			"aquire_hells_gate", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.HELLS_GATE.get()),
			"aquire_end_gate", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.END_GATE.get()),
			"aquire_reversed_tnt", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.REVERSED_TNT.get()),
			"aquire_aether_tnt", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.AETHER_TNT.get())
		);
		advancement(sharedDelusion, BlockRegistry.FLOATING_ISLAND.get(), FrameType.CHALLENGE, AdvancementKeys.PRIVATE_ISLANDS, saver, existingFileHelper, privateIslandsCriteria);
		Map<String, CriterionTriggerInstance> theWorldAtYourFeetsCriteria = new HashMap<>();
		for (RegistryObject<Block> block : LuckyTNTMod.blockRegistry.getEntries()) {
			if (block.get() != BlockRegistry.TNT.get() && block.get() instanceof LTNTBlock b) {
				theWorldAtYourFeetsCriteria.put("aquire_" + block.getId().getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(b));
			}
		}
		advancement(strangerThings, BlockRegistry.TNT_X5.get(), FrameType.CHALLENGE, AdvancementKeys.THE_WORLD_AT_YOUR_FEET, saver, existingFileHelper, theWorldAtYourFeetsCriteria);
		Map<String, CriterionTriggerInstance> stickWithItCriteria = new HashMap<>();
		for (RegistryObject<Item> item : LuckyTNTMod.itemRegistry.getEntries()) {
			if (item.getId().getPath().contains("dynamite") && item.get() instanceof LDynamiteItem l) {
				stickWithItCriteria.put("aquire_" + item.getId().getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(l));
			}
		}
		advancement(bullseye, ItemRegistry.DYNAMITE_X5.get(), FrameType.CHALLENGE, AdvancementKeys.STICK_WITH_IT, saver, existingFileHelper, stickWithItCriteria);
		Map<String, CriterionTriggerInstance> offTheRailsCriteria = new HashMap<>();
		for (RegistryObject<Item> item : LuckyTNTMod.itemRegistry.getEntries()) {
			if (item.getId().getPath().contains("minecart") && item.get() instanceof LTNTMinecartItem l) {
				offTheRailsCriteria.put("aquire_" + item.getId().getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(l));
			}
		}
		advancement(root, ItemRegistry.TNT_X5_MINECART.get(), FrameType.CHALLENGE, AdvancementKeys.OFF_THE_RAILS, saver, existingFileHelper, offTheRailsCriteria);
		Map<String, CriterionTriggerInstance> godsAmongMenCriteria = Map.of(
			"aquire_aether_tnt", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.AETHER_TNT.get()),
			"aquire_phobos", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.PHOBOS.get()),
			"aquire_deimos", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.DEIMOS.get()),
			"aquire_poseidons_wave", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.POSEIDONS_WAVE.get()),
			"aquire_hyperion", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.HYPERION.get())
		);
		advancement(antithesis, BlockRegistry.POSEIDONS_WAVE.get(), FrameType.CHALLENGE, AdvancementKeys.GODS_AMONG_MEN, saver, existingFileHelper, godsAmongMenCriteria);
		Map<String, CriterionTriggerInstance> shape101Criteria = Map.of(
			"aquire_sphere_tnt", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.SPHERE_TNT.get()),
			"aquire_cubic_tnt", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.CUBIC_TNT.get()),
			"aquire_prism_tnt", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.PRISM_TNT.get())
		);
		advancement(root, BlockRegistry.SPHERE_TNT.get(), FrameType.TASK, AdvancementKeys.SHAPE_101, saver, existingFileHelper, shape101Criteria);
		advancement(root, BlockRegistry.TNT_X100.get(), FrameType.CHALLENGE, AdvancementKeys.TOO_MUCH_TNT, saver, existingFileHelper, Map.of("craft_tnt_x100_100_times", new RecipeCraftedTrigger.TriggerInstance(ContextAwarePredicate.create(Craft100Condition.INSTANCE), new ResourceLocation(LuckyTNTMod.MODID, "craft_tnt_x100"), List.of())));
		Map<String, CriterionTriggerInstance> gottaHaveABlastCriteria = Map.of(
			"aquire_tnt_firework", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.TNT_FIREWORK.get()),
			"aquire_sand_firework", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.SAND_FIREWORK.get()),
			"aquire_gravel_firework", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.GRAVEL_FIREWORK.get()),
			"aquire_rainbow_firework", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.RAINBOW_FIREWORK.get()),
			"aquire_new_years_firework", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.NEW_YEARS_FIREWORK.get()),
			"aquire_item_firework", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.ITEM_FIREWORK.get()),
			"aquire_entity_firework", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.ENTITY_FIREWORK.get()),
			"aquire_city_firework", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.CITY_FIREWORK.get()),
			"aquire_custom_firework", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.CUSTOM_FIREWORK.get()),
			"aquire_grande_finale", InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.GRANDE_FINALE.get())
		);
		advancement(theGoodTheBadAndTheUgly, BlockRegistry.ENTITY_FIREWORK.get(), FrameType.CHALLENGE, AdvancementKeys.GOTTA_HAVE_A_BLAST, saver, existingFileHelper, gottaHaveABlastCriteria);
		advancement(root, BlockRegistry.ROULETTE_TNT.get(), FrameType.GOAL, AdvancementKeys.GAMBLING, "hit_by_any_roulette_tnt_without_dying", saver, existingFileHelper);
		advancement(itGlowsInTheDark, BlockRegistry.GHOST_TNT.get(), FrameType.GOAL, AdvancementKeys.BACKFIRE, "die_by_own_ghost_tnt", saver, existingFileHelper);
		advancement(root, BlockRegistry.BOUNCING_TNT.get(), FrameType.GOAL, AdvancementKeys.HOP_TIL_YOU_DROP, "die_by_any_bouncing_tnt", saver, existingFileHelper);
	}
	
	private static Advancement advancement(@Nullable Advancement parent, ItemLike item, FrameType frame, ResourceLocation key, String criterionName, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
		return advancement(parent, item, frame, key, saver, existingFileHelper, Map.of(criterionName, new ImpossibleTrigger.TriggerInstance()));
	}
	
	private static Advancement advancement(@Nullable Advancement parent, ItemLike item, FrameType frame, ResourceLocation key, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper, Map<String, CriterionTriggerInstance> criteria) {
		return advancement(parent, item, frame, key, saver, existingFileHelper, criteria, false);
	}
	
	private static Advancement advancement(@Nullable Advancement parent, ItemLike item, FrameType frame, ResourceLocation key, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper, Map<String, CriterionTriggerInstance> criteria, boolean or) {
		return advancement(parent, display(item, key.getPath().replace(LuckyTNTMod.MODID + "/", ""), frame), key, saver, existingFileHelper, criteria, or);
	}
	
	private static Advancement advancement(@Nullable Advancement parent, DisplayInfo display, ResourceLocation key, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper, Map<String, CriterionTriggerInstance> criteria, boolean or) {
		Advancement.Builder builder = Advancement.Builder.advancement().parent(parent).display(display);
		for (Map.Entry<String, CriterionTriggerInstance> criterion : criteria.entrySet()) {
			builder.addCriterion(criterion.getKey(), criterion.getValue());
		}
		if (or) {
			builder.requirements(RequirementsStrategy.OR);
		}
		return builder.save(saver, key, existingFileHelper);
	}
	
	private static Advancement advancement(@Nullable Advancement parent, ItemLike item, FrameType frame, ResourceLocation key, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper, Map<String, CriterionTriggerInstance> criteria, String[][] requirements) {
		Advancement.Builder builder = Advancement.Builder.advancement().parent(parent).display(display(item, key.getPath().replace(LuckyTNTMod.MODID + "/", ""), frame));
		for (Map.Entry<String, CriterionTriggerInstance> criterion : criteria.entrySet()) {
			builder.addCriterion(criterion.getKey(), criterion.getValue());
		}
		builder.requirements(requirements);
		return builder.save(saver, key, existingFileHelper);
	}
	
	private static DisplayInfo display(ItemLike item, String name, FrameType frame) {
		return display(item, name, null, frame, true, true, false);
	}
	
	private static DisplayInfo display(ItemLike item, String name, @Nullable ResourceLocation background, FrameType frame, boolean showToast, boolean announceChat, boolean hide) {
		return new DisplayInfo(new ItemStack(item), Component.translatable("advancement.luckytntmod." + name + ".title"), Component.translatable("advancement.luckytntmod." + name + ".description"), background, frame, showToast, announceChat, hide);
	}
}
