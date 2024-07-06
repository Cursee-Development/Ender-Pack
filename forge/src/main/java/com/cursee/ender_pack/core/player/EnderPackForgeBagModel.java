package com.cursee.ender_pack.core.player;

import com.cursee.ender_pack.Constants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class EnderPackForgeBagModel<T extends Entity> extends EntityModel<T>
{
	public static final ResourceLocation ENDERPACK_TEXTURE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/entity/ender_pack.png");
	
	private final ModelPart ender_pack;
	
	public EnderPackForgeBagModel(ModelPart root)
	{
		super(RenderType::entityCutoutNoCull);
		this.ender_pack = root.getChild("ender_pack");
	}
	
	public static LayerDefinition createBodyLayer()
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		partdefinition.addOrReplaceChild("ender_pack", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-4.0F, -1.0F, -13.0F, 10.0F, 12.0F, 6.0F)
				.texOffs(0, 18).addBox(-3.0F, -2.0F, -12.0F, 8.0F, 1.0F, 4.0F)
				.texOffs(28, 22).addBox(2.0F, -3.0F, -11.0F, 1.0F, 1.0F, 1.0F)
				.texOffs(0, 4).addBox(-1.0F, -3.0F, -11.0F, 1.0F, 1.0F, 1.0F)
				.texOffs(8, 28).addBox(-1.0F, -4.0F, -11.0F, 4.0F, 1.0F, 1.0F)
				.texOffs(20, 22).addBox(6.0F, 6.0F, -12.0F, 2.0F, 5.0F, 4.0F)
				.texOffs(0, 28).addBox(-6.0F, 6.0F, -12.0F, 2.0F, 5.0F, 4.0F)
				.texOffs(26, 0).addBox(-2.0F, 6.0F, -6.0F, 6.0F, 4.0F, 1.0F)
				.texOffs(0, 0).addBox(4.0F, 6.0F, -6.0F, 1.0F, 3.0F, 1.0F)
				.texOffs(0, 18).addBox(-3.0F, 6.0F, -6.0F, 1.0F, 3.0F, 1.0F)
				.texOffs(0, 23).addBox(-3.0F, 6.0F, -7.0F, 8.0F, 4.0F, 1.0F)
				.texOffs(18, 23).addBox(3.0F, 3.0F, -7.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 28).addBox(-2.0F, 3.0F, -7.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(20, 18).addBox(-3.0F, 0.0F, -7.0F, 8.0F, 3.0F, 1.0F),
			
			PartPose.offsetAndRotation(-1.0F, -4.0F, 10.0F, 0.0F, 0.0F, 0.0F));
		
		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int j, int k) {
		this.ender_pack.render(poseStack, vertexConsumer, packedLightIn, 1, 1);
	}

	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch)
	{
		// no animations, do nothing
	}
}
