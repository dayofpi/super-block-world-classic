package com.dayofpi.super_block_world.common.entities.mob;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.entities.abst.AbstractEnemy;
import com.dayofpi.super_block_world.common.entities.abst.AbstractBro;
import com.dayofpi.super_block_world.common.entities.goals.SeekPowerUpGoal;
import com.dayofpi.super_block_world.common.entities.HammerEntity;
import com.dayofpi.super_block_world.registry.main.EntityInit;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HammerBroEntity extends AbstractBro {
    public HammerBroEntity(EntityType<? extends AbstractEnemy> entityType, World world) {
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
        this.playSound(SoundInit.ITEM_PROJECTILE_HAMMER, 0.9F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    public void initGoals() {
        super.initGoals();
        this.targetSelector.add(1, new SeekPowerUpGoal(this, ItemInit.FIRE_FLOWER));
        this.targetSelector.add(1, new SeekPowerUpGoal(this, ItemInit.ICE_FLOWER));
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.isAlive()) {
            List<ItemEntity> fireFlowerList = this.world.getEntitiesByClass(ItemEntity.class, this.getBoundingBox().expand(0.7), itemEntity -> itemEntity.getStack().isOf(ItemInit.FIRE_FLOWER));
            List<ItemEntity> iceFlowerList = this.world.getEntitiesByClass(ItemEntity.class, this.getBoundingBox().expand(0.7), itemEntity -> itemEntity.getStack().isOf(ItemInit.ICE_FLOWER));
            if (!fireFlowerList.isEmpty()) {
                this.transform(EntityInit.FIRE_BRO);
                fireFlowerList.get(0).discard();
            } else if (!iceFlowerList.isEmpty()) {
                this.transform(EntityInit.ICE_BRO);
                iceFlowerList.get(0).discard();
            }
        }
    }

    private void transform(EntityType<? extends AbstractBro> entityType) {
        this.convertTo(entityType, false);
        this.playSound(SoundInit.ITEM_POWER_UP, 0.5F, 1.0F);
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        this.initEquipment(difficulty);
        this.updateEnchantments(difficulty);

        return entityData;
    }

    protected void initEquipment(LocalDifficulty difficulty) {
        super.initEquipment(difficulty);
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ItemInit.HAMMER));
    }
}
