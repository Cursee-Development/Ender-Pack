package com.cursee.ender_pack.core.network.input;

import com.cursee.ender_pack.Constants;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class OpenEnderPackKeyNeoForge {

    public static final KeyMapping ENDER_PACK_KEY_MAPPING = new KeyMapping(
            Constants.KEY_NAME,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_F9,
            Constants.KEY_CATEGORY
    );
}
