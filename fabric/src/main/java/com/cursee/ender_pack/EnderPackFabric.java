package com.cursee.ender_pack;

import com.cursee.ender_pack.core.registry.RegistryFabric;
import com.cursee.monolib.core.sailing.Sailing;
import net.fabricmc.api.ModInitializer;

public class EnderPackFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        EnderPack.init();
        Sailing.register(Constants.MOD_ID, Constants.MOD_NAME, Constants.MOD_VERSION, Constants.MOD_PUBLISHER, Constants.MOD_URL);
        RegistryFabric.register();
    }
}
