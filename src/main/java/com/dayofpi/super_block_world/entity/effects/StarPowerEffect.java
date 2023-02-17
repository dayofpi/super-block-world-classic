package com.dayofpi.super_block_world.entity.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class StarPowerEffect extends StatusEffect {
    public StarPowerEffect() {
        super(StatusEffectCategory.BENEFICIAL, 16777092);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
