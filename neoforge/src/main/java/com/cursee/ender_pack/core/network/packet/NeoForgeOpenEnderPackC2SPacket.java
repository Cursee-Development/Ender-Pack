package com.cursee.ender_pack.core.network.packet;

import com.cursee.ender_pack.core.network.ModMessagesNeoForge;
import com.cursee.ender_pack.core.network.ModPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class NeoForgeOpenEnderPackC2SPacket implements CustomPacketPayload {

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ModMessagesNeoForge.PACKET_ID;
    }

    public static NeoForgeOpenEnderPackC2SPacket read(FriendlyByteBuf buf) {
        return new NeoForgeOpenEnderPackC2SPacket();
    }

    public void write(FriendlyByteBuf buf) {}

    public static boolean handle(NeoForgeOpenEnderPackC2SPacket packet, IPayloadContext context) {
        ModPacketHandler.openPlayerEnderPackMenu((ServerPlayer) context.player());
        return true;
    }
}
