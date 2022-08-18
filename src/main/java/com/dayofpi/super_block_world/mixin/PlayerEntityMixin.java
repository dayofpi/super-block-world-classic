package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.block_entities.WarpPipeBE;
import com.dayofpi.super_block_world.common.blocks.WarpPipeBlock;
import com.dayofpi.super_block_world.util.PowerUp;
import com.dayofpi.super_block_world.util.FormManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.UUID;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    private int pipeCooldown;
    private int powerTickTimer;
    private int flutterSoundTimer;
    private int airJumpTimer;
    private boolean canAirJump;
    private static final EntityAttributeModifier CAT_SPEED_BOOST = new EntityAttributeModifier(FormManager.CAT_SPEED_BOOST_ID, "Attacking speed boost", 0.04, EntityAttributeModifier.Operation.ADDITION);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    public void setSneaking(boolean sneaking) {
        if (sneaking) {
            this.warpToPipe();
        }
        super.setSneaking(sneaking);
    }

    private void warpToPipe() {
        /* If the Player's pipe cooldown is at 0, attempt to teleport to one of two positions.
        First it checks if there's a usable warp pipe IN the player's current position (ie they're inside it),
        then it checks if there's a usable warp pipe in the position directly below them.*/
        if (this.getPipeCooldown() != 0) return;
        BlockPos blockPos = this.getBlockPos();
        BlockPos floor = blockPos.down();
        BlockPos originPos = null;
        if (WarpPipeBlock.canEnterWarpPipe(world, blockPos)) originPos = blockPos;
        else if (WarpPipeBlock.canEnterWarpPipe(world, floor)) originPos = floor;
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
        if (destinPos == null) return;
        WarpPipeBlock.warp((PlayerEntity) (Object) this, destinPos, world);
        this.setPipeCooldown(20);
    }

    public int getPipeCooldown() {
        return pipeCooldown;
    }

    public void setPipeCooldown(int cooldown) {
        this.pipeCooldown = cooldown;
    }

    @Override
    public boolean isClimbing() {
        return super.isClimbing() || this.horizontalCollision && this.getPowerLevel() > 0 && PowerUp.hasPowerUp(this, PowerUp.CAT);
    }

    @Inject(at = @At("TAIL"), method = "updatePose")
    protected void updatePose(CallbackInfo ci) {
        if (this.getPose() == EntityPose.CROUCHING && PowerUp.hasPowerUp(this, PowerUp.CAT)) {
            this.setPose(EntityPose.SWIMMING);
        }
    }

    protected void removeCatBoost() {
        EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (entityAttributeInstance == null) {
            return;
        }
        if (entityAttributeInstance.getModifier(FormManager.CAT_SPEED_BOOST_ID) != null) {
            entityAttributeInstance.removeModifier(FormManager.CAT_SPEED_BOOST_ID);
        }
    }

    protected void addCatBoost() {
        if (PowerUp.hasPowerUp(this, PowerUp.CAT)) {
            EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if (entityAttributeInstance == null) {
                return;
            }
            if (!entityAttributeInstance.hasModifier(CAT_SPEED_BOOST)) {
                entityAttributeInstance.addTemporaryModifier(CAT_SPEED_BOOST);
            }
        }
    }

    @Override
    protected void applyMovementEffects(BlockPos pos) {
        super.applyMovementEffects(pos);
        if (!PowerUp.hasPowerUp(this, PowerUp.CAT)) {
            this.removeCatBoost();
        }
        this.addCatBoost();
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (this.isAlive()) {
            if (this.getPipeCooldown() > 0) {
                --this.pipeCooldown;
            }
            if (this.airJumpTimer >= 40 && !this.jumping) {
                this.canAirJump = true;
            }
            if (this.shouldFloat() && !this.isOnGround()) {
                this.fallDistance = 0.0F;
                if (PowerUp.hasPowerUp(this, PowerUp.TANOOKI)) {
                    ++this.airJumpTimer;
                }
                ++this.flutterSoundTimer;
                if (this.flutterSoundTimer >= 40 && (this.jumping || this.getVelocity().y > 0)) {
                    this.playSound(this.getFloatingSound(), 1.0F, this.getSoundPitch());
                    this.flutterSoundTimer = 0;
                }
            }
            Vec3d vec3d = this.getVelocity();
            if (PowerUp.hasPowerUp(this, PowerUp.BEE) && this.isTouchingWater()) {
                if (this.getPowerHealth() > 0) {
                    this.setPowerHealth(this.getPowerHealth() - 1);
                }
                if (this.getPowerHealth() <= 0) {
                    if (!world.isClient()) {
                        Random random = world.getRandom();
                        for (int i = 0; i < 4; ++i) {
                            ((ServerWorld) world).spawnParticles(ParticleTypes.POOF, this.getX() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), this.getY() + 0.5D, this.getZ() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                        }
                    }
                    this.playSound(Sounds.ENTITY_GENERIC_POWER_DOWN, 1.0F, 1.0F);
                    this.setPowerUp(PowerUp.NONE.asString());
                    this.setPowerHealth(10);
                }
            }
            if (PowerUp.hasPowerUp(this, PowerUp.BEE) && this.jumping && this.getPowerLevel() > 0) {
                ++this.powerTickTimer;
                this.setVelocity(vec3d.x, 0.2D, vec3d.z);
            }
            else if (PowerUp.hasPowerUp(this, PowerUp.TANOOKI) && this.getVelocity().y <= 0 && this.jumping && this.getPowerLevel() > 0 && this.canAirJump) {
                this.setPowerLevel(this.getPowerLevel() - 1);
                this.setVelocity(vec3d.x, 0.4D, vec3d.z);
                this.playSound(Sounds.ENTITY_GENERIC_TAIL_ATTACK, 0.5F, 1.0F);
                this.canAirJump = false;
            }
            else if (shouldFloat()) {
                this.canAirJump = false;
                if (!this.onGround && vec3d.y < 0.0D)
                    this.setVelocity(vec3d.multiply(1.0D, 0.6D, 1.0D));
            }
            if (this.shouldDecreasePowerLevel()) {
                ++this.powerTickTimer;
                if (this.powerTickTimer >= 12) {
                    this.setPowerLevel(this.getPowerLevel() - 1);
                    this.powerTickTimer = 0;
                    if (PowerUp.hasPowerUp(this, PowerUp.CAT)) {
                        BlockPos blockPos = this.getBlockPos().offset(this.getMovementDirection());
                        this.playStepSound(blockPos, world.getBlockState(blockPos));
                    }
                }
            } else if ((this.getPowerLevel() < 20) && this.isOnGround()) {
                ++this.powerTickTimer;
                if (this.powerTickTimer >= 10) {
                    this.setPowerLevel(this.getPowerLevel() + 1);
                    this.powerTickTimer = 0;
                }
            }
        }
    }

    private SoundEvent getFloatingSound() {
        return PowerUp.hasPowerUp(this, PowerUp.BEE) ? Sounds.ENTITY_GENERIC_FLUTTER_BEE : Sounds.ENTITY_GENERIC_FLUTTER_TANOOKI;
    }

    private boolean shouldDecreasePowerLevel() {
        if (this.getPowerLevel() <= 0 || this.hasVehicle()) return false;
        if (PowerUp.hasPowerUp(this, PowerUp.BEE)) return this.jumping;
        if (PowerUp.hasPowerUp(this, PowerUp.CAT)) return this.isClimbing();
        return false;
    }

    private boolean hasPowerUp() {
        return !this.getPowerUp().equals(PowerUp.NONE.asString());
    }

    @Inject(at = @At("HEAD"), method = "getDeathSound", cancellable = true)
    public void getDeathSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (this.hasPowerUp()) {
            cir.setReturnValue(Sounds.ENTITY_GENERIC_POWER_DOWN);
        }
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
        if (!this.shouldFloat())
            super.fall(heightDifference, onGround, state, landedPosition);
    }

    @Inject(at=@At("HEAD"), method = "handleFallDamage", cancellable = true)
    public void handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (shouldFloat()) {
            cir.setReturnValue(false);
        }
    }

    private boolean shouldFloat() {
        return PowerUp.hasPowerUp(this, PowerUp.BEE) || (PowerUp.hasPowerUp(this, PowerUp.TANOOKI) && this.jumping);
    }

    @Inject(at = @At("HEAD"), method = "applyDamage")
    protected void applyDamage(DamageSource source, float amount, CallbackInfo ci) {
        if (!this.hasPowerUp())
            return;
        if (this.getPowerHealth() > 0) {
            this.setPowerHealth(this.getPowerHealth() - 1);
        }
        if (this.getPowerHealth() <= 0) {
            if (!world.isClient()) {
                Random random = world.getRandom();
                for (int i = 0; i < 4; ++i) {
                    ((ServerWorld) world).spawnParticles(ParticleTypes.POOF, this.getX() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), this.getY() + 0.5D, this.getZ() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            }
            this.world.sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.setPowerUp(PowerUp.NONE.asString());
            this.setPowerHealth(10);
        }
    }

    @Inject(at = @At("HEAD"), method = "onDeath")
    public void onDeath(CallbackInfo ci) {
        if (this.hasPowerUp()) {
            // Remove the player's power-up. Playing the power-down sound in this method did not work.
            this.setPowerUp(PowerUp.NONE.asString());
            this.setPowerHealth(10);
        }
        if (this.isGoo()) {
            if (!this.world.isClient) {
                Entity entity = ((ServerWorld) this.world).getEntity(this.getGooMeUuid());
                if (entity != null) entity.discard();
            }
            this.setGooMeUuid(null);
        }
    }

    @Inject(at = @At("TAIL"), method = "initDataTracker")
    protected void initDataTracker(CallbackInfo ci) {
        this.dataTracker.startTracking(FormManager.POWER_LEVEL, 20);
        this.dataTracker.startTracking(FormManager.POWER_HEALTH, 10);
        this.dataTracker.startTracking(FormManager.POWER_UP, PowerUp.NONE.asString());
        this.dataTracker.startTracking(FormManager.GOO_ME_UUID, Optional.empty());
    }

    @Inject(at = @At("TAIL"), method = "writeCustomDataToNbt")
    protected void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt(FormManager.POWER_LEVEL_KEY, this.getPowerLevel());
        nbt.putInt(FormManager.POWER_HEALTH_KEY, this.getPowerHealth());
        nbt.putInt(FormManager.POWER_TICK_TIMER_KEY, this.powerTickTimer);
        nbt.putString(FormManager.POWER_UP_KEY, this.getPowerUp());
        if (this.getGooMeUuid() != null) {
            nbt.putUuid(FormManager.GOO_ME_KEY, this.getGooMeUuid());
        }
    }

    @Inject(at = @At("TAIL"), method = "readCustomDataFromNbt")
    protected void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains(FormManager.POWER_LEVEL_KEY)) {
            this.setPowerLevel(nbt.getInt(FormManager.POWER_LEVEL_KEY));
        }
        if (nbt.contains(FormManager.POWER_HEALTH_KEY)) {
            this.setPowerHealth(nbt.getInt(FormManager.POWER_HEALTH_KEY));
        }
        if (nbt.contains(FormManager.POWER_TICK_TIMER_KEY)) {
            this.powerTickTimer = nbt.getInt(FormManager.POWER_TICK_TIMER_KEY);
        }
        if (nbt.contains(FormManager.POWER_UP_KEY)) {
            this.setPowerUp(nbt.getString(FormManager.POWER_UP_KEY));
        }
        UUID uUID;
        if (nbt.containsUuid(FormManager.GOO_ME_KEY)) {
            uUID = nbt.getUuid(FormManager.GOO_ME_KEY);
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
        if (this.isGoo()) this.playSound(SoundEvents.BLOCK_SLIME_BLOCK_STEP, 0.45F, 1.0F);
    }

    @Nullable
    public UUID getGooMeUuid() {
        return this.dataTracker.get(FormManager.GOO_ME_UUID).orElse(null);
    }

    public void setGooMeUuid(@Nullable UUID uuid) {
        this.dataTracker.set(FormManager.GOO_ME_UUID, Optional.ofNullable(uuid));
    }

    public String getPowerUp() {
        return this.dataTracker.get(FormManager.POWER_UP);
    }

    public void setPowerUp(String name) {
        this.dataTracker.set(FormManager.POWER_UP, name);
    }

    public int getPowerLevel() {
        return this.dataTracker.get(FormManager.POWER_LEVEL);
    }

    public void setPowerLevel(int i) {
        this.dataTracker.set(FormManager.POWER_LEVEL, i);
    }

    public int getPowerHealth() {
        return this.dataTracker.get(FormManager.POWER_HEALTH);
    }

    public void setPowerHealth(int i) {
        this.dataTracker.set(FormManager.POWER_HEALTH, i);
    }
}
