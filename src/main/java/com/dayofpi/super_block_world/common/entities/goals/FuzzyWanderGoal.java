package com.dayofpi.super_block_world.common.entities.goals;

import com.dayofpi.super_block_world.common.entities.hostile.FuzzyEntity;
import com.dayofpi.super_block_world.registry.ModBlocks;
import com.dayofpi.super_block_world.registry.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class FuzzyWanderGoal extends Goal {
    private final FuzzyEntity fuzzy;

    public FuzzyWanderGoal(FuzzyEntity fuzzyEntity) {
        this.fuzzy = fuzzyEntity;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    public boolean canStart() {
        return fuzzy.getNavigation().isIdle();
    }

    public boolean shouldContinue() {
        return fuzzy.getNavigation().isFollowingPath();
    }

    public void start() {
        Vec3d randomLocation = this.getRandomLocation();
        List<PlayerEntity> players = fuzzy.world.getEntitiesByClass(PlayerEntity.class, fuzzy.getBoundingBox().expand(16), playerEntity -> playerEntity.isUsingItem() && playerEntity.getActiveItem().isOf(ModItems.FUZZY_MAGNET));
        List<Entity> fuzzies = fuzzy.world.getOtherEntities(fuzzy, fuzzy.getBoundingBox().expand(16), entity -> entity instanceof FuzzyEntity);
        fuzzy.setGlowing(false);
        if (!players.isEmpty()) {
            fuzzy.getNavigation().startMovingTo(players.get(0).getX(), players.get(0).getY() -1, players.get(0).getZ(), 1.4D);
            fuzzy.setGlowing(true);
        } else if (!fuzzies.isEmpty()) {
            fuzzy.getNavigation().startMovingTo(fuzzies.get(0), 1.2D);
        } else if (randomLocation != null && fuzzy.world.random.nextInt(6) == 0) {
            fuzzy.getNavigation().startMovingAlong(fuzzy.getNavigation().findPathTo(new BlockPos(randomLocation), 1), 0.4D);
        }

        if (players.isEmpty()) {
                for (BlockPos blockPos : BlockPos.iterateOutwards(fuzzy.getBlockPos(), 16, 16, 16)) {
                    BlockState state = fuzzy.world.getBlockState(blockPos);
                    if (state.isOf(ModBlocks.FUZZBALL) && state.get(Properties.POWERED)) {
                        fuzzy.getNavigation().startMovingTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1.2000000476837158D);
                    }
                }
        }
    }

    @Nullable
    private Vec3d getRandomLocation() {
        Vec3d randomLocation = AboveGroundTargeting.find(fuzzy, 8, 7, fuzzy.getX(), fuzzy.getZ(), 1.5707964F, 3, 3);
        return randomLocation != null ? randomLocation : NoPenaltySolidTargeting.find(fuzzy, 8, 4, -2, fuzzy.getX(), fuzzy.getZ(), 1.5707963705062866D);
    }
}