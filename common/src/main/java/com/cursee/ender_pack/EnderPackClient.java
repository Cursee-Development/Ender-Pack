package com.cursee.ender_pack;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class EnderPackClient {

    public static final ModelLayerLocation ENDER_PACK_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("minecraft", "player"), "ender_pack");
    public static final ResourceLocation ENDER_PACK_TEXTURE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/entity/ender_pack.png");
}
