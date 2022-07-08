package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.util.FormManager;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.UUID;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow @Final private PlayerAbilities abilities;

    @Shadow protected abstract void dropShoulderEntities();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at=@At("HEAD"), method = "damage", cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (this.isGoo()) {
            if (FormManager.isGooMeImmuneTo(source)) {
                cir.setReturnValue(false);
            }
            if (this.abilities.invulnerable && !source.isOutOfWorld()) {
                cir.setReturnValue(false);
            }
            this.despawnCounter = 0;
            if (this.isDead()) {
               cir.setReturnValue(false);
            }
            if (!this.world.isClient) {
                this.dropShoulderEntities();
            }
            amount = amount * 2.0f;
            if (source.isScaledWithDifficulty()) {
                if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
                    amount = 0.0f;
                }
                if (this.world.getDifficulty() == Difficulty.EASY) {
                    amount = Math.min(amount / 2.0f + 1.0f, amount);
                }
                if (this.world.getDifficulty() == Difficulty.HARD) {
                    amount = amount * 3.0f / 2.0f;
                }
            }
            if (amount == 0.0f) {
                cir.setReturnValue(false);
            }
            cir.setReturnValue(super.damage(source, amount));
        }
    }

    @Inject(at=@At("HEAD"), method = "onDeath")
    public void onDeath(CallbackInfo ci) {
        if (this.isGoo()) {
            if (!this.world.isClient) {
                Entity entity = ((ServerWorld)this.world).getEntity(this.getGooMeUuid());
                if (entity != null)
                    entity.discard();
            }
            this.setGooMeUuid(null);
        }
    }

    @Inject(at=@At("TAIL"), method = "initDataTracker")
    protected void initDataTracker(CallbackInfo ci) {
        this.dataTracker.startTracking(FormManager.GOO_ME_UUID, Optional.empty());
    }

    @Inject(at=@At("TAIL"), method = "writeCustomDataToNbt")
    protected void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        if (this.getGooMeUuid() != null) {
            nbt.putUuid("GooMe", this.getGooMeUuid());
        }
    }

    @Inject(at=@At("TAIL"), method = "readCustomDataFromNbt")
    protected void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        UUID uUID;
        if (nbt.containsUuid("GooMe")) {
            uUID = nbt.getUuid("GooMe");
        } else {
            uUID = null;
        }
        if (uUID != null) {
            try {
                this.setGooMeUuid(uUID);
            }
            catch (Throwable ignored) {
            }
        }
    }

    public boolean isGoo() {
        return this.getGooMeUuid() != null;
    }

    @Override
    public boolean hurtByWater() {
        return this.isGoo();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        super.playStepSound(pos, state);
        if (this.isGoo())
            this.playSound(SoundEvents.BLOCK_SLIME_BLOCK_STEP, 0.45F, 1.0F);
    }

    @Nullable
    public UUID getGooMeUuid() {
        return this.dataTracker.get(FormManager.GOO_ME_UUID).orElse(null);
    }

    public void setGooMeUuid(@Nullable UUID uuid) {
        this.dataTracker.set(FormManager.GOO_ME_UUID, Optional.ofNullable(uuid));
    }
}
