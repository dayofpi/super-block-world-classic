package com.dayofpi.super_block_world.common.items;

import com.dayofpi.super_block_world.registry.ModItems;
import net.kyrptonaught.customportalapi.portal.PortalIgnitionSource;
import net.kyrptonaught.customportalapi.portal.PortalPlacer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PowerStarItem extends BlockItem {
    public PowerStarItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(ItemPlacementContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        if (PortalPlacer.attemptPortalLight(world, blockPos, PortalIgnitionSource.ItemUseSource(ModItems.POWER_STAR))) {
            return null;
        }
        if (PortalPlacer.attemptPortalLight(world, blockPos, PortalIgnitionSource.ItemUseSource(ModItems.ZTAR))) {
            return null;
        }
        return super.getPlacementState(context);
    }
}
