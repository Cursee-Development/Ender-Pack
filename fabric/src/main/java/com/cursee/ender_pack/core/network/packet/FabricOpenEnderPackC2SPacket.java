package com.cursee.ender_pack.core.network.packet;

import com.cursee.ender_pack.core.network.ModMessagesFabric;
import com.cursee.ender_pack.core.network.ModPacketHandler;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;

public class FabricOpenEnderPackC2SPacket implements CustomPacketPayload {

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ModMessagesFabric.PACKET_ID;
    }

    public static FabricOpenEnderPackC2SPacket read(RegistryFriendlyByteBuf buf) {
        return new FabricOpenEnderPackC2SPacket();
    }

    public void write(RegistryFriendlyByteBuf buf) {}

    public void handle(ServerPlayNetworking.Context packetContext) {
        ModPacketHandler.openPlayerEnderPackMenu(packetContext.player());
    }
}
