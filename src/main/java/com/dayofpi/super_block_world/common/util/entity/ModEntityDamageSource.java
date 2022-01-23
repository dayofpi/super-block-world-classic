package com.dayofpi.super_block_world.common.util.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public class ModEntityDamageSource extends EntityDamageSource {
    private boolean fromAbove;

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

    public static DamageSource shell(Entity attacker) {
        return new ModEntityDamageSource("super_block_world:shell", attacker).setNeutral();
    }

    public static DamageSource stomp(Entity attacker) {
        return new ModEntityDamageSource("super_block_world:stomp", attacker).setFromAbove();
    }

    public static DamageSource mobDrop(LivingEntity attacker) {
        return new ModEntityDamageSource("super_block_world:mob_drop", attacker).setFromAbove();
    }
}
