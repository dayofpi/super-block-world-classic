package com.dayofpi.super_block_world.entity.entities.passive;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.entity.entities.brains.LilOinkBrain;
import com.dayofpi.super_block_world.entity.ModEntities;
import com.dayofpi.super_block_world.item.ModItems;
import com.dayofpi.super_block_world.entity.entities.LilOinkVariant;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class LilOinkEntity extends AnimalEntity {
    private static final TrackedData<String> VARIANT = DataTracker.registerData(LilOinkEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final ImmutableList<SensorType<? extends Sensor<? super LilOinkEntity>>> SENSORS = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY);
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.IS_PANICKING);

    public LilOinkEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected Brain.Profile<LilOinkEntity> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSORS);
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return LilOinkBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Brain<LilOinkEntity> getBrain() {
        return (Brain<LilOinkEntity>) super.getBrain();
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(ModItems.YOSHI_FRUIT);
    }

    private ItemStack getItemFromType(LilOinkVariant variant) {
        if (variant.getItem() == Items.ENCHANTED_BOOK) return this.getEnchantedBookStack();
        return new ItemStack(variant.getItem());
    }

    public LilOinkVariant getVariant() {
        return LilOinkVariant.fromName(this.dataTracker.get(VARIANT));
    }

    private void setVariant(LilOinkVariant variant) {
        this.dataTracker.set(VARIANT, variant.getName());
    }

    public Identifier getTexturePath() {
        return new Identifier(Main.MOD_ID, "textures/entity/lil_oink/" + this.getVariant().getName() + ".png");
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, LilOinkVariant.WHITE.getName());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("Variant", this.getVariant().getName());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setVariant(LilOinkVariant.fromName(nbt.getString("Variant")));
    }

    @Override
    protected void mobTick() {
        this.world.getProfiler().push("lilOinkBrain");
        this.getBrain().tick((ServerWorld) this.world, this);
        this.world.getProfiler().pop();
        this.world.getProfiler().push("lilOinkActivityUpdate");
        LilOinkBrain.updateActivities(this);
        this.world.getProfiler().pop();
        super.mobTick();
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setVariant(this.chooseRandomVariant().orElse(LilOinkVariant.WHITE));
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    private Optional<LilOinkVariant> chooseRandomVariant() {
        DataPool<LilOinkVariant> variantPool = DataPool.<LilOinkVariant>builder().add(LilOinkVariant.GOLD, 3).add(LilOinkVariant.STARRY, 7).add(LilOinkVariant.FUNGAL, 7).add(LilOinkVariant.FLORAL, 7).add(LilOinkVariant.MYSTERY, 7).add(LilOinkVariant.SILVER, 9).add(LilOinkVariant.WHITE, 15).add(LilOinkVariant.BLACK, 15).add(LilOinkVariant.PINK, 15).add(LilOinkVariant.TIGER, 16).build();
        return variantPool.getDataOrEmpty(this.random);
    }

    @Override
    public float getSoundPitch() {
        return super.getSoundPitch() * 1.5F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_PIG_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIG_DEATH;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        ActionResult actionResult = super.interactMob(player, hand);
        if (actionResult.isAccepted() && this.isBreedingItem(itemStack)) {
            this.world.playSoundFromEntity(null, this, Sounds.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 1.0f, MathHelper.nextBetween(this.world.random, 1.2f, 1.8f));
        }
        return actionResult;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.LIL_OINK.create(world);
    }

    @Override
    protected void onGrowUp() {
        super.onGrowUp();
        if (!this.isBaby() && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            this.dropStack(this.getItemFromType(this.getVariant()), 1);
            this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
        }
    }

    private ItemStack getEnchantedBookStack() {
        List<Enchantment> list = Registry.ENCHANTMENT.stream().filter(this::getEnchantmentOptions).toList();
        Enchantment enchantment = list.get(random.nextInt(list.size()));

        return EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(enchantment, enchantment.getMinLevel()));
    }

    private boolean getEnchantmentOptions(Enchantment enchantment) {
        return enchantment == Enchantments.CHANNELING || enchantment == Enchantments.SILK_TOUCH || enchantment == Enchantments.EFFICIENCY || enchantment == Enchantments.FEATHER_FALLING || enchantment == Enchantments.FROST_WALKER;
    }

}
