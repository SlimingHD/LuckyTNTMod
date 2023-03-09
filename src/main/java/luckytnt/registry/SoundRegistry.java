package luckytnt.registry;

import luckytnt.LuckyTNTMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SoundRegistry {
	
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, LuckyTNTMod.MODID);
	
	public static RegistryObject<SoundEvent> SAY_GOODBYE = register("say_goodbye");
	public static RegistryObject<SoundEvent> DEATH_RAY = register("death_ray");
	public static RegistryObject<SoundEvent> VACUUM_CLEANER_START = register("vacuum_cleaner_start");
	public static RegistryObject<SoundEvent> VACUUM_CLEANER = register("vacuum_cleaner");
	public static RegistryObject<SoundEvent> SPAWN1 = register("spawn1");
	public static RegistryObject<SoundEvent> SPAWN2 = register("spawn2");
	public static RegistryObject<SoundEvent> AMBIENT1 = register("ambient1");
	public static RegistryObject<SoundEvent> AMBIENT2 = register("ambient2");
	public static RegistryObject<SoundEvent> AMBIENT3 = register("ambient3");
	public static RegistryObject<SoundEvent> AMBIENT4 = register("ambient4");
	public static RegistryObject<SoundEvent> AMBIENT5 = register("ambient5");
	public static RegistryObject<SoundEvent> AMBIENT6 = register("ambient6");
	public static RegistryObject<SoundEvent> VAPORIZE_SPAWN = register("vaporize_spawn");
	public static RegistryObject<SoundEvent> ITEMS = register("items");
	public static RegistryObject<SoundEvent> POTATO = register("potato");
	public static RegistryObject<SoundEvent> BLOW_UP_POTATOS = register("blow_up_potatos");
	public static RegistryObject<SoundEvent> WOOD_SWORD = register("wood_sword");	
	public static RegistryObject<SoundEvent> BRENN_UND_STIRB = register("brenn_und_stirb");
	public static RegistryObject<SoundEvent> SCHREI = register("schrei");
	public static RegistryObject<SoundEvent> LACHEN = register("lachen");
	public static RegistryObject<SoundEvent> EVIL_SPAWN1 = register("evil_spawn1");
	public static RegistryObject<SoundEvent> EVIL_SPAWN2 = register("evil_spawn2");
	public static RegistryObject<SoundEvent> SPAWN_VILLAGERS = register("spawn_villagers");
	public static RegistryObject<SoundEvent> KILL_VILLAGERS = register("kill_villagers");
	public static RegistryObject<SoundEvent> NOKILL = register("nokill");
	public static RegistryObject<SoundEvent> SPAWN_ATTACKING = register("spawn_attacking");
	public static RegistryObject<SoundEvent> SPAWN_ZOMBIE = register("spawn_zombie");
	public static RegistryObject<SoundEvent> SPAWN_FLAT = register("spawn_flat");
	public static RegistryObject<SoundEvent> SPAWN_TNT = register("spawn_tnt");
	public static RegistryObject<SoundEvent> SPAWN_TNT_FIREWORK = register("spawn_tnt_firework");
	
	public static RegistryObject<SoundEvent> register(String name){
		return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(LuckyTNTMod.MODID, name)));
	}
}
