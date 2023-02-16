package com.dayofpi.super_block_world.entity.entities.hostile;

import com.dayofpi.super_block_world.entity.ModEntities;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractPokey extends GolemEntity {
    protected AbstractPokey(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 3;
    }

    protected static boolean isThereNoLight(WorldAccess world, BlockPos pos) {
        return !(world.getLightLevel(LightType.BLOCK, pos) > 0);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0, 1.0000001E-5F));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder pokeyAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        PokeySegmentEntity pokeySegment = ModEntities.POKEY_SEGMENT.create(this.world);
        if (pokeySegment != null) {
            pokeySegment.updatePositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
            pokeySegment.setSnowy(this instanceof SnowPokeyEntity);
            world.spawnEntity(pokeySegment);
            pokeySegment.initialize(world, difficulty, spawnReason, null, null);
            this.startRiding(pokeySegment);
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }
}
