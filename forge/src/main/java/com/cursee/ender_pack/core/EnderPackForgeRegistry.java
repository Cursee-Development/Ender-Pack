package com.cursee.ender_pack.core;

import com.cursee.ender_pack.Constants;
import com.cursee.ender_pack.core.block.EnderPackForgeBlock;
import com.cursee.ender_pack.core.block.EnderPackForgeBlockEntity;
import com.cursee.ender_pack.core.item.EnderPackForgeItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnderPackForgeRegistry {
	
	public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Constants.MOD_ID);
	public static final DeferredRegister<Item> ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Constants.MOD_ID);
	
	
	public static final RegistryObject<Item> ENDERPACK = ITEM.register("ender_pack", () -> new EnderPackForgeItem(ArmorMaterials.LEATHER, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	
	public static final RegistryObject<Block> ENDERPACK_BLOCK =
		BLOCK.register("ender_pack", () ->
			new EnderPackForgeBlock(BlockBehaviour.Properties.of()
				.mapColor(MapColor.STONE)
				.instrument(NoteBlockInstrument.BASEDRUM)
				.strength(0.8F)
				.lightLevel((p_50854_) -> {return 7;})
			));
	
	public static final RegistryObject<BlockEntityType<EnderPackForgeBlockEntity>> ENDER_PACK_BLOCK_ENTITY =
		BLOCK_ENTITY_TYPES.register("ender_pack_block_entity", () ->  BlockEntityType.Builder.of(EnderPackForgeBlockEntity::new, ENDERPACK_BLOCK.get()).build(null));
	
	public static final RegistryObject<CreativeModeTab> ENDERPACK_TAB = CREATIVE_MODE_TAB.register("ender_pack", () -> CreativeModeTab.builder()
		.withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
		.icon(() -> ENDERPACK.get().getDefaultInstance())
		.title(Component.translatable("itemGroup.enderPack"))
		.displayItems((parameters, output) -> {
			output.accept(ENDERPACK.get());
		}).build());
	
	public static void registerAll(IEventBus bus) {
		BLOCK.register(bus);
		BLOCK_ENTITY_TYPES.register(bus);
		ITEM.register(bus);
		CREATIVE_MODE_TAB.register(bus);
	}
}
