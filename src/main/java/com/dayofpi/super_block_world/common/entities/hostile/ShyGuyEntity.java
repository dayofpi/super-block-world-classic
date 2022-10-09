package com.dayofpi.super_block_world.common.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.goals.CoinTemptGoal;
import com.dayofpi.super_block_world.common.entities.passive.BabyYoshiEntity;
import com.dayofpi.super_block_world.common.entities.passive.YoshiEntity;
import com.dayofpi.super_block_world.registry.ModCriteria;
import com.dayofpi.super_block_world.registry.ModItems;
import com.dayofpi.super_block_world.registry.ModTags;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShyGuyEntity extends HostileEntity {
    private static final DataPool<Item> TRADE_ITEMS = DataPool.<Item>builder().add(ModItems.TURNIP, 100).add(Items.COAL, 100).add(ModItems.BOMB, 85).add(ModItems.STELLAR_SHARD, 70).add(Items.IRON_PICKAXE, 80).add(Items.IRON_NUGGET, 80).build();
    private static final TrackedData<String> TYPE = DataTracker.registerData(ShyGuyEntity.class, TrackedDataHandlerRegistry.STRING);
    public ShyGuyEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createShyGuyAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 7.0D).add(EntityAttributes.GENERIC_MAX_HEALTH, 18.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D);
    }

    private static boolean isThereNoLight(WorldAccess world, BlockPos pos) {
        return !(world.getLightLevel(LightType.BLOCK, pos) > 0);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TYPE, Type.RED.name);
    }

    public Type getShyGuyType() {
        return Type.fromName(this.dataTracker.get(TYPE));
    }

    private void setShyGuyType(Type type) {
        this.dataTracker.set(TYPE, type.name);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("Type", this.getShyGuyType().name);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setShyGuyType(Type.fromName(nbt.getString("Type")));
    }

    public DataPool<Item> getTradeItems() {
        return TRADE_ITEMS;
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        if (this.isOnGround() && this.getTarget() == null && target != null) {
            this.playSound(Sounds.ENTITY_GENERIC_SPOT, this.getSoundVolume(), this.getSoundPitch());
            this.playSound(Sounds.ENTITY_SHY_GUY_AMBIENT, this.getSoundVolume(), this.getSoundPitch());
            this.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, target.getPos());
            this.jump();
        }
        super.setTarget(target);
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<? extends ShyGuyEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        if (world.getBiome(pos).isIn(ModTags.SURFACE_SHY_GUY_SPAWN))
            return isThereNoLight(world, pos);
        else return isThereNoLight(world, pos) && world.getBlockState(pos.down()).isIn(ModTags.VANILLATE);
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return player.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.SHY_GUY_MASK);
    }

    @Override
    public void tick() {
        if (this.isLeashed() && !this.world.isClient) {
            Entity holdingEntity = this.getHoldingEntity();
            List<BooEntity> list = world.getEntitiesByClass(BooEntity.class, this.getBoundingBox().expand(2.0D), booEntity -> booEntity.getHoldingEntity() != null && booEntity.getHoldingEntity() == holdingEntity);
            if (!list.isEmpty() && holdingEntity instanceof ServerPlayerEntity) {
                ModCriteria.SOULMATES.trigger((ServerPlayerEntity) holdingEntity);
            }
        }
        super.tick();
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.initEquipment(random, difficulty);
        this.setShyGuyType(Type.fromId(random.nextBetween(0, Type.values().length)));
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        if (random.nextFloat() > 0.57F)
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(this.getTradeItems().getDataOrEmpty(random).orElse(ModItems.TURNIP)));
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        ItemStack headStack = player.getEquippedStack(EquipmentSlot.HEAD);
        if (itemStack.isOf(ModItems.COIN) && headStack.isOf(ModItems.SHY_GUY_MASK) && !this.getStackInHand(Hand.MAIN_HAND).isEmpty()) {
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            this.playAmbientSound();
            this.dropStack(this.getMainHandStack().copy());
            this.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
            this.setTarget(null);
            return ActionResult.success(world.isClient());
        }
        return super.interactMob(player, hand);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, YoshiEntity.class, 6.0f, 1.2, 1.4));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, BabyYoshiEntity.class, 6.0f, 1.2, 1.4));
        this.goalSelector.add(2, new CoinTemptGoal(this, 1.2D));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 1.1D, false));
        this.goalSelector.add(3, new EscapeDangerGoal(this, 1.0D));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 7.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.8D));
        this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge());
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true, entity -> !entity.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.SHY_GUY_MASK)));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return Sounds.ENTITY_SHY_GUY_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_SHY_GUY_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_SHY_GUY_DEATH;
    }

    public enum Type implements StringIdentifiable {
        RED("red", 0),
        BLUE("blue", 1),
        LIGHT_BLUE("light_blue", 2),
        BLACK("black", 2),
        PINK("pink", 3),
        ORANGE("orange", 4),
        YELLOW("yellow", 5),
        GREEN("green", 6),
        WHITE("white", 7);

        final String name;
        final int id;

        Type(String name, int id) {
            this.name = name;
            this.id = id;
        }

        static Type fromName(String name) {
            for (ShyGuyEntity.Type type : ShyGuyEntity.Type.values()) {
                if (!type.name.equals(name)) continue;
                return type;
            }
            return RED;
        }

        static Type fromId(int id) {
            for (ShyGuyEntity.Type type : ShyGuyEntity.Type.values()) {
                if (!(type.id == id)) continue;
                return type;
            }
            return RED;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }
}
