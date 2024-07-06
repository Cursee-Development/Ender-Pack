package com.cursee.ender_pack.core;

import com.cursee.ender_pack.Constants;
import com.cursee.ender_pack.core.block.EnderPackNeoForgeBlock;
import com.cursee.ender_pack.core.block.EnderPackNeoForgeBlockEntity;
import com.cursee.ender_pack.core.item.EnderPackNeoForgeItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EnderPackNeoForgeRegistry {
	
	public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(BuiltInRegistries.BLOCK, Constants.MOD_ID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);
	public static final DeferredRegister<Item> ITEM = DeferredRegister.create(BuiltInRegistries.ITEM, Constants.MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Constants.MOD_ID);
	
	
	public static final DeferredHolder<Item, Item> ENDERPACK = ITEM.register("ender_pack", () -> new EnderPackNeoForgeItem(ArmorMaterials.LEATHER, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	
	public static final DeferredHolder<Block, Block> ENDERPACK_BLOCK =
		BLOCK.register("ender_pack", () ->
			new EnderPackNeoForgeBlock(BlockBehaviour.Properties.of()
				.mapColor(MapColor.STONE)
				.instrument(NoteBlockInstrument.BASEDRUM)
				.strength(0.8F)
				.lightLevel((p_50854_) -> {return 7;})
			));
	
	public static final DeferredHolder<BlockEntityType<? extends BlockEntity>, BlockEntityType<EnderPackNeoForgeBlockEntity>> ENDER_PACK_BLOCK_ENTITY =
		BLOCK_ENTITY_TYPES.register("ender_pack_block_entity", () ->  BlockEntityType.Builder.of(EnderPackNeoForgeBlockEntity::new, ENDERPACK_BLOCK.get()).build(null));
	
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ENDERPACK_TAB = CREATIVE_MODE_TAB.register("ender_pack_tab", () -> CreativeModeTab.builder()
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
