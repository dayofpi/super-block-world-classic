package com.dayofpi.super_block_world.common.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.blocks.BrickBlock;
import com.dayofpi.super_block_world.common.blocks.ReactiveBlock;
import com.dayofpi.super_block_world.registry.ModBlocks;
import com.dayofpi.super_block_world.util.ModDamageSource;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.List;

public abstract class AbstractBuzzy extends AnimalEntity {
    private static final TrackedData<Boolean> UPSIDE_DOWN;

    static {
        UPSIDE_DOWN = DataTracker.registerData(AbstractBuzzy.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    protected AbstractBuzzy(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createBuzzyAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 10).add(EntityAttributes.GENERIC_MAX_HEALTH, 10).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.2).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.22D);
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        boolean floorValid = isSpawnBlockValid(world, pos.down());
        boolean ceilingValid = isSpawnBlockValid(world, pos.up());
        return !world.isSkyVisible(pos) && (random.nextInt(5) == 0 ? ceilingValid : floorValid);
    }

    public static boolean isSpawnBlockValid(WorldAccess world, BlockPos pos) {
        return world.getBlockState(pos).isOf(ModBlocks.VANILLATE) || world.getBlockState(pos).isOf(ModBlocks.TOPPED_VANILLATE) || world.getBlockState(pos).isOf(ModBlocks.CHARROCK);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        if (this.isUpsideDown()) {
            if (!this.world.isClient()) {
                this.doLandingEffects(fallDistance, damageMultiplier);
            }
            return false;
        } else return super.handleFallDamage(fallDistance, damageMultiplier, damageSource);
    }

    protected abstract int getDropDamageAddition();

    public void doLandingEffects(float fallDistance, float damageMultiplier) {
        int fallDamage = this.computeFallDamage(fallDistance, damageMultiplier);
        if (fallDamage > 0) {
            List<Entity> entities = world.getOtherEntities(this, this.getBoundingBox().expand(3, 0, 3), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR);
            entities.forEach((entity) -> entity.damage(ModDamageSource.stomp(this), fallDamage + this.getDropDamageAddition()));
            this.playSound(Sounds.ENTITY_BUZZY_BEETLE_IMPACT, this.getSoundVolume(), this.getSoundPitch());
            ((ServerWorld) world).spawnParticles(ParticleTypes.EXPLOSION, this.getX(), this.getBodyY(0.5D), this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        if (fallDistance > 0.0F) {
            for (BlockPos blockPos : BlockPos.iterateOutwards(this.getBlockPos().down(), 1, 0, 1)) {
                BlockState state = world.getBlockState(blockPos);
                if (world.getBlockState(blockPos).getBlock() instanceof BrickBlock) {
                    world.breakBlock(blockPos, true);
                } else if (state.getBlock() instanceof ReactiveBlock reactiveBlock) {
                    reactiveBlock.react(world, blockPos, null);
                    this.setUpsideDown(false);
                } else this.setUpsideDown(false);

            }
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        BlockPos blockPos = this.getBlockPos();

        if (this.isOnCeiling(blockPos)) {
            this.setUpsideDown(true);
            BlockPos floor = this.getBlockPos().down();
            boolean blockBelow = world.getBlockState(floor).isSideSolidFullSquare(world, floor, Direction.UP);
            Box detectionRange = this.getBoundingBox().expand(0, 32, 0).offset(0, -32, 0);
            boolean playerBelow = world.getClosestEntity(PlayerEntity.class, TargetPredicate.createAttackable(), this, this.getX(), this.getY(), this.getZ(), detectionRange) != null;

            if (!playerBelow && !blockBelow) {
                this.setVelocity(this.getVelocity().multiply(1.0F, 0.0F, 1.0F));
            } else if (playerBelow && !blockBelow)
                this.playSound(Sounds.ENTITY_SHELL_HIT, 1.0F, this.getSoundPitch());
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source instanceof ProjectileDamageSource) {
            this.playSound(Sounds.ENTITY_BUZZY_BEETLE_DEFLECT, this.getSoundVolume(), this.getSoundPitch());
            return false;
        } else {
            return super.damage(source, amount);
        }
    }

    public void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeSunlightGoal(this, 1.0D));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return 0.5F - world.getLightLevel(pos);
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isPersistent();
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ActionResult actionResult = super.interactMob(player, hand);
        if (actionResult.isAccepted()) {
            this.setPersistent();
        }
        return super.interactMob(player, hand);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(UPSIDE_DOWN, false);
    }

    public boolean isOnCeiling(BlockPos pos) {
        if (!this.isBaby()) {
            return world.getBlockState(pos.up()).isSideSolidFullSquare(world, pos, Direction.DOWN) && !this.hasVehicle();
        } else
            return world.getStatesInBox(this.getBoundingBox().offset(0.0D, 0.5D, 0.0D)).anyMatch(BlockState::isOpaque);

    }

    public boolean isUpsideDown() {
        return this.dataTracker.get(UPSIDE_DOWN);
    }

    public void setUpsideDown(boolean upsideDown) {
        this.dataTracker.set(UPSIDE_DOWN, upsideDown);
    }
}
