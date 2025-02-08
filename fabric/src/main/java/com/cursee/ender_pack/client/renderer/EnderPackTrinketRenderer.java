package com.cursee.ender_pack.client.renderer;

import com.cursee.ender_pack.EnderPackClient;
import com.cursee.ender_pack.client.model.EnderPackModel;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

public class EnderPackTrinketRenderer implements TrinketRenderer {

    @Override
    public void render(ItemStack itemStack, SlotReference slotReference, EntityModel<? extends LivingEntity> entityModel, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, LivingEntity entity, float v, float v1, float v2, float v3, float v4, float v5) {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        Model model = new EnderPackModel(modelSet.bakeLayer(EnderPackClient.ENDER_PACK_PLAYER_MODEL_LAYER_LOCATION));

        // model.renderToBuffer(poseStack, multiBufferSource.getBuffer(model.renderType(EnderPackClient.ENDER_PACK_TEXTURE_LOCATION)), light, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);

        poseStack.pushPose();

        final boolean crouching = entity.isCrouching();
        if (crouching) poseStack.mulPoseMatrix(new Matrix4f().setRotationXYZ(30.0f * (Mth.PI/180.0f), 0, 0));
        else poseStack.mulPoseMatrix(new Matrix4f().setRotationXYZ(0, 0, 0));

        final float xOffset = -0.0625f;
        final float yOffset = crouching ? 0.0625f * 4 : 0.0625f * 1;
        final float zOffset = crouching ? 1.0f - 0.17f : 1.0f - 0.0625f;
        poseStack.translate(xOffset, yOffset, zOffset);

        model.renderToBuffer(poseStack, multiBufferSource.getBuffer(model.renderType(EnderPackClient.ENDER_PACK_TEXTURE_LOCATION)), light, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);
        poseStack.popPose();
    }
}
