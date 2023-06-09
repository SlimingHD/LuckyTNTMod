package luckytnt.client.gui;

import luckytnt.config.LuckyTNTConfigValues;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraftforge.client.gui.widget.ForgeSlider;
import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigScreen extends Screen{

	ForgeSlider island_slider = null;
	ForgeSlider dropped_slider = null;
	ForgeSlider average_disaster_time_silder = null;
	ForgeSlider average_disaster_strength_slider = null;
	
	Button season_events_always_active = null;
	Button render_contaminated_overlay = null;	
	
	public ConfigScreen() {
		super(Component.translatable("luckytntmod.config.title"));
	}
	
	@Override
	public void init() {
		addRenderableWidget(new Button.Builder(Component.translatable("luckytntmod.config.next"), button -> nextPage()).bounds(width - 120, height - 30, 100, 20).build());
		addRenderableWidget(new Button.Builder(Component.translatable("luckytntmod.config.done"), button -> onClose()).bounds((width - 100) / 2, height - 30, 100, 20).build());
		
		addRenderableWidget(island_slider = new ForgeSlider(20, 40, 200, 20, MutableComponent.create(new LiteralContents("")), MutableComponent.create(new LiteralContents("")), 20, 160, LuckyTNTConfigValues.ISLAND_HEIGHT.get(), true));		
		addRenderableWidget(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetIntValue(LuckyTNTConfigValues.ISLAND_HEIGHT, 50, island_slider)).bounds(width - 220, 40, 200, 20).build());
		addRenderableWidget(dropped_slider = new ForgeSlider(20, 60, 200, 20, MutableComponent.create(new LiteralContents("")), MutableComponent.create(new LiteralContents("")), 60, 400, LuckyTNTConfigValues.DROP_HEIGHT.get(), true));		
		addRenderableWidget(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetIntValue(LuckyTNTConfigValues.DROP_HEIGHT, 200, dropped_slider)).bounds(width - 220, 60, 200, 20).build());
		
		addRenderableWidget(average_disaster_time_silder = new ForgeSlider(20, 80, 200, 20, Component.literal(""), Component.literal(""), 2, 24, LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.get().doubleValue(), true));		
		addRenderableWidget(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetIntValue(LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME, 12, average_disaster_time_silder)).bounds(width - 220, 80, 200, 20).build());
		addRenderableWidget(average_disaster_strength_slider = new ForgeSlider(20, 100, 200, 20, Component.literal(""), Component.literal(""), 1d, 10d, LuckyTNTConfigValues.AVERAGE_DIASTER_INTENSITY.get().doubleValue(), true));		
		addRenderableWidget(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetDoubleValue(LuckyTNTConfigValues.AVERAGE_DIASTER_INTENSITY, 1d, average_disaster_strength_slider)).bounds(width - 220, 100, 200, 20).build());
	
		addRenderableWidget(season_events_always_active = new Button.Builder(LuckyTNTConfigValues.SEASON_EVENTS_ALWAYS_ACTIVE.get().booleanValue() ? Component.translatable("luckytntmod.config.true") : Component.translatable("luckytntmod.config.false"), button -> nextBooleanValue(LuckyTNTConfigValues.SEASON_EVENTS_ALWAYS_ACTIVE, season_events_always_active)).bounds(20, 120, 200, 20).build());
		addRenderableWidget(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetBooleanValue(LuckyTNTConfigValues.SEASON_EVENTS_ALWAYS_ACTIVE, false, season_events_always_active)).bounds(width - 220, 120, 200, 20).build());
		
		addRenderableWidget(render_contaminated_overlay = new Button.Builder(LuckyTNTConfigValues.RENDER_CONTAMINATED_OVERLAY.get().booleanValue() ? Component.translatable("luckytntmod.config.true") : Component.translatable("luckytntmod.config.false"), button -> nextBooleanValue(LuckyTNTConfigValues.RENDER_CONTAMINATED_OVERLAY, render_contaminated_overlay)).bounds(20, 140, 200, 20).build());
		addRenderableWidget(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetBooleanValue(LuckyTNTConfigValues.RENDER_CONTAMINATED_OVERLAY, true, render_contaminated_overlay)).bounds(width - 220, 140, 200, 20).build());
	}
	
	@Override
	public void render(GuiGraphics stack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(stack);
		stack.drawCenteredString(font, title, width / 2, 8, 0xFFFFFF);
		stack.drawCenteredString(font, Component.translatable("luckytntmod.config.island_offset"), width / 2, 46, 0xFFFFFF);
		stack.drawCenteredString(font, Component.translatable("luckytntmod.config.drop_offset"), width / 2, 66, 0xFFFFFF);
		stack.drawCenteredString(font, Component.translatable("luckytntmod.config.maximum_time"), width / 2, 86, 0xFFFFFF);
		stack.drawCenteredString(font, Component.translatable("luckytntmod.config.average_intensity"), width / 2, 106, 0xFFFFFF);
		stack.drawCenteredString(font, Component.translatable("luckytntmod.config.event_always_active"), width / 2, 126, 0xFFFFFF);
		stack.drawCenteredString(font, Component.translatable("luckytntmod.config.render_overlay"), width / 2, 146, 0xFFFFFF);
		super.render(stack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void onClose() {
		if(island_slider != null) {
			LuckyTNTConfigValues.ISLAND_HEIGHT.set(island_slider.getValueInt());
		}
		if(dropped_slider != null) {
			LuckyTNTConfigValues.DROP_HEIGHT.set(dropped_slider.getValueInt());
		}
		if(average_disaster_time_silder != null) {
			LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.set(average_disaster_time_silder.getValueInt());
		}
		if(average_disaster_strength_slider != null) {
			LuckyTNTConfigValues.AVERAGE_DIASTER_INTENSITY.set(average_disaster_strength_slider.getValue());
		}
		super.onClose();
	}
	
	public void nextPage() {
		onClose();
		Minecraft.getInstance().setScreen(new ConfigScreen2());
	}
	
	public void resetIntValue(ForgeConfigSpec.IntValue config, int newValue, ForgeSlider slider) {
		config.set(newValue);
		slider.setValue(newValue);
	}
	
	public void resetDoubleValue(ForgeConfigSpec.DoubleValue config, double newValue, ForgeSlider slider) {
		config.set(newValue);
		slider.setValue(newValue);
	}
	
	public void nextBooleanValue(ForgeConfigSpec.BooleanValue config, Button button) {
		boolean value = config.get().booleanValue();
		if(value) {
			value = false;
		} else {
			value = true;
		}
		config.set(value);
		button.setMessage(value ? Component.translatable("luckytntmod.config.true") : Component.translatable("luckytntmod.config.false"));
	}
	
	public void resetBooleanValue(ForgeConfigSpec.BooleanValue config, boolean defaultValue, Button button) {
		config.set(defaultValue);
		button.setMessage(defaultValue ? Component.translatable("luckytntmod.config.true") : Component.translatable("luckytntmod.config.false"));
	}
}
