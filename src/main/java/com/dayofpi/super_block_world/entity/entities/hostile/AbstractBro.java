package com.dayofpi.super_block_world.entity.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.entity.entities.goals.BroAttackGoal;
import com.dayofpi.super_block_world.entity.entities.goals.BroJumpGoal;
import com.dayofpi.super_block_world.entity.entities.passive.MailtoadEntity;
import com.dayofpi.super_block_world.entity.entities.passive.ToadEntity;
import com.dayofpi.super_block_world.block.ModBlocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public abstract class AbstractBro extends HostileEntity implements RangedAttackMob {
    protected AbstractBro(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 10;
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new BroJumpGoal(this, 0.6F));
        this.goalSelector.add(2, new BroAttackGoal(this, 1.0F, 20, 40, 10.0F));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(5, new LookAtEntityGoal(this, HostileEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this ));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, ToadEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, MailtoadEntity.class, true));
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<? extends AbstractBro> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        if (world.getBlockState(pos.down()).isOf(ModBlocks.CHARROCK))
            return !(world.getLightLevel(LightType.BLOCK, pos) > 0);
        return !(world.getLightLevel(LightType.BLOCK, pos) > 0) && world.isSkyVisible(pos);
    }

    public static DefaultAttributeContainer.Builder createBroAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20).add(EntityAttributes.GENERIC_ARMOR, 2.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D);
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_HAMMER_BRO_HURT;
    }

    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_HAMMER_BRO_DEATH;
    }

    public void tickMovement() {
        super.tickMovement();
        this.tickHandSwing();
    }
}
