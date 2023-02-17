package com.dayofpi.super_block_world.entity.entities.boss;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.audio.ModMusic;
import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.entity.brains.KingBooBrain;
import com.dayofpi.super_block_world.item.ModItems;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class KingBooEntity extends ModBossEntity {
    private static final TrackedData<Boolean> WEAKENED;
    private static final float MIN_ALPHA = 0.5F;

    static {
         WEAKENED = DataTracker.registerData(KingBooEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    public final AnimationState chargingAnimationState = new AnimationState();
    private float alpha;

    public KingBooEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, true);
        this.lookControl = new YawAdjustingLookControl(this, 10);
        this.bossBar = new ServerBossBar(this.getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS);
        this.bossBar.setDarkenSky(true);
        this.bossBar.setThickenFog(true);
        this.experiencePoints = 35;
    }

    protected Brain.Profile<KingBooEntity> createBrainProfile() {
        return KingBooBrain.createProfile();
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return KingBooBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    public Brain<KingBooEntity> getBrain() {
        return (Brain<KingBooEntity>) super.getBrain();
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }

    public static DefaultAttributeContainer.Builder createKingBooAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0D).add(EntityAttributes.GENERIC_MAX_HEALTH, 70.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.3D).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6D).add(EntityAttributes.GENERIC_ARMOR, 20.0D);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.SONIC_BOOM) {
            this.chargingAnimationState.start(this.age);
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    public MusicSound getBossMusic() {
        return ModMusic.BOSS_2;
    }

    @Override
    protected Item getStar() {
        return ModItems.ZTAR;
    }

    @Override
    protected Item getRareItem() {
        return null;
    }

    @Override
    public int getMaxAttacks() {
        return 3;
    }

    @Override
    public boolean tryAttack(Entity target) {
        ModBossEntity.cooldown(this, 40);
        return super.tryAttack(target);
    }

    @Override
    protected void mobTick() {
        if ((this.age + this.getId()) % 1200 == 0) {
            StatusEffectInstance statusEffectInstance = new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 6000, 2);
            List<ServerPlayerEntity> list = StatusEffectUtil.addEffectToPlayersWithinDistance((ServerWorld)this.world, this, this.getPos(), 50.0, statusEffectInstance, 1200);
            list.forEach(serverPlayerEntity -> serverPlayerEntity.networkHandler.sendPacket(new GameStateChangeS2CPacket(Main.KING_BOO_CURSE, this.isSilent() ? GameStateChangeS2CPacket.DEMO_OPEN_SCREEN : (int)1.0f)));
        }
        this.world.getProfiler().push("kingBooBrain");
        this.getBrain().tick((ServerWorld)this.world, this);
        this.world.getProfiler().pop();
        this.world.getProfiler().push("kingBooActivityUpdate");
        KingBooBrain.updateActivities(this);
        this.world.getProfiler().pop();
        super.mobTick();
    }

    @Override
    public void tick() {
        int i = this.getWorld().getLightLevel(LightType.BLOCK, this.getBlockPos());

        if (i > 6)
            alpha += 0.05F;
        else
            alpha -= 0.05F;
        alpha = MathHelper.clamp(alpha, MIN_ALPHA, 1.0F);
        this.setWeakened(this.alpha == 1.0F);
        super.tick();
    }

    public float getAlpha() {
        return this.alpha;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return Sounds.ENTITY_KING_BOO_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_KING_BOO_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_KING_BOO_DEATH;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(WEAKENED, false);
    }

    public boolean isWeakened() {
        return this.dataTracker.get(WEAKENED);
    }

    public void setWeakened(boolean weakened) {
        if (weakened != this.dataTracker.get(WEAKENED) && weakened)
            this.playSound(Sounds.ENTITY_KING_BOO_WEAKENED, 1.0F, this.getSoundPitch());
        this.dataTracker.set(WEAKENED, weakened);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Weakened", this.isWeakened());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setWeakened(nbt.getBoolean("Weakened"));
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(false);
        return birdNavigation;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!this.isWeakened() && !source.isOutOfWorld())
            return false;
        return super.damage(source, amount);
    }
    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }
}
