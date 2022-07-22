package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.common.block_entities.WarpPipeBE;
import com.dayofpi.super_block_world.common.blocks.WarpPipeBlock;
import com.dayofpi.super_block_world.util.FormManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.UUID;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow
    @Final
    private PlayerAbilities abilities;
    private int pipeCooldown;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    protected abstract void dropShoulderEntities();

    public void setSneaking(boolean sneaking) {
        if (sneaking) {
            this.warpToPipe();
        }
        super.setSneaking(sneaking);
    }

    private void warpToPipe() {
        if (this.getPipeCooldown() != 0)
            return;
        BlockPos blockPos = this.getBlockPos();
        BlockPos floor = blockPos.down();
        BlockPos originPos = null;
        if (WarpPipeBlock.canEnterWarpPipe(world, blockPos))
            originPos = blockPos;
        else if (WarpPipeBlock.canEnterWarpPipe(world, floor))
            originPos = floor;
        if (originPos != null) {
            this.warpToPipe(originPos);
        }
    }

    private void warpToPipe(BlockPos originPos) {
        BlockPos destinPos = null;
        BlockEntity blockEntity = world.getBlockEntity(originPos);
        if (blockEntity instanceof WarpPipeBE warpPipeBE) {
            destinPos = warpPipeBE.destinPos;
        }
        if (destinPos == null)
            return;
        WarpPipeBlock.warp((PlayerEntity)(Object)this, destinPos, world);
        this.setPipeCooldown(20);

    }

    public int getPipeCooldown() {
        return pipeCooldown;
    }

    public void setPipeCooldown(int cooldown) {
        this.pipeCooldown = cooldown;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (this.isAlive()) {
            if (this.getPipeCooldown() > 0) {
                --this.pipeCooldown;
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "onDeath")
    public void onDeath(CallbackInfo ci) {
        if (this.isGoo()) {
            if (!this.world.isClient) {
                Entity entity = ((ServerWorld) this.world).getEntity(this.getGooMeUuid());
                if (entity != null)
                    entity.discard();
            }
            this.setGooMeUuid(null);
        }
    }

    @Inject(at = @At("TAIL"), method = "initDataTracker")
    protected void initDataTracker(CallbackInfo ci) {
        this.dataTracker.startTracking(FormManager.GOO_ME_UUID, Optional.empty());
    }

    @Inject(at = @At("TAIL"), method = "writeCustomDataToNbt")
    protected void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        if (this.getGooMeUuid() != null) {
            nbt.putUuid("GooMe", this.getGooMeUuid());
        }
    }

    @Inject(at = @At("TAIL"), method = "readCustomDataFromNbt")
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
            } catch (Throwable ignored) {
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
