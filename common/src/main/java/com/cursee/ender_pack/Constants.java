package com.cursee.ender_pack;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.util.tuples.Pair;
import oshi.util.tuples.Triplet;

public class Constants {

	public static final String MOD_ID = "ender_pack";
	public static final String MOD_NAME = "EnderPack";
	public static final String MOD_VERSION = "2.1.0";
	public static final String MOD_PUBLISHER = "Lupin";
	public static final String MOD_URL = "https://www.curseforge.com/minecraft/mc-mods/enderpack";
	public static final ResourceKey<CreativeModeTab> ENDER_PACK_TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, EnderPack.identifier("ender_pack"));
	public static final String KEY_NAME = "key.ender_pack.toggle";
	public static final String KEY_CATEGORY = "key.category.ender_pack.access";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
}