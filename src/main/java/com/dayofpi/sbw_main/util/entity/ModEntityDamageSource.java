package com.dayofpi.sbw_main.util.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public class ModEntityDamageSource extends EntityDamageSource {
    private boolean fromAbove;
    private boolean stomp;

    public ModEntityDamageSource(String name, Entity source) {
        super(name, source);
    }

    public boolean isFromAbove() {
        return this.fromAbove;
    }

    protected ModEntityDamageSource setFromAbove() {
        this.fromAbove = true;
        return this;
    }

    public boolean isStomp() {
        return this.stomp;
    }

    protected ModEntityDamageSource setStomp() {
        this.stomp = true;
        return this;
    }

    public static DamageSource stomp(LivingEntity attacker) {
        return new ModEntityDamageSource("stomp", attacker).setFromAbove().setStomp();
    }

    public static DamageSource mobDrop(LivingEntity attacker) {
        return new ModEntityDamageSource("mob_drop", attacker).setFromAbove();
    }

    public static DamageSource spikyMob(LivingEntity attacker) {
        return new EntityDamageSource("spiky_mob", attacker);
    }
}
