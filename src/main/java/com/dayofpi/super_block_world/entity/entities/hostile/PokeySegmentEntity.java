package com.dayofpi.super_block_world.entity.entities.hostile;

import com.dayofpi.super_block_world.entity.ModEntities;
import com.dayofpi.super_block_world.item.ModItems;
import com.dayofpi.super_block_world.util.ModDamageSource;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PokeySegmentEntity extends GolemEntity {
    private static final TrackedData<Boolean> IS_SNOWY = DataTracker.registerData(PokeySegmentEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public PokeySegmentEntity(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 1;
    }

    public static DefaultAttributeContainer.Builder pokeySegmentAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2);
    }

    @Override
    public boolean canFreeze() {
        return !this.isSnowy();
    }

    public boolean isSnowy() {
        return this.dataTracker.get(IS_SNOWY);
    }

    void setSnowy(boolean hasEgg) {
        this.dataTracker.set(IS_SNOWY, hasEgg);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(IS_SNOWY, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("IsSnowy", this.isSnowy());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setSnowy(nbt.getBoolean("IsSnowy"));
    }

    @Override
    protected void dropInventory() {
        if (random.nextBoolean()) {
            this.dropItem(this.getPokeyDrop());
        }
        super.dropInventory();
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return this.isSnowy() ? SoundEvents.ENTITY_SNOW_GOLEM_HURT : null;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return this.isSnowy() ? SoundEvents.ENTITY_SNOW_GOLEM_DEATH : null;
    }

    @Override
    public double getMountedHeightOffset() {
        return this.getHeight() - 0.08f;
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        this.damage(player);
    }

    private void damage(LivingEntity target) {
        if (this.isAlive()) {
            if (this.squaredDistanceTo(target) < 1.44 && this.canSee(target) && target.damage(ModDamageSource.SPIKES, this.getDamageAmount())) {
                this.applyDamageEffects(this, target);
            }
        }
    }

    private float getDamageAmount() {
        return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
    }

    private Item getPokeyDrop() {
        return this.isSnowy() ? Items.SNOWBALL : ModItems.SPIKE;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getFirstPassenger() instanceof LivingEntity livingEntity) {
            if (livingEntity.isDead()) {
                this.damage(DamageSource.mob(livingEntity.getAttacker()), this.getMaxHealth());
            }
        }
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (random.nextFloat() < 0.6f && this.getRootVehicle().getPassengerList().size() < 6) {
            PokeySegmentEntity pokeySegment = ModEntities.POKEY_SEGMENT.create(this.world);
            if (pokeySegment != null) {
                pokeySegment.updatePositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                pokeySegment.setSnowy(this.isSnowy());
                world.spawnEntity(pokeySegment);
                pokeySegment.initialize(world, difficulty, spawnReason, null, null);
                this.startRiding(pokeySegment);
            }
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }
}
