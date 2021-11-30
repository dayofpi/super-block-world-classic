package com.dayofpi.sbw_mixin;

import com.dayofpi.sbw_main.ModSounds;
import com.dayofpi.sbw_main.ModTags;
import com.dayofpi.sbw_main.misc.ModDamageSource;
import com.dayofpi.sbw_main.world.registry.ModParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.Fluid;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Entity.class)
public abstract class EntityPoisonInteraction {
    @Shadow public abstract double getX();

    @Shadow public abstract double getY();

    @Shadow public abstract double getZ();

    @Shadow public abstract SoundCategory getSoundCategory();

    @Shadow public abstract EntityDimensions getDimensions(EntityPose pose);

    @Shadow public abstract boolean hasPassengers();

    @Shadow public @Nullable abstract Entity getPrimaryPassenger();

    @Shadow public abstract int getId();

    @Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Shadow public abstract World getWorld();

    @Shadow public abstract EntityType<?> getType();

    @Shadow public abstract boolean updateMovementInFluid(Tag<Fluid> tag, double d);

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Shadow public abstract BlockPos getBlockPos();

    @Shadow private EntityDimensions dimensions;
    private final Random random = new Random();
    protected boolean touchingPoison;

    public boolean isTouchingPoison() {
        return this.touchingPoison;
    }

    @Inject(at = @At("TAIL"), method = "baseTick")
    void baseTick(CallbackInfo info) {
        if (this.isTouchingPoison()) {
            if (!this.getType().isIn(ModTags.POISON_IMMUNE)) {
                if (this.damage(ModDamageSource.POISON, 4.0F)) {
                    this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
                    if (this.getWorld().isClient)
                        this.getWorld().addParticle(ParticleTypes.LARGE_SMOKE, this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() + 1, this.getBlockPos().getZ() + 0.5, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "onSwimmingStart", cancellable = true)
    public void onSwimmingStart(CallbackInfo info) {
        if (this.updateMovementInFluid(ModTags.POISON, 0.0023)) {
            // Replace water splash particles
            Entity entity = this.hasPassengers() && this.getPrimaryPassenger() != null ? this.getPrimaryPassenger() : this.getWorld().getEntityById(this.getId());
            if (entity != null) {
                this.playSound(ModSounds.BLOCK_POISON_SWIM, 0.4F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);

                float h = (float) MathHelper.floor(this.getY());

                int i;
                double d;
                double e;
                for (i = 0; (float) i < 1.0F + this.dimensions.width * 20.0F; ++i) {
                    d = (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getDimensions(EntityPose.STANDING).width;
                    e = (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getDimensions(EntityPose.STANDING).width;
                    this.getWorld().addParticle(ModParticle.POISON_BUBBLE, this.getX() + d, h + 1.0F, this.getZ() + e, 0, 0, 0);
                }
                info.cancel();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "checkWaterState")
    void checkWaterState(CallbackInfo info) {
        this.touchingPoison = this.updateMovementInFluid(ModTags.POISON, 0.0023);
    }
}

