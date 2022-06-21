package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.registry.ModTags;
import com.dayofpi.super_block_world.util.ModDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.Fluid;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    @Final
    protected Random random;

    @Shadow
    public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Shadow
    public abstract boolean damage(DamageSource source, float amount);

    @Shadow
    public abstract EntityType<?> getType();

    @Shadow
    public abstract boolean updateMovementInFluid(TagKey<Fluid> tag, double speed);

    @Inject(at = @At("TAIL"), method = "baseTick")
    private void baseTick(CallbackInfo ci) {
        if (this.isInPoison()) {
            this.doPoisonDamage();
        }
    }

    public boolean isInPoison() {
        return this.updateMovementInFluid(ModTags.POISON, 0.0023);
    }

    private boolean isPoisonImmune() {
        return this.getType().isIn(ModTags.POISON_IMMUNE);
    }

    private void doPoisonDamage() {
        if (this.isPoisonImmune()) {
            return;
        }
        if (this.damage(ModDamageSource.POISON, 5.0f)) {
            this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4f, 2.0f + this.random.nextFloat() * 0.4f);
        }
    }
}
