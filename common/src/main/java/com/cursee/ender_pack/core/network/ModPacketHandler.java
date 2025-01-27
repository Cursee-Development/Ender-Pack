package com.cursee.ender_pack.core.network;

import com.cursee.ender_pack.core.registry.ModItems;
import com.cursee.ender_pack.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.ChestMenu;

import java.util.concurrent.atomic.AtomicBoolean;

public class ModPacketHandler {

    public static void openPlayerEnderPackMenu(ServerPlayer player) {

        final AtomicBoolean SHOULD_OPEN = new AtomicBoolean(false);

        player.getInventory().armor.forEach(itemStack -> {
            if (itemStack.is(ModItems.ENDER_PACK)) SHOULD_OPEN.set(true);
        });
        player.getInventory().items.forEach(itemStack -> {
            if (itemStack.is(ModItems.ENDER_PACK)) SHOULD_OPEN.set(true);
        });
        if (player.getItemBySlot(EquipmentSlot.MAINHAND).is(ModItems.ENDER_PACK)) SHOULD_OPEN.set(true);
        if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.ENDER_PACK)) SHOULD_OPEN.set(true);

        if (Services.PLATFORM.checkCompatibleSlots(player)) SHOULD_OPEN.set(true);

        if (!SHOULD_OPEN.get()) return;

        player.openMenu(new SimpleMenuProvider((i, inventory, playerX) -> ChestMenu.threeRows(i, inventory, player.getEnderChestInventory()), Component.translatable("container.enderPack")));
    }
}
