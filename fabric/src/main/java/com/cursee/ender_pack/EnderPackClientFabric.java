package com.cursee.ender_pack;

import com.cursee.ender_pack.client.block.entity.renderer.EnderPackBlockEntityRenderer;
import com.cursee.ender_pack.client.model.EnderPackModel;
import com.cursee.ender_pack.client.renderer.EnderPackArmorRenderer;
import com.cursee.ender_pack.client.renderer.EnderPackTrinketRenderer;
import com.cursee.ender_pack.core.ClientConfigFabric;
import com.cursee.ender_pack.core.network.ModMessagesFabric;
import com.cursee.ender_pack.core.network.input.OpenEnderPackKeyFabric;
import com.cursee.ender_pack.core.registry.ModBlockEntities;
import com.cursee.ender_pack.core.registry.ModItems;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;

public class EnderPackClientFabric implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        EnderPackClient.init();

        OpenEnderPackKeyFabric.register();

        BlockEntityRenderers.register(ModBlockEntities.ENDER_PACK, EnderPackBlockEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(EnderPackClient.ENDER_PACK_PLAYER_MODEL_LAYER_LOCATION, EnderPackModel::createBodyLayer);

//        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
//            if (entityRenderer instanceof PlayerRenderer) registrationHelper.register(new EnderPackPlayerRenderLayer(entityRenderer));
//        });

        ArmorRenderer.register(new EnderPackArmorRenderer(), ModItems.ENDER_PACK);
        TrinketRendererRegistry.registerRenderer(ModItems.ENDER_PACK, new EnderPackTrinketRenderer());

        ClientConfigFabric.onLoad();

        ModMessagesFabric.registerS2CPackets();
    }
}
