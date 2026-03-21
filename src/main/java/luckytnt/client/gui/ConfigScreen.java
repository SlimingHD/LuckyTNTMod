package luckytnt.client.gui;

import luckytnt.config.LuckyTNTConfigValues;
import luckytntlib.client.gui.CenteredStringWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.GridLayout.RowHelper;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LayoutSettings;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.layouts.LinearLayout.Orientation;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ForgeSlider;
import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigScreen extends Screen {

	ForgeSlider island_slider = null;
	ForgeSlider dropped_slider = null;
	ForgeSlider average_disaster_time_silder = null;
	ForgeSlider average_disaster_strength_slider = null;
	
	Button season_events_always_active = null;
	Button render_contaminated_overlay = null;
	Button present_drop_destroy = null;

	HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 20, 40);
	
	public ConfigScreen() {
		super(Component.translatable("luckytntmod.config.title"));
	}
	
	@Override
	public void init() {
		LinearLayout linear = layout.addToHeader(new LinearLayout(0, 0, Orientation.VERTICAL));
		linear.addChild(new StringWidget(title, font), LayoutSettings.defaults().alignHorizontallyCenter());
		
		GridLayout grid = new GridLayout();
		grid.defaultCellSetting().paddingHorizontal(4).paddingBottom(4).alignHorizontallyCenter();
		RowHelper rows = grid.createRowHelper(3);
		
		rows.addChild(island_slider = new ForgeSlider(0, 0, 100, 20, Component.empty(), Component.empty(), 20, 160, LuckyTNTConfigValues.ISLAND_HEIGHT.get(), true));
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.island_offset"), font));
		rows.addChild(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetIntValue(LuckyTNTConfigValues.ISLAND_HEIGHT, 50, island_slider)).width(100).build());
		
		rows.addChild(dropped_slider = new ForgeSlider(0, 0, 100, 20, Component.empty(), Component.empty(), 60, 400, LuckyTNTConfigValues.DROP_HEIGHT.get(), true));
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.drop_offset"), font));
		rows.addChild(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetIntValue(LuckyTNTConfigValues.DROP_HEIGHT, 200, dropped_slider)).width(100).build());
		
		rows.addChild(average_disaster_time_silder = new ForgeSlider(0, 0, 100, 20, Component.empty(), Component.empty(), 2, 24, LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.get().doubleValue(), true));
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.maximum_time"), font));
		rows.addChild(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetIntValue(LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME, 12, average_disaster_time_silder)).width(100).build());
		
		rows.addChild(average_disaster_strength_slider = new ForgeSlider(0, 0, 100, 20, Component.empty(), Component.empty(), 1d, 10d, LuckyTNTConfigValues.AVERAGE_DIASTER_INTENSITY.get().doubleValue(), true));
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.average_intensity"), font));
		rows.addChild(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetDoubleValue(LuckyTNTConfigValues.AVERAGE_DIASTER_INTENSITY, 1d, average_disaster_strength_slider)).width(100).build());
		
		rows.addChild(season_events_always_active = new Button.Builder(LuckyTNTConfigValues.SEASON_EVENTS_ALWAYS_ACTIVE.get().booleanValue() ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF, button -> nextBooleanValue(LuckyTNTConfigValues.SEASON_EVENTS_ALWAYS_ACTIVE, season_events_always_active)).width(100).build());
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.event_always_active"), font));
		rows.addChild(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetBooleanValue(LuckyTNTConfigValues.SEASON_EVENTS_ALWAYS_ACTIVE, false, season_events_always_active)).width(100).build());
		
		rows.addChild(render_contaminated_overlay = new Button.Builder(LuckyTNTConfigValues.RENDER_CONTAMINATED_OVERLAY.get().booleanValue() ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF, button -> nextBooleanValue(LuckyTNTConfigValues.RENDER_CONTAMINATED_OVERLAY, render_contaminated_overlay)).width(100).build());
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.render_overlay"), font));
		rows.addChild(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetBooleanValue(LuckyTNTConfigValues.RENDER_CONTAMINATED_OVERLAY, true, render_contaminated_overlay)).width(100).build());
		
		rows.addChild(present_drop_destroy = new Button.Builder(LuckyTNTConfigValues.PRESENT_DROP_DESTROY_BLOCKS.get().booleanValue() ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF, button -> nextBooleanValue(LuckyTNTConfigValues.PRESENT_DROP_DESTROY_BLOCKS, present_drop_destroy)).width(100).build());
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.present_drop"), font));
		rows.addChild(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetBooleanValue(LuckyTNTConfigValues.PRESENT_DROP_DESTROY_BLOCKS, true, present_drop_destroy)).width(100).build());
		
		GridLayout grid2 = new GridLayout();
		grid2.defaultCellSetting().paddingHorizontal(20).paddingBottom(4).alignHorizontallyCenter();
		RowHelper rows2 = grid2.createRowHelper(3);
		
		Button deactivated = new Button.Builder(Component.translatable("luckytntmod.config.back"), button -> {}).width(100).build();
		Button done = new Button.Builder(CommonComponents.GUI_DONE, button -> onClose()).width(100).build();
		Button next = new Button.Builder(Component.translatable("luckytntmod.config.next"), button -> nextPage()).width(100).build();
		deactivated.active = false;
		
		rows2.addChild(deactivated);
		rows2.addChild(done);
		rows2.addChild(next);
		
		layout.addToContents(grid);
		layout.addToFooter(grid2);
		layout.visitWidgets(this::addRenderableWidget);
		repositionElements();
	}
	
	@Override
    public void repositionElements() {
        layout.arrangeElements();
    }
	
	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		renderBackground(graphics);
		super.render(graphics, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void onClose() {
		if (island_slider != null) {
			LuckyTNTConfigValues.ISLAND_HEIGHT.set(island_slider.getValueInt());
		}
		if (dropped_slider != null) {
			LuckyTNTConfigValues.DROP_HEIGHT.set(dropped_slider.getValueInt());
		}
		if (average_disaster_time_silder != null) {
			LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.set(average_disaster_time_silder.getValueInt());
		}
		if (average_disaster_strength_slider != null) {
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
		if (value) {
			value = false;
		} else {
			value = true;
		}
		config.set(value);
		button.setMessage(value ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF);
	}
	
	public void resetBooleanValue(ForgeConfigSpec.BooleanValue config, boolean defaultValue, Button button) {
		config.set(defaultValue);
		button.setMessage(defaultValue ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF);
	}
}
