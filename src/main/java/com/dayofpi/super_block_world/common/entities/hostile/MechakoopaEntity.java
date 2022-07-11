package com.dayofpi.super_block_world.common.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.projectile.MechakoopaMissileEntity;
import com.dayofpi.super_block_world.registry.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class MechakoopaEntity extends TameableEntity implements RangedAttackMob {
    private static final TrackedData<Integer> POWER;
    private static final TrackedData<Integer> BEAM_TARGET_ID;

    static {
        POWER = DataTracker.registerData(MechakoopaEntity.class, TrackedDataHandlerRegistry.INTEGER);
        BEAM_TARGET_ID = DataTracker.registerData(MechakoopaEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    @Nullable
    private LivingEntity cachedBeamTarget;
    private int beamTicks;

    public MechakoopaEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createMechakoopaAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.37D).add(EntityAttributes.GENERIC_MAX_HEALTH, 14.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(3, new MechakoopaEntity.MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(3, new MechakoopaEntity.MissileAttackGoal(this, 1.0D, 40, 10.0F));
        this.goalSelector.add(3, new MechakoopaEntity.BeamAttackGoal(this));
        this.goalSelector.add(4, new FollowOwnerGoal(this, 1.0D, 6.0F, 2.0F, false));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(10, new LookAroundGoal(this));
        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
        this.targetSelector.add(3, new UntamedActiveTargetGoal<>(this, PlayerEntity.class, false, livingEntity -> true));
    }

    @Nullable
    @Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(ModItems.MECHAKOOPA);
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        MechakoopaMissileEntity missileEntity = new MechakoopaMissileEntity(world, this, target);
        missileEntity.setVelocity(this, this.getPitch(), this.getHeadYaw(), 0.0F, 0.12F, 0.0F);
        world.spawnEntity(missileEntity);
        this.playSound(Sounds.ENTITY_BULLET_SHOOT, 1.0F, 1.2F);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(POWER, 0);
        this.dataTracker.startTracking(BEAM_TARGET_ID, 0);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Power", this.getPower());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setPower(nbt.getInt("Power"));
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_MECHAKOOPA_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_MECHAKOOPA_DEATH;
    }

    public int getWarmupTime() {
        return 40;
    }

    public boolean hasBeamTarget() {
        return this.dataTracker.get(BEAM_TARGET_ID) != 0;
    }

    @Nullable
    public LivingEntity getBeamTarget() {
        if (!this.hasBeamTarget()) {
            return null;
        } else if (this.world.isClient) {
            if (this.cachedBeamTarget != null) {
                return this.cachedBeamTarget;
            } else {
                Entity entity = this.world.getEntityById(this.dataTracker.get(BEAM_TARGET_ID));
                if (entity instanceof LivingEntity) {
                    this.cachedBeamTarget = (LivingEntity) entity;
                    return this.cachedBeamTarget;
                } else {
                    return null;
                }
            }
        } else {
            return this.getTarget();
        }
    }

    void setBeamTarget(int entityId) {
        this.dataTracker.set(BEAM_TARGET_ID, entityId);
    }

    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        if (BEAM_TARGET_ID.equals(data)) {
            this.beamTicks = 0;
            this.cachedBeamTarget = null;
        }

    }

    public float getBeamProgress(float tickDelta) {
        return ((float) this.beamTicks + tickDelta) / (float) this.getWarmupTime();
    }

    public int getPower() {
        return this.dataTracker.get(POWER);
    }

    private void setPower(int power) {
        this.dataTracker.set(POWER, power);
    }

    public boolean canTakeDamage() {
        return !this.isSitting() && super.canTakeDamage();
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.isAlive() && this.isTamed()) {
            if (this.getHealth() < this.getMaxHealth() / 2) {
                this.setSitting(true);
                if (this.getPower() > 0) {
                    this.setPower(0);
                    this.playSound(Sounds.ENTITY_GENERIC_POWER_DOWN, 1.0F, 1.0F);
                    this.showEmoteParticle(false);
                }
            }
            if (this.getHealth() < this.getMaxHealth() && this.isSitting() && random.nextInt(20) == 0) {
                this.heal(1);
                this.showEmoteParticle(true);
            }

            if (this.hasBeamTarget()) {
                if (this.beamTicks < this.getWarmupTime()) {
                    ++this.beamTicks;
                }

                LivingEntity livingEntity = this.getBeamTarget();
                if (livingEntity != null) {
                    this.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
                    this.getLookControl().tick();
                    double progress = this.getBeamProgress(0.0F);
                    double targetX = livingEntity.getX() - this.getX();
                    double targetY = livingEntity.getBodyY(0.5D) - this.getEyeY();
                    double targetZ = livingEntity.getZ() - this.getZ();
                    double sqrt = Math.sqrt(targetX * targetX + targetY * targetY + targetZ * targetZ);
                    targetX /= sqrt;
                    targetY /= sqrt;
                    targetZ /= sqrt;
                    double d = this.random.nextDouble();

                    while (d < sqrt) {
                        d += 1.8D - progress + this.random.nextDouble() * (1.7D - progress);
                        this.world.addParticle(new DustParticleEffect(new Vec3f(Vec3d.unpackRgb(4897023)), 1.0F), this.getX() + targetX * d, this.getEyeY() + targetY * d, this.getZ() + targetZ * d, 0.0D, 0.0D, 0.0D);
                    }
                }
            }
        }
    }

    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getAttacker();
            if (!this.world.isClient) {
                this.setSitting(false);
            }

            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof PersistentProjectileEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.damage(source, amount);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (this.world.isClient) {
            boolean bl = this.isOwner(player) || this.isTamed();
            return bl ? ActionResult.CONSUME : ActionResult.PASS;
        } else {
            if (this.isTamed()) {
                if (itemStack.isOf(Items.REDSTONE) && this.getPower() == 0) {
                    if (!player.getAbilities().creativeMode) {
                        itemStack.decrement(1);
                    }
                    if (this.getHealth() < this.getMaxHealth()) {
                        this.setHealth(this.getMaxHealth());
                        this.world.sendEntityStatus(this, (byte) 7);
                    }
                    this.setPower(1);
                    return ActionResult.SUCCESS;
                } else if (itemStack.isOf(ModItems.POWER_SHARD) && this.getPower() == 1) {
                    if (!player.getAbilities().creativeMode) {
                        itemStack.decrement(1);
                    }
                    if (this.getHealth() < this.getMaxHealth()) {
                        this.setHealth(this.getMaxHealth());
                        this.world.sendEntityStatus(this, (byte) 7);
                    }
                    this.setPower(2);
                    return ActionResult.SUCCESS;
                }

                ActionResult actionResult = super.interactMob(player, hand);
                if ((!actionResult.isAccepted() || this.isBaby()) && this.isOwner(player)) {
                    this.setSitting(!this.isSitting());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget(null);
                    return ActionResult.SUCCESS;
                }
                return actionResult;
            }
            return super.interactMob(player, hand);
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        if (!this.isSitting())
            this.playSound(Sounds.ENTITY_MECHAKOOPA_STEP, 0.15F, 1.0F);
    }

    @SuppressWarnings("unused")
    public static boolean canMechakoopaSpawn(EntityType<MechakoopaEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        return HostileEntity.isSpawnDark(world, blockPos, random);
    }

    static class MeleeAttackGoal extends net.minecraft.entity.ai.goal.MeleeAttackGoal {
        private final MechakoopaEntity mechakoopaEntity;

        public MeleeAttackGoal(MechakoopaEntity mechakoopaEntity, double speed, boolean pauseWhenMobIdle) {
            super(mechakoopaEntity, speed, pauseWhenMobIdle);
            this.mechakoopaEntity = mechakoopaEntity;
        }

        @Override
        public boolean canStart() {
            return super.canStart() && mechakoopaEntity.getPower() == 0;
        }

        @Override
        public boolean shouldContinue() {
            return super.shouldContinue() && mechakoopaEntity.getPower() == 0;
        }
    }

    static class BeamAttackGoal extends Goal {
        private final MechakoopaEntity mechakoopaEntity;
        private int beamTicks;

        public BeamAttackGoal(MechakoopaEntity mechakoopaEntity) {
            this.mechakoopaEntity = mechakoopaEntity;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        public boolean canStart() {
            LivingEntity livingEntity = this.mechakoopaEntity.getTarget();
            return livingEntity != null && livingEntity.isAlive() && mechakoopaEntity.getPower() == 2;
        }

        public boolean shouldContinue() {
            return super.shouldContinue() && (this.mechakoopaEntity.getTarget() != null && mechakoopaEntity.getPower() == 2);
        }

        public void start() {
            this.beamTicks = -10;
            this.mechakoopaEntity.setAttacking(true);
            this.mechakoopaEntity.velocityDirty = true;
        }

        public void stop() {
            this.mechakoopaEntity.setAttacking(false);
            this.mechakoopaEntity.setBeamTarget(0);
            this.mechakoopaEntity.setTarget(null);
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingEntity = this.mechakoopaEntity.getTarget();
            if (livingEntity != null) {
                if (!this.mechakoopaEntity.canSee(livingEntity)) {
                    this.mechakoopaEntity.setTarget(null);
                } else {
                    ++this.beamTicks;
                    if (this.beamTicks == 0) {
                        this.mechakoopaEntity.setBeamTarget(livingEntity.getId());
                    } else if (this.beamTicks >= this.mechakoopaEntity.getWarmupTime()) {
                        livingEntity.damage(DamageSource.magic(this.mechakoopaEntity, this.mechakoopaEntity), 15.0F);
                        this.mechakoopaEntity.playSound(Sounds.ENTITY_MECHAKOOPA_ZAP, 1.0F, 1.0F);
                        this.mechakoopaEntity.setBeamTarget(0);
                        this.beamTicks = -10;
                    }
                    if (this.beamTicks == 10)
                        this.mechakoopaEntity.playSound(Sounds.ENTITY_MECHAKOOPA_CHARGE, 1.0F, 1.0F);

                    super.tick();
                }
            }
        }
    }

    static class MissileAttackGoal extends ProjectileAttackGoal {
        private final MechakoopaEntity mechakoopaEntity;

        public MissileAttackGoal(RangedAttackMob rangedAttackMob, double mobSpeed, int interval, float maxRange) {
            super(rangedAttackMob, mobSpeed, interval, maxRange);
            this.mechakoopaEntity = (MechakoopaEntity) rangedAttackMob;
        }

        public boolean canStart() {
            return super.canStart() && this.mechakoopaEntity.getPower() == 1;
        }

        public void stop() {
            super.stop();
            this.mechakoopaEntity.setAttacking(false);
        }

        public void start() {
            super.start();
            this.mechakoopaEntity.setAttacking(true);
        }
    }
}
