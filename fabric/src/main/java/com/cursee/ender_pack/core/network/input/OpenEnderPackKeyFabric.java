package com.cursee.ender_pack.core.network.input;

import com.cursee.ender_pack.Constants;
import com.cursee.ender_pack.core.network.packet.FabricOpenEnderPackC2SPacket;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class OpenEnderPackKeyFabric {

    public static KeyMapping KEY_OPEN_ENDER_PACK;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(OpenEnderPackKeyFabric.KEY_OPEN_ENDER_PACK.consumeClick()) {
                ClientPlayNetworking.send(new FabricOpenEnderPackC2SPacket());
            }
        });
    }

    public static void register() {

        OpenEnderPackKeyFabric.KEY_OPEN_ENDER_PACK = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                Constants.KEY_NAME,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_F9,
                Constants.KEY_CATEGORY
        ));

        OpenEnderPackKeyFabric.registerKeyInputs();
    }
}
