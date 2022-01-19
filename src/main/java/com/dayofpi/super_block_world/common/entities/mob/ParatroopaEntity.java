package com.dayofpi.super_block_world.common.entities.mob;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.entities.abst.AbstractEnemy;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

@SuppressWarnings("unused")
public class ParatroopaEntity extends KoopaEntity {
    public ParatroopaEntity(EntityType<? extends AbstractEnemy> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, false);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return KoopaEntity.createAttributes().add(EntityAttributes.GENERIC_FLYING_SPEED, 0.7D);
    }

    public static boolean canSpawn(EntityType<? extends KoopaEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return KoopaEntity.canSpawn(type, world, spawnReason, pos, random) && world.isSkyVisible(pos);
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

    @Override
    public void tickMovement() {
        super.tickMovement();
        Vec3d vec3d = this.getVelocity();
        this.airStrafingSpeed = this.getSaddledSpeed();
        if (!this.onGround && vec3d.y < 0.0D) {
            this.setVelocity(vec3d.multiply(1.0D, 0.8D, 1.0D));
        }
    }

    protected boolean hasWings() {
        return true;
    }

    protected void addFlapEffects() {
        if (random.nextFloat() < 0.03F)
            this.playSound(SoundInit.ENTITY_MISC_FLUTTER, 1.0F, this.getSoundPitch() * 0.8F);
    }
}
