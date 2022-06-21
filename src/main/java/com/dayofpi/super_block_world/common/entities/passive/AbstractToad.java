package com.dayofpi.super_block_world.common.entities.passive;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.Toad;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractToad extends PassiveEntity implements Toad {
    private static final TrackedData<Integer> COINS_RECEIVED;
    private static final TrackedData<Integer> COINS_WANTED;

    static {
        COINS_RECEIVED = DataTracker.registerData(AbstractToad.class, TrackedDataHandlerRegistry.INTEGER);
        COINS_WANTED = DataTracker.registerData(AbstractToad.class, TrackedDataHandlerRegistry.INTEGER);
    }

    protected AbstractToad(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(COINS_RECEIVED, 0);
        this.dataTracker.startTracking(COINS_WANTED, 30);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("ReceivedCoins", this.getReceivedCoins());
        nbt.putInt("WantedCoins", this.getWantedCoins());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setReceivedCoins(nbt.getInt("ReceivedCoins"));
        this.setWantedCoins(nbt.getInt("WantedCoins"));
    }

    public int getWantedCoins() {
        return dataTracker.get(COINS_WANTED);
    }

    public void setWantedCoins(int coins) {
        dataTracker.set(COINS_WANTED, coins);
    }

    public int getReceivedCoins() {
        return dataTracker.get(COINS_RECEIVED);
    }

    public void setReceivedCoins(int coins) {
        dataTracker.set(COINS_RECEIVED, coins);
    }

    @Override
    public int getMinAmbientSoundDelay() {
        return 130;
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (this instanceof ToadEntity toadEntity)
            return toadEntity.isCheering() || toadEntity.isScared() ? null : Sounds.ENTITY_TOAD_AMBIENT;
        return Sounds.ENTITY_TOAD_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_TOAD_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_TOAD_DEATH;
    }
}
