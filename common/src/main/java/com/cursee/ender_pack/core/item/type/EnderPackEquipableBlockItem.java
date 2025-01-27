package com.cursee.ender_pack.core.item.type;

import com.cursee.ender_pack.core.block.type.EnderPackBlock;
import com.cursee.ender_pack.core.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class EnderPackEquipableBlockItem extends BlockItem implements Equipable {

    public EnderPackEquipableBlockItem() {
        super(ModBlocks.ENDER_PACK, new Properties().stacksTo(1));
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.CHEST;
    }

    @Override
    public @NotNull SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }

//    @Override
//    public @NotNull InteractionResultHolder<ItemStack> swapWithEquipmentSlot(Item item, Level level, Player player, InteractionHand hand) {
//        return InteractionResultHolder.fail(player.getItemInHand(hand));
//    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.openMenu(new SimpleMenuProvider((i, inventory, playerx) -> ChestMenu.threeRows(i, inventory, player.getEnderChestInventory()), Component.translatable("container.enderPack")));
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        InteractionResult SIDED_SUCCESS = InteractionResult.sidedSuccess(level.isClientSide());

        // return early if player is null
        Player player = context.getPlayer();
        if (player == null) return SIDED_SUCCESS;

        // open menu by default
        if (!player.isShiftKeyDown()) {
            player.openMenu(new SimpleMenuProvider((i, inventory, playerX) -> ChestMenu.threeRows(i, inventory, player.getEnderChestInventory()), Component.translatable("container.enderPack")));
            return SIDED_SUCCESS;
        }

        // return early if the relative position for placement is not empty
        BlockPos clickedPos = context.getClickedPos();
        BlockPos relativePos = clickedPos.relative(context.getClickedFace());
        if (!level.isEmptyBlock(relativePos)) return SIDED_SUCCESS;

        // if we are able to place a block: play the placement sound, fire block place event, deduct item from player
        if (level.setBlockAndUpdate(relativePos, ModBlocks.ENDER_PACK.defaultBlockState().setValue(EnderPackBlock.FACING, player.getDirection().getOpposite()))) {
            player.playSound(SoundEvents.ENDER_CHEST_CLOSE);
            level.gameEvent(player, GameEvent.BLOCK_PLACE, relativePos);
            context.getItemInHand().shrink(1);
        }

        return SIDED_SUCCESS;
    }
}
