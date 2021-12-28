package com.dayofpi.super_block_world.main.util.block_entity;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class WarpPipeTree {
    //To save memory and time, we store a list of all chunks that contain warp pipes. Within those chunks, we can access all warp pipes contained
    final Map<Long, WarpPipeList> chunks;

    public WarpPipeTree()
    {
        chunks = new HashMap<>();
    }

    //Get the chunk id from its x and z positions
    long chunkToLong(int chunkX, int chunkZ)
    {
        //The key to the chunk map is the chunk's X and Z position merged into a 64 bit long from two 32-bit integers.
        return ((long)chunkX << 32) | chunkZ;
    }

    //Check if we currently have a chunk with that id stored
    boolean chunkExists(int chunkX, int chunkZ)
    {
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
        System.out.println("Added warp to chunk at Chunk X: " + chunkX + ", Chunk Z: " + chunkZ + ", " + pos);
    }

    //We remove a warp pipe's position to the list at a given chunk
    public void removeBlockFromChunk(int chunkX, int chunkZ, BlockPos pos) {
        getChunk(chunkX, chunkZ).remove(pos);
        System.out.println("Removed warp from chunk at Chunk X: " + chunkX + ", Chunk Z:" + chunkZ + ", " + pos);
    }

    //Gets the nearest warp pipe from a given position, taking looking direction into account if there must be a tie-breaker.
    @Nullable
    public BlockPos getNearestBlock(BlockPos pos, World world) {
        int chunkX = pos.getX() / 16;
        int chunkZ = pos.getZ() / 16;

        BlockPos nearestCandidate = null;

        //Check center chunk and directly adjacent chunks for the nearest warp pipe
        if (chunkExists(chunkX, chunkZ)) {
            nearestCandidate = getChunk(chunkX, chunkZ).findNearestBlock(pos, world);
        }
        //Check adjacent chunks
        if (chunkExists(chunkX-1, chunkZ)) {
            BlockPos t = getChunk(chunkX-1, chunkZ).findNearestBlock(pos, world);
            if (t != null)
            {
                if (nearestCandidate == null)
                {
                    nearestCandidate = t;
                }
                else if (t.getManhattanDistance(pos) < nearestCandidate.getManhattanDistance(pos))
                    nearestCandidate = t;
            }
        }
        if (chunkExists(chunkX+1, chunkZ)) {
            BlockPos t = getChunk(chunkX+1, chunkZ).findNearestBlock(pos, world);
            if (t != null)
            {
                if (nearestCandidate == null)
                {
                    nearestCandidate = t;
                }
                else if (t.getManhattanDistance(pos) < nearestCandidate.getManhattanDistance(pos))
                    nearestCandidate = t;
            }
        }
        if (chunkExists(chunkX, chunkZ-1)) {
            BlockPos t = getChunk(chunkX, chunkZ-1).findNearestBlock(pos, world);
            if (t != null)
            {
                if (nearestCandidate == null)
                {
                    nearestCandidate = t;
                }
                else if (t.getManhattanDistance(pos) < nearestCandidate.getManhattanDistance(pos))
                    nearestCandidate = t;
            }

        }
        if (chunkExists(chunkX, chunkZ+1)) {
            BlockPos t = getChunk(chunkX, chunkZ+1).findNearestBlock(pos, world);
            if (t != null)
            {
                if (nearestCandidate == null)
                {
                    nearestCandidate = t;
                }
                else if (t.getManhattanDistance(pos) < nearestCandidate.getManhattanDistance(pos))
                    nearestCandidate = t;
            }
        }

        //If we found the nearest candidate, make sure it's within the range, relative to our position
        if (nearestCandidate != null) {
            BlockPos dPos = pos.subtract(nearestCandidate);
            if (Math.abs(dPos.getX()) >= 80)
                nearestCandidate = null;
            else if (Math.abs(dPos.getZ()) >= 80)
                nearestCandidate = null;
            else if (Math.abs(dPos.getY()) >= 96)
                nearestCandidate = null;
        }
        return nearestCandidate;
    }

}