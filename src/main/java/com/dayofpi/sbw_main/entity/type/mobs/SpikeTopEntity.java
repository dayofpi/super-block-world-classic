package com.dayofpi.sbw_main.entity.type.mobs;

import com.dayofpi.sbw_main.entity.type.bases.AbstractBuzzy;
import com.dayofpi.sbw_main.misc.ModDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SpiderNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SpikeTopEntity extends AbstractBuzzy {
    private static final TrackedData<Byte> SPIKE_TOP_FLAGS;

    static {
        SPIKE_TOP_FLAGS = DataTracker.registerData(SpikeTopEntity.class, TrackedDataHandlerRegistry.BYTE);
    }

    public SpikeTopEntity(EntityType<? extends AbstractBuzzy> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return AbstractBuzzy.createAttributes()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SPIKE_TOP_FLAGS, (byte) 0);
    }

    public void initGoals() {
        super.initGoals();
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, false));
        this.targetSelector.add(2, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    protected EntityNavigation createNavigation(World world) {
        return new SpiderNavigation(this, world);
    }

    public void tick() {
        super.tick();
        if (!this.world.isClient) {
            this.setClimbingWall(this.horizontalCollision);
        }
    }

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

    public void pushAwayFrom(Entity entity) {
        if (entity.getY() > this.getY() && !this.isUpsideDown() && entity.fallDistance > 0 && entity.getBlockX() == this.getBlockX() && !(entity instanceof AbstractBuzzy)) {
            boolean damaged = entity.damage(ModDamageSource.spikyMob(this), entity.fallDistance);
            if (damaged) {
                this.playSound(SoundEvents.ENCHANT_THORNS_HIT, 1.0F, getSoundPitch());
            }
            entity.damage(ModDamageSource.spikyMob(this), entity.fallDistance * 2);
        } else super.pushAwayFrom(entity);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
}
