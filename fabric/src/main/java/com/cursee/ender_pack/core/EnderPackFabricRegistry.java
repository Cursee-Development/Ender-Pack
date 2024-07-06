package com.cursee.ender_pack.core;

import com.cursee.ender_pack.Constants;
import com.cursee.ender_pack.core.block.EnderPackFabricBlock;
import com.cursee.ender_pack.core.block.EnderPackFabricBlockEntity;
import com.cursee.ender_pack.core.item.EnderPackFabricItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class EnderPackFabricRegistry {

    /**
     * Pulls all statically registered objects into global scope via class access
     */
    public static void register() {}

    private static Block registerBlock(Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, Constants.MOD_ID), block);
    }

    private static Item registerItem(Item item) {
        return Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, Constants.MOD_ID), item);
    }

    private static CreativeModeTab registerCreativeModeTab(CreativeModeTab tab) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, Constants.MOD_ID), tab);
    }

    /* End method definitions, begin static declarations */

    public static final Block ENDER_PACK_BLOCK = registerBlock(new EnderPackFabricBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.BASS).strength(0.5f).lightLevel(level -> 7)));
    public static final BlockEntityType<EnderPackFabricBlockEntity> ENDER_PACK_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, Constants.MOD_ID), BlockEntityType.Builder.of(EnderPackFabricBlockEntity::new, ENDER_PACK_BLOCK).build(null));;

    public static final Item ENDER_PACK = registerItem(new EnderPackFabricItem(ArmorMaterials.LEATHER, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final CreativeModeTab ENDERPACK_TAB = registerCreativeModeTab(FabricItemGroup.builder() .title(Component.translatable("itemGroup.enderPack")) .icon(() -> new ItemStack(ENDER_PACK)) .displayItems((context, entries) -> entries.accept(ENDER_PACK)) .build());
}
