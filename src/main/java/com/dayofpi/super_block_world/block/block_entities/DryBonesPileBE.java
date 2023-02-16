package com.dayofpi.super_block_world.block.block_entities;

import com.dayofpi.super_block_world.block.blocks.DryBonesPileBlock;
import com.dayofpi.super_block_world.entity.entities.hostile.DryBonesEntity;
import com.dayofpi.super_block_world.block.ModBlockEntities;
import com.dayofpi.super_block_world.entity.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DryBonesPileBE extends BlockEntity {
    private static int alarm;
    private static final int maxAlarm = 50;

    public DryBonesPileBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DRY_BONES_PILE, pos, state);
        alarm = 0;
    }

    public static void tick(World world, BlockPos pos, BlockState state, DryBonesPileBE be) {
        if (state.get(DryBonesPileBlock.ALIVE)) {
            if (alarm < maxAlarm)
                ++alarm;
            if (alarm == maxAlarm) {
                DryBonesEntity dryBonesEntity = ModEntities.DRY_BONES.create(world);
                if (dryBonesEntity != null && world != null) {
                    dryBonesEntity.refreshPositionAndAngles(pos, 0.0F, 0.0F);
                    world.spawnEntity(dryBonesEntity);
                    world.removeBlock(pos, false);
                    world.removeBlockEntity(pos);
                }
            }
        }
    }
}
