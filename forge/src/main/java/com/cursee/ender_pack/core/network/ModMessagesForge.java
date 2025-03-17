package com.cursee.ender_pack.core.network;

import com.cursee.ender_pack.Constants;
import com.cursee.ender_pack.core.network.packet.ForgeOpenEnderPackC2SPacket;
import com.cursee.ender_pack.core.network.packet.ForgeSyncS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessagesForge {

    private static SimpleChannel INSTANCE;

    private static int packetID = 0;
    private static int id() {
        return packetID++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Constants.MOD_ID, "ender_pack"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ForgeOpenEnderPackC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ForgeOpenEnderPackC2SPacket::new)
                .encoder(ForgeOpenEnderPackC2SPacket::toBytes)
                .consumerMainThread(ForgeOpenEnderPackC2SPacket::handle)
                .add();

        net.messageBuilder(ForgeSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ForgeSyncS2CPacket::decode)
                .encoder(ForgeSyncS2CPacket::encode)
                .consumerMainThread(ForgeSyncS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
