package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.blocks.BrickBlock;
import com.dayofpi.super_block_world.common.blocks.FakeBlock;
import com.dayofpi.super_block_world.common.blocks.ReactiveBlock;
import com.dayofpi.super_block_world.common.entities.passive.SpindriftEntity;
import com.dayofpi.super_block_world.common.entities.projectile.ModFireballEntity;
import com.dayofpi.super_block_world.registry.*;
import com.dayofpi.super_block_world.util.ModDamageSource;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    protected boolean jumping;
    private int stompCooldown;

    @SuppressWarnings("unused")
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract void readCustomDataFromNbt(NbtCompound nbt);

    @Shadow
    public abstract EntityGroup getGroup();

    @Shadow
    public abstract boolean isDead();

    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot var1);

    @SuppressWarnings("UnusedReturnValue")
    @Shadow
    public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow
    public abstract float getSoundPitch();

    @Shadow
    public abstract boolean isInSwimmingPose();

    @Shadow
    public abstract boolean canMoveVoluntarily();

    @Shadow
    protected abstract boolean shouldSwimInFluids();

    @Shadow
    public abstract Vec3d applyFluidMovingSpeed(double gravity, boolean falling, Vec3d motion);

    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow
    protected abstract void swimUpward(TagKey<Fluid> fluid);

    @Shadow
    public abstract float getHealth();

    @Inject(at = @At("HEAD"), method = "onDeath")
    private void onDeath(DamageSource source, CallbackInfo info) {
        if (source.getSource() != null && source.getSource() instanceof ModFireballEntity)
            this.setOnFireFor(5);
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void tick(CallbackInfo info) {
        if (this.getEquippedStack(EquipmentSlot.FEET).isOf(ModItems.CLOUD_BOOTS)) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 200, 0, false, false, true));
        }

        if (this.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.RED_SHELL) && !this.isOnFire() && !this.isInLava()) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 200, 0, false, false, true));
        }
    }

    public boolean isInPoison() {
        return this.updateMovementInFluid(ModTags.POISON, 0.0023);
    }

    @Inject(at = @At("HEAD"), method = "travel")
    private void travel(Vec3d movementInput, CallbackInfo ci) {
        if (this.canMoveVoluntarily() || this.isLogicalSideForUpdatingMovement()) {
            boolean bl;
            double d = 0.08;
            bl = this.getVelocity().y <= 0.0;
            if (bl && this.hasStatusEffect(StatusEffects.SLOW_FALLING)) {
                d = 0.01;
                this.onLanding();
            }
            if (this.isInPoison() && this.shouldSwimInFluids()) {
                Vec3d vec3d3;
                double e = this.getY();
                this.updateVelocity(0.02f, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                if (this.getFluidHeight(ModTags.POISON) <= this.getSwimHeight()) {
                    this.setVelocity(this.getVelocity().multiply(0.5, 0.8f, 0.5));
                    vec3d3 = this.applyFluidMovingSpeed(d, bl, this.getVelocity());
                    this.setVelocity(vec3d3);
                } else {
                    this.setVelocity(this.getVelocity().multiply(0.5));
                }
                if (!this.hasNoGravity()) {
                    this.setVelocity(this.getVelocity().add(0.0, -d / 4.0, 0.0));
                }
                vec3d3 = this.getVelocity();
                if (this.horizontalCollision && this.doesNotCollide(vec3d3.x, vec3d3.y + (double) 0.6f - this.getY() + e, vec3d3.z)) {
                    this.setVelocity(vec3d3.x, 0.3f, vec3d3.z);
                }
            }
        }
    }

    private void stomp(LivingEntity livingEntity) {
        Vec3d vec3d = this.getVelocity();
        this.stompCooldown = 5;
        if (this.isSneaking() || livingEntity.getType().isIn(ModTags.STOMP_IGNORED) || livingEntity.getType().isIn(ModTags.STOMP_IMMUNE)) {
            double d = 0.5D;
            if (livingEntity instanceof SpindriftEntity)
                d = 1.45D;
            this.setVelocity(vec3d.x, d, vec3d.z);
            this.playSound(Sounds.ENTITY_GENERIC_JUMP_BOUNCE, 1.0F, 1.0F);
            return;
        }
        livingEntity.damage(ModDamageSource.stomp((LivingEntity) (Object) this), 5);
        this.setVelocity(vec3d.x, 0.6D, vec3d.z);
        this.playSound(Sounds.ENTITY_GENERIC_JUMP_ATTACK, 1.0F, 1.0F);
    }

    @Inject(at = @At("HEAD"), method = "jump")
    protected void jump(CallbackInfo info) {
        if (this.hasJumpBoots() && !this.isSneaking()) {
            this.playSound(Sounds.ENTITY_GENERIC_JUMP, 0.6F, this.getSoundPitch());
        }
    }

    @Inject(at = @At("HEAD"), method = "getJumpVelocity", cancellable = true)
    private void getJumpVelocity(CallbackInfoReturnable<Float> cir) {
        if (this.hasJumpBoots()) {
            float jumpHeight = 0.92F;
            if (this.isSneaking()) {
                jumpHeight = 0.5F;
            }
            cir.setReturnValue(jumpHeight * this.getJumpVelocityMultiplier());
            cir.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "handleFallDamage", cancellable = true)
    public void handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (this.hasJumpBoots()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!source.isOutOfWorld() && this.hasStatusEffect(Main.STAR_POWER)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
        Entity entity = source.getSource();
        if (entity == null)
            return;
        if (source instanceof ModDamageSource && ((ModDamageSource) source).isStomp()) {
            if (entity instanceof ServerPlayerEntity && (this.getHealth() - amount <= 0.0F)) {
                ModCriteria.STOMP_ENTITY.trigger((ServerPlayerEntity) entity);
            }
        }
        if (this.hasBuzzyShell() && source.isProjectile() && entity.getY() > this.getY() + 0.5D) {
            entity.pushAwayFrom(this);
            this.getEquippedStack(EquipmentSlot.HEAD).damage(5, (LivingEntity) (Object) this, p -> p.sendEquipmentBreakStatus(EquipmentSlot.HEAD));
            cir.setReturnValue(false);
            cir.cancel();
        }
    }


    @Inject(at = @At("TAIL"), method = "tickMovement")
    private void tickMovement(CallbackInfo info) {
        if (this.isSpectator() || this.isDead()) return;
        if (this.isSubmergedInWater() && this.getGroup() == EntityGroup.AQUATIC) return;
        Box box = this.getBoundingBox().contract(0.2D, 0.3D, 0.2D).offset(0.0D, 0.34D, 0.0D);

        List<BlockPos> posList = BlockPos.stream(box).toList();
        for (BlockPos blockPos : posList) {
            BlockState blockState = world.getBlockState(blockPos);
            if (this.getBlockY() < blockPos.getY()) {
                if (blockState.getBlock() instanceof ReactiveBlock reactiveBlock) {
                    reactiveBlock.react(world, blockPos, (LivingEntity) (Object) this);
                }
                if (blockState.getBlock() instanceof BrickBlock || blockState.getBlock() instanceof FakeBlock) {
                    if (this.hasShellmet()) world.breakBlock(blockPos, true);
                }
            }
        }

        if (this.jumping && this.shouldSwimInFluids()) {
            double fluidHeight = this.getFluidHeight(ModTags.POISON);
            double height = this.getSwimHeight();
            if (this.isInPoison() && (!this.onGround || fluidHeight > height)) {
                this.swimUpward(ModTags.POISON);
            }
        }

        if (this.hasJumpBoots() || this.isYoshi()) {
            if (stompCooldown > 0) --stompCooldown;
            if (this.hasVehicle() || this.isOnGround() || this.isInSwimmingPose()) return;
            if (stompCooldown == 0) {
                List<Entity> list = world.getOtherEntities(this, this.getBoundingBox(), EntityPredicates.VALID_LIVING_ENTITY);
                if (!list.isEmpty()) {
                    list.forEach(entity -> {
                        if (this.canStomp(entity)) {
                            this.stomp((LivingEntity) entity);
                        }
                    });
                }
            }
        }
    }

    private boolean canStomp(Entity entity) {
        if (this.getPassengerList().contains(entity)) return false;
        if (entity.getType().isIn(ModTags.STOMP_IGNORED) && !this.isYoshi()) return false;
        return entity.getPassengerList().isEmpty() && this.getY() > entity.getBlockY() + 0.2D;
    }

    private boolean isYoshi() {
        return this.getType() == ModEntities.YOSHI;
    }

    private boolean hasJumpBoots() {
        ItemStack boots = this.getEquippedStack(EquipmentSlot.FEET);
        return (boots.isOf(ModItems.JUMP_BOOTS));
    }

    private boolean hasBuzzyShell() {
        ItemStack helmet = this.getEquippedStack(EquipmentSlot.HEAD);
        return (helmet.isOf(ModItems.BUZZY_SHELL));
    }

    private boolean hasShellmet() {
        ItemStack helmet = this.getEquippedStack(EquipmentSlot.HEAD);
        return (helmet.isIn(ModTags.SHELLMETS));
    }
}
