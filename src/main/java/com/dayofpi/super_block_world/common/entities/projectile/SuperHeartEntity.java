package com.dayofpi.super_block_world.common.entities.projectile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.hostile.BobOmbEntity;
import com.dayofpi.super_block_world.common.entities.hostile.GoombaEntity;
import com.dayofpi.super_block_world.common.entities.hostile.ParagoombaEntity;
import com.dayofpi.super_block_world.common.entities.passive.GladGoombaEntity;
import com.dayofpi.super_block_world.registry.ModEntities;
import com.dayofpi.super_block_world.registry.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SuperHeartEntity extends ThrownItemEntity {
    public SuperHeartEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public SuperHeartEntity(World world, LivingEntity owner) {
        super(ModEntities.SUPER_HEART, owner, world);
    }

    public SuperHeartEntity(World world, double x, double y, double z) {
        super(ModEntities.SUPER_HEART, x, y, z, world);
    }

    private ParticleEffect getParticleParameters() {
        return new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack());
    }

    private ParticleEffect getHeartParticleParameters() {
        return ParticleTypes.HEART;
    }

    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status != 3)
            return;
        ParticleEffect particleEffect = this.getParticleParameters();
        ParticleEffect heartEffect = this.getHeartParticleParameters();
        for (int i = 0; i < 4; ++i) {
            this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
        for (int i = 0; i < 3; ++i) {
            this.world.addParticle(heartEffect, this.getParticleX(1.0), this.getY(), this.getParticleZ(1.0), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.isClient()) {
            world.addParticle(ParticleTypes.WAX_OFF, this.getParticleX(0.5D), this.getRandomBodyY(), this.getParticleZ(0.5D), 0.0D, 0.0D, 0.0D);
        }
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.applyEffects(hitResult.getType() == HitResult.Type.ENTITY ? ((EntityHitResult) hitResult).getEntity() : null);
            this.playSound(Sounds.ITEM_SUPER_HEART, 1.0F, 1.0F);
            this.world.sendEntityStatus(this, (byte) 3);
            this.discard();
        }
    }

    private void applyEffects(@Nullable Entity entity) {
        Box box = new Box(this.getBlockPos()).expand(6.0, 6.0, 6.0);
        List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, box);
        if (!list.isEmpty()) {
            for (LivingEntity livingEntity : list) {
                double d;
                if (!livingEntity.isAffectedBySplashPotions() || !((d = this.squaredDistanceTo(livingEntity)) < 16.0))
                    continue;
                double e = 1.0 - Math.sqrt(d) / 4.0;
                if (livingEntity == entity) {
                    e = 1.0;
                }
                if (livingEntity instanceof BobOmbEntity) {
                    ((BobOmbEntity) livingEntity).convertTo(ModEntities.BOB_OMB_BUDDY, false);
                    livingEntity.dropItem(Items.STRING);
                } else if (livingEntity instanceof ParagoombaEntity paragoomba) {
                    GladGoombaEntity gladGoomba = paragoomba.convertTo(ModEntities.GLAD_PARAGOOMBA, false);
                    if (gladGoomba == null)
                        return;
                    if (paragoomba.getSize() == 0)
                        gladGoomba.setBaby(true);
                    else if (paragoomba.getSize() > 1)
                        gladGoomba.setBig(true);
                } else if (livingEntity instanceof GoombaEntity goomba) {
                    GladGoombaEntity gladGoomba = goomba.convertTo(ModEntities.GLAD_GOOMBA, false);
                    if (gladGoomba == null)
                        return;
                    if (goomba.getSize() == 0)
                        gladGoomba.setBaby(true);
                    else if (goomba.getSize() > 1)
                        gladGoomba.setBig(true);
                }
                StatusEffect statusEffect = StatusEffects.INSTANT_HEALTH;
                statusEffect.applyInstantEffect(this, this.getOwner(), livingEntity, 1, e);
            }
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.SUPER_HEART;
    }

    protected float getGravity() {
        return 0.04F;
    }
}
