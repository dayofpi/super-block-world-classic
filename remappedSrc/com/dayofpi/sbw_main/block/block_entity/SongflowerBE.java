package com.dayofpi.sbw_main.block.block_entity;

import com.dayofpi.sbw_main.block.registry.ModBlockEntities;
import com.dayofpi.sbw_main.block.registry.categories.PlantBlocks;
import com.dayofpi.sbw_main.block.types.SongflowerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SongflowerBE extends BlockEntity {

    public SongflowerBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SONGFLOWER, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, SongflowerBE be) {
        if (state.isOf(PlantBlocks.BLUE_SONGFLOWER) && state.get(SongflowerBlock.DANCING) != world.isSkyVisible(pos) && be != null) {
            world.setBlockState(pos, state.with(SongflowerBlock.DANCING, world.isSkyVisible(pos)));
        }
    }

    // Serialize the BlockEntity
    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
    }

    // Deserialize the BlockEntity
    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
    }

}
