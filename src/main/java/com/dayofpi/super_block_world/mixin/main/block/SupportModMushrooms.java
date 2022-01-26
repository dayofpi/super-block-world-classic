package com.dayofpi.super_block_world.mixin.main.block;

import com.dayofpi.super_block_world.registry.block.MushroomBlocks;
import com.dayofpi.super_block_world.world.feature.configured.ConfiguredTrees;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MushroomPlantBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(MushroomPlantBlock.class)
public class SupportModMushrooms extends PlantBlock {
    public SupportModMushrooms(Settings settings) {
        super(settings);
    }

    @Inject(at = @At("HEAD"), method = ("trySpawningBigMushroom"), cancellable = true)
    private void trySpawningBigMushroom(ServerWorld world, BlockPos pos, BlockState state, Random random, CallbackInfoReturnable<Boolean> info) {
        world.removeBlock(pos, false);
        ConfiguredFeature<?, ?> mushroomFeature;
        if (this == Blocks.BROWN_MUSHROOM) {
            mushroomFeature = ConfiguredTrees.HUGE_BROWN_MUSHROOM;
        } else if (this == MushroomBlocks.PINK_MUSHROOM) {
            mushroomFeature = ConfiguredTrees.HUGE_PINK_MUSHROOM;
        } else if (this == MushroomBlocks.PURPLE_MUSHROOM) {
            mushroomFeature = ConfiguredTrees.HUGE_PURPLE_MUSHROOM;
        } else if (this == MushroomBlocks.ORANGE_MUSHROOM) {
            mushroomFeature = ConfiguredTrees.HUGE_ORANGE_MUSHROOM;
        }else if (this == MushroomBlocks.YELLOW_MUSHROOM) {
            mushroomFeature = ConfiguredTrees.HUGE_YELLOW_MUSHROOM;
        } else if (this == MushroomBlocks.GREEN_MUSHROOM) {
            mushroomFeature = ConfiguredTrees.HUGE_GREEN_MUSHROOM;
        } else {
            if (this != Blocks.RED_MUSHROOM) {
                world.setBlockState(pos, state, 3);
                info.setReturnValue(false);
            }
            mushroomFeature = ConfiguredTrees.HUGE_RED_MUSHROOM;
        }

        if (mushroomFeature.generate(world, world.getChunkManager().getChunkGenerator(), random, pos)) {
            info.setReturnValue(true);
        } else {
            world.setBlockState(pos, state, 3);
            info.setReturnValue(false);
        }
        info.cancel();
    }
}
