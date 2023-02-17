package com.dayofpi.super_block_world.entity.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.block.blocks.BrickBlock;
import com.dayofpi.super_block_world.block.blocks.FakeBlock;
import com.dayofpi.super_block_world.block.blocks.ReactiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.List;

public class BulletBillEntity extends HostileEntity {
    private static final TrackedData<BlockPos> HOME_POS = DataTracker.registerData(BulletBillEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);

    public BulletBillEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.lookControl = new YawAdjustingLookControl(this, 45);
        this.moveControl = new FlightMoveControl(this, 10, true);
        this.setPathfindingPenalty(PathNodeType.UNPASSABLE_RAIL, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
    }

    public static DefaultAttributeContainer.Builder createBulletBillAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 18.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.3D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0D);
    }

    public void setHomePos(BlockPos pos) {
        this.dataTracker.set(HOME_POS, pos);
    }

    public BlockPos getHomePos() {
        return this.dataTracker.get(HOME_POS);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HOME_POS, BlockPos.ORIGIN);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("HomePosX", this.getHomePos().getX());
        nbt.putInt("HomePosY", this.getHomePos().getY());
        nbt.putInt("HomePosZ", this.getHomePos().getZ());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        int i = nbt.getInt("HomePosX");
        int j = nbt.getInt("HomePosY");
        int k = nbt.getInt("HomePosZ");
        this.setHomePos(new BlockPos(i, j, k));
        super.readCustomDataFromNbt(nbt);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, false));
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(true);
        birdNavigation.setCanSwim(false);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.age > 120)
            this.explode();
        if (this.horizontalCollision)
            this.explode();
        this.setVelocity(this.getHorizontalFacing().getVector().getX() * 0.2, 0.0D, this.getHorizontalFacing().getVector().getZ() * 0.2);
    }

    @Override
    protected void pushAway(Entity entity) {
        this.tryAttack(entity);
        super.pushAway(entity);
    }

    @Override
    public int getMaxLookPitchChange() {
        return 1;
    }

    @Override
    public int getMaxHeadRotation() {
        return 1;
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.explode();
        return super.tryAttack(target);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_BULLET_BILL_HURT;
    }

    private void explode() {
        this.discard();
        if (this.world instanceof ServerWorld serverWorld)
            serverWorld.spawnParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0, 0.0D, 0.0D, 0.0D, 0.0D);
        List<Entity> livingEntities = world.getOtherEntities(this, this.getBoundingBox().expand(0.2), EntityPredicates.VALID_LIVING_ENTITY);
        for (Entity entity : livingEntities) {
            entity.damage(DamageSource.explosion(null), 4.0F);
        }
        if (world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            for (BlockPos blockPos : BlockPos.iterateOutwards(this.getBlockPos(), 1, 1, 1)) {
                BlockState state = world.getBlockState(blockPos);
                if (state.getBlock() instanceof BrickBlock || state.getBlock() instanceof FakeBlock) {
                    world.breakBlock(blockPos, true);
                } else if (state.getBlock() instanceof ReactiveBlock reactiveBlock) {
                    reactiveBlock.react(world, blockPos, null);
                }
            }
        }
        this.playSound(Sounds.ENTITY_BULLET_IMPACT, 1.0F, this.getSoundPitch());
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean damage = super.damage(source, amount);
        if (damage) {
            if (source.getAttacker() != null) {
                this.setYaw(this.getYaw() - 180.0F);
                this.setTarget(null);
            }
            if (source.isExplosive())
                this.explode();
            return true;
        }
        return false;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }
}
