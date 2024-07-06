package com.cursee.ender_pack.core.item;

import com.cursee.ender_pack.core.EnderPackFabricRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class EnderPackFabricItem extends Item implements Equipable {

    private final Holder<ArmorMaterial> material;
    private final ArmorItem.Type type;

    public EnderPackFabricItem(Holder<ArmorMaterial> material, ArmorItem.Type type, Properties properties) {
        super(properties);
        this.material = material;
        this.type = type;
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return this.type.getSlot();
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        player.openMenu(new SimpleMenuProvider((containerID, inventory, currentPlayer) ->
                ChestMenu.threeRows(containerID, inventory, player.getEnderChestInventory()),
                Component.translatable("container.ender_pack")));

        return super.use(level, player, interactionHand);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext) {

        Level level = useOnContext.getLevel();
        Player player = useOnContext.getPlayer();
        BlockPos clickedPos = useOnContext.getClickedPos();
        clickedPos = clickedPos.relative(useOnContext.getClickedFace());
        BlockState defaultState = EnderPackFabricRegistry.ENDER_PACK_BLOCK.defaultBlockState();

        if (player != null) {
            if (player.isCrouching() && level.isEmptyBlock(clickedPos)) {
                if (level.setBlockAndUpdate(clickedPos, defaultState.setValue(BlockStateProperties.HORIZONTAL_FACING, player.getDirection().getOpposite()))) {
                    player.playSound(SoundEvents.ENDER_CHEST_CLOSE, 0.5f, 0.5f);
                    level.gameEvent(player, GameEvent.BLOCK_PLACE, clickedPos);
                    useOnContext.getItemInHand().shrink(1);
                }
            }
            else {
                player.openMenu(new SimpleMenuProvider((containerID, inventory, currentPlayer) ->
                        ChestMenu.threeRows(containerID, inventory, player.getEnderChestInventory()),
                        Component.translatable("container.ender_pack")));
            }
        }

        return InteractionResult.sidedSuccess(useOnContext.getLevel().isClientSide());
    }
}
