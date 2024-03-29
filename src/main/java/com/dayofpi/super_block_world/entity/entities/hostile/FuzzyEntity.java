package com.dayofpi.super_block_world.entity.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.entity.goals.FuzzyWanderGoal;
import com.dayofpi.super_block_world.entity.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.List;

public class FuzzyEntity extends HostileEntity {
    public final AnimationState idlingAnimationState = new AnimationState();

    public FuzzyEntity(EntityType<? extends FuzzyEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 3;
        this.moveControl = new FlightMoveControl(this, 10, false);
    }

    public static DefaultAttributeContainer.Builder createFuzzyAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5D);
    }

    @SuppressWarnings("unused")
    public static boolean canFuzzySpawn(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return isSpawnDark((ServerWorldAccess) world, pos, random);
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new FuzzyWanderGoal(this));
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    @Override
    public void pushAwayFrom(Entity entity) {
        super.pushAwayFrom(entity);
        if (!(entity instanceof FuzzyEntity)) {
            entity.damage(DamageSource.mob(this), 6);
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.setAttacker(this);
            }
        }
    }

    @Override
    public boolean onKilledOther(ServerWorld world, LivingEntity other) {
        boolean bl = super.onKilledOther(world, other);
        FuzzyEntity fuzzyEntity = ModEntities.FUZZY.create(world);
        if (fuzzyEntity != null) {
            fuzzyEntity.refreshPositionAndAngles(other.getX(), other.getY(), other.getZ(), other.getYaw(), other.getPitch());
            if (this.hasCustomName()) {
                fuzzyEntity.setCustomName(this.getCustomName());
            }
            world.spawnEntity(fuzzyEntity);
            fuzzyEntity.playSound(SoundEvents.ENTITY_SLIME_SQUISH, 1.0F, other.getSoundPitch());
        }
        return bl;
    }

    @Override
    public void tick() {
        if (this.world.isClient) {
            this.idlingAnimationState.startIfNotRunning(this.age);
            if (random.nextInt(20) == 0) {
                Direction direction = Direction.random(random);
                double x = direction.getOffsetX() == 0 ? random.nextDouble() : (double) direction.getOffsetX() * 0.1D;
                double y = direction.getOffsetY() == 0 ? random.nextDouble() : (double) direction.getOffsetY() * 0.1D;
                double z = direction.getOffsetZ() == 0 ? random.nextDouble() : (double) direction.getOffsetZ() * 0.1D;
                world.addParticle(ParticleTypes.SQUID_INK, this.getX() + x, this.getY() + y, this.getZ() + z, 0.0D, 0.0D, 0.0D);
            }
        }
        if (this.isAlive()) {
            List<ItemEntity> list = this.getWorld().getEntitiesByClass(ItemEntity.class, this.getBoundingBox(), EntityPredicates.VALID_ENTITY);
            if (!list.isEmpty()) {
                this.playSound(Sounds.ENTITY_FUZZY_BREAK_ITEM, 1.0F, this.getSoundPitch());
                list.get(0).discard();
            }
        }

        super.tick();
    }
}
