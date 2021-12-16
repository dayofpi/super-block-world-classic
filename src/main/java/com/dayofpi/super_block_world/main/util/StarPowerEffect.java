package com.dayofpi.super_block_world.main.util;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class StarPowerEffect extends StatusEffect {
    public StarPowerEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xffff84);
    }

    // This method is called every tick to check whether it should apply the status effect or not
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.
        return true;
    }
}
