package com.cursee.ender_pack.core.network.packet;

import com.cursee.ender_pack.core.network.ModMessagesForge;
import com.cursee.ender_pack.core.network.ModPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraftforge.event.network.CustomPayloadEvent;

import java.util.function.Supplier;

public class ForgeOpenEnderPackC2SPacket implements CustomPacketPayload {

    public ForgeOpenEnderPackC2SPacket() {}
    public ForgeOpenEnderPackC2SPacket(FriendlyByteBuf buf) {}
    public void toBytes(RegistryFriendlyByteBuf registryFriendlyByteBuf) {}

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ModMessagesForge.PACKET_ID;
    }

    public void handle(CustomPayloadEvent.Context context) {
        ModPacketHandler.openPlayerEnderPackMenu(context.getSender());
    }
}
