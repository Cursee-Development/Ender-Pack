package com.cursee.ender_pack.core.registry;

import com.cursee.ender_pack.EnderPack;
import com.cursee.ender_pack.core.item.type.EnderPackEquipableBlockItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.BiConsumer;

public class ModItems {

    public static final Item ENDER_PACK = new EnderPackEquipableBlockItem();

    public static void register(BiConsumer<Item, ResourceLocation> consumer) {
        consumer.accept(ENDER_PACK, EnderPack.identifier("ender_pack"));
    }
}
