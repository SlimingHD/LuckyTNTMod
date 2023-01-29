package luckytnt.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import luckytntlib.block.LTNTBlock;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BouncingTNTRenderer extends EntityRenderer<Entity>{
	private BlockRenderDispatcher blockRenderer;
	
	public BouncingTNTRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.blockRenderer = context.getBlockRenderDispatcher();
	}
	
	public void render(Entity entity, float yaw, float partialTicks, PoseStack posestack, MultiBufferSource buffer, int light) {
    	if(entity instanceof IExplosiveEntity ent) {
			posestack.pushPose();
	        posestack.translate(0, 0, 0);	        
	        float scaleMul = (float)Mth.clamp(entity.getDeltaMovement().length() * 1.5f, 0.85f, 1.35f);
	        posestack.scale(1 / scaleMul, scaleMul, 1 / scaleMul);	        
	        int i = ent.getTNTFuse();
	        if ((float)i - partialTicks + 1.0F < 10.0F && ent.getEffect().getBlock() instanceof LTNTBlock) {
	           float f = 1.0F - ((float)i - partialTicks + 1.0F) / 10.0F;
	           f = Mth.clamp(f, 0.0F, 1.0F);
	           f *= f;
	           f *= f;
	           float f1 = 1.0F + f * 0.3F;
	           posestack.scale(f1, f1, f1);
	        }
	        posestack.scale(ent.getEffect().getSize((IExplosiveEntity)entity), ent.getEffect().getSize((IExplosiveEntity)entity), ent.getEffect().getSize((IExplosiveEntity)entity));
	        posestack.translate(-0.5d, 0, -0.5d);
	        TntMinecartRenderer.renderWhiteSolidBlock(blockRenderer, ent.getEffect().getBlockState((IExplosiveEntity)entity), posestack, buffer, light, ent.getEffect().getBlock() instanceof LTNTBlock ? i / 5 % 2 == 0 : false);
	        posestack.popPose();
    	}
        super.render(entity, yaw, partialTicks, posestack, buffer, light);
    }
	
	public ResourceLocation getTextureLocation(Entity entity) {
		return InventoryMenu.BLOCK_ATLAS;
	}
}
