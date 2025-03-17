package com.cursee.ender_pack.core.network.packet;

import com.cursee.ender_pack.EnderPack;
import com.cursee.ender_pack.core.ServerConfiguredValues;
import com.cursee.ender_pack.core.network.ModPacketHandler;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;

public class FabricSyncS2CPacket implements FabricPacket {

    public static final ResourceLocation ENDER_PACK_SYNC = EnderPack.identifier("ender_pack_sync");
    public static PacketType<FabricSyncS2CPacket> TYPE = PacketType.create(ENDER_PACK_SYNC, FabricSyncS2CPacket::read);

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(ServerConfiguredValues.EXTRA_SLOT_ONLY);
    }

    private static FabricSyncS2CPacket read(FriendlyByteBuf friendlyByteBuf) {

        HashMap<String, Object> mappedObjectValues = new HashMap<>();
        mappedObjectValues.put("extra_slot_only", friendlyByteBuf.readBoolean());
        ModPacketHandler.syncCommonValues(mappedObjectValues);

        return new FabricSyncS2CPacket();
    }

    public static void receive(Minecraft minecraft, ClientPacketListener clientPacketListener, FriendlyByteBuf friendlyByteBuf, PacketSender packetSender) {
        HashMap<String, Object> mappedObjectValues = new HashMap<>();
        mappedObjectValues.put("extra_slot_only", friendlyByteBuf.readBoolean());
        ModPacketHandler.syncCommonValues(mappedObjectValues);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
