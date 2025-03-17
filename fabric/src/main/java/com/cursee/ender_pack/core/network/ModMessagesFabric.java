package com.cursee.ender_pack.core.network;

import com.cursee.ender_pack.EnderPack;
import com.cursee.ender_pack.core.network.packet.FabricOpenEnderPackC2SPacket;
import com.cursee.ender_pack.core.network.packet.FabricSyncS2CPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

public class ModMessagesFabric {

    public static final ResourceLocation ENDER_PACK = EnderPack.identifier("ender_pack");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(ENDER_PACK, FabricOpenEnderPackC2SPacket::receive);
    }

    public static void registerS2CPackets() {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) ClientPlayNetworking.registerGlobalReceiver(FabricSyncS2CPacket.ENDER_PACK_SYNC, FabricSyncS2CPacket::receive);
    }
}
