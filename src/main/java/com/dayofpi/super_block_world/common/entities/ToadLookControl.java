package com.dayofpi.super_block_world.common.entities;

import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;

public class ToadLookControl extends LookControl {
    private int waveTimer;
    public ToadLookControl(MobEntity entity) {
        super(entity);
        this.waveTimer = 0;
    }

    @Override
    public void tick() {
        super.tick();
        if (waveTimer > 0) {
            --waveTimer;
        } else if (waveTimer == 0 && ((Toad) this.entity).isWaving()) {
            ((Toad) this.entity).setToadState(0);
        }
    }

    @Override
    public void lookAt(Vec3d direction) {
        super.lookAt(direction);
        if (this.entity.getRandom().nextInt(30) == 0 && waveTimer == 0) {
            this.waveTimer = 20;
            ((Toad) this.entity).setToadState(1);
        }
    }
}
