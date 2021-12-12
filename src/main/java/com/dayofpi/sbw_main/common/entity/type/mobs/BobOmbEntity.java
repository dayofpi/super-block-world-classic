package com.dayofpi.sbw_main.common.entity.type.mobs;

import com.dayofpi.sbw_main.util.sounds.ModSounds;
import com.dayofpi.sbw_main.registry.block.ModBlocks;
import com.dayofpi.sbw_main.common.entity.type.bases.EnemyEntity;
import com.dayofpi.sbw_main.common.entity.type.projectiles.FlowerFireballEntity;
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
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Random;
import java.util.UUID;

public class BobOmbEntity extends EnemyEntity implements Angerable {
    private static final TrackedData<Integer> FUSE_SPEED;
    private static final TrackedData<Boolean> IGNITED;
    private int lastFuseTime;
    private int currentFuseTime;
    private int fuseTime = 22;
    private int explosionRadius = 4;

    public BobOmbEntity(EntityType<? extends BobOmbEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new BobOmbIgniteGoal(this));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D);
    }

    public static boolean canSpawn(EntityType<BobOmbEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        BlockState blockState = world.getBlockState(pos.down());
        return EnemyEntity.isSpawnDark((ServerWorldAccess) world, pos, random) && (blockState.isOf(ModBlocks.VANILLATE) || blockState.isIn(BlockTags.PLANKS));
    }

    public boolean tryAttack(Entity target) {
        return true;
    }
    public float getClientFuseTime(float timeDelta) {
        return MathHelper.lerp(timeDelta, (float)this.lastFuseTime, (float)this.currentFuseTime) / (float)(this.fuseTime - 2);
    }

    public boolean damage(DamageSource source, float amount) {
        if (source.isFire() || source.getSource() instanceof FlowerFireballEntity || source.getSource() instanceof AbstractFireballEntity) {
            if (!this.world.isClient) {
                this.ignite();
            }
        }
        return super.damage(source, amount);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FUSE_SPEED, -1);
        this.dataTracker.startTracking(IGNITED, false);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putShort("Fuse", (short)this.fuseTime);
        nbt.putByte("ExplosionRadius", (byte)this.explosionRadius);
        nbt.putBoolean("ignited", this.isIgnited());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Fuse", 99)) {
            this.fuseTime = nbt.getShort("Fuse");
        }
        if (nbt.contains("ExplosionRadius", 99)) {
            this.explosionRadius = nbt.getByte("ExplosionRadius");
        }
        if (nbt.getBoolean("ignited")) {
            this.ignite();
        }
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ENTITY_BOB_OMB_HURT;
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.ENTITY_BOB_OMB_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(ModSounds.ENTITY_BOB_OMB_STEP, 1.0F, 1.0F);
    }

    public int getFuseSpeed() {
        return this.dataTracker.get(FUSE_SPEED);
    }

    public void setFuseSpeed(int fuseSpeed) {
        this.dataTracker.set(FUSE_SPEED, fuseSpeed);
    }

    public boolean isIgnited() {
        return this.dataTracker.get(IGNITED);
    }

    public void ignite() {
        this.dataTracker.set(IGNITED, true);
    }

    public void tick() {
        if (this.isAlive()) {
            this.lastFuseTime = this.currentFuseTime;
            if (this.isIgnited()) {
                this.setFuseSpeed(1);
            }

            int i = this.getFuseSpeed();
            if (i > 0 && this.currentFuseTime == 0) {
                double d = this.getX();
                double e = this.getY() + 1D;
                double f = this.getZ();
                world.addParticle(ParticleTypes.FLAME, d, e, f, 0.0D, 0.0D, 0.0D);
                this.playSound(ModSounds.ENTITY_BOB_OMB_FUSE, 1.0F, 1.0F);
                this.emitGameEvent(GameEvent.PRIME_FUSE);
                this.ignite();
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

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.FLINT_AND_STEEL)) {
            this.world.playSound(player, this.getX(), this.getY(), this.getZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, this.getSoundCategory(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            if (!this.world.isClient) {
                this.ignite();
                itemStack.damage(1, player, (playerX) -> playerX.sendToolBreakStatus(hand));
            }
            return ActionResult.success(this.world.isClient);
        } else {
            return super.interactMob(player, hand);
        }
    }

    private void explode() {
        if (!this.world.isClient) {
            Explosion.DestructionType destructionType = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE;
            this.dead = true;
            this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius, destructionType);
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
            areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
            for (StatusEffectInstance statusEffectInstance : collection) {
                areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffectInstance));
            }
            this.world.spawnEntity(areaEffectCloudEntity);
        }
    }

    static {
        FUSE_SPEED = DataTracker.registerData(BobOmbEntity.class, TrackedDataHandlerRegistry.INTEGER);
        IGNITED = DataTracker.registerData(BobOmbEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    @Override
    public int getAngerTime() {
        return 0;
    }

    @Override
    public void setAngerTime(int ticks) {
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return null;
    }

    @Override
    public void setAngryAt(@Nullable UUID uuid) {
    }

    @Override
    public void chooseRandomAngerTime() {
    }

    static class BobOmbIgniteGoal extends Goal {
        private final BobOmbEntity bobOmb;
        private LivingEntity target;

        public BobOmbIgniteGoal(BobOmbEntity bobOmb) {
            this.bobOmb = bobOmb;
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean canStart() {
            LivingEntity livingEntity = this.bobOmb.getTarget();
            return this.bobOmb.getFuseSpeed() > 0 || livingEntity != null && this.bobOmb.squaredDistanceTo(livingEntity) < 9.0D;
        }

        public void start() {
            this.bobOmb.getNavigation().stop();
            this.target = this.bobOmb.getTarget();
        }

        public void stop() {
            this.target = null;
        }

        public void tick() {
            if (this.target == null) {
                this.bobOmb.setFuseSpeed(-1);
            } else if (this.bobOmb.squaredDistanceTo(this.target) > 49.0D) {
                this.bobOmb.setFuseSpeed(-1);
            } else if (!this.bobOmb.getVisibilityCache().canSee(this.target)) {
                this.bobOmb.setFuseSpeed(-1);
            } else {
                this.bobOmb.setFuseSpeed(1);
            }
        }
    }
}
