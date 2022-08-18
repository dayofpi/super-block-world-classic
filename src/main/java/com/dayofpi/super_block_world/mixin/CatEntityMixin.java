package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.util.PowerUp;
import com.dayofpi.super_block_world.common.entities.goals.FollowPowerGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CatEntity.class)
public abstract class CatEntityMixin extends PathAwareEntity {
    protected CatEntityMixin(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at=@At("HEAD"), method = "initGoals")
    protected void initGoals(CallbackInfo ci) {
        this.goalSelector.add(3, new FollowPowerGoal(this, 0.6, PowerUp.CAT));
    }
}
