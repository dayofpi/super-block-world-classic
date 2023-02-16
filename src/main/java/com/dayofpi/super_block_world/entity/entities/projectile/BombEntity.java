package com.dayofpi.super_block_world.entity.entities.projectile;

import com.dayofpi.super_block_world.entity.ModEntities;
import com.dayofpi.super_block_world.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class BombEntity extends ThrownItemEntity {
    public BombEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public BombEntity(World world, LivingEntity owner) {
        super(ModEntities.BOMB, owner, world);
    }

    public BombEntity(World world, double x, double y, double z) {
        super(ModEntities.BOMB, x, y, z, world);
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.isClient()) {
            world.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.9D, this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.goBoom();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (this.distanceTo(entity) <= 1)
            this.goBoom();
    }

    private void goBoom() {
        boolean bl = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) || this.getOwner() instanceof PlayerEntity;
        world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 1.8F, bl ? Explosion.DestructionType.BREAK : Explosion.DestructionType.NONE);
        this.discard();
    }

    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            this.scheduleVelocityUpdate();
            Entity entity = source.getAttacker();
            if (entity != null) {
                if (!this.world.isClient) {
                    Vec3d vec3d = entity.getRotationVector();
                    this.setVelocity(vec3d);
                    this.setOwner(entity);
                }
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.BOMB;
    }

    protected float getGravity() {
        return 0.05F;
    }
}
