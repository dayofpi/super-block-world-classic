package com.dayofpi.super_block_world.common.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.projectile.HammerEntity;
import com.dayofpi.super_block_world.registry.ModEntities;
import com.dayofpi.super_block_world.registry.ModItems;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HammerBroEntity extends AbstractBro {
    public HammerBroEntity(EntityType<? extends AbstractBro> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        HammerEntity hammerEntity = new HammerEntity(world, this);
        double x = target.getX() - this.getX();
        double y = target.getBodyY(0.3333333333333333D) - hammerEntity.getY();
        double z = target.getZ() - this.getZ();
        double g = Math.sqrt(x * x + z * z);
        hammerEntity.setVelocity(x, y + g * 0.67D, z, 0.9F, (float)(14 - this.world.getDifficulty().getId() * 4));
        this.world.spawnEntity(hammerEntity);
        this.swingHand(Hand.MAIN_HAND);
        this.playSound(Sounds.ITEM_HAMMER, 0.9F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.isAlive()) {
            List<ItemEntity> fireFlowerList = this.world.getEntitiesByClass(ItemEntity.class, this.getBoundingBox().expand(0.7), itemEntity -> itemEntity.getStack().isOf(ModItems.FIRE_FLOWER));
            List<ItemEntity> iceFlowerList = this.world.getEntitiesByClass(ItemEntity.class, this.getBoundingBox().expand(0.7), itemEntity -> itemEntity.getStack().isOf(ModItems.ICE_FLOWER));
            if (!fireFlowerList.isEmpty()) {
                this.transform(ModEntities.FIRE_BRO);
                fireFlowerList.get(0).discard();
            } else if (!iceFlowerList.isEmpty()) {
                this.transform(ModEntities.ICE_BRO);
                iceFlowerList.get(0).discard();
            }
        }
    }

    private void transform(EntityType<? extends AbstractBro> entityType) {
        this.convertTo(entityType, false);
        this.playSound(Sounds.ENTITY_GENERIC_POWER_UP, 0.5F, 1.0F);
        if (!world.isClient()) {
            Random random = world.getRandom();
            for (int i = 0; i < 4; ++i) {
                ((ServerWorld) world).spawnParticles(ParticleTypes.POOF, this.getX() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), this.getY() + 0.5D, this.getZ() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        this.initEquipment(random, difficulty);
        return entityData;
    }

    protected void initEquipment(Random random, LocalDifficulty difficulty) {
        super.initEquipment(random, difficulty);
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModItems.HAMMER));
    }
}
