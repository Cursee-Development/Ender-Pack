package com.cursee.ender_pack.core.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class EnderPackKeyBinding {
  public static final String KEY_NAME = "key.enderpack.toggle";
  public static final String KEY_CATEGORY = "key.category.enderpack.access";
  
  public static final KeyMapping ENDER_PACK_KEY_MAPPING = new KeyMapping(
    KEY_NAME,
    KeyConflictContext.IN_GAME,
    InputConstants.Type.KEYSYM,
    GLFW.GLFW_KEY_F9,
    KEY_CATEGORY
  );
}
