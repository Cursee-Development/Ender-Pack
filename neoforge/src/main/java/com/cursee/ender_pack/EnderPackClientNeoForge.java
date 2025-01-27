package com.cursee.ender_pack;

import com.cursee.ender_pack.client.block.entity.renderer.EnderPackBlockEntityRenderer;
import com.cursee.ender_pack.client.entity.layer.EnderPackPlayerRenderLayer;
import com.cursee.ender_pack.client.model.EnderPackModel;
import com.cursee.ender_pack.core.network.ModMessagesNeoForge;
import com.cursee.ender_pack.core.network.input.OpenEnderPackKeyNeoForge;
import com.cursee.ender_pack.core.network.packet.NeoForgeOpenEnderPackC2SPacket;
import com.cursee.ender_pack.core.registry.ModBlockEntities;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.function.Function;

public class EnderPackClientNeoForge {

    @EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ModClientBusEvents {

        @SubscribeEvent
        public static void onClientSetup(final FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                EnderPackClient.init();
            });
        }

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(OpenEnderPackKeyNeoForge.ENDER_PACK_KEY_MAPPING);
        }

        @SubscribeEvent
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.ENDER_PACK, EnderPackBlockEntityRenderer::new);
        }

        @SubscribeEvent
        public static void onRegisterLayerDefinitionsForEntityRenderers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(EnderPackClient.ENDER_PACK_PLAYER_MODEL_LAYER_LOCATION, EnderPackModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void onAddLayersForEntityRenderers(EntityRenderersEvent.AddLayers event) {
            EnderPackClientNeoForge.addLayerToPlayerSkin(event, "default", EnderPackPlayerRenderLayer::new);
            EnderPackClientNeoForge.addLayerToPlayerSkin(event, "slim", EnderPackPlayerRenderLayer::new);
        }
    }

    @EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
    public static class ForgeBusEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (OpenEnderPackKeyNeoForge.ENDER_PACK_KEY_MAPPING.consumeClick()) {
                PacketDistributor.sendToServer(new NeoForgeOpenEnderPackC2SPacket());
            }
        }
    }

    private static <E extends Player, M extends HumanoidModel<E>> void addLayerToPlayerSkin(EntityRenderersEvent.AddLayers event, String skinName, Function<LivingEntityRenderer<E, M>, ? extends RenderLayer<E, M>> factory) {
        LivingEntityRenderer renderer = event.getSkin(PlayerSkin.Model.byName(skinName));
        if (renderer != null) renderer.addLayer(factory.apply(renderer));
    }
}
