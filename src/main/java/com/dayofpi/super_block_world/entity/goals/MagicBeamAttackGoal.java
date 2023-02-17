package com.dayofpi.super_block_world.entity.goals;

import com.dayofpi.super_block_world.audio.Sounds;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.mob.MobEntity;

public class MagicBeamAttackGoal extends ProjectileAttackGoal {
    private final RangedAttackMob mob;

    public MagicBeamAttackGoal(RangedAttackMob mob, double mobSpeed, int intervalTicks, float maxShootRange) {
        super(mob, mobSpeed, intervalTicks, maxShootRange);
        this.mob = mob;
    }

    @Override
    public void start() {
        super.start();
        if (this.mob instanceof MobEntity)
            ((MobEntity) this.mob).playSound(Sounds.ENTITY_GENERIC_PREPARE_SPELL, 1.0F, 1.0F);
    }
}
