package com.dayofpi.super_block_world.entity.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.UUID;

public class DryBonesEntity extends HostileEntity {
    private static final UUID ATTACKING_SPEED_BOOST_ID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
    private static final EntityAttributeModifier ATTACKING_SPEED_BOOST = new EntityAttributeModifier(ATTACKING_SPEED_BOOST_ID, "Attacking speed boost", 0.05, EntityAttributeModifier.Operation.ADDITION);
    private static final BlockState DRY_BONES_PILE = ModBlocks.DRY_BONES_PILE.getDefaultState();
    public boolean blockEntityContext;
    public final AnimationState idlingAnimationState = new AnimationState();
    public final AnimationState walkingAnimationState = new AnimationState();
    public final AnimationState chasingAnimationState = new AnimationState();
    public final AnimationState breakingAnimationState = new AnimationState();
    public final AnimationState wakingUpAnimationState = new AnimationState();

    public DryBonesEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 0;
        this.setPathfindingPenalty(PathNodeType.LAVA, 8.0f);
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return canSpawn(world.getBlockState(pos.down())) && isSpawnDark((ServerWorldAccess) world, pos, random);
    }

    private static boolean canSpawn(BlockState blockState) {
        return blockState.isOf(ModBlocks.GRITZY_SAND) || blockState.isOf(ModBlocks.CHARROCK);
    }

    public static DefaultAttributeContainer.Builder createDryBonesAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0).add(EntityAttributes.GENERIC_ARMOR, 2.0);
    }

    protected boolean shouldChase() {
        return this.shouldWalk() && this.isAttacking();
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        if (this.isDead() && this.getRecentDamageSource() != DamageSource.OUT_OF_WORLD) {
            BlockPos blockPos = this.getBlockPos();
            if (world.getBlockState(blockPos).isAir()) {
                world.setBlockState(blockPos, DRY_BONES_PILE);
                world.scheduleBlockTick(blockPos, world.getBlockState(blockPos).getBlock(), 90);
            } else this.dropCrushStacks();
        }
    }

    protected void dropCrushStacks() {
        Block.dropStack(world, this.getBlockPos(), new ItemStack(Items.BONE, random.nextInt(2) + 1));
    }

    private boolean shouldWalk() {
        return this.getVelocity().horizontalLengthSquared() > 1.0E-6;
    }

    @Override
    public void tick() {
        if (world.isClient && !this.blockEntityContext) {
            if (this.shouldChase()) {
                this.chasingAnimationState.startIfNotRunning(this.age);
                this.walkingAnimationState.stop();
                this.idlingAnimationState.stop();
            } else if (this.shouldWalk()) {
                this.walkingAnimationState.startIfNotRunning(this.age);
                this.chasingAnimationState.stop();
                this.idlingAnimationState.stop();
            } else {
                if (this.deathTime > 0) {
                    this.idlingAnimationState.stop();
                    this.breakingAnimationState.startIfNotRunning(this.age);
                }
                else {
                    this.idlingAnimationState.startIfNotRunning(this.age);
                    this.walkingAnimationState.stop();
                }
            }
        }
        super.tick();
    }

    @Override
    protected void mobTick() {
        EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (this.isAttacking()) {
            if (entityAttributeInstance != null && !entityAttributeInstance.hasModifier(ATTACKING_SPEED_BOOST)) {
                entityAttributeInstance.addTemporaryModifier(ATTACKING_SPEED_BOOST);
            }
        } else if (entityAttributeInstance != null && entityAttributeInstance.hasModifier(ATTACKING_SPEED_BOOST)) {
            entityAttributeInstance.removeModifier(ATTACKING_SPEED_BOOST);
        }
        super.mobTick();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_DRY_BONES_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_DRY_BONES_DEATH;
    }


    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(this.getStepSound(), 0.4f, 1.0f);
    }

    SoundEvent getStepSound() {
        return SoundEvents.ENTITY_WITHER_SKELETON_STEP;
    }

    protected void initGoals() {
        this.goalSelector.add(3, new AvoidSunlightGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }
}
