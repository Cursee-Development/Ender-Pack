package com.cursee.ender_pack.core.network;

import com.cursee.ender_pack.Constants;
import com.cursee.ender_pack.core.EnderPackNeoForgeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class EnderPackNeoForgeC2SPacket implements CustomPacketPayload {

	public static final Type<EnderPackNeoForgeC2SPacket> TYPE = new Type<EnderPackNeoForgeC2SPacket>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "teleport"));

	public static final StreamCodec<FriendlyByteBuf, EnderPackNeoForgeC2SPacket> CODEC = StreamCodec.ofMember(EnderPackNeoForgeC2SPacket::write, EnderPackNeoForgeC2SPacket::read);
	
//	public static void receive(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
//		if (player.getInventory().contains(EnderPackNeoForgeRegistry.ENDERPACK.get().getDefaultInstance())) {
//			player.openMenu(new SimpleMenuProvider((p_53124_, p_53125_, p_53126_) -> {
//				return ChestMenu.threeRows(p_53124_, p_53125_, player.getEnderChestInventory());
//			}, Component.translatable("container.ender_pack")));
//		}
//	}

	public static void handle(EnderPackNeoForgeC2SPacket packet, IPayloadContext context) {

		Player player = context.player();

//		player.getServer().execute(() -> {
//			if (player.getInventory().contains(EnderPackNeoForgeRegistry.ENDERPACK.get().getDefaultInstance())) {
//				player.openMenu(new SimpleMenuProvider((p_53124_, p_53125_, p_53126_) -> {
//					return ChestMenu.threeRows(p_53124_, p_53125_, player.getEnderChestInventory());
//				}, Component.translatable("container.ender_pack")));
//			}
//		});

		player.getServer().execute(() -> {
			if (player.getInventory().contains(EnderPackNeoForgeRegistry.ENDERPACK.get().getDefaultInstance())) {
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

	public static EnderPackNeoForgeC2SPacket read(FriendlyByteBuf buf) {
		return new EnderPackNeoForgeC2SPacket();
	}

	public void write(FriendlyByteBuf buf) {
	}

//	public EnderPackNeoForgeC2SPacket() {}
//	public EnderPackNeoForgeC2SPacket(FriendlyByteBuf buf) {}
//	public void toBytes(FriendlyByteBuf buf) {}
//
//	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
//
//		NetworkEvent.Context context = supplier.get();
//
//		context.enqueueWork(() -> {
//			ServerPlayer pPlayer = context.getSender();
//
//			if (pPlayer.getInventory().contains(EnderPackNeoForgeRegistry.ENDERPACK.get().getDefaultInstance())) {
//				pPlayer.openMenu(new SimpleMenuProvider((p_53124_, p_53125_, p_53126_) -> {
//					return ChestMenu.threeRows(p_53124_, p_53125_, pPlayer.getEnderChestInventory());
//				}, Component.translatable("container.ender_pack")));
//			}
//
//
//
//		});
//
//		return true;
//	}
}
