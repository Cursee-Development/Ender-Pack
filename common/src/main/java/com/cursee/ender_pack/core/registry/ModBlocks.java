package com.cursee.ender_pack.core.registry;

import com.cursee.ender_pack.EnderPack;
import com.cursee.ender_pack.core.block.type.EnderPackBlock;
import com.cursee.ender_pack.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.BiConsumer;

public class ModBlocks {

    public static final Block ENDER_PACK = new EnderPackBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).lightLevel(blockState -> 7).strength(0.8F).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY));

    public static void register(BiConsumer<Block, ResourceLocation> consumer) {
        consumer.accept(ENDER_PACK, EnderPack.identifier("ender_pack"));
    }
}
