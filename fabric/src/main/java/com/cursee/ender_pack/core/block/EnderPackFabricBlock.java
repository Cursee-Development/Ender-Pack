package com.cursee.ender_pack.core.block;

import com.cursee.ender_pack.core.EnderPackFabricRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class EnderPackFabricBlock extends BaseEntityBlock implements IFabricRotatable {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final MapCodec<EnderPackFabricBlock> CODEC = simpleCodec(EnderPackFabricBlock::new);

    public EnderPackFabricBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE.get(blockState.getValue(FACING));
    }

    @Override
    protected RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(EnderPackFabricRegistry.ENDER_PACK);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {

        if (player.isCrouching()) {

            level.destroyBlock(blockPos, false);

//            new ItemEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(EnderPackFabricRegistry.ENDER_PACK)); // todo: fix if not working
            level.addFreshEntity(new ItemEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(EnderPackFabricRegistry.ENDER_PACK)));

            return InteractionResult.SUCCESS;
        }
        else {

            player.openMenu(new SimpleMenuProvider((containerID, inventory, currentPlayer) ->
                    ChestMenu.threeRows(containerID, inventory, player.getEnderChestInventory()),
                    Component.translatable("container.ender_pack")));

            return InteractionResult.SUCCESS;
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {


        if (player.isCrouching()) {

            level.destroyBlock(blockPos, false);

//            new ItemEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(EnderPackFabricRegistry.ENDER_PACK)); // todo: fix if not working
            level.addFreshEntity(new ItemEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(EnderPackFabricRegistry.ENDER_PACK)));

            return ItemInteractionResult.SUCCESS;
        }
        else {

            player.openMenu(new SimpleMenuProvider((containerID, inventory, currentPlayer) ->
                    ChestMenu.threeRows(containerID, inventory, player.getEnderChestInventory()),
                    Component.translatable("container.ender_pack")));

            return ItemInteractionResult.SUCCESS;
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new EnderPackFabricBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, EnderPackFabricRegistry.ENDER_PACK_BLOCK_ENTITY, (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1, blockEntity));
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        for(int i = 0; i < 3; ++i) {
            int posMultiplier = randomSource.nextInt(2) * 2 - 1;
            int speedMultiplier = randomSource.nextInt(2) * 2 - 1;
            double xPos = (double)blockPos.getX() + 0.5 + 0.25 * (double)posMultiplier;
            double yPos = (double)((float)blockPos.getY() + randomSource.nextFloat());
            double zPos = (double)blockPos.getZ() + 0.5 + 0.25 * (double)speedMultiplier;
            double xSpeed = (double)(randomSource.nextFloat() * (float)posMultiplier);
            double ySpeed = ((double)randomSource.nextFloat() - 0.5) * 0.125;
            double zSpeed = (double)(randomSource.nextFloat() * (float)speedMultiplier);
            level.addParticle(ParticleTypes.PORTAL, xPos, yPos, zPos, xSpeed, ySpeed, zSpeed);
        }
    }
}
