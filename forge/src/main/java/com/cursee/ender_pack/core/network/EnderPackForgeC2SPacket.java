package com.cursee.ender_pack.core.network;

import com.cursee.ender_pack.Constants;
import com.cursee.ender_pack.core.EnderPackForgeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraftforge.event.network.CustomPayloadEvent;

import java.util.concurrent.atomic.AtomicBoolean;

public class EnderPackForgeC2SPacket implements CustomPacketPayload {

	public static final Type<EnderPackForgeC2SPacket> TYPE = new Type<EnderPackForgeC2SPacket>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "teleport"));



	public static void handle(EnderPackForgeC2SPacket packet, CustomPayloadEvent.Context context) {

		Player player = (Player) context.getSender();

		player.getServer().execute(() -> {
			AtomicBoolean shouldOpen = new AtomicBoolean(false);

			player.getInventory().armor.forEach(itemStack -> {
				if (itemStack.is(EnderPackForgeRegistry.ENDERPACK.get())) {
					shouldOpen.set(true);
				}
			});

			player.getInventory().items.forEach(itemStack -> {
				if (itemStack.is(EnderPackForgeRegistry.ENDERPACK.get())) {
					shouldOpen.set(true);
				}
			});

			if (shouldOpen.get()) {
				player.openMenu(new SimpleMenuProvider((p_53124_, p_53125_, p_53126_) -> {
					return ChestMenu.threeRows(p_53124_, p_53125_, player.getEnderChestInventory());
				}, Component.translatable("container.ender_pack")));
			}
		});
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public EnderPackForgeC2SPacket read(FriendlyByteBuf buf) {
		return new EnderPackForgeC2SPacket();
	}

	public static EnderPackForgeC2SPacket write(FriendlyByteBuf buf) {
		return new EnderPackForgeC2SPacket();
	}

}
