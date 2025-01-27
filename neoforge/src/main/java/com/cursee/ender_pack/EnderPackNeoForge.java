package com.cursee.ender_pack;

import com.cursee.ender_pack.core.network.ModMessagesNeoForge;
import com.cursee.ender_pack.core.network.packet.NeoForgeOpenEnderPackC2SPacket;
import com.cursee.ender_pack.core.registry.RegistryNeoForge;
import com.cursee.monolib.core.sailing.Sailing;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(Constants.MOD_ID)
public class EnderPackNeoForge {

    public static IEventBus EVENT_BUS = null;

    public EnderPackNeoForge(IEventBus modEventBus) {
        EnderPack.init();
        Sailing.register(Constants.MOD_ID, Constants.MOD_NAME, Constants.MOD_VERSION, Constants.MOD_PUBLISHER, Constants.MOD_URL);
        EVENT_BUS = modEventBus;
        RegistryNeoForge.register(EVENT_BUS);
        EnderPackNeoForge.EVENT_BUS.addListener(this::onRegisterPayloadHandlers);
    }

    private void onRegisterPayloadHandlers(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(Constants.MOD_ID);
        registrar.playToServer(ModMessagesNeoForge.PACKET_ID, ModMessagesNeoForge.PACKET_CODEC, NeoForgeOpenEnderPackC2SPacket::handle);
    }
}