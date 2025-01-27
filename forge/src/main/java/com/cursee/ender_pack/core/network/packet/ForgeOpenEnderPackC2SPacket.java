package com.cursee.ender_pack.core.network.packet;

import com.cursee.ender_pack.core.network.ModPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ForgeOpenEnderPackC2SPacket {

    public ForgeOpenEnderPackC2SPacket() {}
    public ForgeOpenEnderPackC2SPacket(FriendlyByteBuf buf) {}
    public void toBytes(FriendlyByteBuf buf) {}

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        ModPacketHandler.openPlayerEnderPackMenu(supplier.get().getSender());
        return true;
    }
}
