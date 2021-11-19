package com.dayofpi.sbw_main.entity.types.bases;

import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.misc.ModDamageSource;
import com.dayofpi.sbw_main.SoundList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.EscapeSunlightGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
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
        this.targetSelector.add(1, new SwimGoal(this));
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundList.buzzyHurt;
    }

    protected SoundEvent getDeathSound() {
        return SoundList.buzzyDeath;
    }

    public static boolean isSpawnBlockValid(WorldAccess world, BlockPos pos) {
        return world.getBlockState(pos).isOf(ModBlocks.VANILLATE) || world.getBlockState(pos).isOf(ModBlocks.FROSTY_VANILLATE);
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
        int i = this.computeFallDamage(fallDistance, damageMultiplier);
        if (i > 0) {
            world.getOtherEntities(this, this.getBoundingBox().expand(3, 0, 3), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR).forEach((entity) -> entity.damage(ModDamageSource.mobDrop(this), i + 1));
            this.playSound(this.getLandingSound(), this.getSoundVolume(), this.getSoundPitch());
            ((ServerWorld) world).spawnParticles(ParticleTypes.EXPLOSION, this.getX(), this.getBodyY(0.5D), this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        if (fallDistance > 0.0F) {
            this.setUpsideDown(false);
        }
    }

    protected SoundEvent getLandingSound() {
        return SoundList.buzzyLand;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        BlockPos blockPos = this.getBlockPos();
        BlockPos floor = this.getBlockPos().down();
        Box detectionRange = this.getBoundingBox().expand(0, 32, 0).offset(0, -32, 0);

        boolean blockBelow = world.getBlockState(floor).isSideSolidFullSquare(world, floor, Direction.UP);
        boolean playerBelow = world.getClosestEntity(PlayerEntity.class, TargetPredicate.createAttackable(), this, this.getX(), this.getY(), this.getZ(), detectionRange) != null;
        if (this.isOnCeiling(blockPos) && hurtTime == 0) {
            if (!playerBelow && !blockBelow) {
                this.setVelocity(this.getVelocity().multiply(1.0F, 0.0F, 1.0F));
            } else if (playerBelow && !blockBelow)
                this.playSound(this.getDropSound(), 1.0F, this.getSoundPitch());
        }

        if (this.isOnCeiling(blockPos)) {
            this.setUpsideDown(true);
        }
    }

    public int getLookPitchSpeed() {
        return 40;
    }

    private SoundEvent getDropSound() {
        return SoundList.buzzyDrop;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source instanceof ProjectileDamageSource) {
            this.playSound(this.getBlockingSound(), this.getSoundVolume(), this.getSoundPitch());
            if (!this.world.isClient) {
                ((ServerWorld) world).spawnParticles(ParticleTypes.CRIT, this.getX() + random.nextFloat() - 0.2F, this.getY() + 1.5D, this.getZ() + random.nextFloat() - 0.2F, 2, 0.0D, 0.0D, 0.0D, 0.0D);
            }
            return false;
        } else {
            return super.damage(source, amount);
        }
    }

    private SoundEvent getBlockingSound() {
        return SoundList.buzzyBlock;
    }
}