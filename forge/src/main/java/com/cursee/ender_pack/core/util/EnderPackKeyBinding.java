package com.cursee.ender_pack.core.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class EnderPackKeyBinding {
  public static final String KEY_NAME = "key.ender_pack.toggle";
  public static final String KEY_CATEGORY = "key.category.ender_pack.access";
  
  public static final KeyMapping ENDER_PACK_KEY_MAPPING = new KeyMapping(
    KEY_NAME,
    KeyConflictContext.IN_GAME,
    InputConstants.Type.KEYSYM,
    GLFW.GLFW_KEY_F9,
    KEY_CATEGORY
  );
}
