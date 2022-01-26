package com.dayofpi.super_block_world.mixin.main.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DeadBushBlock;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DeadBushBlock.class)
public class AllowDeadBush {
    @Inject(at = @At("HEAD"), method = ("canPlantOnTop"), cancellable = true)
    private void canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(floor.isIn(BlockTags.SAND) || floor.isOf(Blocks.TERRACOTTA) || floor.isOf(Blocks.WHITE_TERRACOTTA) || floor.isOf(Blocks.ORANGE_TERRACOTTA) || floor.isOf(Blocks.MAGENTA_TERRACOTTA) || floor.isOf(Blocks.LIGHT_BLUE_TERRACOTTA) || floor.isOf(Blocks.YELLOW_TERRACOTTA) || floor.isOf(Blocks.LIME_TERRACOTTA) || floor.isOf(Blocks.PINK_TERRACOTTA) || floor.isOf(Blocks.GRAY_TERRACOTTA) || floor.isOf(Blocks.LIGHT_GRAY_TERRACOTTA) || floor.isOf(Blocks.CYAN_TERRACOTTA) || floor.isOf(Blocks.PURPLE_TERRACOTTA) || floor.isOf(Blocks.BLUE_TERRACOTTA) || floor.isOf(Blocks.BROWN_TERRACOTTA) || floor.isOf(Blocks.GREEN_TERRACOTTA) || floor.isOf(Blocks.RED_TERRACOTTA) || floor.isOf(Blocks.BLACK_TERRACOTTA) || floor.isIn(BlockTags.DIRT));
        info.cancel();
    }
}