package com.cursee.ender_pack.core.network;

import com.cursee.ender_pack.EnderPack;
import com.cursee.ender_pack.core.network.packet.ForgeOpenEnderPackC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.*;

public class ModMessagesForge {

    // public static final StreamCodec<FriendlyByteBuf, ForgeOpenEnderPackC2SPacket> PACKET_CODEC = StreamCodec.ofMember(ForgeOpenEnderPackC2SPacket::write, ForgeOpenEnderPackC2SPacket::read);
    public static final CustomPacketPayload.Type<ForgeOpenEnderPackC2SPacket> PACKET_ID = new CustomPacketPayload.Type<>(EnderPack.identifier("ender_pack"));

    private static SimpleChannel INSTANCE;

    private static int packetID = 0;
    private static int id() {
        return packetID++;
    }

    private static final String PROTOCOL_VERSION = "1";

    public static void register() {
//        SimpleChannel net = ChannelBuilder
//                .named(EnderPack.identifier("ender_pack"))
//                .networkProtocolVersion(() -> "1.0")
//                .clientAcceptedVersions(s -> true)
//                .serverAcceptedVersions(s -> true)
//                .simpleChannel();

        SimpleChannel net = ChannelBuilder
                .named(EnderPack.identifier("ender_pack"))
                .networkProtocolVersion(1)
                .clientAcceptedVersions(Channel.VersionTest.exact(1))
                .serverAcceptedVersions(Channel.VersionTest.exact(1))
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ForgeOpenEnderPackC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ForgeOpenEnderPackC2SPacket::new)
                .encoder(ForgeOpenEnderPackC2SPacket::toBytes)
                .consumerMainThread(ForgeOpenEnderPackC2SPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        // INSTANCE.sendToServer(message);
        // INSTANCE.send(message, Minecraft.getInstance().getConnection().getConnection());
        INSTANCE.send(message, PacketDistributor.SERVER.noArg());
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(message, PacketDistributor.PLAYER.noArg());
    }
}
