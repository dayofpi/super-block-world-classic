package com.dayofpi.super_block_world.common.entities.projectile;

import com.dayofpi.super_block_world.registry.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;

public class HomingFlameEntity extends ThrownEntity {
    public HomingFlameEntity(EntityType<? extends ThrownEntity> entityType, World world) {
        super(entityType, world);
    }

    public HomingFlameEntity(World world, LivingEntity owner) {
        super(ModEntities.HOMING_FLAME, owner, world);
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entity != this.getOwner()) {
            this.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
            entity.damage(DamageSource.magic(this, this.getOwner()), 5);
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age >= 200 && !this.world.isClient) {
            this.world.sendEntityStatus(this, (byte) 3);
            this.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
            this.discard();
        }
        if (this.getOwner() instanceof MobEntity) {
            Optional<LivingEntity> target = ((MobEntity) this.getOwner()).getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
            if (target.isEmpty())
                return;
            Vec3d pos = target.get().getPos();
            Vec3d vec3d = new Vec3d(pos.getX() - this.getX(), (pos.getY() + 0.5) - this.getY(), pos.getZ() - this.getZ());
            this.setVelocity(vec3d.normalize().multiply(0.35));
        }
    }

    protected float getGravity() {
        return 0.0F;
    }

    @Override
    protected void initDataTracker() {

    }
}
