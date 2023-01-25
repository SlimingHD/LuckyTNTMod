package luckytnt.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;

import luckytnt.config.LuckyTNTConfigValues;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraftforge.client.gui.widget.ForgeSlider;
import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigScreen extends Screen{

	ForgeSlider island_slider = null;
	ForgeSlider dropped_slider = null;
	
	public ConfigScreen() {
		super(MutableComponent.create(new TranslatableContents("luckytntmod.config.title")));
	}
	
	@Override
	public void init() {
		addRenderableWidget(new Button.Builder(Component.translatable("luckytntmod.config.done"), button -> onClose()).bounds((width - 100) / 2, height - 40, 100, 20).build());
		addRenderableWidget(island_slider = new ForgeSlider(20, 40, 200, 20, MutableComponent.create(new LiteralContents("")), MutableComponent.create(new LiteralContents("")), 20, 160, LuckyTNTConfigValues.ISLAND_HEIGHT.get(), true));		
		addRenderableWidget(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetIntValue(LuckyTNTConfigValues.ISLAND_HEIGHT, 50, island_slider)).bounds(width - 220, 40, 200, 20).build());
		addRenderableWidget(dropped_slider = new ForgeSlider(20, 60, 200, 20, MutableComponent.create(new LiteralContents("")), MutableComponent.create(new LiteralContents("")), 60, 400, LuckyTNTConfigValues.DROP_HEIGHT.get(), true));		
		addRenderableWidget(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetIntValue(LuckyTNTConfigValues.ISLAND_HEIGHT, 50, island_slider)).bounds(width - 220, 60, 200, 20).build());
	}
	
	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(stack);
		drawCenteredString(stack, font, title, width / 2, 8, 0xFFFFFF);
		drawCenteredString(stack, font, MutableComponent.create(new TranslatableContents("luckytntmod.config.island_offset")), width / 2, 46, 0xFFFFFF);
		drawCenteredString(stack, font, MutableComponent.create(new TranslatableContents("luckytntmod.config.drop_offset")), width / 2, 66, 0xFFFFFF);
		super.render(stack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void onClose() {
		if(island_slider != null) {
			LuckyTNTConfigValues.ISLAND_HEIGHT.set(island_slider.getValueInt());
		}
		super.onClose();
	}
	
	public void resetIntValue(ForgeConfigSpec.IntValue config, int newValue, ForgeSlider slider) {
		config.set(newValue);
		slider.setValue(newValue);
	}
}
