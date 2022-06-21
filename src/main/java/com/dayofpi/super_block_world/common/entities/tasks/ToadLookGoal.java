package com.dayofpi.super_block_world.common.entities.tasks;

import com.dayofpi.super_block_world.common.entities.Toad;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.passive.PassiveEntity;

public class ToadLookGoal<T extends PassiveEntity> extends LookAtEntityGoal {
    protected final T toad;
    private final int fullTimer = 9;
    private int waveTimer = fullTimer;

    public ToadLookGoal(T toad, Class<? extends LivingEntity> targetType, float range) {
        super(toad, targetType, range);
        this.toad = toad;
    }

    @Override
    public void tick() {
        super.tick();
        if (!(toad instanceof Toad))
            return;
        if (!((Toad) toad).isScared() && !((Toad) toad).isCheering() && toad.getRandom().nextInt(40) == 0 && waveTimer == fullTimer) {
            ((Toad) toad).setToadState(1);
        }

        if (((Toad) toad).isWaving()) {
            --waveTimer;
        } else waveTimer = fullTimer;

        if (waveTimer == 0) {
            ((Toad) toad).setToadState(0);
        }
    }

    @Override
    public void stop() {
        super.stop();
        ((Toad) toad).setToadState(0);
    }
}
