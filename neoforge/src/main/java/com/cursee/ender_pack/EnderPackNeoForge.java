package com.cursee.ender_pack;

import com.cursee.ender_pack.core.registry.RegistryNeoForge;
import com.cursee.monolib.core.sailing.Sailing;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class EnderPackNeoForge {

    public EnderPackNeoForge(IEventBus modEventBus) {
        EnderPack.init();
        Sailing.register(Constants.MOD_ID, Constants.MOD_NAME, Constants.MOD_VERSION, Constants.MOD_PUBLISHER, Constants.MOD_URL);
        RegistryNeoForge.register(modEventBus);
    }
}