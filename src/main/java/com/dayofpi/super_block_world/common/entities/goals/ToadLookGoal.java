package com.dayofpi.super_block_world.common.entities.goals;

import com.dayofpi.super_block_world.common.entities.mob.ToadEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;

public class ToadLookGoal extends LookAtEntityGoal {
    protected final ToadEntity toad;
    private final int fullTimer = 9;
    private int waveTimer = fullTimer;

    public ToadLookGoal(ToadEntity toad, Class<? extends LivingEntity> targetType, float range) {
        super(toad, targetType, range);
        this.toad = toad;
    }

    @Override
    public void tick() {
        super.tick();
        if (!toad.isScared() && !toad.isCheering() && toad.getRandom().nextInt(40) == 0 && waveTimer == fullTimer) {
            toad.setToadState(1);
        }

        if (toad.isWaving()) {
            --waveTimer;
        } else waveTimer = fullTimer;

        if (waveTimer == 0) {
            toad.setToadState(0);
        }
    }

    @Override
    public void stop() {
        super.stop();
        toad.setToadState(0);
    }
}
