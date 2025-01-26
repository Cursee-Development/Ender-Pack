package com.cursee.ender_pack;

import net.minecraft.resources.ResourceLocation;

public class EnderPack {

    public static void init() {}

    public static ResourceLocation identifier(String value) {
        return new ResourceLocation(Constants.MOD_ID, value);
    }
}