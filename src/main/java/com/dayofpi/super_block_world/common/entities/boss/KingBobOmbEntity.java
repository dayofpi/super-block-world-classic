package com.dayofpi.super_block_world.common.entities.boss;

import com.dayofpi.super_block_world.audio.ModMusic;
import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.goals.PickUpTargetGoal;
import com.dayofpi.super_block_world.common.entities.goals.StayByArenaGoal;
import com.dayofpi.super_block_world.common.entities.goals.SummonBobOmbsGoal;
import com.dayofpi.super_block_world.registry.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class KingBobOmbEntity extends ModBossEntity {
    private static final TrackedData<Integer> THROW_COOLDOWN;
    private static final TrackedData<Boolean> SUMMONING;
    private static final TrackedData<Boolean> LAUGHING;

    static {
        THROW_COOLDOWN = DataTracker.registerData(KingBobOmbEntity.class, TrackedDataHandlerRegistry.INTEGER);
        SUMMONING = DataTracker.registerData(KingBobOmbEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        LAUGHING = DataTracker.registerData(KingBobOmbEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    public KingBobOmbEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.lookControl = new YawAdjustingLookControl(this, 10);
        this.bossBar = new ServerBossBar(this.getDisplayName(), BossBar.Color.YELLOW, BossBar.Style.PROGRESS);
        this.stepHeight = 1.0F;
        this.experiencePoints = 20;
    }

    public static DefaultAttributeContainer.Builder createKingBobOmbAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 64.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18D).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6D).add(EntityAttributes.GENERIC_ARMOR, 20.0D);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new StayByArenaGoal(this));
        this.goalSelector.add(2, new SummonBobOmbsGoal(this));
        this.goalSelector.add(3, new PickUpTargetGoal(this));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
    }

    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        if (random.nextInt(3) == 0)
            this.dropItem(ModItems.MUSIC_DISC_FIRE_FACTORY);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(THROW_COOLDOWN, 0);
        this.dataTracker.startTracking(SUMMONING, false);
        this.dataTracker.startTracking(LAUGHING, false);
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.isLaughing() ? Sounds.ENTITY_KING_BOB_OMB_LAUGH : null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_KING_BOB_OMB_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_KING_BOB_OMB_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(Sounds.ENTITY_KING_BOB_OMB_STEP, 0.8F, 1.0F);
    }

    @Override
    protected float getSoundVolume() {
        return 3.0f;
    }

    public int getThrowCooldown() {
        return this.dataTracker.get(THROW_COOLDOWN);
    }

    private void setThrowCooldown(int time) {
        this.dataTracker.set(THROW_COOLDOWN, time);
    }

    public boolean isSummoning() {
        return this.dataTracker.get(SUMMONING);
    }

    public void setSummoning(boolean summoning) {
        this.dataTracker.set(SUMMONING, summoning);
    }

    public boolean isLaughing() {
        return this.dataTracker.get(LAUGHING);
    }

    public void setLaughing(boolean funny) {
        this.dataTracker.set(LAUGHING, funny);
    }

    private void throwEntity(LivingEntity entity) {
        this.setThrowCooldown(50);
        BlockPos blockPos = this.getBlockPos().offset(this.getHorizontalFacing(), 3);
        entity.requestTeleportAndDismount(blockPos.getX(), blockPos.getY() + 6.0D, blockPos.getZ());
        entity.setAttacker(this);
        this.playSound(Sounds.ENTITY_KING_BOB_OMB_THROW, 1.0F, 1.0F);
    }

    @Override
    protected float getJumpVelocity() {
        return super.getJumpVelocity() * 1.5F;
    }

    @Override
    protected void mobTick() {
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
        super.mobTick();
        if (!this.isAlive())
            return;
        if (this.getFirstPassenger() instanceof LivingEntity livingEntity) {
            this.throwEntity(livingEntity);
        } else {
            if (this.getThrowCooldown() > 0)
                this.setThrowCooldown(this.getThrowCooldown() - 1);
        }
        if (this.getTarget() != null) {
            if (this.getTarget().isDead() && !this.isLaughing())
                this.setLaughing(true);
        }
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("ThrowCooldown", this.getThrowCooldown());
        nbt.putBoolean("isSummoning", this.isSummoning());
        nbt.putBoolean("isLaughing", this.isLaughing());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setThrowCooldown(nbt.getInt("ThrowCooldown"));
        this.setSummoning(nbt.getBoolean("isSummoning"));
        this.setLaughing(nbt.getBoolean("isLaughing"));
        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }
    }

    @Override
    public MusicSound getBossMusic() {
        return ModMusic.BOSS_1;
    }

}
