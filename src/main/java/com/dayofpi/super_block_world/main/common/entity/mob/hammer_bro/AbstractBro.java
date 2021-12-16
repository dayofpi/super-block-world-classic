package com.dayofpi.super_block_world.main.common.entity.mob.hammer_bro;

import com.dayofpi.super_block_world.main.common.entity.EnemyEntity;
import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.dayofpi.super_block_world.main.common.entity.goal.BroAttackGoal;
import com.dayofpi.super_block_world.main.common.entity.goal.BroJumpGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

@SuppressWarnings("unused")
public abstract class AbstractBro extends EnemyEntity implements RangedAttackMob {

    protected AbstractBro(EntityType<? extends EnemyEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(1, new BroJumpGoal(this, 0.6F));
        this.goalSelector.add(2, new BroAttackGoal(this, 1.0F, 20, 30, 10.0F));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this ));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static boolean canSpawn(EntityType<? extends AbstractBro> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        boolean allowedBlocks = world.getBlockState(pos.down()).isOf(BlockRegistry.GRITZY_SAND) || world.getBlockState(pos.down()).isOf(BlockRegistry.TOADSTONE);
        return allowedBlocks && isSpawnDark((ServerWorldAccess) world, pos, random) || isSpawnDark((ServerWorldAccess) world, pos, random) && !world.isSkyVisible(pos);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D);
    }

    public void tickMovement() {
        super.tickMovement();
        this.tickHandSwing();
    }
}
