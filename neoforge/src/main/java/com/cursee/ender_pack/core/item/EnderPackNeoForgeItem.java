package com.cursee.ender_pack.core.item;

import com.cursee.ender_pack.core.EnderPackNeoForgeRegistry;
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

public class EnderPackNeoForgeItem extends Item implements Equipable {

	protected final Holder<ArmorMaterial> material;
	protected final ArmorItem.Type type;


	public EnderPackNeoForgeItem(Holder<ArmorMaterial> pMaterial, ArmorItem.Type pType, Properties pProperties) {
		// super(pProperties.defaultDurability(pMaterial.getDurabilityForType(pType)));
		super(pProperties);
		this.material = pMaterial;
		this.type = pType;
	}
	
	@Override
	public EquipmentSlot getEquipmentSlot() {
		return this.type.getSlot();
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
		
		pPlayer.openMenu(new SimpleMenuProvider((p_53124_, p_53125_, p_53126_) -> {
			return ChestMenu.threeRows(p_53124_, p_53125_, pPlayer.getEnderChestInventory());
		}, Component.translatable("container.ender_pack")));
		
		return super.use(pLevel, pPlayer, pUsedHand);
	}
	
	@Override
	public InteractionResult useOn(UseOnContext pContext) {
		Player pPlayer = pContext.getPlayer();
		Level pLevel = pContext.getLevel();
		BlockPos pPos = pContext.getClickedPos();
		BlockState pState = EnderPackNeoForgeRegistry.ENDERPACK_BLOCK.get().defaultBlockState();
		BlockPos pPos1 = pPos.relative(pContext.getClickedFace());
		
//		if (!pPlayer.isCrouching()) {
//			if (pPlayer != null && pLevel.isEmptyBlock(pPos1) && pLevel.getBlockState(pPos) != GlobalFabricRegistry.ENDERPACK_BLOCK.defaultBlockState()) {
//				pLevel.setBlock(pPos1, pState.setValue(BlockStateProperties.HORIZONTAL_FACING, pPlayer.getDirection().getOpposite()), Block.UPDATE_ALL);
//				pLevel.gameEvent(pPlayer, GameEvent.BLOCK_PLACE, pPos1);
//				pContext.getItemInHand().shrink(1);
//			} else {
//				pPlayer.openMenu(new SimpleMenuProvider((p_53124_, p_53125_, p_53126_) -> {
//					return ChestMenu.threeRows(p_53124_, p_53125_, pPlayer.getEnderChestInventory());
//				}, Component.translatable("container.ender_pack")));
//			}
//		} else {
//			pPlayer.openMenu(new SimpleMenuProvider((p_53124_, p_53125_, p_53126_) -> {
//				return ChestMenu.threeRows(p_53124_, p_53125_, pPlayer.getEnderChestInventory());
//			}, Component.translatable("container.ender_pack")));
//		}
		
		// only place if crouching, otherwise we always open the menu
		
		if (pPlayer != null) {
			
			if (pPlayer.isShiftKeyDown() && pLevel.isEmptyBlock(pPos1)) {
				if (pLevel.setBlockAndUpdate(pPos1, pState.setValue(BlockStateProperties.HORIZONTAL_FACING, pPlayer.getDirection().getOpposite()))) {
					pPlayer.playSound(SoundEvents.ENDER_CHEST_CLOSE, 0.5f, 0.5f);
					pLevel.gameEvent(pPlayer, GameEvent.BLOCK_PLACE, pPos1);
					pContext.getItemInHand().shrink(1);
					
					return InteractionResult.sidedSuccess(pLevel.isClientSide());
				}
			}
			
			pPlayer.openMenu(new SimpleMenuProvider((p_53124_, p_53125_, p_53126_) -> {
				return ChestMenu.threeRows(p_53124_, p_53125_, pPlayer.getEnderChestInventory());
			}, Component.translatable("container.ender_pack")));
		}
		
		return InteractionResult.sidedSuccess(pLevel.isClientSide());
	}

	public Holder<ArmorMaterial> getMaterial() {
		return this.material;
	}

	public int getDefense() {
		return ((ArmorMaterial)this.material.value()).getDefense(this.type);
	}

	public float getToughness() {
		return ((ArmorMaterial)this.material.value()).toughness();
	}
	
}
