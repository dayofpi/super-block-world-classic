package com.dayofpi.super_block_world.common.entities.passive;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModEntities;
import com.google.common.collect.Maps;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class MooMooEntity extends CowEntity {
    private static final Map<Integer, Identifier> TEXTURES;
    private static final TrackedData<Integer> TYPE;

    static {
        TYPE = DataTracker.registerData(MooMooEntity.class, TrackedDataHandlerRegistry.INTEGER);
        TEXTURES = Util.make(Maps.newHashMap(), (map) -> {
            map.put(0, new Identifier(Main.MOD_ID, "textures/entity/moo_moo/baunilha.png"));
            map.put(1, new Identifier(Main.MOD_ID, "textures/entity/moo_moo/casadinho.png"));
            map.put(2, new Identifier(Main.MOD_ID, "textures/entity/moo_moo/chocolate.png"));
        });
    }

    public MooMooEntity(EntityType<? extends CowEntity> entityType, World world) {
        super(entityType, world);
    }

    public Identifier getTexture() {
        return TEXTURES.getOrDefault(this.getMooMooType(), TEXTURES.get(0));
    }

    public int getMooMooType() {
        return this.dataTracker.get(TYPE);
    }

    public void setMooMooType(int type) {
        this.dataTracker.set(TYPE, type);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        ActionResult actionResult = super.interactMob(player, hand);
        if (actionResult.isAccepted() && this.isBreedingItem(itemStack)) {
            this.world.playSoundFromEntity(null, this, Sounds.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 1.0f, MathHelper.nextBetween(this.world.random, 0.8f, 1.2f));
        }
        return actionResult;
    }

    @Override
    public MooMooEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        MooMooEntity mooMooEntity = ModEntities.MOO_MOO.create(serverWorld);
        if (passiveEntity instanceof MooMooEntity && mooMooEntity != null) {
            mooMooEntity.setMooMooType(((MooMooEntity) passiveEntity).getMooMooType());
        }
        return mooMooEntity;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.distanceTraveled > (this.calculateNextStepSoundDistance() - random.nextFloat()) && this.random.nextInt(400) < this.ambientSoundChance++) {
            this.playSound(Sounds.ENTITY_MOO_MOO_BELL, 1.0F, 1.0F);
        }
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        EntityData data = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        this.setMooMooType(world.getRandom().nextInt(3));
        return data;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Type", this.getMooMooType());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setMooMooType(nbt.getInt("Type"));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TYPE, 0);
    }
}
