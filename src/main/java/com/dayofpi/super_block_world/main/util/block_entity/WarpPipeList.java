package com.dayofpi.super_block_world.main.util.block_entity;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WarpPipeList {
    public final List<BlockPos> warpList;
    final int chunkX, chunkZ;

    public WarpPipeList(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        warpList = new ArrayList<>();
    }

    public int getX() { return chunkX;}
    public int getZ() { return chunkZ;}

    public boolean isPipeInList(BlockPos pipe) {
        return warpList.contains(pipe);
    }

    public void add(BlockPos warp) {
        if (!isPipeInList(warp))
            warpList.add(warp);
    }

    public void remove(BlockPos warp) {
        warpList.remove(warp);
    }

    @Nullable
    public BlockPos findNearestBlock(BlockPos blockPos, World world) {
        BlockPos nearest = null;
        //loop through warps to find the nearest warp to blockPos
        for(BlockPos potential : warpList) {
            //Ignore if same block
            if (potential.getX() == blockPos.getX() && potential.getY() == blockPos.getY() && potential.getZ() == blockPos.getZ())
                continue;

            //Ignore if no air above
            if (!world.getBlockState(potential.up()).isAir())
                continue;

            //Ignore if not same color
            if (!world.getBlockState(potential).isOf(world.getBlockState(blockPos).getBlock()))
                continue;

            //Immediately accept if no pipe found
            if (nearest == null) {
                nearest = potential;
                continue;
            }

            //In manhattan distance is smaller, set as new pipe. Manhattan distance is only addition by block count.
            int manhattanA, manhattanB;
            manhattanA = potential.getManhattanDistance(blockPos);
            manhattanB = nearest.getManhattanDistance(blockPos);
            if (manhattanA < manhattanB)
                nearest = potential;
            else if (manhattanA == manhattanB) {
                Random random = new Random();
                nearest = random.nextBoolean() ? nearest : potential;
            }
        }
        return nearest;
    }
}