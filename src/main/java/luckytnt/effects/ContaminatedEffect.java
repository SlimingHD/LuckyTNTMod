package luckytnt.effects;

import luckytnt.registry.EffectRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;

public class ContaminatedEffect extends MobEffect {
	
	public ContaminatedEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("effect.luckytntmod.contaminated_effect");
	}
	
	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity instanceof Player player) {
			FoodData data = player.getFoodData();
			CompoundTag tag = new CompoundTag();
			tag.putInt("foodLevel", data.getFoodLevel());
			tag.putInt("foodTickTimer", 0);
			tag.putFloat("foodSaturationLevel", data.getSaturationLevel());
			tag.putFloat("foodExhaustionLevel", data.getExhaustionLevel());
			data.readAdditionalSaveData(tag);
		}
		
		MobEffectInstance effect = entity.getEffect(EffectRegistry.CONTAMINATED_EFFECT.get());
		if (effect == null) {
			return;
		}
		
		int duration = effect.getDuration();
		int i = 40 >> duration;
		if (i > 0) {
			if (amplifier % i == 0) {
				if (entity.getHealth() > 4.0F) {
					entity.hurt(entity.level().damageSources().magic(), 1.0F);
				}
			}
		}
	}
}
