package com.cursee.ender_pack.platform;

import com.cursee.ender_pack.core.registry.ModItems;
import com.cursee.ender_pack.platform.services.IPlatformHelper;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public String getGameDirectory() {

        return FabricLoader.getInstance().getGameDir().toString();
    }

    @Override
    public <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(BiFunction<BlockPos, BlockState, T> func, Block... blocks) {

        return FabricBlockEntityTypeBuilder.create(func::apply, blocks).build();
    }

    @Override
    public boolean checkCompatibleSlots(Player player) {

        final AtomicBoolean FOUND = new AtomicBoolean(false);

        TrinketsApi.getTrinketComponent(player).ifPresent(component -> {
            if (component.isEquipped(ModItems.ENDER_PACK)) FOUND.set(true);
        });

        return FOUND.get();
    }
}
