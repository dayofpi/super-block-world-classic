package com.dayofpi.super_block_world.common.entities.passive;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.block_entities.QuestionBoxBE;
import com.dayofpi.super_block_world.common.blocks.QuestionBoxBlock;
import com.dayofpi.super_block_world.common.entities.ToadLookControl;
import com.dayofpi.super_block_world.common.entities.brains.ToadBrain;
import com.dayofpi.super_block_world.common.entities.hostile.RottenMushroomEntity;
import com.dayofpi.super_block_world.registry.ModBlocks;
import com.dayofpi.super_block_world.registry.ModEntities;
import com.dayofpi.super_block_world.registry.ModItems;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.Map;

public class ToadEntity extends AbstractToad implements IAnimatable {
    public static final Map<Integer, Identifier> TEXTURES;
    protected static final ImmutableList<SensorType<? extends Sensor<? super PassiveEntity>>> SENSORS = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY);
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModuleType.INTERACTABLE_DOORS, MemoryModuleType.DOORS_TO_CLOSE, MemoryModuleType.LOOK_TARGET, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.IS_PANICKING);
    private static final TrackedData<Integer> COLOR;
    private static final TrackedData<Integer> EMOTION;
    private static final TrackedData<Integer> TOAD_STATE;
    private static final TrackedData<Boolean> TOADETTE;

    static {
        COLOR = DataTracker.registerData(ToadEntity.class, TrackedDataHandlerRegistry.INTEGER);
        EMOTION = DataTracker.registerData(ToadEntity.class, TrackedDataHandlerRegistry.INTEGER);
        TOAD_STATE = DataTracker.registerData(ToadEntity.class, TrackedDataHandlerRegistry.INTEGER);
        TOADETTE = DataTracker.registerData(ToadEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        TEXTURES = Util.make(Maps.newHashMap(), (map) -> {
            map.put(0, new Identifier(Main.MOD_ID, "textures/entity/toad/toad.png"));
            map.put(1, new Identifier(Main.MOD_ID, "textures/entity/toad/blue.png"));
            map.put(2, new Identifier(Main.MOD_ID, "textures/entity/toad/yellow.png"));
            map.put(3, new Identifier(Main.MOD_ID, "textures/entity/toad/pink.png"));
            map.put(4, new Identifier(Main.MOD_ID, "textures/entity/toad/green.png"));
            map.put(5, new Identifier(Main.MOD_ID, "textures/entity/toad/orange.png"));
        });
    }

    final AnimationFactory FACTORY = new AnimationFactory(this);

    public ToadEntity(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
        this.lookControl = new ToadLookControl(this);
        ((MobNavigation) this.getNavigation()).setCanPathThroughDoors(true);
    }

    public static DefaultAttributeContainer.Builder createToadAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(COLOR, 1);
        this.dataTracker.startTracking(EMOTION, 0);
        this.dataTracker.startTracking(TOAD_STATE, 0);
        this.dataTracker.startTracking(TOADETTE, false);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Color", this.getColor());
        nbt.putBoolean("Toadette", this.isToadette());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setColor(nbt.getInt("Color"));
        this.setToadette(nbt.getBoolean("Toadette"));
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setToadette(random.nextBoolean());
        this.setColor(random.nextInt(6));
        this.setWantedCoins(UniformIntProvider.create(20, 40).get(random));
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected Brain.Profile<PassiveEntity> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSORS);
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return ToadBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Brain<ToadEntity> getBrain() {
        return (Brain<ToadEntity>) super.getBrain();
    }

    private void giveChest(BlockPos blockPos) {
        this.setToadState(3);
        if (!this.world.isClient) {
            this.setReceivedCoins(0);
            this.playSound(Sounds.ENTITY_TOAD_CHEER, 1.0F, 1.0F);
            world.setBlockState(blockPos, ModBlocks.QUESTION_BOX.getDefaultState().with(QuestionBoxBlock.TEMPORARY, true));
            world.playSound(null, blockPos, Sounds.BLOCK_QUESTION_BOX_SPAWN, SoundCategory.BLOCKS, 1.0F, 1.0F);
            String commonLoot = "question_boxes/common";
            String rareLoot = "question_boxes/rare";
            String path;
            if (random.nextInt(6) == 0) {
                path = rareLoot;
            } else path = commonLoot;
            BlockEntity blockEntity = world.getBlockEntity(blockPos);
            if (blockEntity instanceof QuestionBoxBE) {
                LootableContainerBlockEntity.setLootTable(world, random, blockPos, new Identifier(Main.MOD_ID, path));
            }
        } else {
            for (int i = 0; i < 6; i++) {
                double modifier = 0.5D + (random.nextFloat() * (random.nextBoolean() ? -1 : 1));
                world.addParticle(ParticleTypes.CLOUD, blockPos.getX() + modifier, blockPos.getY() + modifier, blockPos.getZ() + modifier, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
        if (world.getDifficulty() != Difficulty.PEACEFUL) {
            RottenMushroomEntity rottenMushroomEntity = ModEntities.ROTTEN_MUSHROOM.create(world);
            if (rottenMushroomEntity != null) {
                rottenMushroomEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                rottenMushroomEntity.setAiDisabled(this.isAiDisabled());
                if (this.hasCustomName()) {
                    rottenMushroomEntity.setCustomName(this.getCustomName());
                    rottenMushroomEntity.setCustomNameVisible(this.isCustomNameVisible());
                }
                world.spawnEntity(rottenMushroomEntity);
                this.discard();
            }
        } else {
            super.onStruckByLightning(world, lightning);
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.isAlive()) {
            if (this.getAttentionCooldown() > 0)
                this.setAttentionCooldown(this.getAttentionCooldown() - 1);
            List<Entity> baddies = world.getOtherEntities(this, this.getBoundingBox().expand(4, 4, 4), entity -> entity instanceof HostileEntity);
            if (!this.isScared()) {
                if (this.isCheering() || this.forwardSpeed != 0) {
                    if (!this.world.isClient && this.random.nextFloat() < 0.005F) {
                        this.setToadState(0);
                    }
                }
                if (this.getReceivedCoins() == this.getWantedCoins() && !this.isBaby()) {
                    BlockPos blockPos = this.getBlockPos().add(random.nextInt(2), random.nextInt(2), random.nextInt(2));
                    if (world.isSpaceEmpty(Box.from(Vec3d.ofCenter(blockPos))) && world.getBlockState(blockPos.down()).isSideSolidFullSquare(world, blockPos, Direction.UP)) {
                        this.giveChest(blockPos);
                    }
                    if (world.isClient) {
                        if (random.nextInt(10) == 0)
                            for (int i = 0; i < 4; i++) {
                                world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0D), this.getRandomBodyY(), this.getParticleZ(1.0D), 0.0D, 0.0D, 0.0D);
                            }
                    }
                }

                if (!baddies.isEmpty()) {
                    this.setToadState(2);
                }
                if (!this.isHappy() && this.random.nextFloat() < 0.003F) {
                    this.setEmotion(1);
                } else if (this.random.nextFloat() < 0.005F) {
                    this.setEmotion(0);
                }
            } else if (baddies.isEmpty()) {
                this.setToadState(0);
            }
        }
    }

    @Override
    protected void mobTick() {
        this.world.getProfiler().push("toadBrain");
        this.getBrain().tick((ServerWorld) this.world, this);
        this.world.getProfiler().pop();
        this.world.getProfiler().push("toadActivityUpdate");
        ToadBrain.updateActivities(this);
        this.world.getProfiler().pop();
        super.mobTick();
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(ModItems.COIN) && !this.isBaby() && !this.isScared() && !this.isCheering()) {
            if (!this.world.isClient && this.getReceivedCoins() < this.getWantedCoins()) {
                this.setReceivedCoins(this.getReceivedCoins() + 1);
                this.setAttentionCooldown(30);
                itemStack.decrement(1);
                this.playSound(Sounds.ITEM_COIN, 0.8F, 1.0F);
            }
            return ActionResult.success(this.world.isClient);

        } else return super.interactMob(player, hand);
    }

    public Identifier getTexture() {
        return TEXTURES.getOrDefault(this.getColor(), TEXTURES.get(0));
    }

    public int getColor() {
        return this.dataTracker.get(COLOR);
    }

    public void setColor(int type) {
        if (type < 0 || type > 6) {
            type = 0;
        }
        this.dataTracker.set(COLOR, type);
    }

    public boolean isToadette() {
        return dataTracker.get(TOADETTE);
    }

    public void setToadette(boolean toadette) {
        this.dataTracker.set(TOADETTE, toadette);
    }

    public boolean isHappy() {
        return dataTracker.get(EMOTION) == 1;
    }

    public boolean isAnnoyed() {
        return dataTracker.get(EMOTION) == 2;
    }

    public void setEmotion(int emotion) {
        if (emotion < 0 || emotion > 3) {
            emotion = 0;
        }
        this.dataTracker.set(EMOTION, emotion);
    }

    public boolean isWaving() {
        return dataTracker.get(TOAD_STATE) == 1;
    }

    public boolean isScared() {
        return dataTracker.get(TOAD_STATE) == 2;
    }

    public boolean isCheering() {
        return dataTracker.get(TOAD_STATE) == 3;
    }

    public void setToadState(int state) {
        this.dataTracker.set(TOAD_STATE, state);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        if (this.isBaby()) {
            return 0.71f;
        }
        return 1.52f;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity parent) {
        ToadEntity babie = ModEntities.TOAD.create(world);
        if (babie != null) {
            babie.setToadette(random.nextBoolean());
            babie.setColor(((ToadEntity) parent).getColor());
        }
        return babie;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
            if (this.isScared())
                event.getController().setAnimation(new AnimationBuilder().addAnimation("run", true));
            else
                event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
        } else {
            if (this.isWaving())
                event.getController().setAnimation(new AnimationBuilder().addAnimation("wave", true));
            else if (this.isScared())
                event.getController().setAnimation(new AnimationBuilder().addAnimation("scared", true));
            else if (this.isCheering())
                event.getController().setAnimation(new AnimationBuilder().addAnimation("cheer", true));
            else
                event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 1, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return FACTORY;
    }
}
