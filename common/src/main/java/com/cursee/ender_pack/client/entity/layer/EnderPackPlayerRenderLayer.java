package com.cursee.ender_pack.client.entity.layer;

import com.cursee.ender_pack.EnderPackClient;
import com.cursee.ender_pack.client.model.EnderPackModel;
import com.cursee.ender_pack.core.registry.ModItems;
import com.cursee.ender_pack.platform.Services;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.atomic.AtomicBoolean;

/** Must conform to <T extends LivingEntity> void register(RenderLayer<T, ? extends EntityModel<T>> featureRenderer); */
public class EnderPackPlayerRenderLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    private final EnderPackModel BAG_MODEL;

    public EnderPackPlayerRenderLayer(RenderLayerParent<T, M> parent) {
        super(parent);
        BAG_MODEL = new EnderPackModel(Minecraft.getInstance().getEntityModels().bakeLayer(EnderPackClient.ENDER_PACK_PLAYER_MODEL_LAYER_LOCATION));
    }

    @Override
    protected ResourceLocation getTextureLocation(T player) {
        return EnderPackClient.ENDER_PACK_TEXTURE_LOCATION;
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource buffer, int light, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch) {

        if (!(entity instanceof Player player)) return;

        final AtomicBoolean SHOULD_RENDER_BAG = new AtomicBoolean(false);

        player.getInventory().armor.forEach(stack -> {
            if (stack.is(ModItems.ENDER_PACK)) SHOULD_RENDER_BAG.set(true);
        });

        if (Services.PLATFORM.isModLoaded("curios") || Services.PLATFORM.isModLoaded("trinkets")) {
            if (Services.PLATFORM.checkCompatibleSlots(player)) SHOULD_RENDER_BAG.set(true);
        }

        if (!SHOULD_RENDER_BAG.get()) return;

        pose.pushPose();
        pose.translate(0.0d, 0.25d, 0.3125d);
        RenderLayer.renderColoredCutoutModel(BAG_MODEL, getTextureLocation(entity), pose, buffer, light, player, 0xFFFFFFFF);
        pose.popPose();
    }
}
