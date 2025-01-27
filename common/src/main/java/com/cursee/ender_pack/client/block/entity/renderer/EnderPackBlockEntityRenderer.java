package com.cursee.ender_pack.client.block.entity.renderer;

import com.cursee.ender_pack.EnderPackClient;
import com.cursee.ender_pack.core.block.entity.type.EnderPackBlockEntity;
import com.cursee.ender_pack.core.block.type.EnderPackBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class EnderPackBlockEntityRenderer implements BlockEntityRenderer<EnderPackBlockEntity> {

    private final ModelPart BAG_MODEL;

    public EnderPackBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart rootModelPart = context.bakeLayer(EnderPackClient.ENDER_PACK_PLAYER_MODEL_LAYER_LOCATION);
        this.BAG_MODEL = rootModelPart.getChild("ender_pack");
    }

    @Override
    public void render(EnderPackBlockEntity enderPack, float partialTick, PoseStack pose, MultiBufferSource buffer, int light, int overlay) {

        if (!enderPack.hasLevel()) return;
        final Direction FACING_DIRECTION = enderPack.getBlockState().getValue(EnderPackBlock.FACING);
        Vector3f offset = new Vector3f();
        float rotationDegrees = 0;

        switch (FACING_DIRECTION) {
            case NORTH -> {
                offset.set(1f, 0.125f, 1f);
                rotationDegrees = 180;
                break;
            }
            case EAST -> {
                offset.set(0f, 0.125f, 1f);
                rotationDegrees = 90;
                break;
            }
            case SOUTH -> {
                offset.set(0.0f, 0.125f, 0f);
                break;
            }
            case WEST -> {
                offset.set(1f, 0.125f, 0f);
                rotationDegrees = 270;
                break;
            }
        }

        pose.pushPose();

        pose.translate(offset.x, offset.y, offset.z);
        pose.mulPose(com.mojang.math.Axis.YP.rotationDegrees(rotationDegrees));
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(EnderPackClient.ENDER_PACK_TEXTURE_LOCATION));
        pose.translate(0.5D, 0.3125D, 0.5625D);
        pose.mulPose(com.mojang.math.Axis.ZP.rotationDegrees(180));
        BAG_MODEL.render(pose, vertexConsumer, light, overlay);

        pose.popPose();
    }
}
