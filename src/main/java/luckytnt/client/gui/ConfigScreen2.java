package luckytnt.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.util.CustomTNTConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraftforge.client.gui.widget.ForgeSlider;
import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigScreen2 extends Screen {
	
	Button custom_tnt_first_explosion = null;
	ForgeSlider custom_tnt_first_explosion_intensity = null;
	Button custom_tnt_second_explosion = null;
	ForgeSlider custom_tnt_second_explosion_intensity = null;
	Button custom_tnt_third_explosion = null;
	ForgeSlider custom_tnt_third_explosion_intensity = null;

	public ConfigScreen2() {
		super(Component.translatable("luckytntmod.config.title"));
	}
	
	@Override
	public void init() {
		addRenderableWidget(new Button.Builder(Component.translatable("luckytntmod.config.done"), button -> onClose()).bounds((width - 100) / 2, height - 30, 100, 20).build());
		addRenderableWidget(new Button.Builder(Component.translatable("luckytntmod.config.back"), button -> lastPage()).bounds(20, height - 30, 100, 20).build());
		
		addRenderableWidget(custom_tnt_first_explosion = new Button.Builder(LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION.get().getComponent(), button -> nextExplosionValue(LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION, custom_tnt_first_explosion)).bounds(20, 80, 200, 20).build());
		addRenderableWidget(custom_tnt_first_explosion_intensity = new ForgeSlider(20, 100, 200, 20, MutableComponent.create(new LiteralContents("")), MutableComponent.create(new LiteralContents("")), 1, 20, LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY.get().intValue(), true));
		addRenderableWidget(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetExplosion(LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION, custom_tnt_first_explosion)).bounds(width - 220, 80, 200, 20).build());
		addRenderableWidget(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetIntValue(LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY, 1, custom_tnt_first_explosion_intensity)).bounds(width - 220, 100, 200, 20).build());
		
		addRenderableWidget(custom_tnt_second_explosion = new Button.Builder(LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION.get().getComponent(), button -> nextExplosionValue(LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION, custom_tnt_second_explosion)).bounds(20, 140, 200, 20).build());
		addRenderableWidget(custom_tnt_second_explosion_intensity = new ForgeSlider(20, 160, 200, 20, MutableComponent.create(new LiteralContents("")), MutableComponent.create(new LiteralContents("")), 1, 20, LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY.get().intValue(), true));
		addRenderableWidget(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetExplosion(LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION, custom_tnt_second_explosion)).bounds(width - 220, 140, 200, 20).build());
		addRenderableWidget(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetIntValue(LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY, 1, custom_tnt_second_explosion_intensity)).bounds(width - 220, 160, 200, 20).build());
		
		addRenderableWidget(custom_tnt_third_explosion = new Button.Builder(LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION.get().getComponent(), button -> nextExplosionValue(LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION, custom_tnt_third_explosion)).bounds(20, 200, 200, 20).build());
		addRenderableWidget(custom_tnt_third_explosion_intensity = new ForgeSlider(20, 220, 200, 20, MutableComponent.create(new LiteralContents("")), MutableComponent.create(new LiteralContents("")), 1, 20, LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION_INTENSITY.get().intValue(), true));
		addRenderableWidget(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetExplosion(LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION, custom_tnt_third_explosion)).bounds(width - 220, 200, 200, 20).build());
		addRenderableWidget(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetIntValue(LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION_INTENSITY, 1, custom_tnt_third_explosion_intensity)).bounds(width - 220, 220, 200, 20).build());
	}
	
	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(stack);
		drawCenteredString(stack, font, title, width / 2, 8, 0xFFFFFF);
		drawString(stack, font, Component.translatable("luckytntmod.config.custom_tnt"), 20, 46, 0xFFFFFF);
		drawString(stack, font, Component.translatable("luckytntmod.config.first_tnt"), 20, 66, 0xFFFFFF);
		drawCenteredString(stack, font, Component.translatable("luckytntmod.config.first_type"), width / 2, 86, 0xFFFFFF);
		drawCenteredString(stack, font, Component.translatable("luckytntmod.config.first_intensity"), width / 2, 106, 0xFFFFFF);
		drawString(stack, font, Component.translatable("luckytntmod.config.second_tnt"), 20, 126, 0xFFFFFF);
		drawCenteredString(stack, font, Component.translatable("luckytntmod.config.second_type"), width / 2, 146, 0xFFFFFF);
		drawCenteredString(stack, font, Component.translatable("luckytntmod.config.second_intensity"), width / 2, 166, 0xFFFFFF);
		drawString(stack, font, Component.translatable("luckytntmod.config.third_tnt"), 20, 186, 0xFFFFFF);
		drawCenteredString(stack, font, Component.translatable("luckytntmod.config.third_type"), width / 2, 206, 0xFFFFFF);
		drawCenteredString(stack, font, Component.translatable("luckytntmod.config.third_intensity"), width / 2, 226, 0xFFFFFF);
		super.render(stack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void onClose() {
		if(custom_tnt_first_explosion_intensity != null) {
			LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY.set(custom_tnt_first_explosion_intensity.getValueInt());
		}
		if(custom_tnt_second_explosion_intensity != null) {
			LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY.set(custom_tnt_second_explosion_intensity.getValueInt());
		}
		if(custom_tnt_third_explosion_intensity != null) {
			LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION_INTENSITY.set(custom_tnt_third_explosion_intensity.getValueInt());
		}
		super.onClose();
	}
	
	public void lastPage() {
		onClose();
		Minecraft.getInstance().setScreen(new ConfigScreen());
	}
	
	public void resetIntValue(ForgeConfigSpec.IntValue config, int newValue, ForgeSlider slider) {
		config.set(newValue);
		slider.setValue(newValue);
	}
	
	public void nextExplosionValue(ForgeConfigSpec.EnumValue<CustomTNTConfig> config, Button button) {
		CustomTNTConfig value = config.get();
		if(value == CustomTNTConfig.NO_EXPLOSION) {
			value = CustomTNTConfig.NORMAL_EXPLOSION;
		}
		else if(value == CustomTNTConfig.NORMAL_EXPLOSION) {
			value = CustomTNTConfig.SPHERICAL_EXPLOSION;
		}
		else if(value == CustomTNTConfig.SPHERICAL_EXPLOSION) {
			value = CustomTNTConfig.CUBICAL_EXPLOSION;
		}
		else if(value == CustomTNTConfig.CUBICAL_EXPLOSION) {
			value = CustomTNTConfig.EASTER_EGG;
		}
		else if(value == CustomTNTConfig.EASTER_EGG) {
			value = CustomTNTConfig.FIREWORK;
		}
		else if(value == CustomTNTConfig.FIREWORK) {
			value = CustomTNTConfig.NO_EXPLOSION;
		}
		config.set(value);
		button.setMessage(config.get().getComponent());
	}
	
	public void resetExplosion(ForgeConfigSpec.EnumValue<CustomTNTConfig> config, Button button) {
		config.set(CustomTNTConfig.NO_EXPLOSION);
		button.setMessage(config.get().getComponent());
	}
}
