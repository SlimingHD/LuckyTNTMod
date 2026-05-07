package luckytnt.registry.keys;

import luckytnt.LuckyTNTMod;
import net.minecraft.resources.ResourceLocation;

public class AdvancementKeys {

	//Root
	public static ResourceLocation ROOT = createAdvancement("root");
	
	//Normal TNTs
	public static ResourceLocation REAL_ESTATE_ROOKIE = createAdvancement("real_estate_rookie");
	public static ResourceLocation SIZE_MATTERS = createAdvancement("size_matters");
	public static ResourceLocation BAD_TIMING = createAdvancement("bad_timing");
	public static ResourceLocation FALSE_ADVERTISING = createAdvancement("false_advertising");
	public static ResourceLocation DAYLIGHT_SAVINGS = createAdvancement("daylight_savings");
	public static ResourceLocation RICKROLLED = createAdvancement("rickrolled");
	public static ResourceLocation TRICK_OR_TREAT = createAdvancement("trick_or_treat");
	public static ResourceLocation I_CAN_SEE_CLEARLY_NOW = createAdvancement("i_can_see_clearly_now");
	public static ResourceLocation ALL_THAT_GLITTERS = createAdvancement("all_that_glitters");
	public static ResourceLocation PATTERN_RECOGNITION = createAdvancement("pattern_recognition");
	public static ResourceLocation REST_IN_PIECES = createAdvancement("rest_in_pieces");
	public static ResourceLocation INDIANA_JONES = createAdvancement("indiana_jones");
	public static ResourceLocation DEJA_VU = createAdvancement("deja_vu");
	public static ResourceLocation TIS_THE_SEASON = createAdvancement("tis_the_season");
	
	//God TNTs
	public static ResourceLocation REAL_ESTATE_AGENT = createAdvancement("real_estate_agent");
	public static ResourceLocation SURFIN_IN_THE_USA = createAdvancement("surfin_in_the_usa");
	public static ResourceLocation TURNING_POINT = createAdvancement("turning_point");
	public static ResourceLocation ASHES_TO_ASHES = createAdvancement("ashes_to_ashes");
	public static ResourceLocation BAD_DAY = createAdvancement("bad_day");
	public static ResourceLocation BEDROCK_EDITION = createAdvancement("bedrock_edition");
	public static ResourceLocation BIG_IVAN = createAdvancement("big_ivan");
	public static ResourceLocation THE_GOLDEN_TOUCH = createAdvancement("the_golden_touch");
	public static ResourceLocation RESOLUTION = createAdvancement("resolution");
	public static ResourceLocation WHAT_GOES_UP = createAdvancement("what_goes_up");
	public static ResourceLocation STREET_LEGAL = createAdvancement("street_legal");
	public static ResourceLocation OVER_THE_RAINBOW = createAdvancement("over_the_rainbow");
	public static ResourceLocation BURN_IT_DOWN = createAdvancement("burn_it_down");
	public static ResourceLocation REACH_FOR_THE_STARS = createAdvancement("reach_for_the_stars");
	public static ResourceLocation UNLIMITED_POWER = createAdvancement("umlimited_power");
	
	//Doomsday TNTs
	public static ResourceLocation NAIL_IN_THE_COFFIN = createAdvancement("nail_in_the_coffin");
	public static ResourceLocation REAL_ESTATE_MASTER = createAdvancement("real_estate_master");
	public static ResourceLocation ORBITAL_STRIKE = createAdvancement("orbital_strike");
	public static ResourceLocation REFORESTATION = createAdvancement("reforestation");
	public static ResourceLocation THE_LOST_EMPIRE = createAdvancement("the_lost_empire");
	public static ResourceLocation WHAT_LIES_BEYOND = createAdvancement("what_lies_beyond");
	public static ResourceLocation ALL_SEEING = createAdvancement("all_seeing");
	public static ResourceLocation SHARED_DELUSION = createAdvancement("shared_delusion");
	public static ResourceLocation BOUNDLESS_INCOMPETENCE = createAdvancement("boundless_incompetence");
	public static ResourceLocation ORGANIZED_CHAOS = createAdvancement("organized_chaos");
	public static ResourceLocation FEEDBACK_LOOP = createAdvancement("feedback_loop");
	public static ResourceLocation OVER_9000 = createAdvancement("over_9000");
	
	//Annihilation TNTs
	public static ResourceLocation CHROMATIC_ABERRATION = createAdvancement("chromatic_aberration");
	public static ResourceLocation ENDLESS_EXPANSES = createAdvancement("endless_expanses");
	public static ResourceLocation STAR_KILLER = createAdvancement("star_killer");
	public static ResourceLocation WHAT_WOULD_ELON_DO = createAdvancement("what_would_elon_do");
	
	//Dynamite
	public static ResourceLocation BULLSEYE = createAdvancement("bullseye");

	//Cross-Category
	public static ResourceLocation LIFE_FINDS_A_WAY = createAdvancement("life_finds_a_way");
	public static ResourceLocation FROZEN_HARVEST = createAdvancement("frozen_harvest");
	public static ResourceLocation THE_DIE_IS_CAST = createAdvancement("the_die_is_cast");
	public static ResourceLocation WHAT_GOES_AROUND = createAdvancement("what_goes_around");
	public static ResourceLocation PRIVATE_ISLANDS = createAdvancement("private_islands");
	public static ResourceLocation THE_WORLD_AT_YOUR_FEET = createAdvancement("the_world_at_your_feet");
	public static ResourceLocation STICK_WITH_IT = createAdvancement("stick_with_it");
	public static ResourceLocation OFF_THE_RAILS = createAdvancement("off_the_rails");
	public static ResourceLocation GODS_AMONG_MEN = createAdvancement("gods_among_men");
	public static ResourceLocation SHAPE_101 = createAdvancement("shape_101");
	public static ResourceLocation TOO_MUCH_TNT = createAdvancement("too_much_tnt");
	
	//Miscellaneous
	public static ResourceLocation IT_GLOWS_IN_THE_DARK = createAdvancement("it_glows_in_the_dark");
	public static ResourceLocation ANTITHESIS = createAdvancement("antithesis");
	public static ResourceLocation STRANGER_THINGS = createAdvancement("stranger_things");
	public static ResourceLocation MANUAL_OVERRIDE = createAdvancement("manual_override");
	public static ResourceLocation NEVER_GONNA_GIVE_YOU_UP = createAdvancement("never_gonna_give_you_up");
	public static ResourceLocation INTENTIONAL_MALFUNCTION = createAdvancement("intentional_malfunction");
	
	
	private static ResourceLocation createAdvancement(String name) {
		return new ResourceLocation(LuckyTNTMod.MODID, LuckyTNTMod.MODID + "/" + name);
	}
}
