package luckytnt.client.overlay;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.EffectRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class OverlayTick {
	
	private static float contaminatedAmount = 0;
	
	@SuppressWarnings({ "resource", "static-access" })
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onOverlayRender(RenderGuiEvent.Post event) {
		Player player = Minecraft.getInstance().player;
		int w = event.getWindow().getGuiScaledWidth();
		int h = event.getWindow().getGuiScaledHeight();
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.enableBlend();
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		if(player.getPersistentData().getInt("freezeTime") > 0 && !player.hasEffect(EffectRegistry.CONTAMINATED_EFFECT.get())) {
			RenderSystem.setShaderColor(1f, 1f, 1f, (float)(player.getPersistentData().getInt("freezeTime")) / 1200f);
			RenderSystem.setShaderTexture(0, new ResourceLocation("luckytntmod:textures/powder_snow_outline.png"));
			Minecraft.getInstance().gui.blit(event.getPoseStack(), 0, 0, 0, 0, w, h, w, h);
		} else if(player.getEffect(EffectRegistry.CONTAMINATED_EFFECT.get()) != null && player.getEffect(EffectRegistry.CONTAMINATED_EFFECT.get()).getDuration() > 0 && LuckyTNTConfigValues.RENDER_CONTAMINATED_OVERLAY.get()) {
			RenderSystem.setShaderColor(1f, 1f, 1f, contaminatedAmount);
			RenderSystem.setShaderTexture(0, new ResourceLocation("luckytntmod:textures/contaminated_outline.png"));
			Minecraft.getInstance().gui.blit(event.getPoseStack(), 0, 0, 0, 0, w, h, w, h);
			contaminatedAmount = Mth.clamp(contaminatedAmount + 0.025f, 0f, 1f);
		} else if(contaminatedAmount > 0){
			RenderSystem.setShaderColor(1f, 1f, 1f, contaminatedAmount);
			RenderSystem.setShaderTexture(0, new ResourceLocation("luckytntmod:textures/contaminated_outline.png"));
			Minecraft.getInstance().gui.blit(event.getPoseStack(), 0, 0, 0, 0, w, h, w, h);
			contaminatedAmount = Mth.clamp(contaminatedAmount - 0.025f, 0f, 1f);
		}
		RenderSystem.depthMask(true);
		RenderSystem.defaultBlendFunc();
		RenderSystem.disableBlend();
		RenderSystem.enableDepthTest();
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
	}
}
