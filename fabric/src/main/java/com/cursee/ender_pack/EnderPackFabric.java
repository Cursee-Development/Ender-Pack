package com.cursee.ender_pack;

import com.cursee.ender_pack.core.ServerConfigFabric;
import com.cursee.ender_pack.core.network.ModMessagesFabric;
import com.cursee.ender_pack.core.registry.RegistryFabric;
import com.cursee.monolib.core.sailing.Sailing;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;

public class EnderPackFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        EnderPack.init();
        Sailing.register(Constants.MOD_ID, Constants.MOD_NAME, Constants.MOD_VERSION, Constants.MOD_PUBLISHER, Constants.MOD_URL);
        RegistryFabric.register();
        ModMessagesFabric.registerC2SPackets();
        ServerWorldEvents.LOAD.register((server, world) -> ServerConfigFabric.onLoad());
    }
}
