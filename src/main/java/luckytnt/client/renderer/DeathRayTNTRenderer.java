package luckytnt.client.renderer;

import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;

import luckytnt.client.renderer.helper.DeathRayRenderer;
import luckytnt.tnteffects.DeathRayEffect;
import luckytntlib.client.renderer.LTNTRenderer;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class DeathRayTNTRenderer extends LTNTRenderer {

	private static final Vector3f CHARGE_COLOR = new Vector3f(0.8f, 0f, 0f);
	private static final Vector3f DARK_CHARGE_COLOR = new Vector3f(0.6f, 0f, 0f);

	private static final float RAY_START_HEIGHT = 500;
	private static final Vector3f RAY_COLOR = new Vector3f(0.3f, 0f, 0.8f);
	private static final Vector3f RAY_CENTER_COLOR = new Vector3f(1f, 0.6f, 1f);	
	
	public DeathRayTNTRenderer(Context context) {
		super(context);
	}

	@Override
	public void render(Entity entity, float yaw, float partialTick, PoseStack posestack, MultiBufferSource buffer, int light) {
		super.render(entity, yaw, partialTick, posestack, buffer, light);
		if (entity instanceof IExplosiveEntity ent) {
			int fuse = ent.getTNTFuse();
			float partialFuse = fuse - partialTick;
			float smoothFuse = entity.getPersistentData().getFloat("smoothFuse");
			smoothFuse += (partialFuse - smoothFuse) * 0.2f;
			partialFuse = smoothFuse;
			entity.getPersistentData().putFloat("smoothFuse", smoothFuse);
			if (fuse >= DeathRayEffect.RAY_START) {
				float pointerRadius = 3f * (DeathRayEffect.RAY_START / (float)fuse);
				DeathRayRenderer.renderChargeUp(new Vector3f(0f, 0.05f, 0f), new Vector3f(0f, 1f, 0f), pointerRadius, 4f, CHARGE_COLOR, CHARGE_COLOR, posestack, buffer);
			}
			if (fuse >= DeathRayEffect.RAY_END) {
				float chargeRadius = (DeathRayEffect.DURATION - partialFuse) / (DeathRayEffect.DURATION - DeathRayEffect.RAY_END);
				chargeRadius = 30f * (1f - Mth.square(chargeRadius * 2f - 1f));
				chargeRadius += 0.5f;
				DeathRayRenderer.renderChargeUp(new Vector3f(0f, RAY_START_HEIGHT - (float)ent.y(), 0f), new Vector3f(0f, -1f, 0f), chargeRadius, 8f, RAY_COLOR, RAY_CENTER_COLOR, posestack, buffer);
			}
			if (fuse <= DeathRayEffect.LASER_START && fuse >= DeathRayEffect.SHOOT_START) {
				Vector3f laserStart = new Vector3f(0f, RAY_START_HEIGHT - (float)ent.y(), 0f);
				Vector3f laserEnd = new Vector3f(0f, 0.05f, 0f);
				Vector3f cameraPos = entityRenderDispatcher.camera.getPosition().toVector3f().mul(1f, 0f, 1f);
				Vector3f viewDir = entity.position().toVector3f().mul(1f, 0f, 1f).sub(cameraPos).normalize();
				float laserRadius = Mth.sqrt((DeathRayEffect.LASER_START - partialFuse) / (DeathRayEffect.LASER_START - DeathRayEffect.SHOOT_START));
				laserRadius *= 0.5f;
				DeathRayRenderer.renderDeathRay(laserStart, laserEnd, laserRadius, laserEnd.y() - laserStart.y(), 1f, false, viewDir, CHARGE_COLOR, DARK_CHARGE_COLOR, posestack, buffer);
			}
			if (fuse <= DeathRayEffect.SHOOT_START && fuse >= DeathRayEffect.FADEOUT_END) {
				Vector3f rayStart = new Vector3f(0f, RAY_START_HEIGHT + 10f - (float)ent.y(), 0f);
				Vector3f rayEnd = new Vector3f(0f, -entity.getPersistentData().getInt("explosionSize") - 10f - partialTick, 0f);
				if (fuse >= DeathRayEffect.RAY_START) {
					float progress = (DeathRayEffect.SHOOT_START - partialFuse) / (DeathRayEffect.SHOOT_START - DeathRayEffect.RAY_START);
					rayEnd = new Vector3f(0f, Mth.lerp(progress, rayStart.y(), -10f), 0f);
				}
				float rayRadius = 0f;
				if (fuse <= DeathRayEffect.RAY_END) {
					rayRadius = 1f - (DeathRayEffect.RAY_END - partialFuse) / (DeathRayEffect.RAY_END - DeathRayEffect.FADEOUT_END);
				} else {
					rayRadius = (DeathRayEffect.SHOOT_START - partialFuse) / (DeathRayEffect.SHOOT_START - DeathRayEffect.RAY_END);
				}
				rayRadius *= 6f;
				rayRadius += 1f;
				Vector3f cameraPos = entityRenderDispatcher.camera.getPosition().toVector3f().mul(1f, 0f, 1f);
				Vector3f viewDir = entity.position().toVector3f().mul(1f, 0f, 1f).sub(cameraPos).normalize();
				DeathRayRenderer.renderDeathRay(rayStart, rayEnd, rayRadius, rayEnd.y() - rayStart.y(), 2f, true, viewDir, RAY_COLOR, RAY_CENTER_COLOR, posestack, buffer);
			}
		}
	}
	
	@Override
	public boolean shouldRender(Entity entity, Frustum frustum, double x, double y, double z) {
		return true;
	}
}
