package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.ModTags;
import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.entity.ModEntities;
import com.dayofpi.super_block_world.entity.entities.misc.DryBonesShellEntity;
import com.dayofpi.super_block_world.util.FormManager;
import com.dayofpi.super_block_world.util.ModDamageSource;
import com.dayofpi.super_block_world.util.PowerUp;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.fluid.Fluid;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

    @Shadow public abstract @Nullable Entity getVehicle();

    @Shadow public abstract boolean isSubmergedIn(TagKey<Fluid> fluidTag);

    @Shadow public abstract boolean isPlayer();

    @Shadow public abstract DataTracker getDataTracker();

    @Shadow public World world;

    @Shadow public abstract double getX();

    @Shadow public abstract double getY();

    @Shadow public abstract double getZ();

    @Shadow public abstract boolean isLiving();

    @Inject(at = @At("TAIL"), method = "baseTick")
    private void baseTick(CallbackInfo ci) {
        if (this.isInPoison()) {
            this.doPoisonDamage();
        }
    }

    @Inject(at=@At("HEAD"), method = "isInLava", cancellable = true)
    public void isInLava(CallbackInfoReturnable<Boolean> cir) {
        if (this.getVehicle() instanceof DryBonesShellEntity && !this.isSubmergedIn(FluidTags.LAVA)) {
            cir.setReturnValue(false);
        }
    }

    public boolean isInPoison() {
        return this.updateMovementInFluid(ModTags.POISON, 0.0023);
    }

    @SuppressWarnings("ConstantConditions")
    private boolean isPoisonImmune() {
        return this.getType() == ModEntities.DRY_BONES_SHELL || this.isLiving() && ((LivingEntity) (Object) this).getGroup() == EntityGroup.UNDEAD;
    }

    private void doPoisonDamage() {
        if (this.isPoisonImmune()) {
            return;
        }
        if (this.isPlayer() && !this.getDataTracker().get(FormManager.POWER_UP).equals(PowerUp.NONE.asString())) {
            this.getDataTracker().set(FormManager.POWER_UP, PowerUp.NONE.asString());
            this.playSound(Sounds.ENTITY_GENERIC_POWER_DOWN, 1.0F, 1.0F);
            if (!world.isClient()) {
                Random random = world.getRandom();
                for (int i = 0; i < 4; ++i) {
                    ((ServerWorld) world).spawnParticles(ParticleTypes.POOF, this.getX() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), this.getY() + 0.5D, this.getZ() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            }
        }
        if (this.damage(ModDamageSource.POISON, 5.0f)) {
            this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4f, 2.0f + this.random.nextFloat() * 0.4f);
        }
    }
}
