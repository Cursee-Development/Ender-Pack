package com.cursee.ender_pack.core.player;

import com.cursee.ender_pack.EnderPackClient;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EnderPackForgeLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
  
  
  private final EnderPackForgeBagModel<T> bagModel;
  
  public EnderPackForgeLayer(RenderLayerParent<T, M> pRenderer) {
    super(pRenderer);
    
    bagModel = new EnderPackForgeBagModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(EnderPackClient.ENDER_PACK_LAYER));
  }
  
  @Override
  public void render(PoseStack poseStack, MultiBufferSource bufferSource, int lightness, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
    
    boolean flag = false;
    
    for (ItemStack itemStack : ((Player) entity).getInventory().armor) {
      if (itemStack.getItem().getDefaultInstance().getDisplayName().getString().toLowerCase().contains("ender pack")) {
        flag = true;
      }
    }
    
    if (flag) {
    
      poseStack.pushPose();
      poseStack.translate(0.0d, 0.25d, 0.3125d);

//      renderColoredCutoutModel(bagModel, getTextureLocation(entity), poseStack, bufferSource, lightness, entity, 1);

      VertexConsumer test = bufferSource.getBuffer(RenderType.entityCutoutNoCull(EnderPackClient.ENDER_PACK_TEXTURE));
      this.bagModel.renderToBuffer(poseStack, test, lightness, OverlayTexture.NO_OVERLAY);
    
      poseStack.popPose();
    }
    
  }
  
  @Override
  protected @NotNull ResourceLocation getTextureLocation(T pEntity)
  {
    return EnderPackClient.ENDER_PACK_TEXTURE;
  }
  
  private void translateToBody(LivingEntity entity, PoseStack poseStack)
  {
    this.getParentModel().body.translateAndRotate(poseStack);
    if (entity.isBaby() && !(entity instanceof Villager))
    {
      poseStack.scale(2.0F, 2.0F, 0.0F);
      poseStack.translate(1.0D, 1.0D, 1.0D);
    }
  }
}
