package com.dayofpi.super_block_world.main.common.entity.mob;

import com.dayofpi.super_block_world.client.sound.ModSounds;
import com.dayofpi.super_block_world.main.common.entity.EnemyEntity;
import com.dayofpi.super_block_world.main.common.entity.goal.FuzzyWanderGoal;
import com.dayofpi.super_block_world.main.registry.misc.EntityRegistry;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.Random;

public class FuzzyEntity extends EnemyEntity implements IAnimatable {
    AnimationFactory animationFactory = new AnimationFactory(this);
    public FuzzyEntity(EntityType<? extends FuzzyEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.moveControl = new FlightMoveControl(this, 10, false);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5D);
    }

    public static boolean canSpawn(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return isSpawnDark((ServerWorldAccess) world, pos, random);
    }

    public void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(5, new FuzzyWanderGoal(this));
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }

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

    public void onKilledOther(ServerWorld world, LivingEntity other) {
        super.onKilledOther(world, other);
        FuzzyEntity fuzzyEntity = EntityRegistry.FUZZY.create(world);
        if (fuzzyEntity != null) {
            fuzzyEntity.refreshPositionAndAngles(other.getX(), other.getY(), other.getZ(), other.getYaw(), other.getPitch());
            if (this.hasCustomName()) {
                fuzzyEntity.setCustomName(this.getCustomName());
            }
            world.spawnEntity(fuzzyEntity);
            fuzzyEntity.playSound(SoundEvents.ENTITY_SLIME_SQUISH, 1.0F, 0.9F);
        }
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    public void tick() {
        if (this.world.isClient && random.nextInt(20) == 0) {
            Direction direction = Direction.random(random);
            double d = direction.getOffsetX() == 0 ? random.nextDouble() : (double)direction.getOffsetX() * 0.1D;
            double e = direction.getOffsetY() == 0 ? random.nextDouble() : (double)direction.getOffsetY() * 0.1D;
            double f = direction.getOffsetZ() == 0 ? random.nextDouble() : (double)direction.getOffsetZ() * 0.1D;
            world.addParticle(ParticleTypes.SQUID_INK, this.getX() + d, this.getY() + e, this.getZ() + f, 0.0D, 0.0D, 0.0D);
        }
        if (this.isAlive()) {
            List<ItemEntity> list = this.getWorld().getEntitiesByClass(ItemEntity.class, this.getBoundingBox(), EntityPredicates.VALID_ENTITY);
            if (!list.isEmpty()) {
                this.playSound(ModSounds.ENTITY_FUZZY_BREAK, 1.0F, 1.0F);
                list.get(0).discard();
            }
        }

        super.tick();
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        AnimationController<FuzzyEntity> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        animationData.addAnimationController(controller);
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return animationFactory;
    }
}
