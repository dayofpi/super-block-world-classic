package com.dayofpi.sbw_main.common.block.type;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class IcicleBlock extends FallingBlock {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    public IcicleBlock(Settings settings) {
        super(settings);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(12) == 0) {
            BlockPos blockPos = pos.down();
            if (canFallThrough(world.getBlockState(blockPos))) {
                double d = pos.getX() + 0.5;
                double e = (double)pos.getY() - 0.05D;
                double f = pos.getZ() + 0.5;
                world.addParticle(ParticleTypes.DRIPPING_DRIPSTONE_WATER, d, e, f, 0.0D, 0.0D, 0.0D);
            }
        }

    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
    }

    protected void configureFallingBlockEntity(FallingBlockEntity entity) {
        entity.setHurtEntities(1.0F, 10);
    }

    @Override
    public void onLanding(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity) {
        world.breakBlock(pos, true);
    }
}
