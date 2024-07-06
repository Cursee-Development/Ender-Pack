package com.cursee.ender_pack.core.network;

import com.cursee.ender_pack.Constants;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public class EnderPackFabricNetwork {

    public static final StreamCodec<RegistryFriendlyByteBuf, EnderPackFabricC2SPacket> PACKET_CODEC = StreamCodec.ofMember(EnderPackFabricC2SPacket::write, EnderPackFabricC2SPacket::read);
    public static final CustomPacketPayload.Type<EnderPackFabricC2SPacket> PACKET_ID = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "ender_pack"));
}
