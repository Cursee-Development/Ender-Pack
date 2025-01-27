package com.cursee.ender_pack.core.registry;

import com.cursee.ender_pack.Constants;
import com.cursee.ender_pack.core.registry.ModBlocks;
import com.cursee.ender_pack.core.registry.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class RegistryFabric {

    public static void register() {
        ModBlocks.register(bind(BuiltInRegistries.BLOCK));
        ModItems.register(boundForItem);
        ModBlockEntities.register(bind(BuiltInRegistries.BLOCK_ENTITY_TYPE));

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Constants.ENDER_PACK_TAB_KEY, FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.ENDER_PACK)).title(Component.translatable("itemGroup.enderPack")).build());
        ItemGroupEvents.modifyEntriesEvent(Constants.ENDER_PACK_TAB_KEY).register(entries -> CREATIVE_MODE_TAB_ITEMS.forEach(entries::accept));
    }

    private static <T> BiConsumer<T, ResourceLocation> bind(Registry<? super T> registry) {
        return (t, id) -> Registry.register(registry, id, t);
    }

    private static final Set<Item> CREATIVE_MODE_TAB_ITEMS = new LinkedHashSet<>();
    private static final BiConsumer<Item, ResourceLocation> boundForItem = (t, id) -> {
        CREATIVE_MODE_TAB_ITEMS.add(t);
        Registry.register(BuiltInRegistries.ITEM, id, t);
    };
}
