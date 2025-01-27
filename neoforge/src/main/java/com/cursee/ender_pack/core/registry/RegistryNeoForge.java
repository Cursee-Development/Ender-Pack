package com.cursee.ender_pack.core.registry;

import com.cursee.ender_pack.Constants;
import com.cursee.ender_pack.EnderPackNeoForge;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class RegistryNeoForge {

    public static void register(IEventBus modEventBus) {
        bind(Registries.BLOCK, consumer -> {
            ModBlocks.register(consumer);
        });
        bindForItems(ModItems::register);
        bind(Registries.BLOCK_ENTITY_TYPE, ModBlockEntities::register);

        bind(Registries.CREATIVE_MODE_TAB, consumer -> {
            consumer.accept(CreativeModeTab.builder()
                            .icon(() -> new ItemStack(ModItems.ENDER_PACK))
                            .title(Component.translatable("itemGroup.enderPack"))
                            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                            .build(),
                    Constants.ENDER_PACK_TAB_KEY.location());
        });
        modEventBus.addListener((BuildCreativeModeTabContentsEvent entries) -> {
            if (entries.getTabKey() == Constants.ENDER_PACK_TAB_KEY) {
                CREATIVE_MODE_TAB_ITEMS.forEach(entries::accept);
            }
        });
    }

    private static <T> void bind(ResourceKey<Registry<T>> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        EnderPackNeoForge.EVENT_BUS.addListener((RegisterEvent event) -> {
            if (registry.equals(event.getRegistryKey())) {
                source.accept((t, rl) -> event.register(registry, rl, () -> t));
            }
        });
    }

    private static final Set<Item> CREATIVE_MODE_TAB_ITEMS = new LinkedHashSet<>();
    private static void bindForItems(Consumer<BiConsumer<Item, ResourceLocation>> source) {
        EnderPackNeoForge.EVENT_BUS.addListener((RegisterEvent event) -> {
            if (event.getRegistryKey().equals(Registries.ITEM)) {
                source.accept((t, rl) -> {
                    CREATIVE_MODE_TAB_ITEMS.add(t);
                    event.register(Registries.ITEM, rl, () -> t);
                });
            }
        });
    }
}
