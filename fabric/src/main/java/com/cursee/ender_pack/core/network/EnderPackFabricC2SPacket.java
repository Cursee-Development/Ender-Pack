package com.cursee.ender_pack.core.network;

import com.cursee.ender_pack.core.item.EnderPackFabricItem;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;

public class EnderPackFabricC2SPacket implements CustomPacketPayload {

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return EnderPackFabricNetwork.PACKET_ID;
    }

    public static EnderPackFabricC2SPacket read(RegistryFriendlyByteBuf buf) {
        return new EnderPackFabricC2SPacket();
    }

    public void write(RegistryFriendlyByteBuf buf) {}

    public void handle(ServerPlayNetworking.Context packetContext) {

        Player player = packetContext.player();

        if (player != null) {
            boolean shouldOpen = false;

            for (ItemStack stack : player.getInventory().items) {
                if (stack.getItem() instanceof EnderPackFabricItem) {
                    shouldOpen = true;
                }
            }

            final boolean opens = shouldOpen;
            packetContext.server().execute(() -> {
                if (opens) {
                    player.openMenu(new SimpleMenuProvider((containerID, inventory, currentPlayer) ->
                            ChestMenu.threeRows(containerID, inventory, player.getEnderChestInventory()),
                            Component.translatable("container.ender_pack")));
                }
            });
        }
    }
}
