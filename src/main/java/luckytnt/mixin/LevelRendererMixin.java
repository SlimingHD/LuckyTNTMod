package luckytnt.mixin;

import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import luckytnt.LevelVariables;
import luckytnt.LuckyTNTMod;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.resources.ResourceLocation;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

	private static final ResourceLocation AIR = new ResourceLocation(LuckyTNTMod.MODID, "textures/block/air.png");
	
	@Inject(method = "renderSky", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getMoonPhase()I", shift = Shift.AFTER))
	public void beforeMoon(PoseStack p, Matrix4f m, float f, Camera c, boolean b, Runnable r, CallbackInfo callback) {
		if (LevelVariables.clientSide.noMoonTime > 0) {
			RenderSystem.setShaderTexture(0, AIR);
		}
	}
}
