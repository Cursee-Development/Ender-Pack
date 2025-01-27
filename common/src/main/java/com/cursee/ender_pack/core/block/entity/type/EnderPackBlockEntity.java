package com.cursee.ender_pack.core.block.entity.type;

import com.cursee.ender_pack.core.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EnderPackBlockEntity extends BlockEntity {

    public EnderPackBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ENDER_PACK, pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, EnderPackBlockEntity enderPack) {
        if (!(level.random.nextFloat() <= 0.00001f)) return;
        level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.ENDER_PEARL)));
    }
}
