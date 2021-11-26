package com.dayofpi.sbw_main.entity.types.mobs;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.ModSounds;
import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.entity.registry.ModEntities;
import com.dayofpi.sbw_main.item.registry.ModItems;
import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Random;

public class MooMooEntity extends CowEntity {
    public static final Map<Integer, Identifier> TEXTURES;
    private static final Ingredient BREEDING_INGREDIENT;
    private static final TrackedData<Integer> TYPE;
    private static final TrackedData<Boolean> LYING;

    static {
        BREEDING_INGREDIENT = Ingredient.ofItems(Items.WHEAT, ModItems.TURNIP);
        TYPE = DataTracker.registerData(MooMooEntity.class, TrackedDataHandlerRegistry.INTEGER);
        LYING = DataTracker.registerData(MooMooEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        TEXTURES = Util.make(Maps.newHashMap(), (map) -> {
            map.put(0, new Identifier(Main.MOD_ID, "textures/entity/moo_moo/vanilla.png"));
            map.put(1, new Identifier(Main.MOD_ID, "textures/entity/moo_moo/casadinho.png"));
            map.put(2, new Identifier(Main.MOD_ID, "textures/entity/moo_moo/chocolate.png"));
        });
    }

    public MooMooEntity(EntityType<? extends MooMooEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.2)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.20000000298023224D);
    }

    public static boolean canSpawn(EntityType<MooMooEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(BlockTags.DIRT) && world.getBaseLightLevel(pos, 0) > 8;
    }

    public Identifier getTexture() {
        return TEXTURES.getOrDefault(this.getColor(), TEXTURES.get(0));
    }

    public int getColor() {
        return this.dataTracker.get(TYPE);
    }

    public void setColor(int type) {
        if (type < 0 || type > 3) {
            type = 0;
        }
        this.dataTracker.set(TYPE, type);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.0D));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(3, new TemptGoal(this, 1.25D, BREEDING_INGREDIENT, false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.add(8, new MooMooEntity.MooMooWanderGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    protected SoundEvent getAmbientSound() {
        return ModSounds.ENTITY_MOO_MOO_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ENTITY_MOO_MOO_HURT;
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.ENTITY_MOO_MOO_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(ModSounds.ENTITY_MOO_MOO_STEP, 0.2F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.8F;
    }

    public MooMooEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        MooMooEntity mooMooEntity = ModEntities.MOO_MOO.create(serverWorld);
        if (passiveEntity instanceof MooMooEntity && mooMooEntity != null) {
            mooMooEntity.setColor(((MooMooEntity) passiveEntity).getColor());
        }
        return mooMooEntity;
    }

    public void playAmbientSound() {
        SoundEvent soundEvent = this.getAmbientSound();
        if (soundEvent != null) {
            this.playSound(soundEvent, 0.4F, this.getSoundPitch());
        }
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setColor(this.random.nextInt(4));
        this.setLying(this.random.nextInt(3) == 0);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(LYING, false);
        this.dataTracker.startTracking(TYPE, 1);
    }

    public int getMaxLookYawChange() {
        return this.isLying() ? 5 : 10;
    }

    @Override
    public void tickMovement() {
        if (!this.world.isClient && this.isAlive() && this.canMoveVoluntarily()) {
            boolean liePreventions = this.touchingWater || !this.isOnGround() || this.sidewaysSpeed != 0.0F || this.forwardSpeed != 0.0F;
            if (this.isLying()) {
                if (liePreventions || (random.nextInt(5000) == 0))
                    this.setLying(false);
            } else if (!liePreventions && random.nextInt(5000) == 0) {
                this.setLying(true);
            }
        }
        super.tickMovement();

    }

    public boolean isLying() {
        return this.dataTracker.get(LYING);
    }

    public void setLying(boolean lying) {
        this.dataTracker.set(LYING, lying);
    }

    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos.down()).isOf(ModBlocks.TOADSTOOL_GRASS) ? 10.0F : world.getLightLevel(pos) - 0.5F;
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Type", this.getColor());
        nbt.putBoolean("LyingDown", this.isLying());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setColor(nbt.getInt("Type"));
        this.setLying(nbt.getBoolean("LyingDown"));
    }

    public int getMinAmbientSoundDelay() {
        return 120;
    }

    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    static class MooMooWanderGoal extends WanderAroundGoal {
        public static final float CHANCE = 0.001F;
        protected final float probability;
        protected final MooMooEntity mooMooEntity;

        public MooMooWanderGoal(MooMooEntity mob, double speed) {
            this(mob, speed, CHANCE);
        }

        public MooMooWanderGoal(MooMooEntity mob, double speed, float probability) {
            super(mob, speed);
            this.probability = probability;
            this.mooMooEntity = mob;
        }

        public boolean canStart() {
            if (this.mooMooEntity.isLying()) {
                Random random = new Random();
                if (random.nextInt(50) == 0) {
                    return super.canStart();
                } else return false;
            }
            return super.canStart();
        }

        public void start() {
            super.start();
            this.mooMooEntity.playSound(ModSounds.ENTITY_MOO_MOO_BELL, mooMooEntity.getSoundVolume(), mooMooEntity.getSoundPitch());
        }

        public void stop() {
            super.stop();
            this.mooMooEntity.playSound(ModSounds.ENTITY_MOO_MOO_BELL, mooMooEntity.getSoundVolume(), mooMooEntity.getSoundPitch());
        }

        @Nullable
        protected Vec3d getWanderTarget() {
            if (this.mob.isInsideWaterOrBubbleColumn()) {
                Vec3d vec3d = FuzzyTargeting.find(this.mob, 15, 7);
                return vec3d == null ? super.getWanderTarget() : vec3d;
            } else {
                return this.mob.getRandom().nextFloat() >= this.probability ? FuzzyTargeting.find(this.mob, 10, 7) : super.getWanderTarget();
            }
        }
    }

}
