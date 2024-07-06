package com.cursee.ender_pack.core.block;

import com.cursee.ender_pack.core.EnderPackForgeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

public class EnderPackForgeBlockEntity extends BlockEntity implements BlockEntityTicker<EnderPackForgeBlockEntity> {
  
  public EnderPackForgeBlockEntity(BlockPos pPos, BlockState pBlockState) {
    super(EnderPackForgeRegistry.ENDER_PACK_BLOCK_ENTITY.get(), pPos, pBlockState);
  }
  
  @Override
  public void tick(Level level, BlockPos blockPos, BlockState blockState, EnderPackForgeBlockEntity blockEntity) {
    
    float random = level.random.nextFloat();
    
    if (random <= 0.00001f) {
      // System.out.println(CommonConstants.MOD_NAME + " generated a free ender pearl at X: " + blockPos.getX() + " Y: " + blockPos.getY() + " Z: " + blockPos.getZ() + "!");
      // System.out.println(CommonConstants.MOD_NAME + " Float " + random  + " generated, less than 0.00001f!");
      level.addFreshEntity(new ItemEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(Items.ENDER_PEARL)));
    }
  }
}
