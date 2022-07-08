package com.dayofpi.super_block_world.common.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.BobOmb;
import com.dayofpi.super_block_world.common.entities.goals.BobOmbIgniteGoal;
import com.dayofpi.super_block_world.common.entities.passive.BobOmbBuddyEntity;
import com.dayofpi.super_block_world.common.entities.projectile.ModFireballEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class BobOmbEntity extends HostileEntity implements BobOmb {
    private static final TrackedData<Integer> FUSE_SPEED;
    private static final TrackedData<Boolean> IGNITED;
    private static final TrackedData<Boolean> PARACHUTED;

    static {
        FUSE_SPEED = DataTracker.registerData(BobOmbEntity.class, TrackedDataHandlerRegistry.INTEGER);
        IGNITED = DataTracker.registerData(BobOmbEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        PARACHUTED = DataTracker.registerData(BobOmbEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    private int windUp = 0;
    private int lastFuseTime;
    private int currentFuseTime;
    private int fuseTime = 25;
    private int explosionRadius = 2;

    public BobOmbEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0f);
    }

    public static DefaultAttributeContainer.Builder createBobOmbAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 14).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D);
    }

    @SuppressWarnings("unused")
    public static boolean canBobOmbSpawn(EntityType<BobOmbEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        return !(world.getLightLevel(LightType.BLOCK, blockPos) > 0);
    }

    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return 0.0F;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new BobOmbIgniteGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.7D));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, BobOmbBuddyEntity.class, false));
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        super.playStepSound(pos, state);
        this.playSound(Sounds.ENTITY_BOB_OMB_STEP, 0.15F, 1.0F);
    }

    @Override
    public void playAmbientSound() {
        this.playSound(this.getAmbientSound(), 0.3F, 1.0F);
    }

    @Override
    public int getMinAmbientSoundDelay() {
        return 250;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return Sounds.ENTITY_BOB_OMB_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_BOB_OMB_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_BOB_OMB_DEATH;
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FUSE_SPEED, -1);
        this.dataTracker.startTracking(IGNITED, false);
        this.dataTracker.startTracking(PARACHUTED, false);
    }

    @Override
    public void onLanding() {
        super.onLanding();
        this.setParachuted(false);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return !this.hasParachute() && super.handleFallDamage(fallDistance, damageMultiplier, damageSource);
    }

    public void tick() {
        if (this.isAlive()) {
            if (!this.hasParachute() && this.fallDistance > this.getSafeFallDistance()) {
                this.setParachuted(true);
            }

            Vec3d vec3d = this.getVelocity();
            if (this.hasParachute() && !this.onGround && vec3d.y < 0.0D) {
                this.setVelocity(vec3d.multiply(1.0D, 0.5D, 1.0D));
            }

            if (world.isClient && vec3d.horizontalLengthSquared() > 0)
                ++windUp;

            this.lastFuseTime = this.currentFuseTime;
            if (this.isIgnited()) {
                this.setFuseSpeed(1);
            }

            int i = this.getFuseSpeed();
            if (i > 0 && this.currentFuseTime == 0) {
                this.playSound(Sounds.ENTITY_BOB_OMB_FUSE, 1.0F, 0.7F);
                this.emitGameEvent(GameEvent.PRIME_FUSE);
            }

            if (this.world.isClient() && (this.isAttacking() || currentFuseTime > 0)) {
                world.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.9D, this.getZ(), 0.0D, 0.0D, 0.0D);
            }

            this.currentFuseTime += i;
            if (this.currentFuseTime < 0) {
                this.currentFuseTime = 0;
            }

            if (this.currentFuseTime >= this.fuseTime) {
                this.currentFuseTime = this.fuseTime;
                this.explode();
            }
        }

        super.tick();
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putShort("Fuse", (short) this.fuseTime);
        nbt.putByte("ExplosionRadius", (byte) this.explosionRadius);
        nbt.putBoolean("Ignited", this.isIgnited());
        nbt.putBoolean("Parachuted", this.hasParachute());
    }

    public boolean isIgnited() {
        return this.dataTracker.get(IGNITED);
    }

    @Override
    public int getWindUp() {
        return this.windUp;
    }

    public boolean hasParachute() {
        return this.dataTracker.get(PARACHUTED);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Fuse", 99)) {
            this.fuseTime = nbt.getShort("Fuse");
        }

        if (nbt.contains("ExplosionRadius", 99)) {
            this.explosionRadius = nbt.getByte("ExplosionRadius");
        }

        if (nbt.getBoolean("Ignited")) {
            this.ignite();
        }

        this.setParachuted(nbt.getBoolean("Parachuted"));
    }

    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.FLINT_AND_STEEL)) {
            this.world.playSound(player, this.getX(), this.getY(), this.getZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, this.getSoundCategory(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            if (!this.world.isClient) {
                this.ignite();
                itemStack.damage(1, player, (user) -> user.sendToolBreakStatus(hand));
            }

            return ActionResult.success(this.world.isClient);
        } else {
            return super.interactMob(player, hand);
        }
    }

    public boolean tryAttack(Entity target) {
        return true;
    }

    public void ignite() {
        this.dataTracker.set(IGNITED, true);
    }

    public void setParachuted(boolean parachuted) {
        this.dataTracker.set(PARACHUTED, parachuted);
    }

    public int getFuseSpeed() {
        return this.dataTracker.get(FUSE_SPEED);
    }

    public void setFuseSpeed(int fuseSpeed) {
        this.dataTracker.set(FUSE_SPEED, fuseSpeed);
    }

    private void explode() {
        if (!this.world.isClient) {
            Explosion.DestructionType destructionType = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE;
            this.dead = true;
            this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), (float) this.explosionRadius, destructionType);
            this.discard();
            this.spawnEffectsCloud();
        }

    }

    private void spawnEffectsCloud() {
        Collection<StatusEffectInstance> collection = this.getStatusEffects();
        if (!collection.isEmpty()) {
            AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
            areaEffectCloudEntity.setRadius(2.5F);
            areaEffectCloudEntity.setRadiusOnUse(-0.5F);
            areaEffectCloudEntity.setWaitTime(10);
            areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 2);
            areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float) areaEffectCloudEntity.getDuration());

            for (StatusEffectInstance statusEffectInstance : collection) {
                areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffectInstance));
            }

            this.world.spawnEntity(areaEffectCloudEntity);
        }

    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        Entity entity = source.getSource();
        if (entity != null && source.isProjectile() && entity.isOnFire())
            ignite();
        else if (entity instanceof ModFireballEntity)
            ignite();
        else if (source.isExplosive())
            ignite();
        return super.damage(source, amount);
    }

    public float getClientFuseTime(float timeDelta) {
        return MathHelper.lerp(timeDelta, (float) this.lastFuseTime, (float) this.currentFuseTime) / (float) (this.fuseTime - 2);
    }
}
