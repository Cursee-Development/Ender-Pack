package com.cursee.ender_pack.core.registry;

import com.cursee.ender_pack.EnderPack;
import com.cursee.ender_pack.core.block.entity.type.EnderPackBlockEntity;
import com.cursee.ender_pack.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.BiConsumer;

public class ModBlockEntities {

    public static final BlockEntityType<EnderPackBlockEntity> ENDER_PACK = Services.PLATFORM.createBlockEntityType(EnderPackBlockEntity::new, ModBlocks.ENDER_PACK);

    public static void register(BiConsumer<BlockEntityType<?>, ResourceLocation> consumer) {
        consumer.accept(ENDER_PACK, EnderPack.identifier("ender_pack"));
    }
}
