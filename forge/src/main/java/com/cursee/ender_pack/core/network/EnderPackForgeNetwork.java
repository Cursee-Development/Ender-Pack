package com.cursee.ender_pack.core.network;

import com.cursee.ender_pack.Constants;
import com.cursee.ender_pack.core.network.EnderPackForgeC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;

public class EnderPackForgeNetwork {
  
   public static SimpleChannel INSTANCE;

   private static int packetID = 0;
   private static int id() {
     return packetID++;
   }

   public static void register() {
     SimpleChannel net = ChannelBuilder
       .named(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "messages"))
       .acceptedVersions(Channel.VersionTest.exact(1))
       .networkProtocolVersion(1)
       .simpleChannel();
       

     INSTANCE = net;


     net.messageBuilder(EnderPackForgeC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
       .decoder(EnderPackForgeC2SPacket::write)
       .encoder(EnderPackForgeC2SPacket::read)
       .consumerMainThread(EnderPackForgeC2SPacket::handle)
       .add();
   }
}
