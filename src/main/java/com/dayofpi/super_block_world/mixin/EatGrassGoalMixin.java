package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(EatGrassGoal.class)
public abstract class EatGrassGoalMixin extends Goal {
    @Shadow @Final private MobEntity mob;

    @Shadow @Final private World world;

    @Shadow @Final private static Predicate<BlockState> GRASS_PREDICATE;

    @Inject(at=@At("TAIL"), method = "canStart", cancellable = true)
    public void canStart(CallbackInfoReturnable<Boolean> cir) {
        BlockPos blockPos = this.mob.getBlockPos();
        if (this.world.getBlockState(blockPos.down()).isOf(ModBlocks.TOADSTOOL_GRASS))
            cir.setReturnValue(true);
    }

    @Inject(at=@At("TAIL"), method = "tick")
    public void tick(CallbackInfo ci) {
        BlockPos blockPos = this.mob.getBlockPos();
        if (GRASS_PREDICATE.test(this.world.getBlockState(blockPos))) {
            if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                this.world.breakBlock(blockPos, false);
            }
            this.mob.onEatingGrass();
        } else {
            BlockPos blockPos2 = blockPos.down();
             if (this.world.getBlockState(blockPos2).isOf(ModBlocks.TOADSTOOL_GRASS)) {
                if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                    this.world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, blockPos2, Block.getRawIdFromState(ModBlocks.TOADSTOOL_GRASS.getDefaultState()));
                    this.world.setBlockState(blockPos2, ModBlocks.TOADSTOOL_SOIL.getDefaultState(), Block.NOTIFY_LISTENERS);
                }
                this.mob.onEatingGrass();
            }
        }
    }
}
