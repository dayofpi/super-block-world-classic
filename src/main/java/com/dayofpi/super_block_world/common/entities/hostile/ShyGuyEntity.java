package com.dayofpi.super_block_world.common.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.passive.BabyYoshiEntity;
import com.dayofpi.super_block_world.common.entities.passive.YoshiEntity;
import com.dayofpi.super_block_world.registry.ModItems;
import com.dayofpi.super_block_world.registry.ModTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ShyGuyEntity extends HostileEntity implements IAnimatable {
    final AnimationFactory FACTORY = new AnimationFactory(this);

    public ShyGuyEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createShyGuyAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 7.0D).add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D);
    }

    private static boolean isThereNoLight(WorldAccess world, BlockPos pos) {
        return !(world.getLightLevel(LightType.BLOCK, pos) > 0);
    }

    @SuppressWarnings("unused")
    public static boolean canShyGuySpawn(EntityType<? extends ShyGuyEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        if (world.getBiome(pos).isIn(ModTags.SURFACE_SHY_GUY_SPAWN))
            return isThereNoLight(world, pos);
        else return isThereNoLight(world, pos) && world.getBlockState(pos.down()).isIn(ModTags.VANILLATE);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, YoshiEntity.class, 6.0f, 1.2, 1.4));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, BabyYoshiEntity.class, 6.0f, 1.2, 1.4));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.1D, false));
        this.goalSelector.add(3, new EscapeDangerGoal(this, 1.0D));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 7.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.8D));
        this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge());
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true, entity -> !entity.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.SHY_GUY_MASK)));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return Sounds.ENTITY_SHY_GUY_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_SHY_GUY_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_SHY_GUY_DEATH;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, animationEvent -> PlayState.STOP));
    }

    @Override
    public AnimationFactory getFactory() {
        return FACTORY;
    }
}