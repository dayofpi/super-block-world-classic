package com.dayofpi.super_block_world.item.items;

import com.dayofpi.super_block_world.item.ModItems;
import net.kyrptonaught.customportalapi.portal.PortalIgnitionSource;
import net.kyrptonaught.customportalapi.portal.PortalPlacer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
        PortalIgnitionSource source1 = PortalIgnitionSource.ItemUseSource(ModItems.POWER_STAR);
        PortalIgnitionSource source2 = PortalIgnitionSource.ItemUseSource(ModItems.ZTAR);
        PlayerEntity player = context.getPlayer();
        if (player == null)
            return null;
        if (this == ModItems.POWER_STAR) {
            if (PortalPlacer.attemptPortalLight(world, blockPos, source1)) {
                context.getStack().decrement(1);
                player.swingHand(context.getHand());
                world.playSound(null, blockPos, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return null;
            }
        }
        else {
            if (PortalPlacer.attemptPortalLight(world, blockPos, source2)) {
                context.getStack().decrement(1);
                player.swingHand(context.getHand());
                world.playSound(null, blockPos, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return null;
            }
        }
        return super.getPlacementState(context);
    }
}
