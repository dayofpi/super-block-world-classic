package com.dayofpi.super_block_world.entity.entities.hostile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SpiderNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SpikeTopEntity extends AbstractBuzzy implements Monster {
    private static final TrackedData<Byte> SPIKE_TOP_FLAGS;

    static {
        SPIKE_TOP_FLAGS = DataTracker.registerData(SpikeTopEntity.class, TrackedDataHandlerRegistry.BYTE);
    }

    public SpikeTopEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createSpikeTopAttributes() {
        return AbstractBuzzy.createBuzzyAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D).add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D);
    }

    public void initGoals() {
        super.initGoals();
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, false));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new SpiderNavigation(this, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isClient && this.isAlive()) {
            this.setClimbingWall(this.horizontalCollision);
        }
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SPIKE_TOP_FLAGS, (byte) 0);
    }

    @Override
    public boolean isClimbing() {
        return this.isClimbingWall();
    }

    public boolean isClimbingWall() {
        return (this.dataTracker.get(SPIKE_TOP_FLAGS) & 1) != 0;
    }

    public void setClimbingWall(boolean climbing) {
        byte b = this.dataTracker.get(SPIKE_TOP_FLAGS);
        if (climbing) {
            b = (byte) (b | 1);
        } else {
            b &= -2;
        }

        this.dataTracker.set(SPIKE_TOP_FLAGS, b);
    }

    @Override
    public void pushAwayFrom(Entity entity) {
        if (entity.getY() > this.getY() && !this.isUpsideDown() && entity.fallDistance > 0 && entity.getBlockX() == this.getBlockX() && !(entity instanceof AbstractBuzzy)) {
            entity.damage(DamageSource.thorns(this), entity.fallDistance * 2);
        } else super.pushAwayFrom(entity);
    }

    @Override
    protected int getDropDamageAddition() {
        return 3;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
}
