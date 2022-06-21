package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.registry.ModBlocks;
import com.dayofpi.super_block_world.registry.ModItems;
import net.minecraft.block.*;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.List;

@SuppressWarnings("deprecation")
public class SmileySunflowerBlock extends PlantBlock {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 13.0D, 15.0D);

    public SmileySunflowerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        boolean particles = false;
        List<ItemEntity> seedList = world.getEntitiesByClass(ItemEntity.class, Box.from(Vec3d.ofCenter(pos)).expand(7, 7, 7), entity -> entity.getStack().isOf(ModItems.SMILEY_SUNFLOWER_SEED));

        if (random.nextInt(8) == 0 && seedList.isEmpty())
            Block.dropStack(world, pos, new ItemStack(ModItems.SMILEY_SUNFLOWER_SEED));

        Iterable<BlockPos> range = BlockPos.iterateOutwards(pos, 7, 7, 7);
        for (BlockPos blockPos : range) {
            if (world.getBlockState(blockPos.up()).isAir()) {
                if (world.getBlockState(blockPos).isOf(ModBlocks.TOADSTOOL_SOIL)) {
                    world.setBlockState(blockPos, ModBlocks.TOADSTOOL_GRASS.getDefaultState());
                    particles = true;
                } else if (world.getBlockState(blockPos).isOf(ModBlocks.COARSE_TOADSTOOL_SOIL)) {
                    world.setBlockState(blockPos, ModBlocks.TOADSTOOL_SOIL.getDefaultState());
                    particles = true;
                } else if (world.getBlockState(blockPos).isOf(ModBlocks.CHERRY_SOIL)) {
                    world.setBlockState(blockPos, ModBlocks.CHERRY_GRASS.getDefaultState());
                    particles = true;
                } else if (world.getBlockState(blockPos).isOf(Blocks.DIRT)) {
                    world.setBlockState(blockPos, Blocks.GRASS_BLOCK.getDefaultState());
                    particles = true;
                }
            }
        }
        if (particles)
            world.syncWorldEvent(1505, pos, 0);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(ModBlocks.SMILEY_STEM) || floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND);
    }
}
