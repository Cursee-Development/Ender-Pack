package com.cursee.ender_pack.core.network.packet;

import com.cursee.ender_pack.core.ServerConfiguredValues;
import com.cursee.ender_pack.core.network.ModPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.function.Supplier;

public class ForgeSyncS2CPacket {

    public ForgeSyncS2CPacket() {}

    public void encode(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(ServerConfiguredValues.EXTRA_SLOT_ONLY);
    }

    public static ForgeSyncS2CPacket decode(FriendlyByteBuf friendlyByteBuf) {

        HashMap<String, Object> mappedObjectValues = new HashMap<>();
        mappedObjectValues.put("extra_slot_only", friendlyByteBuf.readBoolean());
        ModPacketHandler.syncCommonValues(mappedObjectValues);

        // dummy
        return new ForgeSyncS2CPacket();
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {});
        contextSupplier.get().setPacketHandled(true);
    }
}
