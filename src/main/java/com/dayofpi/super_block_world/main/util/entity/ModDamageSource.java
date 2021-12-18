package com.dayofpi.super_block_world.main.util.entity;

import net.minecraft.entity.damage.DamageSource;

public class ModDamageSource extends DamageSource {
    private boolean light;

    public static DamageSource POISON;
    public static DamageSource SPIKES;
    public static DamageSource MUNCHER;
    public static DamageSource JELLYBEAM;

    public ModDamageSource(String string) {
        super(string);
    }

    private static ModDamageSource registerDamageSource(String name) {
        return new ModDamageSource("super_block_world:" + name);
    }

    public static void registerDamageSources() {
        POISON = registerDamageSource("poison");
        SPIKES = registerDamageSource("spikes");
        MUNCHER = registerDamageSource("muncher");
        JELLYBEAM = registerDamageSource("jellybeam");
    }

    public boolean isLight() {
        return this.light;
    }

    protected DamageSource setLight() {
        this.light = true;
        return this;
    }

    public static DamageSource light() {
        return registerDamageSource("light").setLight();
    }
}
