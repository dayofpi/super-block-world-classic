package com.dayofpi.super_block_world.mixin.main.block;

import com.dayofpi.super_block_world.registry.main.BlockInit;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CropBlock.class)
public class AllowCrop extends PlantBlock {
    protected AllowCrop(Settings settings) {
        super(settings);
    }

    @Inject(at = @At("HEAD"), method = ("getAvailableMoisture"), cancellable = true)
    private static void getAvailableMoisture(Block block, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> info) {
        float f = 1.0F;
        BlockPos blockPos = pos.down();

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float g = 0.0F;
                BlockState blockState = world.getBlockState(blockPos.add(i, 0, j));
                if (blockState.isOf(Blocks.FARMLAND) || blockState.isOf(BlockInit.TOADSTOOL_FARMLAND)) {
                    g = 1.0F;
                    if (blockState.get(FarmlandBlock.MOISTURE) > 0) {
                        g = 3.0F;
                    }
                }

                if (i != 0 || j != 0) {
                    g /= 4.0F;
                }

                f += g;
            }
        }

        BlockPos blockPos2 = pos.north();
        BlockPos blockPos3 = pos.south();
        BlockPos blockPos4 = pos.west();
        BlockPos blockPos5 = pos.east();
        boolean bl = world.getBlockState(blockPos4).isOf(block) || world.getBlockState(blockPos5).isOf(block);
        boolean bl2 = world.getBlockState(blockPos2).isOf(block) || world.getBlockState(blockPos3).isOf(block);
        if (bl && bl2) {
            f /= 2.0F;
        } else {
            boolean bl3 = world.getBlockState(blockPos4.north()).isOf(block) || world.getBlockState(blockPos5.north()).isOf(block) || world.getBlockState(blockPos5.south()).isOf(block) || world.getBlockState(blockPos4.south()).isOf(block);
            if (bl3) {
                f /= 2.0F;
            }
        }
        info.setReturnValue(f);
        info.cancel();
    }

    @Inject(at = @At("HEAD"), method = ("canPlantOnTop"), cancellable = true)
    private void canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(floor.isOf(Blocks.FARMLAND) || floor.isOf(BlockInit.TOADSTOOL_FARMLAND));
        info.cancel();
    }
}
