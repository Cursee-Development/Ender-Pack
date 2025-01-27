package com.cursee.ender_pack.core.network;

import com.cursee.ender_pack.EnderPack;
import com.cursee.ender_pack.core.network.packet.NeoForgeOpenEnderPackC2SPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class ModMessagesNeoForge {

    public static final StreamCodec<FriendlyByteBuf, NeoForgeOpenEnderPackC2SPacket> PACKET_CODEC = StreamCodec.ofMember(NeoForgeOpenEnderPackC2SPacket::write, NeoForgeOpenEnderPackC2SPacket::read);
    public static final CustomPacketPayload.Type<NeoForgeOpenEnderPackC2SPacket> PACKET_ID = new CustomPacketPayload.Type<>(EnderPack.identifier("ender_pack"));
}
