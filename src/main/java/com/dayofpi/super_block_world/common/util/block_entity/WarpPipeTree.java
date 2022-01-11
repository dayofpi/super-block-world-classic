package com.dayofpi.super_block_world.common.util.block_entity;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class WarpPipeTree {
    //To save memory and time, we store a list of all chunks that contain warp pipes. Within those chunks, we can access all warp pipes contained
    final Map<Long, WarpPipeList> chunks;
    BlockPos nearestCandidate;

    public WarpPipeTree()
    {
        chunks = new HashMap<>();
    }

    //Get the chunk id from its x and z positions
    long chunkToLong(int chunkX, int chunkZ) {
        //The key to the chunk map is the chunk's X and Z position merged into a 64 bit long from two 32-bit integers.
        return ((long)chunkX << 32) | chunkZ;
    }

    //Check if we currently have a chunk with that id stored
    boolean chunkExists(int chunkX, int chunkZ) {
        return chunks.containsKey(chunkToLong(chunkX, chunkZ));
    }

    //Get the list stored in the chunk's position. If this chunk doesn't exist, we create an entry for it
    public WarpPipeList getChunk(int chunkX, int chunkZ) {
        if (!chunkExists(chunkX, chunkZ)) {
            chunks.put(chunkToLong(chunkX, chunkZ), new WarpPipeList(chunkX, chunkZ));
        }
        return chunks.get(chunkToLong(chunkX, chunkZ));
    }

    //We add a warp pipe's position to the list at a given chunk
    public void addBlockToChunk(int chunkX, int chunkZ, BlockPos pos) {
        getChunk(chunkX, chunkZ).add(pos);
    }

    //We remove a warp pipe's position to the list at a given chunk
    public void removeBlockFromChunk(int chunkX, int chunkZ, BlockPos pos) {
        getChunk(chunkX, chunkZ).remove(pos);
    }

    //Gets the nearest warp pipe from a given position.
    @Nullable
    public BlockPos getNearestBlock(BlockPos pos, World world) {
        int chunkX = pos.getX() / 16;
        int chunkZ = pos.getZ() / 16;

        this.setNearestCandidate(null);

        //Check center chunk and directly adjacent chunks for the nearest warp
        if (chunkExists(chunkX, chunkZ)) {
            this.setNearestCandidate(getChunk(chunkX, chunkZ).findNearestBlock(pos, world));
        }

        //Check adjacent chunks
        if (chunkExists(chunkX-1, chunkZ))
            this.checkChunk(chunkX-1, chunkZ, pos, world);
        if (chunkExists(chunkX+1, chunkZ))
            this.checkChunk(chunkX+1, chunkZ, pos, world);
        if (chunkExists(chunkX, chunkZ-1))
            this.checkChunk(chunkX, chunkZ-1, pos, world);
        if (chunkExists(chunkX, chunkZ+1))
            this.checkChunk(chunkX, chunkZ+1, pos, world);
        if (chunkExists(chunkX-2, chunkZ))
            this.checkChunk(chunkX-2, chunkZ, pos, world);
        if (chunkExists(chunkX+2, chunkZ))
            this.checkChunk(chunkX+2, chunkZ, pos, world);
        if (chunkExists(chunkX, chunkZ-2))
            this.checkChunk(chunkX, chunkZ-2, pos, world);
        if (chunkExists(chunkX, chunkZ+2))
            this.checkChunk(chunkX, chunkZ+2, pos, world);

        //If we found the nearest candidate, make sure it's within the range
        if (this.getNearestCandidate() != null) {
            BlockPos dPos = pos.subtract(nearestCandidate);
            if (Math.abs(dPos.getX()) >= 80)
                this.setNearestCandidate(null);
            else if (Math.abs(dPos.getZ()) >= 80)
                this.setNearestCandidate(null);
            else if (Math.abs(dPos.getY()) >= 96)
                this.setNearestCandidate(null);
        }
        return this.getNearestCandidate();
    }

    @Nullable
    private BlockPos getNearestCandidate() {
        return this.nearestCandidate;
    }

    private void setNearestCandidate(BlockPos blockPos) {
        this.nearestCandidate = blockPos;
    }

    private void checkChunk(int chunkX, int chunkZ, BlockPos pos, World world) {
        BlockPos closestInChunk = getChunk(chunkX, chunkZ).findNearestBlock(pos, world);
        if (closestInChunk != null) {
            if (this.getNearestCandidate() == null) {
                this.setNearestCandidate(closestInChunk);
            } else if (closestInChunk.getManhattanDistance(pos) < this.getNearestCandidate().getManhattanDistance(pos))
                this.setNearestCandidate(closestInChunk);
        }
    }

}