package com.dayofpi.super_block_world.common.entities.mob;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.blocks.DryBonesPileBlock;
import com.dayofpi.super_block_world.common.entities.abst.AbstractEnemy;
import com.dayofpi.super_block_world.common.util.entity.DirectionHelper;
import com.dayofpi.super_block_world.registry.main.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;
import java.util.UUID;

public class DryBonesEntity extends AbstractEnemy implements IAnimatable {
    private static final UUID ATTACKING_SPEED_BOOST_ID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
    private static final EntityAttributeModifier ATTACKING_SPEED_BOOST = new EntityAttributeModifier(ATTACKING_SPEED_BOOST_ID, "Attacking speed boost", 0.05, EntityAttributeModifier.Operation.ADDITION);
    private final AnimationFactory factory = new AnimationFactory(this);

    public DryBonesEntity(EntityType<? extends AbstractEnemy> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 0;
        this.setPathfindingPenalty(PathNodeType.LAVA, 8.0f);
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
            if (this.isAttacking())
                event.getController().setAnimation(new AnimationBuilder().addAnimation("chase", true));
            else
                event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
            return PlayState.CONTINUE;
        }
        if (this.getHealth() <= 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("break", false));
            return PlayState.CONTINUE;
        } else event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public static boolean canSpawn(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isOf(BlockInit.CHARROCK) && pos.getY() < 0 || world.getBlockState(pos.down()).isOf(BlockInit.GRITZY_SAND) && isSpawnDark((ServerWorldAccess) world, pos, random);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return AbstractEnemy.createEnemyAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0).add(EntityAttributes.GENERIC_ARMOR, 2.0);
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        if (this.isDead() && this.getRecentDamageSource() != DamageSource.OUT_OF_WORLD) {
            if (!this.isFreezing() && (world.getBlockState(this.getBlockPos()).isAir() || world.getBlockState(this.getBlockPos()).isOf(Blocks.WATER))) {
                world.setBlockState(this.getBlockPos(), BlockInit.DRY_BONES_PILE.getDefaultState().with(DryBonesPileBlock.ROTATION, DirectionHelper.directionToIntProperty(DirectionHelper.getCardinalDirection(this))));
                world.createAndScheduleBlockTick(this.getBlockPos(), world.getBlockState(this.getBlockPos()).getBlock(), 90);
            } else Block.dropStack(world, this.getBlockPos(), new ItemStack(Items.BONE, random.nextInt(2) + 1));
        }
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
        return SoundInit.ENTITY_DRY_BONES_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.ENTITY_DRY_BONES_DEATH;
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
