package com.dayofpi.super_block_world.common.entities.abst;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.entities.goals.BroAttackGoal;
import com.dayofpi.super_block_world.common.entities.goals.BroJumpGoal;
import com.dayofpi.super_block_world.registry.main.BlockInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

@SuppressWarnings("unused")
public abstract class AbstractBro extends AbstractEnemy implements RangedAttackMob {

    protected AbstractBro(EntityType<? extends AbstractEnemy> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(1, new BroJumpGoal(this, 0.6F));
        this.goalSelector.add(2, new BroAttackGoal(this, 1.0F, 20, 40, 10.0F));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this ));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static boolean canSpawn(EntityType<? extends AbstractBro> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        boolean allowedBlocks = world.getBlockState(pos.down()).isOf(BlockInit.GRITZY_SAND) || world.getBlockState(pos.down()).isOf(BlockInit.SNOWY_SHERBET_SOIL);
        return allowedBlocks && isSpawnDark((ServerWorldAccess) world, pos, random) && pos.getY() > -4 || isSpawnDark((ServerWorldAccess) world, pos, random) && !world.isSkyVisible(pos) && pos.getY() > -4;
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D);
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.ENTITY_HAMMER_BRO_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundInit.ENTITY_HAMMER_BRO_DEATH;
    }

    public void tickMovement() {
        super.tickMovement();
        this.tickHandSwing();
    }
}
