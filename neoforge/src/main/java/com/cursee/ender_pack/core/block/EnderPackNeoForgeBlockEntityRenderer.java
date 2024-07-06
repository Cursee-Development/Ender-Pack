package com.cursee.ender_pack.core.block;

import com.cursee.ender_pack.EnderPackClient;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.joml.Vector3f;

public class EnderPackNeoForgeBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {

  private final ModelPart ender_pack;
  
  public EnderPackNeoForgeBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
     ModelPart root = context.bakeLayer(EnderPackClient.ENDER_PACK_LAYER);
     this.ender_pack = root.getChild("ender_pack");
  }
  
  @Override
  public void render(T blockEntity, float partialTick, PoseStack pose, MultiBufferSource buffer, int packedLight, int packedOverlay) {
    Level level = blockEntity.getLevel();
    boolean hasLevel = level != null;
    if (hasLevel) {
      BlockState state = blockEntity.getBlockState();
      if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {

        pose.pushPose();

        Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

        Vector3f offset = new Vector3f();
        float rotationDegrees = 0;

        switch (facing) {
          case NORTH:
            offset.set(1f, 0.125f, 1f);
            rotationDegrees = 180;
            break;
          case EAST:
            offset.set(0f, 0.125f, 1f);
            rotationDegrees = 90;
            break;
          case SOUTH:
            offset.set(0.0f, 0.125f, 0f);
            break;
          case WEST:
            offset.set(1f, 0.125f, 0f);
            rotationDegrees = 270;
            break;
        }

        pose.translate(offset.x, offset.y, offset.z);
        pose.mulPose(com.mojang.math.Axis.YP.rotationDegrees(rotationDegrees));
        
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(EnderPackClient.ENDER_PACK_TEXTURE));
        
        pose.translate(0.5D, 0.3125D, 0.5625D);
        
        pose.mulPose(com.mojang.math.Axis.ZP.rotationDegrees(180));
        ender_pack.render(pose, vertexConsumer, packedLight, packedOverlay);
        
        pose.popPose();
        
      }
    }
  }
}
