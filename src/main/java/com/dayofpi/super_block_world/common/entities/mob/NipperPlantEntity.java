package com.dayofpi.super_block_world.common.entities.mob;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.entities.abst.AbstractEnemy;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.JumpControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

@SuppressWarnings("unused")
public class NipperPlantEntity extends AbstractEnemy {
    private int jumpTicks;
    private int jumpDuration;
    private boolean lastOnGround;
    private int ticksUntilJump;

    public NipperPlantEntity(EntityType<? extends AbstractEnemy> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 1;
        this.jumpControl = new NipperJumpControl(this);
        this.moveControl = new NipperMoveControl(this);
        this.setSpeed(0.0D);
    }

    public void setSpeed(double speed) {
        this.getNavigation().setSpeed(speed);
        this.moveControl.moveTo(this.moveControl.getTargetX(), this.moveControl.getTargetY(), this.moveControl.getTargetZ(), speed);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 4)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35D);
    }

    public static boolean canSpawn(EntityType<NipperPlantEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(BlockTags.DIRT) && world.getBaseLightLevel(pos, 0) > 8;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.ENTITY_NIPPER_PLANT_DEATH;
    }

    public void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(4, new NipperAttackGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.goalSelector.add(6, new WanderAroundGoal(this, 0.6D));
    }

    public void tickMovement() {
        super.tickMovement();
        if (this.jumpTicks != this.jumpDuration) {
            ++this.jumpTicks;
        } else if (this.jumpDuration != 0) {
            this.jumpTicks = 0;
            this.jumpDuration = 0;
            this.setJumping(false);
        }
    }

    public void mobTick() {
        if (this.ticksUntilJump > 0) {
            --this.ticksUntilJump;
        }

        if (this.onGround) {
            if (!this.lastOnGround) {
                this.setJumping(false);
                this.scheduleJump();
            }

            if (this.ticksUntilJump == 0) {
                LivingEntity livingEntity = this.getTarget();
                if (livingEntity != null && this.squaredDistanceTo(livingEntity) < 16.0D) {
                    this.lookTowards(livingEntity.getX(), livingEntity.getZ());
                    this.moveControl.moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), this.moveControl.getSpeed());
                    this.startJump();
                    this.lastOnGround = true;
                }
            }

            NipperJumpControl nipperJumpControl = (NipperJumpControl) this.jumpControl;
            if (nipperJumpControl.isInactive()) {
                if (this.moveControl.isMoving() && this.ticksUntilJump == 0) {
                    Path path = this.navigation.getCurrentPath();
                    Vec3d vec3d = new Vec3d(this.moveControl.getTargetX(), this.moveControl.getTargetY(), this.moveControl.getTargetZ());
                    if (path != null && !path.isFinished()) {
                        vec3d = path.getNodePosition(this);
                    }

                    this.lookTowards(vec3d.x, vec3d.z);
                    this.startJump();
                }
            } else if (!nipperJumpControl.method_27313()) {
                this.method_6611();
            }
        }

        this.lastOnGround = this.onGround;
    }

    private void scheduleJump() {
        this.doScheduleJump();
        this.method_6621();
    }

    private void lookTowards(double x, double z) {
        this.setYaw((float) (MathHelper.atan2(z - this.getZ(), x - this.getX()) * 57.2957763671875D) - 90.0F);
    }

    public void startJump() {
        this.setJumping(true);
        this.jumpDuration = 10;
        this.jumpTicks = 0;
    }

    private void method_6611() {
        ((NipperJumpControl) this.jumpControl).method_27311(true);
    }

    private void doScheduleJump() {
        if (this.moveControl.getSpeed() < 2.2D) {
            this.ticksUntilJump = 10;
        } else {
            this.ticksUntilJump = 1;
        }
    }

    private void method_6621() {
        ((NipperJumpControl) this.jumpControl).method_27311(false);
    }

    protected float getJumpVelocity() {
        if (!this.horizontalCollision && (!this.moveControl.isMoving() || !(this.moveControl.getTargetY() > this.getY() + 0.5D))) {
            Path path = this.navigation.getCurrentPath();
            if (path != null && !path.isFinished()) {
                Vec3d vec3d = path.getNodePosition(this);
                if (vec3d.y > this.getY() + 0.5D) {
                    return 0.5F;
                }
            }

            return this.moveControl.getSpeed() <= 0.6D ? 0.2F : 0.3F;
        } else {
            return 0.5F;
        }
    }

    protected void jump() {
        super.jump();
        double d = this.moveControl.getSpeed();
        if (d > 0.0D) {
            double e = this.getVelocity().horizontalLengthSquared();
            if (e < 0.01D) {
                this.updateVelocity(0.1F, new Vec3d(0.0D, 0.0D, 1.0D));
            }
        }

        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, (byte) 1);
        }

    }

    public boolean shouldSpawnSprintingParticles() {
        return false;
    }

    static class NipperAttackGoal extends MeleeAttackGoal {
        public NipperAttackGoal(NipperPlantEntity nipperPlant) {
            super(nipperPlant, 1.4D, true);
        }

        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return 4.0F + entity.getWidth();
        }
    }

    public static class NipperJumpControl extends JumpControl {
        private final NipperPlantEntity plantEntity;
        private boolean field_24091;

        public NipperJumpControl(NipperPlantEntity nipperPlant) {
            super(nipperPlant);
            this.plantEntity = nipperPlant;
        }

        public boolean isInactive() {
            return !this.active;
        }

        public boolean method_27313() {
            return this.field_24091;
        }

        public void method_27311(boolean bl) {
            this.field_24091 = bl;
        }

        public void tick() {
            if (this.active) {
                this.plantEntity.startJump();
                this.active = false;
            }

        }
    }

    private static class NipperMoveControl extends MoveControl {
        private final NipperPlantEntity nipperPlant;
        private double nipperSpeed;

        public NipperMoveControl(NipperPlantEntity owner) {
            super(owner);
            this.nipperPlant = owner;
        }

        public void moveTo(double x, double y, double z, double speed) {
            if (this.nipperPlant.isTouchingWater()) {
                speed = 1.5D;
            }

            super.moveTo(x, y, z, speed);
            if (speed > 0.0D) {
                this.nipperSpeed = speed;
            }

        }

        public void tick() {
            if (this.nipperPlant.onGround && !this.nipperPlant.jumping && ((NipperJumpControl) this.nipperPlant.jumpControl).isInactive()) {
                this.nipperPlant.setSpeed(0.0D);
            } else if (this.isMoving()) {
                this.nipperPlant.setSpeed(this.nipperSpeed);
            }
            super.tick();
        }
    }
}
