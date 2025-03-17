package com.cursee.ender_pack;

import com.cursee.ender_pack.core.ServerConfigFabric;
import com.cursee.ender_pack.core.ServerConfiguredValues;
import com.cursee.ender_pack.core.network.ModMessagesFabric;
import com.cursee.ender_pack.core.network.packet.FabricSyncS2CPacket;
import com.cursee.ender_pack.core.registry.RegistryFabric;
import com.cursee.monolib.core.sailing.Sailing;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.network.FriendlyByteBuf;

public class EnderPackFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        EnderPack.init();
        Sailing.register(Constants.MOD_ID, Constants.MOD_NAME, Constants.MOD_VERSION, Constants.MOD_PUBLISHER, Constants.MOD_URL);
        RegistryFabric.register();
        ModMessagesFabric.registerC2SPackets();
        ServerWorldEvents.LOAD.register((server, world) -> ServerConfigFabric.onLoad());

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            FriendlyByteBuf data = PacketByteBufs.create();
            data.writeBoolean(ServerConfiguredValues.EXTRA_SLOT_ONLY);
            sender.sendPacket(FabricSyncS2CPacket.ENDER_PACK_SYNC, data);
        });
    }
}
