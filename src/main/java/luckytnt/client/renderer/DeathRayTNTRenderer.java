package luckytnt.client.renderer;

import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;

import luckytnt.client.renderer.helper.DeathRayRenderer;
import luckytntlib.client.renderer.LTNTRenderer;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.world.entity.Entity;

public class DeathRayTNTRenderer extends LTNTRenderer {

	private static final Vector3f CHARGE_COLOR = new Vector3f(0.8f, 0f, 0f);

	private static final Vector3f RAY_COLOR = new Vector3f(0.9f, 0f, 0.5f);
	private static final Vector3f RAY_INNER_COLOR = new Vector3f(1f, 0.2f, 0f);
	
	public DeathRayTNTRenderer(Context context) {
		super(context);
	}

	@Override
	public void render(Entity entity, float yaw, float partialTick, PoseStack posestack, MultiBufferSource buffer, int light) {
		super.render(entity, yaw, partialTick, posestack, buffer, light);
		if (entity instanceof IExplosiveEntity ent) {
			if (ent.getTNTFuse() > 120) {
				float chargeRadius = 0.5f * (ent.getEffect().getDefaultFuse(ent) / (float)ent.getTNTFuse());
				DeathRayRenderer.renderChargeUp(new Vector3f(), new Vector3f(0f, 1f, 0f), chargeRadius, 4f, CHARGE_COLOR, CHARGE_COLOR, posestack, buffer);
			}
			if (ent.getTNTFuse() < 140) {
				Vector3f rayStart = new Vector3f(0f, 135f, 0f);
				float rayLength = 2f * (140f - ent.getTNTFuse() + partialTick);
				Vector3f rayEnd = new Vector3f(rayStart).sub(0f, rayLength, 0f);
				DeathRayRenderer.renderDeathRay(rayStart, rayEnd, 3f, rayLength, 3f, 2f, RAY_COLOR, posestack, buffer);
				DeathRayRenderer.renderDeathRay(rayStart, rayEnd, 1.5f, rayLength, 5f, 3.5f, RAY_INNER_COLOR, posestack, buffer);
			}
		}
	}
}
