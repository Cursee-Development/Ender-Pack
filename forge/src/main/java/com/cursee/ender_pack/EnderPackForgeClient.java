package com.cursee.ender_pack;

import com.cursee.ender_pack.core.EnderPackForgeRegistry;
import com.cursee.ender_pack.core.block.EnderPackForgeBlockEntityRenderer;
import com.cursee.ender_pack.core.network.EnderPackForgeC2SPacket;
import com.cursee.ender_pack.core.network.EnderPackForgeNetwork;
import com.cursee.ender_pack.core.player.EnderPackForgeBagModel;
import com.cursee.ender_pack.core.player.EnderPackForgeLayer;
import com.cursee.ender_pack.core.util.EnderPackKeyBinding;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Function;

public class EnderPackForgeClient {
	
	// public static ModelLayerLocation ENDERPACK_LAYER = new ModelLayerLocation(new ResourceLocation("minecraft:player"), "ender_pack");
	
	@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		
		@SubscribeEvent
		public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
			event.registerBlockEntityRenderer(EnderPackForgeRegistry.ENDER_PACK_BLOCK_ENTITY.get(), EnderPackForgeBlockEntityRenderer::new);
		}
		
		@SubscribeEvent
		public static void onKeyRegister(RegisterKeyMappingsEvent event) {
			event.register(EnderPackKeyBinding.ENDER_PACK_KEY_MAPPING);
		}
		
		@SubscribeEvent
		public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event)
		{
			event.registerLayerDefinition(EnderPackClient.ENDER_PACK_LAYER, EnderPackForgeBagModel::createBodyLayer);
		}
		
		@SubscribeEvent
		public static void construct(EntityRenderersEvent.AddLayers event)
		{
			addLayerToPlayerSkin(event, "default", EnderPackForgeLayer::new);
			addLayerToPlayerSkin(event, "slim", EnderPackForgeLayer::new);
		}
	}
	
	@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
	public static class ClientForgeEvents {
		@SubscribeEvent
		public static void onKeyInput(InputEvent.Key event) {
			if (EnderPackKeyBinding.ENDER_PACK_KEY_MAPPING.consumeClick()) {

				EnderPackForgeNetwork.INSTANCE.send(new EnderPackForgeC2SPacket(), PacketDistributor.SERVER.noArg());
			}
		}
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	private static <E extends Player, M extends HumanoidModel<E>>
	void addLayerToPlayerSkin(EntityRenderersEvent.AddLayers event, String skinName, Function<LivingEntityRenderer<E, M>, ? extends RenderLayer<E, M>> factory)
	{
		LivingEntityRenderer renderer = event.getPlayerSkin(PlayerSkin.Model.byName(skinName));
		if (renderer != null) renderer.addLayer(factory.apply(renderer));
	}
}
