package com.dayofpi.super_block_world.common.entities.misc;

import com.dayofpi.super_block_world.registry.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class SuperPickaxEntity extends Entity {
    public SuperPickaxEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean bl;
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (this.world.isClient || this.isRemoved()) {
            return true;
        }

        bl = source.getAttacker() instanceof PlayerEntity && ((PlayerEntity) source.getAttacker()).getAbilities().creativeMode;
        if (bl || this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            if (!bl) {
                this.dropItem(ModItems.SUPER_PICKAX);
            }
            this.discard();
        }
        return true;
    }

    public boolean collides() {
        return true;
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
