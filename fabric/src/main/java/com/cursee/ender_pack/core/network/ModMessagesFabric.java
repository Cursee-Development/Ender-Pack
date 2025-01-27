package com.cursee.ender_pack.core.network;

import com.cursee.ender_pack.EnderPack;
import com.cursee.ender_pack.core.network.packet.FabricOpenEnderPackC2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;

public class ModMessagesFabric {

    public static final ResourceLocation ENDER_PACK = EnderPack.identifier("ender_pack");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(ENDER_PACK, FabricOpenEnderPackC2SPacket::receive);
    }
}
