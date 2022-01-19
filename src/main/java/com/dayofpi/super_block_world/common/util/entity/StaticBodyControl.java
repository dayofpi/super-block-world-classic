package com.dayofpi.super_block_world.common.util.entity;

import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.mob.MobEntity;

public class StaticBodyControl extends BodyControl {
    public StaticBodyControl(MobEntity entity) {
        super(entity);
    }

    @Override
    public void tick() {
    }
}
