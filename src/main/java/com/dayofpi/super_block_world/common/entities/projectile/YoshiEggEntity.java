package com.dayofpi.super_block_world.common.entities.projectile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModBlocks;
import com.dayofpi.super_block_world.registry.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class YoshiEggEntity extends ThrownItemEntity {
    private static final TrackedData<NbtCompound> STORED_ENTITY;

    static {
        STORED_ENTITY = DataTracker.registerData(YoshiEggEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);
    }

    public YoshiEggEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public YoshiEggEntity(World world, double x, double y, double z) {
        super(ModEntities.YOSHI_EGG, x, y, z, world);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (hitResult.getType() != HitResult.Type.MISS) {
            if (!this.getStoredEntity().isEmpty()) {
                NbtCompound nbtCompound = this.getStoredEntity();
                if (!world.isClient) EntityType.getEntityFromNbt(nbtCompound, world).ifPresent(entity -> {
                    BlockPos blockPos = this.getBlockPos();
                    entity.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                    world.spawnEntity(entity);
                    this.playSound(Sounds.BLOCK_YOSHI_EGG_HATCH, 1.0F, 1.0F);
                });
            }
            this.world.sendEntityStatus(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 3) {
            double d = 0.08;
            for (int i = 0; i < 8; ++i) {
                this.world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, ModBlocks.YOSHI_EGG.getDefaultState()), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5) * d, ((double) this.random.nextFloat() - 0.5) * d, ((double) this.random.nextFloat() - 0.5) * d);
            }
        }
    }

    @Override
    public boolean collides() {
        return true;
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(STORED_ENTITY, new NbtCompound());
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.put("StoredEntity", this.getStoredEntity());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setStoredEntity(nbt.getCompound("StoredEntity"));
    }

    public NbtCompound getStoredEntity() {
        return this.dataTracker.get(STORED_ENTITY);
    }

    public void setStoredEntity(NbtCompound storedEntity) {
        this.dataTracker.set(STORED_ENTITY, storedEntity);
    }

    @Override
    protected Item getDefaultItem() {
        return ModBlocks.YOSHI_EGG.asItem();
    }

    protected float getGravity() {
        return 0.05F;
    }
}
