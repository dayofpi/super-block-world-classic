package com.dayofpi.super_block_world.registry.more;

public class DamageSource extends net.minecraft.entity.damage.DamageSource {
    private boolean light;

    public static net.minecraft.entity.damage.DamageSource POISON;
    public static net.minecraft.entity.damage.DamageSource SPIKES;
    public static net.minecraft.entity.damage.DamageSource MUNCHER;
    public static net.minecraft.entity.damage.DamageSource JELLYBEAM;

    public DamageSource(String string) {
        super(string);
    }

    private static DamageSource registerDamageSource(String name) {
        return new DamageSource("super_block_world:" + name);
    }

    public static void register() {
        POISON = registerDamageSource("poison");
        SPIKES = registerDamageSource("spikes");
        MUNCHER = registerDamageSource("muncher");
        JELLYBEAM = registerDamageSource("jellybeam");
    }

    public boolean isLight() {
        return this.light;
    }

    protected net.minecraft.entity.damage.DamageSource setLight() {
        this.light = true;
        return this;
    }

    public static net.minecraft.entity.damage.DamageSource light() {
        return registerDamageSource("light").setLight();
    }
}
