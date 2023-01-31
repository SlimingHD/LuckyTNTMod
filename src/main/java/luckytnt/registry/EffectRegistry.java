package luckytnt.registry;

import java.util.function.Supplier;

import luckytnt.LuckyTNTMod;
import luckytnt.effects.ContaminatedEffect;
import luckytnt.effects.MidasTouchEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EffectRegistry {

	public static final RegistryObject<MobEffect> CONTAMINATED_EFFECT = registerEffect(() -> new ContaminatedEffect(MobEffectCategory.HARMFUL, 0xB9C300), "contaminated");
	public static final RegistryObject<MobEffect> MIDAS_TOUCH_EFFECT = registerEffect(() -> new MidasTouchEffect(MobEffectCategory.NEUTRAL, 0xDFB93E), "midas_touch");
	
	public static RegistryObject<MobEffect> registerEffect(Supplier<MobEffect> effect, String name) {		
		return LuckyTNTMod.effectRegistry.register(name, effect);
	}
}
