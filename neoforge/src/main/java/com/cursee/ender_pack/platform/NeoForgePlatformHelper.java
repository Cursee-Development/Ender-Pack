package com.cursee.ender_pack.platform;

import com.cursee.ender_pack.core.registry.ModItems;
import com.cursee.ender_pack.platform.services.IPlatformHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public String getGameDirectory() {

        return FMLPaths.GAMEDIR.get().toString();
    }

    @Override
    public <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(BiFunction<BlockPos, BlockState, T> func, Block... blocks) {

        return BlockEntityType.Builder.of(func::apply, blocks).build(null);
    }

    @Override
    public boolean checkCompatibleSlots(Player player) {

        final AtomicBoolean FOUND = new AtomicBoolean(false);

        CuriosApi.getCuriosInventory(player).ifPresent(component -> {
            if (component.isEquipped(ModItems.ENDER_PACK)) FOUND.set(true);
        });

        return FOUND.get();
    }
}