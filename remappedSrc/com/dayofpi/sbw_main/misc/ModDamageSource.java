package com.dayofpi.sbw_main.misc;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public class ModDamageSource extends DamageSource {
    private boolean light;

    public static final DamageSource POISON = new ModDamageSource("poison");
    public static final DamageSource SPIKES = new ModDamageSource("spikes");
    public static final DamageSource MUNCHER = new ModDamageSource("muncher");

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

    public static DamageSource stomp(LivingEntity attacker) {
        return new EntityDamageSource("stomp", attacker);
    }

    public static DamageSource mobDrop(LivingEntity attacker) {
        return new EntityDamageSource("mob_drop", attacker);
    }

    public static DamageSource spikyMob(LivingEntity attacker) {
        return new EntityDamageSource("spiky_mob", attacker);
    }
}
