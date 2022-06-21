package com.dayofpi.super_block_world.registry.more;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public class MobDamageSource extends EntityDamageSource {
    private boolean fromAbove;

    public MobDamageSource(String name, Entity source) {
        super(name, source);
    }

    public boolean isFromAbove() {
        return this.fromAbove;
    }

    protected MobDamageSource setFromAbove() {
        this.fromAbove = true;
        return this;
    }

    public static DamageSource shell(Entity attacker) {
        return new MobDamageSource("super_block_world:shell", attacker).setNeutral();
    }

    public static DamageSource stomp(Entity attacker) {
        return new MobDamageSource("super_block_world:stomp", attacker).setFromAbove();
    }

    public static DamageSource mobDrop(LivingEntity attacker) {
        return new MobDamageSource("super_block_world:mob_drop", attacker).setFromAbove();
    }
}
