package com.dayofpi.sbw_mixin;

import com.dayofpi.sbw_mixin.interfaces.InterfaceEntity;
import com.dayofpi.sbw_main.TagList;
import com.dayofpi.sbw_main.misc.ModDamageSource;
import com.dayofpi.sbw_main.world.registry.ModParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
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

    private final Random random = new Random();
    protected boolean touchingPoison;

    public boolean isTouchingPoison() {
        return this.touchingPoison;
    }

    @Inject(at = @At("TAIL"), method = "baseTick")
    void baseTick(CallbackInfo info) {
        if (this.isTouchingPoison()) {
            if (!((InterfaceEntity) this).aType().isIn(TagList.POISON_IMMUNE)) {
                if (((InterfaceEntity) this).iDamage(ModDamageSource.POISON, 4.0F)) {
                    ((InterfaceEntity) this).aWorld().playSound(null, ((InterfaceEntity) this).aBlockPos(), SoundEvents.ENTITY_GENERIC_BURN, ((InterfaceEntity) this).iGetSoundCategory(), 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
                    if (((InterfaceEntity) this).aWorld().isClient)
                        ((InterfaceEntity) this).aWorld().addParticle(ParticleTypes.LARGE_SMOKE, ((InterfaceEntity) this).aBlockPos().getX() + 0.5, ((InterfaceEntity) this).aBlockPos().getY() + 1, ((InterfaceEntity) this).aBlockPos().getZ() + 0.5, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "onSwimmingStart", cancellable = true)
    public void onSwimmingStart(CallbackInfo info) {
        if (((InterfaceEntity) this).iUpdateMovementInFluid(TagList.POISON, 0.0023)) {
            // Replace water splash particles
            Entity entity = this.hasPassengers() && this.getPrimaryPassenger() != null ? this.getPrimaryPassenger() : ((InterfaceEntity) this).aWorld().getEntityById(this.getId());
            if (entity != null) {
                float h = (float) MathHelper.floor(this.getY());

                int i;
                double d;
                double e;
                for (i = 0; (float) i < 1.0F + this.getDimensions(EntityPose.STANDING).width * 20.0F; ++i) {
                    d = (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getDimensions(EntityPose.STANDING).width;
                    e = (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getDimensions(EntityPose.STANDING).width;
                    ((InterfaceEntity) this).aWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_GENERIC_SPLASH, this.getSoundCategory(), 0.6F, 0.6F);
                    ((InterfaceEntity) this).aWorld().addParticle(ModParticles.POISON_BUBBLE, this.getX() + d, h + 1.0F, this.getZ() + e, 0, 0, 0);
                }
                info.cancel();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "checkWaterState")
    void checkWaterState(CallbackInfo info) {
        this.touchingPoison = ((InterfaceEntity) this).iUpdateMovementInFluid(TagList.POISON, 0.0023);
    }
}

