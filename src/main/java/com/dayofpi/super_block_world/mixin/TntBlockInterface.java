package com.dayofpi.super_block_world.mixin;

import net.minecraft.block.TntBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TntBlock.class)
@SuppressWarnings("unused")
public interface TntBlockInterface {
    @Invoker("primeTnt")
    static void primeTnt(World world, BlockPos pos, @Nullable LivingEntity igniter) {
        throw new AssertionError();
    }
}
