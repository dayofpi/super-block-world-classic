package com.dayofpi.super_block_world.common.entities.boss;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.audio.ModMusic;
import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.brains.KingBooBrain;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.control.FlightMoveControl;
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
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;

public class KingBooEntity extends ModBossEntity implements IAnimatable {
    private static final ImmutableList<SensorType<? extends Sensor<? super KingBooEntity>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_PLAYERS);
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModuleType.PATH, MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.NEAREST_ATTACKABLE);

    private static final TrackedData<Boolean> WEAKENED;
    private static final float MIN_ALPHA = 0.5F;

    static {
         WEAKENED = DataTracker.registerData(KingBooEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    private final AnimationFactory FACTORY = new AnimationFactory(this);
    private float alpha;

    public KingBooEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, true);
        this.bossBar = new ServerBossBar(this.getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS);
        this.experiencePoints = 35;
    }

    public static DefaultAttributeContainer.Builder createKingBooAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D).add(EntityAttributes.GENERIC_MAX_HEALTH, 70.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23D).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.23D).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6D).add(EntityAttributes.GENERIC_ARMOR, 20.0D);
    }

    @Override
    public AnimationFactory getFactory() {
        return FACTORY;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    protected <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public MusicSound getBossMusic() {
        return ModMusic.BOSS_2;
    }

    @Override
    protected Brain.Profile<KingBooEntity> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return KingBooBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Brain<KingBooEntity> getBrain() {
        return (Brain<KingBooEntity>) super.getBrain();
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }

    @Override
    protected void mobTick() {
        if ((this.age + this.getId()) % 1200 == 0) {
            StatusEffectInstance statusEffectInstance = new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 6000, 2);
            List<ServerPlayerEntity> list = StatusEffectUtil.addEffectToPlayersWithinDistance((ServerWorld)this.world, this, this.getPos(), 50.0, statusEffectInstance, 1200);
            list.forEach(serverPlayerEntity -> serverPlayerEntity.networkHandler.sendPacket(new GameStateChangeS2CPacket(Main.KING_BOO_CURSE, this.isSilent() ? GameStateChangeS2CPacket.DEMO_OPEN_SCREEN : (int)1.0f)));
        }
        this.world.getProfiler().push("kingBooBrain");
        this.getBrain().tick((ServerWorld) this.world, this);
        this.world.getProfiler().pop();
        this.world.getProfiler().push("kingBooActivityUpdate");
        KingBooBrain.updateActivities(this);
        this.world.getProfiler().pop();
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
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
    public int getMaxLookPitchChange() {
        return super.getMaxLookPitchChange();
    }

    @Override
    public int getMaxHeadRotation() {
        return super.getMaxHeadRotation();
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
