package com.cursee.ender_pack;

import com.cursee.ender_pack.core.EnderPackFabricRegistry;
import com.cursee.ender_pack.core.block.EnderPackFabricBlockEntityRenderer;
import com.cursee.ender_pack.core.layer.EnderPackFabricBagModel;
import com.cursee.ender_pack.core.layer.EnderPackFabricLayer;
import com.cursee.ender_pack.core.network.EnderPackFabricKeyBind;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;

public class EnderPackFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        EnderPackFabricKeyBind.register();

        BlockEntityRenderers.register(EnderPackFabricRegistry.ENDER_PACK_BLOCK_ENTITY, EnderPackFabricBlockEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(EnderPackClient.ENDER_PACK_LAYER, EnderPackFabricBagModel::createBodyLayer);

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if(entityRenderer instanceof PlayerRenderer renderer) {
                registrationHelper.register(new EnderPackFabricLayer<>(renderer));
            }
        });
    }
}
