package com.dayofpi.super_block_world.main.common.entity.mob.buzzy;

import com.dayofpi.super_block_world.main.common.block.reactive.ReactiveBlock;
import com.dayofpi.super_block_world.main.common.entity.CeilingEntity;
import com.dayofpi.super_block_world.main.registry.general.EntityRegistry;
import com.dayofpi.super_block_world.main.registry.general.TagRegistry;
import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.util.entity.ModEntityDamageSource;
import com.dayofpi.super_block_world.client.sound.ModSounds;
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
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public abstract class AbstractBuzzy extends CeilingEntity {

    public AbstractBuzzy(EntityType<? extends AbstractBuzzy> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 10)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.2)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.22D);
    }

    public void initGoals() {
        this.goalSelector.add(4, new EscapeSunlightGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.targetSelector.add(1, new SwimGoal(this));
    }

    public static boolean canSpawn(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        boolean floorValid = isSpawnBlockValid(world, pos.down());
        boolean ceilingValid = isSpawnBlockValid(world, pos.up());
        return !world.isSkyVisible(pos) && (random.nextInt(5) == 0 ? ceilingValid : floorValid);
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ENTITY_BUZZY_HURT;
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.ENTITY_BUZZY_DEATH;
    }

    public static boolean isSpawnBlockValid(WorldAccess world, BlockPos pos) {
        return world.getBlockState(pos).isOf(BlockRegistry.VANILLATE) || world.getBlockState(pos).isOf(BlockRegistry.TOPPED_VANILLATE) || world.getBlockState(pos).isOf(BlockRegistry.FROSTY_VANILLATE);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        if (this.isUpsideDown()) {
            // Don't take fall damage if upside down
            if (!this.world.isClient()) {
                this.doLandingEffects(fallDistance, damageMultiplier);
            }
            return false;
        } else return super.handleFallDamage(fallDistance, damageMultiplier, damageSource);
    }

    public void doLandingEffects(float fallDistance, float damageMultiplier) {
        int fallDamage = this.computeFallDamage(fallDistance, damageMultiplier);
        if (fallDamage > 0) {
            List<Entity> entities = world.getOtherEntities(this, this.getBoundingBox().expand(3, 0, 3), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR);
            if (this.getType() == EntityRegistry.SPIKE_TOP)
                entities.forEach((entity) -> entity.damage(DamageSource.thorns(this), fallDamage + 3));
            else
                entities.forEach((entity) -> entity.damage(ModEntityDamageSource.mobDrop(this), fallDamage + 1));
            this.playSound(ModSounds.ENTITY_BUZZY_IMPACT, this.getSoundVolume(), this.getSoundPitch());
            ((ServerWorld) world).spawnParticles(ParticleTypes.EXPLOSION, this.getX(), this.getBodyY(0.5D), this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        if (fallDistance > 0.0F) {
            for (BlockPos blockPos : BlockPos.iterateOutwards(this.getBlockPos().down(), 1, 0, 1)) {
                BlockState state = world.getBlockState(blockPos);
                if (world.getBlockState(blockPos).isIn(TagRegistry.BRICKS)) {
                    world.breakBlock(blockPos, true);
                } else if (state.getBlock() instanceof ReactiveBlock reactiveBlock) {
                    reactiveBlock.activate(state, world, blockPos);
                    this.setUpsideDown(false);
                } else this.setUpsideDown(false);

            }
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        BlockPos blockPos = this.getBlockPos();

        if (this.isOnCeiling(blockPos) && hurtTime == 0) {
            this.setUpsideDown(true);
            BlockPos floor = this.getBlockPos().down();
            boolean blockBelow = world.getBlockState(floor).isSideSolidFullSquare(world, floor, Direction.UP);
            Box detectionRange = this.getBoundingBox().expand(0, 32, 0).offset(0, -32, 0);
            boolean playerBelow = world.getClosestEntity(PlayerEntity.class, TargetPredicate.createAttackable(), this, this.getX(), this.getY(), this.getZ(), detectionRange) != null;

            if (!playerBelow && !blockBelow) {
                this.setVelocity(this.getVelocity().multiply(1.0F, 0.0F, 1.0F));
            } else if (playerBelow && !blockBelow)
                this.playSound(ModSounds.ENTITY_BUZZY_DROP, 1.0F, this.getSoundPitch());
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source instanceof ProjectileDamageSource) {
            this.playSound(ModSounds.ENTITY_BUZZY_BLOCK, this.getSoundVolume(), this.getSoundPitch());
            if (!this.world.isClient) {
                ((ServerWorld) world).spawnParticles(ParticleTypes.CRIT, this.getX() + random.nextFloat() - 0.2F, this.getY() + 1.5D, this.getZ() + random.nextFloat() - 0.2F, 2, 0.0D, 0.0D, 0.0D, 0.0D);
            }
            return false;
        } else {
            return super.damage(source, amount);
        }
    }
}