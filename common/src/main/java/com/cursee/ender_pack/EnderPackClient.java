package com.cursee.ender_pack;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class EnderPackClient {

    public static final ResourceLocation ENDER_PACK_TEXTURE_LOCATION = EnderPack.identifier("textures/entity/wearable/ender_pack.png");
    public static final ModelLayerLocation ENDER_PACK_PLAYER_MODEL_LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("player"), "ender_pack");

    public static void init() {}
}
