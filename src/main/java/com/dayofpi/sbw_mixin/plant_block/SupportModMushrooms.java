package com.dayofpi.sbw_mixin.plant_block;

import com.dayofpi.sbw_main.block.registry.categories.MushroomBlocks;
import com.dayofpi.sbw_main.world.registry.ModConfiguredFeature;
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

    @Inject(at = @At("HEAD"), method = ("trySpawningBigMushroom(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Ljava/util/Random;)Z"), cancellable = true)
    private void trySpawningBigMushroom(ServerWorld world, BlockPos pos, BlockState state, Random random, CallbackInfoReturnable<Boolean> info) {
        //Your code goes here, replace any 'return true/return' false with:
        //info.setReturnValue(true or false);
        world.removeBlock(pos, false);
        ConfiguredFeature<?, ?> mushroomFeature;
        if (this == Blocks.BROWN_MUSHROOM) {
            mushroomFeature = ModConfiguredFeature.HUGE_BROWN_MUSHROOM;
        } else if (this == MushroomBlocks.PINK_MUSHROOM) {
            mushroomFeature = ModConfiguredFeature.HUGE_PINK_MUSHROOM;
        } else if (this == MushroomBlocks.PURPLE_MUSHROOM) {
            mushroomFeature = ModConfiguredFeature.HUGE_PURPLE_MUSHROOM;
        } else if (this == MushroomBlocks.ORANGE_MUSHROOM) {
            mushroomFeature = ModConfiguredFeature.HUGE_ORANGE_MUSHROOM;
        }else if (this == MushroomBlocks.YELLOW_MUSHROOM) {
            mushroomFeature = ModConfiguredFeature.HUGE_YELLOW_MUSHROOM;
        } else if (this == MushroomBlocks.GREEN_MUSHROOM) {
            mushroomFeature = ModConfiguredFeature.HUGE_GREEN_MUSHROOM;
        } else {
            if (this != Blocks.RED_MUSHROOM) {
                world.setBlockState(pos, state, 3);
                info.setReturnValue(false);
            }
            mushroomFeature = ModConfiguredFeature.HUGE_RED_MUSHROOM;
        }

        if (mushroomFeature.generate(world, world.getChunkManager().getChunkGenerator(), random, pos)) {
            info.setReturnValue(true);
        } else {
            world.setBlockState(pos, state, 3);
            info.setReturnValue(false);
        }
        //make sure to add this to the end
        info.cancel();
    }
}
