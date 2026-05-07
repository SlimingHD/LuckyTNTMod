package luckytnt.client.renderer;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import luckytnt.client.renderer.helper.DeathRayRenderHelper;
import luckytnt.registry.RenderTypeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent.Stage;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(bus = Bus.FORGE, value = Dist.CLIENT)
public class DeathRayRenderQueue {

	private static final List<DeathRayContent> DEATH_RAYS = new ArrayList<DeathRayContent>();
	private static final List<ChargeUpContent> CHARGE_UPS = new ArrayList<ChargeUpContent>();
	
	@SubscribeEvent
	public static void renderLevel(RenderLevelStageEvent event) {
		if (event.getStage() == Stage.AFTER_WEATHER) {
			BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();

			PoseStack mvStack = RenderSystem.getModelViewStack();
			mvStack.pushPose();
			mvStack.setIdentity();
		    RenderSystem.applyModelViewMatrix();
		    
			for (DeathRayContent ray : DEATH_RAYS) {
				DeathRayRenderHelper.renderDeathRay(ray.start, ray.end, ray.rayRadius, ray.rayLengthFactor, ray.axialFalloffFactor, ray.noiseStrength, ray.animationSpeed, ray.rotationSpeed, ray.color, ray.pose, buffer);
			}
			for (ChargeUpContent charge : CHARGE_UPS) {
				DeathRayRenderHelper.renderChargeUp(charge.pos, charge.dir, charge.radius, charge.animationSpeed, charge.color, charge.centerColor, charge.pose, buffer);
			}
			for (DeathRayContent ray : DEATH_RAYS) {
				DeathRayRenderHelper.renderDeathRayDepth(ray.start, ray.end, ray.rayRadius, ray.rotationSpeed, ray.pose, buffer);
			}
			for (ChargeUpContent charge : CHARGE_UPS) {
				DeathRayRenderHelper.renderChargeUpDepth(charge.pos, charge.dir, charge.radius, charge.pose, buffer);
			}
			buffer.endBatch(RenderTypeRegistry.DEPTH_WRITER);
			mvStack.popPose();
			RenderSystem.applyModelViewMatrix();
			DEATH_RAYS.clear();
			CHARGE_UPS.clear();
		}
	}
	
	public static void addDeathRay(DeathRayContent ray) {
		DEATH_RAYS.add(ray);
	}
	
	public static void addChargeUp(ChargeUpContent charge) {
		CHARGE_UPS.add(charge);
	}
	
	public static record DeathRayContent(Vector3f start, Vector3f end, float rayRadius, float rayLengthFactor, float axialFalloffFactor, float noiseStrength, float animationSpeed, float rotationSpeed, Vector3f color, PoseStack.Pose pose) {}
	public static record ChargeUpContent(Vector3f pos, Vector3f dir, float radius, float animationSpeed, Vector3f color, Vector3f centerColor, PoseStack.Pose pose) {}
}
