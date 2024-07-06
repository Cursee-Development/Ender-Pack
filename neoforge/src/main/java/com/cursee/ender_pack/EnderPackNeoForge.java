package com.cursee.ender_pack;


import com.cursee.ender_pack.core.EnderPackNeoForgeRegistry;
import com.cursee.ender_pack.core.block.EnderPackNeoForgeBlockEntityRenderer;
import com.cursee.ender_pack.core.network.EnderPackNeoForgeC2SPacket;
import com.cursee.ender_pack.core.player.EnderPackNeoForgeBagModel;
import com.cursee.ender_pack.core.player.EnderPackNeoForgeLayer;
import com.cursee.ender_pack.core.util.EnderPackKeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.function.Function;

@Mod(Constants.MOD_ID)
public class EnderPackNeoForge {

    public EnderPackNeoForge(IEventBus bus) {
        
        EnderPack.init();
        
        EnderPackNeoForgeRegistry.registerAll(bus);

        bus.addListener(this::registerPayloadHandler);
        bus.addListener(this::addCreative);
    }
    
    private void registerPayloadHandler(final RegisterPayloadHandlersEvent event)
    {
        final PayloadRegistrar registrar = event.registrar(Constants.MOD_ID);
        registrar.playToServer(EnderPackNeoForgeC2SPacket.TYPE, EnderPackNeoForgeC2SPacket.CODEC, EnderPackNeoForgeC2SPacket::handle);
    }
    
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(EnderPackNeoForgeRegistry.ENDERPACK.get());
        }
    }
    
    @EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        
        @SubscribeEvent
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(EnderPackNeoForgeRegistry.ENDER_PACK_BLOCK_ENTITY.get(), EnderPackNeoForgeBlockEntityRenderer::new);
        }
        
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(EnderPackKeyBinding.ENDER_PACK_KEY_MAPPING);
        }
        
        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event)
        {
            event.registerLayerDefinition(EnderPackClient.ENDER_PACK_LAYER, EnderPackNeoForgeBagModel::createBodyLayer);
        }
        
        @SubscribeEvent
        public static void construct(EntityRenderersEvent.AddLayers event)
        {
            addLayerToPlayerSkin(event, "default", EnderPackNeoForgeLayer::new);
            addLayerToPlayerSkin(event, "slim", EnderPackNeoForgeLayer::new);
        }
    }
    
    @EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void handleEventInput(ClientTickEvent.Post event) {

            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) {
                return;
            }

            if (EnderPackKeyBinding.ENDER_PACK_KEY_MAPPING.consumeClick()) {
                PacketDistributor.sendToServer(new EnderPackNeoForgeC2SPacket());
            }
        }
    }

    private static <E extends Player, M extends HumanoidModel<E>>
    void addLayerToPlayerSkin(EntityRenderersEvent.AddLayers event, String skinName, Function<LivingEntityRenderer<E, M>, ? extends RenderLayer<E, M>> factory)
    {
        LivingEntityRenderer renderer = event.getSkin(PlayerSkin.Model.byName(skinName));
        if (renderer != null) renderer.addLayer(factory.apply(renderer));
    }
    
}