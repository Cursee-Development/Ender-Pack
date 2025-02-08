package com.cursee.ender_pack.client.layer;

import com.cursee.ender_pack.EnderPackClient;
import com.cursee.ender_pack.client.model.EnderPackModel;
import com.cursee.ender_pack.core.ClientConfiguredValues;
import com.cursee.ender_pack.core.registry.ModItems;
import com.cursee.ender_pack.platform.Services;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.concurrent.atomic.AtomicBoolean;

public class EnderPackLayer<T extends Player, M extends HumanoidModel<T>> extends RenderLayer<T, M> {

    private final EnderPackModel model;

    public EnderPackLayer(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
        this.model = new EnderPackModel(Minecraft.getInstance().getEntityModels().bakeLayer(EnderPackClient.ENDER_PACK_PLAYER_MODEL_LAYER_LOCATION));
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, @NotNull Player entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
//        if (entity instanceof Player) {
//            boolean hasEnderpack = ((Player) entity).getInventory().armor.stream()
//                    .anyMatch(stack -> stack.getItem().getDescriptionId().toLowerCase().contains("enderpack"));
//
//            if (hasEnderpack) {
//                this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
//                poseStack.pushPose();
//                poseStack.translate(0F, -1F, 0.025F);
//                renderColoredCutoutModel(this.model, EnderPackClient.ENDER_PACK_TEXTURE_LOCATION, poseStack, multiBufferSource, i, entity, 1.0f, 1.0f, 1.0f);
//                poseStack.popPose();
//            }
//        }

        final AtomicBoolean SHOULD_RENDER_BAG = new AtomicBoolean(false);

        entity.getInventory().armor.forEach(stack -> {
            if (stack.is(ModItems.ENDER_PACK)) SHOULD_RENDER_BAG.set(ClientConfiguredValues.RENDERS_IN_ARMOR_SLOT); // originally would be true, now is configurable
        });

        if (Services.PLATFORM.isModLoaded("curios") || Services.PLATFORM.isModLoaded("trinkets")) {
            if (Services.PLATFORM.checkCompatibleSlots(entity)) SHOULD_RENDER_BAG.set(ClientConfiguredValues.RENDERS_IN_EXTRA_SLOT); // originally would be true, now is configurable
        }

        if (!SHOULD_RENDER_BAG.get()) return;

        poseStack.pushPose();
        // poseStack.translate(0F, -1F, 0.025F);

        final boolean crouching = entity.isCrouching();
        if (crouching) poseStack.mulPoseMatrix(new Matrix4f().setRotationXYZ(30.0f * (Mth.PI/180.0f), 0, 0));
        else poseStack.mulPoseMatrix(new Matrix4f().setRotationXYZ(0, 0, 0));

        final float xOffset = -0.0625f;
        final float yOffset = crouching ? 0.0625f * 4 : 0.0625f * 1;
        final float zOffset = crouching ? 1.0f - 0.17f : 1.0f - 0.0625f;
        poseStack.translate(xOffset, yOffset, zOffset);

        RenderLayer.renderColoredCutoutModel(this.model, EnderPackClient.ENDER_PACK_TEXTURE_LOCATION, poseStack, multiBufferSource, i, entity, 1.0f, 1.0f, 1.0f);
        poseStack.popPose();

//        pose.pushPose();
//        pose.translate(0.0d, 0.25d, 0.3125d);
//        RenderLayer.renderColoredCutoutModel(BAG_MODEL, getTextureLocation(entity), pose, buffer, light, entity, 1.0f, 1.0f, 1.0f);
//        pose.popPose();
    }
}
