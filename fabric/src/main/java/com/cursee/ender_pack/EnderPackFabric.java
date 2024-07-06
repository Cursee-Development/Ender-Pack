package com.cursee.ender_pack;

import com.cursee.ender_pack.core.EnderPackFabricRegistry;
import com.cursee.ender_pack.core.network.EnderPackFabricC2SPacket;
import com.cursee.ender_pack.core.network.EnderPackFabricNetwork;
import com.cursee.monolib.core.sailing.Sailing;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class EnderPackFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        
        EnderPack.init();

        Sailing.register(Constants.MOD_NAME, Constants.MOD_ID, Constants.MOD_VERSION, Constants.MC_VERSION_RAW, Constants.PUBLISHER_AUTHOR, Constants.PRIMARY_CURSEFORGE_MODRINTH);

        EnderPackFabricRegistry.register();

        PayloadTypeRegistry.playC2S().register(EnderPackFabricNetwork.PACKET_ID, EnderPackFabricNetwork.PACKET_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(EnderPackFabricNetwork.PACKET_ID, EnderPackFabricC2SPacket::handle);
    }
}
