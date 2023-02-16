package com.dayofpi.super_block_world.entity.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.util.PowerUp;
import com.dayofpi.super_block_world.util.FormManager;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

@SuppressWarnings("unused")
public class StingbyEntity extends HostileEntity implements Flutterer {
    public StingbyEntity(EntityType<? extends StingbyEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 20, true);
    }

    public static DefaultAttributeContainer.Builder createStingbyAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D);
    }

    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return Sounds.ENTITY_STINGBY_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_STINGBY_HURT;
    }

    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_STINGBY_DEATH;
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.add(2, new StingbyWanderGoal());
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, false, this::shouldAttack));
    }

    private boolean shouldAttack(LivingEntity entity) {
        if (!this.canTarget(entity)) {
            return false;
        }
        if (entity instanceof PlayerEntity) {
            return !entity.getDataTracker().get(FormManager.POWER_UP).equals(PowerUp.BEE.asString());
        }
        return false;
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
            this.playSound(Sounds.ENTITY_GENERIC_SPOT, this.getSoundVolume(), this.getSoundPitch());
        }
        super.setTarget(target);
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

    public static boolean canSpawn(EntityType<StingbyEntity> entityType, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, net.minecraft.util.math.random.Random random) {
        return (world.getBlockState(pos.down()).isIn(BlockTags.DIRT) || world.getBlockState(pos.down()).isIn(BlockTags.SAND)) && world.getBaseLightLevel(pos, 0) > 8;
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
