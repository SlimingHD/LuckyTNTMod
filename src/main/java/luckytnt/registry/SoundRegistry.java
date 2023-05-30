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
	
	public static RegistryObject<SoundEvent> register(String name){
		return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(LuckyTNTMod.MODID, name)));
	}
}
