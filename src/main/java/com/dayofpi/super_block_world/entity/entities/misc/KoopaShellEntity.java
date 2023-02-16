package com.dayofpi.super_block_world.entity.entities.misc;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.block.blocks.BrickBlock;
import com.dayofpi.super_block_world.block.blocks.ReactiveBlock;
import com.dayofpi.super_block_world.entity.entities.KoopaVariant;
import com.dayofpi.super_block_world.entity.entities.hostile.BuzzyBeetleEntity;
import com.dayofpi.super_block_world.entity.entities.passive.KoopaTroopaEntity;
import com.dayofpi.super_block_world.entity.ModEntities;
import com.dayofpi.super_block_world.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class KoopaShellEntity extends MobEntity {
    private static final TrackedData<Boolean> OCCUPIED;
    private static final TrackedData<Integer> VARIANT;
    private static final TrackedData<Integer> EXIT_TIME;
    private static final String OCCUPIED_KEY = "Occupied";
    private static final String VARIANT_KEY = "Variant";
    private static final String EXIT_TIME_KEY = "ExitTime";
    public static final Integer MAX_EXIT_TIME = 100;
    private int soundDelay;

    static {
        OCCUPIED = DataTracker.registerData(KoopaShellEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        VARIANT = DataTracker.registerData(KoopaShellEntity.class, TrackedDataHandlerRegistry.INTEGER);
        EXIT_TIME = DataTracker.registerData(KoopaShellEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    public KoopaShellEntity(EntityType<? extends KoopaShellEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 0;
    }

    public static DefaultAttributeContainer.Builder createKoopaShellAtrributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0D);
    }

    protected void leaveShell() {
        if (this.getVariant() >= 0) {
            KoopaTroopaEntity koopaEntity = ModEntities.KOOPA_TROOPA.create(this.world);
            if (koopaEntity != null) {
                koopaEntity.setKoopaColor(this.getVariant());
                koopaEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                koopaEntity.setAiDisabled(this.isAiDisabled());
                if (this.hasCustomName()) {
                    koopaEntity.setCustomName(this.getCustomName());
                    koopaEntity.setCustomNameVisible(this.isCustomNameVisible());
                }
                world.spawnEntity(koopaEntity);
                this.discard();
            }
        } else {
            BuzzyBeetleEntity buzzyBeetle = ModEntities.BUZZY_BEETLE.create(this.world);
            if (buzzyBeetle != null) {
                buzzyBeetle.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                buzzyBeetle.setAiDisabled(this.isAiDisabled());
                if (this.hasCustomName()) {
                    buzzyBeetle.setCustomName(this.getCustomName());
                    buzzyBeetle.setCustomNameVisible(this.isCustomNameVisible());
                }
                world.spawnEntity(buzzyBeetle);
                this.discard();
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.soundDelay > 0)
            --this.soundDelay;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.isOccupied()) {
            this.setExitTime(this.getExitTime() + 1);
            if (this.getExitTime() >= 60) {
                this.setVelocity(this.getVelocity().multiply(0.4));
            }
            if (this.getExitTime() == MAX_EXIT_TIME) {
                this.leaveShell();
            }
        }

        if (this.isAlive() && this.getVelocity().horizontalLengthSquared() > 0.02D) {
            List<Entity> damageable = world.getOtherEntities(this, this.getBoundingBox().contract(0.D, 0.2D, 0.D).expand(0.15D, 0.0D, 0.15D), entity -> entity instanceof LivingEntity && !(entity instanceof KoopaShellEntity));
            damageable.forEach(entity -> entity.damage(DamageSource.thrownProjectile(this, this), 4));

            for (BlockPos blockPos : BlockPos.iterateOutwards(this.getBlockPos(), 1, 0, 1)) {
                BlockState state = world.getBlockState(blockPos);
                if (this.collidesWithStateAtPos(blockPos, state)) {
                    if (state.isSolidBlock(world, blockPos)) {
                        this.setVelocity(this.getVelocity().multiply(-1, 1, -1));
                    }
                }
                if (state.getBlock() instanceof BrickBlock) {
                    world.breakBlock(blockPos, true);
                } else if (state.getBlock() instanceof ReactiveBlock reactiveBlock) {
                    reactiveBlock.react(world, blockPos, null);
                }
            }
        }
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public void pushAwayFrom(Entity entity) {
        double e;
        if (this.isConnectedThroughVehicle(entity)) {
            return;
        }
        if (entity.noClip || this.noClip) {
            return;
        }
        double d = entity.getX() - this.getX();
        double f = MathHelper.absMax(d, e = entity.getZ() - this.getZ());
        if (f >= (double)0.005f) {
            if (this.soundDelay == 0) {
                this.playSound(Sounds.ENTITY_SHELL_HIT, 1.0F, 1.0F);
                this.soundDelay = 5;
            }
            f = Math.sqrt(f);
            d /= f;
            e /= f;
            double g = 1.0 / f;
            if (g > 5.0) {
                g = 5.0;
            }
            d *= g;
            e *= g;
            d *= 0.7f;
            e *= 0.7f;
            if (!this.hasPassengers() && this.isPushable()) {
                this.addVelocity(-d, 0.0, -e);
            }
            if (!entity.hasPassengers() && entity.isPushable()) {
                entity.addVelocity(d, 0.0, e);
            }
        }
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_SHELL_HIT;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isOccupied()) {
            this.leaveShell();
            return false;
        }
        if (super.damage(source, amount)) {
            this.remove(Entity.RemovalReason.KILLED);
            this.emitGameEvent(GameEvent.ENTITY_DIE);
            if (!source.isSourceCreativePlayer())
                this.dropItem(this.getShellItem());
        }
        return super.damage(source, amount);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Nullable
    @Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(this.getShellItem());
    }

    private Item getShellItem() {
        if (this.getVariant() < 0)
            return ModItems.BUZZY_SHELL;
        return KoopaVariant.getShell(this.getVariant());
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(OCCUPIED, false);
        this.dataTracker.startTracking(VARIANT, 0);
        this.dataTracker.startTracking(EXIT_TIME, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean(OCCUPIED_KEY, this.isOccupied());
        nbt.putInt(VARIANT_KEY, this.getVariant());
        nbt.putInt(EXIT_TIME_KEY, this.getExitTime());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setOccupied(nbt.getBoolean(OCCUPIED_KEY));
        this.setVariant(nbt.getInt(VARIANT_KEY));
        this.setExitTime(nbt.getInt(EXIT_TIME_KEY));
    }

    public void setOccupied(boolean occupied) {
        this.dataTracker.set(OCCUPIED, occupied);
    }

    public boolean isOccupied() {
        return this.dataTracker.get(OCCUPIED);
    }

    public int getVariant() {
        return this.dataTracker.get(VARIANT);
    }

    public void setVariant(int color) {
        this.dataTracker.set(VARIANT, color);
    }

    public int getExitTime() {
        return this.dataTracker.get(EXIT_TIME);
    }

    private void setExitTime(int exitTime) {
        this.dataTracker.set(EXIT_TIME, exitTime);
    }
}
