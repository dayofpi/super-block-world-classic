package com.dayofpi.sbw_main.entity.types.bases;

import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.entity.goals.BroAttackGoal;
import com.dayofpi.sbw_main.entity.goals.BroJumpGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public abstract class AbstractBro extends EnemyEntity implements RangedAttackMob {

    protected AbstractBro(EntityType<? extends EnemyEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(1, new BroJumpGoal(this, 0.6F));
        this.goalSelector.add(2, new BroAttackGoal(this, 1.0F, 10, 30, 10.0F));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this ));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static boolean canSpawn(EntityType<? extends AbstractBro> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        boolean allowedBlocks = world.getBlockState(pos.down()).isIn(BlockTags.SAND) || world.getBlockState(pos.down()).isOf(ModBlocks.TOADSTONE);
        return allowedBlocks && world.getBiome(pos).getTemperature() >= 0.9 && !(world.getLightLevel(LightType.BLOCK, pos) > 0) || isSpawnDark((ServerWorldAccess) world, pos, random) && !world.isSkyVisible(pos);
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