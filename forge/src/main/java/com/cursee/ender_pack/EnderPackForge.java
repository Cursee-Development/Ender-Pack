package com.cursee.ender_pack;

import com.cursee.ender_pack.core.ServerConfigForge;
import com.cursee.ender_pack.core.network.ModMessagesForge;
import com.cursee.ender_pack.core.network.packet.ForgeSyncS2CPacket;
import com.cursee.ender_pack.core.registry.RegistryForge;
import com.cursee.monolib.core.sailing.Sailing;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Consumer;

@Mod(Constants.MOD_ID)
public class EnderPackForge {

    public static IEventBus EVENT_BUS = null;
    
    public EnderPackForge() {
        EnderPack.init();
        Sailing.register(Constants.MOD_ID, Constants.MOD_NAME, Constants.MOD_VERSION, Constants.MOD_PUBLISHER, Constants.MOD_URL);
        EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        RegistryForge.register(EVENT_BUS);
        EnderPackForge.EVENT_BUS.addListener(this::onCommonSetup);
        MinecraftForge.EVENT_BUS.addListener((Consumer<LevelEvent.Load>) consumer -> ServerConfigForge.onLoad());
        MinecraftForge.EVENT_BUS.addListener((Consumer<EntityJoinLevelEvent>) event -> {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;
            ModMessagesForge.sendToPlayer(new ForgeSyncS2CPacket(), player);
        });

    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModMessagesForge::register);
    }
}