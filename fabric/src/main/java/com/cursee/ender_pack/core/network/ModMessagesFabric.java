package com.cursee.ender_pack.core.network;

import com.cursee.ender_pack.EnderPack;
import com.cursee.ender_pack.core.network.packet.FabricOpenEnderPackC2SPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class ModMessagesFabric {

    public static final StreamCodec<RegistryFriendlyByteBuf, FabricOpenEnderPackC2SPacket> PACKET_CODEC = StreamCodec.ofMember(FabricOpenEnderPackC2SPacket::write, FabricOpenEnderPackC2SPacket::read);
    public static final CustomPacketPayload.Type<FabricOpenEnderPackC2SPacket> PACKET_ID = new CustomPacketPayload.Type<>(EnderPack.identifier("ender_pack"));
}
