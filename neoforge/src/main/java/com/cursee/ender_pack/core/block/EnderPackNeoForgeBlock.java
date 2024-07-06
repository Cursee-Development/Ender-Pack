package com.cursee.ender_pack.core.block;

import com.cursee.ender_pack.core.EnderPackNeoForgeRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class EnderPackNeoForgeBlock extends BaseEntityBlock {
  
  public static final MapCodec<EnderPackNeoForgeBlock> CODEC = EnderPackNeoForgeBlock.simpleCodec(EnderPackNeoForgeBlock::new);
  
  static final double ONE_SIXTEENTH = 0.0625d;
  
  // VoxelShape constants
  static final double startX = ONE_SIXTEENTH * 3;
  static final double startY = ONE_SIXTEENTH * 0;
  static final double startZ = ONE_SIXTEENTH * 4 + (ONE_SIXTEENTH/8);
  static final double endX = ONE_SIXTEENTH * 13;
  static final double endY = ONE_SIXTEENTH * 12 - (ONE_SIXTEENTH / 2);
  static final double endZ = ONE_SIXTEENTH * 10;
  
  public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
  // public static final VoxelShape SHAPE = Block.box(3, 0, 4, 13, 12, 10);
  
  public EnderPackNeoForgeBlock(Properties pProperties) {
    super(pProperties);
    this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
  }
  
  @Override
  protected MapCodec<? extends BaseEntityBlock> codec() {
    return CODEC;
  }
  
  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(FACING);
  }
  
  @Override
  public BlockState getStateForPlacement(BlockPlaceContext ctx) {
    return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
  }
  
  // @Override
  // public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
  //   return new ItemStack(EnderPackNeoForgeRegistry.ENDERPACK.get());
  // }
  
  @Override // 1.20.4
  public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
    return new ItemStack(EnderPackNeoForgeRegistry.ENDERPACK.get());
  }

  @Override
  protected InteractionResult useWithoutItem(BlockState blockState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult blockHitResult) {
    if (pPlayer.isCrouching()) {
      pLevel.destroyBlock(pPos, false);
      pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), new ItemStack(EnderPackNeoForgeRegistry.ENDERPACK.get())));
    } else {
      pPlayer.openMenu(new SimpleMenuProvider((p_53124_, p_53125_, p_53126_) -> {
        return ChestMenu.threeRows(p_53124_, p_53125_, pPlayer.getEnderChestInventory());
      }, Component.translatable("container.ender_pack")));
      return InteractionResult.SUCCESS;
    }

    return InteractionResult.PASS;
  }

  @Override
  protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand interactionHand, BlockHitResult blockHitResult) {
    if (pPlayer.isCrouching()) {
      pLevel.destroyBlock(pPos, false);
      pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), new ItemStack(EnderPackNeoForgeRegistry.ENDERPACK.get())));
    } else {
      pPlayer.openMenu(new SimpleMenuProvider((p_53124_, p_53125_, p_53126_) -> {
        return ChestMenu.threeRows(p_53124_, p_53125_, pPlayer.getEnderChestInventory());
      }, Component.translatable("container.ender_pack")));
      return ItemInteractionResult.SUCCESS;
    }

    return ItemInteractionResult.FAIL;
  }
  
  @Override
  public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
    for(int i = 0; i < 3; ++i) {
      int posMultiplier = pRandom.nextInt(2) * 2 - 1;
      int speedMultiplier = pRandom.nextInt(2) * 2 - 1;
      double xPos = (double)pPos.getX() + 0.5 + 0.25 * (double)posMultiplier;
      double yPos = (double)((float)pPos.getY() + pRandom.nextFloat());
      double zPos = (double)pPos.getZ() + 0.5 + 0.25 * (double)speedMultiplier;
      double xSpeed = (double)(pRandom.nextFloat() * (float)posMultiplier);
      double ySpeed = ((double)pRandom.nextFloat() - 0.5) * 0.125;
      double zSpeed = (double)(pRandom.nextFloat() * (float)speedMultiplier);
      pLevel.addParticle(ParticleTypes.PORTAL, xPos, yPos, zPos, xSpeed, ySpeed, zSpeed);
    }
    
  }
  
  public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
    VoxelShape[] buffer = new VoxelShape[] { shape, Shapes.empty() };
  
    int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;
    for (int i = 0; i < times; i++) {
      buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.joinUnoptimized(buffer[1],
        Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX),
        BooleanOp.OR
      ));
      buffer[0] = buffer[1];
      buffer[1] = Shapes.empty();
    }
    return buffer[0];
  }
  
  private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
    
    // double startX = 0.1875d;
    // double startY = 0;
    // double startZ = 0.1875;
    // double endX = 0.9375;
    // double endY = 0.9375;
    // double endZ = 0.8125;
    
    
    
    VoxelShape shape = Shapes.empty();
    shape = Shapes.joinUnoptimized(shape, Shapes.box(startX, startY, startZ, endX, endY, endZ), BooleanOp.OR);
    return shape;
  };
  
  public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
    for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
      map.put(direction, rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
    }
  });
  
  @Override
  public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
    return SHAPE.get(state.getValue(FACING));
  }
  
  // @Override
  // public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
  //   return SHAPE;
  // }
  
  @Override
  public RenderShape getRenderShape(BlockState pState) {
    return RenderShape.ENTITYBLOCK_ANIMATED;
  }
  
  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
    return new EnderPackNeoForgeBlockEntity(blockPos, blockState);
  }
  
  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
    return createTickerHelper(type, EnderPackNeoForgeRegistry.ENDER_PACK_BLOCK_ENTITY.get(), (world1, pos, state1, be) -> be.tick(world1, pos, state1, be));
  }
}
