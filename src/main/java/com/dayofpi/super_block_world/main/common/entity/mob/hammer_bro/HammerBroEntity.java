package com.dayofpi.super_block_world.main.common.entity.mob.hammer_bro;

import com.dayofpi.super_block_world.main.util.sounds.ModSounds;
import com.dayofpi.super_block_world.main.common.entity.EnemyEntity;
import com.dayofpi.super_block_world.main.common.entity.projectile.HammerEntity;
import com.dayofpi.super_block_world.main.registry.item.ItemRegistry;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class HammerBroEntity extends AbstractBro {
    public HammerBroEntity(EntityType<? extends EnemyEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        HammerEntity hammerEntity = new HammerEntity(world, this);
        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.3333333333333333D) - hammerEntity.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);
        hammerEntity.setVelocity(d, e + g * 0.3D, f, 1.2F, (float)(14 - this.world.getDifficulty().getId() * 4));
        this.playSound(ModSounds.ITEM_PROJECTILE_THROW, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(hammerEntity);
        this.swingHand(Hand.MAIN_HAND);
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
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.HAMMER));
    }
}
