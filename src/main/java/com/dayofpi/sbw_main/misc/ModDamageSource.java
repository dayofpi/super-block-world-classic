package com.dayofpi.sbw_main.misc;

import net.minecraft.entity.damage.DamageSource;

public class ModDamageSource extends DamageSource {
    private boolean light;

    public static final DamageSource POISON = new ModDamageSource("poison");
    public static final DamageSource SPIKES = new ModDamageSource("spikes");
    public static final DamageSource MUNCHER = new ModDamageSource("muncher");
    public static final DamageSource JELLY = new ModDamageSource("jelly");

    public ModDamageSource(String string) {
        super(string);
    }

    public boolean isLight() {
        return this.light;
    }

    protected DamageSource setLight() {
        this.light = true;
        return this;
    }

    public static DamageSource light() {
        return new ModDamageSource("light").setLight();
    }
}
