package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.common.entities.PowerUp;
import com.dayofpi.super_block_world.common.entities.goals.FollowPowerGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeeEntity.class)
public abstract class BeeEntityMixin extends PathAwareEntity {
    protected BeeEntityMixin(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at=@At("HEAD"), method = "initGoals")
    protected void initGoals(CallbackInfo ci) {
        this.goalSelector.add(6, new FollowPowerGoal(this, 1.25, PowerUp.BEE));
    }
}
