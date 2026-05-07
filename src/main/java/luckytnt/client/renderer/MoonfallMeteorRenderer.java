package luckytnt.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import luckytntlib.client.renderer.LTNTRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.world.entity.Entity;

public class MoonfallMeteorRenderer extends LTNTRenderer {

	public MoonfallMeteorRenderer(Context ctx) {
		super(ctx);
	}
	
	@Override
	public void render(Entity entity, float yaw, float partialTicks, PoseStack posestack, MultiBufferSource buffer, int light) {
		super.render(entity, yaw, partialTicks, posestack, buffer, LightTexture.FULL_BRIGHT);
	}
	
	@Override
	public boolean shouldRender(Entity entity, Frustum frustum, double x, double y, double z) {
		return true;
	}
}
