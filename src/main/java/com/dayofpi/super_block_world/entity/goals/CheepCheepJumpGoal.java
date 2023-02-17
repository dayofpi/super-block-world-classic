package com.dayofpi.super_block_world.entity.goals;

import com.dayofpi.super_block_world.entity.entities.hostile.CheepCheepEntity;
import net.minecraft.entity.ai.goal.DiveJumpingGoal;
import net.minecraft.entity.ai.goal.DolphinJumpGoal;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class CheepCheepJumpGoal extends DiveJumpingGoal {
    private static final int[] OFFSET_MULTIPLIERS = new int[]{0, 1, 4, 5, 6, 7};
    private final CheepCheepEntity cheepCheep;
    private final int chance;
    private boolean inWater;

    public CheepCheepJumpGoal(CheepCheepEntity cheepCheep, int chance) {
        this.cheepCheep = cheepCheep;
        this.chance = DolphinJumpGoal.toGoalTicks(chance);
    }

    @Override
    public boolean canStart() {
        if (this.cheepCheep.getRandom().nextInt(this.chance) != 0) {
            return false;
        }
        Direction direction = this.cheepCheep.getMovementDirection();
        int i = direction.getOffsetX();
        int j = direction.getOffsetZ();
        BlockPos blockPos = this.cheepCheep.getBlockPos();
        for (int k : OFFSET_MULTIPLIERS) {
            if (this.isWater(blockPos, i, j, k) && this.isAirAbove(blockPos, i, j, k)) continue;
            return false;
        }
        return true;
    }

    private boolean isWater(BlockPos pos, int offsetX, int offsetZ, int multiplier) {
        BlockPos blockPos = pos.add(offsetX * multiplier, 0, offsetZ * multiplier);
        return this.cheepCheep.world.getFluidState(blockPos).isIn(FluidTags.WATER) && !this.cheepCheep.world.getBlockState(blockPos).getMaterial().blocksMovement();
    }

    private boolean isAirAbove(BlockPos pos, int offsetX, int offsetZ, int multiplier) {
        return this.cheepCheep.world.getBlockState(pos.add(offsetX * multiplier, 1, offsetZ * multiplier)).isAir() && this.cheepCheep.world.getBlockState(pos.add(offsetX * multiplier, 2, offsetZ * multiplier)).isAir();
    }

    @Override
    public boolean shouldContinue() {
        double d = this.cheepCheep.getVelocity().y;
        return !(d * d < (double)0.03f && this.cheepCheep.getPitch() != 0.0f && Math.abs(this.cheepCheep.getPitch()) < 10.0f && this.cheepCheep.isTouchingWater() || this.cheepCheep.isOnGround());
    }

    @Override
    public boolean canStop() {
        return false;
    }

    @Override
    public void start() {
        Direction direction = this.cheepCheep.getMovementDirection();
        this.cheepCheep.setVelocity(this.cheepCheep.getVelocity().add((double)direction.getOffsetX() * 0.6, 0.7, (double)direction.getOffsetZ() * 0.6));
        this.cheepCheep.getNavigation().stop();
    }

    @Override
    public void stop() {
        this.cheepCheep.setPitch(0.0f);
    }

    @Override
    public void tick() {
        boolean bl = this.inWater;
        if (!bl) {
            FluidState fluidState = this.cheepCheep.world.getFluidState(this.cheepCheep.getBlockPos());
            this.inWater = fluidState.isIn(FluidTags.WATER);
        }
        if (this.inWater && !bl) {
            this.cheepCheep.playSound(SoundEvents.ENTITY_DOLPHIN_JUMP, 1.0f, 1.0f);
        }
        Vec3d vec3d = this.cheepCheep.getVelocity();
        if (vec3d.y * vec3d.y < (double)0.03f && this.cheepCheep.getPitch() != 0.0f) {
            this.cheepCheep.setPitch(MathHelper.lerpAngle(this.cheepCheep.getPitch(), 0.0f, 0.2f));
        } else if (vec3d.length() > (double)1.0E-5f) {
            double d = vec3d.horizontalLength();
            double e = Math.atan2(-vec3d.y, d) * 57.2957763671875;
            this.cheepCheep.setPitch((float)e);
        }
    }
}
