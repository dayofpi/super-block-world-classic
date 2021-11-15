package com.dayofpi.sbw_main.entity.types.mobs;

import com.dayofpi.sbw_main.ModSounds;
import com.dayofpi.sbw_main.entity.types.bases.EnemyEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;

public class ParatroopaEntity extends KoopaEntity {
    public ParatroopaEntity(EntityType<? extends EnemyEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, false);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return KoopaEntity.createAttributes().add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5D);
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
        if (random.nextFloat() < 0.06F)
            this.playSound(ModSounds.ENTITY_GOOMBA_FLUTTER, 1.0F, this.getSoundPitch() * 0.8F);
    }
}
