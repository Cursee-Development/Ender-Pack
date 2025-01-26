package com.cursee.ender_pack;

import net.fabricmc.api.ClientModInitializer;

public class EnderPackClientFabric implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        EnderPackClient.init();
    }
}
