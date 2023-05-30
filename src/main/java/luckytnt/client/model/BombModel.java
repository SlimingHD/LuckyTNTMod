package luckytnt.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class BombModel <T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("luckytntmod", "tsa_bomb_model"), "main");
	private final ModelPart TsarBomb;

	public BombModel(ModelPart root) {
		this.TsarBomb = root.getChild("TsarBomb");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("TsarBomb", CubeListBuilder.create().texOffs(76, 26).addBox(14.8344F, -9.9988F, -0.998F, 5.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(76, 16).addBox(14.8344F, 4.0012F, -0.998F, 5.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(20, 67).addBox(14.8344F, -0.9988F, 4.002F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(60, 62).addBox(14.8344F, -0.9988F, -9.998F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 36).addBox(8.0844F, -2.9988F, -2.998F, 11.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(36, 18).addBox(2.0844F, -3.9988F, -3.998F, 6.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-17.9156F, -3.9988F, -3.998F, 20.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(56, 52).addBox(2.0844F, -4.9988F, -3.998F, 6.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 26).addBox(-11.9156F, -5.9988F, -3.998F, 14.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(-11.9156F, 4.0012F, -3.998F, 14.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(4, 21).addBox(8.0844F, -3.9988F, 3.002F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 21).addBox(8.0844F, -3.9988F, -3.998F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 5).addBox(8.0844F, 3.0012F, -3.998F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 5).addBox(8.0844F, 3.0012F, 3.002F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(56, 26).addBox(2.0844F, 4.0012F, -3.998F, 6.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(28, 43).addBox(8.0844F, -3.9988F, -2.998F, 12.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(34, 36).addBox(8.0844F, 3.0012F, -2.998F, 12.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(64, 35).addBox(8.0844F, -2.9988F, -3.998F, 12.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 61).addBox(8.0844F, -2.9988F, 3.002F, 12.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 76).addBox(2.0844F, -3.9988F, 4.002F, 6.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(19, 75).addBox(2.0844F, -3.9988F, -4.998F, 6.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 8).addBox(-17.9156F, -4.9988F, -3.998F, 6.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 60).addBox(-19.9156F, -3.9988F, -3.998F, 2.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(33, 75).addBox(-21.9156F, -1.9988F, -1.998F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(76, 52).addBox(-22.9156F, -1.9988F, -1.998F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(71, 0).addBox(-21.9156F, -2.9988F, -1.998F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(28, 36).addBox(-21.9156F, 2.0012F, -1.998F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(-21.9156F, -1.9988F, -2.998F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-21.9156F, -1.9988F, 2.002F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(68, 7).addBox(-17.9156F, -3.9988F, -4.998F, 6.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(70, 70).addBox(-17.9156F, -3.9988F, 4.002F, 6.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(56, 17).addBox(-17.9156F, 4.0012F, -3.998F, 6.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(32, 50).addBox(-11.9156F, -3.9988F, -5.998F, 14.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 50).addBox(-11.9156F, -3.9988F, 4.002F, 14.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(62, 50).addBox(-11.9156F, -4.9988F, 4.002F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(58, 47).addBox(-11.9156F, -4.9988F, -4.998F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(58, 45).addBox(-11.9156F, 4.0012F, -4.998F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(58, 43).addBox(-11.9156F, 4.0012F, 4.002F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0012F, 4.0844F, -0.002F, 0.0F, 0.0F, 1.5708F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		TsarBomb.render(poseStack, buffer, packedLight, packedOverlay);
	}
}
