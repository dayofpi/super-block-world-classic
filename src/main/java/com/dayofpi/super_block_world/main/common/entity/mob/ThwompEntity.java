package com.dayofpi.super_block_world.main.common.entity.mob;

import com.dayofpi.super_block_world.main.util.sounds.ModSounds;
import com.dayofpi.super_block_world.main.registry.TagRegistry;
import com.dayofpi.super_block_world.main.util.DirectionHelper;
import com.dayofpi.super_block_world.main.util.entity.ModEntityDamageSource;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@SuppressWarnings("deprecation, unused")
public class ThwompEntity extends GolemEntity {
    private static final TrackedData<Integer> STAGE;
    private static final TrackedData<Integer> HOME_LEVEL;
    private static final TrackedData<Integer> DIRECTION;
    private int riseTimer = 0;
    private int peekTimer = 0;
    private LivingEntity eyeTarget;
    private boolean falling;
    private boolean rising;

    static {
        STAGE = DataTracker.registerData(ThwompEntity.class, TrackedDataHandlerRegistry.INTEGER);
        HOME_LEVEL = DataTracker.registerData(ThwompEntity.class, TrackedDataHandlerRegistry.INTEGER);
        DIRECTION = DataTracker.registerData(ThwompEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    public ThwompEntity(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 9;
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 70.0);
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return 0.5F - world.getBrightness(pos);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(STAGE, 1);
        this.dataTracker.startTracking(HOME_LEVEL, 1);
        this.dataTracker.startTracking(DIRECTION, 0);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("HomeLevel", this.getHomeLevel());
        nbt.putInt("Direction", this.getDirection());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setHomeLevel(nbt.getInt("HomeLevel"));
        this.setDirection(nbt.getInt("Direction"));
    }

    public static boolean canSpawn(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return !(world.getLightLevel(pos) > 0) && !world.isSkyVisible(pos) && world.isSpaceEmpty(type.getDimensions().getBoxAt(Vec3d.ofCenter(pos)).expand(0, 5, 0).offset(0, -5, 0));
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        DirectionHelper.Direction direction = DirectionHelper.getCardinalDirection(this);
        this.setDirection(DirectionHelper.directionToInt(direction));
        this.setYaw(this.getDirection());
        this.headYaw = this.getYaw();
        this.falling = false;
        this.rising = false;
        this.setNoGravity(true);
        this.setHomeLevel(this.getBlockY());
        this.resetPosition();
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ENTITY_THWOMP_HURT;
    }

    public SoundEvent getDeathSound() {
        return ModSounds.ENTITY_THWOMP_DEATH;
    }

    public void tickMovement() {
        if (this.isInsideWall()) {
            // In a wall, die; don't keep doing things
            this.setHealth(0);
            return;
        }

        super.tickMovement();
        if (this.isAlive()) {
            if (this.getStage() == 1)
                homeLogic();

            if (falling) {
                fall();
            } else if (rising) {
                this.setStage(1);
                rise();
            } else {
                if (peekTimer > 0)
                    --peekTimer;
                Box farDetection = this.getBoundingBox().expand(6, 16, 6).offset(0, -16, 0);
                LivingEntity playerNear = world.getClosestEntity(PlayerEntity.class, TargetPredicate.createAttackable(), this, this.getX(), this.getY(), this.getZ(), farDetection);
                this.setEyeTarget(playerNear);
                if (playerNear != null && this.peekTimer == 0) {
                    Box closeDetection = this.getBoundingBox().expand(1, 12, 1).offset(0, -16, 0);
                    PlayerEntity playerUnder = world.getClosestEntity(PlayerEntity.class, TargetPredicate.createAttackable(), this, this.getX(), this.getY(), this.getZ(), closeDetection);
                    if (playerUnder != null && !falling) {
                        startFalling();
                    } else {
                        this.setStage(2);
                    }
                } else this.setStage(1);
            }
        }
    }

    public void setEyeTarget(LivingEntity entity) {
        this.eyeTarget = entity;
    }

    public LivingEntity getEyeTarget() {
        return this.eyeTarget;
    }

    private void homeLogic() {
        if (this.getBlockY() < this.getHomeLevel()) {
            this.rising = true;
        } else if (this.getBlockY() > this.getHomeLevel()) {
            this.setHomeLevel(this.getBlockY());
        } else if (this.getBlockY() == this.getHomeLevel()) {
            this.setVelocity(0, 0D, 0);
            if (rising) {
                this.rising = false;
                this.setYaw(this.getDirection());
                Vec3d vec3d = this.getPos();
                this.setPosition(vec3d.x, this.getHomeLevel(), vec3d.z);
                this.resetPosition();
                this.playSound(ModSounds.ENTITY_THWOMP_REST, 1.0F, this.getSoundPitch());
            }
        }
    }

    private void fall() {
        final double fallingSpeed = 0.5D;
        this.setVelocity(0, -fallingSpeed, 0);
        if (this.isOnGround()) {
            if (riseTimer == 0) {
                LivingEntity damageable = world.getClosestEntity(LivingEntity.class, TargetPredicate.createAttackable(), this, this.getX(), this.getY(), this.getZ(), this.getBoundingBox().expand(0.5).offset(0,0.5, 0));
                this.thwompLandingEffects(damageable);
            }
            if (riseTimer < 25)
                ++this.riseTimer;
            else {
                this.falling = false;
                this.rising = true;
            }
        } else riseTimer = 0;
    }
    private void rise() {
        final double risingSpeed = 0.1D;
        this.setVelocity(0, risingSpeed, 0);
    }

    private void startFalling() {
        this.falling = true;
        this.setStage(3);
        this.playSound(ModSounds.ENTITY_THWOMP_FALL, 1.0F, this.getSoundPitch());
        ParticleUtil.spawnParticle(world, this.getBlockPos(), (new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, Blocks.GRAVEL.getDefaultState())), UniformIntProvider.create(3, 4));
    }

    private void thwompLandingEffects(Entity entity) {
        this.playSound(ModSounds.ENTITY_THWOMP_LAND, 1.0F, 1.0F);
        this.peekTimer = 30;
        for (int i = 0; i < 6; i++) {
            double rand = this.random.nextBoolean() ? 0.05 : -0.05;
            world.addParticle(ParticleTypes.POOF, this.getX(), this.getY(), this.getZ(), i * rand, 0.02D, i * rand);
        }

        if (entity != null) {
            if (!(entity instanceof ThwompEntity))
                entity.damage(ModEntityDamageSource.mobDrop(this), 12F);
        }

        for (BlockPos blockPos : BlockPos.iterateOutwards(this.getBlockPos().down(), 1, 0, 1)) {
            if (world.getBlockState(blockPos).isIn(TagRegistry.BRICKS)) {
                world.breakBlock(blockPos, true);
            }
        }
    }

    public void takeKnockback(double strength, double x, double z) {}

    public int getStage() {
        return this.dataTracker.get(STAGE);
    }

    public void setStage(int stage) {
        this.dataTracker.set(STAGE, stage);
    }

    public int getHomeLevel() {
        return this.dataTracker.get(HOME_LEVEL);
    }

    public void setHomeLevel(int level) {
        this.dataTracker.set(HOME_LEVEL, level);
    }

    public int getDirection() {
        return this.dataTracker.get(DIRECTION);
    }

    public void setDirection(int direction) {
        this.dataTracker.set(DIRECTION, direction);
    }

    @Override
    protected BodyControl createBodyControl() {
        return new ThwompBodyControl(this);
    }

    @Override
    public boolean isCollidable() {
        return this.isAlive();
    }

    public boolean collidesWith(Entity other) {
        return false;
    }

    @Override
    public void pushAwayFrom(Entity entity) {
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    protected void pushAway(Entity entity) {
        if (entity.getY() > this.getY() && this.getVelocity().y != 0) {
            Vec3d vec3d = entity.getVelocity();
            entity.setVelocity(vec3d.x, this.getVelocity().y, vec3d.z);
        }
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    static class ThwompBodyControl extends BodyControl {
        public ThwompBodyControl(MobEntity mobEntity) {
            super(mobEntity);
        }

        @Override
        public void tick() {
        }
    }
}
