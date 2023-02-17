package com.dayofpi.super_block_world.entity.entities.boss;

import com.dayofpi.super_block_world.audio.ModMusic;
import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class KingBobOmbEntity extends ModBossEntity {
    private static final TrackedData<Optional<UUID>> CARRIED_ENTITY = DataTracker.registerData(KingBobOmbEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

    public KingBobOmbEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.lookControl = new YawAdjustingLookControl(this, 10);
        this.bossBar = new ServerBossBar(this.getDisplayName(), BossBar.Color.YELLOW, BossBar.Style.PROGRESS);
        this.stepHeight = 1.0F;
        this.experiencePoints = 25;
    }

    @Override
    public double getMountedHeightOffset() {
        return super.getMountedHeightOffset() + 1.0;
    }

    public static DefaultAttributeContainer.Builder createKingBobOmbAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 64.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18D).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6D).add(EntityAttributes.GENERIC_ARMOR, 20.0D);
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_KING_BOB_OMB_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_KING_BOB_OMB_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(Sounds.ENTITY_KING_BOB_OMB_STEP, 0.8F, 1.0F);
    }

    @Override
    protected void mobTick() {
        Entity entity = this.getCarriedEntity();
        if (entity != null && entity.distanceTo(this) < 5) {
            boolean canBeCaught = true;
            if (this.getCarriedEntity() instanceof PlayerEntity playerEntity)
                canBeCaught = !playerEntity.getAbilities().allowFlying;
            if (!this.hasPassengers() && canBeCaught) {
                entity.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 2.0F, 0.8F);
                entity.startRiding(this, true);
            }
        } else {
            this.setCarriedEntity(null);
        }
        super.mobTick();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CARRIED_ENTITY, Optional.empty());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.getCarriedEntity() != null)
            nbt.putUuid("CarriedEntity", this.getCarriedEntityUuid());
    }

    @Nullable
    public UUID getCarriedEntityUuid() {
        return this.dataTracker.get(CARRIED_ENTITY).orElse(null);
    }

    @Nullable
    public Entity getCarriedEntity() {
        try {
            UUID uUID = this.getCarriedEntityUuid();
            if (uUID == null || this.world.isClient()) {
                return null;
            }
            return ((ServerWorld)world).getEntity(uUID);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.containsUuid("CarriedEntity"))
            this.setCarriedEntity(nbt.getUuid("CarriedEntity"));
    }

    public void setCarriedEntity(@Nullable UUID carriedEntity) {
        this.dataTracker.set(CARRIED_ENTITY, Optional.ofNullable(carriedEntity));
    }

    @Override
    public MusicSound getBossMusic() {
        return ModMusic.BOSS_1;
    }

    @Override
    protected Item getRareItem() {
        return ModItems.MUSIC_DISC_FIRE_FACTORY;
    }

    @Override
    public int getMaxAttacks() {
        return 2;
    }

}
