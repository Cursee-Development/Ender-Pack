package com.cursee.ender_pack.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class EnderPackModel extends EntityModel<Player> {

    private final ModelPart BAG_MODEL;

    public EnderPackModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.BAG_MODEL = root.getChild("ender_pack");
    }

    @Override
    public void renderToBuffer(PoseStack pose, VertexConsumer consumer, int light, int overlay, float red, float green, float blue, float alpha) {
        BAG_MODEL.render(pose, consumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static CubeListBuilder builder() {
        return CubeListBuilder.create()
            .texOffs(0,   0).addBox(-4.0F, -1.0F, -13.0F, 10.0F, 12.0F, 6.0F)
            .texOffs(0,  18).addBox(-3.0F, -2.0F, -12.0F,  8.0F,  1.0F, 4.0F)
            .texOffs(28, 22).addBox(2.0F,  -3.0F, -11.0F,  1.0F,  1.0F, 1.0F)
            .texOffs(0,   4).addBox(-1.0F, -3.0F, -11.0F,  1.0F,  1.0F, 1.0F)
            .texOffs(8,  28).addBox(-1.0F, -4.0F, -11.0F,  4.0F,  1.0F, 1.0F)
            .texOffs(20, 22).addBox(6.0F,   6.0F, -12.0F,  2.0F,  5.0F, 4.0F)
            .texOffs(0,  28).addBox(-6.0F,  6.0F, -12.0F,  2.0F,  5.0F, 4.0F)
            .texOffs(26,  0).addBox(-2.0F,  6.0F,  -6.0F,  6.0F,  4.0F, 1.0F)
            .texOffs(0,   0).addBox(4.0F,   6.0F,  -6.0F,  1.0F,  3.0F, 1.0F)
            .texOffs(0,  18).addBox(-3.0F,  6.0F,  -6.0F,  1.0F,  3.0F, 1.0F)
            .texOffs(0,  23).addBox(-3.0F,  6.0F,  -7.0F,  8.0F,  4.0F, 1.0F)
            .texOffs(18, 23).addBox(3.0F,   3.0F,  -7.0F,  1.0F,  2.0F, 1.0F)
            .texOffs(0,  28).addBox(-2.0F,  3.0F,  -7.0F,  1.0F,  2.0F, 1.0F)
            .texOffs(20, 18).addBox(-3.0F,  0.0F,  -7.0F,  8.0F,  3.0F, 1.0F);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        meshdefinition.getRoot().addOrReplaceChild("ender_pack", builder(), PartPose.offset(0, 0, 0));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Player player, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        // no-op
    }
}
