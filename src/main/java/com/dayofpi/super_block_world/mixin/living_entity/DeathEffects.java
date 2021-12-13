package com.dayofpi.super_block_world.mixin.living_entity;

import com.dayofpi.super_block_world.main.registry.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class DeathEffects extends Entity {
    public DeathEffects(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at=@At("HEAD"), method = "onDeath")
    private void onDeath(DamageSource source, CallbackInfo info) {
        if (source.isProjectile() && source.getSource() != null && source.getSource().getType() == ModEntities.FIREBALL)
            this.setOnFireFor(5);
    }
}
