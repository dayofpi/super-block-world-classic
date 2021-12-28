package com.dayofpi.super_block_world.mixin.main.poison;

import com.dayofpi.super_block_world.main.registry.general.TagRegistry;
import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Queue;

@Mixin(SpongeBlock.class)
public class SpongePoisonBehavior extends Block {
    public SpongePoisonBehavior(Settings settings) {
        super(settings);
    }

    // Makes sure it can't absorb poison
    @Inject(at = @At("HEAD"), method = "absorbWater(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z", cancellable = true)
    private void absorbWater(World world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        Queue<Pair<BlockPos, Integer>> queue = Lists.newLinkedList();
        queue.add(new Pair<>(pos, 0));
        int i = 0;

        while (!queue.isEmpty()) {
            Pair<BlockPos, Integer> pair = queue.poll();
            BlockPos blockPos = pair.getLeft();
            int j = pair.getRight();
            Direction[] var8 = Direction.values();

            for (Direction direction : var8) {
                BlockPos blockPos2 = blockPos.offset(direction);
                BlockState blockState = world.getBlockState(blockPos2);
                FluidState fluidState = world.getFluidState(blockPos2);
                Material material = blockState.getMaterial();
                if (fluidState.isIn(FluidTags.WATER) && !fluidState.isIn(TagRegistry.POISON)) {
                    if (blockState.getBlock() instanceof FluidDrainable && !((FluidDrainable) blockState.getBlock()).tryDrainFluid(world, blockPos2, blockState).isEmpty()) {
                        ++i;
                        if (j < 6) {
                            queue.add(new Pair<>(blockPos2, j + 1));
                        }
                    } else if (blockState.getBlock() instanceof FluidBlock) {
                        world.setBlockState(blockPos2, Blocks.AIR.getDefaultState(), 3);
                        ++i;
                        if (j < 6) {
                            queue.add(new Pair<>(blockPos2, j + 1));
                        }
                    } else if (material == Material.UNDERWATER_PLANT || material == Material.REPLACEABLE_UNDERWATER_PLANT) {
                        BlockEntity blockEntity = blockState.hasBlockEntity() ? world.getBlockEntity(blockPos2) : null;
                        dropStacks(blockState, world, blockPos2, blockEntity);
                        world.setBlockState(blockPos2, Blocks.AIR.getDefaultState(), 3);
                        ++i;
                        if (j < 6) {
                            queue.add(new Pair<>(blockPos2, j + 1));
                        }
                    }
                }
            }

            if (i > 64) {
                break;
            }
        }
        info.setReturnValue(i > 0);
        info.cancel();
    }
}
