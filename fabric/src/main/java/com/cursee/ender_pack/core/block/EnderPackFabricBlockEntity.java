package com.cursee.ender_pack.core.block;

import com.cursee.ender_pack.core.EnderPackFabricRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

public class EnderPackFabricBlockEntity extends BlockEntity implements BlockEntityTicker<EnderPackFabricBlockEntity> {

    public EnderPackFabricBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(EnderPackFabricRegistry.ENDER_PACK_BLOCK_ENTITY, blockPos, blockState);
    }

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState, EnderPackFabricBlockEntity blockEntity) {
        float random = level.random.nextFloat();

        if (random <= 0.00001f) {
            level.addFreshEntity(new ItemEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(Items.ENDER_PEARL)));
        }
    }
}
