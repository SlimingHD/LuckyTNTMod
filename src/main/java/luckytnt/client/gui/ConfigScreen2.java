package luckytnt.client.gui;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.util.CustomTNTConfig;
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

public class ConfigScreen2 extends Screen {
	
	Button custom_tnt_first_explosion = null;
	ForgeSlider custom_tnt_first_explosion_intensity = null;
	Button custom_tnt_second_explosion = null;
	ForgeSlider custom_tnt_second_explosion_intensity = null;
	Button custom_tnt_third_explosion = null;
	ForgeSlider custom_tnt_third_explosion_intensity = null;
	
	HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 20, 40);

	public ConfigScreen2() {
		super(Component.translatable("luckytntmod.config.title"));
	}
	
	@Override
	public void init() {
		LinearLayout linear = layout.addToHeader(new LinearLayout(0, 0, Orientation.VERTICAL));
		linear.addChild(new StringWidget(title, font), LayoutSettings.defaults().alignHorizontallyCenter());
		
		Button empty = new Button.Builder(Component.empty(), button -> {}).size(100, 15).build();
		empty.active = false;
		empty.visible = false;
		empty.setAlpha(0f);
		
		GridLayout grid = new GridLayout();
		grid.defaultCellSetting().paddingHorizontal(4).paddingBottom(1).alignHorizontallyCenter();
		RowHelper rows = grid.createRowHelper(3);
		
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.custom_tnt"), font));
		rows.addChild(empty);
		rows.addChild(new CenteredStringWidget(Component.literal(""), font));
		
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.first_tnt"), font));
		rows.addChild(empty);
		rows.addChild(new CenteredStringWidget(Component.literal(""), font));
		
		rows.addChild(custom_tnt_first_explosion = new Button.Builder(LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION.get().getComponent(), button -> nextExplosionValue(LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION, custom_tnt_first_explosion)).width(100).build());
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.first_type"), font));
		rows.addChild(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetExplosion(LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION, custom_tnt_first_explosion)).width(100).build());
		
		rows.addChild(custom_tnt_first_explosion_intensity = new ForgeSlider(0, 0, 100, 20, Component.empty(), Component.empty(), 1, 20, LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY.get().intValue(), true));
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.first_intensity"), font));
		rows.addChild(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetIntValue(LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY, 1, custom_tnt_first_explosion_intensity)).width(100).build());
		
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.second_tnt"), font));
		rows.addChild(empty);
		rows.addChild(new CenteredStringWidget(Component.literal(""), font));
		
		rows.addChild(custom_tnt_second_explosion = new Button.Builder(LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION.get().getComponent(), button -> nextExplosionValue(LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION, custom_tnt_second_explosion)).width(100).build());
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.second_type"), font));
		rows.addChild(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetExplosion(LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION, custom_tnt_second_explosion)).width(100).build());
		
		rows.addChild(custom_tnt_second_explosion_intensity = new ForgeSlider(0, 0, 100, 20, Component.empty(), Component.empty(), 1, 20, LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY.get().intValue(), true));
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.second_intensity"), font));
		rows.addChild(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetIntValue(LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY, 1, custom_tnt_second_explosion_intensity)).width(100).build());
		
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.third_tnt"), font));
		rows.addChild(empty);
		rows.addChild(new CenteredStringWidget(Component.literal(""), font));
		
		rows.addChild(custom_tnt_third_explosion = new Button.Builder(LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION.get().getComponent(), button -> nextExplosionValue(LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION, custom_tnt_third_explosion)).width(100).build());
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.third_type"), font));
		rows.addChild(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetExplosion(LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION, custom_tnt_third_explosion)).width(100).build());
		
		rows.addChild(custom_tnt_third_explosion_intensity = new ForgeSlider(0, 0, 100, 20, Component.empty(), Component.empty(), 1, 20, LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION_INTENSITY.get().intValue(), true));
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.third_intensity"), font));
		rows.addChild(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetIntValue(LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION_INTENSITY, 1, custom_tnt_third_explosion_intensity)).width(100).build());
		
		GridLayout grid2 = new GridLayout();
		grid2.defaultCellSetting().paddingHorizontal(20).paddingBottom(4).alignHorizontallyCenter();
		RowHelper rows2 = grid2.createRowHelper(3);
		
		Button back = new Button.Builder(Component.translatable("luckytntmod.config.back"), button -> lastPage()).width(100).build();
		Button done = new Button.Builder(CommonComponents.GUI_DONE, button -> onClose()).width(100).build();
		Button deactivated = new Button.Builder(Component.translatable("luckytntmod.config.next"), button -> {}).width(100).build();
		deactivated.active = false;
		
		rows2.addChild(back);
		rows2.addChild(done);
		rows2.addChild(deactivated);
		
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
		if (custom_tnt_first_explosion_intensity != null) {
			LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY.set(custom_tnt_first_explosion_intensity.getValueInt());
		}
		if (custom_tnt_second_explosion_intensity != null) {
			LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY.set(custom_tnt_second_explosion_intensity.getValueInt());
		}
		if (custom_tnt_third_explosion_intensity != null) {
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
		if (value == CustomTNTConfig.NO_EXPLOSION) {
			value = CustomTNTConfig.NORMAL_EXPLOSION;
		} else if (value == CustomTNTConfig.NORMAL_EXPLOSION) {
			value = CustomTNTConfig.SPHERICAL_EXPLOSION;
		} else if (value == CustomTNTConfig.SPHERICAL_EXPLOSION) {
			value = CustomTNTConfig.CUBICAL_EXPLOSION;
		} else if (value == CustomTNTConfig.CUBICAL_EXPLOSION) {
			value = CustomTNTConfig.EASTER_EGG;
		} else if (value == CustomTNTConfig.EASTER_EGG) {
			value = CustomTNTConfig.FIREWORK;
		} else if (value == CustomTNTConfig.FIREWORK) {
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
