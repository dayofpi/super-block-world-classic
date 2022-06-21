package com.dayofpi.super_block_world.common.entities.goals;

import com.dayofpi.super_block_world.common.entities.mob.ToadEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class OpenDoorsGoal extends Goal {
    private final ToadEntity toadEntity;

    public OpenDoorsGoal(ToadEntity toadEntity) {
        this.toadEntity = toadEntity;
        this.setControls(EnumSet.of(Control.LOOK));
    }

    @Override
    public boolean canStart() {
        Path path = toadEntity.getNavigation().getCurrentPath();
        if (path != null)
            return !path.isStart() && !path.isFinished();
        else return false;
    }

    @Override
    public void tick() {
        super.tick();
        for (BlockPos blockPos : BlockPos.iterateOutwards(toadEntity.getBlockPos().offset(toadEntity.getMovementDirection(), 2), 2, 1, 2)) {
            BlockState state = toadEntity.world.getBlockState(blockPos);
            if (state.isIn(BlockTags.WOODEN_DOORS) && !state.get(Properties.OPEN)) {
                DoorBlock doorBlock = (DoorBlock) state.getBlock();
                doorBlock.setOpen(toadEntity, toadEntity.getWorld(), state, blockPos, !state.get(Properties.OPEN));
            }
        }
        for (BlockPos blockPos : BlockPos.iterateOutwards(toadEntity.getBlockPos().offset(toadEntity.getMovementDirection().getOpposite(), 3), 2, 1, 2)) {
            BlockState state = toadEntity.world.getBlockState(blockPos);
            if (state.isIn(BlockTags.WOODEN_DOORS) && state.get(Properties.OPEN)) {
                DoorBlock doorBlock = (DoorBlock) state.getBlock();
                doorBlock.setOpen(toadEntity, toadEntity.getWorld(), state, blockPos, !state.get(Properties.OPEN));
            }
        }
    }
}
