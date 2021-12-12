package com.dayofpi.sbw_main.common.entity.type.mobs;

import com.dayofpi.sbw_main.util.sounds.ModSounds;
import com.dayofpi.sbw_main.common.entity.type.bases.EnemyEntity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@SuppressWarnings("deprecation")
public class ParagoombaEntity extends GoombaEntity {
    private static final TrackedData<Boolean> MOTHER;

    static {
        MOTHER = DataTracker.registerData(ParagoombaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    public ParagoombaEntity(EntityType<? extends EnemyEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, false);
    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    public boolean isMother() {
        return this.dataTracker.get(MOTHER);
    }

    private void setMother(boolean mother) {
        this.dataTracker.set(MOTHER, mother);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(MOTHER, false);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Mother", this.isMother());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        this.setMother(nbt.getBoolean("Mother"));
        super.readCustomDataFromNbt(nbt);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return GoombaEntity.createAttributes().add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5D);
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (this.getSize() == 1 && random.nextInt(5) == 0) {
            this.setMother(true);
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public static boolean canParagoombaSpawn(EntityType<ParagoombaEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return !(world.getLightLevel(LightType.BLOCK, pos) > 0) && world.isSkyVisible(pos) && pos.getY() > world.getSeaLevel();
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected boolean hasWings() {
        return true;
    }

    protected void addFlapEffects() {
        if (random.nextFloat() < 0.06F)
            this.playSound(ModSounds.ENTITY_GOOMBA_FLUTTER, 1.0F, this.getSoundPitch() * 0.8F);
    }
}
