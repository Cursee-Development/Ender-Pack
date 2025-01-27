package com.cursee.ender_pack;

import com.cursee.ender_pack.core.network.ModMessagesForge;
import com.cursee.ender_pack.core.registry.RegistryForge;
import com.cursee.monolib.core.sailing.Sailing;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class EnderPackForge {

    public static IEventBus EVENT_BUS = null;
    
    public EnderPackForge() {
        EnderPack.init();
        Sailing.register(Constants.MOD_ID, Constants.MOD_NAME, Constants.MOD_VERSION, Constants.MOD_PUBLISHER, Constants.MOD_URL);
        EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        RegistryForge.register(EVENT_BUS);
        EnderPackForge.EVENT_BUS.addListener(this::onCommonSetup);
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModMessagesForge::register);
    }
}