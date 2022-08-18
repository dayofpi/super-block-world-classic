package com.dayofpi.super_block_world.common.entities.projectile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class BoomerangEntity extends ThrownEntity {
    public BoomerangEntity(EntityType<? extends ThrownEntity> entityType, World world) {
        super(entityType, world);
    }

    public BoomerangEntity(World world, LivingEntity owner) {
        super(ModEntities.BOOMERANG, owner, world);
    }

    public BoomerangEntity(World world, double x, double y, double z) {
        super(ModEntities.BOOMERANG, x, y, z, world);
    }

    @Override
    protected void initDataTracker() {
    }

    private ParticleEffect getParticleParameters() {
        return ParticleTypes.POOF;
    }

    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status != 3)
            return;
        ParticleEffect particleEffect = this.getParticleParameters();
        for (int i = 0; i < 4; ++i) {
            this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age >= 50 && !this.world.isClient) {
            this.world.sendEntityStatus(this, (byte) 3);
            this.discard();
        }
        if (this.getOwner() != null && this.age >= 15) {
            Vec3d pos = this.getOwner().getPos();
            Vec3d vec3d = new Vec3d(pos.getX() - this.getX(), (pos.getY() + 0.5) - this.getY(), pos.getZ() - this.getZ());
            this.setVelocity(vec3d.normalize());
            List<Entity> entities = world.getOtherEntities(null, this.getBoundingBox().expand(0.5));
            if (!this.world.isClient && !entities.isEmpty()) {
                for (Entity entity : entities) {
                    if (entity == this.getOwner())
                        this.discard();
                    else if (entity instanceof ItemEntity && entities.indexOf(entity) < 3) {
                        entity.setVelocity(vec3d.multiply(0.2));
                    }
                }
            }
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entity != this.getOwner()) {
            this.playSound(Sounds.ENTITY_PROJECTILE_HIT, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 5);
        }
    }

    @Override
    protected float getGravity() {
        return 0.0F;
    }
}
