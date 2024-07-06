package com.cursee.ender_pack.core.network;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class EnderPackFabricKeyBind {
    public static final String KEY_NAME = "key.ender_pack.toggle";
    public static final String KEY_CATEGORY = "key.category.ender_pack.access";

    public static KeyMapping drinkingKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(drinkingKey.consumeClick()) {
                ClientPlayNetworking.send(new EnderPackFabricC2SPacket());
            }
        });
    }

    public static void register() {
        drinkingKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                KEY_NAME,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_F9,
                KEY_CATEGORY
        ));

        registerKeyInputs();
    }
}
