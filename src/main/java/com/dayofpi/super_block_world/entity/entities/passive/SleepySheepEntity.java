package com.dayofpi.super_block_world.entity.entities.passive;

import com.dayofpi.super_block_world.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SleepySheepEntity extends AnimalEntity implements Shearable {
    private static final TrackedData<Boolean> SHEARED = DataTracker.registerData(SleepySheepEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private int eatGrassTimer;
    private EatGrassGoal eatGrassGoal;
    public SleepySheepEntity(EntityType<? extends SleepySheepEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.eatGrassGoal = new EatGrassGoal(this);
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(3, new TemptGoal(this, 1.1, Ingredient.ofItems(Items.WHEAT), false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(5, this.eatGrassGoal);
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(8, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SHEARED, false);
    }

    protected void mobTick() {
        this.eatGrassTimer = this.eatGrassGoal.getTimer();
        super.mobTick();
    }

    public void tickMovement() {
        if (this.world.isClient) {
            this.eatGrassTimer = Math.max(0, this.eatGrassTimer - 1);
        }
        Vec3d vec3d = this.getVelocity();
        if (!this.onGround && vec3d.y < 0.0D) {
            this.setVelocity(vec3d.multiply(1.0D, 0.6D, 1.0D));
        }
        List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(2.0), entity -> entity.isAlive() && entity.isSleeping());
        if (!list.isEmpty()) {
            list.forEach(entity -> entity.heal(1.0F));
        }
        super.tickMovement();
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    public static DefaultAttributeContainer.Builder createSleepySheepAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23000000417232513);
    }

    public void handleStatus(byte status) {
        if (status == 10) {
            this.eatGrassTimer = 40;
        } else {
            super.handleStatus(status);
        }

    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    public float getHeadAngle(float delta) {
        if (this.eatGrassTimer > 4 && this.eatGrassTimer <= 36) {
            float f = ((float)(this.eatGrassTimer - 4) - delta) / 32.0F;
            return 0.62831855F + 0.21991149F * MathHelper.sin(f * 28.7F);
        } else {
            return this.eatGrassTimer > 0 ? 0.62831855F : this.getPitch() * 0.017453292F;
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        Entity entity = source.getSource();
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 100, 2));
            ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 2));
        }
        return super.damage(source, amount);
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.SHEARS)) {
            if (!this.world.isClient && this.isShearable()) {
                this.sheared(SoundCategory.PLAYERS);
                this.emitGameEvent(GameEvent.SHEAR, player);
                itemStack.damage(1, player, (p) -> p.sendToolBreakStatus(hand));
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.CONSUME;
            }
        } else {
            return super.interactMob(player, hand);
        }
    }

    public void sheared(SoundCategory shearedSoundCategory) {
        this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
        this.setSheared(true);
        int i = 1 + this.random.nextInt(2);

        for(int j = 0; j < i; ++j) {
            ItemEntity itemEntity = this.dropItem(ModBlocks.DREAMWOOL, 1);
            if (itemEntity != null) {
                itemEntity.setVelocity(itemEntity.getVelocity().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F));
            }
        }
    }

    public boolean isShearable() {
        return this.isAlive() && !this.isSheared() && !this.isBaby();
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Sheared", this.isSheared());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setSheared(nbt.getBoolean("Sheared"));
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SHEEP_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SHEEP_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SHEEP_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.BLOCK_WOOL_STEP, 0.15F, 1.2F);
    }

    @Override
    public float getSoundPitch() {
        return super.getSoundPitch() + 0.5F;
    }

    public boolean isSheared() {
        return this.dataTracker.get(SHEARED);
    }

    public void setSheared(boolean sheared) {
        this.dataTracker.set(SHEARED, sheared);
    }

    public void onEatingGrass() {
        super.onEatingGrass();
        this.setSheared(false);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
}
