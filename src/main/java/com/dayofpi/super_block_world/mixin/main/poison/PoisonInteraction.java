package com.dayofpi.super_block_world.mixin.main.poison;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.registry.more.ParticleInit;
import com.dayofpi.super_block_world.registry.main.TagInit;
import com.dayofpi.super_block_world.common.util.entity.ModDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.Fluid;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Entity.class)
public abstract class PoisonInteraction {
    @Shadow public abstract double getX();

    @Shadow public abstract double getY();

    @Shadow public abstract double getZ();

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

    @Shadow @Final protected Random random;

    @Inject(at = @At("TAIL"), method = "baseTick")
    void baseTick(CallbackInfo info) {
        if (!this.getType().isIn(TagInit.POISON_IMMUNE)) {
            if (this.updateMovementInFluid(TagInit.POISON, 0.0023)) {
                if (this.damage(ModDamageSource.POISON, 4.0F)) {
                    World world = this.getWorld();
                    if (world.isClient)
                        world.addParticle(ParticleTypes.LARGE_SMOKE, this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() + 1, this.getBlockPos().getZ() + 0.5, 0.0D, 0.0D, 0.0D);
                    else
                        this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "onSwimmingStart", cancellable = true)
    public void onSwimmingStart(CallbackInfo info) {
        if (this.updateMovementInFluid(TagInit.POISON, 0.0023)) {
            // Replace water splash particles
            Entity entity = this.hasPassengers() && this.getPrimaryPassenger() != null ? this.getPrimaryPassenger() : this.getWorld().getEntityById(this.getId());
            if (entity != null) {
                this.playSound(SoundInit.FLUID_POISON_SWIM, 0.4F, 1.0F + (this.random.nextFloat() - this.random.nextFloat() * 0.5F));
                float y = (float) MathHelper.floor(this.getY());
                double x;
                double z;
                for (int i = 0; (float) i < 1.0F + this.dimensions.width * 20.0F; ++i) {
                    x = (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getDimensions(EntityPose.STANDING).width;
                    z = (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getDimensions(EntityPose.STANDING).width;
                    this.getWorld().addParticle(ParticleInit.POISON_BUBBLE, this.getX() + x, y + 1.0F, this.getZ() + z, 0, 0, 0);
                }
                info.cancel();
            }
        }
    }
}

