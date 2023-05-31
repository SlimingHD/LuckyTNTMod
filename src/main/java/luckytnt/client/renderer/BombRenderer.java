package luckytnt.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import luckytnt.client.model.BombModel;
import luckytntlib.entity.LExplosiveProjectile;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class BombRenderer extends EntityRenderer<LExplosiveProjectile>{

	public EntityModel<LExplosiveProjectile> model;
	
	public BombRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new BombModel<LExplosiveProjectile>(context.bakeLayer(BombModel.LAYER_LOCATION));
	}
	
	@Override
	public void render(LExplosiveProjectile entity, float rotY, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
		stack.pushPose();
		stack.scale(entity.getEffect().getSize(entity), entity.getEffect().getSize(entity), entity.getEffect().getSize(entity));
		VertexConsumer vc = buffer.getBuffer(RenderType.entityCutout(getTextureLocation(entity)));
		model.renderToBuffer(stack, vc, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.0625f);
		super.render(entity, rotY, partialTicks, stack, buffer, light);
		stack.popPose();
	}
	
	public ResourceLocation getTextureLocation(LExplosiveProjectile entity) {
		return new ResourceLocation("luckytntmod:textures/tsarbomb.png");
	}
}
