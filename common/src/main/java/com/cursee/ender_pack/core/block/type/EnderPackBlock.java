package com.cursee.ender_pack.core.block.type;

import com.cursee.ender_pack.core.block.IRotatable;
import com.cursee.ender_pack.core.block.entity.type.EnderPackBlockEntity;
import com.cursee.ender_pack.core.registry.ModBlockEntities;
import com.cursee.ender_pack.core.registry.ModItems;
import com.cursee.ender_pack.platform.Services;
import com.mojang.serialization.MapCodec;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnderPackBlock extends BaseEntityBlock implements IRotatable {

    public static final MapCodec<EnderPackBlock> CODEC = simpleCodec(EnderPackBlock::new);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public EnderPackBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EnderPackBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null : createTickerHelper(type, ModBlockEntities.ENDER_PACK, EnderPackBlockEntity::tick);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE.get(blockState.getValue(FACING));
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(ModItems.ENDER_PACK);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

        if (player.isShiftKeyDown()) {
            level.destroyBlock(pos, false);
            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.ENDER_PACK)));
            return InteractionResult.PASS;
        }

        player.openMenu(new SimpleMenuProvider((i, inventory, playerX) -> ChestMenu.threeRows(i, inventory, player.getEnderChestInventory()), Component.translatable("container.enderPack")));

        return InteractionResult.SUCCESS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {


        if (player.isShiftKeyDown()) {
            level.destroyBlock(pos, false);
            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.ENDER_PACK)));
            return ItemInteractionResult.FAIL;
        }

        player.openMenu(new SimpleMenuProvider((i, inventory, playerX) -> ChestMenu.threeRows(i, inventory, player.getEnderChestInventory()), Component.translatable("container.enderPack")));

        return ItemInteractionResult.FAIL;
    }

    /** @see net.minecraft.world.level.block.EnderChestBlock */
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        for(int particleIndex = 0; particleIndex < 3; ++particleIndex) {

            int xModifier = random.nextInt(2) * 2 - 1;
            int zModifier = random.nextInt(2) * 2 - 1;

            double posX = (double)pos.getX() + (double)0.5F + (double)0.25F * (double)xModifier;
            double posY = (double)((float)pos.getY() + random.nextFloat());
            double posZ = (double)pos.getZ() + (double)0.5F + (double)0.25F * (double)zModifier;

            double speedX = (double)(random.nextFloat() * (float)xModifier);
            double speedY = ((double)random.nextFloat() - (double)0.5F) * (double)0.125F;
            double speedZ = (double)(random.nextFloat() * (float)zModifier);

            level.addParticle(ParticleTypes.PORTAL, posX, posY, posZ, speedX, speedY, speedZ);
        }
    }
}
