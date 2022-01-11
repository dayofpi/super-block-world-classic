package com.dayofpi.super_block_world.common.entity.mob;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Random;

@SuppressWarnings("unused")
public class StingbyEntity extends EnemyEntity implements Flutterer {
    public StingbyEntity(EntityType<? extends EnemyEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 20, true);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return EnemyEntity.createEnemyAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D);
    }

    public static boolean canSpawn(EntityType<StingbyEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(BlockTags.DIRT) && world.getBaseLightLevel(pos, 0) > 8;
    }

    public boolean tryAttack(Entity target) {
        this.playSound(SoundInit.ENTITY_STINGBY_STING, 1.0F, getSoundPitch());
        return super.tryAttack(target);
    }

    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.ENTITY_STINGBY_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundInit.ENTITY_STINGBY_DEATH;
    }

    protected void initGoals() {
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, false));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true, true));
        this.goalSelector.add(5, new StingbyWanderGoal());
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(false);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    @Override
    public int getLimitPerChunk() {
        return 3;
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        if (this.getTarget() == null && target instanceof PlayerEntity) {
            this.playSound(SoundInit.ENTITY_ENEMY_SPOT, this.getSoundVolume(), this.getSoundPitch());
        }
        super.setTarget(target);
    }

    public void playAmbientSound() {
        SoundEvent soundEvent = SoundInit.ENTITY_STINGBY_AMBIENT;
        this.playSound(soundEvent, 0.3F, this.getSoundPitch());
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.ARTHROPOD;
    }

    @Override
    public boolean isInAir() {
        return !this.onGround;
    }

    class StingbyWanderGoal extends Goal {
        StingbyWanderGoal() {
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean canStart() {
            return StingbyEntity.this.navigation.isIdle() && StingbyEntity.this.random.nextInt(10) == 0;
        }

        public boolean shouldContinue() {
            return StingbyEntity.this.navigation.isFollowingPath();
        }

        public void start() {
            Vec3d vec3d = this.getRandomLocation();
            if (vec3d != null) {
                StingbyEntity.this.navigation.startMovingAlong(StingbyEntity.this.navigation.findPathTo(new BlockPos(vec3d), 1), 0.4D);
            }
        }

        @Nullable
        private Vec3d getRandomLocation() {
            Vec3d vec3d4 = AboveGroundTargeting.find(StingbyEntity.this, 10, 4, StingbyEntity.this.getX(), StingbyEntity.this.getZ(), 1.5707964F, 2, 2);
            return vec3d4 != null ? vec3d4 : NoPenaltySolidTargeting.find(StingbyEntity.this, 8, 4, -2, StingbyEntity.this.getX(), StingbyEntity.this.getZ(), 1.5707963705062866D);
        }
    }
}
