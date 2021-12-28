package com.dayofpi.super_block_world.main.common.entity.mob.koopa;

import com.dayofpi.super_block_world.main.common.entity.EnemyEntity;
import com.dayofpi.super_block_world.client.sound.ModSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

@SuppressWarnings("unused")
public class ParatroopaEntity extends KoopaEntity {
    public ParatroopaEntity(EntityType<? extends EnemyEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, false);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return KoopaEntity.createAttributes().add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5D);
    }

    public static boolean canSpawn(EntityType<? extends KoopaEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).allowsSpawning(world, pos, type) && world.isSkyVisible(pos);
    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected boolean hasWings() {
        return true;
    }

    protected void addFlapEffects() {
        if (random.nextFloat() < 0.03F)
            this.playSound(ModSounds.ENTITY_GOOMBA_FLUTTER, 1.0F, this.getSoundPitch() * 0.8F);
    }
}
