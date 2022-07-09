package com.dayofpi.super_block_world.common.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.LightType;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class FreezeLavaFeature extends Feature<DefaultFeatureConfig> {
    public FreezeLavaFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        BlockPos.Mutable mutable2 = new BlockPos.Mutable();
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                int k = blockPos.getX() + i;
                int l = blockPos.getZ() + j;
                int m = blockPos.getY();
                mutable.set(k, m, l);
                if (!world.getBlockState(mutable).isAir())
                    continue;
                mutable2.set(mutable).move(Direction.DOWN, 1);
                if (this.canSetIce(world, mutable2, false)) {
                    world.setBlockState(mutable2, Blocks.PACKED_ICE.getDefaultState(), Block.NOTIFY_LISTENERS);
                }
                if (!this.canSetSnow(world, mutable)) continue;
                world.setBlockState(mutable, Blocks.SNOW.getDefaultState(), Block.NOTIFY_LISTENERS);
            }
        }
        return true;
    }

    public boolean canSetSnow(WorldView world, BlockPos pos) {
        return pos.getY() >= world.getBottomY() && pos.getY() < world.getTopY() && world.getLightLevel(LightType.BLOCK, pos) < 10 && world.getBlockState(pos).isAir() && Blocks.SNOW.getDefaultState().canPlaceAt(world, pos);
    }

    public boolean canSetIce(WorldView world, BlockPos pos, boolean doWaterCheck) {
        if (pos.getY() >= world.getBottomY() && pos.getY() < world.getTopY() && world.getLightLevel(LightType.BLOCK, pos) < 10) {
            BlockState blockState = world.getBlockState(pos);
            FluidState fluidState = world.getFluidState(pos);
            if (fluidState.getFluid() == Fluids.LAVA && blockState.getBlock() instanceof FluidBlock) {
                boolean bl;
                if (!doWaterCheck) {
                    return true;
                }
                bl = world.isWater(pos.west()) && world.isWater(pos.east()) && world.isWater(pos.north()) && world.isWater(pos.south());
                return !bl;
            }
        }
        return false;
    }
}
